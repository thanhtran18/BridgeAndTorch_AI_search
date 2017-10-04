//contains every necessary information for a certain problem state (including ProblemState object)
public class ProblemNode
{
    private ProblemState parentState;
    private ProblemState currState;
    private double heristicCost; //for A* algorithm
    private double actualCost;
    private double functionCost;

    public ProblemNode(ProblemState currState) //construct the root node
    {
        parentState = null;
        this.currState = currState;
        heristicCost = 0;
        actualCost = 0;
        functionCost = 0;
    }

    public ProblemNode(ProblemState parentState, ProblemState currState, double heuristicCost, double actualCost)
    {
        this.parentState = parentState;
        this.currState = currState;
        this.heristicCost = heuristicCost;
        this.actualCost = actualCost;
        functionCost = heuristicCost + actualCost;
    }

    public ProblemState getCurrState()
    {
        return currState;
    }

    public ProblemState getParentState()
    {
        return parentState;
    }

    public double getActualCost()
    {
        return actualCost;
    }

    public double getHeristicCost()
    {
        return heristicCost;
    }

    public double getFunctionCost()
    {
        return functionCost;
    }
}
