package economysim;
import java.util.ArrayList;

import economysim.Person;

public class Woodcutter extends Person{
	public Woodcutter(String name)
	{
		super(name);
		
	}
	public void run()
	{
		super.run();
		if(food > 0 && tools > 0)
		{
			//do stuff
			wood+=2;
		}
		else if (food > 0)
		{
			wood++;
		}
	}
	public void print()
	{
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
		for(int i = 0; i < this.wood; ++i)
		{
			Offer newOffer = new Offer(name, 10, "wood");
			ret.add(newOffer);
		}
		return ret;
	}
	public ArrayList<Bid> createBid()
	{
		//buy ore and food
		ArrayList<Bid> ret = new ArrayList<Bid>();
		ret.addAll(super.createBid());
		if(tools < 1)
		{
			Bid newBid = new Bid(name, 10, "tools");
			ret.add(newBid);
		}
		return ret;
	}
	public String getProfession()
	{
		return "Woodcutter";
	}
}
