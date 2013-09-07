package economysim;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import economysim.Offer;
import economysim.Bid;


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
	
	protected Map<String, Integer> averagePrice;
	
	public Person(String name)
	{
		this.name = name;
		this.money = 500;
		this.food = 5;
		this.wood = 5;
		this.tools = 0;
		this.metal = 5;
		
		//set to 10 for now
		averagePrice = new HashMap<String, Integer>();
		averagePrice.put("food", 10);
		averagePrice.put("wood", 10);
		averagePrice.put("tools", 10);
		averagePrice.put("metal", 10);
		averagePrice.put("ore", 10);
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
		System.out.print("Food: ");
		System.out.println(food);
	}
	public int generatePrice()
	{
		//meant to be a virtual function
		//generate the selling price of the commodity
		return 0;
	}
	public ArrayList<Offer> createOffer()
	{
		//create an offer on the market for their commodity
		//something that this person wants to sell
		return null;
	}
	public ArrayList<Bid> createBid()
	{
		ArrayList<Bid> ret = new ArrayList<Bid>();
		if(food < 5)
		{
			for(int i = 0; i < (5-food); ++i)
			{
				Bid newBid = new Bid(name, averagePrice.get("food"), "food");
				ret.add(newBid);
			}
		}
		return ret;
	}

	public boolean purchaseOffer(Offer offer)
	{
		//make sure person can actually purchase this
		if(money > offer.getPrice())
		{
			money -= offer.getPrice();
			if(offer.getGoodName() == "food")
			{
				food++;
			}
			else if(offer.getGoodName() == "tools")
			{
				tools++;
			}
			else if(offer.getGoodName() == "wood")
			{
				wood++;
			}
			else if(offer.getGoodName() == "metal")
			{
				metal++;
			}
			else if(offer.getGoodName() == "ore")
			{
				ore++;
			}
			return true;
		}
		return false;
	}
	public void offerPurchased(Offer offer)
	{
		//someone bought our shit
		money += offer.getPrice();
		if(offer.getGoodName() == "food")
		{
			food--;
		}
		else if(offer.getGoodName() == "tools")
		{
			tools--;
		}
		else if(offer.getGoodName() == "wood")
		{
			wood--;
		}
		else if(offer.getGoodName() == "metal")
		{
			metal--;
		}
		else if(offer.getGoodName() == "ore")
		{
			ore--;
		}
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
