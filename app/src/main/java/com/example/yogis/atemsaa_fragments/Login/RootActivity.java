package com.example.yogis.atemsaa_fragments.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;
import com.parse.Parse;

public class RootActivity extends AppCompatActivity {

    static int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_root);



    }

    @Override
    protected void onStop() {
        Log.i("Root","Entro en onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i("Root","Entro en onDestroy");
        super.onDestroy();
    }
}
