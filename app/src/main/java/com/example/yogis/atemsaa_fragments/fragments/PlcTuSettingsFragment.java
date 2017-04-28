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
public class PlcTuSettingsFragment extends Fragment implements View.OnClickListener {


    //se inicializan todos los objetos



    TextView txtPlcTu, tvRtaPLCTU ;
    Button btnCheckSettingsTU, btnRecordTU ;
    Spinner txGainTU, rxGainTU, txDelayTU, txRateTU;
    ArrayList listGtx, listGrx, listDTx, listRTx, listTime;
    String transmissionGainSpinnerTU, receptionGainSpinnerTU, transmissionDelaySpinnerTU, transmissionRateSpinnerTU;
    String buff = "";

    MainActivity activity;

    byte[] readBuf;
    byte gantxBytesTU, ganrxBytesTU, ratetxBytesTU, delaytxBytesTU;

    OnChangeFragment changeFragment;

    String idUsuarioTU ;
    static String estadoUsuario = "1";


    EditText edTxtIDTU;


    public PlcTuSettingsFragment() {
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
        View vistaStgs = inflater.inflate(R.layout.fragment_plc_tu_settings, container, false);

        // Capturo el contenido del editText donde van los ID
        edTxtIDTU = (EditText) vistaStgs.findViewById(R.id.id_dispositivoTU);

        //textView donde se muestra las respuesta de las consultas
        tvRtaPLCTU=(TextView)vistaStgs.findViewById(R.id.txt_view_rta_setTU);
        tvRtaPLCTU.setText("");

        //se recuperan los botones de las interfaces de Settings
        btnCheckSettingsTU = (Button) vistaStgs.findViewById(R.id.btn_check_settings_tu);
        btnRecordTU = (Button) vistaStgs.findViewById(R.id.btn_record_tu);

        btnCheckSettingsTU.setOnClickListener(this);
        btnRecordTU.setOnClickListener(this);

        //declaro todos los spinner

        //SPINNER PARA PLC-TU

        //Spinner Ganancia de transmision
        txGainTU = (Spinner) vistaStgs.findViewById(R.id.transmission_gain_spinner_tu);

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
        ArrayAdapter<String> adaptador10 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listGtx);
        adaptador10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txGainTU.setAdapter(adaptador10);

        //Spinner Ganancia de recepcion
        rxGainTU = (Spinner) vistaStgs.findViewById(R.id.reception_gain_spinner_tu);

        listGrx = new ArrayList<String>();
        listGrx.add("5 mVrms");
        listGrx.add("2.5 mVrms");
        listGrx.add("1.25 mVrms");
        listGrx.add("600 uVrms");
        listGrx.add("350 uVrms");
        listGrx.add("250 uVrms");
        listGrx.add("125 uVrms");
        ArrayAdapter<String> adaptador11 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listGrx);
        adaptador11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rxGainTU.setAdapter(adaptador11);

        //Spinner Retardo de transmision
        txDelayTU = (Spinner) vistaStgs.findViewById(R.id.transmission_delay_spinner_tu);

        listDTx = new ArrayList<String>();
        listDTx.add("100 ms");
        listDTx.add("200 ms");
        listDTx.add("300 ms");
        listDTx.add("400 ms");
        listDTx.add("500 ms");
        ArrayAdapter<String> adaptador12 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listDTx);
        adaptador12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txDelayTU.setAdapter(adaptador12);

        //Spinner Tasa de transmision
        txRateTU = (Spinner) vistaStgs.findViewById(R.id.transmission_rate_spinner_tu);

        listRTx = new ArrayList<String>();
        listRTx.add("600 bps");
        listRTx.add("1200 bps");
        listRTx.add("2400 bps");
        ArrayAdapter<String> adaptador13 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listRTx);
        adaptador13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txRateTU.setAdapter(adaptador13);


        //aqui van todos los estados de los spinner!!!

        txGainTU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionGainSpinnerTU = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        rxGainTU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                receptionGainSpinnerTU = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        txDelayTU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionDelaySpinnerTU = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        txRateTU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionRateSpinnerTU = arg0.getItemAtPosition(arg2).toString();
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
        tvRtaPLCTU.setText(msg);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_check_settings_tu:

                byte []frame4Send = new byte[7];

                frame4Send[0] = 0x24;// $
                frame4Send[1] = 0x40;// @
                frame4Send[2] = 0x07;// length
                frame4Send[3] = 0x07;// Tipo
                frame4Send[4] = 0x01;// Suponiendo 1 como origen PC
                frame4Send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame4Send[6] = calcularCRC(frame4Send);

                tvRtaPLCTU.setText("");
                buff="";

                sendMessage(frame4Send);

                break;

            case R.id.btn_record_tu:

                //Capturo el valor del spinner 'ganancia de transmision'
                String gananciaTransmisionElegida3 = transmissionGainSpinnerTU;

                switch (gananciaTransmisionElegida3){
                    case "55 mVpp":
                        gantxBytesTU = 0x00;
                        break;

                    case "75 mVpp":
                        gantxBytesTU = 0x01;
                        break;

                    case "100 mVpp":
                        gantxBytesTU = 0x02;
                        break;

                    case "125 mVpp":
                        gantxBytesTU = 0x03;
                        break;

                    case "180 mVpp":
                        gantxBytesTU = 0x04;
                        break;

                    case "250 mVpp":
                        gantxBytesTU = 0x05;
                        break;

                    case "360 mVpp":
                        gantxBytesTU = 0x06;
                        break;

                    case "480 mVpp":
                        gantxBytesTU = 0x07;
                        break;

                    case "660 mVpp":
                        gantxBytesTU = 0x08;
                        break;

                    case "900 mVpp":
                        gantxBytesTU = 0x09;
                        break;

                    case "1.25 Vpp":
                        gantxBytesTU = 0x0A;
                        break;

                    case "1.55 Vpp":
                        gantxBytesTU = 0x0B;
                        break;

                    case "2.25 Vpp":
                        gantxBytesTU = 0x0C;
                        break;

                    case "3.00 Vpp":
                        gantxBytesTU = 0x0D;
                        break;

                    case "3.50 Vpp":
                        gantxBytesTU = 0x0E;
                        break;
                }

                //Capturo el valor del spinner 'ganancia de recepcion'
                String gananciaRecepcionElegida3 = receptionGainSpinnerTU;

                switch (gananciaRecepcionElegida3){
                    case "5 mVrms":
                        ganrxBytesTU = 0x01;
                        break;

                    case "2.5 mVrms":
                        ganrxBytesTU = 0x02;
                        break;

                    case "1.25 mVrms":
                        ganrxBytesTU = 0x03;
                        break;

                    case "600 uVrms":
                        ganrxBytesTU = 0x04;
                        break;

                    case "350 uVrms":
                        ganrxBytesTU = 0x05;
                        break;

                    case "250 uVrms":
                        ganrxBytesTU = 0x06;
                        break;

                    case "125 uVrms":
                        ganrxBytesTU = 0x07;
                        break;
                }

                //Capturo el valor del spinner 'retardo de transmision'
                String retardoTransmisionElegida3 = transmissionDelaySpinnerTU;

                switch (retardoTransmisionElegida3){
                    case "100 ms":
                        delaytxBytesTU = 0x01;
                        break;

                    case "200 ms":
                        delaytxBytesTU = 0x02;
                        break;

                    case "300 ms":
                        delaytxBytesTU = 0x03;
                        break;

                    case "400 ms":
                        delaytxBytesTU = 0x04;
                        break;

                    case "500 ms":
                        delaytxBytesTU = 0x05;
                        break;
                }

                //Capturo el valor del spinner 'tasa de transmision'
                String tasaTransmisionElegida3 = transmissionRateSpinnerTU;

                switch (tasaTransmisionElegida3){
                    case "600 bps":
                        ratetxBytesTU = 0x00;
                        break;

                    case "1200 bps":
                        ratetxBytesTU = 0x01;
                        break;

                    case "2400 bps":
                        ratetxBytesTU = 0x03;
                        break;
                }

                byte[] frame3send = new byte[11];


                frame3send[0] = 0x24;// $
                frame3send[1] = 0x40;// @
                frame3send[2] = 0x0B;// length
                frame3send[3] = 0x12;// Tipo
                frame3send[4] = 0x01;// Suponiendo 1 como origen PC
                frame3send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame3send[6] = ratetxBytesTU;
                frame3send[7] = gantxBytesTU;
                frame3send[8] = ganrxBytesTU;
                frame3send[9] = delaytxBytesTU;
                frame3send[10] = calcularCRC(frame3send);//tasa de transmision

                sendMessage(frame3send);

                break;

        }

    }


}
