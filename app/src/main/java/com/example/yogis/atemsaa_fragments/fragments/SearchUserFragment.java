package com.example.yogis.atemsaa_fragments.fragments;


import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
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
public class SearchUserFragment extends Fragment {

    //se inicializan todos los objetos
    Button btnSearchUser;
    TextView tv_rta_search_user;
    Spinner search_estado_de_usuario, accion_de_usuario;
    ArrayList lista_estado, lista_accion;
    String estado_de_usuario_spinner, accion_de_usuario_spinner;
    String buff = "";
    byte[] readBuf;

    OnChangeFragment changeFragment;

    public SearchUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        changeFragment = (OnChangeFragment) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View vista1 = inflater.inflate(R.layout.fragment_search_user, container, false);


//declaro todos los spinner

        //Spinner Estado de Usuario
        search_estado_de_usuario = (Spinner) vista1.findViewById(R.id.search_estado_de_usuario_spinner);

        lista_estado = new ArrayList<String>();
        lista_estado.add("1");
        lista_estado.add("2");
        lista_estado.add("3");
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, lista_estado);
        adaptador1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search_estado_de_usuario.setAdapter(adaptador1);

        //textView donde se muestra la respuesta a la busqueda del usuario (Search User)
        tv_rta_search_user=(TextView)vista1.findViewById(R.id.txt_view_rta_search);




        //aqui van todos los estados de los spinner!!!

        search_estado_de_usuario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                estado_de_usuario_spinner = arg0.getItemAtPosition(arg2).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //Boton Search User
        btnSearchUser = (Button) vista1.findViewById(R.id.btn_search_usr);
        btnSearchUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // Capturo el contenido del editText donde van los ID
                TextView et_id = (TextView) vista1.findViewById(R.id.id_search_user);
                String id_usuario = et_id.getText().toString();

                //Capturo el valor del spinner 'estado_de_usuario'
                int estado_de_usuario_spinner_int = Integer.parseInt(estado_de_usuario_spinner);
                String estado_de_usuario = Integer.toHexString(estado_de_usuario_spinner_int);
                if (estado_de_usuario.length() == 1) {
                    estado_de_usuario = "0" + estado_de_usuario;
                }
                byte[] estado_de_usuario_bytes = hexStringToByteArray(estado_de_usuario);

                if (id_usuario.length() == 16) {
                    byte[] id_usuario_bytes = hexStringToByteArray(id_usuario);

                    byte[] frame2Send = new byte[15];


                    frame2Send[0] = 0x24;// $
                    frame2Send[1] = 0x40;// @
                    frame2Send[2] = 0x0F;// length
                    frame2Send[3] = 0x08;// Tipo
                    frame2Send[4] = 0x01;// Suponiendo 1 como origen PC
                    frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                    frame2Send[6] = id_usuario_bytes[0];
                    frame2Send[7] = id_usuario_bytes[1];
                    frame2Send[8] = id_usuario_bytes[2];
                    frame2Send[9] = id_usuario_bytes[3];
                    frame2Send[10] = id_usuario_bytes[4];
                    frame2Send[11] = id_usuario_bytes[5];
                    frame2Send[12] = id_usuario_bytes[6];
                    frame2Send[13] = id_usuario_bytes[7];
                    frame2Send[14] = calcularCRC(frame2Send);

                    sendMessage(frame2Send);

                } else {
                    toastIngresarId();
                }
            }

        });

        //Para limpiar la pantalla o descargar archivos
        tv_rta_search_user.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setTitle(getString(R.string.txt_options))
                        .setMessage("")
                        .setNegativeButton(getString(R.string.txt_clear), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                // Limpio los textView
                                tv_rta_search_user.setText("");
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



        return vista1;

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
