package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class RTCFragment extends Fragment implements View.OnClickListener {

    TextView tvRtaRTC;
    Button btnCheckSettingsRTC, btnRecordRTC;
    Spinner dayRTC, monthRTC, yearRTC, hourRTC, minuteRTC, secondRTC;
    ArrayList listDay, listMonth, listYear, listHour, listMinute, listSecond;
    String daySpinnerRTC, monthSpinnerRTC, yearSpinnerRTC, hourSpinnerRTC, minuteSpinnerRTC, secondSpinnerRTC;
    String buff = "";

    MainActivity activity;
    OnChangeFragment changeFragment;


    public RTCFragment() {
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
        View vistaRTC = inflater.inflate(R.layout.fragment_rtc, container, false);


        //textView donde se muestra las respuesta de las consultas
        tvRtaRTC = (TextView) vistaRTC.findViewById(R.id.txt_view_rta_rtc);
        tvRtaRTC.setText("");

        //se recuperan los botones de las interfaz RTC
        btnCheckSettingsRTC = (Button) vistaRTC.findViewById(R.id.btn_check_rtc);
        btnRecordRTC = (Button) vistaRTC.findViewById(R.id.btn_record_rtc);

        btnCheckSettingsRTC.setOnClickListener(this);
        btnRecordRTC.setOnClickListener(this);

        //declaro todos los spinner

        //Spinner Dia
        dayRTC = (Spinner) vistaRTC.findViewById(R.id.day_rtc_spinner);

        listDay = new ArrayList<String>();
        listDay.add("01");
        listDay.add("02");
        listDay.add("03");
        listDay.add("04");
        listDay.add("05");
        listDay.add("06");
        listDay.add("07");
        listDay.add("08");
        listDay.add("09");
        listDay.add("10");
        listDay.add("11");
        listDay.add("12");
        listDay.add("13");
        listDay.add("14");
        listDay.add("15");
        listDay.add("16");
        listDay.add("17");
        listDay.add("18");
        listDay.add("19");
        listDay.add("20");
        listDay.add("21");
        listDay.add("22");
        listDay.add("23");
        listDay.add("24");
        listDay.add("25");
        listDay.add("26");
        listDay.add("27");
        listDay.add("28");
        listDay.add("29");
        listDay.add("30");
        listDay.add("31");
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listDay);
        adaptador1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayRTC.setAdapter(adaptador1);

        //Spinner Mes
        monthRTC = (Spinner) vistaRTC.findViewById(R.id.month_rtc_spinner);

        listMonth = new ArrayList<String>();
        listMonth.add("01");
        listMonth.add("02");
        listMonth.add("03");
        listMonth.add("04");
        listMonth.add("05");
        listMonth.add("06");
        listMonth.add("07");
        listMonth.add("08");
        listMonth.add("09");
        listMonth.add("10");
        listMonth.add("11");
        listMonth.add("12");
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listMonth);
        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthRTC.setAdapter(adaptador2);

        //Spinner Año
        yearRTC = (Spinner) vistaRTC.findViewById(R.id.year_rtc_spinner);

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
        yearRTC.setAdapter(adaptador3);

        //Spinner Hora
        hourRTC = (Spinner) vistaRTC.findViewById(R.id.hour_rtc_spinner);

        listHour = new ArrayList<String>();
        listHour.add("00");
        listHour.add("01");
        listHour.add("02");
        listHour.add("03");
        listHour.add("04");
        listHour.add("05");
        listHour.add("06");
        listHour.add("07");
        listHour.add("08");
        listHour.add("09");
        listHour.add("10");
        listHour.add("11");
        listHour.add("12");
        listHour.add("13");
        listHour.add("14");
        listHour.add("15");
        listHour.add("16");
        listHour.add("17");
        listHour.add("18");
        listHour.add("19");
        listHour.add("20");
        listHour.add("21");
        listHour.add("22");
        listHour.add("23");
        ArrayAdapter<String> adaptador4 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listHour);
        adaptador4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourRTC.setAdapter(adaptador4);

        //Spinner Minuto
        minuteRTC = (Spinner) vistaRTC.findViewById(R.id.minute_rtc_spinner);

        listMinute = new ArrayList<String>();
        listMinute.add("00");
        listMinute.add("01");
        listMinute.add("02");
        listMinute.add("03");
        listMinute.add("04");
        listMinute.add("05");
        listMinute.add("06");
        listMinute.add("07");
        listMinute.add("08");
        listMinute.add("09");
        listMinute.add("10");
        listMinute.add("11");
        listMinute.add("12");
        listMinute.add("13");
        listMinute.add("14");
        listMinute.add("15");
        listMinute.add("16");
        listMinute.add("17");
        listMinute.add("18");
        listMinute.add("19");
        listMinute.add("20");
        listMinute.add("21");
        listMinute.add("22");
        listMinute.add("23");
        listMinute.add("24");
        listMinute.add("25");
        listMinute.add("26");
        listMinute.add("27");
        listMinute.add("28");
        listMinute.add("29");
        listMinute.add("30");
        listMinute.add("31");
        listMinute.add("32");
        listMinute.add("33");
        listMinute.add("34");
        listMinute.add("35");
        listMinute.add("36");
        listMinute.add("37");
        listMinute.add("38");
        listMinute.add("39");
        listMinute.add("40");
        listMinute.add("41");
        listMinute.add("42");
        listMinute.add("43");
        listMinute.add("44");
        listMinute.add("45");
        listMinute.add("46");
        listMinute.add("47");
        listMinute.add("48");
        listMinute.add("49");
        listMinute.add("50");
        listMinute.add("51");
        listMinute.add("52");
        listMinute.add("53");
        listMinute.add("54");
        listMinute.add("55");
        listMinute.add("56");
        listMinute.add("57");
        listMinute.add("58");
        listMinute.add("59");
        ArrayAdapter<String> adaptador5 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listMinute);
        adaptador5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minuteRTC.setAdapter(adaptador5);

        //Spinner Segundo
        secondRTC = (Spinner) vistaRTC.findViewById(R.id.second_rtc_spinner);

        listSecond = new ArrayList<String>();
        listSecond.add("00");
        listSecond.add("01");
        listSecond.add("02");
        listSecond.add("03");
        listSecond.add("04");
        listSecond.add("05");
        listSecond.add("06");
        listSecond.add("07");
        listSecond.add("08");
        listSecond.add("09");
        listSecond.add("10");
        listSecond.add("11");
        listSecond.add("12");
        listSecond.add("13");
        listSecond.add("14");
        listSecond.add("15");
        listSecond.add("16");
        listSecond.add("17");
        listSecond.add("18");
        listSecond.add("19");
        listSecond.add("20");
        listSecond.add("21");
        listSecond.add("22");
        listSecond.add("23");
        listSecond.add("24");
        listSecond.add("25");
        listSecond.add("26");
        listSecond.add("27");
        listSecond.add("28");
        listSecond.add("29");
        listSecond.add("30");
        listSecond.add("31");
        listSecond.add("32");
        listSecond.add("33");
        listSecond.add("34");
        listSecond.add("35");
        listSecond.add("36");
        listSecond.add("37");
        listSecond.add("38");
        listSecond.add("39");
        listSecond.add("40");
        listSecond.add("41");
        listSecond.add("42");
        listSecond.add("43");
        listSecond.add("44");
        listSecond.add("45");
        listSecond.add("46");
        listSecond.add("47");
        listSecond.add("48");
        listSecond.add("49");
        listSecond.add("50");
        listSecond.add("51");
        listSecond.add("52");
        listSecond.add("53");
        listSecond.add("54");
        listSecond.add("55");
        listSecond.add("56");
        listSecond.add("57");
        listSecond.add("58");
        listSecond.add("59");
        ArrayAdapter<String> adaptador6 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listSecond);
        adaptador6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        secondRTC.setAdapter(adaptador6);

        //aqui van todos los estados de los spinner!!!

        dayRTC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                daySpinnerRTC = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        monthRTC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                monthSpinnerRTC = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        yearRTC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                yearSpinnerRTC = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        hourRTC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                hourSpinnerRTC = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        minuteRTC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                minuteSpinnerRTC = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        secondRTC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                secondSpinnerRTC = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        return vistaRTC;
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            //caso de CHECK SETTINGS
            case R.id.btn_check_rtc:

                byte[] frame2Send = new byte[7];

                frame2Send[0] = 0x24;// $
                frame2Send[1] = 0x40;// @
                frame2Send[2] = 0x07;// length
                frame2Send[3] = 0x0F;// Tipo
                frame2Send[4] = 0x01;// Suponiendo 1 como origen PC
                frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame2Send[6] = calcularCRC(frame2Send);

                tvRtaRTC.setText("");
                buff = "";

                sendMessage(frame2Send);

                break;

            case R.id.btn_record_rtc:

                //Capturo el valor del spinner 'dia'
                int daySpinnerInt = Integer.parseInt(daySpinnerRTC);
                String day = Integer.toHexString(daySpinnerInt);
                if (day.length() == 1) {
                    day = "0" + day;
                }
                byte[] dayBytes = hexStringToByteArray(day);

                //Capturo el valor del spinner 'mes'
                int monthSpinnerInt = Integer.parseInt(monthSpinnerRTC);
                String month = Integer.toHexString(monthSpinnerInt);
                if (month.length() == 1) {
                    month = "0" + month;
                }
                byte[] monthBytes = hexStringToByteArray(month);

                //Capturo el valor del spinner 'año'
                int yearSpinnerInt = Integer.parseInt(yearSpinnerRTC);
                String year = Integer.toHexString(yearSpinnerInt);
                if (year.length() == 3) {
                    year = "0" + year;
                }
                byte[] yearBytes = hexStringToByteArray(year);

                //Capturo el valor del spinner 'hora'
                int hourSpinnerInt = Integer.parseInt(hourSpinnerRTC);
                String hour = Integer.toHexString(hourSpinnerInt);
                if (hour.length() == 1) {
                    hour = "0" + hour;
                }
                byte[] hourBytes = hexStringToByteArray(hour);

                //Capturo el valor del spinner 'minuto'
                int minuteSpinnerInt = Integer.parseInt(minuteSpinnerRTC);
                String minute = Integer.toHexString(minuteSpinnerInt);
                if (minute.length() == 1) {
                    minute = "0" + minute;
                }
                byte[] minuteBytes = hexStringToByteArray(minute);

                //Capturo el valor del spinner 'segundo'
                int secondSpinnerInt = Integer.parseInt(secondSpinnerRTC);
                String second = Integer.toHexString(secondSpinnerInt);
                if (second.length() == 1) {
                    second = "0" + second;
                }
                byte[] secondBytes = hexStringToByteArray(second);


                byte[] frame2send = new byte[23];


                frame2send[0] = 0x24;// $
                frame2send[1] = 0x40;// @
                frame2send[2] = 0x0D;// length
                frame2send[3] = 0x10;// Tipo
                frame2send[4] = 0x01;// Suponiendo 1 como origen PC
                frame2send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame2send[6] = dayBytes[0];
                frame2send[7] = monthBytes[0];
                frame2send[8] = yearBytes[0];
                frame2send[9] = hourBytes[0];
                frame2send[10] = minuteBytes[0];//tasa de transmision
                frame2send[11] = secondBytes[0];//ganancia de transmision
                frame2send[22] = calcularCRC(frame2send);

                sendMessage(frame2send);


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

            File file = new File(Environment.getExternalStorageDirectory(), filename);
            OutputStreamWriter outw = new OutputStreamWriter(new FileOutputStream(file));
            outw.write(textfile);
            outw.close();
        } catch (Exception e) {
        }
    }

    public void setMsg(String msg) {
        tvRtaRTC.setText(msg);
    }
}