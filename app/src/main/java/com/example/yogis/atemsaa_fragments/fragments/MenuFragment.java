package com.example.yogis.atemsaa_fragments.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {

    Activity activty;
    private boolean bol = false;
    private final String LOG_TAG = "test";

    OnChangeFragment changeFragment;

    public MenuFragment() {}


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        changeFragment = (OnChangeFragment) context;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        //Recupero los botones
        Button menu_usuarios = (Button) v.findViewById(R.id.btn_menu_usuarios);
        menu_usuarios.setOnClickListener(this);

        Button menu_configuracion = (Button) v.findViewById(R.id.btn_menu_configuracion);
        menu_configuracion.setOnClickListener(this);

        Button menu_reportes = (Button) v.findViewById(R.id.btn_menu_reportes);
        menu_reportes.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_menu_usuarios:
                changeFragment.onChange(OnChangeFragment.USER);
                break;
            case R.id.btn_menu_configuracion:
                changeFragment.onChange(OnChangeFragment.SETTINGS);
                break;
            case R.id.btn_menu_reportes:
                changeFragment.onChange(OnChangeFragment.REPORT);
                break;
        }
    }
}
