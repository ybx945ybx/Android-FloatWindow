package tom.ybxfloatviewlibrary;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by a55 on 2017/8/22.
 */

public class MyWindowManager {
    private static MyFloatView                myFloatView;
    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager              mWindowManager;
    /**
     * 悬浮窗View的参数
     */
    private static WindowManager.LayoutParams mWindowParams;

//    public static void createFloatView(Context context, View contentView, int gravity, int width, int height, int x, int y) {

    public static void createFloatView(Context context, View contentView, int width, int height) {

        WindowManager windowManager = getWindowManager(context);
        int           screenWidth   = windowManager.getDefaultDisplay().getWidth();
        int           screenHeight  = windowManager.getDefaultDisplay().getHeight();
        if (myFloatView == null) {

            myFloatView = new MyFloatView(context, contentView);
            if (mWindowParams == null) {
                mWindowParams = new WindowManager.LayoutParams();
                int type = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    //解决Android 7.1.1起不能再用Toast的问题（先解决crash）
                    if (Build.VERSION.SDK_INT > 24) {
                        type = WindowManager.LayoutParams.TYPE_PHONE;
                    } else {
                        type = WindowManager.LayoutParams.TYPE_TOAST;
                    }
                } else {
                    type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                mWindowParams.type = type;
                mWindowParams.format = PixelFormat.RGBA_8888;
                mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                //调整悬浮窗口至左上角
                mWindowParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                //设置悬浮窗口长宽数据
                mWindowParams.width = width;
                mWindowParams.height = height;
                //以屏幕左上角为原点，设置x、y初始值
                mWindowParams.x = screenWidth;
                mWindowParams.y = screenHeight / 2;
            }
            myFloatView.setWmParams(mWindowParams);
            windowManager.addView(myFloatView, mWindowParams);
        }
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context 必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    /**
     * 是否有悬浮窗显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return myFloatView != null ;
    }

    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void removeWindow(Context context) {
        if (myFloatView != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(myFloatView);
            myFloatView = null;
        }
    }

}
