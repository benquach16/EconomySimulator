package economysim;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import economysim.Offer;
import economysim.Person;
import economysim.Bid;

//market class
public class Market {
	//array of all our agents
	ArrayList<Person> listOfAgents;
	ArrayList<Offer> offersOnMarket;
	ArrayList<Bid> bidsOnMarket;
	Map<String, ArrayList<Integer>> marketHistoryDemand;
	Map<String, ArrayList<Integer>> marketHistorySupply;
	
	public Market()
	{
		//constructor
		//make sure we initialize everything
		listOfAgents = new ArrayList<Person>();
		offersOnMarket = new ArrayList<Offer>();
		bidsOnMarket = new ArrayList<Bid>();
		marketHistoryDemand = new HashMap<String, ArrayList<Integer>>();
		marketHistoryDemand.put("food", new ArrayList<Integer>());
		marketHistoryDemand.put("tools", new ArrayList<Integer>());
		marketHistoryDemand.put("wood", new ArrayList<Integer>());
		marketHistoryDemand.put("ore", new ArrayList<Integer>());
		marketHistoryDemand.put("metal", new ArrayList<Integer>());
		
		marketHistorySupply = new HashMap<String, ArrayList<Integer>>();
		marketHistorySupply.put("food", new ArrayList<Integer>());
		marketHistorySupply.put("tools", new ArrayList<Integer>());
		marketHistorySupply.put("wood", new ArrayList<Integer>());
		marketHistorySupply.put("ore", new ArrayList<Integer>());
		marketHistorySupply.put("metal", new ArrayList<Integer>());
		
	}
	public void addAgent(Person newAgent)
	{
		//add an agent to the market
		listOfAgents.add(newAgent);
	}

	public Offer findOffer(String good)
	{
		for(int i = 0; i < offersOnMarket.size(); ++i)
		{
			if(offersOnMarket.get(i).getGoodName() == good)
			{
				return offersOnMarket.get(i);
			}
		}
		return null;
	}
	public void run()
	{
		//do production!
		for(int i = 0; i < listOfAgents.size(); ++i)
		{
			listOfAgents.get(i).run();
		}
		generateOffers();
		generateBids();
		//calculate the supply and demand of this cycle
		generateSupplyAndDemand();
		//for each bid try to find a matching offer
		for(int i = 0; i < bidsOnMarket.size(); ++i)
		{
			for(int j = 0; j < offersOnMarket.size(); ++j)
			{
				if(bidsOnMarket.get(i).getGoodName() == offersOnMarket.get(j).getGoodName())
				{

					Person buyer = getAgent(bidsOnMarket.get(i).getBuyerName());
					Person seller = getAgent(offersOnMarket.get(j).getSellerName());
					if(buyer.purchaseOffer(offersOnMarket.get(j)))
					{
						//found an offer and a bid for the same thing
						//just buy it for now
						System.out.print(bidsOnMarket.get(i).getBuyerName());
						System.out.print(" purchased from ");
						System.out.print(offersOnMarket.get(j).getSellerName());
						System.out.print(" a ");
						System.out.print(offersOnMarket.get(j).getGoodName());
						System.out.print(" for ");
						System.out.println(offersOnMarket.get(j).getPrice());
						//update supply and demand
						
						seller.offerPurchased(offersOnMarket.get(j));
						offersOnMarket.remove(j);
						bidsOnMarket.remove(i);
						
						break;
					}
				}
			}
		}
		//now go through all the offerss and if bids didn't sell we reduce the price
		//reduce price based on the supply of the item
		
		for(int i = 0; i < offersOnMarket.size(); ++i)
		{
			Person seller = getAgent(offersOnMarket.get(i).getSellerName());
			System.out.print(seller.getName());
			System.out.print(" did not sell ");
			System.out.print(offersOnMarket.get(i).getGoodName());
			System.out.print(" for ");
			System.out.println(offersOnMarket.get(i).getPrice());
		}
		
		for(int i = 0; i < listOfAgents.size(); ++i)
		{
			listOfAgents.get(i).updatePrice("food", generateSupplySlope("food"));
			listOfAgents.get(i).updatePrice("wood", generateSupplySlope("wood"));
			listOfAgents.get(i).updatePrice("tools", generateSupplySlope("tools"));
			listOfAgents.get(i).updatePrice("metal", generateSupplySlope("metal"));
			listOfAgents.get(i).updatePrice("ore", generateSupplySlope("ore"));
		}
	}
	
	//find the agent with this name
	public Person getAgent(String name)
	{
		for(int i = 0; i < listOfAgents.size(); ++i)
		{
			if(listOfAgents.get(i).getName() == name)
			{
				return listOfAgents.get(i);
			}
		}
		//if we didnt find the agent then we return a dummy
		return null;
	}
	
	public void print()
	{
		//print all the agents
		for(int i = 0; i < listOfAgents.size(); ++i)
		{
			listOfAgents.get(i).print();
		}
	}
	
	public float generateSupplySlope(String good)
	{
		//find the average first of each
		//total the vectors and divide by size
		//if a slope is 0, ie x = 5 this is perfectly inelastic
		//if a slope approaches infinity it is perfectly elastic
		float total = 0;
		float avgSupply = 0;
		float avgDemand = 0;
		ArrayList<Integer> supplyVector;
		ArrayList<Integer> demandVector;
		if(good == "food")
		{
			supplyVector = marketHistorySupply.get("food");
			demandVector = marketHistoryDemand.get("food");
		}
		else if(good == "tools")
		{
			supplyVector = marketHistorySupply.get("tools");
			demandVector = marketHistoryDemand.get("tools");
		}
		else if(good == "wood")
		{
			supplyVector = marketHistorySupply.get("wood");
			demandVector = marketHistoryDemand.get("wood");
		}
		else if(good == "ore")
		{
			supplyVector = marketHistorySupply.get("ore");
			demandVector = marketHistoryDemand.get("ore");
		}
		else
		{
			supplyVector = marketHistorySupply.get("metal");
			demandVector = marketHistoryDemand.get("metal");
		}
		for(int i = 0; i < supplyVector.size(); ++i)
		{
			avgSupply += supplyVector.get(i);
		}
		avgSupply = avgSupply / supplyVector.size();
		for(int i = 0; i < demandVector.size(); ++i)
		{
			avgDemand += demandVector.get(i);
		}
		avgDemand = avgDemand / demandVector.size();
		
		total = (avgDemand - avgSupply) / (avgDemand + avgSupply);
		//System.out.println(total);
		return total;
	}
	
	//accessors go here
	
	//protected functions go here
	
	protected void generateOffers()
	{
		//generate offers based on what each agent wants to sell
		offersOnMarket.clear();
		for(int i = 0; i < listOfAgents.size(); ++i)
		{
			ArrayList<Offer> newOffer = listOfAgents.get(i).createOffer();
			offersOnMarket.addAll(newOffer);
		}
	}
	protected void generateBids()
	{
		//generate bids based on what each agent needs
		bidsOnMarket.clear();
		for(int i = 0; i < listOfAgents.size(); ++i)
		{
			ArrayList<Bid> newBid = listOfAgents.get(i).createBid();
			bidsOnMarket.addAll(newBid);
		}
	}
	
	protected void generateSupplyAndDemand()
	{
		int cycleDemand[] = {0,0,0,0,0};
		for(int i = 0; i < bidsOnMarket.size(); ++i)
		{
			Bid bid = bidsOnMarket.get(i);
			if(bid.getGoodName()=="food")
				cycleDemand[0]++;
			if(bid.getGoodName()=="tools")
				cycleDemand[1]++;
			if(bid.getGoodName()=="wood")
				cycleDemand[2]++;
			if(bid.getGoodName()=="ore")
				cycleDemand[3]++;
			if(bid.getGoodName()=="metal")
				cycleDemand[4]++;
		}
		for(int i = 0; i < 5; ++i)
		{
			ArrayList<Integer> tmp;
			if(i == 0)
				tmp = marketHistoryDemand.get("food");
			else if(i == 1)
				tmp = marketHistoryDemand.get("tools");
			else if(i == 2)
				tmp = marketHistoryDemand.get("wood");
			else if(i == 3)
				tmp = marketHistoryDemand.get("ore");
			else
				tmp = marketHistoryDemand.get("metal");
			tmp.add(cycleDemand[i]);
		}
		
		int cycleSupply[] = {0,0,0,0,0};
		for(int i = 0; i < offersOnMarket.size(); ++i)
		{
			Offer offer = offersOnMarket.get(i);
			if(offer.getGoodName()=="food")
				cycleSupply[0]++;
			if(offer.getGoodName()=="tools")
				cycleSupply[1]++;
			if(offer.getGoodName()=="wood")
				cycleSupply[2]++;
			if(offer.getGoodName()=="ore")
				cycleSupply[3]++;
			if(offer.getGoodName()=="metal")
				cycleSupply[4]++;
		}		
		for(int i = 0; i < 5; ++i)
		{
			ArrayList<Integer> tmp;
			if(i == 0)
				tmp = marketHistorySupply.get("food");
			else if(i == 1)
				tmp = marketHistorySupply.get("tools");
			else if(i == 2)
				tmp = marketHistorySupply.get("wood");
			else if(i == 3)
				tmp = marketHistorySupply.get("ore");
			else
				tmp = marketHistorySupply.get("metal");
			tmp.add(cycleSupply[i]);
		}		
		
	}
}
