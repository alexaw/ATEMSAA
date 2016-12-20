package com.example.yogis.atemsaa_fragments.net.api.Cliente;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.api.PlcMms.PlcMms;
import com.example.yogis.atemsaa_fragments.net.http.HttpApi;
import com.example.yogis.atemsaa_fragments.net.http.HttpAsyncTask;
import com.example.yogis.atemsaa_fragments.net.http.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yogis on 1/12/2016.
 */
public class ClienteApi extends HttpApi{

    //region REQUEST & CALLBACK
    private static final int GET_ALL = 0;
    private static final int ADD = 1;

    public interface OnClienteList{
        void onClienteList(List<Cliente> data);
    }

    OnClienteList onClienteList;
    //endregion


    public ClienteApi(Context context, ProgressDialog loading) {
        super(context, loading);
    }

    public void getAll(OnClienteList onClienteList){
        this.onClienteList = onClienteList;
        executeRequest(GET_ALL, HttpAsyncTask.GET, makeUrl(R.string.url_cliente));
    }

    private void processAll(Response res){

        Type type = new TypeToken< List<Cliente>>(){}.getType();
        List<Cliente> data =  gson.fromJson(res.msg, type);
        onClienteList.onClienteList(data);
    }

    public void add(Cliente cliente){
        executeRequest(ADD, HttpAsyncTask.POST, makeUrl(R.string.url_cliente), gson.toJson(cliente));
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
