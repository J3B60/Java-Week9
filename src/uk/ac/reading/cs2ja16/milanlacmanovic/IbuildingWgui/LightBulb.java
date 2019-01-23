package uk.ac.reading.cs2ja16.milanlacmanovic.IbuildingWgui;


import java.awt.Point;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class LightBulb extends BuildingObject {
	LightBulb(){
		objectID++;//ToTest
		objectImage = new Image(getClass().getResourceAsStream("Light-bulb.png"));
		objectName = "Light Bulb - " + String.valueOf(objectID);
		objectPosition = new Point(9,5);//CURRENT DEFAULT FOR NOW
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
	private void drawLight(BuildingGUI bg, Building myBuilding) {
		bg.gc.setFill(Color.YELLOW);
		bg.gc.fillRect(myBuilding.getAllRooms().get(objectInRoom(myBuilding)).getDoorCoords()[0], myBuilding.getAllRooms().get(objectInRoom(myBuilding)).getDoorCoords()[1], myBuilding.getAllRooms().get(objectInRoom(myBuilding)).getDoorCoords()[2], myBuilding.getAllRooms().get(objectInRoom(myBuilding)).getDoorCoords()[3]);
		//NEED TO ADD slightly off centre because of line thickness, NEED RATIO bg.getRatio()
	}
	public void Activate(){
		//drawLight();
	}

	@Override
	public void check(Building b) {
		// TODO Auto-generated method stub
		
	}
}
