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
	Map<String, Integer> averagePrice;
	Map<String, Integer> currentPrices;
	 
	
	public Market()
	{
		//constructor
		listOfAgents = new ArrayList<Person>();
		offersOnMarket = new ArrayList<Offer>();
		bidsOnMarket = new ArrayList<Bid>();
		averagePrice = new HashMap<String, Integer>();

	}
	public void addAgent(Person newAgent)
	{
		//add an agent to the market
		listOfAgents.add(newAgent);
	}
	public void generateOffers()
	{
		//generate offers based on what each agent wants to sell
		offersOnMarket.clear();
		for(int i = 0; i < listOfAgents.size(); ++i)
		{
			ArrayList<Offer> newOffer = listOfAgents.get(i).createOffer();
			offersOnMarket.addAll(newOffer);
		}
	}
	public void generateBids()
	{
		//generate bids based on what each agent needs
		bidsOnMarket.clear();
		for(int i = 0; i < listOfAgents.size(); ++i)
		{
			ArrayList<Bid> newBid = listOfAgents.get(i).createBid();
			bidsOnMarket.addAll(newBid);
		}
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
		//for each bid try to find a matching offer
		for(int i = 0; i < bidsOnMarket.size(); ++i)
		{
			for(int j = 0; j < offersOnMarket.size(); ++j)
			{
				if(bidsOnMarket.get(i).getGoodName() == offersOnMarket.get(j).getGoodName())
				{
					//found an offer and a bid for the same thing
					//just buy it for now
					System.out.print(bidsOnMarket.get(i).getBuyerName());
					System.out.print(" purchased from ");
					System.out.print(offersOnMarket.get(j).getSellerName());
					System.out.print(" a ");
					System.out.println(offersOnMarket.get(j).getGoodName());
					Person buyer = getAgent(bidsOnMarket.get(i).getBuyerName());
					Person seller = getAgent(offersOnMarket.get(j).getSellerName());
					if(buyer.purchaseOffer(offersOnMarket.get(j)))
					{
						seller.offerPurchased(offersOnMarket.get(j));
						offersOnMarket.remove(j);
						bidsOnMarket.remove(i);
						
						break;
					}
				}
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
}
