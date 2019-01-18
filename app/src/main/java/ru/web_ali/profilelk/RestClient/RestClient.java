package ru.web_ali.profilelk.RestClient;

import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

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
            String jsonResponse=response.body().source().readString(Charset.forName("UTF-8"));

            if(!response.isSuccessful()){
                Log.i(LOG_ID,"response is not successfully");
                Log.i(LOG_ID,"response code is " + response.code());
                return "";
            }

            ObjectMapper converter = new ObjectMapper();

            //JavaType js = converter.convertValue(jsonResponse,JavaType.class);
            if(!this.checkJsonCompatibility(jsonResponse,HashMap.class)){
                Log.i(LOG_ID,"response is not successfully");
                Log.i(LOG_ID,"response can`t serialize");
                return "";
            }

            HashMap result = new ObjectMapper().readValue(jsonResponse,HashMap.class);
            if(result.size() > 0 && result.containsKey("token") && result.containsKey("expired")){

                String token = result.get("token").toString();
                String expired_at = result.get("expired").toString();
                Log.i(LOG_ID,"token: "+ token);
                Log.i(LOG_ID,"expired_at: "+ expired_at);

                return token;

            }else{
                Log.i(LOG_ID,"token not exists in the response: ");
                Log.i(LOG_ID,"response: "+ result.toString());
            }

            return "";

        }catch (IOException e){
            String error = e.getMessage();
            Log.i(LOG_ID,"token not exists in the response: ");
            Log.i(LOG_ID,"exception: "+ error);
            return "";
        }

    }


    public boolean checkJsonCompatibility(String jsonStr, Class<?> valueType) throws JsonParseException, IOException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.readValue(jsonStr, valueType);
            return true;
        } catch (JsonMappingException e) {
            return false;
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
