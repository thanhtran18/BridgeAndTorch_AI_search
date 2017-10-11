//-----------------------------------------
// CLASS: DFSearch
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that contains all methods needed to solve the problem by DFS.
//
//-----------------------------------------

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class DFSearch
{
    private static int numberOfTries = 1;
    //------------------------------------------------------
    // processDFS
    //
    // PURPOSE:	sets up the problem, calls other methods to solve the problem using DFS
    // PARAMETERS:
    //		ArrayList: all characters on the left side that need to be moved to the other side
    //      int      : the time constraint of the problem
    // Returns: None
    //------------------------------------------------------
    public static void processDFS(ArrayList<Person> leftSide, int maxTime)
    {
        //set up the initial state
        ProblemState newState = new ProblemState(0, maxTime, new ArrayList<>(), leftSide, Side.LEFT, 0, null);
        NodeOfState rootNode = new NodeOfState(newState);
        HashSet<Integer> visitedStates = new HashSet<>(); //visited states
        Stack<NodeOfState> states = new Stack<>();        //all states
        states.add(rootNode);
        visitedStates.add(rootNode.getCurrState().hashCode());
        performDFS(states, visitedStates); //solve the problem
    } //processDFS

    //------------------------------------------------------
    // performDFS
    //
    // PURPOSE:	solves the problem using BFS algorithm
    // PARAMETERS:
    //		Stack       : all states
    //      HashSet     : all the visited states
    // Returns: None
    //------------------------------------------------------
    public static void performDFS(Stack<NodeOfState> states, HashSet<Integer> visitedStates)
    {
        int count = 1;  //counts number of processed nodes
        while ( !states.isEmpty() )
        {
            NodeOfState temp = states.pop();

            if ( !temp.getCurrState().areWeDone() )
            {
                ArrayList<ProblemState> currChildren = temp.getCurrState().generateNextStates();

                //loop through all children, add them to the stack of all states if they have not been processed
                for (int i = 0; i < currChildren.size(); i++)
                {
                    NodeOfState newNode = new NodeOfState(temp, currChildren.get(i), temp.getActualCost() + currChildren.get(i).calculateCost(), 0);
                    int hashCode = newNode.getCurrState().hashCode();
                    if ( !visitedStates.contains(hashCode) )
                    {
                        states.push(newNode);
                        visitedStates.add(hashCode);
                    }
                }
                count++;
            } //if
            //get the solution
            else
            {
                Stack<NodeOfState> solution = new Stack<>();
                solution.push(temp);
                temp = temp.getParentState();

                while (temp.getParentState() != null)
                {
                    solution.push(temp);
                    temp =temp.getParentState();
                }

                solution.push(temp);

                int stackSize = solution.size();
                int timeSpent = 0;

                StringBuilder output = new StringBuilder();

                //empty the solution stack
                for (int i = 0; i < stackSize; i++)
                {
                    temp = solution.pop();
                    output.append(temp.getCurrState().toString());
                    timeSpent = temp.getCurrState().getTimeSpent();
                }

                if (timeSpent > temp.getCurrState().getTimeConstraint())
                {
                    /*System.out.println("Total time spent by DFS: " + timeSpent);
                    System.out.println("DFS cost: " + temp.getActualCost());
                    System.out.println("\nDFS has failed to move all people to the other side on time for the " + numberOfTries + " time! :(");
                    System.out.println("\n***** RETRY DFS *****");*/
                    numberOfTries++;
                    performDFS(states, visitedStates);
                }
                else
                {
                    System.out.println(output.toString());
                    System.out.println("Total time spent by DSF: " + timeSpent);
                    System.out.println("DFS cost: " + temp.getActualCost());
                    System.out.println("Number of nodes processed: " + count);
                    System.out.println("\nSUCCESSFULLY solved the problem by DFS after " + numberOfTries + " tries!");
                }
                break;
            }
        } //while
    } //performDFS
} //class
