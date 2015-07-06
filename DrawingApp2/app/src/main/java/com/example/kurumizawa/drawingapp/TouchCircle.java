package com.example.kurumizawa.drawingapp;

import android.content.Context;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.util.AttributeSet;
import android.widget.Button;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class TouchCircle extends View {

    private float posx = 0.0f;  //タッチした場所のx座標
    private float posy = 0.0f;  //タッチした芭蕉のy座標
    private Path path = null;   //パス
    private Canvas cav;
    private Paint pai;
    ArrayList<Path> draw_list = new ArrayList<Path>();  //パスの保存配列

    public TouchCircle(Context context)
    {
        super(context);
    }
    public TouchCircle(Context context, AttributeSet attr)
    {
        super(context, attr);
    }

    protected void onDraw(Canvas canvas)
    {
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

        this.cav = canvas;
        this.pai = paint;

        //リストに保存
        for(int i  = 0; i < draw_list.size(); i++)
        {
            Path pt = draw_list.get(i);
            canvas.drawPath(pt, paint);
        }
        //線の描画
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
                draw_list.add(path);
                invalidate();
                break;

            default:
                break;
        }
        return true;
    }

    //戻るボタン押下
    public void onBackButton()
    {
        int i = 0;
        if(draw_list.size() > 0)
        {
            for(i  = 0; i < draw_list.size(); i++);
            Path pt = draw_list.get(i-1);
            cav.drawPath(pt, pai);
        }
    }
}

