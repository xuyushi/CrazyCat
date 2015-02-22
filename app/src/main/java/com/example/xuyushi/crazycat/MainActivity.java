package com.example.xuyushi.crazycat;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        Playground playground = new Playground(this);
        setContentView(playground);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.d("CrazyCat","setting pressed");
            Intent intent = new Intent(MainActivity.this, Settings.class);

            startActivityForResult(intent, 1); //请求码：1
            return true;
        }
        if (id == R.id.share) {
             Log.d("CrazyCat","share pressed");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    int blocks = data.getIntExtra("blocksNum",10);
                    Log.d("CrazyCat", "blcoks=" + blocks);
                    Playground playground = new Playground(this);
                    playground.BLOCKS = blocks;
                    playground.initGame();
                    setContentView(playground);
                }
        }
    }
}
