package org.androidtown.cok;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by LEE on 2017-05-30.
 */

public class dateFragment extends Fragment {
    TextView text;
    Context mainContext;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_date, container, false);
        text=(TextView)rootView.findViewById(R.id.text);
        Bundle bundle = getArguments();
        text.setText(bundle.getString("start"));
        return  rootView;
    }
    public dateFragment(Context _context){
        mainContext = _context;
    }
}
