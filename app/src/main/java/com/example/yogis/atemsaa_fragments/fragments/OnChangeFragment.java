package com.example.yogis.atemsaa_fragments.fragments;

/**
 * Created by yogis on 22/08/2016.
 */
public interface OnChangeFragment {

    int MENU = 0;

    //MenuFragment
    int USER = 1;
    int SETTINGS = 2;
    int REPORT = 3;

    //UserFragment
    int SEARCH = 4;
    int LIST = 5;
    int REGISTER = 6;
    int TEST = 7;

    //SettingsFragment
    int PLCMMS = 8;
    int PLCTU = 9;
    int PLCMC = 10;
    int CLOCK = 11;
    int RF = 12;
    int GPRS = 13;

    int HOLA = 14;

    int NEWUSER = 15;
    int NEWSETTINGS = 16;



    void onChange(int fragment);

}
