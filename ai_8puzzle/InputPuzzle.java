/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_8puzzle;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author andrew
 */
public class InputPuzzle extends Puzzle {
    /**
     * Creates a puzzle from a string of numbers. Return true if the initial node
     * was created
     */
    public boolean createPuzzle(String sPuzzle){
        Integer[] puzzle = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
        int emptyPos = -1;
        if(sPuzzle.length() < 9){
            System.out.println("The puzzle must contain 9 numbers!");
            return false;
        }
        for(int i = 0; i < puzzle.length; ++i){
            Integer val;
            try{
                val = Integer.parseInt(Character.toString(sPuzzle.charAt(i)));
            }catch(NumberFormatException e){
                System.out.println("The string contained a non-number character, try again");
                return false;
            }
            if(val == 0) emptyPos = i;
            if(val < 0){
                System.out.println("The number must be inbetween 0 and 8");
                return false;
            }else if(val > 8){
                System.out.println("The number must be inbetween 0 and 8");
                return false;                  
            }
            if(arrIndexOf(puzzle,val) == -1)
                puzzle[i] = val;
            else{
                System.out.println("The string contains duplicate numbers");
                return false;
            }
        }        
        boolean canSolve = checkSolvable(puzzle);
        if(canSolve == false) System.out.println("This puzzle is not solvable.");
        else{
            System.out.println("Empty Pos: " + emptyPos);
            setInitalState(puzzle);
            setInitialStateNode(new StateNode(puzzle, puzzle,0,"noop",null,emptyPos));            
        }
        return canSolve;     
    }
    /*private void createPuzzle(){
        boolean canSolve = false;
        Integer[] puzzle = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
        int emptyPos = -1;
        while(!canSolve){
            Scanner kb = new Scanner(System.in);
            boolean isANumber = false;
            while(!isANumber){
                Integer[] tempPuzzle = {-1,-1,-1,-1,-1,-1,-1,-1,-1};
                emptyPos = -1;
                boolean finished = true;
                System.out.println("Please enter a new puzzle in the format of '012345678'");
                String puzzleStr = kb.nextLine();
                if(puzzleStr.length() < 9){
                    System.out.println("The puzzle must contain 9 numbers!");
                    continue;
                }
                for(int i = 0; i < puzzle.length; ++i){
                    Integer val;
                    try{
                        val = Integer.parseInt(Character.toString(puzzleStr.charAt(i)));
                    }catch(NumberFormatException e){
                        finished = false;
                        System.out.println("The string contained a non-number character, try again");
                        break;
                    }
                    if(val == 0) emptyPos = i;
                    if(val < 0){
                        System.out.println("The number must be inbetween 0 and 8");
                        finished = false;
                        break;
                    }else if(val > 8){
                        System.out.println("The number must be inbetween 0 and 8");
                        finished = false;
                        break;                        
                    }
                    if(arrIndexOf(tempPuzzle,val) == -1)
                        tempPuzzle[i] = val;
                    else{
                        finished = false;
                        System.out.println("The string contains duplicate numbers");
                        break;
                    }
                }
                if(finished == true){
                    puzzle = tempPuzzle.clone();
                    isANumber = true;
                }
            }
            canSolve = checkSolvable(puzzle);
        }
        
        System.out.println("Empty Pos: " + emptyPos);
        setInitalState(puzzle);
        setInitialStateNode(new StateNode(puzzle, puzzle,0,"noop",null,emptyPos));
    }*/
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
