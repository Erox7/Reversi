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
    private int nCols = 0;
    private int time = 25;

    public ButtonAdapter(Context c, Integer numCols) {
        mContext = c;
        count = numCols * numCols;
        nCols = numCols;
        //Pasarle las referencias a los TV para poder modificarlos.
        //Contador de fichas blancas i negras, como atributos de la classe, que se suman i restan cada vez que modifico una pieza
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
    // We will be the white ones.
    public View getView(int position, View convertView, ViewGroup parent) {
        Button bn;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            bn = new Button(mContext);
            bn.setLayoutParams(new GridView.LayoutParams(100, 100));
            bn.setPadding(8, 8, 8, 8);

            if(position == (((nCols/2)*nCols)+(nCols/2))
                    || position == (((nCols/2)*nCols)+(nCols/2)-nCols-1) ){

                bn.setBackgroundResource(R.drawable.button_white);
                bn.setEnabled(false);
                bn.setId(position);

            }else if(position == (((nCols/2)*nCols) + (nCols/2) - 1 )
                    || position == (((nCols/2)*nCols) + nCols/2 - nCols )){

                bn.setBackgroundResource(R.drawable.button_black);
                bn.setEnabled(false);
                bn.setId(position);

            }else if(position == ((nCols/2)*nCols)+(nCols/2)-2 ||
                    position == ((nCols/2)*nCols)+(nCols/2)-1+nCols ||
                    position == ((nCols/2)*nCols)+(nCols/2)-(2*nCols) ||
                    position == ((nCols/2)*nCols)+(nCols/2)-nCols+1 ){

                bn.setBackgroundResource(R.drawable.button_posible);
                bn.setOnClickListener(new MyOnClickListener(position));
                bn.setEnabled(true);

            }else{

                bn.setBackgroundResource(R.drawable.button_normal);
                bn.setEnabled(false);
            }
        } else {
            bn = (Button) convertView;
            bn.setEnabled(false);
        }



        return bn;
    }

    private class MyOnClickListener implements View.OnClickListener{
        private final int position;

        public MyOnClickListener(int position) {
            this.position =  position;
        }

        @Override
        public void onClick(View v) {
            //all the click IA somefunction(position)
            Button b = (Button) v;
            b.setBackgroundResource(R.drawable.button_white);
            //TIRADA DEL JUGADOR HUMANO




            //TIRADA DEL JUGADOR PC
            notifyDataSetChanged();
        }
    }
}