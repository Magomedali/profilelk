package ru.web_ali.profilelk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    SharedPreferences storage;
    String token = "";
    final public String TOKEN = "token";

    Button ordersBtn,contactsBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        storage = getSharedPreferences("MyStorage",MODE_PRIVATE);
        token = storage.getString(TOKEN,"");

        ordersBtn = (Button) findViewById(R.id.orders_btn);
        contactsBtn = (Button) findViewById(R.id.contacts_btn);

        if(token.isEmpty()){
            Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
            startActivity(intent);
        }else{
            ordersBtn.setText(token);
        }

    }


    @Override
    public void onClick(View view) {

    }
}
