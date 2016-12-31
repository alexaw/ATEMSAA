package com.example.yogis.atemsaa_fragments.net.api.PlcTu;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.fragments.ListUserFragment;
import com.example.yogis.atemsaa_fragments.net.http.HttpApi;
import com.example.yogis.atemsaa_fragments.net.http.HttpAsyncTask;
import com.example.yogis.atemsaa_fragments.net.http.Response;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yogis on 30/12/2016.
 */
public class PlcTuApi extends HttpApi {

    //region REQUEST & CALLBACK
    private static final int GET_ALL = 0;
    private static final int ADD = 1;

    public interface OnPlcTuList{
        void onPlcTuList(List<PlcTu> data);
    }

    OnPlcTuList onPlcTuList;
    //endregion

    public PlcTuApi(Context context, ProgressDialog loading) {
        super(context, loading);
    }

    public void getAll(OnPlcTuList onPlcTuList){
        this.onPlcTuList = onPlcTuList;
        executeRequest(GET_ALL, HttpAsyncTask.GET, makeUrl(R.string.url_plc_tu));
    }

    private void processAll(Response res){
        Type type =  new TypeToken<List<PlcTu>>(){}.getType();
        List<PlcTu> data = gson.fromJson(res.msg, type);
        onPlcTuList.onPlcTuList(data);
    }

    public void add(PlcTu plcTu){
        executeRequest(ADD, HttpAsyncTask.POST, makeUrl(R.string.url_plc_tu), gson.toJson(plcTu));
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
