/**
 * Created by Erox on 22/04/2017.
 */
package com.example.erox.reversi;
public class MoveController {
    public Integer[][] positions;
    private int cols;

    private Integer[][] fourcols = new Integer[][]{{0,0,1,0},
                                                    {0,2,3,1},
                                                    {1,3,2,0},
                                                    {0,1,0,0}};

    private Integer[][] sixcols = new Integer[][]{{0,0,0,0,0,0},
                                          {0,0,0,1,0,0},
                                          {0,0,2,3,1,0},
                                          {2,0,1,3,3,2},
                                          {0,0,1,0,0,0},
                                          {0,0,0,0,0,0}};

    private Integer[][] eightcols = new Integer[][]{{0,0,0,0,0,0,0,0},
                                                     {0,0,0,0,0,0,0,0},
                                                    {0,0,0,0,1,0,0,0},
                                                    {0,0,0,2,3,1,0,0},
                                                    {0,0,1,3,2,0,0,0},
                                                    {0,0,0,1,0,0,0,0},
                                                    {0,0,0,0,0,0,0,0},
                                                    {0,0,0,0,0,0,0,0}};


    public MoveController(int cols){
        //0 will be a normal position, 1 a posible position, 2 white, 3 black
        positions = new Integer[][]{};
        this.cols = cols;
    }

    public void setArray(){
        if(cols == 4){
            positions = fourcols;
        }else if(cols == 6){
            positions = sixcols;
        }else{
            positions = eightcols;
        }
    }

}
