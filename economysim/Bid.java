package economysim;

//if a person wants to buy something they create a bid
public class Bid {
	protected String name;
	protected int price;
	protected String good;
	
	public Bid(String name, int price, String good)
	{
		this.name = name;
		this.price = price;
		this.good = good;
	}
	
	public String getBuyerName()
	{
		return name;
	}
	
	public int getPrice()
	{
		return price;
	}
	
	public String getGoodName()
	{
		return good;
	}
}
