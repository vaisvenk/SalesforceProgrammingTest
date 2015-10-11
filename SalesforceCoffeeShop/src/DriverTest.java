/******************************************************************************************
 * I believe we could write more test cases to the code
 * However, I stop with just 5 of them here. To have the test cases running
 * I had to change the helper methods from private to public. So, make sure it is changed
 * before running the test cases.
 * 
 * Few more test cases that I could immediately think of are:
 * 
 * 1. File not found
 * 2. File is empty
 * 3. The input file has one or more values missing
 * 4. One or more inputs is empty in the file
 * 5. One or more command line arguments passed are missing
 * 6. The range of the coordinates could be tested(say for a signed double, its range)
 * 7. The value mentioned for coordinates are valid(Only double and not alphanumeric)
 * Most of these are taken care of in the the code. However, more robust error handling
 * could be done.
 * 
 * NOTE: Before running the file, please change the access specifier of computeDistance()
 * from private to public.Also, uncomment the multi-line comments placed for test1 and test2.
 ******************************************************************************************/


import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import org.junit.Test;


public class DriverTest {

	Output o1 = new Output("Starbucks Seattle",0.0861441234211632);
	Output o2 = new Output("Starbucks Seattle1",100);
	Output o3 = new Output("Starbucks Seattle2",0.6734);
	Output o4 = new Output("Starbucks Seattle3",0.8467);
	
	Comparator<Output> revComp = Collections.reverseOrder();
	PriorityQueue<Output> maxHeap = new PriorityQueue<Output>(3,revComp);
	
	String inputFile = "/Users/vaisvenk/Documents/MyCode/SalesforceCoffeeShop/Input.csv";
	Driver d1 = new Driver();
	
	//Change the method in the Driver class to public for this to work
	/*@Test
	public void test1() {
		
		double distance = d1.computeDistance(0, 0, 0, 0);
		assertEquals(0,distance,0.0);
	}*/
	
	//Change the method in the Driver class to public for this to work
	/*@Test
	public void test2()
	{
		double distance = d1.computeDistance(-122.4, 47.6, -122.3368,47.5869);
		//would fail if we check for exact 
		//match as the output of this method is not rounded off to 4 decimal places yet
		assertEquals(0.0645,distance,0.00005);
	}*/
	
	@Test
	public void test3()
	{
		if(inputFile == null)
		{	
			assertNotNull("ERROR: Please check your input.",inputFile);
		}
	}
	
	@Test
	public void test4()
	{
		maxHeap.offer(o1);
		maxHeap.offer(o2);
		maxHeap.offer(o3);
		
		assertEquals(o2.getDistance(),maxHeap.peek().getDistance(),0.000);
		
	}
}
