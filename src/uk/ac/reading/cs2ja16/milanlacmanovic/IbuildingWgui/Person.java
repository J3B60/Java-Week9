package uk.ac.reading.cs2ja16.milanlacmanovic.IbuildingWgui;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Person class holds information about the person inside the building
 * and its path which all use point objects. Person can be defined using either
 * integers x,y or Point
 * @author milan
 *
 */

public class Person {
	static int PersonID;
	private Point PersonPosition; //Person point
	private ArrayList<Point> PointPath; //Persons Path List
	private int index=0; //Index for the PointPath ArrayList
	private Boolean PathisCompleted = false; //For Animate&Path Loop in building Interface CURRENTLY NOT USED
	
	/**
	 * sets the Person's Position as point using inputs integer x,y
	 * @param x
	 * @param y
	 */
	
	Person(int x, int y){//Should not be used
		PersonID++;
		PersonPosition = new Point(x,y); //Initialise the Point Object
		PointPath = new ArrayList<Point>();
		PointPath.clear();
		
	}
	
	/**
	 * This can be any point but is used with random generator sent by Building
	 * This can however can be used with any normal Point Object thought it is not
	 * used for this purpose
	 * @param random
	 */
	
	Person(Point random) {//Should not be used
		PersonID++;
		PersonPosition = random; //Assign Random Point position (from building class)
		PointPath = new ArrayList<Point>();
		PointPath.clear();
	}
	
	/**
	 * Getter for the Person Position as a Point object
	 * @return Person Position Point
	 */
	
	public Point getPersonPoint() {
		return PersonPosition; //Get Information of Person as a Point
	}
	
	/**
	 * Sets the Person Position point after the Person has been initialised
	 * using integer x, y to change Point
	 * @param x coordinate
	 * @param y coordinate
	 */
	
	public void setPersonPosition(int x, int y) {
		PersonPosition.setLocation(x, y); //Set X and Y
	}
	
	/**
	 * Getter for the x coordinate of Person Position as an integer 
	 * @return Person Position x Coordinate as integer
	 */
	
	public int getPersonPositionX() {
		return (int)PersonPosition.getX(); //Return X Coordinate of Person
	}
	
	/**
	 * Getter of y coordinate from Person Position Point as integer
	 * @return Person Position y Coordinate as integer
	 */
	
	public int getPersonPositionY() {
		return (int)PersonPosition.getY(); //Return Y Coordinate of person
	}
	
	/**
	 * Outputs information from Person as a String information
	 * being the Person position x and y
	 */
	public String toString(){
		return "" + (int)PersonPosition.getX() + "," + (int)PersonPosition.getY(); //Return PersonPosition Info as a String
	}
	
	/**
	 * adds to BuildingDraw array in the Building Interface the Person as P
	 * @param bi Building Interface
	 */
	
	public void showPerson(BuildingInterface bi) {
		bi.showIt((int)PersonPosition.getX(), (int)PersonPosition.getY(), 'P'); //(int) P.getX() is replaceable with getPersonPositionX() etc
	}
	
	public void showPersonGUI(BuildingGUI bg){
		bg.drawPerson((int)PersonPosition.getX(), (int)PersonPosition.getY());
	}
	
	/**
	 * Sets up the arraylist and sets the first point in the Persons Path by inputing a Point
	 * @param p Point
	 */
	
//	public void PointSet(Point p) {
//		PointPath = new ArrayList<Point>();
//		PointPath.clear();
//		PointPath.add(p);
//	}
	
	/**
	 * Moves Person by calculating the dx and dy. Then converts these values to
	 * -1,0 or 1 which is movex and movey. The function also checks if the move is
	 * legal (if a wall is in the way). It uses the input parameter Building Interface to
	 * get the BuildingDraw so the person has a map of the building preventing it to go 
	 * through walls. For the Person to move properly it must follow points to the door first
	 * otherwise the person will walk into the wall on a loop
	 * @param bi
	 */
	
	public boolean movePerson(BuildingInterface bi) {//boolean if the path has ended
		int dx = 0, dy = 0;
		int movex = 0, movey =0;
		if(index < PointPath.size()) {//If more points to visit continue else finished
			if (PersonPosition.getX() != PointPath.get(index).getX() && PersonPosition.getY() != PointPath.get(index).getY() ) {
				dx = (int) PersonPosition.getX() - (int) PointPath.get(index).getX();
				dy = (int) PersonPosition.getY() - (int) PointPath.get(index).getY();
				//Checks if space is empty to move into however diagonals screw it up, checks are only 
				//done horizontal and vertical, however person can move diagonal which is not checked for so the PointPath 
				//must be clearly defined to inside, outside door
				if (dx > 0 && bi.getBuildingDraw()[(int) PersonPosition.getX()][(int) PersonPosition.getY()+1] == ' ') {
					movex = -1;
				}
				else if (dx < 0 && bi.getBuildingDraw()[(int) PersonPosition.getX()+2][(int) PersonPosition.getY()+1] == ' '){
					movex = 1;
				}
				else {
					movex = 0;
				}
				if (dy > 0 && bi.getBuildingDraw()[(int) PersonPosition.getX()+1][(int) PersonPosition.getY()] == ' ') {
					movey = -1;
				}
				else if (dy < 0 && bi.getBuildingDraw()[(int) PersonPosition.getX()+1][(int) PersonPosition.getY()+2] == ' '){
					movey = 1;
				}
				else {
					movey = 0;
				}
				PersonPosition.translate(movex, movey);
				
			}
			else {
				index++;
			}
			return false;
		}
		else{
			index = 0;//Drop back for memory saving - don't need to remember path
			PointPath.clear();//Clear
			return true;//All points finished - more to be added
		}
	}
	
	/**
	 * Similar to Path completed however checks after each point and increments
	 * the index after the current point is reached
	 * @return
	 */
	
	public boolean DestinationReached() {
		if (index < PointPath.size()) { //// Need to fix out of bounds with PointPath arraylist and index
			if (PersonPosition.getX() == PointPath.get(index).getX() && PersonPosition.getY() == PointPath.get(index).getY()) {
				if (index < PointPath.size()) {
					index++;
				}
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	/**
	 * Checks whether the person has reached Final destination used for Path option 
	 * in the building interface. Animate uses this to check if at final destination however
	 * it does not loop through all points but rather one by one, move is step by step
	 * @return
	 */
	
//	public boolean CompletePath() {
//		if (PointPath.size() == index) {
//			return true;
//		}
//		else {
//			return false;
//		}
//	}
	
//	public void CheckDoorType(BuildingInterface bi) {
//		if (bi.getBuildingDraw()[(int)PointPath.get(index).getX() + 1 ][(int)PointPath.get(index).getY()] == '-' && bi.getBuildingDraw()[(int)PointPath.get(index).getX() - 1 ][(int)PointPath.get(index).getY()] == '-') {
//			//This is a horizontal Door
//		}
//		else if (bi.getBuildingDraw()[(int)PointPath.get(index).getX()][(int)PointPath.get(index).getY()+1] == '|' && bi.getBuildingDraw()[(int)PointPath.get(index).getX()][(int)PointPath.get(index).getY()-1] == '|') {
//			//This is a vertical Door
//		}
//		else{
//			//nothing, point is probably random
//		}
//	}
	
	/**
	 * Adds Points into the PointPath arraylist used for adding points after the
	 * initial point has been set can be altered and ultimately removed and replaced
	 * with the PointPath initialiser if the setPointPath is cleaned up to initialise the
	 * arraylist somewhere earlier 
	 * @param p
	 */
	
	public void addPointPath(Point p) {
		PointPath.add(p);
	}
	
	/**
	 * Currently unused, similar idea to CompletePath but different implementation
	 * @return
	 */
	
//	public Boolean getPathisCompleted() {
//		return PathisCompleted;
//	}
	
//	public void presentGUI(BuildingGUI bg) {
//		bg.drawObject(getImage(), getXPosition(), getYPosition());	
//	}
}
