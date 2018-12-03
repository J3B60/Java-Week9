package uk.ac.reading.cs2ja16.milanlacmanovic.IbuildingWgui;

import javafx.scene.Cursor;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class BuildingGUI extends Application {
	int canvasSize = 512;				// constants for relevant sizes
	double t;
    GraphicsContext gc;
    private VBox rtPane;
    private HBox btPane;
    private boolean SetAnimationRun = true;
    private Random rgen = new Random();
    BuildingInterface bi = new BuildingInterface();

    long startNanoTime = System.nanoTime();
    /**
     * drawIt ... draws object defined by given image at position and size
     * @param i
     * @param x
     * @param y
     * @param sz
     */
	public void drawIt () {
		drawLines(gc);
		bi.myBuilding.occupant.showPersonGUI(this);
	}
	
	private void showMessage(String TStr, String CStr) {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle(TStr);
	    alert.setHeaderText(null);
	    alert.setContentText(CStr);

	    alert.showAndWait();
}
/**
 * function to show in a box ABout the programme
 */
 private void showAbout() {
	 showMessage("About", "Intelligent Building demo by Milan Lacmanovic");
 }
    /**
	 * function to show in a box ABout the programme
	 */
	 private void showHelp() {
		 showMessage("Help", "TODO once all options from 'bi' class added");
	 }
 
	
	MenuBar setMenu() {
		MenuBar menuBar = new MenuBar();		// create menu

		Menu mHelp = new Menu("Help");			// have entry for help
				// then add sub menus for About and Help
				// add the item and then the action to perform
		MenuItem mAbout = new MenuItem("About");
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showAbout();				// show the about message
            }	
		});
		MenuItem miHelp = new MenuItem("Help");
		miHelp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showHelp();
            }	
		});
		mHelp.getItems().addAll(mAbout, miHelp); 	// add submenus to Help
		
				// now add File menu, which here only has Exit
		Menu mFile = new Menu("File");
		MenuItem mNew = new MenuItem("New");
		mNew.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		    	bi.UserInputBuilding();
		    }
		});
		MenuItem mOpen = new MenuItem("Open");
		mOpen.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		        bi.myBuilding = new Building(bi.LoadFile());
		    }
		});
		MenuItem mSave = new MenuItem("Save");
		mSave.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		        bi.SaveFile();
		    }
		});
		MenuItem mExit = new MenuItem("Exit");
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		        System.exit(0);						// quit program
		    }
		});
		mFile.getItems().addAll(mNew, mOpen, mSave, mExit);
		
		Menu mView = new Menu("View");
		MenuItem mGraph = new MenuItem("Graph");
		MenuItem mBuilding = new MenuItem("Building");
		MenuItem mtoFit = new MenuItem("Fit to Screen");
		mBuilding.setDisable(true);
		mGraph.setDisable(false);
		mBuilding.setDisable(false);
		mGraph.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		    	//TODO graph FUNCTION goes Here
		    	mBuilding.setDisable(false);
		    	mtoFit.setDisable(true);
		    	mGraph.setDisable(true);
		    }
		});
		mBuilding.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		    	//TODO return to Building view FUNCTION goes Here
		    	mBuilding.setDisable(true);
		    	mtoFit.setDisable(false);
		    	mGraph.setDisable(false);
		    }
		});
		mtoFit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		    	BuildingtoFit();
		    }
		});
		mView.getItems().addAll(mBuilding, mGraph, mtoFit);
		
		menuBar.getMenus().addAll(mFile, mView, mHelp);	// menu has File and Help
		
		return menuBar;					// return the menu, so can be added
	}
	
	public void drawStatus() {
		rtPane.getChildren().clear();					// clear rtpane
				// now create label
		//Need to loop for all items in solar system and add to temp 
		Label l = new Label(bi.toString());
		rtPane.getChildren().add(l);				// add label to pane	
	}
	
	private void SystemPosSet(double x, double y) {
		// now clear canvas and draw sun and moon
		gc.clearRect(0,  0,  canvasSize,  canvasSize);		// clear canvas
		//////################								// give its position 
	}
	
	/**
	 * calculate position of Earth at specified angle and then draw system
	 * @param t		angle (time dependent) of Earth
	 */
	private void drawSystem (double t) {
		gc.clearRect(0,  0,  canvasSize,  canvasSize);
		//##############################################
	}
	
	private void setMouseEvents (Canvas canvas) {
	       canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
	    	       new EventHandler<MouseEvent>() {
	    	           @Override
	    	           public void handle(MouseEvent e) {
	    	        	   SystemPosSet(e.getX(), e.getY());	
	    	        	   		// draw system where mouse clicked
	    	           }
	    	       });
	}
	
	private Button setAnimateButton() {
			// create button
		Button btnBottom = new Button("Animate");
				// now add handler
		btnBottom.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
//				if(SetAnimationRun == true) {
    				while(!bi.myBuilding.PersonCompletePath()) {//Animate while not final
    					bi.animate();
    					drawIt();
    				}
//				}
//				else {
//					SetAnimationRun = true;
//				}
				setBottomButtons();
					// and its action to draw earth at random angle
			}
		});
		return btnBottom;
	}
	
	private Button setPauseButton() {
		// create button
		Button btnBottom = new Button("ERROR");
		if(SetAnimationRun == true) {
			btnBottom = new Button("Pause");
		}
		else {
			btnBottom = new Button("Stop");
		}
			// now add handler
		btnBottom.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (SetAnimationRun == false) {
					startNanoTime = System.nanoTime();
					SetAnimationRun = false;
					drawSystem(t);
				}
				else {
					SetAnimationRun = false;
				}
				setBottomButtons();
					// and its action to draw earth at random angle
			}
		});
		return btnBottom;
		}
	private void setBottomButtons(){
		btPane.getChildren().clear();
		btPane.getChildren().add(setAnimateButton());
		btPane.getChildren().add(setPauseButton());
		
	}
	/**
	 * main function ... sets up canvas, menu, buttons and timer
	 */
	@Override
	public void start(Stage stagePrimary) throws Exception {
		StackPane holder = new StackPane();
		stagePrimary.setTitle("Intelligent Building");
		BorderPane bp = new BorderPane();
		
		bp.setTop(setMenu());
		
		
	    Group root = new Group();					// for group of what is shown			// put it in a scene				// apply the scene to the stage
	    Canvas canvas = new Canvas( canvasSize, canvasSize );
	    							// create canvas onto which animation shown
	    holder.getChildren().add(canvas);
	    root.getChildren().add( holder );			// add to root and hence stage
	   
	    
	    holder.setStyle("-fx-background-color: lightgrey");
	    gc = canvas.getGraphicsContext2D();
	    setMouseEvents(canvas);
	    bp.setCenter(root);
	    								// get context on canvas onto which images put
		// now load images of earth and sun
		// note these should be in package
	    rtPane = new VBox();
	    bp.setRight(rtPane);
	    
	    btPane = new HBox();
	    bp.setBottom(btPane);
	    setBottomButtons();
	    Scene scene = new Scene(bp, canvasSize*1.4, canvasSize*1.2);
	    stagePrimary.setScene( scene );
	    stagePrimary.show();
//	    final long startNanoTime = System.nanoTime();
		// for animation, note start time
//	    scene.setCursor(Cursor.WAIT); //CHANGE Cursor (could be used on canvas to resize building/edit)
	    drawLines(gc); //Draw Lines Test
	    new AnimationTimer()			// create timer
	    	{
	    		public void handle(long currentNanoTime) {
	    				// define handle for what do at this time
	    			if (SetAnimationRun == true){
	    				t = (currentNanoTime - startNanoTime) / 1000000000.0;
	    				drawIt();
	    				drawStatus();
	    				//#############################################
	    			}
	    			else{
	    				//######################
	    			}
	    		}
	    	}.start();					// start it
	    
		stagePrimary.show();
	}
	
	private double BuildingtoFit() {//NEED TO DRAW BUILDING FIRST
		double ratio = 0;
		ratio = bi.getBuildingDraw().length;
		if (ratio < bi.getBuildingDraw()[0].length) ratio = bi.getBuildingDraw()[0].length;
		ratio = canvasSize/ratio; //Ratio is optimal size to show all of building in canvas at once
		return ratio;
	}
	
	public void drawPerson(int x, int y){
		double ratio = BuildingtoFit();
		gc.setFill(Color.RED);
		gc.fillOval((y*ratio)+ratio*0.5, (x*ratio)+ratio*0.5, ratio*0.8, ratio*0.8);//*0.8 ratio just to get person to be smaller than door
	}
	
	private void drawObject(){
		
	}
	
	private void drawLines(GraphicsContext gc) {
		double ratio = BuildingtoFit();
//		gc.setFill(Color.BLUE);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
//        gc.fillRect(10, 30, 50, 50); //For Lights or Temps representaiton 
        for (int i = 0; i < bi.getBuildingDraw().length; i++) {
        	for (int j = 0; j < bi.getBuildingDraw()[i].length; j++) {
        		if (bi.getBuildingDraw()[j][i] == '|') {
        			gc.strokeLine(i*ratio, j*ratio-(ratio*0.5), i*ratio, j*ratio+(ratio*0.5));///TODO set using ij and ratio from buildingtoFit
        		}
        		else if (bi.getBuildingDraw()[j][i] == '-') {
        			gc.strokeLine(i*ratio-(ratio*0.5), j*ratio, i*ratio+(ratio*0.5), j*ratio);///TODO set using ij and ratio from buildingtoFit
        		}
        	}
        }
//        gc.fillOval(10, 60, 10, 10); //FROM an Example Online (maybe useful to refer back to in the future when drawing new stuff)
//        gc.strokeOval(60, 60, 30, 30);
//        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
//        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
//        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
//        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
//        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
//        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
//        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
//        gc.fillPolygon(new double[]{10, 40, 10, 40},
//                       new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolygon(new double[]{60, 90, 60, 90},
//                         new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolyline(new double[]{110, 140, 110, 140},
//                          new double[]{210, 210, 240, 240}, 4);
	}
	
	public int getCanvasSize(){
		return canvasSize;
	}
	
	public static void main(String[] args) {
		Application.launch(args);			// launch the GUI
	}

}
