package com.example.xuyushi.crazycat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class Settings extends Activity implements View.OnClickListener {

    int blocks = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViewById(R.id.easy).setOnClickListener(this);
        findViewById(R.id.medium).setOnClickListener(this);
        findViewById(R.id.hard).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.easy:
                blocks = 18;
                break;
            case R.id.medium:
                blocks = 14;
                break;
            case R.id.hard:
                blocks = 10;
                break;
        }

        Intent intent = new Intent();
        intent.putExtra("blocksNum", blocks);
        setResult(RESULT_OK,intent);
        finish();
    }
}
