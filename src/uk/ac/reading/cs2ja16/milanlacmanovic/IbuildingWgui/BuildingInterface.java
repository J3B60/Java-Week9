package uk.ac.reading.cs2ja16.milanlacmanovic.IbuildingWgui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;
import javax.swing.JFileChooser;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

/**
 * Building Interface allows for user input from console
 * User interface for accessing the other components of this program
 * @author milan
 *
 */

public class BuildingInterface {
	Scanner s;	//scanner used for input from user
	private char[][] BuildingDraw; //Holds Drawn Building array for the console output
	
	Building myBuilding;//Building
	/**
	 * return as String definition of bOpt'th building
	 * @param bOpt
	 */
	public String buildingString (int bOpt) {
		if (bOpt == 1) {
			return "11 11;0 0 4 4 2 4;6 0 10 10 6 5;0 6 4 10 2 6";
		}
		else {
			return "40 12;0 0 15 4 8 4;15 0 30 4 22 4;0 6 10 11 6 6";
		}
	}
	/**
	 	* constructor for BuildingInterface
	 	* sets up scanner used for input and the arena
	 	* then has main loop allowing user to enter commands
	 	* Commands are N, D, M, A, P, U, S, L, I, X
	 	* N makes a new building from the list of options left by RJM
	 	* D draws the current Building to Console
	 	* M moves the person step by step to the initial point door as in the task
	 	* A animates the person along the path, point by point
	 	* P animates the person fully from start to finish
	 	* U allows user input string of the building layout
	 	* S Saves current building in program to file
	 	* L loads a file and builds a building using it
	 	* I displays information on the building such as Building size, rooms + coordinates and which room the person is currently in
	 	* X exits the program
	 */
	public BuildingInterface() {
		  s = new Scanner(System.in);	// set up scanner for user input
	    int bno = 1;			// initially building 1 selected
	
	    myBuilding = new Building(buildingString(bno));// create building
		
	    char ch = ' ';
	    do {
	       	System.out.print("(N)ew buidling, (D)raw, (M)ove, (A)nimate, (P)ath, (U)ser Input New Building, (S)ave, (L)oad, (I)nfo, e(X)it > ");
	    	ch = s.next().charAt(0);
	    	s.nextLine();
	    	switch (ch) {
	   		case 'N' :
	    		case 'n' :
	    				bno = 3 - bno;  // change 1 to 2 or 2 to 1
	    				myBuilding.setBuilding(buildingString(bno));
	    				break;
	    		case 'I' :
	    		case 'i' :
						System.out.print(myBuilding.toString());
						break;
	    		case 'D' :
	    		case 'd' :
		    			System.out.println(doDisplay());
		    			break;
	    		case 'M' :
	    		case 'm' ://Moves person step by step and to default first position from the task sheet which is the rooms door, can easily be changed to do whole path - would just need to go back t person class
	    				doDisplay(); //Cheap way to get the map out ready for person to use Without Feedback
		    			myBuilding.movePersoninBuilding(this);
		    			break;
	    		case 'A' :
	    		case 'a' ://Moves person point by point in path
	    				doDisplay();//Same reason as M
		    			if (!myBuilding.PersonCompletePath()) {//Animate if not at final
		    				animate();
		    			}
		    			break;
	    		case 'P' :
	    		case 'p' ://Animates from start to finish
	    				doDisplay(); //Same reason as M
	    				while(!myBuilding.PersonCompletePath()) {//Animate while not final
	    					animate();
	    				}
	    				break;
	    		case 'U' :
	    		case 'u' :
	    				myBuilding = new Building(UserInputBuilding());//Make new building using user input
	    			break;
	    		case 'S':
	    		case 's':
	    				SaveFile();//Saves
	    			break;
	    		case 'L':
	    		case 'l':
	    				myBuilding = new Building(LoadFile());//Make new building using load
	    			break;
	     		case 'x' : 	ch = 'X';	// when X detected program ends
	    				break;
		 
	    	}
		} while (ch != 'X');			// test if end
	
	   s.close();					// close scanner
	}
	
	/**
	 * Outputs the Building to console where the building is slightly 
	 * stretched to 2+ in x and y. There are two options in the code one of which is commented
	 * out. Output either as array view or string view. String view has been lefted activated since
	 * it takes up less space on the screen however the font in console is not monospace therefore
	 * a box looks like a rectangle hence the array option might help in some cases where
	 * the image is less warped looking.
	 * @return String containing
	 */
	
	public String doDisplay() {
		BuildingDraw = new char[myBuilding.getBuildingx() +2][myBuilding.getBuildingy() + 2]; //Switched x and y around//Setup char array to size of building
		String temp = "";
		showBuildingWall();
		myBuilding.showBuilding(this);
		for (int i = 0; i < BuildingDraw.length; i++) { //Output as Strings
			for (int j = 0; j < BuildingDraw[i].length; j++) {
				temp += String.valueOf(BuildingDraw[i][j]);
			}
			temp += "\n";
		}
			//NOTE TO SELF: X and Y are flipped, because x,y to j,i not i,j (matricies notation) TODO FIX
//		for (int k = 0; k < BuildingDraw.length; k++) { //TEST //OUTPUTS AS ARRAY View
//			System.out.println(Arrays.toString(BuildingDraw[k]));//Test
//		}//TEST
		return temp;
	}
	
	/**
	 * Places the # character to display the building boundaries in the Building Draw array.
	 * This creates a box or rectangle with gaps in the center and endge of #
	 */
	
	public void showBuildingWall() {
		Arrays.fill(BuildingDraw[0], '#'); //Fill top
		for (int i = 1; i < BuildingDraw.length -1; i++) {
			BuildingDraw[i][0] = '#'; //Add to Start of middle Row
			for (int j = 1; j < BuildingDraw[i].length - 1; j++) {
				BuildingDraw[i][j] = ' '; //Add gaps
			}
			BuildingDraw[i][BuildingDraw[0].length -1] = '#';//Add to end of middle row
		}
		Arrays.fill(BuildingDraw[BuildingDraw.length - 1], '#'); //Fill bottom
//for (int k = 0; k < BuildingDraw.length; k++) { //TEST
//	System.out.println(Arrays.toString(BuildingDraw[k]));//Test
//}//TEST
	}
	
	/**
	 * Adds other symbols and objects other than # which the building creates and offsets these
	 * symbols and objects by 1 in both x and y axis so it is within the rooms boundaries
	 * @param x
	 * @param y
	 * @param ch
	 */
	
	public void showIt(int x, int y, char ch) {
		BuildingDraw[x+1][y+1] = ch;//Y is top down//Swapped x and y to convert to matrix row first column next
	}
	
	/**
	 * Takes room corners and works out whether the wall is horizontal or vertical
	 * and places the right amount of walls in the right position
	 * @param xa
	 * @param ya
	 * @param xb
	 * @param yb
	 */
	
	public void showWall(int xa, int ya, int  xb, int yb) {
		if (ya == yb) {
			for (int i = xa+1; i < xb; i++) {// Fill complete
				showIt(i, ya, '|'); //+1 Added to offset coords by 1 so that rooms are displayed within the building boundaries, Building size should always be bigger than room coords, Y coord can be either ya or yb since equal
			}//Swapped y and x coords around for test
		}
		if (xa == xb) {
			for (int j = ya; j <= yb; j++) {// To fill inbetween horizontal walls just as in task sheet diagram
				showIt(xa, j, '-'); //Can either be xa or xb since equal 
			}
		}
	}
	
	/**
	 * Takes inputs from door coordinates and puts gap in the Building Draw wall 
	 * of a room allowing person to go through door and enter/leave room 
	 * @param x
	 * @param y
	 */
	
	public void showDoor(int x, int y) {
		showIt(x, y, ' ');
	}
	
	/**
	 * Animate using part of RJM's code. Draws the building and moves 
	 * person in a loop with sleep time to animate the movement of person 
	 */
	
	public void animate() {
		while (!myBuilding.CheckPersonReachedDestination()) {
			myBuilding.movePersoninBuilding(this);
			System.out.println(doDisplay());
			try {
				TimeUnit.MILLISECONDS.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * gets the Building Draw and sent to the Person class as a map
	 * for the person to better navigate the building without going through walls
	 * @return
	 */
	
	public char[][] getBuildingDraw(){
		return BuildingDraw;
	}
	
	/**
	 * Window allowing for user to input a string for Building. No checks if the String is valid
	 * @return
	 */
	
	public String UserInputBuilding() {
		String userIn = JOptionPane.showInputDialog(null, "Enter Building using format [Bx By; R1x1 R1y1 R1x2 R1y2 R1Dx R1Dy; ...]");
		return userIn;
	}
	
	/**
	 * Allows user to save the current building in the program to file in storage.
	 */
	
	public void SaveFile() {
		int option;
		File selectedFile;
		JFileChooser jfc = new JFileChooser();
		option = jfc.showSaveDialog(null);
		if (option == JFileChooser.APPROVE_OPTION) {//Check if option is what the JFileChooser has
			selectedFile = jfc.getSelectedFile(); //Assigns to File type variable 
		}
		else {
			selectedFile = null; //##Bad error handling
		}
		try (PrintWriter out = new PrintWriter(selectedFile)) {//Writes to file
				out.println(myBuilding.getOriginalInput());//Writes the Original Input from Building
				out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();//Error Handling similar to RJM's
		}
	}
	
	/**
	 * Allows user to choose a file from storage to be read into a string 
	 * which is then passed to the building. There are no checks for string validity
	 * @return
	 */
	
	public String LoadFile() {
		int option;
		File selectedFile;
		JFileChooser jfc = new JFileChooser();
		option = jfc.showOpenDialog(null); //Set chosen option from Jfilechooser to option variable
		if (option == JFileChooser.APPROVE_OPTION) { //Check if option has correct input
			selectedFile = jfc.getSelectedFile();
		}
		else { //else null - will load null file //NEED Better error handling, could have a while loop until correct input 
			selectedFile = null;
		}
		String temp = "";
		try {
			Scanner sc = new Scanner(selectedFile); //Scan File
			while (sc.hasNextLine()) { //Add lines to the temp string whilst there are lines to add
				temp += sc.nextLine();
			}
			sc.close();
//			System.out.println(temp);//TEST
		} catch (FileNotFoundException e) {
			e.printStackTrace(); //Simple error handling similar to RJM's for the animate
		}
		return temp;
	}
	
	/**
	 * Main for the whole program, starts Building Interface constructor
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BuildingInterface bi = new BuildingInterface();	////###NOTE: Major Change from b to bi
	//just call the interface
	}
}
