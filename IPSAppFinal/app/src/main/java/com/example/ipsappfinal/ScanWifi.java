package com.example.ipsappfinal;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ScanWifi {

    Activity contextParent;

    private List<ScanResult> results;
    private int rssi1;
    private int rssi2;
    private int rssi3;
    private int rssi4;
    private int rssi5;
    private static final String TAG = "ScanWifi";



    public ScanWifi(Activity contextParent) {
        this.contextParent = contextParent;
    }

    public int getRssi1() {
        return rssi1;
    }

    public int getRssi2() {
        return rssi2;
    }

    public int getRssi3() {
        return rssi3;
    }

    public int getRssi4(){return rssi4;}

    public int getRssi5(){return rssi5;}

    public void scanWifi(WifiManager wifiManager, String name1, String name2, String name3,String name4,String name5) {

        BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                results = wifiManager.getScanResults();
                contextParent.unregisterReceiver(this);
                rssi1 = 90;
                rssi2 = 90;
                rssi3 = 90;
                rssi4 = 90;
                rssi5 = 90;
                for (ScanResult scanResult : results) {
                    if (StringUtils.equals(name1, scanResult.SSID)) {
                        rssi1 = -scanResult.level;
                        Log.d(TAG,name1 + "RSSI1: /n" + rssi1);
                    }
                    if (StringUtils.equals(name2, scanResult.SSID)) {
                        rssi2 = -scanResult.level;
                        Log.d(TAG,name2 + "RSSI1: /n" + rssi2);
                    }
                    if (StringUtils.equals(name3, scanResult.SSID)) {
                        rssi3 = -scanResult.level;
                        Log.d(TAG,name3 + "RSSI1: /n" + rssi3);
                    }
                    if (StringUtils.equals(name4, scanResult.SSID)) {
                        rssi4 = -scanResult.level;
                        Log.d(TAG,name4 + "RSSI1: /n" + rssi4);
                    }
                    if (StringUtils.equals(name5, scanResult.SSID)) {
                        rssi5 = -scanResult.level;
                        Log.d(TAG,name5 + "RSSI1: /n" + rssi5);
                    }
                }
            }
        };
        contextParent.registerReceiver(wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
        wifiReceiver.onReceive(contextParent, contextParent.getIntent());
    }
}