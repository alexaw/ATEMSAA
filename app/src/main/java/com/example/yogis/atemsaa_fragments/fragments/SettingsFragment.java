package com.example.yogis.atemsaa_fragments.fragments;



import android.content.Context;

import android.os.Bundle;
import android.os.Environment;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

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
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {

    //se inicializan todos los objetos

    FloatingActionButton flPlcMms, flPlcTu, flPlcMc;
    FloatingActionsMenu flMore;

    Animation open, close, clock, anticlock;
    boolean isOpen = false;

    TextView tvRtaPLCMMS, txtPlcMms, txtPlcTu, txtPlcMc, tvRtaClock, tvRtaPLCMC, tvRtaPLCTU ;
    Button btnCheckSettingsMMS, btnCheckSettingsMC, btnCheckSettingsTU, btnRecordMMS, btnRecordMC, btnRecordTU ;
    Spinner txGainMMS, rxGainMMS, txDelayMMS, txRateMMS,
            timeSurveyMMS, txGainMC, rxGainMC, txDelayMC, txRateMC,
            txGainTU, rxGainTU, txDelayTU, txRateTU;
    ArrayList listGtx, listGrx, listDTx, listRTx, listTime;
    String transmissionGainSpinnerMMS, receptionGainSpinnerMMS, transmissionDelaySpinnerMMS, transmissionRateSpinnerMMS, timeSurveySpinnerMMS,
            transmissionGainSpinnerMC, receptionGainSpinnerMC, transmissionDelaySpinnerMC, transmissionRateSpinnerMC,
            transmissionGainSpinnerTU, receptionGainSpinnerTU, transmissionDelaySpinnerTU, transmissionRateSpinnerTU;
    String buff = "";

    MainActivity activity;

    byte[] readBuf;
    byte gantxBytesMMS, ganrxBytesMMS, ratetxBytesMMS, delaytxBytesMMS,
            gantxBytesMC, ganrxBytesMC, ratetxBytesMC, delaytxBytesMC,
            gantxBytesTU, ganrxBytesTU, ratetxBytesTU, delaytxBytesTU;

    OnChangeFragment changeFragment;

    String idUsuarioMMS, idMacro, idUsuarioTU ;
    static String estadoUsuario = "1";


    EditText edTxtIDMMS, edTxtIDMacro, edTxtIDTU;
    TabHost th;




    public SettingsFragment() {
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
        View vistaStgs = inflater.inflate(R.layout.fragment_settings, container, false);

        // Capturo el contenido del editText donde van los ID
        edTxtIDMMS = (EditText) vistaStgs.findViewById(R.id.id_dispositivoMMS);
        edTxtIDMacro = (EditText) vistaStgs.findViewById(R.id.id_macro);
        edTxtIDTU = (EditText) vistaStgs.findViewById(R.id.id_dispositivoTU);

        //textView donde se muestra las respuesta de las consultas
        tvRtaPLCMMS=(TextView)vistaStgs.findViewById(R.id.txt_view_rta_setMMS);
        tvRtaPLCMMS.setText("");

        tvRtaPLCMC=(TextView)vistaStgs.findViewById(R.id.txt_view_rta_setMC);
        tvRtaPLCMC.setText("");

        tvRtaPLCTU=(TextView)vistaStgs.findViewById(R.id.txt_view_rta_setTU);
        tvRtaPLCTU.setText("");

        tvRtaClock= (TextView) vistaStgs.findViewById(R.id.txt_view_rta_clock);
        tvRtaPLCMMS.setText("");
        tvRtaPLCMC.setText("");

        //TabHost para las diferentes opciones a configurar
        th = (TabHost) vistaStgs.findViewById(R.id.tabHost);

        th.setup();
        TabHost.TabSpec ts1=th.newTabSpec("TabPlcMMS");
        ts1.setIndicator("PLC-MMS");
        ts1.setContent(R.id.tab1);
        th.addTab(ts1);

        th.setup();
        TabHost.TabSpec ts2=th.newTabSpec("TabPlcMc");
        ts2.setIndicator("PLC-MC");
        ts2.setContent(R.id.tab2);
        th.addTab(ts2);

        th.setup();
        TabHost.TabSpec ts3=th.newTabSpec("TabPlcTu");
        ts3.setIndicator("PLC-TU");
        ts3.setContent(R.id.tab3);
        th.addTab(ts3);


        //se recuperan los botones de las interfaces de Settings
        btnCheckSettingsMMS = (Button) vistaStgs.findViewById(R.id.btn_check_settings_mms);
        btnRecordMMS = (Button) vistaStgs.findViewById(R.id.btn_record_mms);
        btnCheckSettingsMC = (Button) vistaStgs.findViewById(R.id.btn_check_settings_mc);
        btnRecordMC = (Button) vistaStgs.findViewById(R.id.btn_record_mc);
        btnCheckSettingsTU = (Button) vistaStgs.findViewById(R.id.btn_check_settings_tu);
        btnRecordTU = (Button) vistaStgs.findViewById(R.id.btn_record_tu);

        btnCheckSettingsMMS.setOnClickListener(this);
        btnRecordMMS.setOnClickListener(this);
        btnCheckSettingsMC.setOnClickListener(this);
        btnRecordMC.setOnClickListener(this);
        btnCheckSettingsTU.setOnClickListener(this);
        btnRecordTU.setOnClickListener(this);



        flMore= (FloatingActionsMenu) vistaStgs.findViewById(R.id.fl_more);
        flPlcMms= (FloatingActionButton) vistaStgs.findViewById(R.id.fl_plc_mms);
        flPlcTu= (FloatingActionButton) vistaStgs.findViewById(R.id.fl_plc_tu);
        flPlcMc= (FloatingActionButton) vistaStgs.findViewById(R.id.fl_plc_mc);


        /*txtPlcMms= (TextView) vistaStgs.findViewById(R.id.txt_plc_mms);
        txtPlcTu= (TextView) vistaStgs.findViewById(R.id.txt_plc_tu);
        txtPlcMc= (TextView) vistaStgs.findViewById(R.id.txt_plc_mc);

        open = AnimationUtils.loadAnimation(vistaStgs.getContext(),R.anim.open);
        close = AnimationUtils.loadAnimation(vistaStgs.getContext(),R.anim.close);
        clock = AnimationUtils.loadAnimation(vistaStgs.getContext(),R.anim.rorate_clock);
        anticlock = AnimationUtils.loadAnimation(vistaStgs.getContext(),R.anim.rotate_anticlock);*/


        flPlcMms.setOnClickListener(this);
        flPlcTu.setOnClickListener(this);
        flPlcMc.setOnClickListener(this);



        //declaro todos los spinner

        //SPINNER PARA PLC-MMS

        //Spinner Ganancia de transmision
        txGainMMS = (Spinner) vistaStgs.findViewById(R.id.transmission_gain_spinner_mms);

        listGtx = new ArrayList<String>();
        listGtx.add("55 mVpp");
        listGtx.add("75 mVpp");
        listGtx.add("100 mVpp");
        listGtx.add("125 mVpp");
        listGtx.add("180 mVpp");
        listGtx.add("250 mVpp");
        listGtx.add("360 mVpp");
        listGtx.add("480 mVpp");
        listGtx.add("660 mVpp");
        listGtx.add("900 mVpp");
        listGtx.add("1.25 Vpp");
        listGtx.add("1.55 Vpp");
        listGtx.add("2.25 Vpp");
        listGtx.add("3.00 Vpp");
        listGtx.add("3.50 Vpp");
        ArrayAdapter<String> adaptador1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listGtx);
        adaptador1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txGainMMS.setAdapter(adaptador1);

        //Spinner Ganancia de recepcion
        rxGainMMS = (Spinner) vistaStgs.findViewById(R.id.reception_gain_spinner_mms);

        listGrx = new ArrayList<String>();
        listGrx.add("5 mVrms");
        listGrx.add("2.5 mVrms");
        listGrx.add("1.25 mVrms");
        listGrx.add("600 uVrms");
        listGrx.add("350 uVrms");
        listGrx.add("250 uVrms");
        listGrx.add("125 uVrms");
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listGrx);
        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rxGainMMS.setAdapter(adaptador2);

        //Spinner Retardo de transmision
        txDelayMMS = (Spinner) vistaStgs.findViewById(R.id.transmission_delay_spinner_mms);

        listDTx = new ArrayList<String>();
        listDTx.add("100 ms");
        listDTx.add("200 ms");
        listDTx.add("300 ms");
        listDTx.add("400 ms");
        listDTx.add("500 ms");
        ArrayAdapter<String> adaptador3 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listDTx);
        adaptador3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txDelayMMS.setAdapter(adaptador3);

        //Spinner Tasa de transmision
        txRateMMS = (Spinner) vistaStgs.findViewById(R.id.transmission_rate_spinner_mms);

        listRTx = new ArrayList<String>();
        listRTx.add("600 bps");
        listRTx.add("1200 bps");
        listRTx.add("2400 bps");
        ArrayAdapter<String> adaptador4 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listRTx);
        adaptador4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txRateMMS.setAdapter(adaptador4);

        //Spinner Hora de encuesta
        timeSurveyMMS = (Spinner) vistaStgs.findViewById(R.id.time_survey_spinner_mms);

        listTime = new ArrayList<String>();
        listTime.add("00");
        listTime.add("01");
        listTime.add("02");
        listTime.add("03");
        listTime.add("04");
        listTime.add("05");
        listTime.add("06");
        listTime.add("07");
        listTime.add("08");
        listTime.add("09");
        listTime.add("10");
        listTime.add("11");
        listTime.add("12");
        listTime.add("13");
        listTime.add("14");
        listTime.add("15");
        listTime.add("16");
        listTime.add("17");
        listTime.add("18");
        listTime.add("19");
        listTime.add("20");
        listTime.add("21");
        listTime.add("22");
        listTime.add("23");
        ArrayAdapter<String> adaptador5 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listTime);
        adaptador5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSurveyMMS.setAdapter(adaptador5);

        //aqui van todos los estados de los spinner!!!

        txGainMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionGainSpinnerMMS = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        rxGainMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                receptionGainSpinnerMMS = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        txDelayMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionDelaySpinnerMMS = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        txRateMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionRateSpinnerMMS = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        timeSurveyMMS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                timeSurveySpinnerMMS = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        //SPINNER PARA PLC-MC

        //Spinner Ganancia de transmision
        txGainMC = (Spinner) vistaStgs.findViewById(R.id.transmission_gain_spinner_mc);

        listGtx = new ArrayList<String>();
        listGtx.add("55 mVpp");
        listGtx.add("75 mVpp");
        listGtx.add("100 mVpp");
        listGtx.add("125 mVpp");
        listGtx.add("180 mVpp");
        listGtx.add("250 mVpp");
        listGtx.add("360 mVpp");
        listGtx.add("480 mVpp");
        listGtx.add("660 mVpp");
        listGtx.add("900 mVpp");
        listGtx.add("1.25 Vpp");
        listGtx.add("1.55 Vpp");
        listGtx.add("2.25 Vpp");
        listGtx.add("3.00 Vpp");
        listGtx.add("3.50 Vpp");
        ArrayAdapter<String> adaptador6 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listGtx);
        adaptador6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txGainMC.setAdapter(adaptador6);

        //Spinner Ganancia de recepcion
        rxGainMC = (Spinner) vistaStgs.findViewById(R.id.reception_gain_spinner_mc);

        listGrx = new ArrayList<String>();
        listGrx.add("5 mVrms");
        listGrx.add("2.5 mVrms");
        listGrx.add("1.25 mVrms");
        listGrx.add("600 uVrms");
        listGrx.add("350 uVrms");
        listGrx.add("250 uVrms");
        listGrx.add("125 uVrms");
        ArrayAdapter<String> adaptador7 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listGrx);
        adaptador7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rxGainMC.setAdapter(adaptador7);

        //Spinner Retardo de transmision
        txDelayMC = (Spinner) vistaStgs.findViewById(R.id.transmission_delay_spinner_mc);

        listDTx = new ArrayList<String>();
        listDTx.add("100 ms");
        listDTx.add("200 ms");
        listDTx.add("300 ms");
        listDTx.add("400 ms");
        listDTx.add("500 ms");
        ArrayAdapter<String> adaptador8 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listDTx);
        adaptador8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txDelayMC.setAdapter(adaptador8);

        //Spinner Tasa de transmision
        txRateMC = (Spinner) vistaStgs.findViewById(R.id.transmission_rate_spinner_mc);

        listRTx = new ArrayList<String>();
        listRTx.add("600 bps");
        listRTx.add("1200 bps");
        listRTx.add("2400 bps");
        ArrayAdapter<String> adaptador9 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listRTx);
        adaptador9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txRateMC.setAdapter(adaptador9);

        //aqui van todos los estados de los spinner!!!

        txGainMC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionGainSpinnerMC = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        rxGainMC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                receptionGainSpinnerMC = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        txDelayMC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionDelaySpinnerMC = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        txRateMC.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionRateSpinnerMC = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });




        //SPINNER PARA PLC-TU

        //Spinner Ganancia de transmision
        txGainTU = (Spinner) vistaStgs.findViewById(R.id.transmission_gain_spinner_tu);

        listGtx = new ArrayList<String>();
        listGtx.add("55 mVpp");
        listGtx.add("75 mVpp");
        listGtx.add("100 mVpp");
        listGtx.add("125 mVpp");
        listGtx.add("180 mVpp");
        listGtx.add("250 mVpp");
        listGtx.add("360 mVpp");
        listGtx.add("480 mVpp");
        listGtx.add("660 mVpp");
        listGtx.add("900 mVpp");
        listGtx.add("1.25 Vpp");
        listGtx.add("1.55 Vpp");
        listGtx.add("2.25 Vpp");
        listGtx.add("3.00 Vpp");
        listGtx.add("3.50 Vpp");
        ArrayAdapter<String> adaptador10 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listGtx);
        adaptador10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txGainTU.setAdapter(adaptador10);

        //Spinner Ganancia de recepcion
        rxGainTU = (Spinner) vistaStgs.findViewById(R.id.reception_gain_spinner_tu);

        listGrx = new ArrayList<String>();
        listGrx.add("5 mVrms");
        listGrx.add("2.5 mVrms");
        listGrx.add("1.25 mVrms");
        listGrx.add("600 uVrms");
        listGrx.add("350 uVrms");
        listGrx.add("250 uVrms");
        listGrx.add("125 uVrms");
        ArrayAdapter<String> adaptador11 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listGrx);
        adaptador11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rxGainTU.setAdapter(adaptador11);

        //Spinner Retardo de transmision
        txDelayTU = (Spinner) vistaStgs.findViewById(R.id.transmission_delay_spinner_tu);

        listDTx = new ArrayList<String>();
        listDTx.add("100 ms");
        listDTx.add("200 ms");
        listDTx.add("300 ms");
        listDTx.add("400 ms");
        listDTx.add("500 ms");
        ArrayAdapter<String> adaptador12 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listDTx);
        adaptador12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txDelayTU.setAdapter(adaptador12);

        //Spinner Tasa de transmision
        txRateTU = (Spinner) vistaStgs.findViewById(R.id.transmission_rate_spinner_tu);

        listRTx = new ArrayList<String>();
        listRTx.add("600 bps");
        listRTx.add("1200 bps");
        listRTx.add("2400 bps");
        ArrayAdapter<String> adaptador13 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, listRTx);
        adaptador13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txRateTU.setAdapter(adaptador13);


        //aqui van todos los estados de los spinner!!!

        txGainTU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionGainSpinnerTU = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        rxGainTU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                receptionGainSpinnerTU = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        txDelayTU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionDelaySpinnerTU = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        txRateTU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //Toast.makeText(arg0.getContext(), "Seleccionado: " + arg0.getItemAtPosition(arg2).toString(), Toast.LENGTH_SHORT).show();

                transmissionRateSpinnerTU = arg0.getItemAtPosition(arg2).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });



        return vistaStgs;
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
        tvRtaPLCMMS.setText(msg);
        tvRtaPLCMC.setText(msg);
        tvRtaPLCTU.setText(msg);

    }


    @Override
    public void onClick(View view) {

    /*    if (isOpen){

            flPlcMms.startAnimation(close);
            txtPlcMms.startAnimation(close);

            flPlcTu.startAnimation(close);
            txtPlcTu.startAnimation(close);

            flPlcMc.startAnimation(close);
            txtPlcMc.startAnimation(close);

            flMore.startAnimation(anticlock);

            flPlcMms.setClickable(false);
            txtPlcMms.setClickable(false);

            flPlcTu.setClickable(false);
            txtPlcTu.setClickable(false);

            flPlcMc.setClickable(false);
            txtPlcMc.setClickable(false);

            isOpen = false;
        }
        else {

            flPlcMms.startAnimation(open);
            txtPlcMms.startAnimation(open);

            flPlcTu.startAnimation(open);
            txtPlcTu.startAnimation(open);

            flPlcMc.startAnimation(open);
            txtPlcMc.startAnimation(open);

            flMore.startAnimation(clock);

            flPlcMms.setClickable(true);
            txtPlcMms.setClickable(false);

            flPlcTu.setClickable(true);
            txtPlcTu.setClickable(false);

            flPlcMc.setClickable(true);
            txtPlcMc.setClickable(false);

            isOpen = true;
        }
        */

        switch (view.getId()) {

            //caso de CHECK SETTINGS
            case R.id.btn_check_settings_mms:

                byte []frame2Send = new byte[7];

                frame2Send[0] = 0x24;// $
                frame2Send[1] = 0x40;// @
                frame2Send[2] = 0x07;// length
                frame2Send[3] = 0x07;// Tipo
                frame2Send[4] = 0x01;// Suponiendo 1 como origen PC
                frame2Send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame2Send[6] = calcularCRC(frame2Send);

                tvRtaPLCMMS.setText("");
                buff="";

                sendMessage(frame2Send);

                break;

            case R.id.btn_record_mms:
                // Capturo el contenido del editText donde van los ID
                idUsuarioMMS = edTxtIDMMS.getText().toString();
                idMacro = edTxtIDMacro.getText().toString();

                //Capturo el valor del spinner 'ganancia de transmision'
                String gananciaTransmisionElegida = transmissionGainSpinnerMMS;

                switch (gananciaTransmisionElegida){
                    case "55 mVpp":
                        gantxBytesMMS = 0x00;
                        break;

                    case "75 mVpp":
                        gantxBytesMMS = 0x01;
                        break;

                    case "100 mVpp":
                        gantxBytesMMS = 0x02;
                        break;

                    case "125 mVpp":
                        gantxBytesMMS = 0x03;
                        break;

                    case "180 mVpp":
                        gantxBytesMMS = 0x04;
                        break;

                    case "250 mVpp":
                        gantxBytesMMS = 0x05;
                        break;

                    case "360 mVpp":
                        gantxBytesMMS = 0x06;
                        break;

                    case "480 mVpp":
                        gantxBytesMMS = 0x07;
                        break;

                    case "660 mVpp":
                        gantxBytesMMS = 0x08;
                        break;

                    case "900 mVpp":
                        gantxBytesMMS = 0x09;
                        break;

                    case "1.25 Vpp":
                        gantxBytesMMS = 0x0A;
                        break;

                    case "1.55 Vpp":
                        gantxBytesMMS = 0x0B;
                        break;

                    case "2.25 Vpp":
                        gantxBytesMMS = 0x0C;
                        break;

                    case "3.00 Vpp":
                        gantxBytesMMS = 0x0D;
                        break;

                    case "3.50 Vpp":
                        gantxBytesMMS = 0x0E;
                        break;
                }

                //Capturo el valor del spinner 'ganancia de recepcion'
                String gananciaRecepcionElegida = receptionGainSpinnerMMS;

                switch (gananciaRecepcionElegida){
                    case "5 mVrms":
                        ganrxBytesMMS = 0x01;
                        break;

                    case "2.5 mVrms":
                        ganrxBytesMMS = 0x02;
                        break;

                    case "1.25 mVrms":
                        ganrxBytesMMS = 0x03;
                        break;

                    case "600 uVrms":
                        ganrxBytesMMS = 0x04;
                        break;

                    case "350 uVrms":
                        ganrxBytesMMS = 0x05;
                        break;

                    case "250 uVrms":
                        ganrxBytesMMS = 0x06;
                        break;

                    case "125 uVrms":
                        ganrxBytesMMS = 0x07;
                        break;
                }

                //Capturo el valor del spinner 'retardo de transmision'
                String retardoTransmisionElegida = transmissionDelaySpinnerMMS;

                switch (retardoTransmisionElegida){
                    case "100 ms":
                        delaytxBytesMMS = 0x01;
                        break;

                    case "200 ms":
                        delaytxBytesMMS = 0x02;
                        break;

                    case "300 ms":
                        delaytxBytesMMS = 0x03;
                        break;

                    case "400 ms":
                        delaytxBytesMMS = 0x04;
                        break;

                    case "500 ms":
                        delaytxBytesMMS = 0x05;
                        break;
                }

                //Capturo el valor del spinner 'tasa de transmision'
                String tasaTransmisionElegida = transmissionRateSpinnerMMS;

                switch (tasaTransmisionElegida){
                    case "600 bps":
                        ratetxBytesMMS = 0x00;
                        break;

                    case "1200 bps":
                        ratetxBytesMMS = 0x01;
                        break;

                    case "2400 bps":
                        ratetxBytesMMS = 0x03;
                        break;
                }


        //Capturo el valor del spinner 'hora de encuesta'
                int horaEncuestaSpinnerInt = Integer.parseInt(timeSurveySpinnerMMS);
                String horaenc = Integer.toHexString(horaEncuestaSpinnerInt);
                if (horaenc.length() == 1){horaenc = "0"+horaenc;}
                byte[] horaencBytes = hexStringToByteArray(horaenc);

                idUsuarioMMS = edTxtIDMMS.getText().toString();
                idMacro = edTxtIDMacro.getText().toString();

                if (idUsuarioMMS.length() == 8 && idMacro.length() == 12) {
                    byte[] idUsuarioBytes = hexStringToByteArray(idUsuarioMMS);
                    byte[] idMacroBytes = hexStringToByteArray(idMacro);

                    byte[] frame2send = new byte[23];


                    frame2send[0] = 0x24;// $
                    frame2send[1] = 0x40;// @
                    frame2send[2] = 0x17;// length
                    frame2send[3] = 0x11;// Tipo
                    frame2send[4] = 0x01;// Suponiendo 1 como origen PC
                    frame2send[5] = 0x02;// Suponiendo 2 como destino PLC
                    frame2send[6] = idUsuarioBytes[0];
                    frame2send[7] = idUsuarioBytes[1];
                    frame2send[8] = idUsuarioBytes[2];
                    frame2send[9] = idUsuarioBytes[3];
                    frame2send[10] = ratetxBytesMMS;//tasa de transmision
                    frame2send[11] = gantxBytesMMS;//ganancia de transmision
                    frame2send[12] = ganrxBytesMMS;//ganancia de recepcion
                    frame2send[13] = delaytxBytesMMS;//retardo de transmision
                    frame2send[14] = 0x08;//BIUThres
                    frame2send[15] = horaencBytes[0];//hora de encuesta
                    frame2send[16] = idMacroBytes[0];
                    frame2send[17] = idMacroBytes[1];
                    frame2send[18] = idMacroBytes[2];
                    frame2send[19] = idMacroBytes[3];
                    frame2send[20] = idMacroBytes[4];
                    frame2send[21] = idMacroBytes[5];
                    frame2send[22] = calcularCRC(frame2send);

                    sendMessage(frame2send);

                } else {
                    toastIngresarId();
                }

                break;

            case R.id.btn_check_settings_mc:

                byte []frame3Send = new byte[7];

                frame3Send[0] = 0x24;// $
                frame3Send[1] = 0x40;// @
                frame3Send[2] = 0x07;// length
                frame3Send[3] = 0x07;// Tipo
                frame3Send[4] = 0x01;// Suponiendo 1 como origen PC
                frame3Send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame3Send[6] = calcularCRC(frame3Send);

                tvRtaPLCMC.setText("");
                buff="";

                sendMessage(frame3Send);

                break;

            case R.id.btn_record_mc:

                //Capturo el valor del spinner 'ganancia de transmision'
                String gananciaTransmisionElegida2 = transmissionGainSpinnerMC;

                switch (gananciaTransmisionElegida2){
                    case "55 mVpp":
                        gantxBytesMC = 0x00;
                        break;

                    case "75 mVpp":
                        gantxBytesMC = 0x01;
                        break;

                    case "100 mVpp":
                        gantxBytesMC = 0x02;
                        break;

                    case "125 mVpp":
                        gantxBytesMC = 0x03;
                        break;

                    case "180 mVpp":
                        gantxBytesMC = 0x04;
                        break;

                    case "250 mVpp":
                        gantxBytesMC = 0x05;
                        break;

                    case "360 mVpp":
                        gantxBytesMC = 0x06;
                        break;

                    case "480 mVpp":
                        gantxBytesMC = 0x07;
                        break;

                    case "660 mVpp":
                        gantxBytesMC = 0x08;
                        break;

                    case "900 mVpp":
                        gantxBytesMC = 0x09;
                        break;

                    case "1.25 Vpp":
                        gantxBytesMC = 0x0A;
                        break;

                    case "1.55 Vpp":
                        gantxBytesMC = 0x0B;
                        break;

                    case "2.25 Vpp":
                        gantxBytesMC = 0x0C;
                        break;

                    case "3.00 Vpp":
                        gantxBytesMC = 0x0D;
                        break;

                    case "3.50 Vpp":
                        gantxBytesMC = 0x0E;
                        break;
                }

                //Capturo el valor del spinner 'ganancia de recepcion'
                String gananciaRecepcionElegida2 = receptionGainSpinnerMC;

                switch (gananciaRecepcionElegida2){
                    case "5 mVrms":
                        ganrxBytesMC = 0x01;
                        break;

                    case "2.5 mVrms":
                        ganrxBytesMC = 0x02;
                        break;

                    case "1.25 mVrms":
                        ganrxBytesMC = 0x03;
                        break;

                    case "600 uVrms":
                        ganrxBytesMC = 0x04;
                        break;

                    case "350 uVrms":
                        ganrxBytesMC = 0x05;
                        break;

                    case "250 uVrms":
                        ganrxBytesMC = 0x06;
                        break;

                    case "125 uVrms":
                        ganrxBytesMC = 0x07;
                        break;
                }

                //Capturo el valor del spinner 'retardo de transmision'
                String retardoTransmisionElegida2 = transmissionDelaySpinnerMC;

                switch (retardoTransmisionElegida2){
                    case "100 ms":
                        delaytxBytesMC = 0x01;
                        break;

                    case "200 ms":
                        delaytxBytesMC = 0x02;
                        break;

                    case "300 ms":
                        delaytxBytesMC = 0x03;
                        break;

                    case "400 ms":
                        delaytxBytesMC = 0x04;
                        break;

                    case "500 ms":
                        delaytxBytesMC = 0x05;
                        break;
                }

                //Capturo el valor del spinner 'tasa de transmision'
                String tasaTransmisionElegida2 = transmissionRateSpinnerMC;

                switch (tasaTransmisionElegida2){
                    case "600 bps":
                        ratetxBytesMC = 0x00;
                        break;

                    case "1200 bps":
                        ratetxBytesMC = 0x01;
                        break;

                    case "2400 bps":
                        ratetxBytesMC = 0x03;
                        break;
                }

                byte[] frame2send = new byte[11];


                    frame2send[0] = 0x24;// $
                    frame2send[1] = 0x40;// @
                    frame2send[2] = 0x0B;// length
                    frame2send[3] = 0x18;// Tipo
                    frame2send[4] = 0x01;// Suponiendo 1 como origen PC
                    frame2send[5] = 0x02;// Suponiendo 2 como destino PLC
                    frame2send[6] = ratetxBytesMC;//tasa de transmision
                    frame2send[7] = gantxBytesMC;//ganancia de transmision
                    frame2send[8] = ganrxBytesMC;//ganancia de recepcion
                    frame2send[9] = delaytxBytesMC;//retardo de transmision
                    frame2send[10] = calcularCRC(frame2send);

                    sendMessage(frame2send);

                break;

            case R.id.btn_check_settings_tu:

                byte []frame4Send = new byte[7];

                frame4Send[0] = 0x24;// $
                frame4Send[1] = 0x40;// @
                frame4Send[2] = 0x07;// length
                frame4Send[3] = 0x07;// Tipo
                frame4Send[4] = 0x01;// Suponiendo 1 como origen PC
                frame4Send[5] = 0x02;// Suponiendo 2 como destino PLC
                frame4Send[6] = calcularCRC(frame4Send);

                tvRtaPLCTU.setText("");
                buff="";

                sendMessage(frame4Send);

                break;

            case R.id.btn_record_tu:

                //Capturo el valor del spinner 'ganancia de transmision'
                String gananciaTransmisionElegida3 = transmissionGainSpinnerTU;

                switch (gananciaTransmisionElegida3){
                    case "55 mVpp":
                        gantxBytesTU = 0x00;
                        break;

                    case "75 mVpp":
                        gantxBytesTU = 0x01;
                        break;

                    case "100 mVpp":
                        gantxBytesTU = 0x02;
                        break;

                    case "125 mVpp":
                        gantxBytesTU = 0x03;
                        break;

                    case "180 mVpp":
                        gantxBytesTU = 0x04;
                        break;

                    case "250 mVpp":
                        gantxBytesTU = 0x05;
                        break;

                    case "360 mVpp":
                        gantxBytesTU = 0x06;
                        break;

                    case "480 mVpp":
                        gantxBytesTU = 0x07;
                        break;

                    case "660 mVpp":
                        gantxBytesTU = 0x08;
                        break;

                    case "900 mVpp":
                        gantxBytesTU = 0x09;
                        break;

                    case "1.25 Vpp":
                        gantxBytesTU = 0x0A;
                        break;

                    case "1.55 Vpp":
                        gantxBytesTU = 0x0B;
                        break;

                    case "2.25 Vpp":
                        gantxBytesTU = 0x0C;
                        break;

                    case "3.00 Vpp":
                        gantxBytesTU = 0x0D;
                        break;

                    case "3.50 Vpp":
                        gantxBytesTU = 0x0E;
                        break;
                }

                //Capturo el valor del spinner 'ganancia de recepcion'
                String gananciaRecepcionElegida3 = receptionGainSpinnerTU;

                switch (gananciaRecepcionElegida3){
                    case "5 mVrms":
                        ganrxBytesTU = 0x01;
                        break;

                    case "2.5 mVrms":
                        ganrxBytesTU = 0x02;
                        break;

                    case "1.25 mVrms":
                        ganrxBytesTU = 0x03;
                        break;

                    case "600 uVrms":
                        ganrxBytesTU = 0x04;
                        break;

                    case "350 uVrms":
                        ganrxBytesTU = 0x05;
                        break;

                    case "250 uVrms":
                        ganrxBytesTU = 0x06;
                        break;

                    case "125 uVrms":
                        ganrxBytesTU = 0x07;
                        break;
                }

                //Capturo el valor del spinner 'retardo de transmision'
                String retardoTransmisionElegida3 = transmissionDelaySpinnerTU;

                switch (retardoTransmisionElegida3){
                    case "100 ms":
                        delaytxBytesTU = 0x01;
                        break;

                    case "200 ms":
                        delaytxBytesTU = 0x02;
                        break;

                    case "300 ms":
                        delaytxBytesTU = 0x03;
                        break;

                    case "400 ms":
                        delaytxBytesTU = 0x04;
                        break;

                    case "500 ms":
                        delaytxBytesTU = 0x05;
                        break;
                }

                //Capturo el valor del spinner 'tasa de transmision'
                String tasaTransmisionElegida3 = transmissionRateSpinnerTU;

                switch (tasaTransmisionElegida3){
                    case "600 bps":
                        ratetxBytesTU = 0x00;
                        break;

                    case "1200 bps":
                        ratetxBytesTU = 0x01;
                        break;

                    case "2400 bps":
                        ratetxBytesTU = 0x03;
                        break;
                }

                byte[] frame3send = new byte[11];


                    frame3send[0] = 0x24;// $
                    frame3send[1] = 0x40;// @
                    frame3send[2] = 0x0B;// length
                    frame3send[3] = 0x12;// Tipo
                    frame3send[4] = 0x01;// Suponiendo 1 como origen PC
                    frame3send[5] = 0x02;// Suponiendo 2 como destino PLC
                    frame3send[6] = ratetxBytesTU;
                    frame3send[7] = gantxBytesTU;
                    frame3send[8] = ganrxBytesTU;
                    frame3send[9] = delaytxBytesTU;
                    frame3send[10] = calcularCRC(frame3send);//tasa de transmision

                    sendMessage(frame3send);

                break;

            case R.id.fl_plc_mms:
                flMore.collapse();

                frame2Send = new byte[7];

                frame2Send[0] = 0x24;
                frame2Send[1] = 0x40;
                frame2Send[2] = 0x07;
                frame2Send[3] = 0x02;
                frame2Send[4] = 0x01;
                frame2Send[5] = 0x02;
                frame2Send[6] = calcularCRC(frame2Send);

                tvRtaPLCMMS.setText("");
                buff="";

                sendMessage(frame2Send);

                break;
            case R.id.fl_plc_tu:
                flMore.collapse();
                frame2Send = new byte[7];

                frame2Send[0] = 0x24;
                frame2Send[1] = 0x40;
                frame2Send[2] = 0x07;
                frame2Send[3] = 0x13;
                frame2Send[4] = 0x01;
                frame2Send[5] = 0x02;
                frame2Send[6] = calcularCRC(frame2Send);

                tvRtaPLCMMS.setText("");
                buff="";

                sendMessage(frame2Send);
                break;

            case R.id.fl_plc_mc:
                flMore.collapse();

                Date horaActual=new Date();

                String fecha=(horaActual.getYear()+1900)+""+(horaActual.getMonth()+1)+""+horaActual.getDate()+""+horaActual.getHours()+""+horaActual.getMinutes()+""+horaActual.getSeconds();

                writeFile("atemsaa"+fecha+".csv",buff);
                tvRtaPLCMMS.setText("");

                buff="";

                break;

        }
    }










}
