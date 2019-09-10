package cn.tencent.DiscuzMob.ui.menu;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import java.util.List;

import cn.tencent.DiscuzMob.R;


/**
 * Created by AiWei on 2016/5/31.
 */
public class SimpleOptionsPopup {

    private ListPopupWindow mListPopupWindow;
    private SimpleMenuAdapter mAdapter;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public SimpleOptionsPopup(Context context) {
        this.mListPopupWindow = new ListPopupWindow(context);
        this.mListPopupWindow.setModal(true);
        this.mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(parent, view, position, id);
                }
            }
        });
    }

    public void setAdapter(SimpleMenuAdapter adapter) {
        this.mAdapter = adapter;
        if (mListPopupWindow != null)
            this.mListPopupWindow.setAdapter(adapter);
    }

    void setWidth(int width) {
        this.mListPopupWindow.setWidth(width);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void selectItemAtPosition(int position, boolean select) {
        if (mListPopupWindow != null && mAdapter != null) {
            Menu menu = ((Menu) mListPopupWindow.getListView().getItemAtPosition(position));
            if (menu != null) {
                menu.setState(select ? android.R.attr.state_selected : android.R.attr.state_empty);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    public void setAnchorView(View view) {
        if (mListPopupWindow != null)
            this.mListPopupWindow.setAnchorView(view);
    }

    public boolean isShowing() {
        return mListPopupWindow != null && mListPopupWindow.isShowing();
    }

    public void show() {
        if (mListPopupWindow != null)
            mListPopupWindow.show();
    }

    public void dissmiss() {
        if (mListPopupWindow != null)
            mListPopupWindow.dismiss();
    }

    public static class Menu {

        int titleResId;
        SparseIntArray stateTitleList;//android.R.attr.state_empty,android.R.attr.state_checked
        int iconResId;
        int iconAlign;
        int currentState = -1;

        private Menu(int iconResId, int iconAlign, int titleResId) {
            this.iconResId = iconResId;
            this.iconAlign = iconAlign;
            this.titleResId = titleResId;
        }

        private Menu(int iconResId, int iconAlign, Integer[] titleResId) {
            this(0, iconResId, iconAlign);
            if (titleResId != null && titleResId.length > 1) {
                this.currentState = titleResId[0];
                this.titleResId = titleResId[0];
                this.stateTitleList = new SparseIntArray();
                this.stateTitleList.put(android.R.attr.state_empty, titleResId[0]);
                this.stateTitleList.put(android.R.attr.state_selected, titleResId[1]);
            }
        }

        void setState(int state) {
            this.currentState = state;
        }

    }

    private static class SimpleMenuAdapter extends BaseAdapter {

        Context context;
        SparseArray<Menu> menu;

        SimpleMenuAdapter(Context context, SparseArray<Menu> menu) {
            this.context = context;
            this.menu = menu;
        }

        @Override
        public int getCount() {
            return menu != null ? menu.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return menu.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.simple_menu_item, parent, false);
                convertView.setTag(convertView.findViewById(R.id.tv_label));
            }
            TextView label = ((TextView) convertView.getTag());
            Menu value = (Menu) getItem(position);
            label.setCompoundDrawablesWithIntrinsicBounds(value.iconAlign < 0 ? value.iconResId : 0, 0, value.iconAlign > 0 ? value.iconResId : 0, 0);
            label.setSelected(value.currentState == android.R.attr.state_selected);
            if (value.stateTitleList != null) {
                label.setText(context.getResources().getString(value.stateTitleList.get(value.currentState)));
            } else {
                label.setText(context.getResources().getString(value.titleResId));
            }
            return convertView;
        }

    }

    public static class Builder {

        private Context mContext;
        private int mWidthResId;
        private int[] mIcon;
        private int mIconAlign;
        private List<?> mTitleResId;
        private AdapterView.OnItemClickListener mListener;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setWidth(int widthResId) {
            this.mWidthResId = widthResId;
            return this;
        }

        public Builder setIcon(int[] icon, int iconAlign) {
            this.mIcon = icon;
            this.mIconAlign = iconAlign;
            return this;
        }

        public Builder setTitle(List<?> titleResId) {
            this.mTitleResId = titleResId;
            if (titleResId == null || titleResId.size() == 0)
                throw new IllegalArgumentException("titleResId's length can't be 0");
            return this;
        }

        public Builder setOnItemClickListener(AdapterView.OnItemClickListener listener) {
            this.mListener = listener;
            return this;
        }

        public SimpleOptionsPopup build() {
            SimpleOptionsPopup instance = new SimpleOptionsPopup(mContext);
            if (mTitleResId != null) {
                SparseArray<Menu> menu = new SparseArray<>();
                for (int i = 0, size = mTitleResId.size(); i < size; i++) {
                    Object resId = mTitleResId.get(i);
                    if (resId instanceof Integer[]) {
                        menu.put(i, new Menu(mIcon[i], mIconAlign, (Integer[]) mTitleResId.get(i)));
                    } else {
                        menu.put(i, new Menu(mIcon[i], mIconAlign, (Integer) mTitleResId.get(i)));
                    }
                }
                instance.setAdapter(new SimpleMenuAdapter(mContext, menu));
            }
            instance.setOnItemClickListener(mListener);
            instance.setWidth((int) mContext.getResources().getDimension(mWidthResId));
            return instance;
        }

    }

}
