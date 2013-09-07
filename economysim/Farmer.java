package economysim;
import java.util.ArrayList;
import economysim.Person;


public class Farmer extends Person {
	protected int foodLimit;
	protected int woodLimit;
	protected int foodUpperBound;
	public Farmer(String name)
	{
		//constructor
		//invoke person constructor
		super(name);
		foodLimit = 5;
		woodLimit = 5;
		foodUpperBound = 20;
	}
	public void run()
	{
		super.run();
		//every tick make food
		if(tools > 0 && wood > 0 && food > 0)
		{
			//we can do stuff
			wood --;
			food += 5;
		}
		else if(wood > 0 && food > 0)
		{
			//has no tools
			wood --;
			food += 3;
		}
		else if(food < 1)
		{
			//no food?
			//better buy some on the market then
			//if no money then we kill this guy
			
		}
	}
	public void print()
	{
		//lets print the resources the farmer has right now
		super.print();
		System.out.print("Tools: ");
		System.out.println(tools);
		System.out.print("Wood: ");
		System.out.println(wood);
		System.out.println("");
		
	}
	public ArrayList<Offer> createOffer()
	{
		ArrayList<Offer> ret = new ArrayList<Offer>();
		//sell everything above limit which is hardcoded to 5 right now
		if(food > foodLimit)
		{
			for(int i = 0; i < food - foodLimit; ++i)
			{
				Offer newOffer = new Offer(name, 10, "food");
				ret.add(newOffer);
			}
			food = foodLimit;
		}
		return ret;
	}
	public ArrayList<Bid> createBid()
	{
		//farmer can just make food if they have wood so try to find wood first
		ArrayList<Bid> ret = new ArrayList<Bid>();
		if(tools < 1)
		{
			Bid newBid = new Bid(name, 10, "tools");
			ret.add(newBid);
		}
		if(wood < woodLimit)
		{
			for(int i = 0; i < woodLimit - wood; ++i)
			{
				//need wood
				Bid newBid = new Bid(name, 10, "wood");
				ret.add(newBid);
			}
		}
		if(food < 1)
		{
			//WE'RE DESPERATE!!!
			Bid newBid = new Bid(name, 10, "food");
			ret.add(newBid);
		}
		return ret;
	}
	public String getProfession()
	{
		return "Farmer";
	}

}
