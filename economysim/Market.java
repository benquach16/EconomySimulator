package economysim;
import java.util.ArrayList;
import economysim.Person;

//market class
public class Market {
	//array of all our agents
	ArrayList<Person> listOfAgents;
	public Market()
	{
		//constructor
	}
	public void addAgent(Person newAgent)
	{
		//add an agent to the market
		listOfAgents.add(newAgent);
	}
	public void generateOffers()
	{
		//generate offers based on whats needed by each agent
		for(int i = 0; i < listOfAgents.size(); ++i)
		{
			
		}
	}
	public int findOffer(String good)
	{
		if(good == "food")
		{
			//search through offers for food
		}
		return 0;
	}
}
