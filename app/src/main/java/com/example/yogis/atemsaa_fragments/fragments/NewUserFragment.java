package com.example.yogis.atemsaa_fragments.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yogis.atemsaa_fragments.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewUserFragment extends Fragment {

    TextView tvRtaListUser;



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

        return vistaUsr;


    }


    public void setMsg(String msg){
        tvRtaListUser.setText(msg);
    }


}
