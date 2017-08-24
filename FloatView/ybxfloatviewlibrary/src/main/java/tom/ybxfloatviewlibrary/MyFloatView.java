package tom.ybxfloatviewlibrary;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * Created by a55 on 2017/8/22.
 */

public class MyFloatView extends LinearLayout {

    private RecyclerView          rycv;
    private RVBaseAdapter<String> mAdapter;
    private ArrayList<String>     dataList;
    private float                 mTouchStartX;
    private float                 mTouchStartY;
    private float                 x;
    private float                 y;
    private WindowManager wm = (WindowManager) getContext().getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    //此wmParams为获取的全局变量，用以保存悬浮窗口的属性
    private WindowManager.LayoutParams wmParams;

    public MyFloatView(Context context) {
        super(context);
        EventBus.getDefault().register(this);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_float_window, this);
        rycv = (RecyclerView) findViewById(R.id.recyclerview);
        rycv.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new RVBaseAdapter<String>(context, new ArrayList<String>(), R.layout.list_item) {
            @Override
            public void bindView(RVBaseHolder holder, String s) {
                holder.setText(R.id.tv_event, s);
            }
        };
        rycv.setAdapter(mAdapter);

    }

    @Subscribe
    public void onEvent(FloatViewDataChangeEvent event) {
        mAdapter.getmDatas().add(event.data);
        mAdapter.notifyDataSetChanged();
        rycv.smoothScrollToPosition(mAdapter.getItemCount() - 1);

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