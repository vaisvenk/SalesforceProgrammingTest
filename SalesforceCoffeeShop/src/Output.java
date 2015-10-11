
/*****************************************************************
 * To Hold the data to be displayed as part of the output
 * ShopName
 * Distance
 * @author vaisvenk
 ***********************************************************/
public class Output implements Comparable<Output>
{
	private String name;
	private double distance;
	
	public Output(String name, double distance)
	{
		setName(name);
		setDistance(distance);
	}
	
	private void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	private void setDistance(double distance)
	{
		this.distance = distance;
	}
	
	public double getDistance()
	{
		return distance;
	}
	
	public int compareTo(Output o)
	{
		return (Double.compare(this.distance, o.getDistance()));
	}
}
