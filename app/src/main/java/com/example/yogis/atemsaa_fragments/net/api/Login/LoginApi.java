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
    private static final int GET_ALL = 0;
    private static final int ADD = 1;


    public interface OnLoginApp {
        void onLoginApp (String data);
    }

    OnLoginApp onLoginApp;
    //endregion


    public LoginApi(Context context, ProgressDialog loading) {
        super(context, loading);

    }


    public void getAll(OnLoginApp onLoginApp){
        this.onLoginApp = onLoginApp;
        String url = makeUrl(R.string.url_login);
        executeRequest(GET_ALL, HttpAsyncTask.GET, makeUrl(R.string.url_login)+"pruebas"+"&contrasenia="+"gonzalo");
    }

    private void processAll(Response res){
        Login login = gson.fromJson(res.msg, Login.class);
    }


    public void add(Login login){
        executeRequest(ADD, HttpAsyncTask.POST, makeUrl(R.string.url_login), gson.toJson(login));
    }


    private void processAdd(Response res){

    }

    @Override
    protected void processResponse(Response res) {

        switch (res.request){
            case GET_ALL:
                processAll(res);
                break;
            case ADD:

                break;
        }

    }
}
