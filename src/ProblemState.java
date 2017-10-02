import java.util.ArrayList;

public class ProblemState
{
    private int timeSpent;
    private int timeRemaining;
    //MAYBE WE CAN JUST COUNT THE NUMBER OF PEOPLE ON EACH SIDE INSTEAD OF HAVE A LIST OF PEOPLE!!!???
    public static ArrayList<Person> peopleOnRightSide;
    public static ArrayList<Person> peopleOnLeftSide; //maybe not?
    //private static boolean torchSide; //maybe not

    public ProblemState(int timeSpent, int timeRemaining, ArrayList<Person> peopleOnRightSide, ArrayList<Person> peopleOnStartSide)
    {
        this.timeSpent = timeSpent;
        this.timeRemaining = timeRemaining;
        this.peopleOnRightSide = peopleOnRightSide;
        this.peopleOnLeftSide = peopleOnStartSide;
    } //constructor

    @Override
    public ProblemState clone()
    {
        int currTimeSpent = timeSpent;
        int currTimeRemaining = timeRemaining;
        ArrayList<Person> currPeopleOnRightSide = new ArrayList<Person>();
        for (Person currPerson : peopleOnRightSide)
        {
            Person clonedPerson = currPerson.clone();
            currPeopleOnRightSide.add(clonedPerson);
        }
        ArrayList<Person> currPeopleOnLeftSide = new ArrayList<Person>();
        for (Person currPerson : peopleOnLeftSide)
        {
            Person clonedPerson = currPerson.clone();
            currPeopleOnLeftSide.add(clonedPerson);
        }

        return new ProblemState(currTimeSpent, currTimeRemaining, currPeopleOnRightSide, currPeopleOnLeftSide);
    } //clone

    //returns 1 if this is better newState
    //should we do number of units of time instead of just counting number of people?
    public int compare(ProblemState newState)
    {
        int result;
        if (peopleOnRightSide.size() > newState.peopleOnRightSide.size())
            result = 1;
        else if (peopleOnRightSide.size() < newState.peopleOnRightSide.size())
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

    public boolean areWeDone()
    {
        boolean result = false;
        if (peopleOnLeftSide.size() == 0)
            result = true;
        return result;
    }
} //class
