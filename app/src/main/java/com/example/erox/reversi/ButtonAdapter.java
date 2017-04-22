package com.example.erox.reversi;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Erox on 20/04/2017.
 */

public class ButtonAdapter extends BaseAdapter {
    private Context mContext;
    private int count = 0;

    public ButtonAdapter(Context c, Integer numCols) {
        mContext = c;
        count = numCols * numCols;
    }

    public int getCount() {
        return count;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new Button for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Button bn;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            bn = new Button(mContext);
            bn.setLayoutParams(new GridView.LayoutParams(100, 100));
            bn.setPadding(8, 8, 8, 8);
        } else {
            bn = (Button) convertView;
        }

        bn.setBackgroundResource(R.drawable.button_normal);
        bn.setId(position);
        bn.setOnClickListener(new MyOnClickListener(position));
        return bn;
    }

    private class MyOnClickListener implements View.OnClickListener {
        private final int position;

        public MyOnClickListener(int position) {
            this.position =  position;
        }

        @Override
        public void onClick(View v) {
            //all the click IA
        }
    }
}