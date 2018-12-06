/**
 * 
 */
package uk.ac.reading.cs2ja16.milanlacmanovic.IbuildingWgui;

import javafx.scene.image.Image;
import java.awt.Point;

/**
 * @author milan
 *
 */
public abstract class BuildingObject {
	static int objectID;
	abstract public void presentGUI();
	abstract public int getID();
	abstract public Image getImage();
	abstract public void setImage(Image objectImage);
	abstract public void setName(String objectName);
	abstract public String getName();
	abstract public String toString();
	abstract public Point getPosition();
	abstract public void setPosition(Point objectPosition);
}
