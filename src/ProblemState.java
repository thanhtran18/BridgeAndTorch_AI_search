import java.util.ArrayList;
import java.util.Collections;

public class ProblemState
{
    private int timeSpent;
    private int timeConstraint;
    //MAYBE WE CAN JUST COUNT THE NUMBER OF PEOPLE ON EACH SIDE INSTEAD OF HAVE A LIST OF PEOPLE!!!???
    private ArrayList<Person> rightSide;
    private ArrayList<Person> leftSide; //maybe not?
    private Side torchSide;
    private int stateCost; //stateValue
    private int stateLevel; //level
    private ProblemState parentState;

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

    @Override
    public ProblemState clone()
    {
        int currTimeSpent = timeSpent;
        int currTimeConstraint = timeConstraint;
        ArrayList<Person> currPeopleOnRightSide = new ArrayList<Person>();
        for (Person currPerson : rightSide)
        {
            Person clonedPerson = currPerson.clone();
            currPeopleOnRightSide.add(clonedPerson);
        }
        ArrayList<Person> currPeopleOnLeftSide = new ArrayList<Person>();
        for (Person currPerson : leftSide)
        {
            Person clonedPerson = currPerson.clone();
            currPeopleOnLeftSide.add(clonedPerson);
        }
        Side currTorchSide = torchSide;
        int currStateCost = stateCost;
        int currStateLevel = stateLevel;
        ProblemState currParentState = parentState;

        return new ProblemState(currTimeSpent, currTimeConstraint, currPeopleOnRightSide, currPeopleOnLeftSide,
                currTorchSide, currStateLevel, parentState);
    } //clone


    public boolean areWeDone()
    {
        boolean result = false;
        if (leftSide.size() == 0)
            result = true;
        return result;
    }

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

    //get the cost f = g + h
    public int calculateCost()
    {
        int heuristic = this.calculateHeuristic();
        stateCost = heuristic + this.stateLevel;
        return stateCost;
    }

    public ProblemState getNewState(ProblemOperation operation, ProblemState prevState) //applyMove
    {
        ProblemOperation thisOperation = operation;

        if (torchSide.equals(Side.LEFT))
        {
            Side newSide = Side.RIGHT;
            //int newCost = calculateCost();
            int level = stateLevel + thisOperation.getMovingTime();
            ProblemState newState = new ProblemState(timeSpent, timeConstraint, rightSide, leftSide, newSide, level, prevState);
            newState.leftSide.removeAll(thisOperation.getPeople());
            //********PROBLEM HERE AT THE ABOVE LINE!!!! CHANGE IN NEW STATE LEADS TO CHANGE IN THIS
            newState.rightSide.addAll(thisOperation.getPeople());
            newState.timeSpent += thisOperation.getMovingTime();
            newState.calculateCost();
            return newState;
        }
        else
        {
            Side newSide = Side.LEFT;
            //int newCost = calculateCost();
            int level = stateLevel + thisOperation.getMovingTime();
            ProblemState newState = new ProblemState(timeSpent, timeConstraint, rightSide, leftSide, newSide, level, prevState);
            newState.rightSide.removeAll(thisOperation.getPeople());
            newState.leftSide.addAll(thisOperation.getPeople());
            newState.timeSpent += thisOperation.getMovingTime();
            newState.calculateCost();
            return newState;
        }

    }

    public void setParentState(ProblemState parentState)
    {
        this.parentState = parentState;
    }

    public ArrayList<ProblemState> generateNextStates() //children states
    {
        ArrayList<ProblemState> children = new ArrayList<>();
        ArrayList<ProblemOperation> allPossibleOps = createAllPossibleOperations();
        //generate all possible operations that could be the children

        for (ProblemOperation currOperation : allPossibleOps)
        {
            //ProblemState nextState = getNewState(currOperation, this);
            //SECOND OPERATION HAS DIFFERENT PREV STATE, MAYBE CONSTRUCTOR CHANGES THE ORIGINAL STATE
            children.add(getNewState(currOperation, this));
        }
        return children;
    } //generateNextStates

    public ArrayList<ProblemOperation> createAllPossibleOperations()
    {
        ArrayList<ProblemOperation> allOperations = new ArrayList<>();
        if (torchSide == Side.LEFT)
            allOperations = createAllLeftToRightOperations();
        else
            allOperations = createAllRightToLeftOperations();
        return allOperations;
    }

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
    } //createAllLeftToRigthOperations

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

    public int getCrossingTimeOfTwo(Person p1, Person p2)
    {
        return (p1.getCrossingTime() > p2.getCrossingTime() ? p1.getCrossingTime():p2.getCrossingTime());
    }

    public void setStateCost(int stateCost)
    {
        this.stateCost = stateCost;
    }

    public ProblemState getParentState()
    {
        return parentState;
    }

    @Override
    public String toString()
    {
        if (timeConstraint > 0)
        {
            StringBuilder result = new StringBuilder();
            result.append("Left side: ");
            for (Person currPerson : leftSide)
                result.append(currPerson.getCrossingTime() + " ");
            result.append("\nRight side: ");
            for (Person currPerson : rightSide)
                result.append(currPerson.getCrossingTime() + " ");
            result.append("\nTorch side: " + getTorchSide());
            result.append("\nTime spent: " + timeSpent + "\n\n");
            return result.toString();
        }
        else
            return "Failed to achieve the goal!";
    }

    public Side getTorchSide()
    {
        return torchSide;
    }

    public int getTimeSpent()
    {
        return timeSpent;
    }
} //class
