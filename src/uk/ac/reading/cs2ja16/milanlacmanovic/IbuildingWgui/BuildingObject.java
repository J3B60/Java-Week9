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
	/**
	 * Unique object ID
	 */
	protected static int objectID;
	/**
	 * object Name
	 */
	protected String objectName;
	/**
	 * object Image
	 */
	protected Image objectImage;
	/**
	 * object position #######(should add a check that its a valid position)
	 */
	protected Point objectPosition;
	/**
	 * Draw to main Canvas
	 * @param bg
	 */
	abstract public void presentGUI(BuildingGUI bg);
	/**
	 * Return objectID
	 * @return objectID
	 */
	abstract public int getID();
	/**
	 * Return objectImage
	 * @return objectImage
	 */
	abstract public Image getImage();
	/**
	 * set a new ObjectImage for customisation
	 * @param objectImage
	 */
	abstract public void setImage(Image objectImage);
	/**
	 * set a new objectName for customisation
	 * @param objectName
	 */
	abstract public void setName(String objectName);
	/**
	 * getter for objectName
	 * @return objectName
	 */
	abstract public String getName();
	/**
	 * object toString
	 */
	abstract public String toString();
	/**
	 * get point position of object
	 * @return objectPosition
	 */
	abstract public Point getPosition();
	/**
	 * get object X coord
	 * @return int point.X
	 */
	abstract public int getXPosition();
	/**
	 * get object Y coord
	 * @return int point.Y
	 */
	abstract public int getYPosition();
	/**
	 * set new objectPosition
	 * @param objectPosition
	 */
	abstract public void setPosition(Point objectPosition);
	/**
	 * get room number of object
	 * ######Similar to get Person Position func in Building class
	 * @param myBuilding
	 * @return object Room 
	 */
	abstract public int objectInRoom(Building myBuilding);
	
	abstract public void Activate();
	
	abstract public void check(Building b);
}
