/**
 * 
 * @author Chirag Shah..
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class FindRoute {
	
	 static ArrayList<Stations> stationsListL = new ArrayList<Stations>();// maintaining list of stations with optimal path to destination..CHS
	 static ArrayList<Stations> sortedStationsList = new ArrayList<Stations>();// maintaining sorted list of stations from input files to use as queue..CHS
	 static	ArrayList<Stations> clonnedSortedStationsListL = new ArrayList<Stations>();
	 static int flag=0;// maintaining flags for breaking conditions..CHS
	 static int vistedMethod=0;
	 
	 static String sourceStation="";
	 static String destinationStation="";
	 @SuppressWarnings("unchecked")
	public static void main(String [] args)
	 {
		 try {
			 if (args.length>0) 
			 {
				 String filePath = args[0];
				 String startingStation = args[1];
				 String endingStation = args[2];

				 System.out.println("Filepath to read input text :" + filePath+ "\nStart Station :" + startingStation+ "\nDestination Station :" + endingStation+"\n");
				 ArrayList<Stations> stationsList = new ArrayList<Stations>();//adding details from input file in this list in format {Start,End,Path}..CHS
				 File fileObj = new File(filePath);// create a file object for input file provided..CHS

				 try {
					 //scan the input file..
					 Scanner scannObj = new Scanner(fileObj);
					 while (scannObj.hasNextLine()) {
						 String line = scannObj.nextLine();
						 //splitting the stations name and path with the space and adding in stationList Array..
						 String[] fields = line.split(" ");
						 stationsList.add(new Stations(String.valueOf(fields[0]),String.valueOf(fields[1]), Integer.parseInt(fields[2])));
						 //System.out.println("CHS:Stations that are added to the Class object are: "+ fields[0]+ ":: "+ fields[1]+ "::"+ fields[2]);
					 }
					 scannObj.close();
				 } catch (FileNotFoundException e) {
					 System.out.println("Error while scanning the input file: "+ e.getMessage());
				 } catch (NumberFormatException e) {
					 //System.out.println("CHS:End of the input file: " + e.getMessage());
				 }
				 Collections.sort(stationsList); // sorting station List in ascending order..
				 sortedStationsList = (ArrayList<Stations>) stationsList.clone();
				 sourceStation = startingStation;
				 destinationStation=endingStation;
				 costSearch();// calling first method to begin operation with. 
			 }
			 else
			 {
				 System.out.println("Error as no argument was passed");
			 }
		 } catch (Exception e) {
			 System.out.println("Exception inside PSVM() :"+e.getMessage());
		 }
	 }
	
	@SuppressWarnings("rawtypes")
	public static ArrayList<ArrayList> costSearch()
	{
		//System.out.println("costSearch() Begins: ");
		try {

			if(flag==1)// if End Station found..
			{
				filterFinalSearchList(stationsListL);
			}

			//inserting root node first..
			if(stationsListL.size()==0)
			{
				for(int i=0; i< sortedStationsList.size();i++)
				{
					// logic for checking in Source->Destination form..
					if(sortedStationsList.get(i).startStation.equals(sourceStation) && !sortedStationsList.get(i).endStation.equals(destinationStation) )
					{
						stationsListL.add(new Stations(sortedStationsList.get(i).startStation,sortedStationsList.get(i).endStation, sortedStationsList.get(i).pathCost));
						clonnedSortedStationsListL.add(new Stations(sortedStationsList.get(i).startStation,sortedStationsList.get(i).endStation, sortedStationsList.get(i).pathCost));
						sortedStationsList.remove(i);
						return costSearch(); 
					}
					// logic for checking in Destination->Source form..
					else if(sortedStationsList.get(i).endStation.equals(sourceStation) && !sortedStationsList.get(i).startStation.equals(destinationStation) )
					{
						stationsListL.add(new Stations(sortedStationsList.get(i).endStation,sortedStationsList.get(i).startStation, sortedStationsList.get(i).pathCost));
						clonnedSortedStationsListL.add(new Stations(sortedStationsList.get(i).endStation,sortedStationsList.get(i).startStation, sortedStationsList.get(i).pathCost));
						sortedStationsList.remove(i);
						return costSearch(); 
					}
				}
			}
			rootSearch();//Checking if there are more parent Stations existing in List..CHS
			if(flag!=1){
				childrenSearch();//Checking	 there are children to Parent Stations in List.. CHS
			}
		}
		catch (Exception e) 
		{
			System.out.println("Exception inside costSearch() :" + e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static ArrayList<ArrayList> rootSearch()
	{
		//System.out.println("rootSearch() Begins: ");
		try {
			if(stationsListL.size()>0)
			{
				for(int m=0; m< stationsListL.size();m++)
				{
					for(int j=0; j< sortedStationsList.size();j++)
					{
						// logic for checking in Source->Destination form..
						if(stationsListL.get(m).startStation.equals(sortedStationsList.get(j).startStation))
						{
							//System.out.println("Checking the parents conditions");
							stationsListL.add(new Stations(sortedStationsList.get(j).startStation,sortedStationsList.get(j).endStation, sortedStationsList.get(j).pathCost));
							clonnedSortedStationsListL.add(new Stations(sortedStationsList.get(j).startStation,sortedStationsList.get(j).endStation, sortedStationsList.get(j).pathCost));
							Collections.sort(stationsListL);
							sortedStationsList.remove(j);
							return costSearch();
						}
						// logic for checking in Destination->Source form..
						else if(stationsListL.get(m).startStation.equals(sortedStationsList.get(j).endStation))
						{
							//System.out.println("Destination->Source :CHS Checking the parents conditions");
							stationsListL.add(new Stations(sortedStationsList.get(j).endStation,sortedStationsList.get(j).startStation, sortedStationsList.get(j).pathCost));
							clonnedSortedStationsListL.add(new Stations(sortedStationsList.get(j).endStation,sortedStationsList.get(j).startStation, sortedStationsList.get(j).pathCost));
							Collections.sort(stationsListL);
							sortedStationsList.remove(j);
							return costSearch();
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Exception inside rootSearch() :"+e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static ArrayList<ArrayList> childrenSearch(){
		//System.out.println("childrenSearch() Begins: "+stationsListL.size());
		try {
			if (stationsListL.size()>0) {
				for (int k = 0; k < stationsListL.size(); k++) {
					if (!stationsListL.get(k).endStation.equals(destinationStation)) {// if OptimalPathList not ending in goal state..CHS 
						for (int l = 0; l < sortedStationsList.size(); l++) {
							// logic for checking in Source->Destination form..
							if (stationsListL.get(k).endStation.equals(sortedStationsList.get(l).startStation)) {
								if (!sortedStationsList.get(l).endStation.equals(destinationStation)) {
									// adding stations in Optimal List Path if distance short
									stationsListL.add(new Stations(stationsListL.get(k).startStation.concat(":"+ sortedStationsList.get(l).startStation),sortedStationsList.get(l).endStation,stationsListL.get(k).pathCost+ sortedStationsList.get(l).pathCost));
									clonnedSortedStationsListL.add(new Stations(sortedStationsList.get(l).startStation,sortedStationsList.get(l).endStation,sortedStationsList.get(l).pathCost));
									sortedStationsList.remove(l);// iterate again by dequeuing unwanted goal states ..
									Collections.sort(stationsListL);
									flag = 0;// set flag 0 if goal not found and optimal path found.CHS
									return costSearch();// iterate again
								} else if (sortedStationsList.get(l).endStation.equals(destinationStation)) {
									//System.out.println("Got End Station.. "+ sortedStationsList.get(l).endStation);
									stationsListL.add(new Stations(stationsListL.get(k).startStation.concat(":"+ sortedStationsList.get(l).startStation),sortedStationsList.get(l).endStation,stationsListL.get(k).pathCost+ sortedStationsList.get(l).pathCost));
									clonnedSortedStationsListL.add(new Stations(sortedStationsList.get(l).startStation,sortedStationsList.get(l).endStation,sortedStationsList.get(l).pathCost));
									sortedStationsList.remove(l);// if match found dequeue it from sortedList 
									Collections.sort(stationsListL);
									//check if endStation yet present in sortedList 
									if (checkIfConsistsEndStation(sortedStationsList, stationsListL)) {
										flag = 0;// set flag 0 if goal not found and optimal path found.CHS
										return costSearch();// iterate again
									} else {
										flag = 1;// set flag 1 if match found and optimal path found.CHS
										System.out.println("Path Found ");
										return costSearch();// iterate again
									}
								}
							}
							// logic for checking in Destination->Source form..
							else if (stationsListL.get(k).endStation.equals(sortedStationsList.get(l).endStation)) 
							{
								if (!sortedStationsList.get(l).startStation.equals(destinationStation)) {
									// adding stations in Optimal List Path if distance short
									stationsListL.add(new Stations(stationsListL.get(k).startStation.concat(":"+ sortedStationsList.get(l).endStation),sortedStationsList.get(l).startStation,stationsListL.get(k).pathCost+ sortedStationsList.get(l).pathCost));
									clonnedSortedStationsListL.add(new Stations(sortedStationsList.get(l).endStation,sortedStationsList.get(l).startStation,sortedStationsList.get(l).pathCost));
									sortedStationsList.remove(l);// iterate again by dequeuing unwanted goal states ..
									Collections.sort(stationsListL);
									flag = 0;// set flag 0 if goal not found and optimal path found.CHS
									return costSearch();// iterate again
								} else if (sortedStationsList.get(l).startStation.equals(destinationStation)) {
									//System.out.println("D->S form..Got End Station.. "+ sortedStationsList.get(l).startStation);
									stationsListL.add(new Stations(stationsListL.get(k).startStation.concat(":"+ sortedStationsList.get(l).endStation),sortedStationsList.get(l).startStation,stationsListL.get(k).pathCost+ sortedStationsList.get(l).pathCost));
									clonnedSortedStationsListL.add(new Stations(sortedStationsList.get(l).endStation,sortedStationsList.get(l).startStation,sortedStationsList.get(l).pathCost));
									sortedStationsList.remove(l);// if match found dequeue it from sortedList 
									Collections.sort(stationsListL);
									//check if endStation yet present in sortedList 
									if (checkIfConsistsEndStation(sortedStationsList, stationsListL)) {
										flag = 0;// set flag 0 if goal not found and optimal path found.CHS
										return costSearch();// iterate again
									} else {
										flag = 1;// set flag 1 if match found and optimal path found.CHS
										System.out.println("Path Found");
										return costSearch();// iterate again
									}
								}
							}
						}//end of FOR
						//remove from mainList
						stationsListL.remove(k);
						k--;
					}//end of IF
				}// end of FOR
			}
			else
			{
				// display final result if searching results nothing..
				flag = 1;// set flag 1 if match found and optimal path found.CHS
				return costSearch();
			}
		} catch (Exception e) {
			System.out.println("Exception inside childrenSearch():"+ e.getMessage());
		}
		return null;
	}
	

	public static void filterFinalSearchList(ArrayList<Stations> filterStationsList)
	{
		// this functions displays optimal route with expected output style..
		
		//System.out.println("filterFinalSearchList() Begins: ");
		try {
			ArrayList<Stations> displayArrayList = new ArrayList<Stations>(); 
			if (filterStationsList.size()>0) {
				Collections.sort(filterStationsList);
				for (int i = 0; i < filterStationsList.size(); i++) {
					if (filterStationsList.get(i).endStation.equals(destinationStation)) {
						displayArrayList.add(new Stations(filterStationsList.get(0).startStation.concat(":"+ filterStationsList.get(0).endStation),filterStationsList.get(0).endStation, filterStationsList.get(0).pathCost));
						String values= displayArrayList.get(0).startStation;
						String[] routeArray = values.split(":"); 
						System.out.println("Distance :"+ displayArrayList.get(0).pathCost +"\nRoute:");
						for(int j=0;j<routeArray.length;j++){
							for(int k=0;k<clonnedSortedStationsListL.size();k++){
								if(clonnedSortedStationsListL.get(k).startStation.equals(routeArray[j]) && clonnedSortedStationsListL.get(k).endStation.equals(routeArray[j+1]) )
								{
									System.out.println(clonnedSortedStationsListL.get(k).startStation +" to "+clonnedSortedStationsListL.get(k).endStation +","+clonnedSortedStationsListL.get(k).pathCost +" km");	
								}
							}
						}

						//System.out.println("This path : "+ displayArrayList.get(0).startStation+ " can be taken with optimal cost :"+ displayArrayList.get(0).pathCost);
						break;
					} else {
						filterStationsList.remove(i);
						i--;
					}
				}
			}
			else
			{
				System.out.println("Distance : infinity ");
				System.out.println("Route : none ");
			}
		} catch (Exception e) {
			System.out.println("Exception inside filterFinalSearchList():"+ e.getMessage());
		}
	}
	
	public static boolean checkIfConsistsEndStation(ArrayList<Stations> checkEndStations,ArrayList<Stations> checkStartStations)
	{
		try {
			for(int i=0;i<checkEndStations.size();i++)
			{
				if(checkEndStations.get(i).endStation.equals(destinationStation))
				{
					//System.out.println("checkIfConsistsEndStation:: End Station present");
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			System.out.println("Exception inside checkIfConsistsEndStation() :"+e.getMessage());
		}
		return false;
	}

}
