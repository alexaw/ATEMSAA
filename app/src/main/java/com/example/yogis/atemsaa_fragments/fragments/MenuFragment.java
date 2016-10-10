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
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.BluetoothCommandService;
import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements View.OnClickListener {

    MainActivity activity;
    private boolean bol = false;
    private final String LOG_TAG = "test";

    OnChangeFragment changeFragment;

    Button menuUsuarios, menuConfiguracion, menuReportes;

    public MenuFragment() {}


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        changeFragment = (OnChangeFragment) context;
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        //Recupero los botones
        menuUsuarios = (Button) v.findViewById(R.id.btn_menu_usuarios);
        menuUsuarios.setOnClickListener(this);

        menuConfiguracion = (Button) v.findViewById(R.id.btn_menu_configuracion);
        menuConfiguracion.setOnClickListener(this);

        menuReportes = (Button) v.findViewById(R.id.btn_menu_reportes);
        menuReportes.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
//        if (MainActivity.mCommandService.getState() != BluetoothCommandService.STATE_CONNECTED) {
        if (MainActivity.mCommandService.getState() == BluetoothCommandService.STATE_CONNECTED) {

                Toast.makeText(this.getActivity(), "Por favor conectarse a un dispositivo", Toast.LENGTH_SHORT).show();


        }else {
            switch (view.getId()) {
                case R.id.btn_menu_usuarios:
                    changeFragment.onChange(OnChangeFragment.USER);

                    listUser();


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

    private void listUser() {
        byte[] frame2Send = new byte[7];

        frame2Send[0] = 0x24;// $
        frame2Send[1] = 0x40;// @
        frame2Send[2] = 0x07;// length
        frame2Send[3] = 0x06;// Tipo
        frame2Send[4] = 0x01;// Suponiendo 1 como origen PC
        frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
        //se calcula el CRC
        frame2Send[6] = calcularCRC(frame2Send);

        sendMessage(frame2Send);
    }

    private void sendMessage(byte[] message) {

        // Check that we're actually connected before trying anything
        if (MainActivity.mCommandService.getState() != BluetoothCommandService.STATE_CONNECTED) {
            Toast.makeText(this.getActivity(), getString(R.string.txt_not_connect_yet), Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length > 0) {
            activity.writeMessage(message);
        }
    }

    private byte calcularCRC(byte[] frame2send) {
        Byte CRC = (byte) (frame2send[2]);
        for (int i = 3; i <= frame2send.length - 1; i++) {
            CRC = (byte) (CRC ^ frame2send[i]);
        }
        Log.e("CRCCCCCC", CRC.toString());
        return CRC;
    }
}
