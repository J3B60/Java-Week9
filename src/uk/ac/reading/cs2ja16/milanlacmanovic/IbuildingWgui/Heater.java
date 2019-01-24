package uk.ac.reading.cs2ja16.milanlacmanovic.IbuildingWgui;

import java.awt.Point;

import javafx.scene.image.Image;

public class Heater extends BuildingObject {
	private double sourceTemp = 25; 
//	Heater(){
//		objectID++;//ToTest
//		objectImage = new Image(getClass().getResourceAsStream("Radiator.jpg"));
//		objectName = "Radiator - " + String.valueOf(objectID);
//		objectPosition = new Point(9,5);//CURRENT DEFAULT FOR NOW
//	}
	
	Heater(Point Random){
		objectID++;//ToTest
		objectImage = new Image(getClass().getResourceAsStream("Heater.png"));
		objectName = "Heater - " + String.valueOf(objectID);
		objectPosition = Random;//CURRENT DEFAULT FOR NOW
	}
	
	public void presentGUI(BuildingGUI bg) {
		bg.drawObject(getImage(), getXPosition(), getYPosition());	
	}

	public void DrawInGUI(BuildingGUI bg) {	
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

	@Override
	public void Activate(BuildingInterface bi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void check(BuildingInterface bi) {//Replace Activate too
		if (bi.allBuildings.get(bi.getCurrentBuildingIndex()).checkTemp() < 23) {
			bi.allBuildings.get(bi.getCurrentBuildingIndex()).changeTemp(sourceTemp);
		}
	}

}
