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
public class ListUserFragment extends Fragment {

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for Bluetooth Command Service
    private BluetoothCommandService mCommandService = null;

    //se inicializan todos los objetos
    Button btnListUser;
    TextView tvRtaListUser;
    Spinner listEstadoUsuario;
    ArrayList listaEstado, lista_accion;
    String estadoUsuarioSpinner;
    String buff = "";
    byte[] readBuf;


    public ListUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vista2 = inflater.inflate(R.layout.fragment_list_user, container, false);

        //declaro todos los spinner

        //Spinner Estado de Usuario
        listEstadoUsuario = (Spinner) vista2.findViewById(R.id.list_estado_de_usuario_spinner);

        listaEstado = new ArrayList<String>();
        listaEstado.add("1");
        listaEstado.add("2");
        listaEstado.add("3");
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listaEstado);
        adaptador1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listEstadoUsuario.setAdapter(adaptador1);

        //textView donde se muestra la respuesta a la busqueda del usuario (Search User)
        tvRtaListUser=(TextView)vista2.findViewById(R.id.txt_view_rta_list_usr);




        //aqui van todos los estados de los spinner!!!

        listEstadoUsuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                estadoUsuarioSpinner = arg0.getItemAtPosition(arg2).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //Boton Search User
        btnListUser = (Button) vista2.findViewById(R.id.btn_list_usr);
        btnListUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // Capturo el contenido del editText donde van los ID
                TextView et_id = (TextView) vista2.findViewById(R.id.id_list_user);
                String id_usuario = et_id.getText().toString();

                //Capturo el valor del spinner 'estado_de_usuario'
                int estado_de_usuario_spinner_int = Integer.parseInt(estadoUsuarioSpinner);
                String estado_de_usuario = Integer.toHexString(estado_de_usuario_spinner_int);
                if (estado_de_usuario.length() == 1) {
                    estado_de_usuario = "0" + estado_de_usuario;
                }
                byte[] estado_de_usuario_bytes = hexStringToByteArray(estado_de_usuario);

                byte[] frame2Send = new byte[7];

                frame2Send[0] = 0x24;// $
                frame2Send[1] = 0x40;// @
                frame2Send[2] = 0x07;// length
                frame2Send[3] = 0x06;// Tipo
                frame2Send[4] = 0x01;// Suponiendo 1 como origen PC
                frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                //se calcula el CRC
                frame2Send[6] = calcularCRC(frame2Send);

                tvRtaListUser.setText("");

                sendMessage(frame2Send);
            }

        });

        //Para limpiar la pantalla o descargar archivos
        tvRtaListUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setTitle(getString(R.string.txt_options))
                        .setMessage("")
                        .setNegativeButton(getString(R.string.txt_clear), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                // Limpio los textView
                                tvRtaListUser.setText("");
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



        return vista2;

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
       tvRtaListUser.setText(msg);
    }
}
