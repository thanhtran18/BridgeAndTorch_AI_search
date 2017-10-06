import java.util.*;

public class AStartAlgorithm
{
    public static void processAStar(ArrayList<Person> leftSide, int maxTime)
    {
        ProblemState rootState = new ProblemState(0, maxTime, new ArrayList<Person>(), leftSide, Side.LEFT, 0, null);
        //ProblemNode rootNode = new ProblemNode(rootState);
        ProblemState lastState = applyAStar(rootState);
        String solution = getSolutionToGoalState(lastState);
        System.out.println(solution);

    }

    public static ProblemState applyAStar(ProblemState currState) //performAStar
    {
        if ( !currState.areWeDone() )
        {
            Comparator<ProblemState> comparator = new ComparableState();
            Queue<ProblemState> open = new PriorityQueue<>(10, comparator);
            HashSet<ProblemState> closed = new HashSet<>();
            open.add(currState);
            while ( !open.isEmpty() )
            {
                ProblemState current = open.poll();
                closed.add(current);

                for (ProblemState newState : current.generateNextStates())
                {
                    if ( newState.areWeDone() )
                    {
                        return newState;
                    }
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
                            }
                        }

                    }
                }
            }

        }
        else
            return currState;
        return null;
    } //applyAStar

    public static ProblemState swapProblemStates(ProblemState state) //reverseStates
    {
        ProblemState currState = state;
        ProblemState parentState = null;

        while (currState != null)
        {
            ProblemState tempState = currState.getParentState();
            currState.setParentState(parentState);
            parentState = currState;
            currState = tempState;
        }
        return parentState;
    }

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
    }
}
