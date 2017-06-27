package cn.muse.findyou.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;

import cn.muse.findyou.R;

/**
 * Created by Sun on 2017/6/24.
 */

public class MyButton extends android.support.v7.widget.AppCompatButton{

    private Drawable drawable;
    private float drawableWidth;
    private float drawableHeight;
    private float height;

    // 创建控件时
    public MyButton (Context context , AttributeSet set){
        super(context,set);
        // 获取xml的属性
        TypedArray a = context.obtainStyledAttributes(set, R.styleable.myClare);
        // 获取xml的对应的图片
        drawable = a.getDrawable(R.styleable.myClare_drawable);
        drawableHeight = drawable.getIntrinsicHeight();
        drawableWidth = drawable.getIntrinsicWidth();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取控件高度
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    // 绘制控件时
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 设置边界
        drawable.setBounds(50,10,(int)(drawableWidth*(height-20)/drawableHeight+50),(int)(height-10));
        //
        drawable.draw(canvas);
    }
}
