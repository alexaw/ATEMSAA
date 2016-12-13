package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuBaseDatosFragment extends Fragment implements View.OnClickListener {

    MainActivity activity;
    private boolean bol = false;
    private final String LOG_TAG = "test";

    OnChangeFragment changeFragment;

    Button menuTrafo, menuCliente;

    public MenuBaseDatosFragment() {
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
        View v = inflater.inflate(R.layout.fragment_menu_base_datos, container, false);

        //Recupero los botones
        menuTrafo = (Button) v.findViewById(R.id.btn_menu_trafo);
        menuTrafo.setOnClickListener(this);

        menuCliente = (Button) v.findViewById(R.id.btn_menu_client);
        menuCliente.setOnClickListener(this);



        return v;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_menu_trafo:

                changeFragment.onChange(OnChangeFragment.AMARRETRAFO);

                break;

            case R.id.btn_menu_client:

                changeFragment.onChange(OnChangeFragment.CLIENTE);

                break;

        }
    }
}
