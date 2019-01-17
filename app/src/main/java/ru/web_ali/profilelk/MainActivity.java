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

import java.io.IOException;
import ru.web_ali.profilelk.RestClient.RestClient;

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

        new AsyncRequest().execute(login_value, password_value);
    }


    class AsyncRequest extends AsyncTask<String, Integer, String> {



        @Override
        protected String doInBackground(String... arg) {
            RestClient rcl = new RestClient();
            String json = rcl.parametersAuthJson(arg[0], arg[1]);
            try {
                return rcl.auth(json);
            }catch (IOException e){
                Log.d(LOG_ID,"IOException");
                return e.getMessage();
            }
            //return new ru.web_ali.profilelk.RestClient.Request("POST", "/auth", "login=web-ali@yandex.ru&password=12345qwE");
            //return new ru.web_ali.profilelk.RestClient.Request("GET", "/contacts", "");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            login.setText(result);
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
