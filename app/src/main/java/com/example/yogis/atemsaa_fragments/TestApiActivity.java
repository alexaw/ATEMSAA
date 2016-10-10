package com.example.yogis.atemsaa_fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yogis.atemsaa_fragments.net.api.PlcMms.PlcMms;
import com.example.yogis.atemsaa_fragments.net.api.PlcMms.PlcMmsApi;
import com.example.yogis.atemsaa_fragments.utl.Qk;

import java.util.List;

public class TestApiActivity extends AppCompatActivity implements PlcMmsApi.OnPlcMmsList {

    PlcMmsApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);
        api = new PlcMmsApi(this, Qk.getLoading(this));
        api.getAll(this);
    }

    @Override
    public void onPlcMmsList(List<PlcMms> data) {

    }
}
