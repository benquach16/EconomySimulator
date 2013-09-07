package economysim;
import economysim.Farmer;
import economysim.Blacksmith;
import economysim.Market;
import economysim.Woodcutter;
import economysim.Miner;
import economysim.Refiner;

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
		Farmer alfredo = new Farmer("Alfredo");
		Blacksmith alfonso = new Blacksmith("Alfonso");
		Woodcutter alberto = new Woodcutter("Alberto");
		Miner alejandro = new Miner("Alejandro");
		Refiner alfrondso = new Refiner("Alfrondso");
		Market market = new Market();
		market.addAgent(john);
		market.addAgent(alfredo);
		market.addAgent(alfonso);
		market.addAgent(alberto);
		market.addAgent(alejandro);
		market.addAgent(alfrondso);
		market.print();
		while(run)
		{
			market.run();
			market.print();
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
