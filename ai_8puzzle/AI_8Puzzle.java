/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai_8puzzle;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        start += "1) Random Puzzle\n2) User-Defined Puzzle\n3) Import Puzzle\n4) Exit";
        while(!isGoodInput){
            System.out.println(start);
            Scanner kb = new Scanner(System.in);
            String dirtyInput = kb.nextLine();
            try{
                cleanInput = Integer.parseInt(dirtyInput);
                if(cleanInput > -1 && cleanInput <=4) break;
                else
                    System.out.println("Input is not a valid option");
            }catch(NumberFormatException e) {
                System.out.println("Input is not a number. Please try again.");
            }
        }
        AI_8Puzzle ai = new AI_8Puzzle();
        switch (cleanInput) {
            case 1:
                ai.randomPuzzle();
                break;
            case 2:
                ai.userInputPuzzle();
                break;
            case 3:
                ai.importPuzzle();
                break;
            case 4:
                System.exit(0);
            default:
                break;
        }
    }
    private AStar aStar = new AStar();
    public void randomPuzzle(){
        Map<Integer,ArrayList<SearchData>> runtimeData = new TreeMap<>();
        int timesToRun = -1;
        System.out.println("How many times do you want to run this?");
        Scanner kb = new Scanner(System.in);
        while(timesToRun < 1){
            String num = kb.nextLine();
            try{
                timesToRun = Integer.parseInt(num);
            }catch(NumberFormatException e){
                System.out.println("That was not a number.");
                timesToRun = -1;
            }
            if(timesToRun < 1) System.out.println("How many times do you want to run this? (Must be greater than 0)");
        }
        File random = new File(timesToRun + "_Random_Test_Cases.txt");
        BufferedWriter bw = null;
        try {
            random.createNewFile();
            bw = new BufferedWriter(new FileWriter(random));
        } catch (IOException ex) {
            
        }
        for(int i = 0; i < timesToRun; ++i){
            Puzzle puzzle = new RandomPuzzle();
            try {
                bw.write(puzzle.getInitialStateNode().toString().replace(" ","").replace("\n",""));
                bw.newLine();
            } catch (IOException ex) {
            }
            SearchData compute = solve(puzzle.getInitialStateNode());
            if(!runtimeData.containsKey(compute.depth)){
                runtimeData.put(compute.depth, new ArrayList<>());
            }
            runtimeData.get(compute.depth).add(compute);
        }
        try {
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(AI_8Puzzle.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("d  | Total Cases | Search Cost H1 | Total Time H1 | Search Cost H2 | Total Time H2");
        runtimeData.entrySet().stream().forEach((entry) -> {
            int h1AvgCost=0,h1AvgTime=0,h2AvgCost=0,h2AvgTime=0,total=entry.getValue().size();
            for(int i = 0; i < entry.getValue().size(); ++i){
                SearchData data = entry.getValue().get(i);
                h1AvgCost += data.searchCostH1;
                h1AvgTime += data.totalTimeH1;
                h2AvgCost += data.searchCostH2;
                h2AvgTime += data.totalTimeH2;
            }
            System.out.println("----------------------------------------------------------------------------------");
            System.out.println(entry.getKey() + " | " + total + " | " + (h1AvgCost/total) + " | " + (h1AvgTime/total)
                    + " ms | " + (h2AvgCost/total) + " | " + (h2AvgTime/total) + " ms");
        });
    }
    public void userInputPuzzle(){
        Puzzle puzzle = new InputPuzzle();
        boolean madePuzzle = false;
        while(!madePuzzle){
            System.out.println("Enter a new puzzle:");
            Scanner kb = new Scanner(System.in);
            String sPuzzle = "";
            String r1 = kb.nextLine().replace(" ", "");
            if(r1.length() == 9){
                sPuzzle = r1;
            }else{
                String r2 = kb.nextLine().replace(" ", "");
                String r3 = kb.nextLine().replace(" ", "");    
                sPuzzle = r1.replace("\n", "") + r2.replace("\n", "") + r3.replace("\n", ""); 
            }
            madePuzzle = puzzle.createPuzzle(sPuzzle);
        }
        StateNode init = puzzle.getInitialStateNode();
        SearchData compute = solve(init);
        System.out.println("d  | Total Cases | Search Cost H1 | Total Time H1 | Search Cost H2 | Total Time H2");
        System.out.println(compute.depth + " | " + 1 + " | " + compute.searchCostH1 + " | " + compute.totalTimeH1 + " | " + compute.searchCostH2 + " | " + compute.totalTimeH2);
    }
    
    public void importPuzzle(){
        Map<Integer,ArrayList<SearchData>> runtimeData = new TreeMap<>();
        ArrayList<String> puzzleList = new ArrayList<>();
        String fileStr = "";
        Puzzle puzzle = new InputPuzzle();
        System.out.println("Enter the name of the text file you wish to import");
        final String dir = System.getProperty("user.dir");
        System.out.println("The CWD is " + dir);
        Scanner kb = new Scanner(System.in);
        fileStr = kb.nextLine();
        try{
            File file = new File(fileStr);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            try {
                while((line = br.readLine()) != null){
                    if(Character.isDigit(line.charAt(0))){
                        puzzleList.add(line);
                    }
                }
                br.close();
                fr.close();
            } catch (IOException ex) {
                System.out.println("IO Exception Occured!");
                System.exit(0);
            }
        }catch(FileNotFoundException e){
            System.out.println("File Not Found!");
            System.exit(0);
        }
        if(puzzleList.size() > 0){
            for(String line : puzzleList){
                boolean madePuzzle = puzzle.createPuzzle(line);
                if(madePuzzle){
                    SearchData compute = solve(puzzle.getInitialStateNode());
                    if(!runtimeData.containsKey(compute.depth)){
                        runtimeData.put(compute.depth, new ArrayList<>());
                    }
                    runtimeData.get(compute.depth).add(compute);
                }
            }
            System.out.println("d  | Total Cases | Search Cost H1 | Total Time H1 | Search Cost H2 | Total Time H2");
            runtimeData.entrySet().stream().forEach((entry) -> {
                int h1AvgCost=0,h1AvgTime=0,h2AvgCost=0,h2AvgTime=0,total=entry.getValue().size();
                for(int i = 0; i < entry.getValue().size(); ++i){
                    SearchData data = entry.getValue().get(i);
                    h1AvgCost += data.searchCostH1;
                    h1AvgTime += data.totalTimeH1;
                    h2AvgCost += data.searchCostH2;
                    h2AvgTime += data.totalTimeH2;
                }
                System.out.println("----------------------------------------------------------------------------------");
                System.out.println(entry.getKey() + " | " + total + " | " + (h1AvgCost/total) + " | " + (h1AvgTime/total)
                        + " ms | " + (h2AvgCost/total) + " | " + (h2AvgTime/total) + " ms");
            });           
        }else{
            System.out.println("The puzzle list size is 0!");
        }
    }
    
    private SearchData solve(StateNode init){     
        System.out.println(init);
        System.out.println("--------------- STARTING TO SOLVE PUZZLE USING H1 -------------");
        long start1 = System.currentTimeMillis();
        StateNode goalNode1 = aStar.runAStar(init, true, true);
        long end1 = System.currentTimeMillis();
        long total1 = end1 - start1;
        System.out.println("-------------------- FINISHED H1, STARTING H2 ------------------------");
        long start2 = System.currentTimeMillis();
        StateNode goalNode2 = aStar.runAStar(init, false, true);
        long end2 = System.currentTimeMillis();
        long total2 = end2 - start2;
        System.out.println("-------------------- FINISHED H2 ------------------------");
        System.out.println("Solved Using H1\nDepth: " + goalNode1.getCost() 
                         + " - Search Cost: " + goalNode1.getSearchCost()
                         + " - Fringe Size: " + goalNode1.getFringeSize() 
                         + " - Explored Set Size: " + goalNode1.getExploredSize()
                         + " - Total Time: " + total1 + " ms");
        System.out.println("Solved Using H2\nDepth: " + goalNode2.getCost() 
                         + " - Search Cost: " + goalNode2.getSearchCost()
                         + " - Fringe Size: " + goalNode2.getFringeSize() 
                         + " - Explored Set Size: " + goalNode2.getExploredSize()
                         + " - Total Time: " + total2 + " ms"+"\n");
        if(goalNode1.getCost() != goalNode2.getCost()){
            System.out.println(goalNode1.getCost() + " != " + goalNode2.getCost());
            System.out.println("The depths calculated from the heursitics are not the same! Exiting!");
            System.exit(0);           
        }
        return new SearchData(goalNode1.getCost(),goalNode1.getSearchCost(),total1,goalNode2.getSearchCost(), total2);
    }

    /**
     * A struct to hold all the search data from solving a puzzle. Will be used 
     * to calculate averages
     */
    private class SearchData {
        public int depth;
        public int searchCostH1;
        public long totalTimeH1;
        public int searchCostH2;
        public long totalTimeH2;
        public SearchData(int d, int sCostH1, long tTimeH1, int sCostH2, long tTimeH2){
            depth = d;
            searchCostH1 = sCostH1;
            totalTimeH1 = tTimeH1;
            searchCostH2 = sCostH2;
            totalTimeH2 = tTimeH2;
        }
       /* @Override
        public String toString(){
            return "Depth: " + depth + " -- Search Cost: " + searchCost + " -- Total Time: " + totalTime + " -- Heuristic: h" + heuristic;
        }*/      
    }
}
