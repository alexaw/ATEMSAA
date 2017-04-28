package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yogis.atemsaa_fragments.DataBaseActivity;
import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuBaseDatosFragment extends Fragment implements View.OnClickListener {

    DataBaseActivity activity;
    private boolean bol = false;
    private final String LOG_TAG = "test";

    OnChangeFragment changeFragment;

    Button menuPLCMMS, menuCliente, menuMACRO, menuMEDIDOR, menuPLCMC, menuPLCTU, menuPRODUCTO, menuTRAFO;

    public MenuBaseDatosFragment() {
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
        View v = inflater.inflate(R.layout.fragment_menu_base_datos, container, false);

        //Recupero los botones
        menuPLCMMS = (Button) v.findViewById(R.id.btn_menu_plcMMS_bd);
        menuPLCMMS.setOnClickListener(this);

        menuCliente = (Button) v.findViewById(R.id.btn_menu_client_bd);
        menuCliente.setOnClickListener(this);

        menuMACRO = (Button) v.findViewById(R.id.btn_menu_macro_bd);
        menuMACRO.setOnClickListener(this);

        menuMEDIDOR = (Button) v.findViewById(R.id.btn_menu_medidor_bd);
        menuMEDIDOR.setOnClickListener(this);

        menuPLCMC = (Button) v.findViewById(R.id.btn_menu_plcMC_bd);
        menuPLCMC.setOnClickListener(this);

        menuPLCTU = (Button) v.findViewById(R.id.btn_menu_plcTU_bd);
        menuPLCTU.setOnClickListener(this);

        menuPRODUCTO = (Button) v.findViewById(R.id.btn_menu_producto_bd);
        menuPRODUCTO.setOnClickListener(this);

        menuTRAFO = (Button) v.findViewById(R.id.btn_menu_trafo_bd);
        menuTRAFO.setOnClickListener(this);



        return v;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_menu_plcMMS_bd:

                changeFragment.onChange(OnChangeFragment.PLCMMSBD);

                break;

            case R.id.btn_menu_client_bd:

                changeFragment.onChange(OnChangeFragment.CLIENTEBD);

                break;

            case R.id.btn_menu_macro_bd:

                changeFragment.onChange(OnChangeFragment.MACROBD);

                break;

            case R.id.btn_menu_medidor_bd:

                changeFragment.onChange(OnChangeFragment.MEDIDORBD);

                break;

            case R.id.btn_menu_plcMC_bd:

                changeFragment.onChange(OnChangeFragment.PLCMCBD);

                break;

            case R.id.btn_menu_plcTU_bd:

                changeFragment.onChange(OnChangeFragment.PLCTUBD);

                break;

            case R.id.btn_menu_producto_bd:

                changeFragment.onChange(OnChangeFragment.PRODUCTOBD);

                break;

            case R.id.btn_menu_trafo_bd:

                changeFragment.onChange(OnChangeFragment.TRAFOBD);

                break;
        }
    }
}
