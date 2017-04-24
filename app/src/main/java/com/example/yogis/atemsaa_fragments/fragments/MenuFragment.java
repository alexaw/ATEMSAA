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

    Button menuUsr, menuSettings, menuReports, menuMeter, menuDataBase, menuPlcTu, menuRTC;

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
        menuUsr = (Button) v.findViewById(R.id.btn_menu_usr);
        menuUsr.setOnClickListener(this);

        menuSettings = (Button) v.findViewById(R.id.btn_menu_settings);
        menuSettings.setOnClickListener(this);

        menuReports = (Button) v.findViewById(R.id.btn_menu_reports);
        menuReports.setOnClickListener(this);

        menuMeter = (Button) v.findViewById(R.id.btn_menu_meter);
        menuMeter.setOnClickListener(this);

        menuDataBase = (Button) v.findViewById(R.id.btn_menu_data_base);
        menuDataBase.setOnClickListener(this);

        menuPlcTu = (Button) v.findViewById(R.id.btn_menu_plc_tu);
        menuPlcTu.setOnClickListener(this);

        menuRTC = (Button) v.findViewById(R.id.btn_menu_rtc);
        menuRTC.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_menu_data_base) {
            changeFragment.onChange(OnChangeFragment.DATABASE);
            return;
        }

       //if (MainActivity.mCommandService.getState() == BluetoothCommandService.STATE_CONNECTED) {

          if (MainActivity.mCommandService.getState() != BluetoothCommandService.STATE_CONNECTED) {
                Toast.makeText(this.getActivity(), "Por favor conectarse a un dispositivo", Toast.LENGTH_SHORT).show();
            }else {
                switch (view.getId()) {
                    case R.id.btn_menu_usr:
                        changeFragment.onChange(OnChangeFragment.USERS);
                        listUser();
                        break;

                    case R.id.btn_menu_settings:
                        changeFragment.onChange(OnChangeFragment.SETTINGS);

                        byte []frame2Send = new byte[7];
                        frame2Send[0] = 0x24;// $
                        frame2Send[1] = 0x40;// @
                        frame2Send[2] = 0x07;// length
                        frame2Send[3] = 0x07;// Tipo
                        frame2Send[4] = 0x01;// Suponiendo 1 como origen PC
                        frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                        frame2Send[6] = calcularCRC(frame2Send);
                        sendMessage(frame2Send);


                        break;

                    case R.id.btn_menu_reports:

                        changeFragment.onChange(OnChangeFragment.REPORTS);

                        break;

                    case R.id.btn_menu_meter:

                        changeFragment.onChange(OnChangeFragment.METERS);
                        listMeter();

                        break;

                    case R.id.btn_menu_plc_tu:

                        changeFragment.onChange(OnChangeFragment.PLCTU);
                        Toast.makeText(this.getActivity(), "Por favor seleccione una opcion del menu", Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.btn_menu_rtc:

                        changeFragment.onChange(OnChangeFragment.RTC);

                        byte []frame3Send = new byte[7];

                        frame3Send[0] = 0x24;// $
                        frame3Send[1] = 0x40;// @
                        frame3Send[2] = 0x07;// length
                        frame3Send[3] = 0x0F;// Tipo
                        frame3Send[4] = 0x01;// Suponiendo 1 como origen PC
                        frame3Send[5] = 0x02;// Suponiendo 2 como destino PLC
                        frame3Send[6] = calcularCRC(frame3Send);
                        sendMessage(frame3Send);
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

    private void listMeter(){
        byte[] frame2Send = new byte[7];

        frame2Send[0] = 0x24;// $
        frame2Send[1] = 0x40;// @
        frame2Send[2] = 0x07;// length
        frame2Send[3] = 0x1A;// Tipo
        frame2Send[4] = 0x01;// Supioniendo 1 como origen PC
        frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
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
