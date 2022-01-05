package com.example.bookedit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Mainpage extends AppCompatActivity {

    TextView tv_hello;
    CardView cv_hotel1, cv_hotel2, cv_hotel3, cv_hotel4, cv_hotel5;

    public void openhotel1(){
        Intent intent1 = new Intent(this, Room1.class);
        startActivity(intent1);
    }

    public void openhotel2(){
        Intent intent2 = new Intent(this, Room2.class);
        startActivity(intent2);
    }
    public void openhotel3(){
        Intent intent3 = new Intent(this, Room3.class);
        startActivity(intent3);
    }
    public void openhotel4(){
        Intent intent4 = new Intent(this, Room4.class);
        startActivity(intent4);
    }
    public void openhotel5(){
        Intent intent5 = new Intent(this, Room5.class);
        startActivity(intent5);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        cv_hotel1 = findViewById(R.id.cv_hotel1);
        cv_hotel2 = findViewById(R.id.cv_hotel2);
        cv_hotel2 = findViewById(R.id.cv_hotel2);
        cv_hotel3 = findViewById(R.id.cv_hotel3);
        cv_hotel4 = findViewById(R.id.cv_hotel4);
        cv_hotel5 = findViewById(R.id.cv_hotel5);
        tv_hello = (TextView) findViewById(R.id.tv_helloname);
        //creating a method getSharedPreferances class so that this activity can receive the values from the orignal activity.
        SharedPreferences pref = getSharedPreferences("com.example.bookedit.userdetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        tv_hello.setText("Hello"+ " " + pref.getString("fname", null).toString());




        cv_hotel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openhotel5();
            }
        });

        cv_hotel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openhotel4();
            }
        });

        cv_hotel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openhotel3();
            }
        });
        cv_hotel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openhotel2();
            }
        });

        cv_hotel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openhotel1();
            }
        });
    }
}