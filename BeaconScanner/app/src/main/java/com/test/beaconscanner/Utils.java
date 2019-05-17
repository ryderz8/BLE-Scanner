package com.test.beaconscanner;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.os.Build;

/**
 * Created by amresh on 14/05/2019
 */
public class Utils {

    public static boolean isBluetoothDeviceConnected() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET) == BluetoothHeadset.STATE_CONNECTED
                    || mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP) == BluetoothA2dp.STATE_CONNECTED
                    || mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEALTH) == BluetoothA2dp.STATE_CONNECTED
                    || mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.GATT) == BluetoothA2dp.STATE_CONNECTED
                    || mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.GATT_SERVER) == BluetoothA2dp.STATE_CONNECTED
                    || mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.SAP) == BluetoothA2dp.STATE_CONNECTED;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET) == BluetoothHeadset.STATE_CONNECTED
                    || mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP) == BluetoothA2dp.STATE_CONNECTED
                    || mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEALTH) == BluetoothA2dp.STATE_CONNECTED
                    || mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.GATT) == BluetoothA2dp.STATE_CONNECTED
                    || mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.GATT_SERVER) == BluetoothA2dp.STATE_CONNECTED;
        } else {
            return mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET) == BluetoothHeadset.STATE_CONNECTED
                    || mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP) == BluetoothA2dp.STATE_CONNECTED
                    || mBluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEALTH) == BluetoothA2dp.STATE_CONNECTED;
        }
    }

    public static void turnOnBluetooth() {
        //enabling bluetooth automatically
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
        }
    }

}
