package com.example.yogis.atemsaa_fragments.utl;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import com.example.yogis.atemsaa_fragments.R;

/**
 * Created by yogis on 10/10/2016.
 */
public class Qk {

    public static void showToast(Context context,@StringRes int msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static ProgressDialog getLoading(Context context){
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.loading));
        return dialog;
    }

}
