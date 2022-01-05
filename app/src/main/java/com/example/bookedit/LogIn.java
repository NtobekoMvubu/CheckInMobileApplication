package com.example.bookedit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class LogIn extends AppCompatActivity {

    DBHelper DB;
    EditText email, password;
    Button btnlogin;

    public void openMainpage(){
        Intent intent = new Intent(this, Mainpage.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        DB = new DBHelper(this);
        email = (EditText) findViewById(R.id.et_email_login);
        password = (EditText) findViewById(R.id.et_password_login);
        btnlogin = (Button) findViewById(R.id.btn_login);

        //class that will store the values that will be used in other activities, basically global variables, this will be used to hold the users
        SharedPreferences pref = getSharedPreferences("com.example.bookedit.userdetails",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtxt = email.getText().toString();
                String  passwordtxt = password.getText().toString();
                if (emailtxt.equals(" ")|| passwordtxt.equals("")) {
                    Toast.makeText(LogIn.this, "Please enter your details", Toast.LENGTH_LONG).show();
                }else {
                Cursor res = DB.getData(emailtxt, passwordtxt);
                if (res.getCount() == 0)
                {
                    Toast.makeText(LogIn.this, "Please check your details and try again", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(LogIn.this, "Welcome ", Toast.LENGTH_LONG).show();

                    Cursor res1 = DB.getuserdetails(emailtxt);
                    if (res1 != null) {
                        res1.moveToFirst();
                        String firstnametxt, surnametxt, emailtxt2;
                        firstnametxt = res1.getString(1).toString();
                        surnametxt = res1.getString(2).toString();
                        emailtxt2 = res1.getString(0).toString();

                        editor.putString("fname", firstnametxt);
                        editor.putString("sname", surnametxt);
                        editor.putString("email", emailtxt2);
                        editor.apply();
                    }
                    /*StringBuffer buffer = new StringBuffer();
                    buffer.append(res1.getString(1));
                    String firstnametxt = buffer.toString();
                    Toast.makeText(LogIn.this, buffer.toString(), Toast.LENGTH_LONG).show();*/


                    openMainpage();
                }
                }
            }
        });
    }
}