import java.util.*;

public class AStartAlgorithm
{
    public static void processAStar(ArrayList<Person> leftSide, int maxTime)
    {
        ProblemState rootState = new ProblemState(0, maxTime, new ArrayList<Person>(), leftSide, Side.LEFT, 0, 0, null);
        ProblemNode rootNode = new ProblemNode(rootState);
        //perfrom searching
    }

    public static ProblemState applyAStar(ProblemState currState)
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

                for (ProblemState newState : current.generateNextState())
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
                                    openState.setStateCost((int)newState.calculateCost());
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
}
