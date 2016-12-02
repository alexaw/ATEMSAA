package com.example.yogis.atemsaa_fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yogis.atemsaa_fragments.net.api.Cliente.Cliente;
import com.example.yogis.atemsaa_fragments.net.api.Cliente.ClienteApi;
import com.example.yogis.atemsaa_fragments.net.api.PlcMms.PlcMms;
import com.example.yogis.atemsaa_fragments.net.api.PlcMms.PlcMmsApi;
import com.example.yogis.atemsaa_fragments.url.Qk;

import java.util.List;

public class TestApiActivity extends AppCompatActivity implements PlcMmsApi.OnPlcMmsList, ClienteApi.OnClienteList {

    PlcMmsApi api;
    ClienteApi apiCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);

        api = new PlcMmsApi(this, Qk.getLoading(this));
        api.getAll(this);

        apiCliente = new ClienteApi(this, Qk.getLoading(this));
        apiCliente.getAll(this);
    }

    @Override
    public void onPlcMmsList(List<PlcMms> data) {

    }

    @Override
    public void onClienteList(List<Cliente> data) {

    }
}
