package com.example.yogis.atemsaa_fragments.net.http;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yogis on 10/10/2016.
 */
public class HttpConnection {

    private static final int CON_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 15000;

    public Response get(String url) throws IOException {
        return request("GET", url,null);
    }

    public Response post(String url, String json) throws IOException {
        return request("POST", url, json);
    }

    public Response put(String url, String json) throws IOException {
        return request("PUT", url, json);
    }

    public Response delete(String url) throws IOException {
        return request("DELETE", url, null);
    }

    private Response request(String method, String url, String json) throws IOException {

        URL u = new URL(url);
        HttpURLConnection con = (HttpURLConnection) u.openConnection();

        //Se especifica que van a llegar datos
        con.setDoInput(true);

        con.setRequestProperty("Content-Type","application/json");
        con.setRequestProperty("Accept","application/json");
        con.setRequestMethod(method);
        con.setConnectTimeout(CON_TIMEOUT);
        con.setConnectTimeout(READ_TIMEOUT);

        //si envio datos
        if(json != null)
            //especifico que envío datos
            con.setDoOutput(true);

        con.connect();


        //ENVIANDO AL SERVIDOR
        if(json!=null){
            OutputStreamWriter writer  = new OutputStreamWriter(con.getOutputStream());
            writer.write(json);
            writer.flush();
            writer.close();
        }

        //RECIBIR RESPUESTA
        InputStreamReader reader = new InputStreamReader(con.getInputStream());

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int ch;

        while((ch = reader.read()) != -1){
            out.write(ch);
        }


        Response response = new Response();
        response.msg = new String(out.toByteArray());
        response.error = Response.NO_ERROR;
        response.code = con.getResponseCode();

        return response;



    }

}
