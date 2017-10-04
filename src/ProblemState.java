import java.util.ArrayList;
import java.util.Collections;

public class ProblemState
{
    private int timeSpent;
    private int timeRemaining;
    //MAYBE WE CAN JUST COUNT THE NUMBER OF PEOPLE ON EACH SIDE INSTEAD OF HAVE A LIST OF PEOPLE!!!???
    private ArrayList<Person> rightSide;
    private ArrayList<Person> leftSide; //maybe not?
    private Side torchSide;
    private int stateCost; //stateValue
    private int stateLevel; //level
    private ProblemState parentState;

    public ProblemState(int timeSpent, int timeRemaining, ArrayList<Person> peopleOnRightSide,
                        ArrayList<Person> peopleOnLeftSide, Side torchSide, int stateCost, int stateLevel, ProblemState parentState)
    {
        this.timeSpent = timeSpent;
        this.timeRemaining = timeRemaining;
        this.rightSide = peopleOnRightSide;
        this.leftSide = peopleOnLeftSide;
        this.torchSide = torchSide;
        this.stateCost = stateCost;
        this.stateLevel = stateLevel;
        this.parentState = parentState;
    } //constructor

    @Override
    public ProblemState clone()
    {
        int currTimeSpent = timeSpent;
        int currTimeRemaining = timeRemaining;
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

        return new ProblemState(currTimeSpent, currTimeRemaining, currPeopleOnRightSide, currPeopleOnLeftSide,
                currTorchSide, currStateCost, currStateLevel, parentState);
    } //clone


    public boolean areWeDone()
    {
        boolean result = false;
        if (leftSide.size() == 0)
            result = true;
        return result;
    }

    public int calculateHeuristic(ProblemState state)
    {
        int number = state.leftSide.size()/2;
        int count = state.leftSide.size() - 1;
        int cost = 0;
        Collections.sort(leftSide);

        while (number > 0)
        {
            cost += state.leftSide.get(count).getCrossingTime();
            count--;
            number--;
        }
        return cost;
    }

    public int calculateCost(ProblemState state)
    {
        int heuristic = calculateHeuristic(state);
        stateCost = heuristic + state.stateLevel;
        return stateCost;
    }

    public ProblemState generateMove(ProblemOperation operation, ProblemState prevState) //applyMove
    {
        ProblemOperation thisOperation = operation;
        int level;
        Side newSide;
        if (torchSide.equals(Side.LEFT))
            newSide = Side.RIGHT;
        else
            newSide = Side.LEFT;

        int newCost = calculateCost(this);
        level = stateLevel + thisOperation.getMovingTime();
        ProblemState newState = new ProblemState(timeSpent, timeRemaining, rightSide, leftSide, newSide, newCost, level, prevState);
        newState.leftSide.removeAll(thisOperation.getPeople());
        newState.rightSide.addAll(thisOperation.getPeople());
        newState.timeSpent += thisOperation.getMovingTime();
        newState.stateCost = calculateCost(newState);
        return newState;
    }

    public void setParentState(ProblemState parentState)
    {
        this.parentState = parentState;
    }

    public ArrayList<Person> generateNextState() //children states
    {
        ArrayList<ProblemState> children = new ArrayList<>();
        //generate ALL POSSIBLE MOVES
    }

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
} //class
