package com.example.yogis.atemsaa_fragments.fragments;


import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.BluetoothCommandService;
import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlcMmsSettingsFragment extends Fragment {

    //se inicializan todos los objetos
    TextView tvRtaMmsSettings;
    Button btnCheckSettings, btnRecord;
    Spinner ganTransmisionMMS, ganRecepcionMMS, retTransmisionMMS, tasaTransmisionMMS, horaEncuestaMMS;
    ArrayList listaGtx, listaGrx, listaRetardoTx, listaTasaTx, listaHoraEncuesta;
    String gananciaTransmisionSpinner, gananciaRecepcionSpinner, retardoTransmisionSpinner, tasaTransmisionSpinner, horaEncuestaSpinner;
    String buff = "";
    byte[] readBuf;
    byte gantxBytes, ganrxBytes, tasatxBytes, retxBytes;



    public PlcMmsSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vista5 = inflater.inflate(R.layout.fragment_plc_mms_settings, container, false);

        //declaro todos los spinner

        //Spinner Ganancia de transmision
        ganTransmisionMMS = (Spinner) vista5.findViewById(R.id.mms_ganancia_transmision_spinner);

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
        ganTransmisionMMS.setAdapter(adaptador3);

        //Spinner Ganancia de recepcion
        ganRecepcionMMS = (Spinner) vista5.findViewById(R.id.mms_ganancia_recepcion_spinner);

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
        ganRecepcionMMS.setAdapter(adaptador4);

        //Spinner Retardo de transmision
        retTransmisionMMS = (Spinner) vista5.findViewById(R.id.mms_retardo_transmision_spinner);

        listaRetardoTx = new ArrayList<String>();
        listaRetardoTx.add("100 ms");
        listaRetardoTx.add("200 ms");
        listaRetardoTx.add("300 ms");
        listaRetardoTx.add("400 ms");
        listaRetardoTx.add("500 ms");
        ArrayAdapter<String> adaptador5 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listaRetardoTx);
        adaptador5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        retTransmisionMMS.setAdapter(adaptador5);

        //Spinner Tasa de transmision
        tasaTransmisionMMS = (Spinner) vista5.findViewById(R.id.mms_tasa_transmision_spinner);

        listaTasaTx = new ArrayList<String>();
        listaTasaTx.add("600 bps");
        listaTasaTx.add("1200 bps");
        listaTasaTx.add("2400 bps");
        ArrayAdapter<String> adaptador6 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listaTasaTx);
        adaptador6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tasaTransmisionMMS.setAdapter(adaptador6);

        //Spinner Hora de encuesta
        horaEncuestaMMS = (Spinner) vista5.findViewById(R.id.mms_hora_encuesta_spinner);

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
        horaEncuestaMMS.setAdapter(adaptador7);

        //textView donde se muestra la respuesta a la busqueda del usuario (Search User)
        tvRtaMmsSettings=(TextView)vista5.findViewById(R.id.txt_view_mms_rta_settings);

        //aqui van todos los estados de los spinner!!!

        ganTransmisionMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                gananciaTransmisionSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ganRecepcionMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                gananciaRecepcionSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        retTransmisionMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                retardoTransmisionSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        tasaTransmisionMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                tasaTransmisionSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        horaEncuestaMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                horaEncuestaSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //Boton de Verificar la configuracion del PLC-MMS

        btnCheckSettings = (Button) vista5.findViewById(R.id.btn_mms_verificar_configuracion);
        btnCheckSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                byte []frame2Send = new byte[7];

                frame2Send[0] = 0x24;// $
                frame2Send[1] = 0x40;// @
                frame2Send[2] = 0x07;// length
                frame2Send[3] = 0x07;// Tipo
                frame2Send[4] = 0x01;// Suponiendo 1 como origen PC
                frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame2Send[6] = calcularCRC(frame2Send);

                tvRtaMmsSettings.setText("");
                buff="";

                sendMessage(frame2Send);

            }
        });

//Boton de Grabar la configuracion del PLC-MMS
        btnRecord = (Button) vista5.findViewById(R.id.btn_mms_configuracion);
        btnRecord.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Capturo el contenido del editText donde van los ID
                TextView et_id = (TextView) vista5.findViewById(R.id.id_mms_user);
                String id_usuario = et_id.getText().toString();

                //Capturo el valor del spinner 'ganancia de transmision'
                String ganancia_transmision_elegida = gananciaTransmisionSpinner;

                switch (ganancia_transmision_elegida){
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
                String ganancia_recepcion_elegida = gananciaRecepcionSpinner;

                switch (ganancia_recepcion_elegida){
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
                String retardo_transmision_elegida = retardoTransmisionSpinner;

                switch (retardo_transmision_elegida){
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
                String tasa_transmision_elegida = tasaTransmisionSpinner;

                switch (tasa_transmision_elegida){
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
                int hora_encuesta_spinner_int = Integer.parseInt(horaEncuestaSpinner);
                String horaenc = Integer.toHexString(hora_encuesta_spinner_int);
                if (horaenc.length() == 1){horaenc = "0"+horaenc;}
                byte[] horaenc_bytes = hexStringToByteArray(horaenc);

                if (id_usuario.length() == 8) {

                    byte[] frame2Send = new byte[16];

                    byte[] id_usuario_bytes = hexStringToByteArray(id_usuario);

                    frame2Send[0] = 0x24;// $
                    frame2Send[1] = 0x40;// @
                    frame2Send[2] = 0x10;// length
                    frame2Send[3] = 0x11;// Tipo
                    frame2Send[4] = 0x01;// Suponiendo 1 como origen PC
                    frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                    frame2Send[6] = id_usuario_bytes[0];
                    frame2Send[7] = id_usuario_bytes[1];
                    frame2Send[8] = id_usuario_bytes[2];
                    frame2Send[9] = id_usuario_bytes[3];
                    frame2Send[10] = tasatxBytes;//tasa de transmision
                    frame2Send[11] = gantxBytes;//ganancia de transmision
                    frame2Send[12] = ganrxBytes;//ganancia de recepcion
                    frame2Send[13] = retxBytes;//retardo de transmision
                    frame2Send[14] = horaenc_bytes[0];//hora de encuesta
                    frame2Send[15] = calcularCRC(frame2Send);

                    sendMessage(frame2Send);

                } else {
                    toastIngresarId();
                }

            }
        });

        //Para limpiar la pantalla o descargar archivos
        tvRtaMmsSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setTitle(getString(R.string.txt_options))
                        .setMessage("")
                        .setNegativeButton(getString(R.string.txt_clear), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                // Limpio los textView
                                tvRtaMmsSettings.setText("");
                                buff="";
                            }
                        })
                        .setPositiveButton(getString(R.string.txt_download), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {

                                Date horaActual=new Date();

                                String fecha=(horaActual.getYear()+1900)+""+(horaActual.getMonth()+1)+
                                        ""+horaActual.getDate()+""+horaActual.getHours()+
                                        ""+horaActual.getMinutes()+""+horaActual.getSeconds();

                                writeFile("atemsaa"+fecha+".csv", buff);

                            }
                        })
                        .show();
            }
        });
        return vista5;

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
        tvRtaMmsSettings.setText(msg);
    }

}
