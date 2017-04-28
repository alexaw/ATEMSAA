package com.example.yogis.atemsaa_fragments.fragments;


import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
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
public class PlcMmsSettingsFragment extends Fragment implements View.OnClickListener {

    //se inicializan todos los objetos

    FloatingActionButton flPlcMms, flPlcTu, flPlcMc;
    FloatingActionsMenu flMore;

    Animation open, close, clock, anticlock;
    boolean isOpen = false;

    TextView tvRtaPLCMMS, txtPlcMms;
    Button btnCheckSettingsMMS, btnRecordMMS ;
    Spinner txGainMMS, rxGainMMS, txDelayMMS, txRateMMS, timeSurveyMMS;
    ArrayList listGtx, listGrx, listDTx, listRTx, listTime;
    String transmissionGainSpinnerMMS, receptionGainSpinnerMMS, transmissionDelaySpinnerMMS, transmissionRateSpinnerMMS, timeSurveySpinnerMMS;
    String buff = "";

    MainActivity activity;

    byte[] readBuf;
    byte gantxBytesMMS, ganrxBytesMMS, ratetxBytesMMS, delaytxBytesMMS;

    OnChangeFragment changeFragment;

    String idUsuarioMMS, idMacro;
    static String estadoUsuario = "1";


    EditText edTxtIDMMS, edTxtIDMacro;
    TabHost th;



    public PlcMmsSettingsFragment() {
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
        View vistaStgs = inflater.inflate(R.layout.fragment_plc_mms_settings, container, false);

        // Capturo el contenido del editText donde van los ID
        edTxtIDMMS = (EditText) vistaStgs.findViewById(R.id.id_dispositivoMMS);
        edTxtIDMacro = (EditText) vistaStgs.findViewById(R.id.id_macro);



        //textView donde se muestra las respuesta de las consultas
        tvRtaPLCMMS=(TextView)vistaStgs.findViewById(R.id.txt_view_rta_setMMS);
        tvRtaPLCMMS.setText("");

        tvRtaPLCMMS.setText("");

        //se recuperan los botones de las interfaces de Settings
        btnCheckSettingsMMS = (Button) vistaStgs.findViewById(R.id.btn_check_settings_mms);
        btnRecordMMS = (Button) vistaStgs.findViewById(R.id.btn_record_mms);

        btnCheckSettingsMMS.setOnClickListener(this);
        btnRecordMMS.setOnClickListener(this);


        /*txtPlcMms= (TextView) vistaStgs.findViewById(R.id.txt_plc_mms);
        txtPlcTu= (TextView) vistaStgs.findViewById(R.id.txt_plc_tu);
        txtPlcMc= (TextView) vistaStgs.findViewById(R.id.txt_plc_mc);

        open = AnimationUtils.loadAnimation(vistaStgs.getContext(),R.anim.open);
        close = AnimationUtils.loadAnimation(vistaStgs.getContext(),R.anim.close);
        clock = AnimationUtils.loadAnimation(vistaStgs.getContext(),R.anim.rorate_clock);
        anticlock = AnimationUtils.loadAnimation(vistaStgs.getContext(),R.anim.rotate_anticlock);*/

        //declaro todos los spinner

        //SPINNER PARA PLC-MMS

        //Spinner Ganancia de transmision
        txGainMMS = (Spinner) vistaStgs.findViewById(R.id.transmission_gain_spinner_mms);

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
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listGtx);
        adaptador1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txGainMMS.setAdapter(adaptador1);

        //Spinner Ganancia de recepcion
        rxGainMMS = (Spinner) vistaStgs.findViewById(R.id.reception_gain_spinner_mms);

        listGrx = new ArrayList<String>();
        listGrx.add("5 mVrms");
        listGrx.add("2.5 mVrms");
        listGrx.add("1.25 mVrms");
        listGrx.add("600 uVrms");
        listGrx.add("350 uVrms");
        listGrx.add("250 uVrms");
        listGrx.add("125 uVrms");
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listGrx);
        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rxGainMMS.setAdapter(adaptador2);

        //Spinner Retardo de transmision
        txDelayMMS = (Spinner) vistaStgs.findViewById(R.id.transmission_delay_spinner_mms);

        listDTx = new ArrayList<String>();
        listDTx.add("100 ms");
        listDTx.add("200 ms");
        listDTx.add("300 ms");
        listDTx.add("400 ms");
        listDTx.add("500 ms");
        ArrayAdapter<String> adaptador3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listDTx);
        adaptador3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txDelayMMS.setAdapter(adaptador3);

        //Spinner Tasa de transmision
        txRateMMS = (Spinner) vistaStgs.findViewById(R.id.transmission_rate_spinner_mms);

        listRTx = new ArrayList<String>();
        listRTx.add("600 bps");
        listRTx.add("1200 bps");
        listRTx.add("2400 bps");
        ArrayAdapter<String> adaptador4 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listRTx);
        adaptador4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txRateMMS.setAdapter(adaptador4);

        //Spinner Hora de encuesta
        timeSurveyMMS = (Spinner) vistaStgs.findViewById(R.id.time_survey_spinner_mms);

        listTime = new ArrayList<String>();
        listTime.add("00");
        listTime.add("01");
        listTime.add("02");
        listTime.add("03");
        listTime.add("04");
        listTime.add("05");
        listTime.add("06");
        listTime.add("07");
        listTime.add("08");
        listTime.add("09");
        listTime.add("10");
        listTime.add("11");
        listTime.add("12");
        listTime.add("13");
        listTime.add("14");
        listTime.add("15");
        listTime.add("16");
        listTime.add("17");
        listTime.add("18");
        listTime.add("19");
        listTime.add("20");
        listTime.add("21");
        listTime.add("22");
        listTime.add("23");
        ArrayAdapter<String> adaptador5 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listTime);
        adaptador5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSurveyMMS.setAdapter(adaptador5);

        //aqui van todos los estados de los spinner!!!

        txGainMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionGainSpinnerMMS = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        rxGainMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                receptionGainSpinnerMMS = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        txDelayMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionDelaySpinnerMMS = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        txRateMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionRateSpinnerMMS = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        timeSurveyMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                timeSurveySpinnerMMS = arg0.getItemAtPosition(arg2).toString();
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
            MainActivity.mCommandService.write(message);
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
        tvRtaPLCMMS.setText(msg);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //caso de CHECK SETTINGS
            case R.id.btn_check_settings_mms:

                byte []frame2Send = new byte[7];

                frame2Send[0] = 0x24;// $
                frame2Send[1] = 0x40;// @
                frame2Send[2] = 0x07;// length
                frame2Send[3] = 0x07;// Tipo
                frame2Send[4] = 0x01;// Suponiendo 1 como origen PC
                frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame2Send[6] = calcularCRC(frame2Send);

                tvRtaPLCMMS.setText("");
                buff="";

                sendMessage(frame2Send);

                break;

            case R.id.btn_record_mms:
                // Capturo el contenido del editText donde van los ID
                idUsuarioMMS = edTxtIDMMS.getText().toString();
                idMacro = edTxtIDMacro.getText().toString();

                //Capturo el valor del spinner 'ganancia de transmision'
                String gananciaTransmisionElegida = transmissionGainSpinnerMMS;

                switch (gananciaTransmisionElegida){
                    case "55 mVpp":
                        gantxBytesMMS = 0x00;
                        break;

                    case "75 mVpp":
                        gantxBytesMMS = 0x01;
                        break;

                    case "100 mVpp":
                        gantxBytesMMS = 0x02;
                        break;

                    case "125 mVpp":
                        gantxBytesMMS = 0x03;
                        break;

                    case "180 mVpp":
                        gantxBytesMMS = 0x04;
                        break;

                    case "250 mVpp":
                        gantxBytesMMS = 0x05;
                        break;

                    case "360 mVpp":
                        gantxBytesMMS = 0x06;
                        break;

                    case "480 mVpp":
                        gantxBytesMMS = 0x07;
                        break;

                    case "660 mVpp":
                        gantxBytesMMS = 0x08;
                        break;

                    case "900 mVpp":
                        gantxBytesMMS = 0x09;
                        break;

                    case "1.25 Vpp":
                        gantxBytesMMS = 0x0A;
                        break;

                    case "1.55 Vpp":
                        gantxBytesMMS = 0x0B;
                        break;

                    case "2.25 Vpp":
                        gantxBytesMMS = 0x0C;
                        break;

                    case "3.00 Vpp":
                        gantxBytesMMS = 0x0D;
                        break;

                    case "3.50 Vpp":
                        gantxBytesMMS = 0x0E;
                        break;
                }

                //Capturo el valor del spinner 'ganancia de recepcion'
                String gananciaRecepcionElegida = receptionGainSpinnerMMS;

                switch (gananciaRecepcionElegida){
                    case "5 mVrms":
                        ganrxBytesMMS = 0x01;
                        break;

                    case "2.5 mVrms":
                        ganrxBytesMMS = 0x02;
                        break;

                    case "1.25 mVrms":
                        ganrxBytesMMS = 0x03;
                        break;

                    case "600 uVrms":
                        ganrxBytesMMS = 0x04;
                        break;

                    case "350 uVrms":
                        ganrxBytesMMS = 0x05;
                        break;

                    case "250 uVrms":
                        ganrxBytesMMS = 0x06;
                        break;

                    case "125 uVrms":
                        ganrxBytesMMS = 0x07;
                        break;
                }

                //Capturo el valor del spinner 'retardo de transmision'
                String retardoTransmisionElegida = transmissionDelaySpinnerMMS;

                switch (retardoTransmisionElegida){
                    case "100 ms":
                        delaytxBytesMMS = 0x01;
                        break;

                    case "200 ms":
                        delaytxBytesMMS = 0x02;
                        break;

                    case "300 ms":
                        delaytxBytesMMS = 0x03;
                        break;

                    case "400 ms":
                        delaytxBytesMMS = 0x04;
                        break;

                    case "500 ms":
                        delaytxBytesMMS = 0x05;
                        break;
                }

                //Capturo el valor del spinner 'tasa de transmision'
                String tasaTransmisionElegida = transmissionRateSpinnerMMS;

                switch (tasaTransmisionElegida){
                    case "600 bps":
                        ratetxBytesMMS = 0x00;
                        break;

                    case "1200 bps":
                        ratetxBytesMMS = 0x01;
                        break;

                    case "2400 bps":
                        ratetxBytesMMS = 0x03;
                        break;
                }


                //Capturo el valor del spinner 'hora de encuesta'
                int horaEncuestaSpinnerInt = Integer.parseInt(timeSurveySpinnerMMS);
                String horaenc = Integer.toHexString(horaEncuestaSpinnerInt);
                if (horaenc.length() == 1){horaenc = "0"+horaenc;}
                byte[] horaencBytes = hexStringToByteArray(horaenc);

                idUsuarioMMS = edTxtIDMMS.getText().toString();
                idMacro = edTxtIDMacro.getText().toString();

                if (idUsuarioMMS.length() == 8 && idMacro.length() == 12) {
                    byte[] idUsuarioBytes = hexStringToByteArray(idUsuarioMMS);
                    byte[] idMacroBytes = hexStringToByteArray(idMacro);

                    byte[] frame2send = new byte[23];


                    frame2send[0] = 0x24;// $
                    frame2send[1] = 0x40;// @
                    frame2send[2] = 0x17;// length
                    frame2send[3] = 0x11;// Tipo
                    frame2send[4] = 0x01;// Suponiendo 1 como origen PC
                    frame2send[5] = 0x02;// Suponiendo 2 como destino PLC
                    frame2send[6] = idUsuarioBytes[0];
                    frame2send[7] = idUsuarioBytes[1];
                    frame2send[8] = idUsuarioBytes[2];
                    frame2send[9] = idUsuarioBytes[3];
                    frame2send[10] = ratetxBytesMMS;//tasa de transmision
                    frame2send[11] = gantxBytesMMS;//ganancia de transmision
                    frame2send[12] = ganrxBytesMMS;//ganancia de recepcion
                    frame2send[13] = delaytxBytesMMS;//retardo de transmision
                    frame2send[14] = 0x08;//BIUThres
                    frame2send[15] = horaencBytes[0];//hora de encuesta
                    frame2send[16] = idMacroBytes[0];
                    frame2send[17] = idMacroBytes[1];
                    frame2send[18] = idMacroBytes[2];
                    frame2send[19] = idMacroBytes[3];
                    frame2send[20] = idMacroBytes[4];
                    frame2send[21] = idMacroBytes[5];
                    frame2send[22] = calcularCRC(frame2send);

                    sendMessage(frame2send);

                } else {
                    toastIngresarId();
                }

                break;



        }
    }

    public void close(){
        flMore.collapse();
    }
}
