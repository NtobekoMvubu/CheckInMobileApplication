package com.example.bookedit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText firstname, surname, email, password, conpassword,phonenumber;
    TextView gologon, dob;
    Button btnSignUp;
    DBHelper DB;
    DatePickerDialog.OnDateSetListener setListener;


    // creating list for gender list for users
    String[] items = {"Female", "Male"};

    AutoCompleteTextView autoCompletetxt;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstname = (EditText) findViewById(R.id.et_name);
        surname = (EditText) findViewById(R.id.et_surname);
        email = (EditText) findViewById(R.id.et_email);
        password = (EditText) findViewById(R.id.et_password);
        conpassword = (EditText) findViewById(R.id.et_confirmpasseord);
        dob = (TextView) findViewById(R.id.tv_dob);
        phonenumber = (EditText) findViewById(R.id.et_phone_number);
        autoCompletetxt = (AutoCompleteTextView) findViewById(R.id.actv_gender);
        gologon = (TextView) findViewById(R.id.tv_gologin);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        DB = new DBHelper(this);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);
        autoCompletetxt.setAdapter(adapterItems);

        autoCompletetxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String item = adapterView.getItemAtPosition(position).toString();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nametxt = firstname.getText().toString();
                String surnametxt = surname.getText().toString();
                String emailtxt = email.getText().toString();
                String passwordtxt = password.getText().toString();
                String conpasswordtxt = conpassword.getText().toString();
                String dobtxt = dob.getText().toString();
                String phonenumbertxt = phonenumber.getText().toString();
                String gendertxt = autoCompletetxt.getText().toString();
                if (nametxt.equals("") || surnametxt.equals("") || emailtxt.equals("") || passwordtxt.equals("") || conpasswordtxt.equals("") || dobtxt.equals("") || phonenumbertxt.equals("") || gendertxt.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter the relevant text fields", Toast.LENGTH_LONG).show();
                } else {
                    if (passwordtxt.equals(conpasswordtxt)) {
                        Boolean checkinsertuser = DB.insertuser(emailtxt, nametxt, surnametxt, passwordtxt, phonenumbertxt, gendertxt, dobtxt);
                        if (checkinsertuser == true) {
                            Toast.makeText(MainActivity.this, "Sign Complete", Toast.LENGTH_LONG).show();
                            openlogin();
                        } else {
                            Toast.makeText(MainActivity.this, "Please check your details and try again.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Passwords don't match.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        gologon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openlogin();
            }
        });

        //assigning variables to the year, month and day
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //once we click the edittext control for date, the calendar will pop up
       /* dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = day +"/"+month+"/"+year;
                        dob.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });*/
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month  = month+1;
                String date = dayOfMonth+"/"+month+"/"+year;
                dob.setText(date);
            }
        };

    }
    public void openlogin(){
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }


}