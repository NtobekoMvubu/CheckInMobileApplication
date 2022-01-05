package com.example.bookedit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

import javax.mail.MessagingException;

public class reserve extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //we gonna use the intent.putextra method in order to determine which sharedpreference to choose as the intent.put extra will hold the hotel name, and according to the hotel name

    DBHelper DB;
    ImageView iv_hotel, iv_back;
    Spinner spinner;
    TextView fname, email, noOfrooms, ppn,totalcost, hotelname;
    Spinner noofpeople;
    TextView checkInDate, checkOutDate;
    Button btncheckdate, btnmakeres, btnviewbookings;
    Boolean clicked = false;

    public void goback()
    {
        Intent intent = new Intent(this, Mainpage.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);
        fname = (TextView) findViewById(R.id.tv_username);
        email = (TextView) findViewById(R.id.tv_email);
        checkInDate = (TextView) findViewById(R.id.et_checkindate);
        checkOutDate = (TextView) findViewById(R.id.et_checkoutdate);
        noOfrooms = (TextView) findViewById(R.id.tv_noofRooms);
        ppn = (TextView) findViewById(R.id.tv_ppn);
        totalcost = (TextView) findViewById(R.id.tv_cost);
        btncheckdate = (Button) findViewById(R.id.btn_check);
        btnmakeres = (Button) findViewById(R.id.btn_makereservation);
        hotelname = (TextView) findViewById(R.id.tv_hotelname);
        noofpeople = (Spinner) findViewById(R.id.sp_noOfPeople);
        iv_hotel = (ImageView) findViewById(R.id.iv_hotelpic);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        btnviewbookings = (Button) findViewById(R.id.btn_viewbookings);
        DB = new DBHelper(this);

        Intent intent= getIntent();
        String hotelnameTXT = intent.getStringExtra("hotelname");
        hotelname.setText(hotelnameTXT);

        if (hotelnameTXT.equals("Onomo Hotel"))
        {
            iv_hotel.setImageResource(R.drawable.onomo);

        }else if (hotelnameTXT.equals("Blue Waters Hotel"))
        {
            iv_hotel.setImageResource(R.drawable.bluewaters2);
        } else if (hotelnameTXT.equals("Holiday Inn Express"))
        {
            iv_hotel.setImageResource(R.drawable.express1);
        } else if (hotelnameTXT.equals("Pearls of Umhlanga"))
        {
            iv_hotel.setImageResource(R.drawable.pearls3);
        } else if (hotelnameTXT.equals("Coastlands Musgrave Hotel"))
        {
            iv_hotel.setImageResource(R.drawable.musgrave);
        }

        //class that will store the values that will be used in other activities, basically global variables, this will be used to hold the users
        SharedPreferences prefprice = getSharedPreferences("com.example.bookedit.userdetails",Context.MODE_PRIVATE);
        SharedPreferences.Editor editorprice = prefprice.edit();

        //THIS IS THE SHAREDPREFENCE FILE FOR USER DETAILS FROM LOGIN
        //creating a method getSharedPreferances class so that this activity can receive the values from the orignal activity.
        SharedPreferences pref = getSharedPreferences("com.example.bookedit.userdetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        fname.setText("Your name: " + pref.getString("fname", null).toString() + " " + pref.getString("sname", null).toString());
        email.setText("Your email: " + pref.getString("email", null).toString());


        //ROOM5
        //creating a method getSharedPreferances class so that this activity can receive the values from the orignal activity.
        SharedPreferences pref1 = getSharedPreferences("com.example.bookedit.userdetails1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = pref1.edit();
        noOfrooms.setText(pref1.getString("nor", null).toString());
        ppn.setText(pref1.getString("ppn", null));




        spinner = (Spinner) findViewById(R.id.sp_noOfPeople);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        //assigning variables to the year, month and day
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        //once we click the edittext control for date, the calendar will pop up
        checkInDate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        reserve.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        checkInDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        checkOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        reserve.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        checkOutDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });

        btncheckdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //the method to calculate the number of days the user will be reserving the room and will give the price according to those days.
                int price;



                if (checkInDate.getText().toString().equals("") || checkOutDate.getText().toString().equals("")) {
                    Toast.makeText(reserve.this, "Please choose your Checkin and Checkout dates", Toast.LENGTH_LONG).show();
                } else {
                    String checkin = checkInDate.getText().toString();
                    String checkout = checkOutDate.getText().toString();
                    String Scheckin = checkin.substring(0,2);
                    String Scheckout = checkout.substring(0,2);
                    int checkinNo = Integer.parseInt(Scheckin);
                    int checkOutNo = Integer.parseInt(Scheckout);
                    if (checkOutNo < checkinNo) {
                        Toast.makeText(reserve.this, "Please make sure your Checkout date is ahead of your Checkin date.", Toast.LENGTH_LONG).show();
                    } else {
                        int noofdays, stcost;
                        noofdays = checkOutNo - checkinNo;
                        stcost = pref1.getInt("price", 0);
                        price = stcost * noofdays;
                        String costprice = String.valueOf(price);
                        editorprice.putString("price", costprice);
                        editorprice.apply();
                        totalcost.setText("Total Cost: R" + costprice);
                        clicked = true;
                    }
                }


            }
        });

        btnmakeres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = pref.getString("fname", null).toString();
                String emailTXT = pref.getString("email", null).toString();
                String hotelnametxt = hotelname.getText().toString();
                String checkindatetxt = checkInDate.getText().toString();
                String checkoutdatetxt = checkOutDate.getText().toString();
                String noofpeopletxt = noofpeople.getSelectedItem().toString();
                String pricetxt = prefprice.getString("price", null).toString();

                Cursor res = DB.getPRes(checkindatetxt, checkoutdatetxt, hotelnametxt);
                if (res.getCount() > 2 || res == null) {
                    Toast.makeText(reserve.this, "The Room is unavailable for your selected dates. Please select new CheckIn and CheckOut Dates.", Toast.LENGTH_LONG).show();
                } else {
                    if (clicked == true) {
                        boolean checkinsertres = DB.insertRes(nameTXT, emailTXT, hotelnametxt, checkindatetxt, checkoutdatetxt, noofpeopletxt, pricetxt);
                        if (checkinsertres == true) {
                            Toast.makeText(reserve.this, "Reservation Completed", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(reserve.this, "Please fill in the required information and try again", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor1.clear();
                editor1.apply();
                goback();
            }
        });

        btnviewbookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.getRes();
                if (res.getCount()==0)
                {
                    Toast.makeText(reserve.this, "No Entry Existing", Toast.LENGTH_LONG).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext())
                {
                    buffer.append("firstname :"+ res.getString(0)+"\n");
                    buffer.append("email :"+ res.getString(1)+"\n");
                    buffer.append("hotel_name:"+ res.getString(2)+"\n");
                    buffer.append("checkindate :"+ res.getString(3)+"\n");
                    buffer.append("checkoutdate:"+ res.getString(4)+"\n");
                    buffer.append("noofpeople:"+ res.getString(5)+"\n");
                    buffer.append("price:"+ res.getString(6)+"\n");
                    buffer.append("\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(reserve.this);
                builder.setCancelable(true);
                builder.setTitle("Your Reservations");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
}


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}