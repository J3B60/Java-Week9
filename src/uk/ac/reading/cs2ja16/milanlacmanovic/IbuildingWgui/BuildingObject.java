/**
 * 
 */
package uk.ac.reading.cs2ja16.milanlacmanovic.IbuildingWgui;

import java.awt.Point;

import javafx.scene.image.Image;

/**
 * @author milan
 *
 */
public abstract class BuildingObject {
	static int objectID;
	String objectName;
	Image objectImage;
	Point objectPosition;
	abstract public void presentGUI(BuildingGUI bg);
	abstract public int getID();
	abstract public Image getImage();
	abstract public void setImage(Image objectImage);
	abstract public void setName(String objectName);
	abstract public String getName();
	abstract public String toString();
	abstract public Point getPosition();
	abstract public int getXPosition();
	abstract public int getYPosition();
	abstract public void setPosition(Point objectPosition);
}
