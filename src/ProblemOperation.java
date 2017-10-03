import java.util.ArrayList;

public class ProblemOperation
{
    private ArrayList<Person> people;
    private int movingTime;

    public ProblemOperation( ArrayList<Person> people, int movingTime)
    {
        this.people = people;
        this.movingTime = movingTime;
    }

    public ArrayList<Person> getPeople()
    {
        return people;
    }

    public int getCrossingTimeOfTwo(Person p1, Person p2)
    {
        return (p1.getCrossingTime() > p2.getCrossingTime() ? p1.getCrossingTime():p2.getCrossingTime());
    }

    public int getMovingTime()
    {
        return movingTime;
    }
}
