package com.example.leisu.selectdorm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by leisu on 2017/12/13.
 */

public class SelectFinal extends Activity implements View.OnClickListener{

    private Button check;
    private String stuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_final);

        check = (Button)findViewById(R.id.sel_fin_btn);
        check.setOnClickListener(this);

        Intent intent = this.getIntent();
        stuId = intent.getStringExtra("stuId");
        Log.d("stuId",stuId);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.sel_fin_btn) {
            Intent i1 = new Intent(SelectFinal.this,StudentInfo.class);
            i1.putExtra("stuId",stuId);
            startActivity(i1);
            //finish();
        }
    }
}
