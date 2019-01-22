package uk.ac.reading.cs2ja16.milanlacmanovic.IbuildingWgui;

import java.awt.Point;

import javafx.scene.image.Image;

public class Window extends BuildingObject {

	Window(){
		objectID++;//ToTest
		//NO IMAGE make it a blue line
		objectName = "Window - " + String.valueOf(objectID);
		objectPosition = new Point(7,1);//CURRENT DEFAULT FOR NOWb
	}
	
	public void presentGUI(BuildingGUI bg) {
		bg.drawObject(getImage(), getXPosition(), getYPosition());	
	}

	public int getID(){
		return objectID;
	}
	public Image getImage(){
		return objectImage;
	}
	public void setImage(Image pobjectImage){
		objectImage = pobjectImage;
	}
	public void setName(String pobjectName){
		objectName= pobjectName;
	}
	public String getName(){
		return objectName;
	}
	public String toString(){
		return objectName + " (" + String.valueOf(getXPosition()) + "," + String.valueOf(getYPosition()) + ")";
	}
	public Point getPosition(){
		return objectPosition;
	}
	
	public int getXPosition(){//NOTE DOUBLE FOR NOW FOR FINER CONTROL
		return (int) objectPosition.getX();
	}
	
	public int getYPosition(){
		return (int) objectPosition.getY();
	}
	
	public void setPosition(Point pobjectPosition){
		objectPosition = pobjectPosition;
	}

	public int objectInRoom(Building myBuilding) {
		for (int i = 0; i < myBuilding.getAllRooms().size(); i++) {//Loop to check all rooms
			if (myBuilding.getAllRooms().get(i).isInRoom(objectPosition)) {//if in a room return room number (The position in array)
				return i; //NOTE THIS IS DIFFERENT Start from 0
			}
		}
		return -1; //If not found then return -1
	}
	//Day -> then Light in Room

	@Override
	public void Activate() {
		// TODO Auto-generated method stub
		
	}

}
