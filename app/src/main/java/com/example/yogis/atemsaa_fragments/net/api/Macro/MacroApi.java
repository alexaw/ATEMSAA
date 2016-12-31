package com.example.yogis.atemsaa_fragments.net.api.Macro;

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
public class MacroApi extends HttpApi {

    //region REQUEST & CALLBACK
    private static final int GET_ALL = 0;
    private static final int ADD = 1;

    public interface OnMacroList{
        void onMacroList(List<Macro> data);
    }

    OnMacroList onMacroList;
    //endregion


    public MacroApi(Context context, ProgressDialog loading) {
        super(context, loading);
    }

    public void getAll(OnMacroList onMacroList){
        this.onMacroList = onMacroList;
        executeRequest(GET_ALL, HttpAsyncTask.GET, makeUrl(R.string.url_macro));
    }

    private void processAll(Response res){
        Type type = new TypeToken<List<Macro>>(){}.getType();
        List<Macro> data = gson.fromJson(res.msg, type);
        onMacroList.onMacroList(data);
    }

    public void add(Macro macro){
        executeRequest(ADD, HttpAsyncTask.POST, makeUrl(R.string.url_macro), gson.toJson(macro));
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
