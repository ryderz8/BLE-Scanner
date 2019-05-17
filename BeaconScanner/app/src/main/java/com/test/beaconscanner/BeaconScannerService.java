package com.test.beaconscanner;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.altbeacon.beacon.Beacon;

import java.util.Collection;
import java.util.Date;


/**
 * Created by amresh on 4/3/17.
 */

public class BeaconScannerService extends Service implements BeaconScanCallback {

    private static final String TAG = "BeaconScannerService";
    private BeaconScanner scanner;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @NonNull
    @Override
    public void onSuccesfulScan(Collection<Beacon> beacons) {
        Log.d(getClass().getSimpleName(), "" + beacons.size());

    }

    @Nullable
    @Override
    public void onFailedScan() {
        Log.d(getClass().getSimpleName(), "No Beacon scanned");
    }


    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        enableBluetoothImmediately();

        processCommand(intent);

        return Service.START_REDELIVER_INTENT;
    }

    private void processCommand(Intent intent) {
        Log.i(TAG, "Scan started at " + new Date().getTime());
        // Initializing scan service.
        initBeaconScanService();

    }

    private void enableBluetoothImmediately() {
        try {
            BluetoothAdapter.getDefaultAdapter().enable();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }

    private void initBeaconScanService() {
        if (scanner == null) {
            scanner = new BeaconScanner(getApplicationContext(), this);
            scanner.startScanning();
        } else {
            scanner.stopScanning();
            scanner.startScanning();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        if (scanner != null) {
            scanner.stopScanning();
        }

    }
}
