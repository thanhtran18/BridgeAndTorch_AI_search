//-----------------------------------------
// CLASS: ProblemOperation
//
// Author: Cong Thanh Tran
//
// REMARKS: A class that contains every necessary information for a an operation
//
//-----------------------------------------

import java.util.ArrayList;

public class ProblemOperation
{
    private ArrayList<Person> people; //list of all people in the problem
    private int movingTime;           //moving time

    //------------------------------------------------------
    // ProblemState Constructor
    //
    // PURPOSE:	Initializes this object
    // PARAMETERS:
    //		ArrayList : list of people
    //      int       : moving time
    // Returns: None
    //------------------------------------------------------
    public ProblemOperation( ArrayList<Person> people, int movingTime)
    {
        this.people = people;
        this.movingTime = movingTime;
    }

    //------------------------------------------------------
    // getPeople
    //
    // PURPOSE:	get the all the people
    // PARAMETERS: none
    // Returns:
    //		ArrayList: list of all the people
    //------------------------------------------------------
    public ArrayList<Person> getPeople()
    {
        return people;
    }

    //------------------------------------------------------
    // getMovingTime
    //
    // PURPOSE:	get the moving time of this operation
    // PARAMETERS: none
    // Returns:
    //		int : moving time
    //------------------------------------------------------
    public int getMovingTime()
    {
        return movingTime;
    }
}
