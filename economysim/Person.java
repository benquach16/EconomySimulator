package economysim;


//base class
//a base person will continue to buy food until he runs out of money and dies :(
public class Person {
	
	protected int food;
	protected int money;
	protected int wood;
	protected int tools;
	protected int metal;
	public Person()
	{
		
	}
	public void run()
	{
		//virtual function
		if(money > 0)
		{
			//buy food
		}
	}
	public void print()
	{
		//print out the resources that this person has
	}
	public int generatePrice()
	{
		//meant to be a virtual function
		//generate the selling price of the commodity
		return 0;
	}
	public void createOffer()
	{
		//create an offer on the market for their commodity
		//must be a need, eg farmers need wood and tools, etc etc
	}
}
