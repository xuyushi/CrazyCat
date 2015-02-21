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
    //    public int k = 1;
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

    private boolean isAtEdge(Dot dot) {

        if (dot.getX() * dot.getY() == 0 || dot.getX() + 1 == COL || dot.getY() + 1 == ROW) {
            return true;
        }
        return false;
    }

    private Dot getNeighbour(Dot dot, int dir) {

        switch (dir) {
            case 1:
                return getDot(dot.getX() - 1, dot.getY());
            case 2:
                if (dot.getY() % 2 == 0) {
                    //偶数行 注意 行数从0到row-1
                    return getDot(dot.getX() - 1, dot.getY() - 1);
                } else {
                    //基数行
                    return getDot(dot.getX(), dot.getY() - 1);
                }
            case 3:
                if (dot.getY() % 2 == 0) {
                    return getDot(dot.getX(), dot.getY() - 1);
                } else {
                    return getDot(dot.getX() + 1, dot.getY() - 1);
                }
            case 4:
                return getDot(dot.getX() + 1, dot.getY());
            case 5:
                if (dot.getY() % 2 == 0) {
                    return getDot(dot.getX(), dot.getY() + 1);
                } else {
                    return getDot(dot.getX() + 1, dot.getY() + 1);
                }
            case 6:
                if (dot.getY() % 2 == 0) {
                    return getDot(dot.getX() - 1, dot.getY() + 1);
                } else {
                    return getDot(dot.getX(), dot.getY() + 1);
                }
            default:
                return null;
        }
    }

    private int getDistance(Dot dot, int dir) {

        int distance = 0;
        Dot ori = dot;
        Dot next;
        while (true) {
            next = getNeighbour(ori, dir);
            if (next.getStatus() == Dot.STATUS_ON) {
                return distance * (-1);
            }
            if (isAtEdge(next)) {
                distance++;
                return distance;
            }
            distance++;
            ori = next;
        }
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
//                for (int i = 1; i < 7; i++) {
//                    Log.d("CrazyCat", i + ":" + getDistance(cat, i) + "\n");
//                }

//                getNeighbour(cat, k).setStatus(Dot.STATUS_IN);
//                redraw();
//                k++;
            } else {

                getDot(x, y).setStatus(Dot.STATUS_ON);
            }
            redraw();
        }
        return true;
    }
}
