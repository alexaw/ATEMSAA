package com.example.yogis.atemsaa_fragments.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewUserFragment extends Fragment implements View.OnClickListener {

    TextView tvRtaListUser;
    ImageButton floatButton;
    OnChangeFragment changeFragment;


    public NewUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View vistaUsr = inflater.inflate(R.layout.fragment_new_user, container, false);



        //textView donde se muestra la respuesta a la busqueda del usuario (Search User)
        tvRtaListUser=(TextView)vistaUsr.findViewById(R.id.txt_view_rta_list_usr);

        tvRtaListUser.setText("");
        floatButton = (ImageButton)vistaUsr.findViewById(R.id.imButton);
        floatButton.setOnClickListener(this);

        return vistaUsr;


    }


    public void setMsg(String msg){
        tvRtaListUser.setText(msg);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imButton:
                changeFragment.onChange(OnChangeFragment.SEARCH);
                break;
        }
    }
}
