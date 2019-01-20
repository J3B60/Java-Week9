package uk.ac.reading.cs2ja16.milanlacmanovic.IbuildingWgui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * Building Holds the information of the 
 * building its rooms, the occupant and the random generator
 * @author milan
 *
 */

public class Building {
	private int xSize = 10;				// size of building in x
	private int ySize = 10;				// and y
	private ArrayList<Room> allRooms;	// array of rooms
	private Random randGen;				//Random Generator using Random obj
	//private int randRoom;				//Random Room Number
	//public Person occupant;			//Person point P on BuildingDraw
	private String OriginalInput; //Original input string for building Constructor to be able to call back for saveing in the building interface
	private ArrayList<BuildingObject> allBuildingObjects;
//	public BuildingObject smokeD;//Temporary !!!!!!!!!!
//	public BuildingObject smartLB;//TEMP
//	public BuildingObject smartLift;//TEMP
//	public BuildingObject motionsens;//TEMP
//	public BuildingObject aircon;//Temo
	private ArrayList<Person> allPeople;
	private double temperature = 17;
	
	/**
	 * Building Constructor which initialises the Rooms arraylist, Random generator
	 *  and Person, sets up the building calling the setBuilding method.
	 *  Next Path Points also called to be added to the PointPath arraylist in person
	 * @param bS Input string to make Building [Bx By; R1x1 R1y1 R1x2 R1y2 R1Dx R1Dy;...]
	 */
	
	Building(String bS){
		allRooms = new ArrayList<Room>();
		allRooms.clear(); //Arraylist SETUP
		allBuildingObjects = new ArrayList<BuildingObject>();
		allBuildingObjects.clear(); //SETUP
		allPeople = new ArrayList<Person>();
		allPeople.clear(); //SETUP
		
		OriginalInput = bS; //Input saved
		setBuilding(bS); //Setup the building
		//System.out.println(allRooms);//Test
		randGen = new Random(); //Random generator
		
		//occupant = new Person(allRooms.get(randRoom).getRandom(randGen)); // Occupant start at random point
		
		//smokeD = new SmokeDetector();//TEMP
		//smartLB = new LightBulb();//TEMP
		//smartLift = new Lift();//TEMP
		//aircon = new AirConditioner();//Temp
		//motionsens = new MotionSensor();//TEMP
		//occupant.PointSet(allRooms.get(PersonInRoom()-1).getDoorInsidePoint(allRooms.get(PersonInRoom()-1).getDoorPositionRelativetoRoom())); //Set the Persons first point
		allPeople.add(new Person(allRooms.get(RoomRandomSelect()).getRandom(randGen)));
//USES POINT SET	//allPeople.get(0).PointSet(allRooms.get(PersonInRoom()-1).getDoorInsidePoint(allRooms.get(PersonInRoom()-1).getDoorPositionRelativetoRoom())); //Set the Persons first point
		nextPathPoint(); //Add the list of Point paths for person to follow next TOFIX
		allBuildingObjects.add(new SmokeDetector());
		allBuildingObjects.add(new LightBulb());
		allBuildingObjects.add(new Lift());
		allBuildingObjects.add(new AirConditioner());
		allBuildingObjects.add(new MotionSensor());
	}
	
	/**
	 * Returns the Room that the Person is in as an integer, -1 if not in room
	 * @return
	 */
	
	public int PersonInRoom(int indexPerson) { /////###Can be changed to have a point argument
		for (int i = 0; i < allRooms.size(); i++) {//Loop to check all rooms
			if (allRooms.get(i).isInRoom(allPeople.get(indexPerson).getPersonPoint())) {//if in a room return room number (The position in array)
				return i + 1; //Rooms start from 1 (not 0)
			}
		}
		return -1; //If not found then return -1
	}
	
	/**
	 * Sets up the building by extracting Building x,y and rooms from input string
	 * Building x,y go into dedicated integer variables
	 * Rooms are put into Room arraylist
	 * @param bS input string from constructor
	 */
	
	public void setBuilding(String bS) {
		StringSplitter S = new StringSplitter(bS, ";"); //Splits between sets of co-ordinates 
		StringSplitter SS = new StringSplitter(S.getStrings()[0], " "); //Splits first co-ordniates again using spaces
		xSize = SS.getIntegers()[0]; //gets and assign the integer x building size
		ySize = SS.getIntegers()[1]; //gets and assign the integer y building size
		//System.out.println(ySize); //Test
		allRooms.clear();
		for (int i = 1; i < S.getStrings().length; i++){
			allRooms.add(new Room(S.getStrings()[i])); //put each separate room as new Room obj in arraylist
		}
		//System.out.println(allRooms);//Test
	}
	
	/**
	 * Getter for the Building x coordinate
	 * @return
	 */
	
	public int getBuildingx() { //Return the Building x dimension
		return xSize;
	}
	
	/**
	 * Getter for the Building y coordinate
	 * @return
	 */
	
	public int getBuildingy() { //Return the Building y dimension
		return ySize;
	}
	
	public void setBuildingx(int x) {
		xSize = x;
	}
	
	public void setBuildingy(int y) {
		ySize = y;
	}
	
	/**
	 * Outputs Building object information as a string
	 */
	
	public String toString() {
		String temp = "";
		temp += "Building size " + xSize + "," + ySize + "\n";
		//temp += "Room from " + allRooms.toString();//Test
		for (Room r: allRooms) temp = temp + "Room from " + r.toStringRoomX() + " to " + r.toStringRoomY() + " door at " + r.toStringRoomDoor() + "\n";
		temp += "Occupant is in Room: ";
		for (int i = 0; i<allPeople.size(); i++) {//Get info for each person
			temp += PersonInRoom(i) + "\n";
		}
		return temp;
	}
	
	/**
	 * Selects a random room for occupant to start
	 */
	
	public int RoomRandomSelect() {
		return randGen.nextInt(allRooms.size());
	}
	
	/**
	 * Passes all rooms and occupant to BuildingInterface for drawing in BuildingDraw
	 * @param bi
	 */
	
	public void showBuilding (BuildingInterface bi) {
		for (Room r: allRooms) r.showRoom(bi);
		allPeople.get(0).showPerson(bi);
		//for (BuildingObject bo : allBuildingObjects) bo.presentGUI();
	}
	
	/**
	 * Passes building interface to Person so Person has a 
	 * map of Building and decides how they can move within the building
	 * @param bi
	 */
	
	public void movePersoninBuilding(BuildingInterface bi) {
		allPeople.get(0).movePerson(bi);//tell Person class to move and the person can know how to move because it has BuildngDraw
	}
	
	/**
	 * A Check if the Person has reached their destination passed to the Building Interface
	 * @return
	 */
	
	public boolean CheckPersonReachedDestination() {
		return allPeople.get(0).DestinationReached(); //Passed to the building Interface
	}
	
	/**
	 * Adds all the point required for the path to the Person PointPath arraylist (this can be edited to be point by point if required by using an index in building)
	 */
	
	public void nextPathPoint() {
		Point temp = new Point(0,0);
//		temp.setLocation((int)allRooms.get(0).getDoorPoint().getX()+1, (int)allRooms.get(1).getDoorPoint().getY());
//		occupant.addPointPath(temp);
//		temp.setLocation((int)allRooms.get(1).getDoorPoint().getX(), (int)allRooms.get(1).getDoorPoint().getY()-1);
//		occupant.addPointPath(temp);
		//#########################################################################OLDPathDefinedonTaskSheet
//		allPeople.get(0).addPointPath(allRooms.get(PersonInRoom()-1).getDoorOutsidePoint(allRooms.get(PersonInRoom()-1).getDoorPositionRelativetoRoom()));
//		/////////////////////////////////////////////////////////////////////////////
//		allPeople.get(0).addPointPath(allRooms.get(1).getDoorOutsidePoint(allRooms.get(1).getDoorPositionRelativetoRoom()));
//		allPeople.get(0).addPointPath(allRooms.get(1).getDoorInsidePoint(allRooms.get(1).getDoorPositionRelativetoRoom()));
//		allPeople.get(0).addPointPath(allRooms.get(1).getRandom(randGen));
//		allPeople.get(0).addPointPath(allRooms.get(1).getDoorInsidePoint(allRooms.get(1).getDoorPositionRelativetoRoom()));
//		allPeople.get(0).addPointPath(allRooms.get(1).getDoorOutsidePoint(allRooms.get(1).getDoorPositionRelativetoRoom()));
//		////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		allPeople.get(0).addPointPath(allRooms.get(2).getDoorOutsidePoint(allRooms.get(2).getDoorPositionRelativetoRoom()));
//		allPeople.get(0).addPointPath(allRooms.get(2).getDoorInsidePoint(allRooms.get(2).getDoorPositionRelativetoRoom()));
//		allPeople.get(0).addPointPath(allRooms.get(2).getRandom(randGen));
		//##########################################################################
//		temp.setLocation((int)allRooms.get(1).getDoorPoint().getX(), (int)allRooms.get(1).getDoorPoint().getY()+1);
//		occupant.addPointPath(temp);
//		temp.setLocation((int)allRooms.get(1).getDoorPoint().getX(), (int)allRooms.get(1).getDoorPoint().getY()-1);
//		occupant.addPointPath(temp);
//		temp.setLocation((int)allRooms.get(2).getDoorPoint().getX()-1, (int)allRooms.get(1).getDoorPoint().getY());
//		occupant.addPointPath(temp);
//		occupant.addPointPath(allRooms.get(2).getDoorPoint());
//		occupant.addPointPath(allRooms.get(2).getRandom(randGen));
		int randomAction = ActionRandomSelect();
		int randomPerson = PersonRandomSelect();
		int randomRoom = RoomRandomSelect();
		switch (randomAction) {
			case 0: //Leave current Room and enter next
				//Get to this rooms inner side door and outer side door
				allPeople.get(randomPerson).addPointPath(allRooms.get(PersonInRoom(randomPerson)-1).getDoorInsidePoint(allRooms.get(PersonInRoom(randomPerson)-1).getDoorPositionRelativetoRoom()));
				allPeople.get(randomPerson).addPointPath(allRooms.get(PersonInRoom(randomPerson)-1).getDoorOutsidePoint(allRooms.get(PersonInRoom(randomPerson)-1).getDoorPositionRelativetoRoom()));
				//Get to Next Room inner side door and outer side door
				allPeople.get(randomPerson).addPointPath(allRooms.get(randomRoom).getDoorOutsidePoint(allRooms.get(randomRoom).getDoorPositionRelativetoRoom()));
				allPeople.get(randomPerson).addPointPath(allRooms.get(randomRoom).getDoorInsidePoint(allRooms.get(randomRoom).getDoorPositionRelativetoRoom()));
				break;
			default: //Random point in same Room
				allPeople.get(randomPerson).addPointPath(allRooms.get(PersonInRoom(randomPerson)-1).getRandom(randGen));
				allPeople.get(randomPerson).addPointPath(allRooms.get(PersonInRoom(randomPerson)-1).getRandom(randGen));
				allPeople.get(randomPerson).addPointPath(allRooms.get(PersonInRoom(randomPerson)-1).getRandom(randGen));
				allPeople.get(randomPerson).addPointPath(allRooms.get(PersonInRoom(randomPerson)-1).getRandom(randGen));
				allPeople.get(randomPerson).addPointPath(allRooms.get(PersonInRoom(randomPerson)-1).getRandom(randGen));
				allPeople.get(randomPerson).addPointPath(allRooms.get(PersonInRoom(randomPerson)-1).getRandom(randGen));
				break;
		}
	}
	
	private int ActionRandomSelect() {
		return randGen.nextInt(2);
	}
	
	private int PersonRandomSelect() {
		return randGen.nextInt(allPeople.size());
	}
	
	
	/**
	 * Sent to the building interface to stop calling animate/path if th person has reached
	 * final destination
	 * @return
	 */
	
	public boolean PersonCompletePath() {//now not for more than one person
		return allPeople.get(0).CompletePath();//Works as is, sent to BuildingInterface
	}
	
	/**
	 * Gets the Original string input to Building Constructor to be able to take current 
	 * Building and save to file
	 * @return
	 */
	
	public String getOriginalInput() {
		return OriginalInput; //Building Constructor String Input
	}
	/**
	 * get list of uilding objects
	 * @return allBuildingObjects
	 */
	public ArrayList<BuildingObject> getAllBuildingObjects(){
		return allBuildingObjects;
	}
	/**
	 * 	gets a copy of allRooms in building
	 * @return allRooms
	 */
	public ArrayList<Room> getAllRooms(){
		return allRooms;
	}
	
	public ArrayList<Person> getAllPeople(){
		return allPeople;
	}
	/**
	 * Building test main
	 * @param args
	 */
	
	public void addRoom(String dim) {
		StringSplitter s = new StringSplitter(dim, " ");
		if (s.getIntegers()[0] <= xSize && s.getIntegers()[2] <= xSize && s.getIntegers()[1] <= ySize && s.getIntegers()[3] <= ySize) {//Would need more checks such as rooms, so that they dont overlap, check that person is outside of new room area to prevent problems, such as PersonInRoom or making room wall ontop of person.//Can be moved to Room class, would need to pass building size, people and other rooms for checks
			allRooms.add(new Room(dim));
		}
		else {
			JOptionPane.showMessageDialog(null, "Invalid Room", "Error",  JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void deletePerson(int i) {
		if (i+1 <= allPeople.size() && i>0) {
			allPeople.remove(i);
		}
		else {
			JOptionPane.showMessageDialog(null, "Person does not exist, please enter valid Person Number", "Error",  JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void deleteRoom(int i) {
		if (i+1 <= allRooms.size() && i>0) {
			allRooms.remove(i);
		}
		else {
			JOptionPane.showMessageDialog(null, "Room does not exist, please enter valid Room Number", "Error",  JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void deleteObject(int i) {
		if (i+1 <= allBuildingObjects.size() && i>0) {
			allBuildingObjects.remove(i);
		}
		else {
			JOptionPane.showMessageDialog(null, "Object does not exist, please enter valid Object ID Number", "Error",  JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void addObject(String s) {//TODO change to not be in default POS allBO.get(last) {which is the new object} then .setXPos(), .setYPos() <- come from JOption Input, if ok set, if cancelled JOption then in default position {default position will cause problems}
		switch (s) {
		case "Air Conditioner":
			allBuildingObjects.add(new AirConditioner());
			break;
		case "Heater":
			allBuildingObjects.add(new Heater());
			break;
		case "Lift":
			allBuildingObjects.add(new Lift());
			break;
		case "Light Bulb":
			allBuildingObjects.add(new LightBulb());
			break;
		case "Motion Sensor":
			allBuildingObjects.add(new MotionSensor());
			break;
		case "Smoke Detector":
			allBuildingObjects.add(new SmokeDetector());
			break;
		case "Window":
			allBuildingObjects.add(new Window());
			break;
		default:
			JOptionPane.showMessageDialog(null, "Bad Object, Should not happen, nothing added", "Error",  JOptionPane.ERROR_MESSAGE);
			break;
		}
	}
	
//	public static void main(String[] args) {
//		Building b = new Building("11 11;0 0 4 4 2 4;6 0 10 10 6 5;0 6 4 10 2 6"); 
//		System.out.println(b.toString());
//	}
}
