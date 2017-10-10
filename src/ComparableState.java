//-----------------------------------------
// CLASS: ComparableState
//
// Author: Cong Thanh Tran
//
// REMARKS: implemented in order to work with the queue
//
//-----------------------------------------

import java.util.Comparator;

public class ComparableState implements Comparator<ProblemState>
{
    @Override
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
