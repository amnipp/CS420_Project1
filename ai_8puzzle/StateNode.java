/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_8puzzle;

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
    
    public StateNode(){
        initalState = new Integer[9];
        Arrays.fill(initalState,-1);
        currentState = initalState;
        cost = 0;
        actionTaken = "noop";
    }
    
    public StateNode(Integer[] initState, Integer[] currState, int cost, String actionTaken){
        initalState = initState;
        currentState = currState;
        this.cost = cost;
        this.actionTaken = actionTaken;
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
}
