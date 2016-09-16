package com.example.yogis.atemsaa_fragments.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yogis.atemsaa_fragments.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlcMcSettingsFragment extends Fragment {

    //se inicializan todos los objetos
    TextView tv_rta_mC_settings;
    Button btnCheckSettings, btnRecord;
    Spinner ganTransmisionMC, ganRecepcionMC, retTransmisionMC, tasaTransmisionMC, horaEncuestaMC;
    ArrayList lista_gtx, lista_grx, lista_retardo_tx, lista_tasa_tx, lista_hora_encuesta;
    String ganancia_transmision_spinner, ganancia_recepcion_spinner, retardo_transmision_spinner, tasa_transmision_spinner, hora_encuesta_spinner;
    String buff = "";
    byte[] readBuf;
    byte gantx_bytes, ganrx_bytes, tasatx_bytes, retx_bytes;


    public PlcMcSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plc_mc_settings, container, false);
    }

}
