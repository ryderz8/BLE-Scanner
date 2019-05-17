package com.test.beaconscanner;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.service.scanner.NonBeaconLeScanCallback;

import java.util.Collection;

import static com.test.beaconscanner.Utils.turnOnBluetooth;

/**
 * Scanner class to start ranging beacons in range.
 */
public class BeaconScanner implements BeaconConsumer, NonBeaconLeScanCallback {

    protected static final String TAG = "BeaconScanner";
    private static BeaconManager beaconManager;
    private Context context;
    private static BeaconScanCallback beaconScanCallback;
    private SharedPreferences preferences;

    public BeaconScanner(Context context, BeaconScanCallback callback) {
        this.context = context;
        this.beaconScanCallback = callback;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context);

        turnOnBluetooth();
        if(beaconManager == null) {
            beaconManager = BeaconManager.getInstanceForApplication(this.context);
            beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
            beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.ALTBEACON_LAYOUT));

//
//            // Sets between scanning periods.
            beaconManager.setForegroundBetweenScanPeriod(Integer.parseInt(preferences.getString("key_between_scan_period", "500")));
            beaconManager.setBackgroundBetweenScanPeriod(Integer.parseInt(preferences.getString("key_between_scan_period", "500")));


        }
    }

    public void startScanning() {
        if(!beaconManager.isBound(this))
        beaconManager.bind(this);
    }

    public void stopScanning() {
        beaconManager.unbind(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onBeaconServiceConnect() {

        beaconManager.addRangeNotifier((Collection<Beacon> beacons, Region region) -> {
            try{
                if (beacons.size() > 0) {
                    beaconScanCallback.onSuccesfulScan(beacons);
                    Log.d(TAG, "didRangeBeaconsInRegion called with beacon count:  " + beacons.size());
                } else {
                    beaconScanCallback.onFailedScan();
                }
            }catch (Exception e){
                context.startActivity(context.getPackageManager()
                        .getLaunchIntentForPackage(context.getPackageName()).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
            beaconManager.setNonBeaconLeScanCallback(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Context getApplicationContext() {
        return context.getApplicationContext();
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        context.unbindService(serviceConnection);

    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return context.bindService(intent, serviceConnection, i);
    }

    // Here we are getting the raw packets
    @Override
    public void onNonBeaconLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {

        Log.i(TAG, "beacons byte length:  " + bytes.length);
        String name = bluetoothDevice.getName();
        Log.i(TAG, "beacons name:  " + name);

    }
}
