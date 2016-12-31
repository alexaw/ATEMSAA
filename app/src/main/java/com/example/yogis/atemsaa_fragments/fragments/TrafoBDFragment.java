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
import com.example.yogis.atemsaa_fragments.net.api.Trafo.Trafo;
import com.example.yogis.atemsaa_fragments.net.api.Trafo.TrafoApi;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrafoBDFragment extends Fragment implements TrafoApi.OnTrafoList {

    TrafoApi trafoApi;
    MainActivity activity;
    OnChangeFragment changeFragment;


    public TrafoBDFragment() {
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
        final View vistaTrafo = inflater.inflate(R.layout.fragment_trafo_bd, container, false);

        trafoApi = new TrafoApi(getActivity(), null);
        trafoApi.getAll(this);

        return vistaTrafo;
    }

    @Override
    public void onTrafoList(List<Trafo> data) {
        Log.i("tamanio Trafo", ""+data.size());

    }
}
