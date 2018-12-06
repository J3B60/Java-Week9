package uk.ac.reading.cs2ja16.milanlacmanovic.IbuildingWgui;

import java.awt.Point;

import javafx.scene.image.Image;

public class SmokeDetector extends BuildingObject {
	
	
	SmokeDetector(){
		objectID++;//ToTest
		objectImage = new Image(getClass().getResourceAsStream("Smoke-Detector.png"));
		objectName = "Smoke Detector - " + String.valueOf(objectID);
		objectPosition = new Point(4,5);//CURRENT DEFAULT FOR NOW
	}
	
	public void presentGUI(BuildingGUI bg){
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
		return "";
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
	
	public void detectFire(){
		//Have some sort of flashing/ change the image some how or add an alert to the GUI/Sound/
	}
	
}
