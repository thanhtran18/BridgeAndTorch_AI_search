//-----------------------------------------
// CLASS: IterativeDeepeningDFS
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that contains all methods needed to solve the problem by iterative deepening DFS
//
//-----------------------------------------

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class IterativeDeepeningDFS
{

    private static int depth;   //given depth all each level of iterative deepening

    //------------------------------------------------------
    // processIterativeDeepening
    //
    // PURPOSE:	sets up the problem, calls other methods to solve the problem using iterative deepening DFS
    // PARAMETERS:
    //		ArrayList: all characters on the left side that need to be moved to the other side
    //      int      : the time constraint of the problem
    //      int      : the maximum depth
    // Returns: None
    //------------------------------------------------------
    public static void processIterativeDeepening(ArrayList<Person> leftSide, int maxTime, int maxDepth)
    {
        //set up the problem
        ProblemState newState = new ProblemState(0, maxTime, new ArrayList<>(), leftSide, Side.LEFT, 0, null);
        NodeOfState rootNode = new NodeOfState(newState);
        HashSet<Integer> visitedStates = new HashSet<>(); //visited states
        Stack<NodeOfState> states = new Stack<>();          //all states
        states.add(rootNode);
        visitedStates.add(rootNode.getCurrState().hashCode());
        performIterativeDeepening(states, visitedStates, maxDepth); //solve
    }

    //------------------------------------------------------
    // depthLimitedSearch
    //
    // PURPOSE:	solves the problem using DFS with a limited depth
    // PARAMETERS:
    //		Stack       : all states
    //      HashSet     : all the visited states
    //      int         : maximum depth
    // Returns: None
    //------------------------------------------------------
    public static void depthLimitedSearch(Stack<NodeOfState> states, NodeOfState temp, HashSet<Integer> visitedStates, int maxDepth)
    {
        depth = 0; //initial depth

        //get deeper till the maximum allowed depth
        while ( depth < maxDepth )
        {
            ArrayList<ProblemState> currChildren = temp.getCurrState().generateNextStates();

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
            depth++;
        } //while
    } //depthLimitedSearch

    //------------------------------------------------------
    // performIterativeDeepening
    //
    // PURPOSE:	solves the problem using BFS algorithm
    // PARAMETERS:
    //		Stack       : all states
    //      HashSet     : all the visited states
    //      int         : maximum depth
    // Returns: None
    //------------------------------------------------------
    public static void performIterativeDeepening(Stack<NodeOfState> states, HashSet<Integer> visitedStates, int maxDepth)
    {
        int count = 1;
        while ( !states.isEmpty() )
        {
            NodeOfState temp = states.pop();
            if ( !temp.getCurrState().areWeDone() )
            {
                depthLimitedSearch(states, temp, visitedStates, maxDepth);
                maxDepth++; //increase the maximum allowed depth if necessary
            }
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

                int stackSize = solution.size();
                int timeSpent = 0;

                for (int i = 0; i < stackSize; i++)
                {
                    temp = solution.pop();
                    System.out.print(temp.getCurrState().toString());
                    timeSpent += temp.getCurrState().getTimeSpent();
                }

                System.out.println("Total time spent: " + timeSpent);
                System.out.println("DFS cost: " + temp.getCurrState().calculateCost());
                System.out.println("Number of nodes processed: " + count);
                System.out.println("Solution found at depth: " + depth);
                if (timeSpent > temp.getCurrState().getTimeConstraint())
                    System.out.println("\nIterative Deepening DFS has failed to move all people to the other side on time! :(");
                break;
            }
        } //while

    } //performIterativeDeepening


}
