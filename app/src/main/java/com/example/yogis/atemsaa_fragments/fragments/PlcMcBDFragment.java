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

import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.adapters.PlcMcAdapter;
import com.example.yogis.atemsaa_fragments.net.api.PlcMc.PlcMc;
import com.example.yogis.atemsaa_fragments.net.api.PlcMc.PlcMcApi;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlcMcBDFragment extends Fragment implements PlcMcApi.OnPlcMcList {

    PlcMcApi plcMcApi;
    MainActivity activity;
    OnChangeFragment changeFragment;

    RecyclerView recyclerView;
    PlcMcAdapter adapter;


    public PlcMcBDFragment() {
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
        final View vistaPlcMc = inflater.inflate(R.layout.fragment_plc_mc_bd, container, false);

        plcMcApi = new PlcMcApi(getActivity(), null);
        plcMcApi.getAll(this);

        recyclerView = (RecyclerView) vistaPlcMc.findViewById(R.id.recycler_plcmc);
        adapter = new PlcMcAdapter(getLayoutInflater(null));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return vistaPlcMc;
    }

    @Override
    public void onPlcMcList(List<PlcMc> data) {
        Log.i("tamanio PLC-MC", ""+data.size());
        adapter.setData(data);

    }
}
