//-----------------------------------------
// NAME		    : CONG THANH TRAN
// STUDENT NUMBER	: 7802106
// COURSE		: COMP 3190 - Introduction to Artificial Intelligence
// INSTRUCTOR	: JOHN BRAICO
// ASSIGNMENT	: assignment #1
// QUESTION	    : question #1
//
// REMARKS: Solving the Bridge and Torch problem with different strategies: Heuristic (A* algorithm), BFS, DFS and iterative deepening
//
//-----------------------------------------

import java.util.ArrayList;

public class A1Q1
{
    //------------------------------------------------------
    // main
    //
    // PURPOSE:	main method - gets things going
    // PARAMETERS:
    //		String[]: commandline argument list
    // Returns: none
    //------------------------------------------------------
    public static void main(String[] args)
    {
        if (args.length > 0)
        {
            int maxTime = 0;        //maximum time constraint given by the user
            int[] crossingTimes;    //array of the crossing times of the characters
            ArrayList<Person> initialPeople = new ArrayList<>();    //list of all characters

            //Get input from users, the first number will be taken as the time constraint
            crossingTimes = new int[args.length-1];
            maxTime = Integer.parseInt(args[0]);

            for (int i = 0; i < args.length - 1; i++)
                crossingTimes[i] = Integer.parseInt(args[i + 1]);

            for (int i = 0; i < crossingTimes.length; i++)
                initialPeople.add(new Person(crossingTimes[i], Side.LEFT));

            //Call different methods from different classes to solve the problem with different strategies
            System.out.println("\n***** A* ALGORITHM *****");
            AStartAlgorithm.processAStar(initialPeople, maxTime);
            System.out.println("\n***** BFS ALGORITHM *****");
            BFSearch.processBFS(initialPeople, maxTime);
            System.out.println("\n***** DFS ALGORITHM *****");
            DFSearch.processDFS(initialPeople, maxTime);
            System.out.println("\n***** ITERATIVE DEEPENING DFS ALGORITHM *****");
            IterativeDeepeningDFS.processIterativeDeepening(initialPeople, maxTime, 3);
        }
    } //main
} //class
