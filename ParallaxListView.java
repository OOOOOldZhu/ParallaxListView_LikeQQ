package com.itheima.parallax97;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by lxj on 2017/1/5.
 */

public class ParallaxListView extends ListView {
    public ParallaxListView(Context context) {
        super(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    int originalHeight;//最初的高度，就是120dp
    ImageView image;
    int maxHeight;//小孩的最大高度,就是设定为图片本身的高度
    public void setParallaxImage(ImageView image){
        this.image = image;

        //从dimens文件中读取image_height的值，并自动转为像素
        originalHeight = getResources().getDimensionPixelSize(R.dimen.image_height);

        maxHeight = this.image.getDrawable().getIntrinsicHeight();
    }

    /**
     * 该方法是在listview滑动到头的时候执行，并且可以在该方法中获取到手指移动的距离
     * @param deltaY    手指移动的距离,顶部到头是负值，底部到头是正值
     * @param scrollY   scrollTo滚动的坐标
     * @param maxOverScrollY    listview滑到头之后可以继续滑动的最大距离
     * @param isTouchEvent   是否是手指拖动到头，true：是， false：表示是靠惯性滑动到头
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY,
                                   boolean isTouchEvent) {

        //如果是手指拖动到头，并且是顶部到头,其他忽略不处理
        if(isTouchEvent && deltaY<0){
            //让ImageView的高度随着手指移动而增高
            int newHeight = image.getHeight() + Math.abs(deltaY)/3;
            //限制newHeight
            if(newHeight>maxHeight){
                newHeight = maxHeight;
            }

            ViewGroup.LayoutParams params = image.getLayoutParams();
            params.height = newHeight;
            image.setLayoutParams(params);
        }

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_UP){
            //让ImageView的高度缓慢恢复到最初的120高度
            ValueAnimator animator = ValueAnimator.ofInt(image.getHeight(),originalHeight);
            //监听动画值的变化，实现自己的动画逻辑
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    //我们需要将value设置给高度
                    ViewGroup.LayoutParams params = image.getLayoutParams();
                    params.height = value;
                    image.setLayoutParams(params);
                }
            });
            animator.setInterpolator(new OvershootInterpolator());
            animator.setDuration(500);
            animator.start();

        }
        return super.onTouchEvent(ev);
    }
}
