package org.androidtown.cok;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

/**
 * Created by LEE on 2017-05-30.
 */

public class VoteActivtiy extends AppCompatActivity {
    Intent intent;
    Bundle bundle;
    String start;
    String end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vote);
        intent=getIntent();
        bundle=intent.getExtras();
        start=bundle.getString("START");
        end = bundle.getString("FINISH");
        for(int i=0;i<30;i++){
            makefragment(start);
        }

    }
    public void makefragment(String start) {
        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction tr = fm.beginTransaction();
        dateFragment cf = new dateFragment(VoteActivtiy.this);
        Bundle bundle2 = new Bundle();

        bundle2.putString("start",start);

        cf.setArguments(bundle2);
        tr.add(R.id.sframe,cf,"co");
        tr.commit();
    }

}
