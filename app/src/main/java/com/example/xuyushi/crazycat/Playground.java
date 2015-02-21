package com.example.xuyushi.crazycat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by xuyushi on 15/2/21.
 */
public class Playground extends SurfaceView {
    SurfaceHolder surfaceHolder;
    public Playground(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                redraw();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private void redraw(){

        Canvas c = getHolder().lockCanvas();
        c.drawColor(Color.BLUE);
        getHolder().unlockCanvasAndPost(c);
    }
}
