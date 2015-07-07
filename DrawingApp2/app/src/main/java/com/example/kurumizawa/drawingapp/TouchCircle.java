package com.example.kurumizawa.drawingapp;

import android.content.Context;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.util.AttributeSet;
import android.widget.Button;

import java.util.ArrayList;
import java.util.jar.Attributes;
import android.os.Handler;

public class TouchCircle extends View {

    private float posx = 0.0f;  //タッチした場所のx座標
    private float posy = 0.0f;  //タッチした芭蕉のy座標
    private Path path = null;   //パス
    private boolean backFlag;
    private boolean nextFlag;
    private int listpotision;
    private int nextpotision;
    private final Handler handler = new Handler();
    ArrayList<Path> draw_list = new ArrayList<Path>();  //パスの配列
    ArrayList<Path> save_draw_list = new ArrayList<Path>(); //パスの保存配列

    public TouchCircle(Context context, AttributeSet attr)
    {
        super(context, attr);
        backFlag = false;
        nextFlag = false;
        listpotision = 0;
        nextpotision = 0;
    }

    //戻るボタン
    public void onBackButton(boolean flg)
    {
        backFlag = flg;
        invalidate();
    }

    //進むボタン
    public void onNextButton(boolean flg)
    {
        nextFlag = flg;
        invalidate();
    }

    protected void onDraw(Canvas canvas)
    {
        Log.e("VIEW","onDraw");
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

        //リストに保存
        for(int i  = 0; i < draw_list.size(); i++)
        {
            Path pt = draw_list.get(i);
            canvas.drawPath(pt, paint);
            Log.e("VIEW", "onDraw:"+pt);
        }
        //線の描画
        if(path != null)
        {
            canvas.drawPath(path, paint);
        }

        //戻る機能
        if(backFlag == true)
        {
            if(listpotision > 0)
            {
                Path pt = draw_list.get(listpotision-1);
                Path spt = new Path(pt);
                Log.e("HOGE","back b:"+spt.isEmpty()+":"+pt.isEmpty()+":"+pt);
                pt.reset();
                Log.e("HOGE", "back a:" + spt.isEmpty()+":"+spt.isEmpty()+":"+spt);
                save_draw_list.add(spt);
                nextpotision++;
                draw_list.remove(pt);
                invalidate();
                listpotision--;
            }
            backFlag = false;
        }

        //進む機能
        if(nextFlag == true)
        {
            if (nextpotision > 0)
            {
                Path pt = save_draw_list.get(nextpotision-1);
                Path spt = new Path(pt);
                Log.e("HOGE", "next:" + pt.isEmpty()+":"+pt);
                draw_list.add(pt);
                listpotision++;
                save_draw_list.remove(pt);
                Log.e("HOGE", "next:"+spt.isEmpty()+":"+spt);
                invalidate();
                nextpotision--;
            }
            nextFlag = false;
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
                listpotision++;
                invalidate();
                break;

            default:
                break;
        }
        return true;
    }
}

