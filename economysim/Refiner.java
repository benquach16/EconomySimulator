package economysim;

import java.util.ArrayList;

public class Refiner extends Person {
	protected int oreLimit;
	public Refiner(String name)
	{
		super(name);
		oreLimit = 5;
	}
	public void run()
	{
		super.run();
		double totalPrice = (averagePrice.get("ore") * profitFactor);
		averagePrice.put("metal", (int) totalPrice);
		if(food > 0 && tools > 0)
		{
			//convert ore into metal
			metal += ore;
			ore = 0;
			//break tools
			if((int)(Math.random() * 10) < 1)
			{
				tools--;
			}
		}
		else if (food > 0)
		{
			//no tools
			if(ore > 2)
			{
				ore-=2;
				metal +=2;
			}
			else
			{
				metal += ore;
				ore = 0;
			}
		}
	}
	public void print()
	{
		super.print();
		System.out.print("Tools: ");
		System.out.println(tools);
		System.out.print("Ore: ");
		System.out.println(ore);
		System.out.print("Metal: ");
		System.out.println(metal);
		System.out.println("");
	}
	public ArrayList<Offer> createOffer()
	{
		ArrayList<Offer> ret = new ArrayList<Offer>();
		for(int i = 0; i < this.metal; ++i)
		{
			Offer newOffer = new Offer(name, averagePrice.get("metal"), "metal");
			ret.add(newOffer);
		}
		return ret;
	}
	public ArrayList<Bid> createBid()
	{
		//buy ore and food
		ArrayList<Bid> ret = new ArrayList<Bid>();
		if(tools < 1)
		{
			Bid newBid = new Bid(name, averagePrice.get("tools"), "tools");
			ret.add(newBid);
		}
		if(ore < oreLimit)
		{
			for(int i = 0; i < oreLimit - ore; ++i)
			{
				Bid newBid = new Bid(name, averagePrice.get("ore"), "ore");
				ret.add(newBid);
			}
		}
		ret.addAll(super.createBid());
		return ret;
	}
	public String getProfession()
	{
		return "Refiner";
	}
}
