package com.example.yogis.atemsaa_fragments.net.api.PlcMms;

import android.app.ProgressDialog;
import android.content.Context;

import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.net.api.PlcMms.PlcMms;
import com.example.yogis.atemsaa_fragments.net.http.HttpApi;
import com.example.yogis.atemsaa_fragments.net.http.HttpAsyncTask;
import com.example.yogis.atemsaa_fragments.net.http.Response;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yogis on 10/10/2016.
 */
public class PlcMmsApi extends HttpApi {


    //region REQUEST & CALLBACK
    private static final int GET_ALL = 0;
    private static final int ADD = 1;

    public interface OnPlcMmsList{
        void onPlcMmsList(List<PlcMms> data);
    }

    OnPlcMmsList onPlcMmsList;
    //endregion


    public PlcMmsApi(Context context, ProgressDialog loading) {
        super(context, loading);
    }

    public void getAll(OnPlcMmsList onPlcMmsList){
        this.onPlcMmsList = onPlcMmsList;
        String url = makeUrl(R.string.url_plc_mms);
        executeRequest(GET_ALL, HttpAsyncTask.GET, makeUrl(R.string.url_plc_mms));
    }

    private void processAll(Response res){

        Type type = new TypeToken< List<PlcMms>>(){}.getType();
        List<PlcMms> data =  gson.fromJson(res.msg, type);
        onPlcMmsList.onPlcMmsList(data);
    }

    public void add(PlcMms plcMms){
        executeRequest(ADD, HttpAsyncTask.POST, makeUrl(R.string.url_plc_mms), gson.toJson(plcMms));
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
