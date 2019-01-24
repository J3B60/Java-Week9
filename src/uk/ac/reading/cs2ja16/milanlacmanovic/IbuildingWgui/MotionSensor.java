package uk.ac.reading.cs2ja16.milanlacmanovic.IbuildingWgui;

import java.awt.Point;

import javafx.scene.image.Image;

public class MotionSensor extends BuildingObject{
//	private Boolean powerSwitch = false;
//	MotionSensor(){
//		objectID++;//ToTest
//		objectImage = new Image(getClass().getResourceAsStream("motiondetect.png"));
//		objectName = "Motion Detector - " + String.valueOf(objectID);
//		objectPosition = new Point(7,5);//CURRENT DEFAULT FOR NOW
//	}
	
	MotionSensor(Point Random){
		objectID++;//ToTest
		objectImage = new Image(getClass().getResourceAsStream("motiondetect.png"));
		objectName = "Motion Detector - " + String.valueOf(objectID);
		objectPosition = Random;//CURRENT DEFAULT FOR NOW
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
				return i + 1; //Rooms start from 1 (not 0)
			}
		}
		return -1; //If not found then return -1
	}
	/**
	 * If person is in the same room as Motion Sensor then turn ON sensor else OFF
	 * @param myBuilding - Passed to get rooms and person
	 */
	private void sense(Building myBuilding) {
		for (int i = 0; i < myBuilding.getAllPeople().size(); i++) {
			if (myBuilding.PersonInRoom(i) == objectInRoom(myBuilding)){
				for (int j = 0; j < myBuilding.getAllBuildingObjects().size(); j++){
					myBuilding.getAllBuildingObjects().get(j).Activate();
				}
			}
		}
	}

	@Override
	public void Activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void check(BuildingInterface bi) {
		for (int index = 0; index < bi.allBuildings.get(bi.getCurrentBuildingIndex()); index++
		bi.allBuildings.get(bi.getCurrentBuildingIndex()).getAllPeople().get(index)
		
	}
	
}
