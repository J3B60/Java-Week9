package uk.ac.reading.cs2ja16.milanlacmanovic.IbuildingWgui;

import java.awt.Point;
import java.util.Random;

import javafx.scene.image.Image;

public class Lift extends BuildingObject {
	Random rgen = new Random();
//	Lift(){
//		objectID++;//ToTest
//		objectImage = new Image(getClass().getResourceAsStream("Elevator.png"));
//		objectName = "Lift - " + String.valueOf(objectID);
//		objectPosition = new Point(7,1);//CURRENT DEFAULT FOR NOWb
//		
//	}
	
	Lift(Point Random){
		objectID++;//ToTest
		objectImage = new Image(getClass().getResourceAsStream("Elevator.png"));
		objectName = "Lift - " + String.valueOf(objectID);
		objectPosition = Random;//CURRENT DEFAULT FOR NOWb
		
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
	public void liftPerson(){
		
	}

	@Override
	public void Activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void check(BuildingInterface bi) {
		int PersonNumber = -1;
		int ULiftindex = -1;
		int DLiftindex = -1;
		if (bi.allBuildings.size()>1) {
			for(int i = 0; i < bi.allBuildings.get(bi.getCurrentBuildingIndex()).getAllPeople().size(); i++) {
				if (bi.allBuildings.get(bi.getCurrentBuildingIndex()).getAllPeople().get(i).getPersonPositionX() == getXPosition() && bi.allBuildings.get(bi.getCurrentBuildingIndex()).getAllPeople().get(i).getPersonPositionY() == getYPosition()) {//Person positions to be at Lift and that there is a floor above or below
					PersonNumber = i;
					break;
				}
			}
			if(PersonNumber != -1) {
				if(bi.getCurrentBuildingIndex()-1 >= 0) {//check if there is floor below
					for(int j = 0; j < bi.allBuildings.get(bi.getCurrentBuildingIndex()-1).getAllBuildingObjects().size(); j++) {
						if(bi.allBuildings.get(bi.getCurrentBuildingIndex()-1).getAllBuildingObjects().get(j).getClass().equals(this.getClass())){
							DLiftindex = j;
							break;
						}
					}
				}
				//else {}DLiftIndex= -1
				if(bi.getCurrentBuildingIndex()+1 < bi.allBuildings.size()) {//check if there is floor above
					for(int k = 0; k < bi.allBuildings.get(bi.getCurrentBuildingIndex()+1).getAllBuildingObjects().size(); k++) {
						if(bi.allBuildings.get(bi.getCurrentBuildingIndex()+1).getAllBuildingObjects().get(k).getClass().equals(this.getClass())){
							ULiftindex = k;
							break;
						}
					}
				}
				//else{} ULiftindex = -1
				if(ULiftindex != -1 && DLiftindex != -1) {//Both true and found
					switch(rgen.nextInt(2)) {
					case 0://FloorBelow
						bi.allBuildings.get(bi.getCurrentBuildingIndex()).deletePerson(PersonNumber);
						//bi.allBuildings.get(bi.getCurrentBuildingIndex()-1).addPersonInPos(bi.allBuildings.get(bi.getCurrentBuildingIndex()-1).getAllBuildingObjects().get(DLiftindex).getXPosition(), bi.allBuildings.get(bi.getCurrentBuildingIndex()-1).getAllBuildingObjects().get(DLiftindex).getYPosition());
						bi.allBuildings.get(bi.getCurrentBuildingIndex()-1).addPerson();//Changed to random position since the person could get stuck going back an forth in the elevators
						break;
					case 1://FloorAbove
						bi.allBuildings.get(bi.getCurrentBuildingIndex()).deletePerson(PersonNumber);
						//bi.allBuildings.get(bi.getCurrentBuildingIndex()+1).addPersonInPos(bi.allBuildings.get(bi.getCurrentBuildingIndex()+1).getAllBuildingObjects().get(ULiftindex).getXPosition(), bi.allBuildings.get(bi.getCurrentBuildingIndex()+1).getAllBuildingObjects().get(ULiftindex).getYPosition());
						bi.allBuildings.get(bi.getCurrentBuildingIndex()+1).addPerson();
						break;
					default:
						break;
					}
				}
				else if(ULiftindex != -1 && DLiftindex == -1) {//Above Floor with Lift
					bi.allBuildings.get(bi.getCurrentBuildingIndex()).deletePerson(PersonNumber);//+1 because of -1 offset in deletePerson function
					//bi.allBuildings.get(bi.getCurrentBuildingIndex()+1).addPersonInPos(bi.allBuildings.get(bi.getCurrentBuildingIndex()+1).getAllBuildingObjects().get(ULiftindex).getXPosition(), bi.allBuildings.get(bi.getCurrentBuildingIndex()+1).getAllBuildingObjects().get(ULiftindex).getYPosition());
					bi.allBuildings.get(bi.getCurrentBuildingIndex()+1).addPerson();
				}
				else if(ULiftindex == -1 && DLiftindex != -1) {//Below Floor with Lift
					bi.allBuildings.get(bi.getCurrentBuildingIndex()).deletePerson(PersonNumber);//+1 because of -1 offset in deletePerson function
					//bi.allBuildings.get(bi.getCurrentBuildingIndex()-1).addPersonInPos(bi.allBuildings.get(bi.getCurrentBuildingIndex()-1).getAllBuildingObjects().get(DLiftindex).getXPosition(), bi.allBuildings.get(bi.getCurrentBuildingIndex()-1).getAllBuildingObjects().get(DLiftindex).getYPosition());
					bi.allBuildings.get(bi.getCurrentBuildingIndex()-1).addPerson();
				}
			}
		}
	}
}
