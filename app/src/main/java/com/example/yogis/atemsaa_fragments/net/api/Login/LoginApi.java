package com.example.yogis.atemsaa_fragments.net.api.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.EditText;

import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.http.HttpApi;
import com.example.yogis.atemsaa_fragments.net.http.HttpAsyncTask;
import com.example.yogis.atemsaa_fragments.net.http.Response;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by yogis on 12/01/2017.
 */
public class LoginApi extends HttpApi{

    //region REQUEST & CALLBACK
    private static final int LOGIN = 0;
    
    public interface OnLoginApp {
        void onLoginApp (boolean respuesta);
    }

    OnLoginApp onLoginApp;
    //endregion


    public LoginApi(Context context, ProgressDialog loading) {
        super(context, loading);

    }


    public void login(String usr, String pass, OnLoginApp onLoginApp){
        this.onLoginApp = onLoginApp;
        String url = makeUrl(R.string.url_login, usr, pass);
        executeRequest(LOGIN, HttpAsyncTask.GET, url);
    }

    private void processLogin(Response res){
        Login login = gson.fromJson(res.msg, Login.class);
        onLoginApp(login.getRespuesta())
    }


   

    @Override
    protected void processResponse(Response res) {

        switch (res.request){
            case LOGIN:
                processLogin(res);
                break;          
        }

    }
}
