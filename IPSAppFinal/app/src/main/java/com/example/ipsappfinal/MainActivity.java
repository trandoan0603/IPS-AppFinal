package com.example.ipsappfinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    MyAsyncTask myAsyncTask;
    ImageView houseImg;
    ImageView pointImg;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectToView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (myAsyncTask != null) {
            Log.d(TAG, "onBackPressed: cancel");
            myAsyncTask.cancel(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        myAsyncTask = new MyAsyncTask(MainActivity.this);
        myAsyncTask.execute();
    }

    public void connectToView(){
        houseImg = findViewById(R.id.houseImg);
        houseImg.setVisibility(View.INVISIBLE);
        pointImg = findViewById(R.id.pointImg);
        pointImg.setVisibility(View.INVISIBLE);
        textView = findViewById(R.id.resultTxt);
    }
}