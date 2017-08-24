package tom.ybxfloatviewlibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by a55 on 2017/8/22.
 */

public abstract class RVBaseAdapter<T> extends RecyclerView.Adapter<RVBaseHolder> {

    protected ArrayList<T> mDatas ;

    protected Context mContext ;

    private LayoutInflater mLayoutInflater ;

    private int mResId ;

    private View.OnClickListener mItemListener;
    private int                  mClickId ;
    private View.OnClickListener mViewListener;

    public RVBaseAdapter(Context context , ArrayList<T> arrayList,int resId){
        mContext = context ;
        mLayoutInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<>(arrayList);
        mResId = resId ;
    }

    @Override
    public RVBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RVBaseHolder holder = new RVBaseHolder(mLayoutInflater.inflate(mResId, parent, false));
        if (mItemListener != null){
            holder.itemView.setOnClickListener(this.mItemListener);
        }
        if (mViewListener != null){
            holder.getView(mClickId).setOnClickListener(mViewListener);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RVBaseHolder holder, int position) {
        bindView(holder,mDatas.get(position));
    }

    public abstract void bindView(RVBaseHolder holder, T t) ;

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public void setOnItemClickListener(View.OnClickListener onItemClickListener){
        this.mItemListener = onItemClickListener ;
    }

    public void setOnClickListener(int id,View.OnClickListener clickListener){
        this.mClickId = id ;
        this.mViewListener = clickListener;
    }

    public void addDatas(ArrayList<T> arrayList){
        mDatas.addAll(arrayList);
        notifyDataSetChanged();
    }

    public void changeDatas(ArrayList<T> arrayList){
        mDatas.clear();
        addDatas(arrayList);
    }

    public ArrayList<T> getmDatas() {
        return mDatas;
    }

    public void setmDatas(ArrayList<T> mDatas) {
        this.mDatas = mDatas;
    }
}

