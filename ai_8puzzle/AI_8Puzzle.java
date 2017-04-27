/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_8puzzle;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author andrew
 */
public class AI_8Puzzle {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean isGoodInput = false;
        Integer cleanInput = 1;
        String start = "8-Puzzle Problem Start\n";
        start += "Please select an option:\n";
        start += "1) Random Puzzle\n2) User-Defined Puzzle\n3) Exit";
        while(!isGoodInput){
            System.out.println(start);
            Scanner kb = new Scanner(System.in);
            String dirtyInput = kb.nextLine();
            try{
                cleanInput = Integer.parseInt(dirtyInput);
                if(cleanInput == 1 || cleanInput == 2 || cleanInput == 3) break;
                else
                    System.out.println("Input is not a valid option");
            }catch(NumberFormatException e) {
                System.out.println("Input is not a number. Please try again.");
            }
        }
        Puzzle puzzle = null;
        if(cleanInput == 1) puzzle = new RandomPuzzle();
        else if(cleanInput == 2) puzzle = new InputPuzzle();
        else if(cleanInput == 3) System.exit(0);
        new AI_8Puzzle(puzzle);
    }
    private Puzzle puzzle;
    private AStar aStar;
    public AI_8Puzzle(Puzzle puzzle){
        if(puzzle == null) this.puzzle = new RandomPuzzle();
        else this.puzzle = puzzle;
        aStar = new AStar();
    }
}
