package com.example.yogis.atemsaa_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.Login.LoginActivity;
import com.example.yogis.atemsaa_fragments.fragments.ClockSettingsFragment;
import com.example.yogis.atemsaa_fragments.fragments.GprsSettingsFragment;
import com.example.yogis.atemsaa_fragments.fragments.ListUserFragment;
import com.example.yogis.atemsaa_fragments.fragments.MenuFragment;
import com.example.yogis.atemsaa_fragments.fragments.NewSettingsFragment;
import com.example.yogis.atemsaa_fragments.fragments.NewUserFragment;
import com.example.yogis.atemsaa_fragments.fragments.OnChangeFragment;
import com.example.yogis.atemsaa_fragments.fragments.PlcMmsSettingsFragment;
import com.example.yogis.atemsaa_fragments.fragments.PlcTuSettingsFragment;
import com.example.yogis.atemsaa_fragments.fragments.RegisterUserFragment;
import com.example.yogis.atemsaa_fragments.fragments.SearchUserFragment;
import com.example.yogis.atemsaa_fragments.fragments.SettingsFragment;
import com.example.yogis.atemsaa_fragments.fragments.TestFrameFragment;
import com.example.yogis.atemsaa_fragments.fragments.ToolBarFragment;
import com.example.yogis.atemsaa_fragments.fragments.UserFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity implements OnChangeFragment {

    static int band=0;

    public static Context appContext;
    private boolean bol = false;

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothCommandService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    public static final String strInput = "toast";

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for Bluetooth Command Service
    public static BluetoothCommandService mCommandService = null;

    private MenuItem menuItemConnectDisconnect;

    //private FrameManager frameManager;
    private boolean flagConnectDisconnect = false;

    public boolean sdDisponible = false;
    public boolean sdAccesoEscritura = false;

    //se inicializan todos los objetos
    Button btnUsers;
    TextView tv_rta_user;

    //se inicializan todos los objetos
    String buff = "";
    byte[] readBuf;

    int currentFragment;
    MenuFragment menu;
    UserFragment user;
    SettingsFragment settings;
    SearchUserFragment search;
    ListUserFragment list;
    RegisterUserFragment register;
    TestFrameFragment test;
    PlcMmsSettingsFragment plcmms;
    PlcTuSettingsFragment plctu;

    ClockSettingsFragment fclock;
    GprsSettingsFragment fgprs;

    NewUserFragment newUser;
    NewSettingsFragment newSettings;
    ToolBarFragment hola;



    ActionBar myToolbar;

    private static final int MENUPPAL = 0;
    private static final int MENUSEARCH = 1;
    private static final int MENUCLOCKGPRS = 2;
    private static final int MENUGPRS = 3;

    //private static final int HIDE = 2;
    Menu menuActionBar;
    int state;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        appContext = getApplicationContext();

        myToolbar = getSupportActionBar();

        menu = new MenuFragment();
        settings = new SettingsFragment();
        user = new UserFragment();
        search = new SearchUserFragment();
        list = new ListUserFragment();
        register = new RegisterUserFragment();
        test = new TestFrameFragment();
        plcmms = new PlcMmsSettingsFragment();
        plctu = new PlcTuSettingsFragment();

        fclock = new ClockSettingsFragment();
        fgprs = new GprsSettingsFragment();

        newUser = new NewUserFragment();
        newSettings = new NewSettingsFragment();

        hola = new ToolBarFragment();

        currentFragment = MENU;
        putFragment(menu, MENU);

        // Get local Bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.bt_not_available, Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // If BT is not on, request that it be enabled.
        // setupCommand() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        // otherwise set up the command service
        else {
            if (mCommandService == null)
                setupCommand();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mCommandService != null) {
            if (mCommandService.getState() == BluetoothCommandService.STATE_NONE) {
                mCommandService.start();
            }
        }
    }

    public void setupCommand() {
        // Initialize the BluetoothChatService to perform bluetooth connections
        mCommandService = new BluetoothCommandService(this, mHandler);
    }

    @Override
    protected void onDestroy() {

        if (mCommandService != null) {
            mCommandService.stop();
            mCommandService = null;
        }
        super.onDestroy();
    }

    public void sendMessage(byte[] message) {

        // Check that we're actually connected before trying anything
        if (mCommandService.getState() != BluetoothCommandService.STATE_CONNECTED) {
            Toast.makeText(this, getString(R.string.txt_not_connect_yet), Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length > 0) {

            mCommandService.write(message);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        this.menuActionBar = menu;
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();


        if(state == MENUPPAL){
            menu.clear();
            inflater.inflate(R.menu.menu_main,menu);
           // menu.clear();
        }else if (state == MENUSEARCH)
        {
            menu.clear();
            inflater.inflate(R.menu.menu_main,menu);
            inflater.inflate(R.menu.menu_main_search, menu);
       }else if (state == MENUCLOCKGPRS)
        {
            menu.clear();
            inflater.inflate(R.menu.menu_main,menu);
            inflater.inflate(R.menu.menu_main_settings, menu);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.connect_disconnect:
                // Launch the ScanDevicesActivity to see devices and do scan
                menuItemConnectDisconnect = item;

                if (flagConnectDisconnect) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.txt_caution)
                            .setMessage(getString(R.string.txt_sure_interrupt) + " " + mConnectedDeviceName + "?")
                            .setNegativeButton(R.string.txt_no_off, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {
                                    // no se ejectua acci�n - cierre automatico de AlertDialog
                                }
                            })
                            .setPositiveButton(R.string.txt_si_off, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dlg, int sumthin) {

                                    if (mCommandService != null) {

                                        menuItemConnectDisconnect.setActionView(R.layout.progress_bar);
                                        //String strTemp = "Bluetooth Connection Lost...";
                                        //mCommandService.write(strTemp.getBytes());
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        mCommandService.stop();
                                    }

                                }
                            })
                            .show();

                } else {

                    Intent serverIntent = new Intent(this, DeviceListActivity.class);
                    startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
                }
                return true;

            case R.id.btn_sign_off:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();

                return true;

            case R.id.action_search_user:

               newUser.getSearchUser();
                return true;

            case R.id.action_settings_clock:

                onChange(CLOCK);
                return true;

            case R.id.action_settings_gprs:

                onChange(GPRS);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private final void setStatus(int resId) {
        myToolbar.setSubtitle(resId);
    }

    private final void setStatus(CharSequence subTitle) {
        myToolbar.setSubtitle(subTitle);
    }

    // The Handler that gets information back from the BluetoothChatService
    private  Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_STATE_CHANGE:
                        switch (msg.arg1) {
                            case BluetoothCommandService.STATE_CONNECTED:

                                //actionBar.setSubtitle(getString(R.string.title_connected_to) + " " + mConnectedDeviceName);

                                //le coloco el nombre en el actionBar
                                setStatus(getString(R.string.title_connected_to) + " " + mConnectedDeviceName);
                                menuItemConnectDisconnect.setActionView(null);
                                //menuItemConnectDisconnect.setIcon(R.drawable.ic_bluetooth_on);
                                menuItemConnectDisconnect.setTitle(R.string.title_disconnect);

                                flagConnectDisconnect = true;



                                //se env�a la confirmacion de que se estableci�n la conexion bt
                                //String strTemp1 = "Bluetooth Connection Enabled...";
                                //mCommandService.write(strTemp1.getBytes());

                                break;

                            case BluetoothCommandService.STATE_CONNECTING:

                                //actionBar.setSubtitle(getString(R.string.title_connecting));
                                setStatus(R.string.title_connecting);

                                break;

                            case BluetoothCommandService.STATE_LISTEN:

                                setStatus(R.string.title_not_connected);

                                break;

                            case BluetoothCommandService.STATE_NONE:

                                //actionBar.setSubtitle(getString(R.string.title_not_connected));
                                setStatus(R.string.title_not_connected);
                                flagConnectDisconnect = false;

                                if (menuItemConnectDisconnect != null) {
                                    menuItemConnectDisconnect.setActionView(null);
                                    //menuItemConnectDisconnect.setIcon(R.drawable.ic_bluetooth_off);
                                    menuItemConnectDisconnect.setTitle(R.string.title_connect);
                                }

                                break;
                        }

                        break;

                    case MESSAGE_DEVICE_NAME:
                        // save the connected device's name
                        mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                        Toast.makeText(getApplicationContext(), getString(R.string.title_connected_to)
                                + " " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                        break;

                    case MESSAGE_TOAST:
                        Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                                Toast.LENGTH_SHORT).show();
                        break;

                    case MESSAGE_READ:

                        // SE RECIBEN TODOS LOS DATOS DESDE EL MODULO.
                        readBuf = (byte[]) msg.obj;
                        // construct a string from the valid bytes in the buffer
                        String readMessage = new String(readBuf);
                        buff = buff + readMessage;

                        //LLAMA LAS RESPUESTAS DE LOS TEXTVIEW
                        answerTextView();
                        break;
                }
            }
        };

    private void answerTextView() {
        switch (band){
            case 1:
                newUser.setMsg(buff);
                break;
            case 2:
                newSettings.setMsg(buff);
                break;
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras()
                            .getString(ScanDevicesActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mCommandService.connect(device);
                    //Activate Progress Bar
                    menuItemConnectDisconnect.setActionView(R.layout.progress_bar);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupCommand();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    public void onChange(int fragment) {
        switch (fragment){
            case MENU :
                putFragment(menu, fragment);
                break;
            case USER :

                putFragment(newUser, fragment);
                band = 1;
                showMenuSearch();
                break;
            case SETTINGS :
                putFragment(newSettings, fragment);
                band = 2;
                showMenuSettings();
                break;
            case REPORT:
                putFragment(newSettings, fragment);
                showMenuSearch();
                break;

            case SEARCH :
                putFragment(search, fragment);
                break;
            case LIST :
                putFragment(list, fragment);
                break;
            case REGISTER :
                putFragment(register, fragment);
                break;
            case TEST :
                putFragment(test, fragment);
                break;

            case PLCMMS :
                putFragment(plcmms, fragment);
                break;
            case PLCTU :
                putFragment(plctu, fragment);
                break;
            case PLCMC :
                putFragment(plcmms, fragment);
                break;
            case CLOCK :
                putFragment(fclock, fragment);
                break;
            case GPRS :
                putFragment(fgprs, fragment);
                break;
            case HOLA :
                putFragment(hola, fragment);
                break;
        }
    }

    @Override
    public void onBackPressed() {

        switch (currentFragment){
            case MENU :
                super.onBackPressed();
                break;
            case USER:
                putFragment(menu, MENU);
                showMenuPpal();
                break;
            case SETTINGS:
                putFragment(menu, MENU);
                showMenuPpal();
                break;
            case REPORT:
                putFragment(menu, MENU);
                showMenuSearch();
                break;


            case SEARCH:
                putFragment(newUser, NEWUSER);
                break;
            case LIST:
                putFragment(user, USER);
                break;
            case REGISTER:
                putFragment(newUser, NEWUSER);
                break;
            case TEST:
                putFragment(user, USER);
                break;

            case PLCMMS:
                putFragment(settings, SETTINGS);
                break;
            case PLCTU:
                putFragment(settings, SETTINGS);
                break;
            case PLCMC:
                putFragment(settings, SETTINGS);
                break;
            case CLOCK:
                putFragment(newSettings, SETTINGS);
                break;
            case GPRS:
                putFragment(newSettings, SETTINGS);
                break;
            case HOLA:
                putFragment(settings, NEWUSER);
                break;

        }
    }

    private void putFragment(Fragment fragment, int fragmentType){
        currentFragment = fragmentType;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_menu, fragment);
        ft.commit();
    }

    private void showMenuSearch() {
        state = MENUSEARCH;
        onPrepareOptionsMenu(menuActionBar);
    }

    private void showMenuPpal() {
        state = MENUPPAL;
        onPrepareOptionsMenu(menuActionBar);
    }

    private void showMenuSettings() {
        state = MENUCLOCKGPRS;
        onPrepareOptionsMenu(menuActionBar);
    }


    public void writeMessage(byte[] msg){
        buff = "";
        mCommandService.write(msg);
    }


}
