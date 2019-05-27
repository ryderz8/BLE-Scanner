package com.test.beaconscanner;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.altbeacon.beacon.Beacon;

import java.util.Collection;

/**
 * Created by amresh on 09/11/18.
 */
public interface BeaconScanCallback {

    @NonNull
    void onSuccesfulScan(Collection<Beacon> beacons);

    @Nullable
    void onFailedScan();
}
