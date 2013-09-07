package economysim;
import java.util.ArrayList;

import economysim.Person;

//miner class
//produces ore
public class Miner extends Person{
	public Miner(String name)
	{
		super(name);
	}
	public void run()
	{
		super.run();
		if(food > 0 && tools > 0)
		{
			ore+=4;
		}
		else if(food > 0)
		{
			ore+=2;
		}
	}
	public void print()
	{
		super.print();
		System.out.print("Tools: ");
		System.out.println(tools);
		System.out.print("Ore: ");
		System.out.println(ore);
		System.out.println("");
	}
	public ArrayList<Offer> createOffer()
	{
		ArrayList<Offer> ret = new ArrayList<Offer>();
		for(int i = 0; i < this.ore; ++i)
		{
			Offer newOffer = new Offer(name, 10, "ore");
			ret.add(newOffer);
		}
		return ret;
	}
	public String getProfession()
	{
		return "Miner";
	}
}
