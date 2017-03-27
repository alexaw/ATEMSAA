package com.example.yogis.atemsaa_fragments.fragments;

/**
 * Created by yogis on 22/08/2016.
 */
public interface OnChangeFragment {

    int MENU = 0;

    //MenuFragment
    int USERS = 1;
    int SETTINGS = 2;
    int REPORTS = 3;
    int METERS = 4;
    int DATABASE = 5;

    //SettingsFragment
    int PLCMMS = 6;
    int PLCMC = 7;
    int CLOCK = 8;
    int GPRS = 9;

    //ReportsFragment
    int PARCIAL = 10;

    //DataBaseFragment
    int PLCMMSBD = 16;
    int CLIENTEBD = 17;
    int PLCMCBD = 20;
    int PLCTUBD = 21;
    int MACROBD = 18;
    int MEDIDORBD = 19;
    int PRODUCTOBD = 22;
    int TRAFOBD = 23;

    int PLCTU = 24;
    int RTC = 25;


    void onChange(int fragment);

}
