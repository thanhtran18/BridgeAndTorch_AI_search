//-----------------------------------------
// CLASS: Person
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that contains every necessary information for a people character in the problem
//
//-----------------------------------------

public class Person implements Comparable<Person>
{
    private int crossingTime;   //crossing time of the person
    private Side side;          //current side of the person

    //------------------------------------------------------
    // Person Constructor
    //
    // PURPOSE:	Initializes this object
    // PARAMETERS:
    //		int : crossing time
    //      Side: current side
    // Returns: None
    //------------------------------------------------------
    public Person(int crossingTime, Side side)
    {
        this.crossingTime = crossingTime;
        this.side = side;
    }

    //------------------------------------------------------
    // clone (override)
    //
    // PURPOSE:	deep copy this object
    // PARAMETERS: None
    // Returns: the new version of this object
    //------------------------------------------------------
    @Override
    public Person clone()
    {
        int currCrossingTime = crossingTime;
        Side currSide = side;
        return new Person(currCrossingTime, currSide);
    }

    //------------------------------------------------------
    // getCrossingTime
    //
    // PURPOSE:	get the crossing time of this person
    // PARAMETERS: none
    // Returns:
    //		double: the crossing time
    //------------------------------------------------------
    public int getCrossingTime()
    {
        return crossingTime;
    }

    //------------------------------------------------------
    // compareTo (override)
    //
    // PURPOSE:	compare two Person objects by crossing time.
    // PARAMETERS:
    //      Person : the other person who will be compared with
    // Returns: the difference
    //------------------------------------------------------
    @Override
    public int compareTo(Person other)
    {
        return Integer.compare(crossingTime, other.crossingTime);
    }
}
