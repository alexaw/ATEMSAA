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
import com.example.yogis.atemsaa_fragments.adapters.PlcMmsAdapter;
import com.example.yogis.atemsaa_fragments.net.api.Cliente.ClienteApi;
import com.example.yogis.atemsaa_fragments.net.api.PlcMms.PlcMms;
import com.example.yogis.atemsaa_fragments.net.api.PlcMms.PlcMmsApi;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmarreTrafoFragment extends Fragment implements PlcMmsApi.OnPlcMmsList, View.OnClickListener {

    PlcMmsApi plcMmsApi;
    MainActivity activity;
    OnChangeFragment changeFragment;

    Button btnListPlcMms;
    TextView tvRtaListPlcMms;

    RecyclerView recyclerView;
    PlcMmsAdapter adapter;


    public AmarreTrafoFragment() {
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
        final View vista6 = inflater.inflate(R.layout.fragment_amarre_trafo, container, false);

        btnListPlcMms = (Button) vista6.findViewById(R.id.btn_consulta);
        btnListPlcMms.setOnClickListener(this);

        plcMmsApi= new PlcMmsApi(getActivity(), null);
        plcMmsApi.getAll(this);

        recyclerView = (RecyclerView) vista6.findViewById(R.id.recycler_plcmms);
        adapter = new PlcMmsAdapter(getLayoutInflater(null));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return vista6;    }

    @Override
    public void onPlcMmsList(List<PlcMms> data) {
        Log.i("tamanio amarre trafo", ""+data.size());
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
