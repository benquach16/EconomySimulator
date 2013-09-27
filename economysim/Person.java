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
	protected double profitFactor;
	
	protected String name;
	
	protected Map<String, Integer> averagePrice;
	protected Map<String, Integer> goodBounds;
	
	//list of necessary items that people will buy irregardless of price
	protected ArrayList<String> inelasticGoods;
	
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
		
		goodBounds = new HashMap<String, Integer>();
		goodBounds.put("food", 10);
		goodBounds.put("wood", 10);
		goodBounds.put("tools", 10);
		goodBounds.put("metal", 10);
		goodBounds.put("ore", 10);
		
		profitFactor = 1.5f;
	}
	public void run()
	{
		//virtual function
		food--;
		//modify good bounds by how much money we have
		
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
		if(food < goodBounds.get("food"))
		{
			for(int i = 0; i < (goodBounds.get("food")-food); ++i)
			{
				Bid newBid = new Bid(name, averagePrice.get("food")*2, "food");
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
			if(offer.getPrice() > averagePrice.get(offer.getGoodName()) * 2)
			{
				//too expensive
				//but we increase our standards to compensate for this

				int diffAvg = averagePrice.get(offer.getGoodName()) - offer.getPrice();
				diffAvg = averagePrice.get(offer.getGoodName()) - (diffAvg / 2);
				return false;
			}
			//make sure we lower the averageprice if what we bought is lower
			money -= offer.getPrice();
			if(offer.getGoodName() == "food")
			{
				food++;
				int tmp = (offer.getPrice() + averagePrice.get("food"))/2;
				averagePrice.put("food", tmp);
			}
			else if(offer.getGoodName() == "tools")
			{
				tools++;
				int tmp = (offer.getPrice() + averagePrice.get("tools"))/2;
				averagePrice.put("tools", tmp);
			}
			else if(offer.getGoodName() == "wood")
			{
				wood++;
				int tmp = (offer.getPrice() + averagePrice.get("wood"))/2;
				averagePrice.put("wood", tmp);
			}
			else if(offer.getGoodName() == "metal")
			{
				metal++;
				int tmp = (offer.getPrice() + averagePrice.get("metal"))/2;
				averagePrice.put("metal", tmp);
			}
			else if(offer.getGoodName() == "ore")
			{
				ore++;
				int tmp = (offer.getPrice() + averagePrice.get("ore"))/2;
				averagePrice.put("ore", tmp);
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
		//update prices
		
	}
	//function to change limits based on avg demand
	public void updateLimit(String good, float avgDemand)
	{
		
	}
	//function to update prices based on supply and demand
	public void updatePrice(String good, float slope)
	{
		if(slope > 0.23f || slope < -0.23f)
		{
			int newAvg = (int) (averagePrice.get(good) * (1 - slope));
			int diffAvg = averagePrice.get(good) - newAvg;
			diffAvg = averagePrice.get(good) + (diffAvg / 2);
			averagePrice.put(good, diffAvg);
			
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
