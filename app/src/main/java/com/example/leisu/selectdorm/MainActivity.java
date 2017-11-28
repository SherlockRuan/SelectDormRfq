package com.example.leisu.selectdorm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by leisu on 2017/10/27.
 */

public class MainActivity extends Activity implements View.OnClickListener{

    private ImageView sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dorm_info);

        sign = (ImageView)findViewById(R.id.wel_btn_sign);
        sign.setOnClickListener(this);
    }

    public void onClick(View view) {

        if (view.getId() == R.id.wel_btn_sign) {

        }
    }
}
