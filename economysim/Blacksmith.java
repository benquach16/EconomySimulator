package economysim;
import economysim.Person;

public class Blacksmith extends Person {
	
	public Blacksmith(String name)
	{
		super(name);
	
	}
	public void run()
	{
		super.run();
		if(food > 1)
		{
			tools+=metal;
			metal = 0;
		}
	}
	public void print()
	{
		super.print();
		System.out.print("Food: ");
		System.out.println(food);
		System.out.print("Tools: ");
		System.out.println(tools);
		System.out.print("Metal: ");
		System.out.println(metal);
		System.out.println("");
	}
	public String getProfession()
	{
		return "Blacksmith";
	}
	
}
