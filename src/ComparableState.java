import java.util.Comparator;

public class ComparableState implements Comparator<ProblemState>
{
    public int compare(ProblemState o1, ProblemState o2)
    {
        if(o1.calculateCost() > o2.calculateCost())
            return 1;
        else if(o1.calculateCost() < o2.calculateCost())
            return -1;
        else
            return 0;
    }
}
