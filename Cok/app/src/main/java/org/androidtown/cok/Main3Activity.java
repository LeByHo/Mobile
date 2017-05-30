package org.androidtown.cok;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by LEE on 2017-05-17.
 */

public class Main3Activity extends AppCompatActivity  {
    TextView text1;
    TextView text2;
    Button btn2;
    Button btn;
    Bundle bundle;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        text1 = (TextView)findViewById(R.id.textView5);
        text2 =(TextView)findViewById(R.id.textView6);
        btn = (Button)findViewById(R.id.btn);
        btn2 = (Button)findViewById(R.id.btn2);
        Intent data=getIntent();
        bundle = data.getExtras();
        String title=bundle.getString("NAME").toString();
        String number =bundle.getString("NUM").toString();
        text1.setText(title);
        text2.setText(number);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main3Activity.this, VoteActivtiy.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("START",bundle.getString("Start"));
                bundle2.putString("FINISH",bundle.getString("Finish"));
                intent.putExtras(bundle2);
                startActivity(intent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
