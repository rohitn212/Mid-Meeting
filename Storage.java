/*
 * Midpoint meeting
 * 
 * Class: CS 342, Fall 2016
 * System: Windows 10, Eclipse IDE
 * Author Code Number: 1636N
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Storage {
	
	private String[] cityArray;	// to store the cities
	private int cityNo;	// no of cities
	private int[][] distanceArray;	// matrix to store distances between each and every cities
	private Participants[] participantArray;	// to store participants data
	private int[] weight;	// to store the distances of every vertex in the graph
	
	private class Participants	{	// inner class to store participant's data
		private String name;
		private int cityNo;
	}
	
	public void djikstra(int source)	{
		
		boolean[] visited = new boolean[this.cityNo];	// to check if a vertex has been explored
		this.weight = new int[this.cityNo]; // to store the distances for each vertex
		int maxVal = Integer.MAX_VALUE;	
		
		for (int i=1; i<this.cityNo; i++)	{
			this.weight[i] = maxVal;	// initialize every vertex's distance to max value
			visited[i] = false;	// initialize every vertex as unvisited
		}

		this.weight[source] = 0;	// the first vertex 
		int visitedCounter = 0;	
		int minVal;
		int minCityNo = source;
		int current = source;
		
		while(true)	{
			
			visitedCounter = 0;
			
			for (int i=1; i<this.cityNo; i++)	{	// check how many vertex's have been visited
				if (visited[i] == true)	{
					visitedCounter++;
				}
			}

			if (visitedCounter == this.cityNo-1)	{	// end condition
				return;
			}
			
			minVal = maxVal;	
			visited[current] = true;	// update current vertex as visited
			
			/*
			 * Update the neighbors of current vertex
			 */
			for (int i=1; i<this.cityNo; i++)	{
				if (distanceArray[current][i] != 0 && visited[i] == false)	{
					if (this.weight[i] > (this.weight[current]+distanceArray[current][i]))	{
						this.weight[i] = this.weight[current]+distanceArray[current][i];
					}
				}
			}
			
			/*
			 * Find the smallest unvisited vertex
			 */
			for (int i=1; i<this.cityNo; i++)	{
				if (visited[i] == false && this.weight[i]<minVal)	{
					minVal = this.weight[i];
					minCityNo = i;
				}
			}
			
			current = minCityNo;
		}
	}
	
	public void parseCity() throws IOException	{
		String fileName = "CityNames.txt";
		
		try {
			BufferedReader buff = new BufferedReader(new FileReader(fileName));
			String line;
			int cityNo;
			int counter = 1;
			
			cityNo = Integer.parseInt(buff.readLine());
			cityNo = cityNo + 1;
			String[] cityArray = new String[cityNo];
			
			while ((line = buff.readLine()) != null)	{
				cityArray[counter] = line;
				counter++;
			}
			this.cityArray = cityArray;
			this.cityNo = cityNo;
			buff.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void printDistanceFromChicago(String[] cityArray, int[] tracker)	{
		for (int i=1; i<cityArray.length; i++)	{
			System.out.println("Distance from Chicago to " + cityArray[i] + ": " + this.weight[tracker[i]]);
		}
		System.out.println("\n");
	}
	
	public void sortCities()	{	// to sort the cities alphabetically
		String[] sortedCityArray = new String[this.cityNo];	// to store the sorted cities
		int[] tracker = new int[this.cityNo];	// to keep track of the cities
		String temp = new String();	
		int temp2;
		
		for (int i=1; i<this.cityNo; i++)	{
			sortedCityArray[i] = this.cityArray[i];
			tracker[i] = i;
		}
		
		for (int i=1; i<this.cityNo; i++)	{	// sort 
			for (int j=1; j<this.cityNo; j++)	{
				if (sortedCityArray[i].compareTo(sortedCityArray[j]) < 0)	{	// if alphabetically smaller
					temp = sortedCityArray[i];
					temp2 = tracker[i];
					sortedCityArray[i] = sortedCityArray[j];
					sortedCityArray[j] = temp;
					tracker[i] = tracker[j];
					tracker[j] = temp2;
				}
			}
		}

		printDistanceFromChicago(sortedCityArray, tracker);
	}
	
	public void printInfo()	{
		System.out.println("Author Code Number: 1636N");
		System.out.println("Class: CS342, Fall 2016");
		System.out.println("Program: #1, Mid Meeting");
		System.out.print("\n");
	}
	
	public void printCityArray()	{
		for (int i=1; i<this.cityNo; i++)	{
			System.out.println("cityArray[" + i + "] = " + cityArray[i]);
		}
	}
	
	public void parseDistance() throws IOException	{
		String fileName = "CityDistances.txt";
		
		try {
			BufferedReader buff = new BufferedReader(new FileReader(fileName));
			String line;
			String[] parts = new String[3];
			int [][] distanceArray = new int[this.cityNo][this.cityNo];
			String first;
			first = buff.readLine();
			int cityA, cityB, distance;
			
			while ((line = buff.readLine()) != null)	{
				parts = line.split(" ", 3);
				cityA = Integer.parseInt(parts[0]);
				cityB = Integer.parseInt(parts[1]);
				distance = Integer.parseInt(parts[2]);
				distanceArray[cityA][cityB] = distance;
				distanceArray[cityB][cityA] = distance;
			}
			this.distanceArray = distanceArray;
			buff.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void printDistanceArray()	{
		
		System.out.println("Format: City[CityNo] = [CityNo/distance]");
		System.out.print("\n");
		
		for (int i=1; i<this.cityNo; i++)	{
			System.out.print("City[" + i + "] = ");
			for (int j=1; j<this.cityNo; j++)	{
				if (distanceArray[i][j] != 0)	{	// if there is distance between the cities
					System.out.print("[" + j + "/" + distanceArray[i][j] + "] ");
				}
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
	
	public void parseParticipants() throws IOException	{
		String fileName = "Participants.txt";
		
		try {
			BufferedReader buff = new BufferedReader(new FileReader(fileName));
			String line;
			String[] parts = new String[2];
			int participantsNo = Integer.parseInt(buff.readLine());
			Participants[] participantArray = new Participants[participantsNo];
			int counter=0;
			
			while ((line = buff.readLine()) != null)	{
				parts = line.split(" ", 2);
				Participants newParticipant = new Participants();
				newParticipant.name = parts[0];
				newParticipant.cityNo = Integer.parseInt(parts[1]);
				participantArray[counter] = newParticipant;
				counter++;
			}
			this.participantArray = participantArray;
			buff.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void findShortestAverageDistance()	{	// find the midpoint and the smallest average distance
		
		int[] shortestDistanceArray = new int[this.cityNo];	// to store the average distance traveled among participants for each city
		int minVal = Integer.MAX_VALUE;
		int minCityNo = 0;
		
		for (int i=1; i<this.cityNo; i++)	{
			djikstra(i);	// use djikstra's function on each city
			for (int j=0; j<this.participantArray.length; j++)	{
				shortestDistanceArray[i] =  shortestDistanceArray[i]	// calculate the sum of the distance traveled by all the participants to get to a city
						+ this.weight[this.participantArray[j].cityNo];
			}
			shortestDistanceArray[i] = shortestDistanceArray[i]/this.participantArray.length; // to find the average distance traveled among participants
			if (minVal > shortestDistanceArray[i])	{	// to keep track of which city has the shortest average distance traveled among all participants
				minVal = shortestDistanceArray[i];
				minCityNo = i;
			}
		}
		
		System.out.println("Midpoint City:" + this.cityArray[minCityNo]);
		System.out.println("Average Distance:" + minVal);
		
	}
	
	public void printParticipantArray()	{
		for (int i=0; i<this.participantArray.length; i++)	{
			System.out.println("Participant[" + i + "} name: " + this.participantArray[i].name);
			System.out.println("Participant[" + i + "} cityNo: " + this.participantArray[i].cityNo);
		}
	}

}