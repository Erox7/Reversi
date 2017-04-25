package com.example.erox.reversi;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Created by Erox on 20/04/2017.
 */

public class ButtonAdapter extends BaseAdapter implements Serializable {
    private final long time_start_activity;
    private Activity mContext;
    private int count;
    private int nCols;
    private int time = 25;
    private MoveController mController;
    private TextView timeView;
    private TextView emptyFields;
    private TextView contadorView;
    private int nEmpty = 0;
    private int nWhite = 0;
    private int nBlack = 0;
    private long time_start;
    private long time_end;
    private Boolean time_counter;



    public ButtonAdapter(Activity c, Integer numCols, TextView timeView, TextView emptyFields, TextView contadorView, long l, Boolean count_timer) {
        mContext = c;
        count = numCols * numCols;
        nCols = numCols;
        mController = new MoveController(numCols);
        mController.setArray();
        this.time_start = l;
        this.time_start_activity = l;
        this.timeView = timeView;
        this.emptyFields = emptyFields;
        this.contadorView = contadorView;
        this.emptyFields.setText(count-4 + "  Caselles Pendents");
        this.time_counter = count_timer;
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
            if(nCols == 6) {//Stetic if/else

                bn.setLayoutParams(new GridView.LayoutParams(80, 80));

            }else if(nCols == 8){


                bn.setLayoutParams(new GridView.LayoutParams(58, 58));

            }else{

                bn.setLayoutParams(new GridView.LayoutParams(120, 120));

            }
            bn.setPadding(8, 8, 8, 8);
            int fila = position / nCols;
            int columna = position % nCols;
            decideButton(position,bn,fila,columna);

        } else {

            bn = (Button) convertView;
            int fila = position / nCols;
            int columna = position % nCols;
            decideButton(position,bn,fila,columna);
        }
        if(position == (getCount() - 1)){
            if(time_counter) {
                time_end = (System.currentTimeMillis() / 1000);
                timeControl();
                countTotal();
                contadorView.setText("Tu:" + nWhite +" ; "+ "Oponent: " + nBlack);
            }

        }
        return bn;
    }

    private void decideButton(int position, Button bn, int i, int j) {

            int num = mController.positions[i][j];
            //0 will be a normal position, 1 a posible position, 2 white, 3 black
            if (num == 0) {

                bn.setBackgroundResource(R.drawable.button_normal);
                bn.setOnClickListener(new MyOnClickListener(position));
                bn.setClickable(false);
                nEmpty +=1;

            } else if (num == 1) {

                bn.setBackgroundResource(R.drawable.button_posible);
                bn.setOnClickListener(new MyOnClickListener(position));
                bn.setClickable(true);
                nEmpty +=1;

            } else if (num == 2) {

                bn.setBackgroundResource(R.drawable.button_white);
                bn.setOnClickListener(new MyOnClickListener(position));
                bn.setClickable(false);

            } else if (num == 3) {

                bn.setBackgroundResource(R.drawable.button_black);
                bn.setOnClickListener(new MyOnClickListener(position));
                bn.setClickable(false);

            }

    }


    private class MyOnClickListener implements View.OnClickListener {
        private final int position;

        public MyOnClickListener(int position) {
            this.position = position;
        }
        //Control de tiempo inactivo
        @Override
        public void onClick(View v) {
            /*Calcular tiempos:
            long time_start, time_end;
            time_start = System.currentTimeMillis();
            ReallyHeavyTask(); // llamamos a la tarea
            time_end = System.currentTimeMillis();
            System.out.println("the task has taken "+ ( time_end/1000 - time_start/1000 ) +" milliseconds");
             */
            if(time_counter) {
                time_end = (System.currentTimeMillis() / 1000);
                timeControl();
            }
            Button b = (Button) v;
            b.setBackgroundResource(R.drawable.button_white);

            //position in Array mController = [position / cols][pos % cols]
            int fila = position / nCols;
            int columna = position % nCols;

            mController.positions[fila][columna] = 2;
            searchHorizontal(fila, columna);
            searchVertical(fila, columna);
            searchDiagonals(fila, columna);
            //Actualitzar els posibles moviments, primer borren els vells
            resetPosibleMoves();

            //posibles moviments
            posibleMovesHorizontal();
            posibleMoveVertical();
            posibleDiagonalMoves();

            countTotal();
            contadorView.setText("Tu:" + nWhite +" ; "+ "Oponent: " + nBlack);
            emptyFields.setText(nEmpty + "  Caselles Pendents");
            notifyDataSetChanged();
        }


        private void posibleMoveVertical() {
            boolean ntrovada;
            ntrovada = false;
            for (int x = 0; x < nCols; x++) {//posiblesa vertical
                for (int y = 0; y < nCols; y++) {
                    if (mController.positions[x][y] == 2) {
                        //if(x+1 < nCols && mController.positions[x + 1][y] == 3) {
                        for (int i = x; i < nCols; i++) {//buscan dal-baix
                            if (mController.positions[i][y] == 3) {
                                ntrovada = true;
                            } else if (mController.positions[i][y] == 0 && ntrovada == true && i > 0 && mController.positions[i-1][y] == 3) {
                                mController.positions[i][y] = 1;
                                break;
                            } else if (mController.positions[i][y] == 0 && ntrovada == false){//si despres de la blanca hiha un espai buit pasem
                                break;
                            } else if (mController.positions[i][y] == 1 ) {
                                break;
                            }
                            //}
                        }
                        ntrovada = false;
                        //if(x > 0 && mController.positions[x - 1][y] == 3) {
                        for (int i = x; i >= 0; i--) {//buscan baix-dal
                            if (mController.positions[i][y] == 3) {
                                ntrovada = true;
                            } else if (mController.positions[i][y] == 0 && ntrovada == true && i+1 < nCols && mController.positions[i+1][y] == 3) {
                                mController.positions[i][y] = 1;
                                break;
                            } else if (mController.positions[i][y] == 0 && ntrovada == false){
                                break;
                            } else if (mController.positions[i][y] == 1 ) {
                                break;
                            }
                            //}
                        }
                        ntrovada = false;

                    }
                }
            }
        }

        private void posibleMovesHorizontal() {
            boolean ntrovada = false;
            for (int x = 0; x < nCols; x++) {//posiblesa horitzontal
                for (int y = 0; y < nCols; y++) {
                    if (mController.positions[x][y] == 2) {
                        if(y < nCols -1 && mController.positions[x][y + 1] == 3) {
                            for (int i = y; i < nCols; i++) {//buscan cap a dreta
                                if (mController.positions[x][i] == 3) {
                                    ntrovada = true;
                                } else if (mController.positions[x][i] == 0 && ntrovada == true && i > 0 && mController.positions[x][i-1] == 3) {
                                    mController.positions[x][i] = 1;
                                    break;
                                } else if (mController.positions[x][i] == 0 && ntrovada == false){
                                    break;
                                } else if (mController.positions[x][i] == 1 ) {
                                    break;
                                }
                            }
                        }
                        ntrovada = false;

                        if(y > 0 && mController.positions[x][y - 1] == 3) {
                            for (int i = y; i >= 0; i--) {//buscan cap a esquerra
                                if (mController.positions[x][i] == 3) {
                                    ntrovada = true;
                                } else if (mController.positions[x][i] == 0 && ntrovada == true && i+1 < nCols && mController.positions[x][i+1] == 3) {
                                    mController.positions[x][i] = 1;
                                    break;
                                } else if (mController.positions[x][i] == 0 && ntrovada == false){
                                    break;
                                } else if (mController.positions[x][i] == 1 ) {
                                    break;
                                }
                            }
                        }
                        ntrovada = false;

                    }
                }
            }
        }
        private void posibleDiagonalMoves() {
            boolean ntrovada;
            //diagonals
            for (int x = 0; x < nCols; x++) {//posiblesa diagonal dal baix esquerra-dreta
                for (int y = 0; y < nCols; y++) {
                    if (mController.positions[x][y] == 2) {
                        ntrovada = false;
                        for (int i = 1; i < nCols; i++) {//diagonal dal baix esquerra-dreta
                            if (x + i < nCols && y + i < nCols) {//mirem si existeix la posicio
                                if (mController.positions[x + i][y + i] == 3) {
                                    ntrovada = true;
                                } else if (mController.positions[x + i][y + i] == 0 && ntrovada == true && i > 0 && mController.positions[x + i-1][y + i - 1] == 3){
                                    mController.positions[x + i][y + i] = 1;
                                    break;
                                } else if (mController.positions[x + i][y + i] == 0 && ntrovada == false){
                                    break;
                                } else if (mController.positions[x + i][y + i] == 1 ) {
                                    break;
                                }
                            }
                        }
                        ntrovada = false;
                        for (int i = 1; i < nCols; i++) {//dreta esquerra baix-dal
                            if (x - i >= 0 && y - i >= 0) {//mirem si existeix la posicio
                                if (mController.positions[x - i][y - i] == 3) {
                                    ntrovada = true;
                                } else if (mController.positions[x - i][y - i] == 0 && ntrovada == true && i+1 < nCols && mController.positions[x - i+1][y - i + 1] == 3) {
                                    mController.positions[x - i][y - i] = 1;
                                    break;
                                } else if (mController.positions[x - i][y - i] == 0 && ntrovada == false){
                                    break;
                                } else if (mController.positions[x - i][y - i] == 1 ) {
                                    break;
                                }
                            }
                        }
                        ntrovada = false;
                        for (int i = 1; i < nCols; i++) {//dreta esquerra dal-baix
                            if (x - i >= 0 && y + i < nCols) {//mirem si existeix la posicio
                                if (mController.positions[x - i][y + i] == 3) {
                                    ntrovada = true;
                                } else if (mController.positions[x - i][y + i] == 0 && ntrovada == true && i+1 < nCols && i > 0 && mController.positions[x - i+1][y + i-1] == 3) {
                                    mController.positions[x - i][y + i] = 1;
                                    break;
                                } else if (mController.positions[x - i][y + i] == 0 && ntrovada == false){
                                    break;
                                } else if (mController.positions[x - i][y + i] == 1 ) {
                                    break;
                                }
                            }
                        }
                        ntrovada = false;
                        for (int i = 1; i < nCols; i++) {// esquerra-dreta baix-dal
                            if (x + i < nCols && y - i >= 0) {//mirem si existeix la posicio
                                if (mController.positions[x + i][y - i] == 3) {
                                    ntrovada = true;
                                } else if (mController.positions[x + i][y - i] == 0 && ntrovada == true && i+1 < nCols && i > 0 && mController.positions[x + i-1][y - i+1] == 3) {
                                    mController.positions[x + i][y - i] = 1;
                                    break;
                                } else if (mController.positions[x + i][y - i] == 0 && ntrovada == false){
                                    break;
                                } else if (mController.positions[x + i][y - i] == 1 ) {
                                    break;
                                }
                            }
                        }
                    }
                }

            }
        }

        private void resetPosibleMoves() {
            for (int x = 0; x < nCols; x++) {
                for (int y = 0; y < nCols; y++) {
                    if (mController.positions[x][y] == 1) {
                        mController.positions[x][y] = 0;
                    }
                }
            }
        }

        private boolean checkPosibleMoves() {
            for (int x = 0; x < nCols; x++) {
                for (int y = 0; y < nCols; y++) {
                    if (mController.positions[x][y] == 1) {
                        return true;
                    }
                }
            }
            return false;
        }


        private void searchDiagonals(int fila, int columna) {


            if((fila + 1)  < nCols && (columna + 1) < nCols && mController.positions[fila + 1][columna + 1] == 3) {
                for (int x = 0; x < nCols - 1; x++) {//Sud Est
                     if (nCols > columna + x && nCols > fila + x) {
                        if (mController.positions[fila + x][columna + x] == 2) {
                            int tmpfila = fila + x;
                            int tmpcolumna = columna + x;
                            while(tmpfila > fila && tmpcolumna > columna){
                                mController.positions[tmpfila][tmpcolumna] = 2;
                                tmpfila--;
                                tmpcolumna--;
                            }
                        }else if(mController.positions[fila + x][columna + x] == 0 || mController.positions[fila + x][columna + x] == 1){
                            break;
                        }
                    }
                }
            }
            if (nCols > (fila + 1) && 0 < columna && mController.positions[fila + 1][columna - 1 ] == 3) {
             for (int x = 0; x < nCols - 1; x++) {//Sud Oest
                    if (0 <= columna - x && nCols > fila + x) {
                        if (mController.positions[fila + x][columna - x] == 2) {
                            int tmpfila = fila + x;
                            int tmpcolumna = columna - x;
                            while(tmpfila > fila && tmpcolumna < columna){
                                mController.positions[tmpfila][tmpcolumna] = 2;
                                tmpfila--;
                                tmpcolumna++;
                            }
                        }else if(mController.positions[fila + x][columna - x] == 0 || mController.positions[fila + x][columna - x] == 1){
                            break;
                        }

                    }
                }
            }
            if (fila > 0 && nCols > (columna + 1) && mController.positions[fila - 1][columna + 1 ] == 3) {
                for (int x = 0; x < nCols; x++) {//Nord Est
                    if (nCols > columna + x && 0 <= fila - x) {
                        if (mController.positions[fila - x][columna + x] == 2) {
                            int tmpfila = fila - x;
                            int tmpcolumna = columna + x;
                            while(tmpfila < fila && tmpcolumna > columna){
                                mController.positions[tmpfila][tmpcolumna] = 2;
                                tmpfila++;
                                tmpcolumna--;
                            }
                        }else if(mController.positions[fila - x][columna + x] == 0 || mController.positions[fila - x][columna + x] == 1){
                            break;
                        }

                    }
                }
            }
            if(fila > 0 && columna > 0 && mController.positions[fila - 1][columna - 1] == 3) {
                for (int x = 0; x < nCols - 1; x++) {//Nord Oest
                    if (0 <= columna - x && 0 <= fila - x) {
                        if (mController.positions[fila - x][columna - x] == 2) {
                            int tmpfila = fila - x;
                            int tmpcolumna = columna - x;
                            while(tmpfila < fila && tmpcolumna < columna){
                                mController.positions[tmpfila][tmpcolumna] = 2;
                                tmpfila++;
                                tmpcolumna++;
                            }
                        }else if(mController.positions[fila - x][columna - x] == 0 || mController.positions[fila - x][columna - x] == 1){
                            break;
                        }
                    }
                }
            }
        }

        private void searchVertical(int fila, int columna) {
            if((fila + 1 ) < nCols && mController.positions[fila + 1][columna] == 3) {
                for (int x = 0; x < nCols; x++) {//SUD
                    if (nCols > fila + x) {
                        if (mController.positions[fila + x][columna] == 2) {
                            for (int y = fila; y < fila + x; y++) {
                                mController.positions[y][columna] = 2;
                            }
                        }else if(mController.positions[fila + x][columna] == 0 || mController.positions[fila + x][columna] == 1){
                            break;
                        }
                    }
                }
            }
            if(fila > 0 && mController.positions[fila - 1][columna] == 3) {
                for (int x = 0; x < nCols; x++) {//NORD
                    if (0 <= fila - x) {
                        if (mController.positions[fila - x][columna] == 2) {
                            for (int y = fila; y > fila - x; y--) {
                                mController.positions[y][columna] = 2;
                            }
                        }else if(mController.positions[fila - x][columna] == 0 || mController.positions[fila - x][columna] == 1){
                             break;
                        }
                    }
                }

            }
        }

        private void searchHorizontal(int fila, int columna) {
            if((columna + 1) < nCols && mController.positions[fila][columna + 1] == 3) {
                for (int x = 0; x < nCols; x++) {//EST
                    if (nCols > columna + x) {
                        if (mController.positions[fila][columna + x] == 2) {
                            for (int y = columna; y < columna + x; y++) {
                                mController.positions[fila][y] = 2;
                            }
                        }else if(mController.positions[fila][columna + x] == 0 || mController.positions[fila][columna + x] == 1){
                            break;
                        }
                    }
                }
            }
            if(columna > 0 && mController.positions[fila][columna - 1] == 3) {
                for (int x = 0; x < nCols -1; x++) {//OEST
                    if (0 <= columna - x) {
                        if (mController.positions[fila][columna - x] == 2) {
                            for (int y = columna; y > columna - x; y--) {
                                mController.positions[fila][y] = 2;
                            }
                        }else if(mController.positions[fila][columna - x] == 0 || mController.positions[fila][columna - x] == 1){
                            break;
                        }
                    }
                }
            }
        }
    }
    //Metodos de la classe ButtonAdapter, no de la InnerClass MyOnClickListener
    private void timeControl() {

        if((time_end - time_start) > time){
            Toast.makeText(mContext, "Time Expired", Toast.LENGTH_SHORT).show();
            Intent in = new Intent(mContext, ResultsActivity.class);
            mContext.startActivity(in);
            mContext.finish();

        }else{

            timeView.setText("Time: " + (time_end - time_start));
            time_start = time_end;

        }
    }

    private void countTotal(){
        nEmpty = 0;
        nWhite = 0;
        nBlack = 0;
        for (int x = 0; x < nCols; x++) {
            for (int y = 0; y < nCols; y++) {
                selectCounter(mController.positions[x][y]);
            }
        }
    }

    private void selectCounter(Integer integer) {
        if (integer == 0 || integer == 1) {
            nEmpty+=1;
        }else if(integer == 2){
            nWhite+=1;
        }else{
            nBlack+=1;
        }
    }
}