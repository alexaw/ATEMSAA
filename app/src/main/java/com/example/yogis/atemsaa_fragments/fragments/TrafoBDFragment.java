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

import com.example.yogis.atemsaa_fragments.DataBaseActivity;
import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.adapters.TrafoAdapter;
import com.example.yogis.atemsaa_fragments.net.api.Trafo.Trafo;
import com.example.yogis.atemsaa_fragments.net.api.Trafo.TrafoApi;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrafoBDFragment extends Fragment implements TrafoApi.OnTrafoList {

    TrafoApi trafoApi;
    DataBaseActivity activity;
    OnChangeFragment changeFragment;

    RecyclerView recyclerView;
    TrafoAdapter adapter;


    public TrafoBDFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        changeFragment = (OnChangeFragment) context;
        activity = (DataBaseActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vistaTrafo = inflater.inflate(R.layout.fragment_trafo_bd, container, false);

        trafoApi = new TrafoApi(getActivity(), null);
        trafoApi.getAll(this);

        recyclerView = (RecyclerView) vistaTrafo.findViewById(R.id.recycler_trafo);
        adapter = new TrafoAdapter(getLayoutInflater(null));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return vistaTrafo;
    }

    @Override
    public void onTrafoList(List<Trafo> data) {
        Log.i("tamanio Trafo", ""+data.size());
        adapter.setData(data);

    }
}
