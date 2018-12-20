package ru.web_ali.profilelk.RestClient;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

public class AsyncRequest extends AsyncTask<String, Integer, String> {

    final String LOG_ID = "AsyncRequest";

    @Override
    protected String doInBackground(String... arg) {
        RestClient rcl = new RestClient();
        String json = rcl.parametersAuthJson(arg[0], arg[1]);
        try {
            Log.i(LOG_ID,"Execute request auth");
            String resp = rcl.auth(json);
            //String resp = rcl.contacts();
            Log.i(LOG_ID,"Responce: "+resp);
            return resp;
        }catch (IOException e){
            Log.d(LOG_ID,"IOException");
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
