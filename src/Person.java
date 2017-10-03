public class Person implements Comparable<Person>
{
    private int crossingTime;
    private Side side;

    public Person(int crossingTime, Side side)
    {
        this.crossingTime = crossingTime;
        this.side = side;
    }

    @Override
    public Person clone()
    {
        int currCrossingTime = crossingTime;
        Side currSide = side;
        return new Person(currCrossingTime, currSide);
    }

    //SHOULD IT BE HERE?
    public void move()
    {
        if (side == Side.LEFT)
        {
            side = Side.RIGHT;
            ProblemState.rightSide.add(this);
            ProblemState.leftSide.remove(this);
        }
        else
        {
            side = Side.LEFT;
            ProblemState.leftSide.add(this);
            ProblemState.rightSide.remove(this);
        }
    }

    public int getCrossingTime()
    {
        return crossingTime;
    }

    public Side getSide()
    {
        return side;
    }

    @Override
    public int compareTo(Person other)
    {
        return Integer.compare(crossingTime, other.crossingTime);
    }
}
