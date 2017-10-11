//-----------------------------------------
// CLASS: ProblemState
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that contains every necessary information for a a state
//-----------------------------------------

import java.util.ArrayList;
import java.util.Collections;

public class ProblemState
{
    private int timeSpent;              //total time spent to solve the prople
    private int timeConstraint;         //given maximum amount of time
    private ArrayList<Person> rightSide;//list of people ont the right sie of the river
    private ArrayList<Person> leftSide; //list of people ont the left sie of the river
    private Side torchSide;             //the side of the torch
    private int stateCost;              //stateValue
    private int stateLevel;             //actual cost
    private ProblemState parentState;

    //------------------------------------------------------
    // ProblemState Constructor
    //
    // PURPOSE:	Initializes this object
    // PARAMETERS:
    // Returns: None
    //------------------------------------------------------
    public ProblemState(int timeSpent, int timeConstraint, ArrayList<Person> peopleOnRightSide,
                        ArrayList<Person> peopleOnLeftSide, Side torchSide, int newStateLevel, ProblemState parentState)
    {
        this.timeSpent = timeSpent;
        this.timeConstraint = timeConstraint;
        this.rightSide = new ArrayList<>(peopleOnRightSide);
        this.leftSide = new ArrayList<>(peopleOnLeftSide);
        Collections.sort(this.leftSide);
        this.torchSide = torchSide;
        this.stateLevel = newStateLevel;
        this.stateCost = calculateCost();

        this.parentState = parentState;
    } //constructor

    //------------------------------------------------------
    // clone (override)
    //
    // PURPOSE:	deep copy this object
    // PARAMETERS: None
    // Returns: the new version of this object
    //------------------------------------------------------
    @Override
    public ProblemState clone()
    {
        int currTimeSpent = timeSpent;
        int currTimeConstraint = timeConstraint;
        ArrayList<Person> currPeopleOnRightSide = new ArrayList<>();
        for (Person currPerson : rightSide)
        {
            Person clonedPerson = currPerson.clone();
            currPeopleOnRightSide.add(clonedPerson);
        }
        ArrayList<Person> currPeopleOnLeftSide = new ArrayList<>();
        for (Person currPerson : leftSide)
        {
            Person clonedPerson = currPerson.clone();
            currPeopleOnLeftSide.add(clonedPerson);
        }
        Side currTorchSide = torchSide;
        int currStateLevel = stateLevel;

        return new ProblemState(currTimeSpent, currTimeConstraint, currPeopleOnRightSide, currPeopleOnLeftSide,
                currTorchSide, currStateLevel, parentState);
    } //clone


    //------------------------------------------------------
    // areWeDone
    //
    // PURPOSE:	check if we finish the solving the problem
    // PARAMETERS: None
    // Returns: true if we're done, false otherwise
    //------------------------------------------------------
    public boolean areWeDone()
    {
        boolean result = false;
        if (leftSide.size() == 0)
            result = true;
        return result;
    }

    //------------------------------------------------------
    // calculateHeuristic
    //
    // PURPOSE:	calculate the heuristic cost of the state
    // PARAMETERS: None
    // Returns: the heuristic cost
    //------------------------------------------------------
    public int calculateHeuristic()
    {
        int number = this.leftSide.size()/2;
        int count = this.leftSide.size() - 1;
        int cost = 0;
        Collections.sort(leftSide);

        while (number > 0)
        {
            cost += this.leftSide.get(count).getCrossingTime();
            count--;
            number--;
        }
        return cost;
    }

    //------------------------------------------------------
    // calculateCost
    //
    // PURPOSE:	calculate the actual cost of the state
    // PARAMETERS: None
    // Returns: the new version of this object
    //------------------------------------------------------
    public int calculateCost()
    {
        int heuristic = this.calculateHeuristic();
        stateCost = heuristic + this.stateLevel;
        return stateCost;
    }

    //------------------------------------------------------
    // getNewState
    //
    // PURPOSE:	get a new state after the current state
    // PARAMETERS:
    //      ProblemOperation: the operation
    //      ProblemState    : the privous state
    // Returns:
    //		double: the crossing time
    //------------------------------------------------------
    public ProblemState getNewState(ProblemOperation operation, ProblemState prevState)
    {
        ProblemOperation thisOperation = operation;

        if (torchSide.equals(Side.LEFT))
        {
            Side newSide = Side.RIGHT;
            int level = stateLevel + thisOperation.getMovingTime();
            ProblemState newState = new ProblemState(timeSpent, timeConstraint, rightSide, leftSide, newSide, level, prevState);
            newState.leftSide.removeAll(thisOperation.getPeople());
            newState.rightSide.addAll(thisOperation.getPeople());
            newState.timeSpent += thisOperation.getMovingTime();
            newState.calculateCost();
            return newState;
        }
        else
        {
            Side newSide = Side.LEFT;
            int level = stateLevel + thisOperation.getMovingTime();
            ProblemState newState = new ProblemState(timeSpent, timeConstraint, rightSide, leftSide, newSide, level, prevState);
            newState.rightSide.removeAll(thisOperation.getPeople());
            newState.leftSide.addAll(thisOperation.getPeople());
            newState.timeSpent += thisOperation.getMovingTime();
            newState.calculateCost();
            return newState;
        }
    }

    //------------------------------------------------------
    // setParentState
    //
    // PURPOSE:	set the current parent state to a new parent state
    // PARAMETERS:
    //      ProblemState : new problem state
    // Returns: None
    //------------------------------------------------------
    public void setParentState(ProblemState parentState)
    {
        this.parentState = parentState;
    }

    //------------------------------------------------------
    // generateNextStates
    //
    // PURPOSE:	generate all possible children
    // PARAMETERS: none
    // Returns: a list of all children
    //------------------------------------------------------
    public ArrayList<ProblemState> generateNextStates()
    {
        ArrayList<ProblemState> children = new ArrayList<>();
        ArrayList<ProblemOperation> allPossibleOps = createAllPossibleOperations();

        //generate all possible operations that could be the children
        for (ProblemOperation currOperation : allPossibleOps)
            children.add(getNewState(currOperation, this));

        return children;
    } //generateNextStates

    //------------------------------------------------------
    // createAllPossibleOperations
    //
    // PURPOSE:	create all possible operations for the current state
    // PARAMETERS: none
    // Returns: a list of all possible operations
    //------------------------------------------------------
    public ArrayList<ProblemOperation> createAllPossibleOperations()
    {
        ArrayList<ProblemOperation> allOperations = new ArrayList<>();

        if (torchSide == Side.LEFT)
            allOperations = createAllLeftToRightOperations();
        else
            allOperations = createAllRightToLeftOperations();

        return allOperations;
    }

    //------------------------------------------------------
    // createAlLeftToRightOperations
    //
    // PURPOSE:	create all possible operations for the current state from left to right
    // PARAMETERS: none
    // Returns: a list of all possible operations
    //------------------------------------------------------
    public ArrayList<ProblemOperation> createAllLeftToRightOperations()
    {
        ArrayList<ProblemOperation> ltrOperations = new ArrayList<>();
        for (int i = 0; i < leftSide.size() - 1; i++)
        {
            Person p1 = leftSide.get(i);
            for (int j = i + 1; j < leftSide.size(); j++)
            {
                Person p2 = leftSide.get(j);
                int movingTime = getCrossingTimeOfTwo(p1, p2);
                ArrayList<Person> peopleOnTheMove = new ArrayList<>();
                peopleOnTheMove.add(p1);
                peopleOnTheMove.add(p2);
                ltrOperations.add(new ProblemOperation(peopleOnTheMove, movingTime));
            }
        }
        return ltrOperations;
    } //createAllLeftToRightOperations

    //------------------------------------------------------
    // createARightToLeftOperations
    //
    // PURPOSE:	create all possible operations for the current state from right to left
    // PARAMETERS: none
    // Returns: a list of all possible operations
    //------------------------------------------------------
    public ArrayList<ProblemOperation> createAllRightToLeftOperations()
    {
        ArrayList<ProblemOperation> rtlOperations = new ArrayList<>();
        for (int i = 0; i < rightSide.size() - 1; i++)
        {
            Person currPerson = rightSide.get(i);
            int movingTime = currPerson.getCrossingTime();
            ArrayList<Person> peopleOnTheMove = new ArrayList<>();
            peopleOnTheMove.add(currPerson);
            rtlOperations.add(new ProblemOperation(peopleOnTheMove, movingTime));
        }
        return rtlOperations;
    } //createAllRightToLeftOperations

    //------------------------------------------------------
    // getCrossingTimeOfTwo
    //
    // PURPOSE:	get crossing of two people crossing together
    // PARAMETERS: none
    // Returns: the crossing time
    //------------------------------------------------------
    public int getCrossingTimeOfTwo(Person p1, Person p2)
    {
        return (p1.getCrossingTime() > p2.getCrossingTime() ? p1.getCrossingTime():p2.getCrossingTime());
    }

    //------------------------------------------------------
    // setStateCost
    //
    // PURPOSE:	set to the new cost
    // PARAMETERS: the new cost
    // Returns: None
    //------------------------------------------------------
    public void setStateCost(int stateCost)
    {
        this.stateCost = stateCost;
    }

    //------------------------------------------------------
    // getParentState
    //
    // PURPOSE:	get the previous state
    // PARAMETERS: None
    // Returns: the previous state
    //------------------------------------------------------
    public ProblemState getParentState()
    {
        return parentState;
    }

    //------------------------------------------------------
    // toString
    //
    // PURPOSE:	print the state after each move
    // PARAMETERS: None
    // Returns: the string output
    //------------------------------------------------------
    @Override
    public String toString()
    {
        if (timeConstraint > 0)
        {
            StringBuilder result = new StringBuilder();
            result.append("\nLeft side: ");
            for (Person currPerson : leftSide)
                result.append(currPerson.getCrossingTime() + " ");
            result.append("| ");
            result.append("Right side: ");
            for (Person currPerson : rightSide)
                result.append(currPerson.getCrossingTime() + " ");
            result.append("| ");
            result.append("Torch side: " + getTorchSide());
            result.append("| ");
            result.append("Time spent: " + timeSpent);
            return result.toString();
        }
        else
            return "Failed to achieve the goal!";
    }

    //------------------------------------------------------
    // getTorchSide
    //
    // PURPOSE:	get the side of the torch
    // PARAMETERS: None
    // Returns: the side of the torch
    //------------------------------------------------------
    public Side getTorchSide()
    {
        return torchSide;
    }

    //------------------------------------------------------
    // getTimeSpent
    //
    // PURPOSE:	get the time spent of the solution
    // PARAMETERS: None
    // Returns: the time spent
    //------------------------------------------------------
    public int getTimeSpent()
    {
        return timeSpent;
    }

    //------------------------------------------------------
    // getTimeConstraint
    //
    // PURPOSE:	get the given maximum time
    // PARAMETERS: None
    // Returns: the time constraint
    //------------------------------------------------------
    public int getTimeConstraint()
    {
        return timeConstraint;
    }
} //class
