/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_8puzzle;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 *
 * @author andrew
 */
public class AStar {
    private PriorityQueue<StateNode> frontier;
    private HashSet<StateNode> exploredSet;
    
    public AStar(){
        frontier = new PriorityQueue<>((final StateNode o1, final StateNode o2) -> o1.getCost() - o2.getCost());
        exploredSet = new HashSet<>();
    }
    
    /**
     * Heuristic function that simply counts the number of misplaced tiles
     * Returns the total amount of misplaced tiles
     */
    public int misplacedTiles(StateNode node){
        int misplaced = 0;
        for(int i = 0; i < node.getCurrentState().length; ++ i){
            if(!node.getCurrentState()[i].equals(i)) misplaced++;
        }
        return misplaced;
    }
    
    /**
     * Heuristic function for the Manhattan distance, i.e. the sum of the dist.
     * from the tile to the goal.
     * Returns the sum of the distances
     */
    public int sumOfDistance(StateNode node){
        int sum = 0;
        //Since I am using a 1D array instead of a 2D array, I need to find the 
        //row and column in order to find the x and the y coordinates for the 
        //distance
        int row = 0;
        int col = 0;
        for(int i = 0; i < node.getCurrentState().length; ++i){
            //TODO: Goal Location: Floor(value/3) = row, value%3 = col
            //Doing the above will remove an entire loop
            //int[] goalLoc = goalLocation(goalState, currentState[i]);
            int goalCol = node.getCurrentState()[i]%3;
            int goalRow = (int)Math.floor(node.getCurrentState()[i]/3.0);
            // goalLoc[0] is column, goalLoc[1] is row
            //sum += Math.abs(col - goalLoc[0]) + Math.abs(row - goalLoc[1]);
            sum += Math.abs(col - goalCol) + Math.abs(row - goalRow);
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
    
}
