package com.example.yogis.atemsaa_fragments.fragments;


import android.app.Activity;
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
public class UserFragment extends Fragment implements View.OnClickListener {

    private final String LOG_TAG = "test";

    OnChangeFragment changeFragment;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        changeFragment = (OnChangeFragment) context;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_user, container, false);

        //Recupero el titulo
        TextView txt = (TextView) v.findViewById(R.id.title_user);

        //Recupero los botones
        Button menuBuscar = (Button) v.findViewById(R.id.btn_menu_buscar);
        menuBuscar.setOnClickListener(this);

        Button menuListar = (Button) v.findViewById(R.id.btn_menu_listar);
        menuListar.setOnClickListener(this);

        Button menuRegistrar = (Button) v.findViewById(R.id.btn_menu_registrar);
        menuRegistrar.setOnClickListener(this);

        Button menuTest = (Button) v.findViewById(R.id.btn_menu_prueba);
        menuTest.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_menu_buscar:
                changeFragment.onChange(OnChangeFragment.SEARCH);
                break;
            case R.id.btn_menu_listar:
                changeFragment.onChange(OnChangeFragment.LIST);
                break;
            case R.id.btn_menu_registrar:
                changeFragment.onChange(OnChangeFragment.REGISTER);
                break;
            case R.id.btn_menu_prueba:
                changeFragment.onChange(OnChangeFragment.TEST);
                break;
        }

    }
}


