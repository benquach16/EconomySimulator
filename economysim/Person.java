package economysim;
import economysim.Offer;
import economysim.Bid;
import economysim.Market;

//base class
//a base person will continue to buy food until he runs out of money and dies :(
public class Person {
	
	protected int food;
	protected int money;
	protected int wood;
	protected int tools;
	protected int metal;
	protected int ore;
	
	protected String name;
	
	public Person(String name)
	{
		this.name = name;
		this.money = 100;
		this.food = 5;
		this.wood = 5;
		this.tools = 1;
		this.metal = 5;
	}
	public void run()
	{
		//virtual function
		food--;
	}
	public void print()
	{
		//print out the resources that this person has
		System.out.print(name);
		System.out.println(getProfession());
		System.out.print("Money: ");
		System.out.println(money);
	}
	public int generatePrice()
	{
		//meant to be a virtual function
		//generate the selling price of the commodity
		return 0;
	}
	public Offer createOffer()
	{
		//create an offer on the market for their commodity
		//something that this person wants to sell
		Offer ret = new Offer(name, 5, "food");
		return ret;
	}
	public boolean wantsToSell()
	{
		//base people dont sell
		return false;
	}
	public Bid createBid()
	{
		Bid ret = new Bid(name, 5, "food");
		return ret;
	}
	public boolean wantsToBuy()
	{
		if(food < 5)
		{
			return true;
		}
		return false;
	}
	
	//accessors go down here
	public String getProfession()
	{
		return "Person";
	}
	public String getName()
	{
		return name;
	}
}
