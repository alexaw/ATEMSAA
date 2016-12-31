package com.example.yogis.atemsaa_fragments.net.api.Medidor;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.http.HttpApi;
import com.example.yogis.atemsaa_fragments.net.http.HttpAsyncTask;
import com.example.yogis.atemsaa_fragments.net.http.Response;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yogis on 30/12/2016.
 */
public class MedidorApi extends HttpApi {

    //region REQUEST & CALLBACK
    private static final int GET_ALL = 0;
    private static final int ADD = 1;

    public interface OnMedidorList{
        void onMedidorList(List<Medidor> data);
    }

    OnMedidorList onMedidorList;
    //endregion


    public MedidorApi(Context context, ProgressDialog loading) {
        super(context, loading);
    }

    public void getAll(OnMedidorList onMedidorList){
        this.onMedidorList = onMedidorList;
        executeRequest(GET_ALL, HttpAsyncTask.GET, makeUrl(R.string.url_medidor));
    }

    private void processAll(Response res){
        Type type = new TypeToken<List<Medidor>>(){}.getType();
        List<Medidor> data = gson.fromJson(res.msg, type);
        onMedidorList.onMedidorList(data);
    }

    public void add(Medidor medidor){
        executeRequest(ADD, HttpAsyncTask.POST, makeUrl(R.string.url_medidor), gson.toJson(medidor));
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
