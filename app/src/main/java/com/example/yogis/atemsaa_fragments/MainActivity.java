package com.example.yogis.atemsaa_fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.Login.LoginActivity;
import com.example.yogis.atemsaa_fragments.fragments.MacroBDFragment;
import com.example.yogis.atemsaa_fragments.fragments.MedidorBDFragment;
import com.example.yogis.atemsaa_fragments.fragments.MeterFragment;
import com.example.yogis.atemsaa_fragments.fragments.PlcMcBDFragment;
import com.example.yogis.atemsaa_fragments.fragments.PlcMmsBDFragment;
import com.example.yogis.atemsaa_fragments.fragments.ClientBDFragment;
import com.example.yogis.atemsaa_fragments.fragments.ClockSettingsFragment;
import com.example.yogis.atemsaa_fragments.fragments.GprsSettingsFragment;
import com.example.yogis.atemsaa_fragments.fragments.MenuBaseDatosFragment;
import com.example.yogis.atemsaa_fragments.fragments.MenuFragment;
import com.example.yogis.atemsaa_fragments.fragments.RTCFragment;
import com.example.yogis.atemsaa_fragments.fragments.ReportsFragment;
import com.example.yogis.atemsaa_fragments.fragments.SettingsFragment;
import com.example.yogis.atemsaa_fragments.fragments.TerminalUFragment;
import com.example.yogis.atemsaa_fragments.fragments.UserFragment;
import com.example.yogis.atemsaa_fragments.fragments.OnChangeFragment;
import com.example.yogis.atemsaa_fragments.fragments.PlcMmsSettingsFragment;
import com.example.yogis.atemsaa_fragments.fragments.PlcTuBDFragment;
import com.example.yogis.atemsaa_fragments.fragments.ProductBDFragment;
import com.example.yogis.atemsaa_fragments.fragments.TrafoBDFragment;


public class MainActivity extends AppCompatActivity implements OnChangeFragment, ClockSettingsFragment.DateSelectedListener {

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

    private Menu  optMenu;

    //private FrameManager frameManager;
    private static boolean flagConnectDisconnect = false;

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
    PlcMmsSettingsFragment plcmms;

    ClockSettingsFragment fclock;
    GprsSettingsFragment fgprs;

    UserFragment newUser;
    SettingsFragment newSettings;
    ReportsFragment newReports;
    MeterFragment meters;

    MenuBaseDatosFragment menuBD;
    ClientBDFragment clientBD;
    PlcMmsBDFragment plcMmsBD;
    MacroBDFragment macroBD;
    MedidorBDFragment medidorBD;
    PlcMcBDFragment plcMcBD;
    PlcTuBDFragment plcTuBD;
    ProductBDFragment productoBD;
    TrafoBDFragment trafoBD;
    TerminalUFragment terminal;
    RTCFragment rtc;

    ActionBar myToolbar;

    private static final int MENUPPAL = 0;
    private static final int MENUSEARCH = 1;
    private static final int MENUCLOCKGPRS = 2;
    private static final int MENUGPRS = 3;

    //private static final int HIDE = 2;
    Menu menuActionBar;
    int state;

    TextView  campoFecha;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        campoFecha = (TextView) findViewById(R.id.editFecha);


        appContext = getApplicationContext();

        myToolbar = getSupportActionBar();

        menu = new MenuFragment();
        plcmms = new PlcMmsSettingsFragment();


        fclock = new ClockSettingsFragment();
        fgprs = new GprsSettingsFragment();

        newUser = new UserFragment();
        newSettings = new SettingsFragment();
        newReports = new ReportsFragment();
        meters = new MeterFragment();

        menuBD = new MenuBaseDatosFragment();
        clientBD = new ClientBDFragment();
        plcMmsBD = new PlcMmsBDFragment();
        macroBD = new MacroBDFragment();
        medidorBD = new MedidorBDFragment();
        plcMcBD = new PlcMcBDFragment();
        plcTuBD = new PlcTuBDFragment();
        productoBD = new ProductBDFragment();
        trafoBD = new TrafoBDFragment();
        terminal = new TerminalUFragment();
        rtc = new RTCFragment();

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
        optMenu = menu;

        if(state == MENUPPAL){
            menu.clear();
            if(flagConnectDisconnect)
                inflater.inflate(R.menu.menu_main_off,menu);
            else
                inflater.inflate(R.menu.menu_main, menu);
           // menu.clear();
        }else if (state == MENUSEARCH)
        {
            menu.clear();
            if(flagConnectDisconnect){
                inflater.inflate(R.menu.menu_main_off,menu);
                inflater.inflate(R.menu.menu_main_search, menu);}
            else{
                inflater.inflate(R.menu.menu_main, menu);
                inflater.inflate(R.menu.menu_main_search, menu);}
       }else if (state == MENUCLOCKGPRS)
        {
            menu.clear();
            if(flagConnectDisconnect){
                inflater.inflate(R.menu.menu_main_off,menu);
                inflater.inflate(R.menu.menu_main_settings, menu);}
            else{
                inflater.inflate(R.menu.menu_main, menu);
                inflater.inflate(R.menu.menu_main_settings, menu);}
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.connect_disconnect:
                // Launch the DevicesActivity to see devices and do scan
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

                ClockSettingsFragment.showDateDialog(this, this);

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

                                //menuItemConnectDisconnect.setIcon(R.drawable.ic_bluetooth_on);


                                flagConnectDisconnect = true;

                                onPrepareOptionsMenu(optMenu);

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

                                onPrepareOptionsMenu(optMenu);
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
            case 3:
                newReports.setMsg(buff);
                break;
            case 4:
                meters.setMsg(buff);
                break;
            case 5:
                terminal.setMsg(buff);
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
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mCommandService.connect(device);
                    //Activate Progress Bar

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
            case USERS :
                putFragment(newUser, fragment);
                band = 1;
                showMenuSearch();
                break;
            case SETTINGS :
                putFragment(newSettings, fragment);
                band = 2;
                showMenuSettings();
                break;
            case REPORTS:
                putFragment(newReports, fragment);
                band = 3;
                break;
            case METERS:
                putFragment(meters, fragment);
                band = 4;
                break;
            case PLCTU:
                putFragment(terminal, fragment);
                band = 5;
                break;
            case RTC:
                putFragment(rtc, fragment);
                break;
            case DATABASE:
                putFragment(menuBD, fragment);
                break;



            case PLCMMS :
                putFragment(plcmms, fragment);
                break;

            case PLCMC :
                putFragment(plcmms, fragment);
                break;
            case CLOCK :
                putFragment(fclock, fragment);
                showMenuPpal();
                break;
            case GPRS :
                putFragment(fgprs, fragment);
                showMenuPpal();
                break;

            case PLCMMSBD :
                putFragment(plcMmsBD, fragment);
                break;
            case CLIENTEBD :
                putFragment(clientBD, fragment);
                break;
            case MACROBD :
                putFragment(macroBD, fragment);
                break;
            case MEDIDORBD :
                putFragment(medidorBD, fragment);
                break;
            case PLCMCBD :
                putFragment(plcMcBD, fragment);
                break;
            case PLCTUBD :
                putFragment(plcTuBD, fragment);
                break;
            case PRODUCTOBD :
                putFragment(productoBD, fragment);
                break;
            case TRAFOBD :
                putFragment(trafoBD, fragment);
                break;
        }
    }

    @Override
    public void onBackPressed() {

        switch (currentFragment){
            case MENU :
                super.onBackPressed();
                break;
            case USERS:
                putFragment(menu, MENU);
                showMenuPpal();
                break;
            case SETTINGS:
                putFragment(menu, MENU);
                showMenuPpal();
                break;
            case REPORTS:
                putFragment(menu, MENU);
                showMenuPpal();
                break;
            case METERS:
                putFragment(menu, MENU);
                showMenuPpal();
                break;
            case PLCTU:
                putFragment(menu, MENU);
                showMenuPpal();
            case RTC:
                putFragment(menu, MENU);
                showMenuPpal();
                break;
            case DATABASE:
                putFragment(menu, MENU);
                showMenuPpal();
                break;
            case CLOCK:
                putFragment(newSettings, SETTINGS);
                showMenuSettings();
                break;
            case GPRS:
                putFragment(newSettings, SETTINGS);
                showMenuSettings();
                break;
            case PLCMMSBD:
                putFragment(menuBD, DATABASE);
                break;
            case CLIENTEBD:
                putFragment(menuBD, DATABASE);
                break;
            case MACROBD:
                putFragment(menuBD, DATABASE);
                break;
            case MEDIDORBD:
                putFragment(menuBD, DATABASE);
                break;
            case PLCMCBD:
                putFragment(menuBD, DATABASE);
                break;
            case PLCTUBD:
                putFragment(menuBD, DATABASE);
                break;
            case PRODUCTOBD:
                putFragment(menuBD, DATABASE);
                break;
            case TRAFOBD:
                putFragment(menuBD, DATABASE);
                break;
            case PARCIAL:
                putFragment(newReports, REPORTS);
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

    public void mostrarCalendario(View control){

        ClockSettingsFragment.showDateDialog(this, this);

    }

    @Override
    public void onDateSelected(String date, int year, int month, int day) {
        campoFecha.setText(date);
    }


}
