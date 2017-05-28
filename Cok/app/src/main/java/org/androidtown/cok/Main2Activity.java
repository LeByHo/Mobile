package org.androidtown.cok;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    Button btn_up,btn_down;
    Button Fbutton,Cbutton;
    String s;
    String f;
    TextView text;
    EditText title;
    TextView text2;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setup();
        Cbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent clintent = new Intent(Main2Activity.this, Calendar.class);
                Bundle bundle = new Bundle();
                clintent.putExtras(bundle);
                startActivityForResult(clintent,2);
            }
        });
        Fbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                bundle.putString("title", title.getText().toString());
                bundle.putString("number", count + "");
                bundle.putString("start",s);
                bundle.putString("finish",f);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        Toast.makeText(getApplicationContext(),bundle.getInt("YEAR")+"",Toast.LENGTH_SHORT).show();
        text2.setVisibility(View.VISIBLE);
        text2.setText("시작일 :"+bundle.getInt("YEAR")+" 년 "+bundle.getInt("MONTH")+" 월 "+bundle.getInt("DAY")+" 일"+"\n"+
                "종료일 :"+bundle.getInt("Year")+" 년 "+bundle.getInt("Month")+" 월 "+bundle.getInt("Day")+" 일");
        if(bundle.getInt("MONTH")<10)
         s=bundle.getInt("YEAR")+"-"+"0"+bundle.getInt("MONTH")+"-"+bundle.getInt("DAY");
        else{
            s=bundle.getInt("YEAR")+"-"+bundle.getInt("MONTH")+"-"+bundle.getInt("DAY");
        }
        if(bundle.getInt("Month")<10){
            f=bundle.getInt("Year")+"-"+"0"+bundle.getInt("Month")+"-"+bundle.getInt("Day");
        }
        else{
            f=bundle.getInt("Year")+"-"+bundle.getInt("Month")+"-"+bundle.getInt("Day");
        }
        Cbutton.setVisibility(View.INVISIBLE);
    }
/*

    private void modify(){
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        Toast.makeText(getApplicationContext(),bundle.getInt("YEAR")+"",Toast.LENGTH_SHORT).show();
        text2.setVisibility(View.VISIBLE);
        text2.setText("시작일 :"+bundle.getInt("YEAR")+" 년 "+bundle.getInt("MONTH")+" 월 "+bundle.getInt("DAY")+" 일"+"\n"+
                "종료일 :"+bundle.getInt("Year")+" 년 "+bundle.getInt("Month")+" 월 "+bundle.getInt("Day")+" 일");
        Cbutton.setVisibility(View.INVISIBLE);
    }
    }
*/


    private void setup() {
        title = (EditText)findViewById(R.id.editText3);
        btn_up = (Button) findViewById(R.id.buttonp);
        btn_down = (Button) findViewById(R.id.buttonm);
        text = (TextView) findViewById(R.id.count);
        Fbutton = (Button)findViewById(R.id.finish);
        Cbutton = (Button)findViewById(R.id.button);
        btn_up.setOnClickListener(listener);
        btn_down.setOnClickListener(listener);
        text2 = (TextView)findViewById(R.id.c_text);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.buttonp:
                    count++;
                    text.setText("" + count);
                    break;
                case R.id.buttonm:
                    count--;
                    if(count<0){
                        Toast.makeText(getApplicationContext(),"음수ㄴㄴ",Toast.LENGTH_SHORT).show();
                       count=0;
                        break;
                    }
                    text.setText("" + count);
                    break;
            }
        }
    };
}
