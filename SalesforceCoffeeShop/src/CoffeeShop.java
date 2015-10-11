public class CoffeeShop 
{
	private String shopName;
	
	private Coordinate coordinates;
	
	public CoffeeShop(String shopName, double y, double x)
	{
		setName(shopName);
		coordinates = new Coordinate(y,x);
	}
	
	public String getName()
	{
		return shopName;
	}
	
	private void setName(String shopName)
	{
		this.shopName = shopName;
	}
	
	public Coordinate getCoordinates()
	{
		return coordinates;
	}

}
