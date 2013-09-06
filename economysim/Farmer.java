package economysim;
import economysim.Person;


public class Farmer extends Person {
	public Farmer(String name)
	{
		//constructor
		//invoke person constructor
		super(name);
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
		System.out.print("Food: ");
		System.out.println(food);
		System.out.print("Tools: ");
		System.out.println(tools);
		System.out.print("Wood: ");
		System.out.println(wood);
		System.out.println("");
		
	}
	public Offer createOffer()
	{
		Offer ret = new Offer(name, 5, "food");
		return ret;
	}
	public boolean wantsToSell()
	{
		if(food > 5)
			return true;
		return false;
	}
	public String getProfession()
	{
		return "Farmer";
	}

}
