package org.androidtown.cok;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by LEE on 2017-06-03.
 */

public class subwayActivity extends Activity {
    ImageButton k = null;
    Bitmap tmpBitmap3, tmpBitmap2;
    Drawable temp3;
    int count = 0, count2 = 0;
    Button btn;
    String station;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subway_station);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                Toast.makeText(getApplicationContext(),station,Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    public void click(View view) {
        station = view.getTag()+"";
        ImageButton imageButton = (ImageButton) view;
        Drawable temp = getApplicationContext().getResources().getDrawable(R.drawable.translation);
        Drawable temp1 = getApplicationContext().getResources().getDrawable(R.drawable.nomal_station);

        Bitmap tmpBitmap = ((BitmapDrawable) temp).getBitmap();
        Bitmap tmpBitmap1 = ((BitmapDrawable) temp1).getBitmap();
        if (count % 2 == 0) {
            Drawable temp2 = imageButton.getDrawable();
            tmpBitmap2 = ((BitmapDrawable) temp2).getBitmap();
        }
        if (count % 2 == 1) {
            temp3 = imageButton.getDrawable();
            tmpBitmap3 = ((BitmapDrawable) temp3).getBitmap();
        }

        imageButton.setImageResource(R.drawable.plus);
        if (k != null) {
            if (count2 % 2 == 0) {
                if (tmpBitmap.equals(tmpBitmap2))
                    k.setImageResource(R.drawable.translation);
                else if (tmpBitmap1.equals(tmpBitmap2))
                    k.setImageResource(R.drawable.nomal_station);
            } else if (count2 % 2 == 1) {
                if (tmpBitmap.equals(tmpBitmap3))
                    k.setImageResource(R.drawable.translation);
                else if (tmpBitmap1.equals(tmpBitmap3))
                    k.setImageResource(R.drawable.nomal_station);
            }
            count2++;
        }
        k = imageButton;
        count++;
    }
}


