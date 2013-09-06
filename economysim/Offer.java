package economysim;


//if a person wants to sell something they create an offer
public class Offer {
	protected String name;
	protected int price;
	protected String good;
	
	public Offer(String name, int price, String good)
	{
		this.name = name;
		this.price = price;
		this.good = good;
	}
	
	public String getSellerName()
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
