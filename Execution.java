import java.io.IOException;

public class Execution  {

	public static void main (String[] args) throws IOException	{
		Storage storage = new Storage();
		storage.printInfo();
		storage.parseCity();
		storage.parseDistance();
		storage.printDistanceArray();
		storage.djikstra(58);
		storage.sortCities();
		storage.parseParticipants();
		storage.findShortestAverageDistance();
	}
	
}
