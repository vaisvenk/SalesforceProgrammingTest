/**************************************************************************
 * @author vaisvenk
 * 
 * This class has the following responsibilities:
 * 	1. Parse the command line input to get the coordinates of user location
 * 	2. Read the input file containing the details(Shop name and coordinates) 
 * 	   of coffee shops
 * 	3. Compute the distance of each coffee shop from the user's location
 * 	4. If the distance is less than the max of the existing list of 3 closest 
 * 	   coffee shops then remove the max node from the heap and insert the new
 * 	   shop name and its distance(Object of class Output)
 * 	5. Print the objects in the heap in increasing order of its distance
 * 	   from the user. Round off the distance to 4 decimal places.
 * 
 * Time Complexity: O(NlogK)
 * 
 * 					where N: Number of coffee shops in the input file
 * 						  K: Number of closest coffee shops
 * 							 In our case: 3
 * Space Complexity: O(K) 
 * 					where K: Number of closest coffee shops required
 * 
 * Also, the usage of heaps give us the flexibility in the number of closest points
 * that we need .In case we decide to display 10 closest coffee
 * shops, all we need to do is create a max-heap of size 10. In the sense, the size 
 * is customizable. For now to meet our immediate requirement, I have a reverse-ordered
 * priority queue of size 10.
 * 
 * To make the code modular, I have split the computations to be performed into
 * different methods. So, in case we decide to compute the distance using a different
 * formula, we could just go ahead and change the logic in computeDistance().
 * 
 * What could have been done better: There are 2 source code files which I
 * haven't used, namely, CoffeeShop.java and Coordinate.java.
 * 
 * On looking through the problem, I first thing I got to my mind was to 
 * create a class for CoffeeShop which will hold all the attributes of it.
 * And a Coordinate class which will hold the coordinates. However, upon implementing 
 * the logic I realized I need to compute the distance between the user and each of the
 * coffee shop and this by any means would not be an attribute of a coffee shop
 * as it is relative to the user. Hence, in order to complete code within time, I created
 * a new class Output.java which will hold all the information that we need as part of the 
 * output to be displayed: Shop Name and its distance from the user. 
 * 
 * However, the CoffeeShop and Coordinate classes could still be used as a part of the Output class.
 * This will make the code much more extensible. In the sense, in future if we have a new 
 * requirement where we need to display more information say something like a shop address or
 * rating, we could just pull up the data from the CoffeeShop class into the Output
 * and display them. Likewise, the Coordinate class is also generic. It can be inherited or
 * used as it is to refer to coordinates of different objects like user, coffeeShop or may be a
 * new requirement say nearest caltrain station to the shop.
 * 
 * Given an opportunity I would like to brainstorm more about the design decisions.
 * 
 ***********************************************************************************/

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;


public class Driver 
{
	private static PriorityQueue<Output> maxHeap = null;
	
	public static void main(String[] args)
	{
		/*
		 * Creating a max-heap which will at any point store 
		 * 3 closest coffee shops.
		 */
		Comparator<Output> revComp = Collections.reverseOrder();
		maxHeap = new PriorityQueue<Output>(3,revComp);
		
		/*
		 * Parse the command line inputs
		 * Format: Y-Coordinate X-Coordinate Input FileName
		 */
		if(args.length != 3)
		{
			System.out.println("ERROR: Please check your input");
			System.exit(0);
		}	
		double userY = Double.parseDouble(args[0]);
		double userX = Double.parseDouble(args[1]);
		String inputFile = args[2];
		
		processInputFile(inputFile, userX, userY);
		
		writeOutput();
	}
	
	/*
	 * This method is used to process the input csv file.
	 * Split the input file to retrieve the shop name and its respective x and y coordinates.
	 * Create an object of type Output such that it holds the shopName and its corresponding
	 * distance from the user's location.
	 */
	public static void processInputFile(String inputFile, double userX, double userY)
	{	
		BufferedReader br = null;
		String line = null;
		
		try
		{
			br = new BufferedReader(new FileReader(inputFile));
			while((line = br.readLine()) != null)
			{
				String[] data = line.split(",");
				if(data.length != 3)
				{
					System.out.println("ERROR: Please check the input");
					System.exit(0);
				}
				String name = data[0];
				double y = Double.parseDouble(data[1]);
				double x = Double.parseDouble(data[2]);
				double distance = computeDistance(userX, userY, x, y);
				
				Output output = new Output(name, distance);
				
				findClosestPoints(output, distance);
			}
		}
		catch(Exception e) 
		{ 
			System.out.println("ERROR: Please check your input.");
			System.exit(0);
		}
		
	}
	
	/*
	 * Compute and return the distance of each shop from the user's current location.
	 * userX, userY: User's location
	 * x, y: Location of the shop
	 */
	private static double computeDistance(double userX, double userY, double x, double y)
	{
		double distance = Math.pow((userX - x), 2) + Math.pow((userY - y), 2);
		distance = Math.sqrt(distance);
		
		return distance;
	}
	
	/*
	 * This method holds the logic to find the 3 closest coffee shops to the user.
	 * This algorithm makes use of a max heap such that if the distance of a coffee shop
	 * from a user is lesser than an already existing value in the max heap, it removes the 
	 * max element from the heap, inserts the new distance and heap re-adjusts itself
	 * with the new max node at the top.
	 * Thus, at any point in time, it holds the 3 closest coffee shops to the user.
	 * Time complexity of this function: logK where K = 3
	 */
	private static void findClosestPoints(Output output, double distance)
	{
		if(maxHeap.size() < 3)
		{
			maxHeap.offer(output);
		}
		else
		{
			Output maxVal = (Output)maxHeap.peek();
			
			if(maxVal.getDistance() > distance)
			{
				maxHeap.poll();
				maxHeap.offer(output);
			}				
		}
	}
	
	/*
	 * This method prints the closest coffee shops in increasing order 
	 * of its distance from the user. The max heap holds the 3 closest coffee shops
	 * in decreasing order of its distance. So, we covert it to an array and sort it.
	 * The distance is also rounded off to 4 decimal places.
	 * 
	 * Time complexity of this method: O(KlogK) + O(K) = KO(logK +1) = O(klogK)
	 * 								   where K = 3
	 */
	public static void writeOutput()
	{
		Object[] outputs = maxHeap.toArray();// 
		Arrays.sort(outputs);
		
		int i = 0;
		DecimalFormat df = new DecimalFormat("#.####");
		for(i=0; i<outputs.length; i++)
		{
			Output o = (Output)outputs[i];
			System.out.println(o.getName() +","+ df.format(o.getDistance()));
		}
	}
	
}
