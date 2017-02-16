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
public class NewSettingsFragment extends Fragment implements View.OnClickListener {

    //se inicializan todos los objetos

    FloatingActionButton flPlcMms, flPlcTu, flPlcMc;
    FloatingActionsMenu flMore;

    Animation open, close, clock, anticlock;
    boolean isOpen = false;

    TextView tvRtaNewSettings, txtPlcMms, txtPlcTu, txtPlcMc, tvRtaClock;
    Button btnCheckSettings, btnRecord;
    Spinner ganTransmision, ganRecepcion, retTransmision, tasaTransmision, horaEncuesta;
    ArrayList listaGtx, listaGrx, listaRetardoTx, listaTasaTx, listaHoraEncuesta;
    String gananciaTransmisionSpinner, gananciaRecepcionSpinner, retardoTransmisionSpinner, tasaTransmisionSpinner, horaEncuestaSpinner;
    String buff = "";

    MainActivity activity;

    byte[] readBuf;
    byte gantxBytes, ganrxBytes, tasatxBytes, retxBytes;

    OnChangeFragment changeFragment;

    String idUsuario;
    static String estadoUsuario = "1";


    EditText edTxtID;




    public NewSettingsFragment() {
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
        View vistaStgs = inflater.inflate(R.layout.fragment_new_settings, container, false);

        // Capturo el contenido del editText donde van los ID
        edTxtID = (EditText) vistaStgs.findViewById(R.id.id_dispositivo);

        //textView donde se muestra las respuesta de las consultas
        tvRtaNewSettings=(TextView)vistaStgs.findViewById(R.id.txt_view_rta_settings);
        tvRtaNewSettings.setText("");

        tvRtaClock= (TextView) vistaStgs.findViewById(R.id.txt_view_rta_clock);
        tvRtaNewSettings.setText("");

        //se recuperan los botones de la interfaz de Settings
        btnCheckSettings = (Button) vistaStgs.findViewById(R.id.btn_verificar_configuracion);
        btnRecord = (Button) vistaStgs.findViewById(R.id.btn_configuracion);

        btnCheckSettings.setOnClickListener(this);
        btnRecord.setOnClickListener(this);


        flMore= (FloatingActionsMenu) vistaStgs.findViewById(R.id.fl_more);
        flPlcMms= (FloatingActionButton) vistaStgs.findViewById(R.id.fl_plc_mms);
        flPlcTu= (FloatingActionButton) vistaStgs.findViewById(R.id.fl_plc_tu);
        flPlcMc= (FloatingActionButton) vistaStgs.findViewById(R.id.fl_plc_mc);


        /*txtPlcMms= (TextView) vistaStgs.findViewById(R.id.txt_plc_mms);
        txtPlcTu= (TextView) vistaStgs.findViewById(R.id.txt_plc_tu);
        txtPlcMc= (TextView) vistaStgs.findViewById(R.id.txt_plc_mc);

        open = AnimationUtils.loadAnimation(vistaStgs.getContext(),R.anim.open);
        close = AnimationUtils.loadAnimation(vistaStgs.getContext(),R.anim.close);
        clock = AnimationUtils.loadAnimation(vistaStgs.getContext(),R.anim.rorate_clock);
        anticlock = AnimationUtils.loadAnimation(vistaStgs.getContext(),R.anim.rotate_anticlock);*/


        flPlcMms.setOnClickListener(this);
        flPlcTu.setOnClickListener(this);
        flPlcMc.setOnClickListener(this);



        //declaro todos los spinner

        //Spinner Ganancia de transmision
        ganTransmision = (Spinner) vistaStgs.findViewById(R.id.ganancia_transmision_spinner);

        listaGtx = new ArrayList<String>();
        listaGtx.add("55 mVpp");
        listaGtx.add("75 mVpp");
        listaGtx.add("100 mVpp");
        listaGtx.add("125 mVpp");
        listaGtx.add("180 mVpp");
        listaGtx.add("250 mVpp");
        listaGtx.add("360 mVpp");
        listaGtx.add("480 mVpp");
        listaGtx.add("660 mVpp");
        listaGtx.add("900 mVpp");
        listaGtx.add("1.25 Vpp");
        listaGtx.add("1.55 Vpp");
        listaGtx.add("2.25 Vpp");
        listaGtx.add("3.00 Vpp");
        listaGtx.add("3.50 Vpp");
        ArrayAdapter<String> adaptador3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listaGtx);
        adaptador3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ganTransmision.setAdapter(adaptador3);

        //Spinner Ganancia de recepcion
        ganRecepcion = (Spinner) vistaStgs.findViewById(R.id.ganancia_recepcion_spinner);

        listaGrx = new ArrayList<String>();
        listaGrx.add("5 mVrms");
        listaGrx.add("2.5 mVrms");
        listaGrx.add("1.25 mVrms");
        listaGrx.add("600 uVrms");
        listaGrx.add("350 uVrms");
        listaGrx.add("250 uVrms");
        listaGrx.add("125 uVrms");
        ArrayAdapter<String> adaptador4 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listaGrx);
        adaptador4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ganRecepcion.setAdapter(adaptador4);

        //Spinner Retardo de transmision
        retTransmision = (Spinner) vistaStgs.findViewById(R.id.retardo_transmision_spinner);

        listaRetardoTx = new ArrayList<String>();
        listaRetardoTx.add("100 ms");
        listaRetardoTx.add("200 ms");
        listaRetardoTx.add("300 ms");
        listaRetardoTx.add("400 ms");
        listaRetardoTx.add("500 ms");
        ArrayAdapter<String> adaptador5 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listaRetardoTx);
        adaptador5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        retTransmision.setAdapter(adaptador5);

        //Spinner Tasa de transmision
        tasaTransmision = (Spinner) vistaStgs.findViewById(R.id.tasa_transmision_spinner);

        listaTasaTx = new ArrayList<String>();
        listaTasaTx.add("600 bps");
        listaTasaTx.add("1200 bps");
        listaTasaTx.add("2400 bps");
        ArrayAdapter<String> adaptador6 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listaTasaTx);
        adaptador6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tasaTransmision.setAdapter(adaptador6);

        //Spinner Hora de encuesta
        horaEncuesta = (Spinner) vistaStgs.findViewById(R.id.hora_encuesta_spinner);

        listaHoraEncuesta = new ArrayList<String>();
        listaHoraEncuesta.add("00");
        listaHoraEncuesta.add("01");
        listaHoraEncuesta.add("02");
        listaHoraEncuesta.add("03");
        listaHoraEncuesta.add("04");
        listaHoraEncuesta.add("05");
        listaHoraEncuesta.add("06");
        listaHoraEncuesta.add("07");
        listaHoraEncuesta.add("08");
        listaHoraEncuesta.add("09");
        listaHoraEncuesta.add("10");
        listaHoraEncuesta.add("11");
        listaHoraEncuesta.add("12");
        listaHoraEncuesta.add("13");
        listaHoraEncuesta.add("14");
        listaHoraEncuesta.add("15");
        listaHoraEncuesta.add("16");
        listaHoraEncuesta.add("17");
        listaHoraEncuesta.add("18");
        listaHoraEncuesta.add("19");
        listaHoraEncuesta.add("20");
        listaHoraEncuesta.add("21");
        listaHoraEncuesta.add("22");
        listaHoraEncuesta.add("23");
        ArrayAdapter<String> adaptador7 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listaHoraEncuesta);
        adaptador7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        horaEncuesta.setAdapter(adaptador7);

        //aqui van todos los estados de los spinner!!!

        ganTransmision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                gananciaTransmisionSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ganRecepcion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                gananciaRecepcionSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        retTransmision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                retardoTransmisionSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        tasaTransmision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                tasaTransmisionSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        horaEncuesta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                horaEncuestaSpinner = arg0.getItemAtPosition(arg2).toString();
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
        tvRtaNewSettings.setText(msg);
    }


    @Override
    public void onClick(View view) {

    /*    if (isOpen){

            flPlcMms.startAnimation(close);
            txtPlcMms.startAnimation(close);

            flPlcTu.startAnimation(close);
            txtPlcTu.startAnimation(close);

            flPlcMc.startAnimation(close);
            txtPlcMc.startAnimation(close);

            flMore.startAnimation(anticlock);

            flPlcMms.setClickable(false);
            txtPlcMms.setClickable(false);

            flPlcTu.setClickable(false);
            txtPlcTu.setClickable(false);

            flPlcMc.setClickable(false);
            txtPlcMc.setClickable(false);

            isOpen = false;
        }
        else {

            flPlcMms.startAnimation(open);
            txtPlcMms.startAnimation(open);

            flPlcTu.startAnimation(open);
            txtPlcTu.startAnimation(open);

            flPlcMc.startAnimation(open);
            txtPlcMc.startAnimation(open);

            flMore.startAnimation(clock);

            flPlcMms.setClickable(true);
            txtPlcMms.setClickable(false);

            flPlcTu.setClickable(true);
            txtPlcTu.setClickable(false);

            flPlcMc.setClickable(true);
            txtPlcMc.setClickable(false);

            isOpen = true;
        }
        */

        switch (view.getId()) {

            //caso de CHECK SETTINGS
            case R.id.btn_verificar_configuracion:

                byte []frame2Send = new byte[7];

                frame2Send[0] = 0x24;// $
                frame2Send[1] = 0x40;// @
                frame2Send[2] = 0x07;// length
                frame2Send[3] = 0x07;// Tipo
                frame2Send[4] = 0x01;// Suponiendo 1 como origen PC
                frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame2Send[6] = calcularCRC(frame2Send);

                tvRtaNewSettings.setText("");
                buff="";

                sendMessage(frame2Send);

                break;

            case R.id.btn_configuracion:
                // Capturo el contenido del editText donde van los ID
                idUsuario = edTxtID.getText().toString();

                //Capturo el valor del spinner 'ganancia de transmision'
                String gananciaTransmisionElegida = gananciaTransmisionSpinner;

                switch (gananciaTransmisionElegida){
                    case "55 mVpp":
                        gantxBytes = 0x00;
                        break;

                    case "75 mVpp":
                        gantxBytes = 0x01;
                        break;

                    case "100 mVpp":
                        gantxBytes = 0x02;
                        break;

                    case "125 mVpp":
                        gantxBytes = 0x03;
                        break;

                    case "180 mVpp":
                        gantxBytes = 0x04;
                        break;

                    case "250 mVpp":
                        gantxBytes = 0x05;
                        break;

                    case "360 mVpp":
                        gantxBytes = 0x06;
                        break;

                    case "480 mVpp":
                        gantxBytes = 0x07;
                        break;

                    case "660 mVpp":
                        gantxBytes = 0x08;
                        break;

                    case "900 mVpp":
                        gantxBytes = 0x09;
                        break;

                    case "1.25 Vpp":
                        gantxBytes = 0x0A;
                        break;

                    case "1.55 Vpp":
                        gantxBytes = 0x0B;
                        break;

                    case "2.25 Vpp":
                        gantxBytes = 0x0C;
                        break;

                    case "3.00 Vpp":
                        gantxBytes = 0x0D;
                        break;

                    case "3.50 Vpp":
                        gantxBytes = 0x0E;
                        break;
                }

                //Capturo el valor del spinner 'ganancia de recepcion'
                String gananciaRecepcionElegida = gananciaRecepcionSpinner;

                switch (gananciaRecepcionElegida){
                    case "5 mVrms":
                        ganrxBytes = 0x01;
                        break;

                    case "2.5 mVrms":
                        ganrxBytes = 0x02;
                        break;

                    case "1.25 mVrms":
                        ganrxBytes = 0x03;
                        break;

                    case "600 uVrms":
                        ganrxBytes = 0x04;
                        break;

                    case "350 uVrms":
                        ganrxBytes = 0x05;
                        break;

                    case "250 uVrms":
                        ganrxBytes = 0x06;
                        break;

                    case "125 uVrms":
                        ganrxBytes = 0x07;
                        break;
                }

                //Capturo el valor del spinner 'retardo de transmision'
                String retardoTransmisionElegida = retardoTransmisionSpinner;

                switch (retardoTransmisionElegida){
                    case "100 ms":
                        retxBytes = 0x01;
                        break;

                    case "200 ms":
                        retxBytes = 0x02;
                        break;

                    case "300 ms":
                        retxBytes = 0x03;
                        break;

                    case "400 ms":
                        retxBytes = 0x04;
                        break;

                    case "500 ms":
                        retxBytes = 0x05;
                        break;
                }

                //Capturo el valor del spinner 'tasa de transmision'
                String tasaTransmisionElegida = tasaTransmisionSpinner;

                switch (tasaTransmisionElegida){
                    case "600 bps":
                        tasatxBytes = 0x00;
                        break;

                    case "1200 bps":
                        tasatxBytes = 0x01;
                        break;

                    case "2400 bps":
                        tasatxBytes = 0x03;
                        break;
                }

                //Capturo el valor del spinner 'hora de encuesta'
                int horaEncuestaSpinnerInt = Integer.parseInt(horaEncuestaSpinner);
                String horaenc = Integer.toHexString(horaEncuestaSpinnerInt);
                if (horaenc.length() == 1){horaenc = "0"+horaenc;}
                byte[] horaencBytes = hexStringToByteArray(horaenc);

                if (idUsuario.length() == 8) {
                    byte[] idUsuarioBytes = hexStringToByteArray(idUsuario);

                    byte[] frame2send = new byte[16];


                    frame2send[0] = 0x24;// $
                    frame2send[1] = 0x40;// @
                    frame2send[2] = 0x10;// length
                    frame2send[3] = 0x11;// Tipo
                    frame2send[4] = 0x01;// Suponiendo 1 como origen PC
                    frame2send[5] = 0x02;// Suponiendo 2 como destino PLC
                    frame2send[6] = idUsuarioBytes[0];
                    frame2send[7] = idUsuarioBytes[1];
                    frame2send[8] = idUsuarioBytes[2];
                    frame2send[9] = idUsuarioBytes[3];
                    frame2send[10] = tasatxBytes;//tasa de transmision
                    frame2send[11] = gantxBytes;//ganancia de transmision
                    frame2send[12] = ganrxBytes;//ganancia de recepcion
                    frame2send[13] = retxBytes;//retardo de transmision
                    frame2send[14] = horaencBytes[0];//hora de encuesta
                    frame2send[15] = calcularCRC(frame2send);

                    sendMessage(frame2send);

                } else {
                    toastIngresarId();
                }

                break;

            case R.id.fl_plc_mms:
                flMore.collapse();

                frame2Send = new byte[7];

                frame2Send[0] = 0x24;
                frame2Send[1] = 0x40;
                frame2Send[2] = 0x07;
                frame2Send[3] = 0x02;
                frame2Send[4] = 0x01;
                frame2Send[5] = 0x02;
                frame2Send[6] = calcularCRC(frame2Send);

                tvRtaNewSettings.setText("");
                buff="";

                sendMessage(frame2Send);

                break;
            case R.id.fl_plc_tu:
                flMore.collapse();
                frame2Send = new byte[7];

                frame2Send[0] = 0x24;
                frame2Send[1] = 0x40;
                frame2Send[2] = 0x07;
                frame2Send[3] = 0x13;
                frame2Send[4] = 0x01;
                frame2Send[5] = 0x02;
                frame2Send[6] = calcularCRC(frame2Send);

                tvRtaNewSettings.setText("");
                buff="";

                sendMessage(frame2Send);
                break;
            case R.id.fl_plc_mc:
                flMore.collapse();

                Date horaActual=new Date();

                String fecha=(horaActual.getYear()+1900)+""+(horaActual.getMonth()+1)+""+horaActual.getDate()+""+horaActual.getHours()+""+horaActual.getMinutes()+""+horaActual.getSeconds();

                writeFile("atemsaa"+fecha+".csv",buff);
                tvRtaNewSettings.setText("");

                buff="";

                break;

        }
    }










}
