package economysim;
import java.util.ArrayList;

import economysim.Person;

public class Blacksmith extends Person {
	protected int metalLimit;
	protected int toolsLimit;
	public Blacksmith(String name)
	{
		super(name);
		metalLimit = 5;
		toolsLimit = 5;
	}
	public void run()
	{
		super.run();
		double totalPrice = (averagePrice.get("metal") * profitFactor);
		averagePrice.put("tools", (int) totalPrice);
		if(food > 0)
		{
			tools+=metal;
			metal = 0;
		}
	}
	public void print()
	{
		super.print();

		System.out.print("Tools: ");
		System.out.println(tools);
		System.out.print("Metal: ");
		System.out.println(metal);
		System.out.println("");
	}
	public ArrayList<Offer> createOffer()
	{
		ArrayList<Offer> ret = new ArrayList<Offer>();
		for(int i = 0; i < this.tools; ++i)
		{
			Offer newOffer = new Offer(name, averagePrice.get("tools"), "tools");
			ret.add(newOffer);
		}
		return ret;
	}
	public ArrayList<Bid> createBid()
	{
		//buy ore and food
		ArrayList<Bid> ret = new ArrayList<Bid>();
		ret.addAll(super.createBid());
		//above our limit is surplus we don't need for now
		if(metal < metalLimit && tools < 10)
		{
			for(int i = 0; i < metalLimit - metal; ++i)
			{
				Bid newBid = new Bid(name, averagePrice.get("tools"), "metal");
				ret.add(newBid);
			}
		}


		return ret;
	}
	public String getProfession()
	{
		return "Blacksmith";
	}
	
	
}
