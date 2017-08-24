package tom.ybxfloatviewlibrary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by a55 on 2017/8/22.
 */

public class MyFloatView extends LinearLayout {

    private FrameLayout contentContainer;   // 添加自定义view的容器
    private float       mTouchStartX;
    private float       mTouchStartY;
    private float       x;
    private float       y;
    private WindowManager wm = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    //此wmParams为获取的全局变量，用以保存悬浮窗口的属性
    private WindowManager.LayoutParams wmParams;

    public MyFloatView(Context context, View contentView) {
        super(context);
        initView(context, contentView);
    }

    private void initView(Context context, View contentView) {
        LayoutInflater.from(context).inflate(R.layout.layout_for_float_window, this);
        contentContainer = (FrameLayout) findViewById(R.id.content_view);
        if (contentView != null){
            contentContainer.addView(contentView);
        }

    }

    public void setWmParams(WindowManager.LayoutParams wmParams) {
        this.wmParams = wmParams;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY() - 25; //25是系统状态栏的高度
        Log.i("currP", "currX" + x + "====currY" + y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //获取相对View的坐标，即以此View左上角为原点
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                updateViewPosition();
                mTouchStartX = mTouchStartY = 0;
                break;
        }
        return true;
    }

    private void updateViewPosition() {
        //更新浮动窗口位置参数
        wmParams.x = (int) (x - mTouchStartX);
        wmParams.y = (int) (y - mTouchStartY);
        wm.updateViewLayout(this, wmParams);
    }
}