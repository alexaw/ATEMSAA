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

        SharedPreferences preferences = getSharedPreferences(LoginActivity.PREFERENCE, MODE_PRIVATE);

        //Con el preference sabemos si se esta logueado o no
        boolean login = preferences.getBoolean(LoginActivity.KEY_LOGIN, false);
        Intent intent = null;

        if(login)
            intent = new Intent(this, MainActivity.class);
        else

            while (a==0) {
                //Se inicializa Parse
                Parse.initialize(this, "oWuXpc5ahPda0W9eBntpFNW3Grk3wLwVgSJZzbyQ", "JmCInHZTQuiYtizQTPYVDk6qUZ6VQLq4585RNr6q");
                a++;

                if (a>0){

                    break;
                }
            }
        intent = new Intent(this, LoginActivity.class);


        startActivity(intent);

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
