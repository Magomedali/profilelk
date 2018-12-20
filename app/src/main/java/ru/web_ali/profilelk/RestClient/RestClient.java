package ru.web_ali.profilelk.RestClient;

import android.util.Log;
import okhttp3.*;

import java.io.IOException;
import java.nio.charset.Charset;

public class RestClient {
    final String LOG_ID = "RestClient";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    public static final String HOST = "http://api.tcrm.web-ali.ru";

    public String auth(String json) throws IOException {
        String url = HOST + "/auth";
        RequestBody body = RequestBody.create(JSON, json);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String r =response.body().source().readString(Charset.forName("UTF-8"));
            Log.i(LOG_ID,"response: "+ r);
            return r;
        }catch (IOException e){
            return e.getMessage();
        }


    }

    public String parametersAuthJson(String login, String password) {
        return "{"
                + "\"login\":\"" + login +"\","
                + "\"password\":\""+password+"\""
                +"}";
    }


    public String contacts() throws IOException{
        String url = HOST + "/contacts";
        Log.i(LOG_ID,"URI: "+ url);
        okhttp3.Request get = new okhttp3.Request.Builder().url(url).build();
        try{
            Response response = client.newCall(get).execute();
            String r =response.body().source().readString(Charset.forName("UTF-8"));
            Log.i(LOG_ID,"response: "+ r);
            return  r;
        }catch (IOException e){
            return e.getMessage();
        }
    }

}
