//-----------------------------------------
// CLASS: AStarAlgorithm
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that contains all methods needed to solve the problem by A* algorithm
//
//-----------------------------------------

import java.util.*;

public class AStartAlgorithm
{
    //------------------------------------------------------
    // processAStar
    //
    // PURPOSE:	sets up the problem, calls other methods to solve the problem using A* algorithm
    // PARAMETERS:
    //		ArrayList: all characters on the left side that need to be moved to the other side
    //      int      : the time constraint of the problem
    // Returns: None
    //------------------------------------------------------

    public static void processAStar(ArrayList<Person> leftSide, int maxTime)
    {
        //set up the initial state
        ProblemState rootState = new ProblemState(0, maxTime, new ArrayList<Person>(), leftSide, Side.LEFT, 0, null);
        ProblemState lastState = applyAStar(rootState);         //solve the problem
        String solution = getSolutionToGoalState(lastState);    //get the solution
        System.out.println(solution);
        System.out.println("Total time spent: " + lastState.getTimeSpent());
        if (lastState.getTimeSpent() > lastState.getTimeConstraint())
            System.out.println("\nA* algorithm has failed to move all the people to the other side on time! :(");
        else
            System.out.println("\nSUCCESSFULLY solved the problem by A* algorithm!");
    } //processAStar

    //------------------------------------------------------
    // getSolutionToGoalState
    //
    // PURPOSE: prints the solution
    // PARAMETERS:
    //		ProblemState: the goal state
    // Returns: None
    //------------------------------------------------------
    public static String getSolutionToGoalState(ProblemState goal)
    {
        StringBuilder result = new StringBuilder();
        goal = swapProblemStates(goal);
        int numberOfNodes = 0;

        while (goal != null)
        {
            result.append(goal.toString());
            goal = goal.getParentState();
            numberOfNodes++;
        }

        result.append("Number of nodes processed: " + numberOfNodes);
        return result.toString();
    } //getSolutionToGoalState

    //------------------------------------------------------
    // applyAStar
    //
    // PURPOSE:	solves the problem using A* algorithm (A* algorithm implementation)
    // PARAMETERS:
    //		ProblemState: the current state of the problem
    // Returns a ProblemState which is the result
    //------------------------------------------------------
    public static ProblemState applyAStar(ProblemState currState)
    {
        if ( !currState.areWeDone() )
        {
            //set up a Comparator to be used in Queue
            Comparator<ProblemState> comparator = new ComparableState();
            Queue<ProblemState> open = new PriorityQueue<>(100, comparator); //frontier states
            HashSet<ProblemState> closed = new HashSet<>();                               //completed states
            open.add(currState);

            while ( !open.isEmpty() )
            {
                ProblemState current = open.poll();
                closed.add(current);

                for (ProblemState newState : current.generateNextStates())
                {
                    //if we are done
                    if ( newState.areWeDone() )
                        return newState;
                    else
                    {
                        boolean inClosed = closed.contains(newState);

                        if ( !inClosed )
                        {
                            boolean inOpen = open.contains(newState);
                            if (!inOpen)
                                open.add(newState);

                            else
                            {
                                ProblemState openState = null;
                                for (ProblemState state : open)
                                {
                                    if (newState.equals(state))
                                    {
                                        openState = state;
                                        break;
                                    }
                                }
                                if (newState.calculateCost() < openState.calculateCost())
                                {
                                    openState.setStateCost(newState.calculateCost());
                                    openState.setParentState(newState.getParentState());
                                }
                            } //else
                        } //if
                    } //big else
                } //for
            } //while
        } //big if
        else
            return currState;
        return null;
    } //applyAStar

    //------------------------------------------------------
    // swapProblemStates
    //
    // PURPOSE:	this is used when we want to print in order (backwards)
    // PARAMETERS:
    //		ProblemState: current state
    // Returns:
    //------------------------------------------------------
    public static ProblemState swapProblemStates(ProblemState state)
    {
        ProblemState currState = state;     //current state
        ProblemState parentState = null;

        while (currState != null)
        {
            ProblemState tempState = currState.getParentState();
            currState.setParentState(parentState);
            parentState = currState;
            currState = tempState;
        }
        return parentState;
    } //swapProblemStates
} //class
