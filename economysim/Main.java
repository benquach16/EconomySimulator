package economysim;
import economysim.Farmer;
import economysim.Blacksmith;
import economysim.Market;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//main class
public class Main {

	public static void main(String[] args) throws IOException {
		//create input stream
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean run = true;
		
		Farmer john = new Farmer("John");
		Blacksmith alfonso = new Blacksmith("Alfonso");
		Market market = new Market();
		market.addAgent(john);
		market.addAgent(alfonso);
		john.print();
		alfonso.print();
		while(run)
		{
			market.run();
			john.print();
			alfonso.print();
			
			String input = br.readLine();
			if(input == "stop")
			{
				run = false;
			}
			else
			{
				//keep going
			}
		}

	}

}
