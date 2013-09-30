package economysim;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
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
		randomize(bidsOnMarket);
		randomize(offersOnMarket);
		for(int i = 0; i < bidsOnMarket.size(); ++i)
		{
			for(int j = 0; j < offersOnMarket.size(); ++j)
			{
				if(bidsOnMarket.get(i).getGoodName() == offersOnMarket.get(j).getGoodName())
				{

					Person buyer = getAgent(bidsOnMarket.get(i).getBuyerName());
					Person seller = getAgent(offersOnMarket.get(j).getSellerName());
					if(buyer != seller && buyer.purchaseOffer(offersOnMarket.get(j)))
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
		/*
		for(int i = 0; i < offersOnMarket.size(); ++i)
		{
			Person seller = getAgent(offersOnMarket.get(i).getSellerName());
			System.out.print(seller.getName());
			System.out.print(" did not sell ");
			System.out.print(offersOnMarket.get(i).getGoodName());
			System.out.print(" for ");
			System.out.println(offersOnMarket.get(i).getPrice());
		}*/
		
		for(int i = 0; i < listOfAgents.size(); ++i)
		{
			listOfAgents.get(i).updatePrice("food", generateSupplySlope("food"));
			listOfAgents.get(i).updatePrice("wood", generateSupplySlope("wood"));
			listOfAgents.get(i).updatePrice("tools", generateSupplySlope("tools"));
			listOfAgents.get(i).updatePrice("metal", generateSupplySlope("metal"));
			listOfAgents.get(i).updatePrice("ore", generateSupplySlope("ore"));
			if(listOfAgents.get(i).getFood() < 0)
			{
				//died
				swapAgent(listOfAgents.get(i));
			}
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
		//System.out.print(good);
		//System.out.println(total);
		return total;
	}
	
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
	
	//add the avg supply and demand to the history
	//for each cycle
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
			//System.out.println(cycleDemand[i]);
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
	
	//protected function
	//determines the most profitable job
	protected String getMostDemandedGood()
	{
		String good;
		//for each good find the supply to demand ratio
		int index = 0;
		float ratio[] = {0,0,0,0,0};
		//find ratios
		ratio[0] = ((float)getAverageSupply("food") / getAverageDemand("food"));
		ratio[1] = ((float)getAverageSupply("tools") / getAverageDemand("tools"));
		ratio[2] = ((float)getAverageSupply("wood") / getAverageDemand("wood"));
		ratio[3] = ((float)getAverageSupply("ore") / getAverageDemand("ore"));
		ratio[4] = ((float)getAverageSupply("metal") / getAverageDemand("metal"));
		
		for(int i = 0; i < 5; ++i)
		{
			if(ratio[i] > ratio[index])
			{
				index = i;
			}
		}
		if(index == 0)
			good = "food";
		else if(index == 1)
			good = "tools";
		else if(index == 2)
			good = "wood";
		else if(index == 3)
			good = "ore";
		else
			good = "metal";
		return good;
	}
	
	//protected function
	//if an agent is dead we swap him for a new class and a new life
	protected void swapAgent(Person agent)
	{
		for(int i = 0 ; i < listOfAgents.size(); ++i)
		{
			if(listOfAgents.get(i) == agent)
			{
				listOfAgents.remove(i);
			}
		}
		String good = getMostDemandedGood();

		if(good == "food")
		{
			agent = new Farmer(agent.getName());
			
		}
		else if (good == "tools")
		{
			agent = new Blacksmith(agent.getName());
		}
		else if (good == "wood")
		{
			agent = new Woodcutter(agent.getName());
		}
		else if (good == "ore")
		{
			agent = new Miner(agent.getName());
		}
		System.out.print(agent.getName());
		System.out.print(" is added as a new ");
		System.out.print(agent.getProfession());
		listOfAgents.add(agent);
	}
	
	//protected function
	//get averagebids
	protected int getAverageDemand(String good)
	{
		ArrayList<Integer> demandVector = marketHistoryDemand.get(good);
		int avgDemand = 0;
		for(int i = 0; i < demandVector.size(); ++i)
		{
			avgDemand += demandVector.get(i);
		}
		avgDemand = avgDemand / demandVector.size();
		return avgDemand;
	}
	
	//protected function
	//get averageoffers
	protected int getAverageSupply(String good)
	{
		ArrayList<Integer> supplyVector = marketHistorySupply.get(good);
		int avgSupply = 0;
		for(int i = 0; i < supplyVector.size(); ++i)
		{
			avgSupply += supplyVector.get(i);
		}
		avgSupply = avgSupply / supplyVector.size();
		return avgSupply;
	}	
	
	//protected function
	//randomizes bids so position is not a factor in who gets to sell and buy
	protected <T> void randomize(ArrayList<T> toShuffle)
	{
		//this function ensures that position is not a factor in whose items get sold and bought
		if(toShuffle.size() > 1)
		{
			for(int i = 0; i < toShuffle.size(); ++i)
			{
				int n = (int) (Math.random() * toShuffle.size());
				Collections.swap(toShuffle, i, n);
			}
		}
	}
}
