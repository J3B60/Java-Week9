package uk.ac.reading.cs2ja16.milanlacmanovic.IbuildingWgui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This class deals with rooms, it takes string input co-ordinates which 
 * include a door and converts to a integer array using string splitter.
 * It can also determine whether a point is inside the room.
 * @author milan
 *
 */

public class Room{
	private int[] roomCoord = new int[6]; //Room Co-ordinates from user input including door
	/**
	 * Creates Splitter object temporarily to get an integer array for
	 * the room from a string input
	 * @param StringSplitter temporary object
	 */
	Room (String S){
		StringSplitter SS = new StringSplitter(S, " "); //String Splitter for user input
		roomCoord = SS.getIntegers(); //Integer Array from string splitter copied
	}
	/**
	 * Returns the information in the Room co-ordinate array as a String
	 * @return roomCoord as String
	 */
	public String toString(){
		return Arrays.toString(roomCoord); //return Array as String
	}
	/**
	 * 
	 * @return return 1st set of coordinates of the room as string
	 */
	public String toStringRoomX() {
		return String.valueOf(roomCoord[0]) + "," + String.valueOf(roomCoord[1]);
	}
	/**
	 * 
	 * @return return 2nd set of coordinates of the room as string
	 */
	
	public String toStringRoomY() {
		return String.valueOf(roomCoord[2]) + "," + String.valueOf(roomCoord[3]);
	}
	/**
	 * 
	 * @return return the door coordinates of the room as string
	 */
	public String toStringRoomDoor() {
		return String.valueOf(roomCoord[4]) + "," + String.valueOf(roomCoord[5]);
	}
	/**
	 * Polymorphed toString to return whether the input point is within the input room
	 * 
	 * @param P is the input Point
	 * @param isInRoomResult is boolean result from running function isInRoom 
	 * @return statement saying if the point is/not in room
	 */
	public String toString(Point P, boolean isInRoomResult){
		if (isInRoomResult == true){
			return (int)P.getX() + ", " + (int)P.getY() + " is in the room"; //getsPoint coords from Point object
		}
		else{
			return (int)P.getX() + ", " + (int)P.getY() + " is not in the room";
		}
		
	}
	/**
	 * Returns a boolean if the point is/not in room 
	 * @param P
	 * @return boolean from if statement
	 */
	public boolean isInRoom(Point P){
		if ((int)P.getX() > roomCoord[0] && (int)P.getX() < roomCoord[2] && (int)P.getY() > roomCoord[1] && (int)P.getY() < roomCoord[3]){ //if its within room boundaries >^v<
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 *  Finds a random point within the room and returns the point
	 * @param randGen Input the random generator from building
	 * @return
	 */
	public Point getRandom(Random randGen) {
		int x;
		int y;
		x = roomCoord[0] + 1 + randGen.nextInt(roomCoord[2] - roomCoord[0] - 2);//Random x
		y = roomCoord[1] + 1 + randGen.nextInt(roomCoord[3] - roomCoord[1] - 2);//Random y
		Point RandomPoint = new Point(x,y);//Random Temp point made
		return RandomPoint;//Return
	}
	
	/**
	 * Used by Building Interface in BuildingDraw to be able to draw rooms and the door
	 * @param bi
	 */
	
	public void showRoom(BuildingInterface bi) {
		bi.showWall(roomCoord[0], roomCoord[3], roomCoord[2], roomCoord[3]);//Uses Coord Combinations that are corners of the room
		bi.showWall(roomCoord[0], roomCoord[1], roomCoord[2], roomCoord[1]);
		bi.showWall(roomCoord[0], roomCoord[1], roomCoord[0], roomCoord[3]);
		bi.showWall(roomCoord[2], roomCoord[1], roomCoord[2], roomCoord[3]);
		bi.showDoor(roomCoord[4], roomCoord[5]);//Creates gap in wall where door should be in BuildingDraw
	}
	
	/**
	 * Return Door as Point rather than integers
	 * @return Door as Point
	 */
	
	public Point getDoorPoint() {
		Point temp = new Point(roomCoord[4], roomCoord[5]);
		return temp;
	}
	
	/**
	 * Identifies where the door is relative to the room so that 
	 * the inside door and outside door can be calculated for path
	 * #Potentially useful for drawing horizontal/Vertical doors in GUI
	 * @return an integer which is ID which is recognised by 
	 * getDoorInsidePoint and getDoorOutsidePoint
	 */
	
	public int getDoorPositionRelativetoRoom() {
		if (roomCoord[4] == roomCoord[0]) {
			return 1;//west side
		}
		if (roomCoord[4] == roomCoord[2]) {
			return 2;//East Side
		}
		if (roomCoord[5] == roomCoord[1]) {
			return 3;//South Side
		}
		if (roomCoord[5] == roomCoord[3]) {
			return 4;//North Side
		}
		else {
			return 0; //a real door Point Should not reach this point otherwise input is not valid
		}
	}
	/**
	 * Finds the Point just before door from inside room which
	 *  is used by Building when adding path points to persons 
	 *  arraylist >v^<
	 * @param DoorRelativePositiontoRoom ID integer 
	 * @return
	 */
	
	public Point getDoorInsidePoint(int DoorRelativePositiontoRoom) {
		Point temp;
		if (DoorRelativePositiontoRoom == 1) {
			temp = new Point((int)getDoorPoint().getX()+1, (int)getDoorPoint().getY());
			return temp;
		}
		else if (DoorRelativePositiontoRoom == 2) {
			temp = new Point((int)getDoorPoint().getX()-1, (int)getDoorPoint().getY());
			return temp;
		}
		else if (DoorRelativePositiontoRoom == 3) {
			temp = new Point((int)getDoorPoint().getX(), (int)getDoorPoint().getY()+1);
			return temp;
		}
		else if (DoorRelativePositiontoRoom == 4) {
			temp = new Point((int)getDoorPoint().getX(), (int)getDoorPoint().getY()-1);
			return temp;
		}
		else {
			temp = getDoorPoint();//ERROR, Better than nothing :( not good error handling but good enough
			return temp;
		}
	}
	/**
	 * Same as getDoorInsidePoint but finds points points just outside the door
	 * @param DoorRelativePositiontoRoom
	 * @return
	 */
	
	public Point getDoorOutsidePoint(int DoorRelativePositiontoRoom) {
		Point temp;
		if (DoorRelativePositiontoRoom == 1) {
			temp = new Point((int)getDoorPoint().getX()-1, (int)getDoorPoint().getY());
			return temp;
		}
		else if (DoorRelativePositiontoRoom == 2) {
			temp = new Point((int)getDoorPoint().getX()+1, (int)getDoorPoint().getY());
			return temp;
		}
		else if (DoorRelativePositiontoRoom == 3) {
			temp = new Point((int)getDoorPoint().getX(), (int)getDoorPoint().getY()-1);
			return temp;
		}
		else if (DoorRelativePositiontoRoom == 4) {
			temp = new Point((int)getDoorPoint().getX(), (int)getDoorPoint().getY()+1);
			return temp;
		}
		else {
			temp = getDoorPoint();//ERROR, Better than nothing :( not good error handling but good enough
			return temp;
		}
	}
	public int[] getDoorCoords() {
		return roomCoord;
	}
	/**
	 * Used for testing Room class
	 * @param args
	 */
//	public static void main(String[] args) {
//		Point P = new Point(7,9); //Create test Point object
//		// Note to self:(int) P.getX() is an integer returning x coord of Point
//		Room r = new Room("0 0 5 5 0 2"); //Create Test Room
//		System.out.println(r.toString(P, r.isInRoom(P))); //output to console if point is in room statement
//	}
}

