/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_8puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 *
 * @author andrew
 */
public class AStar {
    private PriorityQueue<StateNode> fringe;
    private HashSet<StateNode> exploredSet;
    private final Integer[] goal = {0,1,2,3,4,5,6,7,8};
    /**
     * Run the A* algo on a specific board state using either the misplaced
     * tile or sum of distance heuristic.
     * Most of the heavy lifting is done by the priority queue and the lambda 
     * function it is using to sort costs. The priority queue will expand children
     * of the smallest cost first and then they will be added to the explored set
     * which should disallow duplicate states
     */
    public StateNode runAStar(StateNode initialState, boolean isH1, boolean print){
        int searchCost = 0;
        exploredSet = new HashSet<>();
        if(isH1){
            fringe = new PriorityQueue<>((final StateNode o1, final StateNode o2) -> (o1.getCost()+misplacedTiles(o1)) - (o2.getCost()+misplacedTiles(o2)));
        }else{
            fringe = new PriorityQueue<>((final StateNode o1, final StateNode o2) -> (o1.getCost()+sumOfDistance(o1)) - (o2.getCost()+sumOfDistance(o2)));            
        }
        fringe.add(initialState);
        while(!fringe.isEmpty()){
            StateNode current = fringe.poll();
            exploredSet.add(current);    
            if(print == true){
                System.out.println(current);
                System.out.println("------ Current Step Cost: " + current.getCost() + " - Search Cost: " + searchCost + "  - Fringe Size: " + fringe.size() + " - Explored Size:  " + exploredSet.size() + " -------");              
            }
            if(Arrays.equals(current.getCurrentState(), goal)){
                System.out.println("----------- GOAL FOUND AT SEARCH COST OF " + searchCost + "------------");
                 return current;
            }     
            ArrayList<StateNode> children = current.expandCurrentNode();
            for(int i = 0; i < children.size(); ++i){
                if(!exploredSet.contains(children.get(i))){
                    searchCost++;
                    children.get(i).setSearchCost(searchCost);
                    children.get(i).setExploredSize(exploredSet.size());
                    children.get(i).setFringeSize(fringe.size());
                    fringe.add(children.get(i));
                }
            }
        }
        return null;
    }  
    /**
     * Heuristic function that simply counts the number of misplaced tiles
     * Returns the total amount of misplaced tiles
     * A.K.A H1
     */
    public int misplacedTiles(StateNode node){
        int misplaced = 0;
        for(int i = 0; i < node.getCurrentState().length; ++ i){
            if(node.getCurrentState()[i] != i) misplaced++;
        }
        return misplaced;
    }
    
    /**
     * Heuristic function for the Manhattan distance, i.e. the sum of the dist.
     * from the tile to the goal.
     * Returns the sum of the distances
     * A.K.A H2
     */
    public int sumOfDistance(StateNode node){
        int sum = 0;
        for(int i = 0; i < node.getCurrentState().length; ++i){
            if(node.getCurrentState()[i] == i) continue;
            if(node.getCurrentState()[i] == 0) continue;
            int row = node.getCurrentState()[i]/3;
            int col = node.getCurrentState()[i]%3;
            int goalRow = i/3;
            int goalCol = i%3;
            sum += Math.abs(col - goalCol) +  Math.abs(row - goalRow);
        }
        return sum;
    }
    
}
