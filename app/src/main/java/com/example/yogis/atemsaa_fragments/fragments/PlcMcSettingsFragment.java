package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.BluetoothCommandService;
import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlcMcSettingsFragment extends Fragment implements View.OnClickListener {

    //se inicializan todos los objetos


    Animation open, close, clock, anticlock;
    boolean isOpen = false;

    TextView txtPlcMc, tvRtaPLCMC;
    Button btnCheckSettingsMC, btnRecordMC;
    Spinner txGainMC, rxGainMC, txDelayMC, txRateMC;
    ArrayList listGtx, listGrx, listDTx, listRTx, listTime;
    String transmissionGainSpinnerMC, receptionGainSpinnerMC, transmissionDelaySpinnerMC, transmissionRateSpinnerMC;
    String buff = "";

    MainActivity activity;

    byte[] readBuf;
    byte   gantxBytesMC, ganrxBytesMC, ratetxBytesMC, delaytxBytesMC;

    OnChangeFragment changeFragment;


    static String estadoUsuario = "1";


    public PlcMcSettingsFragment() {
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
        View vistaStgs = inflater.inflate(R.layout.fragment_plc_mc_settings, container, false);


        tvRtaPLCMC=(TextView)vistaStgs.findViewById(R.id.txt_view_rta_setMC);
        tvRtaPLCMC.setText("");

        tvRtaPLCMC.setText("");

        //se recuperan los botones de las interfaces de Settings
        btnCheckSettingsMC = (Button) vistaStgs.findViewById(R.id.btn_check_settings_mc);
        btnRecordMC = (Button) vistaStgs.findViewById(R.id.btn_record_mc);

        btnCheckSettingsMC.setOnClickListener(this);
        btnRecordMC.setOnClickListener(this);

        //declaro todos los spinner

        //SPINNER PARA PLC-MC

        //Spinner Ganancia de transmision
        txGainMC = (Spinner) vistaStgs.findViewById(R.id.transmission_gain_spinner_mc);

        listGtx = new ArrayList<String>();
        listGtx.add("55 mVpp");
        listGtx.add("75 mVpp");
        listGtx.add("100 mVpp");
        listGtx.add("125 mVpp");
        listGtx.add("180 mVpp");
        listGtx.add("250 mVpp");
        listGtx.add("360 mVpp");
        listGtx.add("480 mVpp");
        listGtx.add("660 mVpp");
        listGtx.add("900 mVpp");
        listGtx.add("1.25 Vpp");
        listGtx.add("1.55 Vpp");
        listGtx.add("2.25 Vpp");
        listGtx.add("3.00 Vpp");
        listGtx.add("3.50 Vpp");
        ArrayAdapter<String> adaptador6 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listGtx);
        adaptador6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txGainMC.setAdapter(adaptador6);

        //Spinner Ganancia de recepcion
        rxGainMC = (Spinner) vistaStgs.findViewById(R.id.reception_gain_spinner_mc);

        listGrx = new ArrayList<String>();
        listGrx.add("5 mVrms");
        listGrx.add("2.5 mVrms");
        listGrx.add("1.25 mVrms");
        listGrx.add("600 uVrms");
        listGrx.add("350 uVrms");
        listGrx.add("250 uVrms");
        listGrx.add("125 uVrms");
        ArrayAdapter<String> adaptador7 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listGrx);
        adaptador7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rxGainMC.setAdapter(adaptador7);

        //Spinner Retardo de transmision
        txDelayMC = (Spinner) vistaStgs.findViewById(R.id.transmission_delay_spinner_mc);

        listDTx = new ArrayList<String>();
        listDTx.add("100 ms");
        listDTx.add("200 ms");
        listDTx.add("300 ms");
        listDTx.add("400 ms");
        listDTx.add("500 ms");
        ArrayAdapter<String> adaptador8 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listDTx);
        adaptador8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txDelayMC.setAdapter(adaptador8);

        //Spinner Tasa de transmision
        txRateMC = (Spinner) vistaStgs.findViewById(R.id.transmission_rate_spinner_mc);

        listRTx = new ArrayList<String>();
        listRTx.add("600 bps");
        listRTx.add("1200 bps");
        listRTx.add("2400 bps");
        ArrayAdapter<String> adaptador9 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listRTx);
        adaptador9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txRateMC.setAdapter(adaptador9);

        //aqui van todos los estados de los spinner!!!

        txGainMC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionGainSpinnerMC = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        rxGainMC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                receptionGainSpinnerMC = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        txDelayMC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionDelaySpinnerMC = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        txRateMC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionRateSpinnerMC = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        return vistaStgs;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public void toastIngresarId() {
        Toast.makeText(this.getContext(), getString(R.string.txt_verificar_ID), Toast.LENGTH_SHORT).show();
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

    public void writeFile(String filename, String textfile) {
        try {

            File file = new File(Environment.getExternalStorageDirectory(), filename );
            OutputStreamWriter outw = new OutputStreamWriter(new FileOutputStream(file));
            outw.write(textfile);
            outw.close();
        } catch (Exception e) {}
    }

    public void setMsg(String msg){
        tvRtaPLCMC.setText(msg);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_check_settings_mc:

                byte []frame3Send = new byte[7];

                frame3Send[0] = 0x24;// $
                frame3Send[1] = 0x40;// @
                frame3Send[2] = 0x07;// length
                frame3Send[3] = 0x07;// Tipo
                frame3Send[4] = 0x01;// Suponiendo 1 como origen PC
                frame3Send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame3Send[6] = calcularCRC(frame3Send);

                tvRtaPLCMC.setText("");
                buff="";

                sendMessage(frame3Send);

                break;

            case R.id.btn_record_mc:

                //Capturo el valor del spinner 'ganancia de transmision'
                String gananciaTransmisionElegida2 = transmissionGainSpinnerMC;

                switch (gananciaTransmisionElegida2){
                    case "55 mVpp":
                        gantxBytesMC = 0x00;
                        break;

                    case "75 mVpp":
                        gantxBytesMC = 0x01;
                        break;

                    case "100 mVpp":
                        gantxBytesMC = 0x02;
                        break;

                    case "125 mVpp":
                        gantxBytesMC = 0x03;
                        break;

                    case "180 mVpp":
                        gantxBytesMC = 0x04;
                        break;

                    case "250 mVpp":
                        gantxBytesMC = 0x05;
                        break;

                    case "360 mVpp":
                        gantxBytesMC = 0x06;
                        break;

                    case "480 mVpp":
                        gantxBytesMC = 0x07;
                        break;

                    case "660 mVpp":
                        gantxBytesMC = 0x08;
                        break;

                    case "900 mVpp":
                        gantxBytesMC = 0x09;
                        break;

                    case "1.25 Vpp":
                        gantxBytesMC = 0x0A;
                        break;

                    case "1.55 Vpp":
                        gantxBytesMC = 0x0B;
                        break;

                    case "2.25 Vpp":
                        gantxBytesMC = 0x0C;
                        break;

                    case "3.00 Vpp":
                        gantxBytesMC = 0x0D;
                        break;

                    case "3.50 Vpp":
                        gantxBytesMC = 0x0E;
                        break;
                }

                //Capturo el valor del spinner 'ganancia de recepcion'
                String gananciaRecepcionElegida2 = receptionGainSpinnerMC;

                switch (gananciaRecepcionElegida2){
                    case "5 mVrms":
                        ganrxBytesMC = 0x01;
                        break;

                    case "2.5 mVrms":
                        ganrxBytesMC = 0x02;
                        break;

                    case "1.25 mVrms":
                        ganrxBytesMC = 0x03;
                        break;

                    case "600 uVrms":
                        ganrxBytesMC = 0x04;
                        break;

                    case "350 uVrms":
                        ganrxBytesMC = 0x05;
                        break;

                    case "250 uVrms":
                        ganrxBytesMC = 0x06;
                        break;

                    case "125 uVrms":
                        ganrxBytesMC = 0x07;
                        break;
                }

                //Capturo el valor del spinner 'retardo de transmision'
                String retardoTransmisionElegida2 = transmissionDelaySpinnerMC;

                switch (retardoTransmisionElegida2){
                    case "100 ms":
                        delaytxBytesMC = 0x01;
                        break;

                    case "200 ms":
                        delaytxBytesMC = 0x02;
                        break;

                    case "300 ms":
                        delaytxBytesMC = 0x03;
                        break;

                    case "400 ms":
                        delaytxBytesMC = 0x04;
                        break;

                    case "500 ms":
                        delaytxBytesMC = 0x05;
                        break;
                }

                //Capturo el valor del spinner 'tasa de transmision'
                String tasaTransmisionElegida2 = transmissionRateSpinnerMC;

                switch (tasaTransmisionElegida2){
                    case "600 bps":
                        ratetxBytesMC = 0x00;
                        break;

                    case "1200 bps":
                        ratetxBytesMC = 0x01;
                        break;

                    case "2400 bps":
                        ratetxBytesMC = 0x03;
                        break;
                }

                byte[] frame2send = new byte[11];


                frame2send[0] = 0x24;// $
                frame2send[1] = 0x40;// @
                frame2send[2] = 0x0B;// length
                frame2send[3] = 0x18;// Tipo
                frame2send[4] = 0x01;// Suponiendo 1 como origen PC
                frame2send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame2send[6] = ratetxBytesMC;//tasa de transmision
                frame2send[7] = gantxBytesMC;//ganancia de transmision
                frame2send[8] = ganrxBytesMC;//ganancia de recepcion
                frame2send[9] = delaytxBytesMC;//retardo de transmision
                frame2send[10] = calcularCRC(frame2send);

                sendMessage(frame2send);

                break;

        }

    }
}
