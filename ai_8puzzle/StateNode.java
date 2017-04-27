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
    private StateNode successor;
    final private Integer[] initalState;
    final private Integer[] currentState;
    final private int cost;
    final private String actionTaken;
    final private int emptyPosition;
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
    
    public StateNode getPredecessor(){
        return predecessor;
    }    
    public StateNode getSuccessor(){
        return successor;
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
    public void setPredecessor(StateNode pred){
        predecessor = pred;
    }
    public void setSuccessor(StateNode succ){
        successor = succ;
    }

    public boolean inBounds(String action){
        Integer[] currentBoard = currentState;
        int emptyPos = emptyPosition;
        switch(action){
            case "up":
                if(emptyPos - 3 < 0) return false;
                break;
            case "down":
                if(emptyPos + 3 > currentBoard.length) return false;
                break;
            case "left":
                if(emptyPos == 0 || emptyPos%3 == 0) return false;
                break;
            case "right":
                if(emptyPos+1%3 == 0) return false;
                break;
            default:
                return false;
        }
        return true;
    }
    
    public StateNode generateNode(String action){
        StateNode node;
        //Integer[] initState, Integer[] currState, int cost, String actionTaken;
        Integer[] newState = currentState;
        int newEmpty = emptyPosition;
        System.out.println("DEBUG: Empty Pos: " + emptyPosition + " - Empty Pos % 3: " + emptyPosition%3);
        switch(action){
             case "up":
                if(emptyPosition - 3 >= 0) {
                    newState = swap(currentState, emptyPosition, emptyPosition-3);
                    newEmpty = emptyPosition - 3;
                }
                break;
            case "down":
                if(emptyPosition + 3 <= currentState.length){
                    newState = swap(currentState, emptyPosition, emptyPosition+3);
                    newEmpty = emptyPosition + 3;
                }
                break;
            case "left":
                if(emptyPosition == 0) break;
                if(emptyPosition%3 != 0){
                    newState = swap(currentState, emptyPosition, emptyPosition-1);
                    newEmpty = emptyPosition - 1;
                }
                break;
            case "right":
                if((emptyPosition+1)%3 != 0){
                    newState = swap(currentState, emptyPosition, emptyPosition+1);
                    newEmpty = emptyPosition + 1;
                }
                break;
            default:        
        }
        node = new StateNode(initalState, newState, getCost()+1, action, this, newEmpty);
        return node;
    }
  
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
    
    private Integer[] swap(Integer[] arr, int pos1, int pos2){
        Integer temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
        return arr;
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(currentState);
    }

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
        if (!Arrays.deepEquals(this.currentState, other.currentState)) {
            return false;
        }
        return true;
    }
    
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
