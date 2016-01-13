package icss.wz.manager.adapter.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import icss.wz.manager.listener.OnItemClickListener;
import icss.wz.manager.listener.OnItemLongClickListener;


/**
 * this is a BaseAdapter for RecyclerView
 * author lizhangqu
 * version 1.0
 * date 2015/6/3.
 */
public abstract class BaseRecyclerViewAdapter<T, VH extends BaseRecyclerViewAdapter.SparseArrayViewHolder> extends
        RecyclerView.Adapter<VH> {

    /**
     * click listener
     */
    protected OnItemClickListener mOnItemClickListener;
    /**
     * long click listener
     */
    protected OnItemLongClickListener mOnItemLongClickListener;
    /**
     * data
     */
    protected List<T> mList;

    /**
     * @param list the datas to attach the adapter
     */
    public BaseRecyclerViewAdapter(List<T> list) {
        mList = list;
    }

    /**
     * get a item by index
     *
     * @param position
     * @return
     */
    protected T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void add(@NonNull T object) {
        mList.add(object);
        notifyDataSetChanged();
    }

    public long getItemId(int position) {
        return position;
    }

    public void add(@NonNull int index, @NonNull T object) {
        mList.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(@NonNull Collection<? extends T> collection) {
        if (collection != null) {
            mList.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void replace(@NonNull int index, @NonNull T object) {
        mList.remove(index);
        mList.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(@NonNull T... items) {
        addAll(Arrays.asList(items));
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public boolean contains(T object) {
        if (mList.contains(object)) {
            return true;
        }
        return false;
    }

    public void remove(T object) {
        mList.remove(object);
        notifyDataSetChanged();
    }

    public List<T> getAll() {
        return mList;
    }

    /**
     * set a long click listener
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * set a click listener
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * inflate a view by viewgroup ,id ,etc
     *
     * @param viewGroup
     * @param layoutId
     * @return
     */
    protected View inflateItemView(ViewGroup viewGroup, int layoutId) {
        return inflateItemView(viewGroup, layoutId, false);
    }

    /**
     * inflate a view by viewgroup ,id ,etc
     *
     * @param viewGroup
     * @param layoutId
     * @param attach
     * @return
     */
    protected View inflateItemView(ViewGroup viewGroup, int layoutId, boolean attach) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, attach);
    }


    /**
     * a final function to avoid you override
     * use template design pattern
     *
     * @param vh
     * @param position
     */
    @Override
    public final void onBindViewHolder(VH vh, int position) {
        bindDataToItemView(vh, position);
        bindItemViewClickListener(vh, position);
    }


    /**
     * bind data to itemview
     *
     * @param vh       viewholder
     * @param position position
     */
    protected abstract void bindDataToItemView(VH vh, int position);


    /**
     * bind click listner to itemview
     *
     * @param vh       viewholder
     * @param position position
     */
    protected final void bindItemViewClickListener(VH vh, final int position) {
        if (mOnItemClickListener != null) {
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(view, position);
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            vh.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onLongClick(v, position);
                    return true;
                }
            });
        }
    }


    public static class SparseArrayViewHolder extends RecyclerView.ViewHolder {
        private final SparseArray<View> views;

        public SparseArrayViewHolder(View itemView) {
            super(itemView);
            views = new SparseArray<View>();
        }


        public <T extends View> T getView(int id) {
            View view = views.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                views.put(id, view);
            }
            return (T) view;
        }

        public SparseArrayViewHolder setText(int viewId, String value) {
            TextView view = getView(viewId);
            view.setText(value);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setText(int viewId, CharSequence text) {
            TextView view = getView(viewId);
            view.setText(text);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setTextColor(int viewId, int textColor) {
            TextView view = getView(viewId);
            view.setTextColor(textColor);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setImageResource(int viewId, int imageResId) {
            ImageView view = getView(viewId);
            view.setImageResource(imageResId);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setBackgroundColor(int viewId, int color) {
            View view = getView(viewId);
            view.setBackgroundColor(color);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setBackgroundResource(int viewId, int backgroundRes) {
            View view = getView(viewId);
            view.setBackgroundResource(backgroundRes);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setVisible(int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
            View view = getView(viewId);
            view.setOnClickListener(listener);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
            View view = getView(viewId);
            view.setOnTouchListener(listener);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
            View view = getView(viewId);
            view.setOnLongClickListener(listener);
            return SparseArrayViewHolder.this;
        }

        public SparseArrayViewHolder setTag(int viewId, Object tag) {
            View view = getView(viewId);
            view.setTag(tag);
            return SparseArrayViewHolder.this;
        }

    }
}
