package com.example.ipsappfinal;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

    Activity contextParent;
    WifiManager wifiManager;
    private int rssi1 =0 ;
    private int rssi2 = 0;
    private int rssi3 = 0;
    private int rssi4 = 0;
    private int rssi5 = 0;
    private int check1 =0;
    private int check2 =0;



    private static final String TAG = "MyAsyncTask";
    public MyAsyncTask (Activity contextParent){
        this.contextParent = contextParent;
    }

    GetName getName = new GetName();
    GetData getData = new GetData();

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        ImageView houseView = (ImageView) contextParent.findViewById(R.id.houseImg);
        houseView.setVisibility(View.VISIBLE);
        wifiManager = (WifiManager) contextParent.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        Toast.makeText(contextParent,"Start Positioning",Toast.LENGTH_SHORT).show();

    }
    @Override
    protected Void doInBackground(Void... voids) {
        if(!wifiManager.isWifiEnabled()){
            publishProgress(6);
        }
        ScanWifi scanWifi = new ScanWifi(contextParent);
        while (getName.getData()==null){
            getName.getName();
            check1 = check1+1;
            SystemClock.sleep(1000);
            if(check1==2){
                break;
            }
        }
        if(getName.getData()!=null) {
            while (rssi1 == 0 || rssi2 == 0 || rssi3 == 0 || rssi4 == 0 || rssi5==0) {
                scanWifi.scanWifi(wifiManager, getName.getName1(), getName.getName2(), getName.getName3(),getName.getName4(),getName.getName5());
                check2 = check2 + 1;
                SystemClock.sleep(1000);
                if (check2 == 3) {
                    rssi1 = scanWifi.getRssi1();
                    rssi2 = scanWifi.getRssi2();
                    rssi3 = scanWifi.getRssi3();
                    rssi4 = scanWifi.getRssi4();
                    rssi5 = scanWifi.getRssi5();
                    break;
                }
            }
            if (rssi1 != 90 && rssi2 != 90 && rssi3 != 90 && rssi4!=90) {
                while (true) {
                    if (isCancelled()) {
                        break;
                    }
                    while (rssi1==90||rssi2==90||rssi3==90||rssi4==90
                            ||(rssi1==90&&rssi5==90)
                            ||(rssi2==90&&rssi5==90)
                            ||(rssi3==90&&rssi5==90)
                            ||(rssi4==90&&rssi5==90)){
                        publishProgress(5);
                        scanWifi.scanWifi(wifiManager, getName.getName1(), getName.getName2(), getName.getName3(),getName.getName4(),getName.getName5());
                        rssi1 = scanWifi.getRssi1();
                        rssi2 = scanWifi.getRssi2();
                        rssi3 = scanWifi.getRssi3();
                        rssi4 = scanWifi.getRssi4();
                        rssi5 = scanWifi.getRssi5();
                        SystemClock.sleep(3000);
                    }
                    scanWifi.scanWifi(wifiManager, getName.getName1(), getName.getName2(), getName.getName3(),getName.getName4(),getName.getName5());
                    SystemClock.sleep(1000);
                    rssi1 = scanWifi.getRssi1();
                    rssi2 = scanWifi.getRssi2();
                    rssi3 = scanWifi.getRssi3();
                    rssi4 = scanWifi.getRssi4();
                    rssi5 = scanWifi.getRssi5();
                    getData.getData(rssi1, rssi2, rssi3,rssi4,rssi5);
                    publishProgress(getData.getPos());
                    SystemClock.sleep(4000);
                }
            } else {
                publishProgress(getData.getPos());
            }
        }else{
            publishProgress(getData.getPos());
        }

        return null;
    }
    protected void onProgressUpdate(Integer...pos){
        super.onProgressUpdate(pos);
        if(getName.getData()!=null) {
            if(getData.getResult()!=null){
            if (pos != null) {
                int flag = pos[0];
                Animation animation = new AlphaAnimation(1,0);
                animation.setDuration(1000);
                animation.setInterpolator(new LinearInterpolator());
                animation.setRepeatCount(Animation.INFINITE);
                animation.setRepeatMode(Animation.REVERSE);
                ImageView pointView = (ImageView) contextParent.findViewById(R.id.pointImg);
                pointView.setVisibility(View.VISIBLE);
                pointView.setAnimation(animation);
                TextView textView = (TextView) contextParent.findViewById(R.id.resultTxt);
                Log.d(TAG, "Result= "+ getData.getPos());
                textView.setText("Press 'Back' to return to Menu");
                if(flag == 0){
                    pointView.setX(470);
                    pointView.setY(140);
                }
                else if(flag==1){
                    pointView.setX(470);
                    pointView.setY(400);
                }
                else if(flag==2){
                    pointView.setX(370);
                    pointView.setY(770);

                }
                else if(flag==3){
                    pointView.setX(370);
                    pointView.setY(1090);
                }
                else if(flag==4){
                    pointView.setX(470);
                    pointView.setY(1270);
                }
                else if(flag==5){
                    pointView.setX(100);
                    pointView.setY(100);
                    textView.setText("You Went too far!!");
                }
            }
        }}
    }
    protected void onPostExecute(final Void unused){
        super.onPreExecute();
        if(getName.getData()==null){
            if (!wifiManager.isWifiEnabled()){
                TextView textView = (TextView) contextParent.findViewById(R.id.resultTxt);
                textView.setText("Please turn on/check your wifi connection");
            }else {
                TextView textView = (TextView) contextParent.findViewById(R.id.resultTxt);
                textView.setText("Cannot connect to server");
            }
        }
        else {
            if(getData.getResult()==null){
                TextView textView = (TextView) contextParent.findViewById(R.id.resultTxt);
                textView.setText("Cannot find required Wifi: "+getName.getData());
            }


        }
    }

}