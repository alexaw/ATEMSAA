package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.api.Cliente.Cliente;
import com.example.yogis.atemsaa_fragments.net.api.Cliente.ClienteApi;

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



        return vista6;
    }

    @Override
    public void onClienteList(List<Cliente> data) {
        Log.i("tamanio cliente", ""+data.size());


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_consulta:

                tvRtaListClient.setText("");
                break;
        }
    }
}
