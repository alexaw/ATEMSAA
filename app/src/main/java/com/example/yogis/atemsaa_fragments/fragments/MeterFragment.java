package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
public class MeterFragment extends Fragment implements View.OnClickListener {

    FloatingActionsMenu flMore;
    FloatingActionButton flNewMeter, flEnergyRead, flOpc;
    TextView tvRtaMeters;
    String buff = "";
    String idMeter, sPosMed;
    static String estadoUsuario = "1";

    Spinner posMed;
    ArrayList listPosMed;

    OnChangeFragment changeFragment;

    EditText edTxtID;

    MainActivity activity;


    public MeterFragment() {
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
        View vistaMeter = inflater.inflate(R.layout.fragment_meter, container, false);

        tvRtaMeters=(TextView)vistaMeter.findViewById(R.id.txt_view_rta_meter);
        tvRtaMeters.setText("");

        posMed = (Spinner) vistaMeter.findViewById(R.id.pos_med);

        spinnerPosMed();


        flMore = (FloatingActionsMenu) vistaMeter.findViewById(R.id.fl_more);

        flNewMeter = (FloatingActionButton) vistaMeter.findViewById(R.id.fl_new_meter);
        flEnergyRead = (FloatingActionButton) vistaMeter.findViewById(R.id.fl_energy_reading);
        flOpc = (FloatingActionButton) vistaMeter.findViewById(R.id.fl_opc);

        flNewMeter.setOnClickListener(this);
        flEnergyRead.setOnClickListener(this);
        flOpc.setOnClickListener(this);


        // Capturo el contenido del editText donde van los ID
        edTxtID = (EditText) vistaMeter.findViewById(R.id.id_meter);

        return vistaMeter;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.fl_new_meter:
                flMore.collapse();

                idMeter = edTxtID.getText().toString();
                byte[] posMedByte = hexStringToByteArray(sPosMed);

                if (idMeter.length() == 14) {

                byte[] frame2Send = new byte[14];
                byte[] array_id = hexStringToByteArray(idMeter);


                frame2Send[0] = 0x24;// $
                frame2Send[1] = 0x40;// @
                frame2Send[2] = 0x0E;// length
                frame2Send[3] = 0x19;// Tipo
                frame2Send[4] = 0x01;// Supioniendo 1 como origen PC
                frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame2Send[6] = posMedByte[0];
                frame2Send[7] = array_id[0];
                frame2Send[8] = array_id[1];
                frame2Send[9] = array_id[2];
                frame2Send[10] = array_id[3];
                frame2Send[11] = array_id[4];
                frame2Send[12] = array_id[5];
                frame2Send[13] = calcularCRC(frame2Send);

                    tvRtaMeters.setText("");
                    buff="";

                sendMessage(frame2Send);

                } else {
                    toastIngresarId();
                }

                break;
            case R.id.fl_energy_reading:
                flMore.collapse();

                idMeter = edTxtID.getText().toString();

                if (idMeter.length() == 12) {

                byte[] frame2Send = new byte[13];
                byte[] array_id = hexStringToByteArray(idMeter);


                frame2Send[0] = 0x24;// $
                frame2Send[1] = 0x40;// @
                frame2Send[2] = 0x0D;// length
                frame2Send[3] = 0x1E;// Tipo
                frame2Send[4] = 0x01;// Supioniendo 1 como origen PC
                frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame2Send[6] = array_id[0];
                frame2Send[7] = array_id[1];
                frame2Send[8] = array_id[2];
                frame2Send[9] = array_id[3];
                frame2Send[10] = array_id[4];
                frame2Send[11] = array_id[5];
                frame2Send[12] = calcularCRC(frame2Send);

                    tvRtaMeters.setText("");
                    buff="";

                sendMessage(frame2Send);

                } else {
                    toastIngresarId();
                }

                break;
            case R.id.fl_opc:
                flMore.collapse();

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
        tvRtaMeters.setText(msg);
    }

    public void toastIngresarId() {
        Toast.makeText(this.getContext(), getString(R.string.txt_verificar_ID), Toast.LENGTH_SHORT).show();

    }

    public void spinnerPosMed(){
        listPosMed = new ArrayList <String> ();
        listPosMed.add("1");
        listPosMed.add("2");
        listPosMed.add("3");
        listPosMed.add("4");
        listPosMed.add("5");
        listPosMed.add("6");

        ArrayAdapter<String> adaptador3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listPosMed);
        adaptador3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        posMed.setAdapter(adaptador3);

        posMed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                sPosMed = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
}
