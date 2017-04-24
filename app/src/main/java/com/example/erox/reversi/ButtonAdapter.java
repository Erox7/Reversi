package com.example.erox.reversi;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Erox on 20/04/2017.
 */

public class ButtonAdapter extends BaseAdapter {
    private Context mContext;
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



    public ButtonAdapter(Activity c, Integer numCols, TextView timeView, TextView emptyFields, TextView contadorView) {
        mContext = c;
        count = numCols * numCols;
        nCols = numCols;
        mController = new MoveController(numCols);
        mController.setArray();

        this.timeView = timeView;
        this.emptyFields = emptyFields;
        this.contadorView = contadorView;
        this.emptyFields.setText(count-4 + "  Caselles Pendents");
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
            if(nCols == 6) {

                bn = new Button(mContext);
                bn.setLayoutParams(new GridView.LayoutParams(60, 60));
                bn.setPadding(8, 8, 8, 8);

            }else if(nCols == 8){

                bn = new Button(mContext);
                bn.setLayoutParams(new GridView.LayoutParams(50, 50));
                bn.setPadding(8, 8, 8, 8);

            }else{
                bn = new Button(mContext);
                bn.setLayoutParams(new GridView.LayoutParams(100, 100));
                bn.setPadding(8, 8, 8, 8);
            }
            int fila = position / nCols;
            int columna = position % nCols;
            decideButton(position,bn,fila,columna);
            contadorView.setText("Tu:" + nWhite +" ; "+ "Oponent: " + nBlack);
        } else {

            bn = (Button) convertView;
            int fila = position / nCols;
            int columna = position % nCols;
            decideButton(position,bn,fila,columna);
            contadorView.setText("Tu:" + nWhite +" ; "+ "Oponent: " + nBlack);
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

        @Override
        public void onClick(View v) {

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
                                } else if (mController.positions[x + i][y + i] == 0 && ntrovada == true) {
                                    mController.positions[x + i][y + i] = 1;
                                    break;
                                } else if (mController.positions[x + i][y + i] == 1 && ntrovada == true) {
                                    break;
                                }
                            }
                        }
                        ntrovada = false;
                        for (int i = 1; i < nCols; i++) {//dreta esquerra baix-dal
                            if (x - i >= 0 && y - i >= 0) {//mirem si existeix la posicio
                                if (mController.positions[x - i][y - i] == 3) {
                                    ntrovada = true;
                                } else if (mController.positions[x - i][y - i] == 0 && ntrovada == true) {
                                    mController.positions[x - i][y - i] = 1;
                                    break;
                                } else if (mController.positions[x - i][y - i] == 1 && ntrovada == true) {
                                    break;
                                }
                            }
                        }
                        ntrovada = false;
                        for (int i = 1; i < nCols; i++) {//dreta esquerra dal-baix
                            if (x - i >= 0 && y + i < nCols) {//mirem si existeix la posicio
                                if (mController.positions[x - i][y + i] == 3) {
                                    ntrovada = true;
                                } else if (mController.positions[x - i][y + i] == 0 && ntrovada == true) {
                                    mController.positions[x - i][y + i] = 1;
                                    break;
                                } else if (mController.positions[x - i][y + i] == 1 && ntrovada == true) {
                                    break;
                                }
                            }
                        }
                        ntrovada = false;
                        for (int i = 1; i < nCols; i++) {// esquerra-dreta baix-dal
                            if (x + i < nCols && y - i >= 0) {//mirem si existeix la posicio
                                if (mController.positions[x + i][y - i] == 3) {
                                    ntrovada = true;
                                } else if (mController.positions[x + i][y - i] == 0 && ntrovada == true) {
                                    mController.positions[x + i][y - i] = 1;
                                    break;
                                } else if (mController.positions[x + i][y - i] == 1 && ntrovada == true) {
                                    break;
                                }
                            }
                        }
                    }
                }

            }
        }

        private void posibleMoveVertical() {
            boolean ntrovada;
            ntrovada = false;
            for (int x = 0; x < nCols; x++) {//posiblesa vertical
                for (int y = 0; y < nCols; y++) {
                    if (mController.positions[x][y] == 2) {
                        for (int i = x; i < nCols; i++) {//buscan dal-baix
                            if (mController.positions[i][y] == 3) {
                                ntrovada = true;
                            } else if (mController.positions[i][y] == 0 && ntrovada == true) {
                                mController.positions[i][y] = 1;
                                break;
                            } else if (mController.positions[i][y] == 1 && ntrovada == true) {
                                break;
                            }
                        }
                        ntrovada = false;

                        for (int i = x; i >= 0; i--) {//buscan baix-dal
                            if (mController.positions[i][y] == 3) {
                                ntrovada = true;
                            } else if (mController.positions[i][y] == 0 && ntrovada == true) {
                                mController.positions[i][y] = 1;
                                break;
                            } else if (mController.positions[i][y] == 1 && ntrovada == true) {
                                break;
                            }
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
                        for (int i = y; i < nCols; i++) {//buscan cap a dreta
                            if (mController.positions[x][i] == 3) {
                                ntrovada = true;
                            } else if (mController.positions[x][i] == 0 && ntrovada == true) {
                                mController.positions[x][i] = 1;
                                break;
                            } else if (mController.positions[x][i] == 1 && ntrovada == true) {
                                break;
                            }
                        }
                        ntrovada = false;

                        for (int i = y; i >= 0; i--) {//buscan cap a esquerra
                            if (mController.positions[x][i] == 3) {
                                ntrovada = true;
                            } else if (mController.positions[x][i] == 0 && ntrovada == true) {
                                mController.positions[x][i] = 1;
                                break;
                            } else if (mController.positions[x][i] == 1 && ntrovada == true) {
                                break;
                            }
                        }
                        ntrovada = false;

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
        private void countTotal(){
            nEmpty = 0;
            nWhite = 0;
            nBlack = 0;
            for (int x = 0; x < nCols; x++) {
                for (int y = 0; y < nCols; y++) {
                    if (mController.positions[x][y] == 0 || mController.positions[x][y] == 1) {
                        nEmpty+=1;
                    }else if(mController.positions[x][y] == 2){
                        nWhite+=1;
                    }else{
                        nBlack+=1;
                    }
                }
            }
        }
        private void searchDiagonals(int fila, int columna) {
            for (int x = 1; x < nCols - 1; x++) {//Sud Est
                if (nCols  > columna + x && nCols  > fila + x) {
                    if(mController.positions[fila + 1][columna + 1] == 3) {
                        if (mController.positions[fila + x][columna + x] == 2) {
                            int tmpfila = fila + x;
                            int tmpcolumna = columna + x;
                            while(tmpfila > fila && tmpcolumna > columna){
                                mController.positions[tmpfila][tmpcolumna] = 2;
                                tmpfila--;
                                tmpcolumna--;
                            }
                        }
                    }
                }
            }
            for (int x = 0; x < nCols - 1; x++) {//Sud Oest
                if (0 <= columna - x && nCols > fila + x) {
                    if (mController.positions[fila + 1][columna - 1 ] == 3) {
                        if (mController.positions[fila + x][columna - x] == 2) {
                            int tmpfila = fila + x;
                            int tmpcolumna = columna - x;
                            while(tmpfila > fila && tmpcolumna < columna){
                                mController.positions[tmpfila][tmpcolumna] = 2;
                                tmpfila--;
                                tmpcolumna++;
                            }
                        }

                    }
                }
            }

            for (int x = 0; x < nCols - 1; x++) {//Nord Est
                if (nCols > columna - x && 0 <= fila - x) {
                    if (mController.positions[fila - 1][columna + 1 ] == 3) {
                        if (mController.positions[fila - x][columna + x] == 2) {
                            int tmpfila = fila - x;
                            int tmpcolumna = columna + x;
                            while(tmpfila < fila && tmpcolumna > columna){
                                mController.positions[tmpfila][tmpcolumna] = 2;
                                tmpfila++;
                                tmpcolumna--;
                            }
                        }

                    }
                }
            }
            for (int x = 1; x < nCols - 1; x++) {//Nord Oest
                if (0<= columna - x && 0 <= fila - x) {
                    if(mController.positions[fila - 1][columna - 1] == 3) {
                        if (mController.positions[fila - x][columna - x] == 2) {
                            int tmpfila = fila - x;
                            int tmpcolumna = columna - x;
                            while(tmpfila < fila && tmpcolumna < columna){
                                mController.positions[tmpfila][tmpcolumna] = 2;
                                tmpfila++;
                                tmpcolumna++;
                            }
                        }
                    }
                }
            }
        }

        private void searchVertical(int fila, int columna) {
            for (int x = 0; x < nCols - 1; x++) {//VERTICAL DAL-BAIX
                if (nCols > fila + x) {//&& 0 <= (position + x) % nCols) {
                    if(mController.positions[fila + 1][columna] == 3) {
                        if (mController.positions[fila + x][columna] == 2) {
                            for (int y = fila; y < fila + x; y++) {
                                mController.positions[y][columna] = 2;
                            }
                        }
                    }
                }
            }
            for (int x = 0; x < nCols - 1; x++) {//VERTICAL baix-dal
                if (0 < fila - x) {//&& 0 <= (position + x) % nCols) {
                    if(mController.positions[fila - 1][columna] == 3) {
                        if (mController.positions[fila - x][columna] == 2) {
                            for (int y = fila; y > fila - x; y--) {
                                mController.positions[y][columna] = 2;
                            }
                        }
                    }
                }

            }
        }

        private void searchHorizontal(int fila, int columna) {
            for (int x = 0; x < nCols - 1; x++) {//horitzontal dreta
                if (nCols > columna + x) {//&& 0 <= (position + x) % nCols) {
                    if(mController.positions[fila][columna + 1] == 3) {
                        if (mController.positions[fila][columna + x] == 2) {
                            for (int y = columna; y < columna + x; y++) {
                                mController.positions[fila][y] = 2;
                            }
                        }
                }
                }
            }
            for (int x = 0; x < nCols -1; x++) {//horitzontal ESQUERRA
                if (0 < columna - x) {//&& 0 <= (position + x) % nCols) {
                    if(mController.positions[fila][columna - 1] == 3) {
                        if (mController.positions[fila][columna - x] == 2) {
                            for (int y = columna; y > columna - x; y--) {
                                mController.positions[fila][y] = 2;
                            }
                        }
                    }
                }
            }
        }
    }
}