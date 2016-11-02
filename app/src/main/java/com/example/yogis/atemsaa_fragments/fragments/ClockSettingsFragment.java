package com.example.yogis.atemsaa_fragments.fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
public class ClockSettingsFragment extends DialogFragment  {

    //se inicializan todos los objetos
    private int year, month, day;
    private static DatePickerDialog.OnDateSetListener oyenteSelectorFecha;

    public static void showDateDialog(AppCompatActivity activity, DateSelectedListener dateSelectedListener){

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("dialog");

        if(fragment != null){
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.remove(fragment);
        }

        ClockSettingsFragment clockFragment = new ClockSettingsFragment();
        clockFragment.show(activity.getSupportFragmentManager(), "dialog");
    }

    public interface DateSelectedListener
    {
        void onDateSelected(String date, int year, int month, int day);
    }

    DateSelectedListener dateSelectedListener;

    public ClockSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.dateSelectedListener = (DateSelectedListener) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar calendario = Calendar.getInstance();
        year = calendario.get(Calendar.YEAR);
        month = calendario.get(Calendar.MONTH)+1;
        day = calendario.get(Calendar.DAY_OF_MONTH);

        oyenteSelectorFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1;
                day = i2;

                dateSelectedListener.onDateSelected(year+"/"+month+"/"+day, year, month, day);
            }
        };
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getContext(), oyenteSelectorFecha, year, month, day);
    }

}
