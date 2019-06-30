package com.example.ipsappfinal;


import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetName {

    private String data;
    private String name1 ;
    private String name2 ;
    private String name3;
    private String name4;
    private String name5;
    private static final String TAG = "GetName";

    public String getName1(){
        return name1;
    }
    public String getName2(){
        return name2;
    }
    public String getName3(){
        return name3;
    }

    public String getName4(){return name4; }
    public String getName5(){return name5;}
    public String getData(){
        return data;
    }
    public void getName(){
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://192.168.1.66:5000/hello")
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    data = response.body().string().replace("\"","");
                    Log.d(TAG,"DATA = " + data);
                    name1 = data.split(",")[0];
                    name2 = data.split(",")[1];
                    name3 = data.split(",")[2];
                    name4 = data.split(",")[3];
                    name5 = data.split(",")[4].replace("\n","");
                }
            }
        });
    }

}