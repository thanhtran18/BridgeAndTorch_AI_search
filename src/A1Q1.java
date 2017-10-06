import java.util.ArrayList;
import java.util.Scanner;

public class A1Q1
{

    public static void main(String[] args)
    {
	// write your code here
        System.out.println("Hello");
        System.out.println("PK");
        System.out.println("dfD");

        int maxTime = 0;
        int[] crossingTimes;
        ArrayList<Person> initialPeople = new ArrayList<>();

        System.out.println("Give me the input please: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] inputNumbers = input.split(" ");
        crossingTimes = new int[inputNumbers.length-1];

        maxTime = Integer.parseInt(inputNumbers[0]);
        for (int i = 0; i < inputNumbers.length-1; i++)
            crossingTimes[i] = Integer.parseInt(inputNumbers[i+1]);

        for (int i = 0; i < crossingTimes.length; i++)
            initialPeople.add(new Person(crossingTimes[i], Side.LEFT));

        AStartAlgorithm.processAStar(initialPeople, maxTime);
    }
}
