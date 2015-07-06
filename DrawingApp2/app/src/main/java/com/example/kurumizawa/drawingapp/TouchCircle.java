package com.example.kurumizawa.drawingapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class TouchCircle extends View {

    private float posx = 0.0f;  //タッチした場所のx座標
    private float posy = 0.0f;  //タッチした芭蕉のy座標
    private Path path = null;   //パス

    public TouchCircle(Context context)
    {
        super(context);
    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Paint paint = new Paint();
        //アンチエイリアスの設定
        paint.setAntiAlias(true);
        //円の色を設定(黒色)
        paint.setColor(Color.BLACK);
        //描画は線のみ
        paint.setStyle(Paint.Style.STROKE);
        //線の太さ
        paint.setStrokeWidth(6);
        //線の端を丸くする
        paint.setStrokeCap(Paint.Cap.ROUND);
        //線の繋ぎ目を丸くする
        paint.setStrokeJoin(Paint.Join.ROUND);
        //円の描画
        if(path != null)
        {
            canvas.drawPath(path, paint);
        }
    }

    public boolean onTouchEvent(MotionEvent e)
    {
        //イベントごとに処理を設定する
        switch (e.getAction())
        {
            //タッチイベント(押された時)
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                posx = e.getX();
                posy = e.getY();
                path.moveTo(e.getX(), e.getY());
                break;

            //タッチイベント(移動中)
            case MotionEvent.ACTION_MOVE:
                posx += (e.getX() - posx) / 1.4f;
                posy += (e.getY() - posy) / 1.4f;
                path.lineTo(posx, posy);
                invalidate();
                break;

            //タッチイベント(離れた時)
            case MotionEvent.ACTION_UP:
                path.lineTo(e.getX(), e.getY());
                break;

            default:
                break;
        }
        return true;
    }
}

