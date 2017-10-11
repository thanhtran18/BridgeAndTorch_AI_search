//-----------------------------------------
// CLASS: NodeOfState
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that contains every necessary information for a certain problem state (including ProblemState object)
//
//-----------------------------------------
//contains every necessary information for a certain problem state (including ProblemState object)
public class NodeOfState
{
    private NodeOfState parentState;//previous state of the problem
    private ProblemState currState; //current state of the problem
    private double heuristicCost;   //h, for A* algorithm
    private double actualCost;      //g
    private double functionCost;    //f

    //------------------------------------------------------
    // NodeOfState Constructor
    //
    // PURPOSE:	Initializes this object
    // PARAMETERS:
    //		ProblemState : current state of the problem
    // Returns: None
    //------------------------------------------------------
    public NodeOfState(ProblemState currState) //construct the root node
    {
        parentState = null;
        this.currState = currState;
        heuristicCost = 0;
        actualCost = 0;
        functionCost = 0;
    }

    //------------------------------------------------------
    // NodeOfState Constructor
    //
    // PURPOSE:	Initializes this object
    // PARAMETERS:
    //      NodeOfState  : the node contains the previous state
    //		ProblemState : current state of the problem
    //      actualCost   : g cost
    //      heuristicCost: h cost
    // Returns: None
    //------------------------------------------------------
    public NodeOfState(NodeOfState parentState, ProblemState currState, double actualCost, double heuristicCost)
    {
        this.parentState = parentState;
        this.currState = currState;
        this.heuristicCost = heuristicCost;
        this.actualCost = actualCost;
        functionCost = heuristicCost + actualCost;
    }

    //------------------------------------------------------
    // getCurrState
    //
    // PURPOSE:	get the current state of the problem
    // PARAMETERS: none
    // Returns:
    //		ProblemState: the current problem state
    //------------------------------------------------------
    public ProblemState getCurrState()
    {
        return currState;
    }

    //------------------------------------------------------
    // getParentState
    //
    // PURPOSE:	get the parent state of the problem
    // PARAMETERS: none
    // Returns:
    //		ProblemState: the parent problem state
    //------------------------------------------------------
    public NodeOfState getParentState()
    {
        return parentState;
    }

    //------------------------------------------------------
    // getActualCost
    //
    // PURPOSE:	get the actual cost of current state
    // PARAMETERS: none
    // Returns:
    //		double: the actual cost
    //------------------------------------------------------
    public double getActualCost()
    {
        return actualCost;
    }

    //------------------------------------------------------
    // getHeuristicCost
    //
    // PURPOSE:	get the heuristic cost of current state
    // PARAMETERS: none
    // Returns:
    //		double: the heuristic cost
    //------------------------------------------------------
    public double getHeristicCost()
    {
        return heuristicCost;
    }

    //------------------------------------------------------
    // getFunctionCost
    //
    // PURPOSE:	get the function cost of current state
    // PARAMETERS: none
    // Returns: the function cost
    //------------------------------------------------------
    public double getFunctionCost()
    {
        return functionCost;
    }
}
