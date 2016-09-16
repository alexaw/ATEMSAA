package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yogis.atemsaa_fragments.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    OnChangeFragment changeFragment;

    Button menuPlcMMS, menuPlcTU, menuPlcMC, menuReloj, menuRF, menuGPRS;


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        changeFragment = (OnChangeFragment) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        //Recupero el titulo
        TextView txt = (TextView) v.findViewById(R.id.title_settings);

        //Recupero los botones
        menuPlcMMS = (Button) v.findViewById(R.id.btn_menu_PLC_MMS);
        menuPlcMMS.setOnClickListener(this);

        menuPlcTU  = (Button) v.findViewById(R.id.btn_menu_PLC_TU);
        menuPlcTU.setOnClickListener(this);

        menuPlcMC= (Button) v.findViewById(R.id.btn_menu_PLC_MC);
        menuPlcMC.setOnClickListener(this);

        menuReloj  = (Button) v.findViewById(R.id.btn_menu_reloj);
        menuReloj.setOnClickListener(this);

        menuRF = (Button) v.findViewById(R.id.btn_menu_RF);
        menuRF.setOnClickListener(this);

        menuGPRS = (Button) v.findViewById(R.id.btn_menu_GPRS);
        menuGPRS.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_menu_PLC_MMS:
                changeFragment.onChange(OnChangeFragment.PLCMMS);
                break;
            case R.id.btn_menu_PLC_TU:
                changeFragment.onChange(OnChangeFragment.PLCTU);
                break;
            case R.id.btn_menu_PLC_MC:
                changeFragment.onChange(OnChangeFragment.PLCMMS);
                break;
            case R.id.btn_menu_reloj:
                changeFragment.onChange(OnChangeFragment.PLCMMS);
                break;
            case R.id.btn_menu_RF:
                changeFragment.onChange(OnChangeFragment.HOLA);
                break;
            case R.id.btn_menu_GPRS:
                changeFragment.onChange(OnChangeFragment.HOLA);
                break;
        }
    }
}
