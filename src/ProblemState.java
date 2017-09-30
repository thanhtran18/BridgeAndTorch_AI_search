import java.util.ArrayList;

public class ProblemState
{
    private int timeSpent;
    private int timeRemaining;
    private ArrayList<Integer> peopleOnEndSide;
    private ArrayList<Integer> peopleOnStartSide; //maybe not?
    //private static boolean torchSide; //maybe not

    public ProblemState(int timeSpent, int timeRemaining, ArrayList<Integer> peopleOnEndSide, ArrayList<Integer> peopleOnStartSide)
    {
        this.timeSpent = timeSpent;
        this.timeRemaining = timeRemaining;
        this.peopleOnEndSide = peopleOnEndSide;
        this.peopleOnStartSide = peopleOnStartSide;
    } //constructor

    @Override
    public ProblemState clone()
    {
        int currTimeSpent = timeSpent;
        int currTimeRemaining = timeRemaining;
        ArrayList<Integer> currPeopleOnEndSide = new ArrayList<Integer>();
        for (int currPerson : peopleOnEndSide)
            currPeopleOnEndSide.add(currPerson);
        ArrayList<Integer> currPeopleOnStartSide = new ArrayList<Integer>();
        for (int currPerson : peopleOnStartSide)
            currPeopleOnStartSide.add(currPerson);

        return new ProblemState(currTimeSpent, currTimeRemaining, currPeopleOnEndSide, currPeopleOnStartSide);
    } //clone

    //returns 1 if this is better newState
    //should we do number of units of time instead of just counting number of people?
    public int compare(ProblemState newState)
    {
        int result;
        if (peopleOnEndSide.size() > newState.peopleOnEndSide.size())
            result = 1;
        else if (peopleOnEndSide.size() < newState.peopleOnEndSide.size())
            result = -1;
        else
        {
            if (timeSpent < newState.timeSpent)
                result = 1;
            else if (timeSpent > newState.timeSpent)
                result = -1;
            else
                result = 0;
        }
        return result;
    } //compare


} //class
