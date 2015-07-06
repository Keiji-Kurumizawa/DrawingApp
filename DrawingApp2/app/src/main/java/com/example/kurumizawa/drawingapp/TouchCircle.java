package com.example.kurumizawa.drawingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by kurumizawa on 2015/07/03.
 */
public class TouchCircle extends View {

    public int x = 100, y = 100;

    public TouchCircle(Context context)
    {
        super(context);
    }

    protected void onDraw(Canvas canvas)
    {
        Paint paint = new Paint();
        //アンチエイリアスの設定
        paint.setAntiAlias(true);
        //円の色を設定(黒色)
        paint.setColor(Color.BLACK);
        //線の太さ
        paint.setStrokeWidth(10);
        //円の描画
        canvas.drawCircle(x, y, 50, paint);
    }

    public boolean onTouchEvent(MotionEvent e)
    {
        switch (e.getAction())
        {
            //タッチイベント(押された時)
            case MotionEvent.ACTION_DOWN:
                x = (int)e.getX();
                y = (int)e.getY();
                invalidate();
                break;

            //タッチイベント(移動中)
            case MotionEvent.ACTION_MOVE:
                x = (int)e.getX();
                y = (int)e.getY();
                invalidate();
                break;

            //タッチイベント(離れた時)
            case MotionEvent.ACTION_UP:
                performClick();
                break;

            default:
                break;
        }
        return true;
    }
}

