package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.api.PlcTu.PlcTu;
import com.example.yogis.atemsaa_fragments.net.api.PlcTu.PlcTuApi;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlcTuBDFragment extends Fragment implements PlcTuApi.OnPlcTuList {

    PlcTuApi plcTuApi;
    MainActivity activity;
    OnChangeFragment changeFragment;


    public PlcTuBDFragment() {
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
        final View vistaPlcTu = inflater.inflate(R.layout.fragment_plc_tu_bd, container, false);

        plcTuApi = new PlcTuApi(getActivity(), null);
        plcTuApi.getAll(this);

        return vistaPlcTu;
    }

    @Override
    public void onPlcTuList(List<PlcTu> data) {
        Log.i("tamanio PLC-TU", ""+data.size());

    }
}
