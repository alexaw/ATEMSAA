package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.adapters.ClientAdapter;
import com.example.yogis.atemsaa_fragments.net.api.Cliente.Cliente;
import com.example.yogis.atemsaa_fragments.net.api.Cliente.ClienteApi;
import com.example.yogis.atemsaa_fragments.net.http.HttpApi;
import com.example.yogis.atemsaa_fragments.net.http.HttpAsyncTask;
import com.example.yogis.atemsaa_fragments.net.http.HttpConnection;
import com.example.yogis.atemsaa_fragments.net.http.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientFragment extends Fragment implements ClienteApi.OnClienteList, View.OnClickListener {

    ClienteApi clienteApi;
    MainActivity activity;
    OnChangeFragment changeFragment;

    Button btnListClient;
    TextView tvRtaListClient;
    RecyclerView recyclerView;
    ClientAdapter adapter;



    public ClientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        changeFragment = (OnChangeFragment) context;
        activity = (MainActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vista6 = inflater.inflate(R.layout.fragment_client, container, false);

        btnListClient = (Button) vista6.findViewById(R.id.btn_consulta);
        btnListClient.setOnClickListener(this);

        clienteApi= new ClienteApi(getActivity(), null);
        clienteApi.getAll(this);

        recyclerView = (RecyclerView) vista6.findViewById(R.id.recycler_cliente);
        adapter = new ClientAdapter(getLayoutInflater(null));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));






        return vista6;
    }

    @Override
    public void onClienteList(List<Cliente> data) {
        Log.i("tamanio cliente", ""+data.size());
        adapter.setData(data);
           }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_consulta:


                break;
        }
    }
}
