package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClockSettingsFragment extends Fragment implements View.OnClickListener {

    //se inicializan todos los objetos
    TextView tvRtaClockSettings;
    Button btnCheckClock, btnSetClock;
    Spinner daySet, monthSet, yearSet, hourSet, minuteSet, secondSet;
    ArrayList listaDay, listaMonth, listaYear, listaHour, listaMinute, listaSecond;
    String daySpinner, monthSpinner, yearSpinner, hourSpinner, minuteSpinner, secondSpinner;
    String buff = "";

    MainActivity activity;

    byte[] readBuf;
    byte gantxBytes, ganrxBytes, tasatxBytes, retxBytes;

    OnChangeFragment changeFragment;

    String idUsuario;
    static String estadoUsuario = "1";


    EditText edTxtID;

    public ClockSettingsFragment() {
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
        View vistaClock = inflater.inflate(R.layout.fragment_clock_settings, container, false);

        //textView donde se muestra las respuesta de las consultas
        tvRtaClockSettings=(TextView)vistaClock.findViewById(R.id.txt_view_rta_clock);
        tvRtaClockSettings.setText("");

        //se recuperan los botones de la interfaz de Settings
        btnCheckClock = (Button) vistaClock.findViewById(R.id.btn_check_clock);
        btnSetClock = (Button) vistaClock.findViewById(R.id.btn_set_clock);

        btnCheckClock.setOnClickListener(this);
        btnSetClock.setOnClickListener(this);


        //declaro todos los spinner

        //Spinner Dia
        daySet = (Spinner) vistaClock.findViewById(R.id.day_spinner);

        listaDay = new ArrayList<String>();
        listaDay.add("01");
        listaDay.add("02");
        listaDay.add("03");
        listaDay.add("125 mVpp");
        listaDay.add("180 mVpp");
        listaDay.add("250 mVpp");
        listaDay.add("360 mVpp");
        listaDay.add("480 mVpp");
        listaDay.add("660 mVpp");
        listaDay.add("900 mVpp");
        listaDay.add("1.25 Vpp");
        listaDay.add("1.55 Vpp");
        listaDay.add("2.25 Vpp");
        listaDay.add("3.00 Vpp");
        listaDay.add("3.50 Vpp");
        ArrayAdapter<String> adaptador3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listaDay);
        adaptador3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySet.setAdapter(adaptador3);

        //Spinner Mes
        monthSet = (Spinner) vistaClock.findViewById(R.id.month_spinner);

        listaMonth = new ArrayList<String>();
        listaMonth.add("5 mVrms");
        listaMonth.add("2.5 mVrms");
        listaMonth.add("1.25 mVrms");
        listaMonth.add("600 uVrms");
        listaMonth.add("350 uVrms");
        listaMonth.add("250 uVrms");
        listaMonth.add("125 uVrms");
        ArrayAdapter<String> adaptador4 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listaMonth);
        adaptador4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSet.setAdapter(adaptador4);

        //Spinner Retardo de transmision
        yearSet = (Spinner) vistaClock.findViewById(R.id.year_spinner);

        listaYear = new ArrayList<String>();
        listaYear.add("100 ms");
        listaYear.add("200 ms");
        listaYear.add("300 ms");
        listaYear.add("400 ms");
        listaYear.add("500 ms");
        ArrayAdapter<String> adaptador5 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listaYear);
        adaptador5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSet.setAdapter(adaptador5);

        //Spinner Hora
        hourSet = (Spinner) vistaClock.findViewById(R.id.hour_spinner);

        listaHour = new ArrayList<String>();
        listaHour.add("600 bps");
        listaHour.add("1200 bps");
        listaHour.add("2400 bps");
        ArrayAdapter<String> adaptador6 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listaHour);
        adaptador6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSet.setAdapter(adaptador6);

        //Spinner Minuto
        minuteSet = (Spinner) vistaClock.findViewById(R.id.minute_spinner);

        listaMinute = new ArrayList<String>();
        listaMinute.add("00");
        listaMinute.add("01");
        listaMinute.add("02");
        listaMinute.add("03");
        listaMinute.add("04");
        listaMinute.add("05");
        listaMinute.add("06");
        listaMinute.add("07");
        listaMinute.add("08");
        listaMinute.add("09");
        listaMinute.add("10");
        listaMinute.add("11");
        listaMinute.add("12");
        listaMinute.add("13");
        listaMinute.add("14");
        listaMinute.add("15");
        listaMinute.add("16");
        listaMinute.add("17");
        listaMinute.add("18");
        listaMinute.add("19");
        listaMinute.add("20");
        listaMinute.add("21");
        listaMinute.add("22");
        listaMinute.add("23");
        ArrayAdapter<String> adaptador7 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listaMinute);
        adaptador7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minuteSet.setAdapter(adaptador7);

        //aqui van todos los estados de los spinner!!!

        daySet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                daySpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        monthSet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                monthSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        yearSet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                yearSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        hourSet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                hourSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        minuteSet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                minuteSpinner = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        return vistaClock;
    }

    @Override
    public void onClick(View view) {

    }
}
