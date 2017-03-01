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


    //UserFragment

    int METERS = 26;

    //SettingsFragment
    int PLCMMS = 8;

    int PLCMC = 10;
    int CLOCK = 11;
    int GPRS = 12;




    int DATABASE = 15;
    int PLCMMSBD = 16;
    int CLIENTEBD = 17;
    int MACROBD = 18;
    int MEDIDORBD = 19;
    int PLCMCBD = 20;
    int PLCTUBD = 21;
    int PRODUCTOBD = 22;
    int TRAFOBD = 23;


    int PARCIAL=25;


    void onChange(int fragment);

}
