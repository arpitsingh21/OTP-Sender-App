package com.example.dell.otp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chilkatsoft.CkGlobal;
import com.chilkatsoft.CkJsonObject;
import com.chilkatsoft.CkRest;
import com.chilkatsoft.chilkat;

import org.json.JSONArray;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import okhttp3.OkHttpClient;

public class SmsActivity extends AppCompatActivity {
    private EditText mTo;
    private TextView mBody;
    private Button mSend;
    private OkHttpClient mClient = new OkHttpClient();
    private Context mContext;

    boolean success;

    Random rnd;
    private static final String TAG = "Arpit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
            System.loadLibrary("chilkat");
        rnd = new Random();
        CkGlobal ck=new CkGlobal();
        ck.UnlockBundle("Hello");

       final CkRest rest =new CkRest();
        mTo = (EditText) findViewById(R.id.txtNumber);
        mBody = (TextView) findViewById(R.id.txtMessage);
        mSend = (Button) findViewById(R.id.btnSend);

        Intent intent = getIntent();
        final String name2 = intent.getStringExtra("name");
        final String number = intent.getStringExtra("number");
       // Toast.makeText(SmsActivity.this, name2+"\n"+number+"", Toast.LENGTH_SHORT).show();


mTo.setText(number);

       final int a= rnd.nextInt(900000)+100000;
        mBody.setText("Your OTP is  "+a);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog progressDialog= new ProgressDialog(SmsActivity.this);
                progressDialog.setTitle("Sending Otp..");
                progressDialog.show();
                Date date = new Date();
                DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
               String h= sdf.format(date);           // String formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                HashMap<String, String>  contact=new HashMap<>();

               // ArrayList<HashMap<String, String>> contactList = new ArrayList<>();

                // adding each child node to HashMap key => value
                //contact.put("id", id);
                contact.put("name",name2);
                contact.put("mobile",number);
                contact.put("time",h);
                SentFragment.contactList.add(contact);
Collections.reverse(SentFragment.contactList);

                try {
                    FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory()+"/hello.txt");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(SentFragment.contactList);
                    oos.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }





                //Toast.makeText(SmsActivity.this, ""+contactList, Toast.LENGTH_SHORT).show();

                    boolean bTls = true;
                    int port = 443;
                    boolean bAutoReconnect = true;
                success = rest.SetAuthBasic("ACce6c0cd451bcd2951e4e6d68463c7441", "93812eea7f92336046110c2ad22f2751");
                success = rest.Connect("api.twilio.com", port, bTls, bAutoReconnect);
                success = rest.AddQueryParam("To",mTo.getText().toString());
                success = rest.AddQueryParam("From", "+15619356763");
                success = rest.AddQueryParam("Body", "Hello,"+name2+" "+mBody.getText().toString()+ "");



                String responseJson = rest.fullRequestFormUrlEncoded("POST", "/2010-04-01/Accounts/ACce6c0cd451bcd2951e4e6d68463c7441/Messages.json");
                    if (rest.get_LastMethodSuccess() != true) {
                        Log.i(TAG, rest.lastErrorText());
                        return;
                    }
                CkJsonObject json = new CkJsonObject();
                json.put_EmitCompact(false);
                success = json.Load(responseJson);


                if (rest.get_ResponseStatusCode() != 201) {
                        //  Examine the request/response to see what happened.
                     progressDialog.setTitle("Getting response..");
                        Log.i(TAG, "response status code = " + String.valueOf(rest.get_ResponseStatusCode()));
                        Log.i(TAG, "response status text = " + rest.responseStatusText());
                        Log.i(TAG, "response header: " + rest.responseHeader());
                        Log.i(TAG, "response body (if any): " + responseJson);

                        Log.i(TAG, "---");
                        Log.i(TAG, "LastRequestStartLine: " + rest.lastRequestStartLine());
                        Log.i(TAG, "LastRequestHeader: " + rest.lastRequestHeader());
                    progressDialog.dismiss();
                    Toast.makeText(SmsActivity.this, rest.get_ResponseStatusCode() + "\n"+rest.responseStatusText()+responseJson, Toast.LENGTH_SHORT).show();

                    }



                else if(rest.get_ResponseStatusCode()== 201){
                        Log.i(TAG, "Otp Sent");
                    progressDialog.dismiss();
                    Toast.makeText(SmsActivity.this, "Otp Sent", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(SmsActivity.this,MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();


                }



                }
                    });




//        Random rnd = new Random();
        //  Send the SMS text message.
        //  Your Twilio Account SID is part of the URI path:


    }



}