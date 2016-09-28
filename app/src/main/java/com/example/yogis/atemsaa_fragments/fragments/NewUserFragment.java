package com.example.yogis.atemsaa_fragments.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.BluetoothCommandService;
import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewUserFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton flBut, flAdd, flTestFrame;
    Animation open, close, clock, anticlock;
    boolean isOpen = false;

    TextView tvRtaListNewUser;
    String buff = "";
    String idUsuario;
    static String estadoUsuario = "1";

    OnChangeFragment changeFragment;

    EditText edTxtID;

    MainActivity activity;

    public NewUserFragment() {
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
         View vistaUsr = inflater.inflate(R.layout.fragment_new_user, container, false);

        //textView donde se muestra las respuesta de las consultas
        tvRtaListNewUser=(TextView)vistaUsr.findViewById(R.id.txt_view_rta_list_newusr);
        tvRtaListNewUser.setText("");

        //Para limpiar la pantalla o descargar archivos
        tvRtaListNewUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                new AlertDialog.Builder(v.getContext())
                        .setTitle(getString(R.string.txt_options))
                        .setMessage("")
                        .setNegativeButton(getString(R.string.txt_clear), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dlg, int sumthin) {
                                // Limpio los textView
                                tvRtaListNewUser.setText("");
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

        flBut = (FloatingActionButton) vistaUsr.findViewById(R.id.flButton);
        flAdd = (FloatingActionButton) vistaUsr.findViewById(R.id.flAddUser);
        flTestFrame = (FloatingActionButton) vistaUsr.findViewById(R.id.flTestFrameUser);

        open = AnimationUtils.loadAnimation(vistaUsr.getContext(),R.anim.open);
        close = AnimationUtils.loadAnimation(vistaUsr.getContext(),R.anim.close);
        clock = AnimationUtils.loadAnimation(vistaUsr.getContext(),R.anim.rorate_clock);
        anticlock = AnimationUtils.loadAnimation(vistaUsr.getContext(),R.anim.rotate_anticlock);

        flBut.setOnClickListener(this);
        flAdd.setOnClickListener(this);
        flTestFrame.setOnClickListener(this);

        // Capturo el contenido del editText donde van los ID
        edTxtID = (EditText) vistaUsr.findViewById(R.id.id_list_newuser);

        return vistaUsr;
    }

    @Override
    public void onClick(View view) {

        if (isOpen){

            flAdd.startAnimation(close);
            flTestFrame.startAnimation(close);
            flBut.startAnimation(anticlock);
            flAdd.setClickable(false);
            flTestFrame.setClickable(false);
            isOpen = false;
        }
        else {
            flAdd.startAnimation(open);
            flTestFrame.startAnimation(open);
            flBut.startAnimation(clock);
            flAdd.setClickable(true);
            flTestFrame.setClickable(true);
            isOpen = true;
        }
        switch(view.getId()){

            //caso de REGISTRAR un usuario
            case R.id.flAddUser:
                //Toast.makeText(this.getActivity(),"Button is clicked", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),"Button is clicked", Toast.LENGTH_LONG).show();
                //changeFragment.onChange(OnChangeFragment.NEWUSER);
                idUsuario = edTxtID.getText().toString();

                if (estadoUsuario.length() == 1) {
                    estadoUsuario = "0" + estadoUsuario;
                }
                byte[] estadoUsuarioBytes = hexStringToByteArray(estadoUsuario);

                if (idUsuario.length() == 16) {

                    byte[] frame2Send = new byte[16];

                    byte[] idUsuarioBytes = hexStringToByteArray(idUsuario);

                    frame2Send[0] = 0x24;// $
                    frame2Send[1] = 0x40;// @
                    frame2Send[2] = 0x10;// length
                    frame2Send[3] = 0x05;// Tipo
                    frame2Send[4] = 0x01;// Suponiendo 1 como origen PC
                    frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                    frame2Send[6] = idUsuarioBytes[0];
                    frame2Send[7] = idUsuarioBytes[1];
                    frame2Send[8] = idUsuarioBytes[2];
                    frame2Send[9] = idUsuarioBytes[3];
                    frame2Send[10] = idUsuarioBytes[4];
                    frame2Send[11] = idUsuarioBytes[5];
                    frame2Send[12] = idUsuarioBytes[6];
                    frame2Send[13] = idUsuarioBytes[7];
                    frame2Send[14] = estadoUsuarioBytes[0]; //Estado de usuario
                    frame2Send[15] = calcularCRC(frame2Send);

                    tvRtaListNewUser.setText("");
                    buff="";

                    sendMessage(frame2Send);

                } else {
                    toastIngresarId();
                }
                break;

            //caso de ENVIAR TRAMA DE PRUEBA
            case R.id.flTestFrameUser:
                //Toast.makeText(this.getActivity(),"Button is clicked", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),"Button is clicked", Toast.LENGTH_LONG).show();
                //changeFragment.onChange(OnChangeFragment.SEARCH);

                idUsuario = edTxtID.getText().toString();

                if (idUsuario.length() == 16) {
                    byte[] idUsuarioBytes = hexStringToByteArray(idUsuario);

                    byte[] frame2Send = new byte[15];

                    frame2Send[0] = 0x24;// $
                    frame2Send[1] = 0x40;// @
                    frame2Send[2] = 0x0F;// length
                    frame2Send[3] = 0x09;// Tipo
                    frame2Send[4] = 0x01;// Suponiendo 1 como origen PC
                    frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                    frame2Send[6] = idUsuarioBytes[0];
                    frame2Send[7] = idUsuarioBytes[1];
                    frame2Send[8] = idUsuarioBytes[2];
                    frame2Send[9] = idUsuarioBytes[3];
                    frame2Send[10] = idUsuarioBytes[4];
                    frame2Send[11] = idUsuarioBytes[5];
                    frame2Send[12] = idUsuarioBytes[6];
                    frame2Send[13] = idUsuarioBytes[7];
                    frame2Send[14] = calcularCRC(frame2Send);

                    tvRtaListNewUser.setText("");
                    buff="";

                    sendMessage(frame2Send);

                } else {
                    toastIngresarId();
                }

                break;


        }
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
        tvRtaListNewUser.setText(msg);
    }

   public void getSearchUser(){

       idUsuario = edTxtID.getText().toString();

       if (idUsuario.length() == 16) {
           byte[] idUsuarioBytes = hexStringToByteArray(idUsuario);

           byte[] frame2Send = new byte[15];

           frame2Send[0] = 0x24;// $
           frame2Send[1] = 0x40;// @
           frame2Send[2] = 0x0F;// length
           frame2Send[3] = 0x08;// Tipo
           frame2Send[4] = 0x01;// Suponiendo 1 como origen PC
           frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
           frame2Send[6] = idUsuarioBytes[0];
           frame2Send[7] = idUsuarioBytes[1];
           frame2Send[8] = idUsuarioBytes[2];
           frame2Send[9] = idUsuarioBytes[3];
           frame2Send[10] = idUsuarioBytes[4];
           frame2Send[11] = idUsuarioBytes[5];
           frame2Send[12] = idUsuarioBytes[6];
           frame2Send[13] = idUsuarioBytes[7];
           frame2Send[14] = calcularCRC(frame2Send);

           tvRtaListNewUser.setText("");
           buff="";

           sendMessage(frame2Send);

       } else {
           toastIngresarId();
       }
       return;


   }

}
