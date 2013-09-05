package economysim;
import economysim.Farmer;
import economysim.Blacksmith;
import economysim.Market;

//main class
public class Main {

	public static void main(String[] args) {
		Farmer john = new Farmer();
		Blacksmith alfonso = new Blacksmith();
		Market market = new Market();
		market.addAgent(john);
		market.addAgent(alfonso);
	}

}
