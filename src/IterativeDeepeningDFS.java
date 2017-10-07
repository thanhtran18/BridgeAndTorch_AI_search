import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class IterativeDeepeningDFS
{

    private static int depth;

    public static void processIterativeDeepening(ArrayList<Person> leftSide, int maxTime, int maxDepth)
    {
        ProblemState newState = new ProblemState(0, maxTime, new ArrayList<>(), leftSide, Side.LEFT, 0, null);
        NodeOfState rootNode = new NodeOfState(newState);
        HashSet<Integer> visitedStates = new HashSet<>();
        Stack<NodeOfState> states = new Stack<>();
        states.add(rootNode);
        visitedStates.add(rootNode.getCurrState().hashCode());
        performIterativeDeepening(states, visitedStates, maxDepth);
    }

    public static void performIterativeDeepening(Stack<NodeOfState> states, HashSet<Integer> visitedStates, int maxDepth)
    {
        int count = 1;
        while ( !states.isEmpty() )
        {
            NodeOfState temp = states.pop();
            if ( !temp.getCurrState().areWeDone() )
            {
                depthLimitedSearch(states, temp, visitedStates, maxDepth);
                maxDepth++;
            }
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

                for (int i = 0; i < stackSize; i++)
                {
                    temp = solution.pop();
                    System.out.println(temp.getCurrState().toString());
                    timeSpent += temp.getCurrState().getTimeSpent();
                    System.out.println();
                }

                System.out.println("Total time spent: " + timeSpent);
                System.out.println("DFS cost: " + temp.getCurrState().calculateCost());
                System.out.println("Number of nodes processed: " + count);
                System.out.println("Solution found at depth: " + depth);
                System.exit(0);
            }
        } //while

    } //performIterativeDeepening

    public static void depthLimitedSearch(Stack<NodeOfState> states, NodeOfState temp, HashSet<Integer> visitedStates, int maxDepth)
    {
        int count = 1;
        depth = 0;
        //while ( !states.isEmpty() && depth < maxDepth )
        while ( depth < maxDepth )
        {
            //NodeOfState temp = states.pop();

            //if ( !temp.getCurrState().areWeDone() )
            //{
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
                count++;
                depth++;
            //} //if
            //System.exit(0);

        } //while

    } //performDFS
}
