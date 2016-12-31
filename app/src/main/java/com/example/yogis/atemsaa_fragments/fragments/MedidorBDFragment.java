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
import com.example.yogis.atemsaa_fragments.net.api.Medidor.Medidor;
import com.example.yogis.atemsaa_fragments.net.api.Medidor.MedidorApi;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MedidorBDFragment extends Fragment implements MedidorApi.OnMedidorList {

    MedidorApi medidorApi;
    MainActivity activity;
    OnChangeFragment changeFragment;


    public MedidorBDFragment() {
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
        final View vistaMedidor = inflater.inflate(R.layout.fragment_medidor_bd, container, false);

        medidorApi = new MedidorApi(getActivity(),null);
        medidorApi.getAll(this);

        return vistaMedidor;
    }

    @Override
    public void onMedidorList(List<Medidor> data) {
        Log.i("tamanio medidor", ""+data.size());

    }
}
