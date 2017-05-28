package org.androidtown.cok;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import android.app.FragmentManager;

import java.lang.reflect.Array;

/**
 * Created by GE62 on 2017-05-10.
 */

public class Calendar extends AppCompatActivity{

    Button button;
    Button s_btn;
    Button e_btn;
    android.app.FragmentManager fm;
    Thread th;
    Thread th2;
    CalendarFragment cf;
    CalendarFragment2 cf2;
    Bundle bundle;
    Intent intent;
    int s_y,s_m,s_d,e_y,e_m,e_d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        intent=getIntent();
        cf = new CalendarFragment(Calendar.this,intent);
        cf2= new CalendarFragment2(Calendar.this,intent);
        fm=getFragmentManager();
        android.app.FragmentTransaction tr=fm.beginTransaction();
        tr.add(R.id.c_frame,cf,"ccc");
        tr.commit();
        th=new cThread(1);
        th2=new cThread(2);
        th.start();

        button = (Button)findViewById(R.id.button3);

        s_btn =(Button)findViewById(R.id.btn);
        s_btn.setBackgroundColor(Color.RED);
        s_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e_btn.setBackgroundColor(Color.WHITE);
                s_btn.setBackgroundColor(Color.RED);
                android.app.FragmentTransaction tr=fm.beginTransaction();
                tr.replace(R.id.c_frame,cf);
                th.start();
                if(th2.isAlive())
                th2.interrupt();
                tr.commit();
                }
        });
        e_btn=(Button)findViewById(R.id.btn2);
        e_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e_btn.setBackgroundColor(Color.RED);
                s_btn.setBackgroundColor(Color.WHITE);
                android.app.FragmentTransaction tr=fm.beginTransaction();
                tr.replace(R.id.c_frame,cf2);
                th2.start();
                if(th.isAlive())
                    th.interrupt();
                tr.commit();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle2 = intent.getExtras();
                bundle2.putInt("SY",s_y);
                bundle2.putInt("SM",s_m);
                bundle2.putInt("SD",s_d);
                bundle2.putInt("EY",e_y);
                bundle2.putInt("EM",e_m);
                bundle2.putInt("ED",e_d);
                intent.putExtras(bundle2);
                setResult(RESULT_OK, intent);
                th2.interrupt();
                th.interrupt();
                finish();
            }
        });

    }
    class cThread extends Thread{
        int num;
        cThread(int i){
            num=i;
        }

        public void run(){
            while(true){
                try{
                    Thread.sleep(100);
                    bundle = intent.getExtras();
                    if(num==1){
                        s_y=bundle.getInt("YEAR");
                        s_m=bundle.getInt("MONTH");
                        s_d=bundle.getInt("DAY");
                    }
                    else if(num==2){
                        e_y=bundle.getInt("Year");
                        e_m=bundle.getInt("Month");
                        e_d=bundle.getInt("Day");
                    }
                }catch (Exception e){ }
            }
        }

    }
}

