package org.androidtown.cok;

import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by LEE on 2017-06-03.
 */

public class subwayActivity extends Activity  {
    View mainView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_station);
        mainView =(RelativeLayout)findViewById(R.id.linearLayout);

        Button buttonZoomOut = (Button)findViewById(R.id.buttonZoomOut);
        Button buttonNormal = (Button)findViewById(R.id.buttonNormal);
        Button buttonZoomIn = (Button)findViewById(R.id.buttonZoomIn);

        buttonZoomOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                zoom(0.5f,0.5f,new PointF(0,0));
            }
        });
        buttonNormal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                zoom(1f,1f,new PointF(0,0));
            }
        });
        buttonZoomIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                zoom(2f,2f,new PointF(0,0));
            }
        });
    }

    /** zooming is done from here */
    public void zoom(Float scaleX,Float scaleY,PointF pivot){
        mainView.setPivotX(pivot.x);
        mainView.setPivotY(pivot.y);
        mainView.setScaleX(scaleX);
        mainView.setScaleY(scaleY);
    }
}


