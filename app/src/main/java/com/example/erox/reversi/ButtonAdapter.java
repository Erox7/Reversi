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
    private int count ;
    private int nCols;
    private int time = 25;
    private MoveController mController;

    public ButtonAdapter(Context c, Integer numCols) {
        mContext = c;
        count = numCols * numCols;
        nCols = numCols;
        mController = new MoveController(numCols);
        mController.setArray();
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

            for(int i = 0; i < nCols;i++){
                for(int j = 0; j < nCols;j++){
                    if(position == (j + (i*nCols))){

                        int num = mController.positions[i][j];

                        //0 will be a normal position, 1 a posible position, 2 white, 3 black
                        if(num == 0){

                            bn.setBackgroundResource(R.drawable.button_normal);
                            bn.setClickable(false);

                        }else if(num == 1){

                            bn.setBackgroundResource(R.drawable.button_posible);
                            bn.setOnClickListener(new MyOnClickListener(position));
                            bn.setClickable(true);
                            bn.setEnabled(true);

                        }else if(num == 2){

                            bn.setBackgroundResource(R.drawable.button_white);
                            bn.setClickable(false);

                        }else if(num == 3){

                            bn.setBackgroundResource(R.drawable.button_black);
                            bn.setClickable(false);

                        }
                    }
                }
            }
            /*if(position == (((nCols/2)*nCols)+(nCols/2))
                    || position == (((nCols/2)*nCols)+(nCols/2)-nCols-1) ){

                bn.setBackgroundResource(R.drawable.button_white);
                bn.setClickable(false);
                bn.setId(position);

            }else if(position == (((nCols/2)*nCols) + (nCols/2) - 1 )
                    || position == (((nCols/2)*nCols) + nCols/2 - nCols )){

                bn.setBackgroundResource(R.drawable.button_black);
                bn.setClickable(false);
                bn.setId(position);

            }else if(position == ((nCols/2)*nCols)+(nCols/2)-2 ||
                    position == ((nCols/2)*nCols)+(nCols/2)-1+nCols ||
                    position == ((nCols/2)*nCols)+(nCols/2)-(2*nCols) ||
                    position == ((nCols/2)*nCols)+(nCols/2)-nCols+1 ){

                bn.setBackgroundResource(R.drawable.button_posible);
                bn.setOnClickListener(new MyOnClickListener(position));
                bn.setClickable(true);
                bn.setId(position);

            }else{

                bn.setBackgroundResource(R.drawable.button_normal);
                bn.setClickable(false);
                bn.setId(position);
            }*/
        } else {

            bn = (Button) convertView;
            for(int i = 0; i < nCols;i++){
                for(int j = 0; j < nCols;j++){
                    decideButton(position, bn, i, j);
                }
            }
        }

        return bn;
    }

    private void decideButton(int position, Button bn, int i, int j) {
        if(position == (j + (i*nCols))){

            int num = mController.positions[i][j];

            //0 will be a normal position, 1 a posible position, 2 white, 3 black
            if(num == 0){

                bn.setBackgroundResource(R.drawable.button_normal);
                bn.setClickable(false);

            }else if(num == 1){

                bn.setBackgroundResource(R.drawable.button_posible);
                bn.setClickable(true);
                bn.setEnabled(true);

            }else if(num == 2){

                bn.setBackgroundResource(R.drawable.button_white);
                bn.setClickable(false);

            }else if(num == 3){

                bn.setBackgroundResource(R.drawable.button_black);
                bn.setClickable(false);

            }
        }
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
            //position in Array mController = [position / cols][pos % cols]
            int fila = position / nCols;
            int columna = position % nCols;

            mController.positions[fila][columna] = 2;
            for (int x = 0; x < nCols; x++){//horitzontal dreta
                if(nCols > columna+x ){//&& 0 <= (position + x) % nCols) {
                    if (mController.positions[fila][columna+x] == 2) {
                        for(int y =columna; y < columna+x;y ++){
                            mController.positions[fila][y] = 2;
                        }

                    }
                }
            }
            for (int x = 0; x < nCols; x++){//horitzontal ESQUERRA
                if(0 <= columna-x ){//&& 0 <= (position + x) % nCols) {
                    if (mController.positions[fila][columna-x] == 2) {
                        for(int y = columna; y > columna-x;y --){
                            mController.positions[fila][y] = 2;
                        }

                    }
                }
            }
            for (int x = 0; x < nCols; x++){//VERTICAL DAL-BAIX
                if(nCols > fila+x ){//&& 0 <= (position + x) % nCols) {
                    if (mController.positions[fila+x][columna] == 2) {
                        for(int y =fila; y < fila+x;y ++){
                            mController.positions[y][columna] = 2;
                        }

                    }
                }
            }
            for (int x = 0; x < nCols; x++){//VERTICAL baix-dal
                if(0 <= fila-x ){//&& 0 <= (position + x) % nCols) {
                    if (mController.positions[fila-x][columna] == 2) {
                        for(int y = fila; y > fila-x;y --){
                            mController.positions[y][columna] = 2;
                        }

                    }
                }
            }
            //TIRADA DEL JUGADOR PC
            notifyDataSetChanged();
        }
    }
}