CS420: Artificial Intelligence

Project Description  

The A* search can be used to solve the 8-puzzle problem. As described in the book, there are two candidate heuristic functions: (1) h1 = the number of misplaced tiles; (2) h2 = the sum of the distances of the tiles from their goal positions.

You are to implement the A* using both heuristics and compare their efficiency in terms of depth of the solution and search costs.  The following figure (Figure 3.29 in the book) provides some data points that you can refer to. 
To test your program and analyze the efficiency, you should generate random problems (>100 cases) with different solution lengths. Please collect data on the different solution lengths that you have tested, with their corresponding search cost (# of nodes generate). 
A good testing program should test a range of possible cases(2 <= d <= 20). Note that the average solution cost for a randomly generated 8-puzzle instance is about 22 steps.

The input of your program should be either (1) a randomly generated 8-puzzle problem by your program; or (2) a specific 8-puzzle configuration entered through the standard input, which contains the configuration for only one puzzle, in the following format:

1 2 4
0 5 6
8 3 7

The goal state is:

0 1 2
3 4 5
6 7 8

Where 0 represents the empty tile.

Please handle the input/output gracefully.

Note that the 8-puzzle states are divided into two disjoint sets, such that any state is reachable from any other state in the same set, which no state is reachable from any state in the other set. Before you solve a puzzle, you need to make sure that it's solvable. 


______________________________________________________________________________________________________________________________________________________________________
Compile the Java source code
        `javac .\AI_8Puzzle\src\*.java`
Run the program
        `java .\AI_8Puzzle\src\`
Select the option you wish to see
        1) Create and solve a random puzzle
        2) Create your own puzzle then solve
        3) Import a text file to create puzzles then solve
Option 1:
        After selecting this option, the program will ask you how many times to run it
        Type in an integer and hit enter, the program will then create and solve them all
        **NOTE** This will take some time since the average depth is around 24
Option 2:
        The program will ask you to enter a puzzle, it accepts two formats:
            a) 012345678
            b) 0 1 2
                3 4 5
                6 7 8
         If you use format b then you will need to enter all three lines before the program
         will notify you
         Once you finish, the puzzle will be created and then solved
Option 3:
         The program will ask you for a file name with your list of puzzles
         The program will accept input from the file if it is in the format
              012345678\n
         It will ignore any lines where the first character is not a number
         The program will create a puzzle for every line within that file