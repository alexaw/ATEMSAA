package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
import android.os.Bundle;
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
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsFragment extends Fragment implements View.OnClickListener {

    FloatingActionsMenu flMore;
    FloatingActionButton flParcial, flList, flOpc;

    TextView tvRtaReports;
    Spinner monthReports, yearReports;
    ArrayList listMonth, listYear;
    String monthSpinnerReports, yearSpinnerReports;
    String buff = "";

    MainActivity activity;
    OnChangeFragment changeFragment;

    public ReportsFragment() {
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
        View vistaReports = inflater.inflate(R.layout.fragment_reports, container, false);

        //textView donde se muestra las respuesta de las consultas
        tvRtaReports=(TextView)vistaReports.findViewById(R.id.txt_view_rta_reports);
        tvRtaReports.setText("");


        flMore = (FloatingActionsMenu) vistaReports.findViewById(R.id.fl_more);

        flList = (FloatingActionButton) vistaReports.findViewById(R.id.fl_list);
        flParcial = (FloatingActionButton) vistaReports.findViewById(R.id.fl_parcial);
        flOpc = (FloatingActionButton) vistaReports.findViewById(R.id.fl_opc);

        flList.setOnClickListener(this);
        flParcial.setOnClickListener(this);
        flOpc.setOnClickListener(this);

        //Spinner Mes
        monthReports = (Spinner) vistaReports.findViewById(R.id.month_rtc_spinner);

        listMonth = new ArrayList<String>();
        listMonth.add("1");
        listMonth.add("2");
        listMonth.add("3");
        listMonth.add("4");
        listMonth.add("5");
        listMonth.add("6");
        listMonth.add("7");
        listMonth.add("8");
        listMonth.add("9");
        listMonth.add("10");
        listMonth.add("11");
        listMonth.add("12");
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listMonth);
        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthReports.setAdapter(adaptador2);

        //Spinner Año
        yearReports = (Spinner) vistaReports.findViewById(R.id.year_rtc_spinner);

        listYear = new ArrayList<String>();
        listYear.add("2011");
        listYear.add("2012");
        listYear.add("2013");
        listYear.add("2014");
        listYear.add("2015");
        listYear.add("2016");
        listYear.add("2017");
        listYear.add("2018");
        listYear.add("2019");
        listYear.add("2020");
        ArrayAdapter<String> adaptador3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listYear);
        adaptador3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearReports.setAdapter(adaptador3);

        monthReports.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                monthSpinnerReports = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        yearReports.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                yearSpinnerReports = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });



        return vistaReports;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {

            case R.id.fl_list:

            close();

                byte[] frame2Send = new byte[7];

            frame2Send[0] = 0x24;
            frame2Send[1] = 0x40;
            frame2Send[2] = 0x07;
            frame2Send[3] = 0x02;
            frame2Send[4] = 0x01;
            frame2Send[5] = 0x02;
            frame2Send[6] = calcularCRC(frame2Send);

            tvRtaReports.setText("");
            buff="";

            sendMessage(frame2Send);
                break;

            //caso de reporte parcial
            case R.id.fl_parcial:
                close();

                frame2Send = new byte[7];

                frame2Send[0] = 0x24;
                frame2Send[1] = 0x40;
                frame2Send[2] = 0x07;
                frame2Send[3] = 0x13;
                frame2Send[4] = 0x01;
                frame2Send[5] = 0x02;
                frame2Send[6] = calcularCRC(frame2Send);

                tvRtaReports.setText("");
                buff="";

                sendMessage(frame2Send);
                break;

            case R.id.fl_opc:
                close();

                //Capturo el valor del spinner 'mes'
                int monthSpinnerInt = Integer.parseInt(monthSpinnerReports);
                String month = Integer.toHexString(monthSpinnerInt);
                if (month.length() == 1) {
                    month = "0" + month;
                }
                byte[] monthBytes = hexStringToByteArray(month);

                //Capturo el valor del spinner 'año'
                int yearSpinnerInt = Integer.parseInt(yearSpinnerReports);
                String year = Integer.toHexString(yearSpinnerInt);
                if (year.length() == 1) {
                    year = "0" + year;
                }
                byte[] yearBytes = hexStringToByteArray(year);

                frame2Send = new byte[9];

                frame2Send[0] = 0x24;// $
                frame2Send[1] = 0x40;// @
                frame2Send[2] = 0x09;// length
                frame2Send[3] = 0x03;// Tipo
                frame2Send[4] = 0x01;// Supioniendo 1 como origen PC
                frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame2Send[6] = monthBytes[0];
                frame2Send[7] = yearBytes[0];
                frame2Send[8] = calcularCRC(frame2Send);

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

    private byte calcularCRC(byte[] frame2send) {
        Byte CRC = (byte) (frame2send[2]);
        for (int i = 3; i <= frame2send.length - 1; i++) {
            CRC = (byte) (CRC ^ frame2send[i]);
        }

        Log.e("CRCCCCCC", CRC.toString());

        return CRC;
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

    public void setMsg(String msg){
        tvRtaReports.setText(msg);

    }

    public void close(){
        flMore.collapse();
    }
}
