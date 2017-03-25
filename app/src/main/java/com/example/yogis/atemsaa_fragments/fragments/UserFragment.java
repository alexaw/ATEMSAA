package com.example.yogis.atemsaa_fragments.fragments;


import android.app.AlertDialog;
import android.content.Context;
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
public class UserFragment extends Fragment implements View.OnClickListener {

    FloatingActionsMenu flMore;
    FloatingActionButton flAdd, flRemove, flTestFrame, flOpc;

    TextView tvRtaListNewUser;

    Spinner typeDevice, meterNumber;
    ArrayList listTypeDev, listMeterNum;
    String typeDeviceSpinner, meterNumberSpinner;
    byte typeDevBytes, meterNumBytes;


    String buff = "";
    String idUsuario;
    static String estadoUsuario = "1";

    OnChangeFragment changeFragment;

    EditText edTxtID;

    MainActivity activity;

    public UserFragment() {
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
         View vistaUsr = inflater.inflate(R.layout.fragment_user, container, false);

        //textView donde se muestra las respuesta de las consultas
        tvRtaListNewUser=(TextView)vistaUsr.findViewById(R.id.txt_view_rta_list_newusr);
        tvRtaListNewUser.setText("");


        flMore = (FloatingActionsMenu) vistaUsr.findViewById(R.id.fl_more);

        flAdd = (FloatingActionButton) vistaUsr.findViewById(R.id.fl_add_user);
        flRemove = (FloatingActionButton) vistaUsr.findViewById(R.id.fl_remove_user);
        flTestFrame = (FloatingActionButton) vistaUsr.findViewById(R.id.fl_test_frame_user);
        flOpc = (FloatingActionButton) vistaUsr.findViewById(R.id.fl_opc);

        /*txtOpc = (TextView) vistaUsr.findViewById(R.id.txt_opc);
        txtAdd = (TextView) vistaUsr.findViewById(R.id.txt_add_user);
        txtTest = (TextView) vistaUsr.findViewById(R.id.txt_test_frame);

        open = AnimationUtils.loadAnimation(vistaUsr.getContext(),R.anim.open);
        close = AnimationUtils.loadAnimation(vistaUsr.getContext(),R.anim.close);
        clock = AnimationUtils.loadAnimation(vistaUsr.getContext(),R.anim.rorate_clock);
        anticlock = AnimationUtils.loadAnimation(vistaUsr.getContext(),R.anim.rotate_anticlock);

*/
        flAdd.setOnClickListener(this);
        flRemove.setOnClickListener(this);
        flTestFrame.setOnClickListener(this);
        flOpc.setOnClickListener(this);


        // Capturo el contenido del editText donde van los ID
        edTxtID = (EditText) vistaUsr.findViewById(R.id.id_list_newuser);

        //SPINNER PARA PLC-MMS

        //Spinner Tipo de Dispositivo
        typeDevice = (Spinner) vistaUsr.findViewById(R.id.type_device_spinner);

        listTypeDev = new ArrayList<String>();
        listTypeDev.add("PLC-TU");
        listTypeDev.add("PLC-MC");
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listTypeDev);
        adaptador1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeDevice.setAdapter(adaptador1);

        //Spinner Cantidad de Medidores
        meterNumber = (Spinner) vistaUsr.findViewById(R.id.meter_number_spinner);

        listMeterNum = new ArrayList<String>();
        listMeterNum.add("1");
        listMeterNum.add("2");
        listMeterNum.add("3");
        listMeterNum.add("4");
        listMeterNum.add("5");
        listMeterNum.add("6");
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listMeterNum);
        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meterNumber.setAdapter(adaptador2);

        //aqui van todos los estados de los spinner!!!

        typeDevice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                typeDeviceSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        meterNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                meterNumberSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        return vistaUsr;
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            //caso de REGISTRAR un usuario
            case R.id.fl_add_user:

                flMore.collapse();
                idUsuario = edTxtID.getText().toString();

                //Capturo el valor del spinner 'tipo de dispositivo'
                String typeDeviceChosen = typeDeviceSpinner;

                switch (typeDeviceChosen){
                    case "PLC-TU":
                        typeDevBytes = 0x01;
                        break;

                    case "PLC-MC":
                        typeDevBytes = 0x02;
                        break;
                }

                //Capturo el valor del spinner 'cantidad de medidores'
                String meterNumberChosen = meterNumberSpinner;


                switch (meterNumberChosen)
                {

                    case "1":
                        meterNumBytes = 0x01;
                        break;

                    case "2":
                        meterNumBytes = 0x02;
                        break;

                    case "3":
                        meterNumBytes = 0x01;
                        break;

                    case "4":
                        meterNumBytes = 0x02;
                        break;

                    case "5":
                        meterNumBytes = 0x01;
                        break;

                    case "6":
                        meterNumBytes = 0x02;
                        break;
                }

                if (idUsuario.length() == 16) {

                    byte[] frame2Send = new byte[17];

                    byte[] idUsuarioBytes = hexStringToByteArray(idUsuario);

                    frame2Send[0] = 0x24;// $
                    frame2Send[1] = 0x40;// @
                    frame2Send[2] = 0x11;// length
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
                    frame2Send[14] = typeDevBytes; //Tipo de dispositivo
                    frame2Send[15] = meterNumBytes; //Numero de medidores
                    frame2Send[16] = calcularCRC(frame2Send);

                    tvRtaListNewUser.setText("");
                    buff="";

                    sendMessage(frame2Send);

                } else {
                    toastIngresarId();
                }
                break;

            //caso de ELIMINAR un usuario
            case R.id.fl_remove_user:

                flMore.collapse();
                idUsuario = edTxtID.getText().toString();

                if (idUsuario.length() == 16) {

                    byte[] frame2Send = new byte[14];

                    byte[] idUsuarioBytes = hexStringToByteArray(idUsuario);

                    frame2Send[0] = 0x24;// $
                    frame2Send[1] = 0x40;// @
                    frame2Send[2] = 0x0F;// length
                    frame2Send[3] = 0x0B;// Tipo
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


            //caso de ENVIAR TRAMA DE PRUEBA
            case R.id.fl_test_frame_user:

                flMore.collapse();
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

            case R.id.fl_opc:

                flMore.collapse();

                new AlertDialog.Builder(view.getContext())
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
