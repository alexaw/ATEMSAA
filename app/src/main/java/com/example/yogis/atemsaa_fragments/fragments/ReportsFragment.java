package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.BluetoothCommandService;
import com.example.yogis.atemsaa_fragments.MainActivity;
import com.example.yogis.atemsaa_fragments.R;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsFragment extends Fragment implements View.OnClickListener {

    FloatingActionsMenu flMore;
    FloatingActionButton flParcial, flList, flOpc;

    TextView tvRtaNewReports;
    String buff = "";

    OnChangeFragment changeFragment;
    MainActivity activity;

    public ReportsFragment() {
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
        View vistaReport = inflater.inflate(R.layout.fragment_reports, container, false);

        //textView donde se muestra las respuesta de las consultas
        tvRtaNewReports=(TextView)vistaReport.findViewById(R.id.txt_view_rta_newreports);
        tvRtaNewReports.setText("");


        flMore = (FloatingActionsMenu) vistaReport.findViewById(R.id.fl_more);

        flList = (FloatingActionButton) vistaReport.findViewById(R.id.fl_list);
        flParcial = (FloatingActionButton) vistaReport.findViewById(R.id.fl_parcial);
        flOpc = (FloatingActionButton) vistaReport.findViewById(R.id.fl_opc);

        flList.setOnClickListener(this);
        flParcial.setOnClickListener(this);
        flOpc.setOnClickListener(this);

        return vistaReport;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {

            case R.id.fl_list:

            flMore.collapse();

                byte[] frame2Send = new byte[7];

            frame2Send[0] = 0x24;
            frame2Send[1] = 0x40;
            frame2Send[2] = 0x07;
            frame2Send[3] = 0x02;
            frame2Send[4] = 0x01;
            frame2Send[5] = 0x02;
            frame2Send[6] = calcularCRC(frame2Send);

            tvRtaNewReports.setText("");
            buff="";

            sendMessage(frame2Send);
                break;

            //caso de reporte parcial
            case R.id.fl_parcial:
                flMore.collapse();

                frame2Send = new byte[7];

                frame2Send[0] = 0x24;
                frame2Send[1] = 0x40;
                frame2Send[2] = 0x07;
                frame2Send[3] = 0x13;
                frame2Send[4] = 0x01;
                frame2Send[5] = 0x02;
                frame2Send[6] = calcularCRC(frame2Send);

                tvRtaNewReports.setText("");
                buff="";

                sendMessage(frame2Send);
                break;

            case R.id.fl_opc:
                flMore.collapse();
                break;

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

    public void setMsg(String msg){
        tvRtaNewReports.setText(msg);
    }
}
