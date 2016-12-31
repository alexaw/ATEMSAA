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
import android.widget.TextView;

import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.adapters.ClientAdapter;
import com.example.yogis.atemsaa_fragments.net.api.Cliente.Cliente;
import com.example.yogis.atemsaa_fragments.net.api.Cliente.ClienteApi;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientBDFragment extends Fragment implements ClienteApi.OnClienteList, View.OnClickListener {

    ClienteApi clienteApi;
    MainActivity activity;
    OnChangeFragment changeFragment;

    TextView tvRtaListClient;
    RecyclerView recyclerView;
    ClientAdapter adapter;



    public ClientBDFragment() {
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
        final View vistaClient = inflater.inflate(R.layout.fragment_client_bd, container, false);


        clienteApi= new ClienteApi(getActivity(), null);
        clienteApi.getAll(this);

        recyclerView = (RecyclerView) vistaClient.findViewById(R.id.recycler_cliente);
        adapter = new ClientAdapter(getLayoutInflater(null));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return vistaClient;
    }

    @Override
    public void onClienteList(List<Cliente> data) {
        Log.i("tamanio cliente", ""+data.size());
        adapter.setData(data);
           }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

        }
    }
}
