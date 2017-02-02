package com.example.yogis.atemsaa_fragments.net.api.Producto;

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
public class ProductoApi extends HttpApi {

    //region REQUEST & CALLBACK
    private static final int GET_ALL = 0;
    private static final int ADD = 1;

    public interface OnProductoList{
        void onProductoList(List<Producto> data);
    }

    OnProductoList onProductoList;
    //endregion

    public ProductoApi(Context context, ProgressDialog loading) {
        super(context, loading);
    }

    public void getAll(OnProductoList onProductoList){
        this.onProductoList = onProductoList;
        executeRequest(GET_ALL, HttpAsyncTask.GET, makeUrl(R.string.url_product));
    }

    private void processAll(Response res){
        Type type = new TypeToken<List<Producto>>(){}.getType();
        List<Producto> data = gson.fromJson(res.msg, type);
        onProductoList.onProductoList(data);
    }

    public void add(Producto producto){
        executeRequest(ADD, HttpAsyncTask.POST, makeUrl(R.string.url_product), gson.toJson(producto));
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
