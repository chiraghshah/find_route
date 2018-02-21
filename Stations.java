/**
 * 
 * @author Chirag Shah..
 *
 */
public class Stations implements Comparable<Stations> {
String startStation;
String endStation;
int pathCost;

public Stations(String startStation, String endStation, int pathCost)
{
	this.startStation= startStation;
	this.endStation = endStation;
	this.pathCost=pathCost;
}

@Override
public int compareTo(Stations s)
{
    return this.pathCost - s.pathCost;     //Sorts the objects in ascending order
     
    // return s.id - this.id;    //Sorts the objects in descending order
}
@Override
public String toString()
{
    return "{"+startStation+","+endStation+","+pathCost+"}";
}
public String getStartStation() {
	return startStation;
}

public void setStartStation(String startStation) {
	this.startStation = startStation;
}

public String getEndStation() {
	return endStation;
}

public void setEndStation(String endStation) {
	this.endStation = endStation;
}

public int getPathCost() {
	return pathCost;
}

public void setPathCost(int pathCost) {
	this.pathCost = pathCost;
}

}
