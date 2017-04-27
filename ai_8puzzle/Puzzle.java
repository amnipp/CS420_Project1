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
public class Puzzle {  
    //Fields
    private Integer[] initialState = new Integer[9];
    private StateNode initialStateNode;
    final private Integer[] goalState = {0,1,2,3,4,5,6,7,8};
    //Getters and Setters for fields
    public void setInitalState(Integer[] state){
        initialState = state;
    }        
    public void setInitialStateNode(StateNode init){
        initialStateNode = init;
    }
    public StateNode getInitialStateNode(){
        return initialStateNode;
    }
    public Integer[] getInitialState(){
        return initialState;
    }
    public Integer[] getGoalState(){
        return goalState;
    }   

    /**
     * Checks to see if a puzzle is solvable. A solvable puzzle has a positive 
     * of inversions. An inversion is when a tile with a greater number on it 
     * precedes a tile with a smaller number
     * Returns true if inversions are even
     */
    public boolean checkSolvable(Integer[] board){
        int inversions = 0;
        for(int i = 0; i < board.length-1; ++i) {
            for(int j = i+1; j < board.length; ++j) {
                //Make sure not to count the 0 tile
                if(board[i] != 0 && board[j] != 0 && board[i] > board[j])
                    inversions++;
            }
        }
        return inversions%2 == 0;
    }
 }
