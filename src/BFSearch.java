//-----------------------------------------
// CLASS: BFSearch
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that contains all methods needed to solve the problem by BFS.
//
//-----------------------------------------

import java.util.*;

public class BFSearch
{
    private static int numberOfTries = 1; //number of tries taken
    //------------------------------------------------------
    // processBFS
    //
    // PURPOSE:	sets up the problem, calls other methods to solve the problem using BFS
    // PARAMETERS:
    //		ArrayList: all characters on the left side that need to be moved to the other side
    //      int      : the time constraint of the problem
    // Returns: None
    //------------------------------------------------------
    public static void processBFS(ArrayList<Person> leftSide, int maxTime)
    {
        //set up the initial state
        ProblemState newState = new ProblemState(0, maxTime, new ArrayList<>(), leftSide, Side.LEFT, 0, null);
        NodeOfState rootNode = new NodeOfState(newState);
        HashSet<Integer> visitedStates = new HashSet<>(); //visited states
        Queue<NodeOfState> states = new LinkedList<>();   //all states
        states.add(rootNode);
        visitedStates.add(rootNode.getCurrState().hashCode());
        performBFS(states, visitedStates);  //solve the problem
    } //processBFS

    //------------------------------------------------------
    // performBFS
    //
    // PURPOSE:	solves the problem using BFS algorithm
    // PARAMETERS:
    //		Queue       : all states
    //      HashSet     : all the visited states
    // Returns: None
    //------------------------------------------------------
    public static void performBFS(Queue<NodeOfState> states, HashSet<Integer> visitedStates)
    {
        int count = 1;      //counts number of processed nodes
        while ( !states.isEmpty() )
        {
            NodeOfState temp = states.poll();
            if ( !temp.getCurrState().areWeDone() )
            {
                ArrayList<ProblemState> currChildren = temp.getCurrState().generateNextStates(); //current considered children

                //loop through all possible children, check if they have been visited, add to the all states queue if they have not
                for (int i = 0; i < currChildren.size(); i++)
                {
                    NodeOfState newNode = new NodeOfState(temp, currChildren.get(i), temp.getActualCost() + currChildren.get(i).calculateCost(), 0);
                    int hashCode = newNode.getCurrState().hashCode();
                    if ( !visitedStates.contains(hashCode) )
                    {
                        states.add(newNode);
                        visitedStates.add(hashCode);
                    }
                } //for
                count++;
            } //if
            //get the solution, track the path using stack
            else
            {
                Stack<NodeOfState> solution = new Stack<>();
                solution.push(temp);
                temp = temp.getParentState();

                while (temp.getParentState() != null)
                {
                    solution.push(temp);
                    temp = temp.getParentState();
                }
                solution.push(temp);

                int stackSize = solution.size(); //initial size of the stack
                int timeSpent = 0;

                StringBuilder output = new StringBuilder();

                for (int i = 0; i < stackSize; i++)
                {
                    temp = solution.pop();
                    output.append(temp.getCurrState().toString());

                    if (i == stackSize - 1)
                        timeSpent += temp.getCurrState().getTimeSpent();
                } //for

                if (timeSpent > temp.getCurrState().getTimeConstraint())
                {
                    System.out.println("Total time spent by BFS: " + timeSpent);
                    System.out.println("BFS cost: " + temp.getActualCost());
                    System.out.println("\nBFS has failed to move all people to the other side on time for the " + numberOfTries + " time! :(");
                    System.out.println("\n***** RETRY BFS *****");
                    numberOfTries++;
                    performBFS(states, visitedStates);
                }
                else
                {
                    System.out.println(output.toString());
                    System.out.println("Total time spent by BFS: " + timeSpent);
                    System.out.println("BFS cost: " + temp.getActualCost());
                    System.out.println("Number of nodes processed: " + count);
                    System.out.println("\nSUCCESSFULLY solved the problem by BFS!");
                }
                break;
            } //else
        } //while
    } //performBFS
} //class
