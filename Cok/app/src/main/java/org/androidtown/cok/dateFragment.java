package org.androidtown.cok;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by LEE on 2017-05-30.
 */

public class dateFragment extends Fragment {
    TextView text;
    Context mainContext;
    CheckBox check;
    Intent intent;
    Bundle bundle;
    int count;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_date, container, false);
        text=(TextView)rootView.findViewById(R.id.text);
        check=(CheckBox)rootView.findViewById(R.id.check);
        bundle = getArguments();

        text.setText(bundle.getString("start"));

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check.isChecked()){
                    count = VoteActivtiy.data.get(bundle.getString("start"));
                    count++;
                   VoteActivtiy.data.put(bundle.getString("start"),count);
                }
                else if(!check.isChecked()){
                    count = VoteActivtiy.data.get(bundle.getString("start"));
                    count--;
                    VoteActivtiy.data.put(bundle.getString("start"),count);
                }
            }
        });

        return  rootView;
    }
    public dateFragment(Context _context){
        mainContext = _context;

    }
}
