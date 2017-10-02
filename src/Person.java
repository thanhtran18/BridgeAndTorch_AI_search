public class Person
{
    private int crossingTime;
    private Side side;

    public Person(int speed, Side side)
    {
        this.crossingTime = speed;
        this.side = side;
    }

    @Override
    public Person clone()
    {
        int currCrossingTime = crossingTime;
        Side currSide = side;
        return new Person(crossingTime, currSide);
    }

    public void move()
    {
        if (side == Side.LEFT)
        {
            side = Side.RIGHT;
            ProblemState.peopleOnRightSide.add(this);
            ProblemState.peopleOnLeftSide.remove(this);
        }
        else
        {
            side = Side.LEFT;
            ProblemState.peopleOnLeftSide.add(this);
            ProblemState.peopleOnRightSide.remove(this);
        }
    }
}
