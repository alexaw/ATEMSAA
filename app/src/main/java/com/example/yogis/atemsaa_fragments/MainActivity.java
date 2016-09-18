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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


public class MainActivity extends AppCompatActivity implements OnChangeFragment {

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
    NewUserFragment newUser;
    NewSettingsFragment newSettings;


    ToolBarFragment hola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appContext = getApplicationContext();

        menu = new MenuFragment();
        settings = new SettingsFragment();
        user = new UserFragment();
        search = new SearchUserFragment();
        list = new ListUserFragment();
        register = new RegisterUserFragment();
        test = new TestFrameFragment();
        plcmms = new PlcMmsSettingsFragment();
        plctu = new PlcTuSettingsFragment();
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
        super.onDestroy();

        if (mCommandService != null)
            mCommandService.stop();
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
        return true;
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private final void setStatus(int resId) {
        final ActionBar myToolbar = getSupportActionBar();
        myToolbar.setSubtitle(resId);
    }

    private final void setStatus(CharSequence subTitle) {
        final ActionBar myToolbar = getSupportActionBar();
        myToolbar.setSubtitle(subTitle);

    }

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler;
    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_STATE_CHANGE:
                        switch (msg.arg1) {
                            case BluetoothCommandService.STATE_CONNECTED:

                                //actionBar.setSubtitle(getString(R.string.title_connected_to) + " " + mConnectedDeviceName);
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

                        ///Ahora esto!!!!! noooooo
                        //list.setMsg(buff);
                        newUser.setMsg(buff);
                        break;
                }
            }
        };
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
                putFragment(user, fragment);
                break;
            case SETTINGS :
                putFragment(newSettings, fragment);
                break;
            case NEWUSER:
                putFragment(newUser, fragment);
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
                putFragment(plcmms, fragment);
                break;
            case RF :
                putFragment(hola, fragment);
                break;
            case GPRS :
                putFragment(hola, fragment);
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
                break;
            case SETTINGS:
                putFragment(menu, MENU);
                break;
            case NEWUSER:
                putFragment(menu, MENU);
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
                putFragment(settings, SETTINGS);
                break;
            case RF:
                putFragment(settings, SETTINGS);
                break;
            case GPRS:
                putFragment(settings, USER);
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
}
