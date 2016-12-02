package com.example.yogis.atemsaa_fragments.net.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.StringRes;

import com.example.yogis.atemsaa_fragments.R;
import com.example.yogis.atemsaa_fragments.url.Qk;
import com.google.gson.Gson;

/**
 * Created by yogis on 10/10/2016.
 */
public abstract class HttpApi implements HttpAsyncTask.OnResponseRecived {

    Context context;
    ProgressDialog loading;
    protected Gson gson;

    public HttpApi(Context context, ProgressDialog loading){
        this.context = context;
        this.loading = loading;
        gson = new Gson();
    }

    protected String makeUrl(@StringRes int url, Object... data){
        String base = context.getString(R.string.url_base);
        return String.format(base+context.getString(url), data);
    }

    protected void executeRequest(int req, int method, String url,String obj){
        if(loading != null){
            loading.show();
        }
        HttpAsyncTask task = new HttpAsyncTask(method,req, this);
        task.execute(url, obj);
    }

    protected void executeRequest(int req, int method, String url){
        if(loading != null){
            loading.show();
        }
        HttpAsyncTask task = new HttpAsyncTask(method, req, this);
        task.execute(url);
    }

    private boolean validate(Response res){
        if(res.error != Response.NO_ERROR){
            if(res.error == Response.ERROR_TIME_OUT){
                Qk.showToast(context, R.string.error_timeout);
                return false;
            }else if(res.error == Response.ERROR_NO_INTERNET){
                Qk.showToast(context, R.string.error_no_internet);
                return false;
            }else {
                Qk.showToast(context, R.string.error_unknown);
                return false;
            }
        }else{
            if(res.code == 200){
                return true;
            }else if(res.code == 401){
                Qk.showToast(context, R.string.error_401);
                return false;
            }else if(res.code == 500){
                Qk.showToast(context, R.string.error_500);
                return false;
            }else if(res.code == 404){
                Qk.showToast(context, R.string.error_404);
                return false;
            }else{
                Qk.showToast(context, R.string.error_unknown);
                return false;
            }
        }
    }

    @Override
    public void onResponse(Response res) {
        if(loading != null){
            loading.dismiss();
        }
        if(validate(res)){
            processResponse(res);
        }
    }

    protected abstract void processResponse(Response res);


}
