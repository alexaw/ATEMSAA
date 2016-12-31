package com.example.yogis.atemsaa_fragments.net.api.Trafo;

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
public class TrafoApi extends HttpApi {

    //region REQUEST & CALLBACK
    private static final int GET_ALL = 0;
    private static final int ADD = 1;

    public interface OnTrafoList{
        void onTrafoList(List<Trafo> data);
    }

    OnTrafoList onTrafoList;
    //endregion

    public TrafoApi(Context context, ProgressDialog loading) {
        super(context, loading);
    }

    public void getAll(OnTrafoList onTrafoList){
        this.onTrafoList = onTrafoList;
        executeRequest(GET_ALL, HttpAsyncTask.GET, makeUrl(R.string.url_trafo));
    }

    private void processAll(Response res){
        Type type = new TypeToken<List<Trafo>>(){}.getType();
        List<Trafo> data = gson.fromJson(res.msg, type);
        onTrafoList.onTrafoList(data);
    }

    public void add(Trafo trafo){
        executeRequest(ADD, HttpAsyncTask.POST, makeUrl(R.string.url_trafo), gson.toJson(trafo));
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
