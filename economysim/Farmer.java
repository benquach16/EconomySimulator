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
		//every tick make food
		if(tools > 0 && wood > 0 && food > 0)
		{
			//we can do stuff
			food --;
			wood --;
			food += 5;
		}
		else if(wood > 0 && food > 0)
		{
			//has no tools
			food --;
			wood --;
			food += 3;
		}
		else if(food < 1)
		{
			//no food?
			//better buy some on the market then
			//if no money then we kill this guy
			
		}
		if(food > 5)
		{
			//excess food so lets sell it
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
	public String getProfession()
	{
		return "Farmer";
	}

}
