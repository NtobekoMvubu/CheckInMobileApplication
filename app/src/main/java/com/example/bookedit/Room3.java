package com.example.bookedit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Room3 extends AppCompatActivity {
    Button btn_regnow, btn_sendemail;
    TextView ppn_value, nor, hotelname;
    EditText eMessage;
    ImageView iv_main, iv_bottomleft, iv_bottomright, iv_bottommid, iv_back;
    public void openReserve()
    {
        String hotelnametxt = hotelname.getText().toString();
        Intent intent = new Intent(this, reserve.class);
        intent.putExtra("hotelname", hotelnametxt);
        startActivity(intent);
    }
    public void goback()
    {
        Intent intent = new Intent(this, Mainpage.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room3);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        btn_regnow = (Button) findViewById(R.id.btn_regnow);
        ppn_value = (TextView) findViewById(R.id.tv_hotel3_price_value);
        nor = (TextView) findViewById(R.id.tv_hotel3_room_no_value);
        iv_main = (ImageView) findViewById(R.id.iv_main);
        iv_bottomleft = (ImageView) findViewById(R.id.iv_bottomleft);
        iv_bottommid = (ImageView) findViewById(R.id.iv_bottommid);
        iv_bottomright = (ImageView) findViewById(R.id.iv_bottomright);
        hotelname = (TextView) findViewById(R.id.tv_hotel3_name);

        //controls and shared preferences for email
        btn_sendemail = (Button) findViewById(R.id.btn_sendemail);
        eMessage = (EditText) findViewById(R.id.et_eMessage);
        SharedPreferences pref = getSharedPreferences("com.example.bookedit.userdetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        //creating a method getSharedPreferances class so that this activity can receive the values from the orignal activity.
        SharedPreferences pref1 = getSharedPreferences("com.example.bookedit.userdetails1", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = pref1.edit();

        editor1.putString("ppn", ppn_value.getText().toString());
        editor1.putString("nor", "Number of Rooms: 5");
        editor1.putInt("price", 400);
        editor1.apply();

        btn_regnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReserve();
            }
        });

        iv_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_main.setImageResource(R.drawable.express);
            }
        });

        iv_bottomleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_main.setImageResource(R.drawable.express2);
            }
        });
        iv_bottommid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_main.setImageResource(R.drawable.express3);
            }
        });
        iv_bottomright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_main.setImageResource(R.drawable.express4);

            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goback();
            }
        });

        btn_sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final String myemail = pref.getString("email", null); <<< this is the code to get the email of the user from their login activity
                final String myemail = "checkin925@gmail.com";
                final String password = "fakebusinessaccount123";
                String messageToSend = eMessage.getText().toString();
                Properties props = new Properties();
                props.put("mail.smtp.auth","true");
                props.put("mail.smtp.starttls.enable","true");
                props.put("mail.smtp.host","smtp.gmail.com");
                props.put("mail.smtp.port","587");
                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator(){
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(myemail, password);
                            }
                        });
                try{
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(myemail));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(pref.getString("email", null).toString()));
                    message.setSubject(pref.getString("fname", null).toString() + " " + pref.getString("sname", null).toString() + "'s " + "Enquiry about Holiday Inn Express"  );
                    message.setText(messageToSend);
                    Transport.send(message);
                    Toast.makeText(Room3.this, "Enquiry Sent successfully", Toast.LENGTH_LONG).show();
                } catch (MessagingException e ){
                    throw new RuntimeException(e);
                }
            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}