import java.util.*;

public class BFSearch
{
    public static void processBFS(ArrayList<Person> leftSide, int maxTime)
    {
        ProblemState newState = new ProblemState(0, maxTime, new ArrayList<>(), leftSide, Side.LEFT, 0, null);
        NodeOfState rootNode = new NodeOfState(newState);
        HashSet<Integer> visitedStates = new HashSet<>();
        Queue<NodeOfState> states = new LinkedList<>();

        states.add(rootNode);
        visitedStates.add(rootNode.getCurrState().hashCode());
        performBFS(states, visitedStates);
    }

    public static void performBFS(Queue<NodeOfState> states, HashSet<Integer> visitedStates)
    {
        int count = 1;
        while ( !states.isEmpty() )
        {
            NodeOfState temp = states.poll();
            if ( !temp.getCurrState().areWeDone() )
            {
                ArrayList<ProblemState> currChildren = temp.getCurrState().generateNextStates();

                for (int i = 0; i < currChildren.size(); i++)
                {
                    NodeOfState newNode = new NodeOfState(temp, currChildren.get(i), temp.getActualCost() + currChildren.get(i).calculateCost(), 0);
                    int hashCode = newNode.getCurrState().hashCode();
                    if ( !visitedStates.contains(hashCode) )
                    {
                        states.add(newNode);
                        visitedStates.add(hashCode);
                    }
                }
                count++;
            } //if
            else //get the solution, track the path using stack
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
                    System.out.println(temp.getCurrState().toString());

                    if (i == stackSize - 1)
                        timeSpent += temp.getCurrState().getTimeSpent();
                    //System.out.println("\n");
                } //for

                System.out.println("Total time spent: " + timeSpent);
                System.out.println("BFS cost: " + temp.getActualCost());
                System.out.println("Number of nodes processed: " + count);
                System.exit(0);
            } //else
        } //while

        System.out.println("Error in performing breadth-first search!");
    } //performBFS
}
