package com.example.ipsappfinal;


import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetData {

    private static final String TAG = "GetData";
    private String result = null;
    private int pos;

    public int getPos(){
        return pos;
    }
    public String getResult(){
        return result;
    }

    public void getData(int a, int b, int c,int d,int e){
        OkHttpClient client = new OkHttpClient();
        String data = "{\n" +
                "\"a\":"+String.valueOf(a)+",\n" +
                "\"b\":"+String.valueOf(b)+",\n" +
                "\"c\":"+String.valueOf(c)+",\n" +
                "\"d\":"+String.valueOf(d)+",\n" +
                "\"e\":"+String.valueOf(e)+"\n" +
                "}";
        String json = new Gson().toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        final Request request = new Request.Builder().url("http://192.168.1.66:5000/hello")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    result = response.body().string();
                    try {
                        JSONObject json = new JSONObject(result);
                        String u = json.getString("a");
                        pos = Integer.parseInt(u.replace("['","").replace("']",""));
                        Log.d(TAG, "Result=" + pos);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }}
        });
    }
}
