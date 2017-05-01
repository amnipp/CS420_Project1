/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_8puzzle;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author andre
 */
public class StateNode {
    private StateNode predecessor;
    final private Integer[] initalState;
    final private Integer[] currentState;
    final private int cost;
    final private String actionTaken;
    final private int emptyPosition;
    private int fringeSize = 0;
    private int exploredSize = 0;
    private int searchCost = 0;
    public StateNode(){
        initalState = new Integer[9];
        Arrays.fill(initalState,-1);
        currentState = initalState;
        cost = 0;
        actionTaken = "noop";
        emptyPosition = 0;
    }
    
    public StateNode(Integer[] initState, Integer[] currState, int cost, String actionTaken, StateNode predecssor, int emptyPos){
        initalState = initState;
        currentState = currState;
        this.cost = cost;
        this.actionTaken = actionTaken;
        this.predecessor = predecssor;
        emptyPosition = emptyPos;
    }
    
    public StateNode(StateNode node){
        this(node.getInitialState(), node.getCurrentState(), node.getCost(), node.getAction(), node.getPredecessor(), node.getEmptyPosition());
    }
    
    public void setFringeSize(int fSize){
        fringeSize = fSize;
    }
    public void setExploredSize(int eSize){
        exploredSize = eSize;
    }
    public void setSearchCost(int searchCost){
        this.searchCost = searchCost;
    }
    public int getSearchCost(){
        return searchCost;
    }
    public StateNode getPredecessor(){
        return predecessor;
    }    
    public Integer[] getInitialState(){
        return initalState;
    }
    public Integer[] getCurrentState(){
        return currentState;
    }
    public int getCost(){
        return cost;
    }
    public String getAction(){
        return actionTaken;
    }
    public int getEmptyPosition(){
        return emptyPosition;
    }
    public int getFringeSize(){
        return fringeSize;
    }
    public int getExploredSize(){
        return exploredSize;
    }
    /**
     * Checks if the action will keep the empty pos in bounds of the state
     * Since it is a 1D array and the actions treat it like a matrix we need to 
     * think about the mathematical conversion between them.
     * {0,1,2,3,4,5,6,7,8}  How it is stored
     * 
     * {{0,1,2},
     *  {3,4,5},
     *  {4,7,8}} How the actions treat it
     * Moving up or down requires the index to be in bounds for i+-3
     * Moving left and right requires the index to be i%3!=0 and i+1%3!=0
     */
    public boolean inBounds(String action){
        Integer[] currentBoard = currentState;
        int emptyPos = emptyPosition;
        switch(action){
            case "up":
                if(emptyPos - 3 < 0) return false;
                break;
            case "down":
                if(emptyPos + 3 >= currentBoard.length) return false;
                break;
            case "left":
                if(emptyPos == 0) return false;
                if(emptyPos%3 == 0) return false;
                break;
            case "right":
                if(emptyPos+1%3 == 0) return false;
                break;
            default:
                return false;
        }
        return true;
    }
     /**
     * Generates a child node given a specific action. Returns the child node 
     * that will have the empty tile moved and will have its step cost increased
     * linearly.
     * Since it is a 1D array and the actions treat it like a matrix we need to 
     * think about the mathematical conversion between them.
     * {0,1,2,3,4,5,6,7,8}  How it is stored
     * 
     * {{0,1,2},
     *  {3,4,5},
     *  {4,7,8}} How the actions treat it
     * Moving up or down requires the index to be in bounds for i+-3
     * Moving left and right requires the index to be i%3!=0 and i+1%3!=0
     */   
    public StateNode generateNode(String action){
        StateNode node;
        //Integer[] initState, Integer[] currState, int cost, String actionTaken;
        Integer[] newState = currentState.clone();
        int newEmpty = emptyPosition;
        switch(action){
             case "up":
                if(emptyPosition - 3 >= 0) {
                    newState = swap(newState, emptyPosition, emptyPosition-3);
                    newEmpty = emptyPosition - 3;
                }
                break;
            case "down":
                if(emptyPosition + 3 <= currentState.length){
                    newState = swap(newState, emptyPosition, emptyPosition+3);
                    newEmpty = emptyPosition + 3;
                }
                break;
            case "left":
                if(emptyPosition == 0) break;
                if(emptyPosition%3 != 0){
                    newState = swap(newState, emptyPosition, emptyPosition-1);
                    newEmpty = emptyPosition - 1;
                }
                break;
            case "right":
                if((emptyPosition+1)%3 != 0){
                    newState = swap(newState, emptyPosition, emptyPosition+1);
                    newEmpty = emptyPosition + 1;
                }
                break;
            default:        
        }
        node = new StateNode(initalState, newState, getCost()+1, action, this, newEmpty);
        return node;
    }
    /**
     * Helper function for generateNode, used to swap the empty tile with another 
     */
    private Integer[] swap(Integer[] arr, int pos1, int pos2){
        Integer temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
        return arr;
    }  
    
    /**
     * Generates all the children for this specific node by doing all possible
     * actions. Up, down, left, right
     */
    public ArrayList<StateNode> expandCurrentNode(){
        ArrayList<StateNode> successorList = new ArrayList<>();
        if(inBounds("up"))
            successorList.add(generateNode("up"));
        if(inBounds("down"))
            successorList.add(generateNode("down"));
        if(inBounds("left"))
            successorList.add(generateNode("left"));
        if(inBounds("right"))
            successorList.add(generateNode("right"));
        return successorList;
    }
    
    /**
     * The hash code for this object is simply the hash code for the current state
     * of the board. Uses the Arrays built in hash code function
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(currentState);
    }

    /**
     * Two nodes are considered equal if the current state of their boards are 
     * equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StateNode other = (StateNode) obj;
        return Arrays.deepEquals(this.currentState, other.currentState);
    }
    
    /**
     * Prints out the board as a matrix 
     */
    @Override
    public String toString(){
        String board = "";
        for(int i = 0; i < currentState.length; ++i){
            if(i != 0 && i%3 == 0) board += "\n";
            board += currentState[i] + " ";
        } 
        return board;
    }
}
