/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_8puzzle;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author andrew
 */
public class RandomPuzzle extends Puzzle {
    public RandomPuzzle(){
        createPuzzle();
    }
    /**
     * Creates a new unique 8 puzzle that is solvable then will set the initial and current board state
     */
    private void createPuzzle(){
        boolean canSolve = false;
        Integer[] board = new Integer[9];
        Arrays.fill(board, -1);
        int numFailed = 0;
        int emptyPosition = 0;
        while(!canSolve){
            Random rand = new Random();
            board = new Integer[9];
            Arrays.fill(board, -1);
            //Fill the board with random values between 0 and 9
            for(int i = 0; i < board.length; ++i){
                Integer tile = rand.nextInt(9);
                //Check for unique-ness
                while(arrIndexOf(board,tile) != -1){
                    tile = rand.nextInt(9);
                }
                if(tile.equals(0)) emptyPosition = i;
                board[i] = tile;
            }
            //Runs the Puzzle classes checkSolvable function, returns true if inversions is even
            canSolve = checkSolvable(board);
            if(canSolve == false) numFailed++;
        }
        System.out.println("Amount of times failed: " + numFailed);
        System.out.println("Empty Pos: " + emptyPosition);
        setInitalState(board);
        setInitialStateNode(new StateNode(board, board,0,"noop",null,emptyPosition));
        //setCurrentState(board);
    }
    
    /**
     * Helper function for createPuzzle, used to find the index of `searchFor` within the array `arr`
     */
    private int arrIndexOf(Integer[] arr, Integer searchFor){
        for(int i = 0; i < arr.length; ++i){
            if(arr[i].equals(searchFor)) return i;
        }
        return -1;
    }
}
