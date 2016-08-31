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
    TextView tv_rta_mms_settings;
    Button btnCheckSettings, btnRecord;
    Spinner ganancia_transmision, ganancia_recepcion, retardo_transmision, tasa_transmision, hora_encuesta;
    ArrayList lista_gtx, lista_grx, lista_retardo_tx, lista_tasa_tx, lista_hora_encuesta;
    String ganancia_transmision_spinner, ganancia_recepcion_spinner, retardo_transmision_spinner, tasa_transmision_spinner, hora_encuesta_spinner;
    String buff = "";
    byte[] readBuf;
    byte gantx_bytes, ganrx_bytes, tasatx_bytes, retx_bytes;



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
        ganancia_transmision = (Spinner) vista5.findViewById(R.id.mms_ganancia_transmision_spinner);

        lista_gtx = new ArrayList<String>();
        lista_gtx.add("55 mVpp");
        lista_gtx.add("75 mVpp");
        lista_gtx.add("100 mVpp");
        lista_gtx.add("125 mVpp");
        lista_gtx.add("180 mVpp");
        lista_gtx.add("250 mVpp");
        lista_gtx.add("360 mVpp");
        lista_gtx.add("480 mVpp");
        lista_gtx.add("660 mVpp");
        lista_gtx.add("900 mVpp");
        lista_gtx.add("1.25 Vpp");
        lista_gtx.add("1.55 Vpp");
        lista_gtx.add("2.25 Vpp");
        lista_gtx.add("3.00 Vpp");
        lista_gtx.add("3.50 Vpp");
        ArrayAdapter<String> adaptador3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, lista_gtx);
        adaptador3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ganancia_transmision.setAdapter(adaptador3);

        //Spinner Ganancia de recepcion
        ganancia_recepcion = (Spinner) vista5.findViewById(R.id.mms_ganancia_recepcion_spinner);

        lista_grx = new ArrayList<String>();
        lista_grx.add("5 mVrms");
        lista_grx.add("2.5 mVrms");
        lista_grx.add("1.25 mVrms");
        lista_grx.add("600 uVrms");
        lista_grx.add("350 uVrms");
        lista_grx.add("250 uVrms");
        lista_grx.add("125 uVrms");
        ArrayAdapter<String> adaptador4 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, lista_grx);
        adaptador4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ganancia_recepcion.setAdapter(adaptador4);

        //Spinner Retardo de transmision
        retardo_transmision = (Spinner) vista5.findViewById(R.id.mms_retardo_transmision_spinner);

        lista_retardo_tx = new ArrayList<String>();
        lista_retardo_tx.add("100 ms");
        lista_retardo_tx.add("200 ms");
        lista_retardo_tx.add("300 ms");
        lista_retardo_tx.add("400 ms");
        lista_retardo_tx.add("500 ms");
        ArrayAdapter<String> adaptador5 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, lista_retardo_tx);
        adaptador5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        retardo_transmision.setAdapter(adaptador5);

        //Spinner Tasa de transmision
        tasa_transmision = (Spinner) vista5.findViewById(R.id.mms_tasa_transmision_spinner);

        lista_tasa_tx = new ArrayList<String>();
        lista_tasa_tx.add("600 bps");
        lista_tasa_tx.add("1200 bps");
        lista_tasa_tx.add("2400 bps");
        ArrayAdapter<String> adaptador6 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, lista_tasa_tx);
        adaptador6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tasa_transmision.setAdapter(adaptador6);

        //Spinner Hora de encuesta
        hora_encuesta = (Spinner) vista5.findViewById(R.id.mms_hora_encuesta_spinner);

        lista_hora_encuesta = new ArrayList<String>();
        lista_hora_encuesta.add("00");
        lista_hora_encuesta.add("01");
        lista_hora_encuesta.add("02");
        lista_hora_encuesta.add("03");
        lista_hora_encuesta.add("04");
        lista_hora_encuesta.add("05");
        lista_hora_encuesta.add("06");
        lista_hora_encuesta.add("07");
        lista_hora_encuesta.add("08");
        lista_hora_encuesta.add("09");
        lista_hora_encuesta.add("10");
        lista_hora_encuesta.add("11");
        lista_hora_encuesta.add("12");
        lista_hora_encuesta.add("13");
        lista_hora_encuesta.add("14");
        lista_hora_encuesta.add("15");
        lista_hora_encuesta.add("16");
        lista_hora_encuesta.add("17");
        lista_hora_encuesta.add("18");
        lista_hora_encuesta.add("19");
        lista_hora_encuesta.add("20");
        lista_hora_encuesta.add("21");
        lista_hora_encuesta.add("22");
        lista_hora_encuesta.add("23");
        ArrayAdapter<String> adaptador7 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, lista_hora_encuesta);
        adaptador7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hora_encuesta.setAdapter(adaptador7);

        //textView donde se muestra la respuesta a la busqueda del usuario (Search User)
        tv_rta_mms_settings=(TextView)vista5.findViewById(R.id.txt_view_mms_rta_settings);




        //aqui van todos los estados de los spinner!!!

        ganancia_transmision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                ganancia_transmision_spinner = arg0.getItemAtPosition(arg2).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ganancia_recepcion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                ganancia_recepcion_spinner = arg0.getItemAtPosition(arg2).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        retardo_transmision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                retardo_transmision_spinner = arg0.getItemAtPosition(arg2).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        tasa_transmision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                tasa_transmision_spinner = arg0.getItemAtPosition(arg2).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        hora_encuesta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                hora_encuesta_spinner = arg0.getItemAtPosition(arg2).toString();

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

                tv_rta_mms_settings.setText("");
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
                String ganancia_transmision_elegida = ganancia_transmision_spinner;

                switch (ganancia_transmision_elegida){
                    case "55 mVpp":
                        gantx_bytes = 0x00;
                        break;

                    case "75 mVpp":
                        gantx_bytes = 0x01;
                        break;

                    case "100 mVpp":
                        gantx_bytes = 0x02;
                        break;

                    case "125 mVpp":
                        gantx_bytes = 0x03;
                        break;

                    case "180 mVpp":
                        gantx_bytes = 0x04;
                        break;

                    case "250 mVpp":
                        gantx_bytes = 0x05;
                        break;

                    case "360 mVpp":
                        gantx_bytes = 0x06;
                        break;

                    case "480 mVpp":
                        gantx_bytes = 0x07;
                        break;

                    case "660 mVpp":
                        gantx_bytes = 0x08;
                        break;

                    case "900 mVpp":
                        gantx_bytes = 0x09;
                        break;

                    case "1.25 Vpp":
                        gantx_bytes = 0x0A;
                        break;

                    case "1.55 Vpp":
                        gantx_bytes = 0x0B;
                        break;

                    case "2.25 Vpp":
                        gantx_bytes = 0x0C;
                        break;

                    case "3.00 Vpp":
                        gantx_bytes = 0x0D;
                        break;

                    case "3.50 Vpp":
                        gantx_bytes = 0x0E;
                        break;
                }


                //Capturo el valor del spinner 'ganancia de recepcion'
                String ganancia_recepcion_elegida = ganancia_recepcion_spinner;

                switch (ganancia_recepcion_elegida){
                    case "5 mVrms":
                        ganrx_bytes = 0x01;
                        break;

                    case "2.5 mVrms":
                        ganrx_bytes = 0x02;
                        break;

                    case "1.25 mVrms":
                        ganrx_bytes = 0x03;
                        break;

                    case "600 uVrms":
                        ganrx_bytes = 0x04;
                        break;

                    case "350 uVrms":
                        ganrx_bytes = 0x05;
                        break;

                    case "250 uVrms":
                        ganrx_bytes = 0x06;
                        break;

                    case "125 uVrms":
                        ganrx_bytes = 0x07;
                        break;
                }

                //Capturo el valor del spinner 'retardo de transmision'
                String retardo_transmision_elegida = retardo_transmision_spinner;

                switch (retardo_transmision_elegida){
                    case "100 ms":
                        retx_bytes = 0x01;
                        break;

                    case "200 ms":
                        retx_bytes = 0x02;
                        break;

                    case "300 ms":
                        retx_bytes = 0x03;
                        break;

                    case "400 ms":
                        retx_bytes = 0x04;
                        break;

                    case "500 ms":
                        retx_bytes = 0x05;
                        break;
                }

                //Capturo el valor del spinner 'tasa de transmision'
                String tasa_transmision_elegida = tasa_transmision_spinner;

                switch (tasa_transmision_elegida){
                    case "600 bps":
                        tasatx_bytes = 0x00;
                        break;

                    case "1200 bps":
                        tasatx_bytes = 0x01;
                        break;

                    case "2400 bps":
                        tasatx_bytes = 0x03;
                        break;
                }

                //Capturo el valor del spinner 'hora de encuesta'
                int hora_encuesta_spinner_int = Integer.parseInt(hora_encuesta_spinner);
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
                    frame2Send[10] = tasatx_bytes;//tasa de transmision
                    frame2Send[11] = gantx_bytes;//ganancia de transmision
                    frame2Send[12] = ganrx_bytes;//ganancia de recepcion
                    frame2Send[13] = retx_bytes;//retardo de transmision
                    frame2Send[14] = horaenc_bytes[0];//hora de encuesta
                    frame2Send[15] = calcularCRC(frame2Send);

                    sendMessage(frame2Send);

                } else {
                    toastIngresarId();
                }

            }
        });


        //Para limpiar la pantalla o descargar archivos
        tv_rta_mms_settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setTitle(getString(R.string.txt_options))
                        .setMessage("")
                        .setNegativeButton(getString(R.string.txt_clear), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                // Limpio los textView
                                tv_rta_mms_settings.setText("");
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

}
