package economysim;
import economysim.Offer;

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
		this.food = 20;
		this.wood = 20;
		this.tools = 1;
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
		//must be a need, eg farmers need wood and tools, etc etc
		Offer ret = new Offer();
		return ret;
	}
	public String getProfession()
	{
		return "Person";
	}
}
