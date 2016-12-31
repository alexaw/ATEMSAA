package com.example.yogis.atemsaa_fragments.net.api.PlcMc;

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
public class PlcMcApi extends HttpApi {

    //region REQUEST & CALLBACK
    private static final int GET_ALL = 0;
    private static final int ADD = 1;

    public interface OnPlcMcList{
        void onPlcMcList(List<PlcMc> data);
    }

    OnPlcMcList onPlcMcList;
    //endregion

    public PlcMcApi(Context context, ProgressDialog loading) {
        super(context, loading);
    }

    public void getAll(OnPlcMcList onPlcMcList){
        this.onPlcMcList = onPlcMcList;
        executeRequest(GET_ALL, HttpAsyncTask.GET, makeUrl(R.string.url_plc_mc));
    }

    private void processAll(Response res){
        Type type = new TypeToken<List<PlcMc>>(){}.getType();
        List<PlcMc> data = gson.fromJson(res.msg, type);
        onPlcMcList.onPlcMcList(data);
    }

    public void add(PlcMc plcMc){
        executeRequest(ADD, HttpAsyncTask.POST, makeUrl(R.string.url_plc_mc), gson.toJson(plcMc));
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
