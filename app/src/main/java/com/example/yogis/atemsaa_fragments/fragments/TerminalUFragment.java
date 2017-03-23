package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.BluetoothCommandService;
import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TerminalUFragment extends Fragment implements View.OnClickListener {

    FloatingActionsMenu flMore;
    FloatingActionButton flEnergyRead, flReleOn,flReleOff;
    TextView tvRtaPLCTU;
    String buff = "";
    String idPLCTU;
    static String estadoUsuario = "1";


    OnChangeFragment changeFragment;

    EditText edTxtID;

    MainActivity activity;


    public TerminalUFragment() {
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
        View vistaPLCTU = inflater.inflate(R.layout.fragment_terminal_u, container, false);

        tvRtaPLCTU=(TextView)vistaPLCTU.findViewById(R.id.txt_view_rta_plc_tu);
        tvRtaPLCTU.setText("");

        flMore = (FloatingActionsMenu) vistaPLCTU.findViewById(R.id.fl_more);

        flEnergyRead = (FloatingActionButton) vistaPLCTU.findViewById(R.id.fl_energy_reading);
        flReleOn = (FloatingActionButton) vistaPLCTU.findViewById(R.id.fl_rele_on);
        flReleOff = (FloatingActionButton) vistaPLCTU.findViewById(R.id.fl_rele_off);

        flEnergyRead.setOnClickListener(this);
        flReleOn.setOnClickListener(this);
        flReleOff.setOnClickListener(this);

        // Capturo el contenido del editText donde van los ID
        edTxtID = (EditText) vistaPLCTU.findViewById(R.id.id_plc_tu);

        return vistaPLCTU;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.fl_energy_reading:
                flMore.collapse();

                idPLCTU = edTxtID.getText().toString();

                if (idPLCTU.length() == 12) {

                    byte[] frame2Send = new byte[13];
                    byte[] array_id = hexStringToByteArray(idPLCTU);


                    frame2Send[0] = 0x24;// $
                    frame2Send[1] = 0x40;// @
                    frame2Send[2] = 0x0D;// length
                    frame2Send[3] = 0x1D;// Tipo
                    frame2Send[4] = 0x01;// Supioniendo 1 como origen PC
                    frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                    frame2Send[6] = array_id[0];
                    frame2Send[7] = array_id[1];
                    frame2Send[8] = array_id[2];
                    frame2Send[9] = array_id[3];
                    frame2Send[10] = array_id[4];
                    frame2Send[11] = array_id[5];
                    frame2Send[12] = calcularCRC(frame2Send);

                    tvRtaPLCTU.setText("");
                    buff="";

                    sendMessage(frame2Send);

                } else {
                    toastIngresarId();
                }

                break;

            case R.id.fl_rele_on:
                flMore.collapse();

                idPLCTU = edTxtID.getText().toString();

                if (idPLCTU.length() == 12) {

                    byte[] frame2Send = new byte[14];
                    byte[] array_id = hexStringToByteArray(idPLCTU);


                    frame2Send[0] = 0x24;// $
                    frame2Send[1] = 0x40;// @
                    frame2Send[2] = 0x0E;// length
                    frame2Send[3] = 0x1C;// Tipo
                    frame2Send[4] = 0x01;// Supioniendo 1 como origen PC
                    frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                    frame2Send[6] = array_id[0];
                    frame2Send[7] = array_id[1];
                    frame2Send[8] = array_id[2];
                    frame2Send[9] = array_id[3];
                    frame2Send[10] = array_id[4];
                    frame2Send[11] = array_id[5];
                    frame2Send[12] = 1;
                    frame2Send[13] = calcularCRC(frame2Send);


                    tvRtaPLCTU.setText("");
                    buff="";

                    sendMessage(frame2Send);

                } else {
                    toastIngresarId();
                }

                break;

            case R.id.fl_rele_off:
                flMore.collapse();

                idPLCTU = edTxtID.getText().toString();

                if (idPLCTU.length() == 12) {

                    byte[] frame2Send = new byte[14];
                    byte[] array_id = hexStringToByteArray(idPLCTU);


                    frame2Send[0] = 0x24;// $
                    frame2Send[1] = 0x40;// @
                    frame2Send[2] = 0x0E;// length
                    frame2Send[3] = 0x1C;// Tipo
                    frame2Send[4] = 0x01;// Supioniendo 1 como origen PC
                    frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                    frame2Send[6] = array_id[0];
                    frame2Send[7] = array_id[1];
                    frame2Send[8] = array_id[2];
                    frame2Send[9] = array_id[3];
                    frame2Send[10] = array_id[4];
                    frame2Send[11] = array_id[5];
                    frame2Send[12] = 2;
                    frame2Send[13] = calcularCRC(frame2Send);


                    tvRtaPLCTU.setText("");
                    buff="";

                    sendMessage(frame2Send);

                } else {
                    toastIngresarId();
                }

                break;
        }
    }

    //Converting a string of hex character to bytes
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2){
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private byte calcularCRC(byte[] frame2send) {
        Byte CRC = (byte) (frame2send[2]);
        for (int i = 3; i <= frame2send.length - 1; i++) {
            CRC = (byte) (CRC ^ frame2send[i]);
        }
        Log.e("CRCCCCCC", CRC.toString());
        return CRC;
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

    public void setMsg(String msg){
        tvRtaPLCTU.setText(msg);
    }

    public void toastIngresarId() {
        Toast.makeText(this.getContext(), getString(R.string.txt_verificar_ID), Toast.LENGTH_SHORT).show();

    }
}
