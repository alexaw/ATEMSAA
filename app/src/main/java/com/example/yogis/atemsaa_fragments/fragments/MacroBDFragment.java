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
import com.example.yogis.atemsaa_fragments.net.api.Macro.Macro;
import com.example.yogis.atemsaa_fragments.net.api.Macro.MacroApi;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MacroBDFragment extends Fragment implements MacroApi.OnMacroList {

    MacroApi macroApi;
    MainActivity activity;
    OnChangeFragment changeFragment;


    public MacroBDFragment() {
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
        final View vistaMacro = inflater.inflate(R.layout.fragment_macro_bd, container, false);

        macroApi = new MacroApi(getActivity(), null);
        macroApi.getAll(this);


        return vistaMacro;
    }

    @Override
    public void onMacroList(List<Macro> data) {
        Log.i("tamanio macro", ""+data.size());

    }
}
