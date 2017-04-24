/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_8puzzle;

/**
 *
 * @author andrew
 */
public class AStar {
    private boolean isH1;
    public AStar(){
        isH1 = true;
    }
    public AStar(boolean isH1){
        this.isH1 = isH1;
    }
    public int h1(){
        return -1;
    }
    public int h2(){
        return -1;
    }
}
