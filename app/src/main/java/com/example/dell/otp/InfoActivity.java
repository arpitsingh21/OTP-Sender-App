package com.example.dell.otp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class InfoActivity extends AppCompatActivity {
TextView tname,tnumber;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
       final String name = intent.getStringExtra("name");
       final String number = intent.getStringExtra("mobile");
       // Toast.makeText(this, name+number+"", Toast.LENGTH_SHORT).show();
        tname=(TextView)findViewById(R.id.textView2);
        tnumber=(TextView)findViewById(R.id.textView3);

        tname.setText(name);
        tnumber.setText(number);

        b=(Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(InfoActivity.this,SmsActivity.class);
                i.putExtra("name",name);
                i.putExtra("number",number);
                startActivity(i);

            }
        });


    }
}
