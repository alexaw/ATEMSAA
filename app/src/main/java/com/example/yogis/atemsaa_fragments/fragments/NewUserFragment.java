package com.example.yogis.atemsaa_fragments.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewUserFragment extends Fragment implements View.OnClickListener {

    FloatingActionButton flBut, flAdd, flSearch;
    Animation open, close, clock, anticlock;
    boolean isOpen = false;

    TextView tvRtaListUser;

    OnChangeFragment changeFragment;


    public NewUserFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        changeFragment = (OnChangeFragment) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View vistaUsr = inflater.inflate(R.layout.fragment_new_user, container, false);



        //textView donde se muestra la respuesta a la busqueda del usuario (Search User)
        tvRtaListUser=(TextView)vistaUsr.findViewById(R.id.txt_view_rta_list_usr);

        tvRtaListUser.setText("");

        flBut = (FloatingActionButton) vistaUsr.findViewById(R.id.flButton);
        flAdd = (FloatingActionButton) vistaUsr.findViewById(R.id.flAddUser);
        flSearch = (FloatingActionButton) vistaUsr.findViewById(R.id.flSearchUser);


        open = AnimationUtils.loadAnimation(vistaUsr.getContext(),R.anim.open);
        close = AnimationUtils.loadAnimation(vistaUsr.getContext(),R.anim.close);
        clock = AnimationUtils.loadAnimation(vistaUsr.getContext(),R.anim.rorate_clock);
        anticlock = AnimationUtils.loadAnimation(vistaUsr.getContext(),R.anim.rotate_anticlock);


        flBut.setOnClickListener(this);
        flAdd.setOnClickListener(this);
        flSearch.setOnClickListener(this);

        return vistaUsr;


    }


    public void setMsg(String msg){
        tvRtaListUser.setText(msg);
    }


    @Override
    public void onClick(View view) {

        if (isOpen){

            flAdd.startAnimation(close);
            flSearch.startAnimation(close);
            flBut.startAnimation(anticlock);
            flAdd.setClickable(false);
            flSearch.setClickable(false);
            isOpen = false;
        }
        else {
            flAdd.startAnimation(open);
            flSearch.startAnimation(open);
            flBut.startAnimation(clock);
            flAdd.setClickable(true);
            flSearch.setClickable(true);
            isOpen = true;
        }
        switch(view.getId()){

            case R.id.flAddUser:
                //Toast.makeText(this.getActivity(),"Button is clicked", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),"Button is clicked", Toast.LENGTH_LONG).show();
                changeFragment.onChange(OnChangeFragment.SEARCH);
                break;

            case R.id.flSearchUser:
                //Toast.makeText(this.getActivity(),"Button is clicked", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(),"Button is clicked", Toast.LENGTH_LONG).show();
                changeFragment.onChange(OnChangeFragment.SEARCH);
                break;
        }
    }
}
