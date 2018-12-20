package ru.web_ali.profilelk;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import ru.web_ali.profilelk.RestClient.AsyncRequest;
import ru.web_ali.profilelk.RestClient.Request;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_ID = "mainactivity";

    Button submitBtn;
    EditText login;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitBtn = (Button)findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit_btn : this.submitBtn();
                break;
        }
    }

    public void submitBtn(){
        login = (EditText) findViewById(R.id.input_login);
        password = (EditText) findViewById(R.id.input_password);

        String login_value = login.getText().toString();
        String password_value = password.getText().toString();
        Log.d(LOG_ID,login_value);
        Log.d(LOG_ID,password_value);

        //MyTask mt = new MyTask();
        //mt.execute();
        new AsyncRequest().execute(login_value, password_value);
    }


    class MyTask extends AsyncTask<Void, String, Request> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            login.setText("Begin");
        }

        @Override
        protected Request doInBackground(Void... params) {
            return new ru.web_ali.profilelk.RestClient.Request("POST", "/auth", "login=web-ali@yandex.ru&password=12345qwE");
            //return new ru.web_ali.profilelk.RestClient.Request("GET", "/contacts", "");
        }

        @Override
        protected void onPostExecute(Request result) {
            super.onPostExecute(result);
            Log.d(LOG_ID,"Responce : ");
            Log.d(LOG_ID,result.Content);
            password.setText("End");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
