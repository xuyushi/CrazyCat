package com.example.xuyushi.crazycat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by xuyushi on 15/2/21.
 */
public class Playground extends SurfaceView implements View.OnTouchListener {
    public static int WIDTH = 100;
    public static final int ROW = 10;
    public static final int COL = 10;
    public static final int BLOCKS = 10;//障碍物个数
    private Dot matrix[][];
    private Dot cat;
    SurfaceHolder surfaceHolder;

    public Playground(Context context) {
        super(context);

        matrix = new Dot[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                matrix[i][j] = new Dot(j, i);
            }
        }

        initGame();

        setOnTouchListener(this);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                redraw();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                WIDTH = width / (COL + 1);
                redraw();

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private void redraw() {

        Canvas c = getHolder().lockCanvas();
        c.drawColor(Color.LTGRAY);
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        for (int i = 0; i < ROW; i++) {
            int offset = 0;
            if (i % 2 == 1) {
                offset = WIDTH / 2;

            }
            for (int j = 0; j < COL; j++) {
//                matrix[i][j] = new Dot(j, i);
                Dot one = getDot(j, i);
                switch (one.getStatus()) {
                    case Dot.STATUS_OFF:

                        paint.setColor(0xffeeeeee);
                        break;
                    case Dot.STATUS_ON:
                        paint.setColor(0xffffaa00);
                        break;
                    case Dot.STATUS_IN:
                        paint.setColor(0xffff0000);
                        break;
                }
                c.drawOval(new RectF(one.getX() * WIDTH + offset, one.getY() * WIDTH, (one.getX() + 1) * WIDTH + offset, (one.getY() + 1) * WIDTH), paint);
            }
        }
        getHolder().unlockCanvasAndPost(c);
    }

    private void initGame() {
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                matrix[i][j].setStatus(Dot.STATUS_OFF);
            }
        }

        for (int i = 0; i < BLOCKS; ) {

            int x = (int) (Math.random() * 1000 % COL);
            int y = (int) (Math.random() * 1000 % ROW);
            if (getDot(x, y).getStatus() == Dot.STATUS_OFF) {
                getDot(x, y).setStatus(Dot.STATUS_ON);
                i++;
                Log.d("CrazyCat", "x=" + x + ",y=" + y);
            }

        }

        cat = new Dot(4, 5);
        getDot(4, 5).setStatus(Dot.STATUS_IN);
    }

    private Dot getDot(int x, int y) {
        return matrix[y][x];
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {

//            Toast.makeText(getContext(), "X=" + event.getX() + "Y=" + event.getY(),Toast.LENGTH_SHORT).show();
            int x, y;
            y = (int) (event.getY() / WIDTH);
            if (y % 2 == 0) {
                //偶数行
                x = (int) (event.getX() / WIDTH);

            } else {
                //计数行
                x = (int) ((event.getX() - WIDTH / 2) / WIDTH);

            }
            if (x + 1 > COL || y + 1 > ROW) {
                initGame();
            } else {

                getDot(x, y).setStatus(Dot.STATUS_ON);
            }
            redraw();
        }
        return true;
    }
}
