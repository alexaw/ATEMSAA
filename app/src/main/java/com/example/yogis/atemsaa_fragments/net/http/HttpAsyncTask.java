package com.example.yogis.atemsaa_fragments.net.http;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by yogis on 10/10/2016.
 */
public class HttpAsyncTask extends AsyncTask<String,Integer,Response>{

    public static final int GET = 0;
    public static final int POST = 1;
    public static final int PUT = 2;
    public static final int DELETE = 3;

    public interface OnResponseRecived{
        void onResponse(Response res);
    }

    private int method;
    private int request;
    OnResponseRecived onResponseRecived;

    public HttpAsyncTask(int method, int request ,OnResponseRecived onResponseRecived){
        this.onResponseRecived = onResponseRecived;
        this.method = method;
        this.request = request;
    }

    @Override
    protected Response doInBackground(String... strings) {
        HttpConnection con = new HttpConnection();
        Response rta = new Response();
        try {
            switch (method) {
                case GET:
                    rta = con.get(strings[0]);
                    break;
                case POST:
                    rta = con.post(strings[0], strings[1]);
                    break;
                case PUT:
                    rta = con.put(strings[0], strings[1]);
                    break;
                case DELETE:
                    rta = con.delete(strings[0]);
                    break;
            }
        }catch (SocketTimeoutException e){
            rta.error = Response.ERROR_TIME_OUT;
        }catch(IOException e){
            rta.error = Response.ERROR_UNKOWN;
        }

        rta.request = request;
        return rta;
    }

    @Override
    protected void onPostExecute(Response r) {
        onResponseRecived.onResponse(r);
    }
}
