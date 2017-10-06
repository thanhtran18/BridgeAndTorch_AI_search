//contains every necessary information for a certain problem state (including ProblemState object)
public class NodeOfState
{
    private NodeOfState parentState;
    private ProblemState currState;
    private double heuristicCost; //for A* algorithm
    private double actualCost;
    private double functionCost;

    public NodeOfState(ProblemState currState) //construct the root node
    {
        parentState = null;
        this.currState = currState;
        heuristicCost = 0;
        actualCost = 0;
        functionCost = 0;
    }

    public NodeOfState(NodeOfState parentState, ProblemState currState, double actualCost, double heuristicCost)
    {
        this.parentState = parentState;
        this.currState = currState;
        this.heuristicCost = heuristicCost;
        this.actualCost = actualCost;
        functionCost = heuristicCost + actualCost;
    }

    public ProblemState getCurrState()
    {
        return currState;
    }

    public NodeOfState getParentState()
    {
        return parentState;
    }

    public double getActualCost()
    {
        return actualCost;
    }

    public double getHeristicCost()
    {
        return heuristicCost;
    }

    public double getFunctionCost()
    {
        return functionCost;
    }
}
