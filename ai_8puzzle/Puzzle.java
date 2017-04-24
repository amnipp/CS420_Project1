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
    private Integer[] goalState = {0,1,2,3,4,5,6,7,8};
    private Integer[] currentState = new Integer[9];
    
    //Getters and Setters for fields
    public void setInitalState(Integer[] state){
        initialState = state;
    }        
    public void setCurrentState(Integer[] state){
        currentState = state;
    }    
    public Integer[] getInitialState(){
        return initialState;
    }
    public Integer[] getGoalState(){
        return goalState;
    }   
    public Integer[] getCurrentState(){
        return currentState;
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
    
    /**
     * Heuristic function that simply counts the number of misplaced tiles
     * Returns the total amount of misplaced tiles
     */
    public int misplacedTiles(){
        int misplaced = 0;
        for(int i = 0; i < currentState.length; ++ i){
            if(!currentState[i].equals(goalState[i])) misplaced++;
        }
        return misplaced;
    }
    
    /**
     * Heuristic function for the Manhattan distance, i.e. the sum of the dist.
     * from the tile to the goal.
     * Returns the sum of the distances
     */
    public int sumOfDistance(){
        int sum = 0;
        //Since I am using a 1D array instead of a 2D array, I need to find the 
        //row and column in order to find the x and the y coordinates for the 
        //distance
        int row = 0;
        int col = 0;
        for(int i = 0; i < currentState.length; ++i){
            //TODO: Goal Location: Floor(value/3) = row, value%3 = col
            //Doing the above will remove an entire loop
            int[] goalLoc = goalLocation(goalState, currentState[i]);
            // goalLoc[0] is column, goalLoc[1] is row
            sum += Math.abs(col - goalLoc[0]) + Math.abs(row - goalLoc[1]);
            row++;
            //When i%3 is 0 that means we are on the next column so lets reset
            //the row count and increase the column count
            if(i%3 == 0){
                col++;
                row = 0;
            }
        }
        return sum;
    }
    
    /**
     * A helper function for the Manhattan distance function
     * Used to find the 2D location of the value within the goal state
     * Returns the column and the row of the value we are looking for
     */
    private int[] goalLocation(Integer[] goal, Integer val){
        int row = 0;
        int col = 0;
        for(int i = 0; i < goal.length; ++i){
            if(goal[i].equals(val)) break;
            row++;
            if(i%3 == 0){
                col++;
                row = 0;
            }
        }
        int[] location = {col, row};
        return location;
    }
}
