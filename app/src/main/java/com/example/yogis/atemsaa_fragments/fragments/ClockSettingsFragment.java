package com.example.yogis.atemsaa_fragments.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClockSettingsFragment extends DialogFragment implements View.OnClickListener  {

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


    EditText campoFecha;

    private int year, month, day;
    private static final  int TIPO_DIALOGO = 0;
    private static DatePickerDialog.OnDateSetListener oyenteSelectorFecha;

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

        campoFecha = (EditText) vistaClock.findViewById(R.id.editFecha);
        btnCheckClock = (Button) vistaClock.findViewById(R.id.btn_clock);
        btnCheckClock.setOnClickListener(this);

        Calendar calendario = Calendar.getInstance();
        year = calendario.get(Calendar.YEAR);
        month = calendario.get(Calendar.MONTH)+1;
        day = calendario.get(Calendar.DAY_OF_MONTH);

        mostrarFecha();


        oyenteSelectorFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1;
                day = i2;

                mostrarFecha();
            }
        };


        return vistaClock;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {

            case R.id.btn_clock:
                mostrarCalendario(view);

        }

    }

    public  void mostrarFecha(){

        campoFecha.setText(year+"/"+month+"/"+day);
    }



    protected Dialog onCreateDialog(int id){
        switch (id) {
            case 0:
                return new DatePickerDialog(this.getActivity(), oyenteSelectorFecha, year, month, day);

        }
        return null;
    }

    public void mostrarCalendario(View control){

        onCreateDialog(TIPO_DIALOGO);
    }



}
