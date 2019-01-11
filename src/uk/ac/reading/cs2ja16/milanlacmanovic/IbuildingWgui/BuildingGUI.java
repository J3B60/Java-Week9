package uk.ac.reading.cs2ja16.milanlacmanovic.IbuildingWgui;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuildingGUI extends Application {
	int canvasSize = 512;				// constants for relevant sizes
	double t;
    GraphicsContext gc;
    GraphicsContext secondaryGC;
//    private VBox ltPane;
    private VBox rtPane;
    private FlowPane toolbar;
    private HBox btPane;
    private boolean SetAnimationRun = false;
    private Random rgen = new Random();
    BuildingInterface bi = new BuildingInterface();
    private int skyPos = -360;
    private Boolean gridViewSwitch = false;

    long startNanoTime = System.nanoTime();
    /**
     * drawIt ... draws object defined by given image at position and size
     * @param i
     * @param x
     * @param y
     * @param sz
     */
	public void drawIt () {
		gc.clearRect(0,  0,  canvasSize,  canvasSize);
		drawLines(gc);
		for (int i = 0; i < bi.allBuildings.get(bi.getCurrentBuildingIndex()).getAllPeople().size(); i++) {
			bi.allBuildings.get(bi.getCurrentBuildingIndex()).getAllPeople().get(i).showPersonGUI(this);
		}
		for (int i = 0; i < bi.allBuildings.get(bi.getCurrentBuildingIndex()).getAllBuildingObjects().size(); i++) {
			bi.allBuildings.get(bi.getCurrentBuildingIndex()).getAllBuildingObjects().get(i).presentGUI(this);
		}
		drawGrid();
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
		        bi.allBuildings.set(bi.CurrentBuildingIndex, new Building(bi.LoadFile()));//Should work
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
		
		Menu mEdit = new Menu("Edit");
		MenuItem mBuildingSize = new MenuItem("Building Size");
		mBuildingSize.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {
		    	bi.UserConfigBuildingSize();
		    }
		});
		mEdit.getItems().addAll(mBuildingSize);
		
		menuBar.getMenus().addAll(mFile, mEdit, mView, mHelp);	// menu has File and Help
		
		return menuBar;					// return the menu, so can be added
	}
	
	public void drawStatus() {
		rtPane.getChildren().clear();					// clear rtpane
				// now create label
		//Need to loop for all items in solar system and add to temp 
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/mm/yyyy");
		Date date = new Date();
		String temp = "";
		for (int i = 0; i < bi.allBuildings.get(bi.getCurrentBuildingIndex()).getAllBuildingObjects().size(); i ++) {
			temp += "\n" + bi.allBuildings.get(bi.getCurrentBuildingIndex()).getAllBuildingObjects().get(i).toString();
		}
		Label Rl = new Label(dateFormat.format(date) + "\n\n" + bi.toString() + "\n" + temp + "\n\nTools");
		Rl.setWrapText(true);
		rtPane.getChildren().add(Rl);				// add label to pane
		toolbarCollection();
	}
	
//	public void drawSky() {
//		ltPane.getChildren().clear();					// clear rtpane
//		
//				// now create label
//		//Need to loop for all items in solar system and add to temp 
//		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//		Date date = new Date();
//		Label Ll = new Label(dateFormat.format(date));
//		Canvas sky = new Canvas(50, canvasSize);
//		secondaryGC = sky.getGraphicsContext2D();
//		Image skyImg = new Image(getClass().getResourceAsStream("DaySkyTransition.png"));
//		secondaryGC.setFill(Color.RED);
//		secondaryGC.fillPolygon(new double[]{20, 20, 30},
//              new double[]{(canvasSize/2) - 5, (canvasSize/2) + 5, canvasSize/2}, 3);
//		if (skyPos == -1440) {
//			skyPos = -360;
//		}
//		skyPos =- 3*((int) t/120);
//		secondaryGC.drawImage(skyImg, 30, skyPos);
//		ltPane.getChildren().add(Ll);
//		ltPane.getChildren().add(sky);				// add label to pane
//	}
	
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
	    	        	   //SystemPosSet(e.getX(), e.getY());	
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
				if(SetAnimationRun == true) {
    				while(!bi.allBuildings.get(bi.getCurrentBuildingIndex()).PersonCompletePath()) {//Animate while not final
    					bi.animate();
    					drawIt();
    				}
				}
				else {
					SetAnimationRun = true;
				}
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
		btnBottom.setTooltip(new Tooltip("Pause Animation"));
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
	
	private Button toolbarAddPerson() {
		// create button
		Image buttonIcon = new Image(getClass().getResourceAsStream("personIcon.png"));
		Button btn = new Button();
		ImageView imageView = new ImageView(buttonIcon);
		imageView.setFitWidth(15);
		imageView.setFitHeight(15);
		btn.setGraphic(imageView);
			// now add handler
		btn.setTooltip(new Tooltip("Add Person to Building {Max 5}"));
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
					
					
			}
		});
		return btn;
	 }
	
	private Button toolbarGridView() {
		// create button
		Image buttonIcon = new Image(getClass().getResourceAsStream("gridIcon.png"));
		Button btn = new Button();
		ImageView imageView = new ImageView(buttonIcon);
		imageView.setFitWidth(15);
		imageView.setFitHeight(15);
		btn.setGraphic(imageView);
			// now add handler
		btn.setTooltip(new Tooltip("Show grid lines"));
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
					gridViewSwitch = !gridViewSwitch;
					drawIt();
			}
		});
		return btn;
	 }
	
	private void drawGrid() {
		double ratio = BuildingtoFit();
		if (gridViewSwitch == true) {
			for (int i = 0; i < canvasSize / ratio; i++) {
				gc.setFill(Color.BLACK);
				gc.setLineWidth(1);
				gc.strokeLine(ratio*i, 0, ratio*i, canvasSize);//Vertical
				gc.strokeLine( 0, ratio*i, canvasSize, ratio*i);//Horizontal
			}
		}
		else;
	}
	
	private void toolbarCollection() {
		toolbar = new FlowPane();
		toolbar.getChildren().add(toolbarAddPerson());
		toolbar.getChildren().add(toolbarGridView());
		rtPane.getChildren().add(toolbar);
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
	    rtPane.setMaxWidth(200);
	    bp.setRight(rtPane);
	    
//	    ltPane = new VBox();
//	    bp.setLeft(ltPane);
	    
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
	    			t = (currentNanoTime - startNanoTime) / 1000000000.0;
    				drawIt();
    				drawStatus();
	    			if (SetAnimationRun == true){
//	    				drawSky();
	    				if (bi.allBuildings.get(bi.getCurrentBuildingIndex()).PersonCompletePath()) {
	    					SetAnimationRun = false;
	    				}
	    				bi.allBuildings.get(bi.getCurrentBuildingIndex()).movePersoninBuilding(bi);
//	    				System.out.println(doDisplay());
	    				try {
	    					TimeUnit.MILLISECONDS.sleep(250);
	    				} catch (InterruptedException e) {
	    					e.printStackTrace();
	    				}
	    			}
	    			else{
	    				//######################
	    			}
	    		}
	    	}.start();					// start it
	    
		stagePrimary.show();
	}
	/**
	 * The ratio to keep things inside the canvas NOTE ignores line thickness
	 * @return ratio
	 */
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
	
	public void drawObject(Image i, double x, double y){
		double ratio = BuildingtoFit();
		gc.drawImage(i, (y*ratio)+ratio*0.5, (x*ratio)+ratio*0.5, ratio, ratio);//Bs
	}
	
	private void drawLines(GraphicsContext gc) {
		double ratio = BuildingtoFit();
//		gc.setFill(Color.BLUE);
//        gc.setStroke(Color.BLACK);
//        gc.setLineWidth(5);
//        gc.fillRect(10, 30, 50, 50); //For Lights or Temps representaiton 
//        for (int i = 0; i < bi.getBuildingDraw().length; i++) {
//        	for (int j = 0; j < bi.getBuildingDraw()[i].length; j++) {
//        		if (bi.getBuildingDraw()[j][i] == '|') {
//        				gc.strokeLine(i*ratio, j*ratio-(ratio*0.5), i*ratio, j*ratio+(ratio*0.5));///TODO set using ij and ratio from buildingtoFit
//        		}
//        		else if (bi.getBuildingDraw()[j][i] == '-') {
//        			if (i == 1 && j == 1) {//Is the First # then - //this assumes room starts right after building
//        				gc.strokeLine(i*ratio+(ratio*0.5), j*ratio, i*ratio, j*ratio);///TODO set using ij and ratio from buildingtoFit
//        				gc.strokeLine(i*ratio, j*ratio, i*ratio, j*ratio+(ratio*0.5));///TODO set using ij and ratio from buildingtoFit
//        			}
//        			else if (i == 1 && j == bi.getBuildingDraw()[i].length -2) {//Is the First # then - //this assumes room starts right after building
//        				gc.strokeLine(i*ratio+(ratio*0.5), j*ratio, i*ratio, j*ratio);///TODO set using ij and ratio from buildingtoFit
//        				gc.strokeLine(i*ratio, j*ratio, i*ratio, j*ratio+(ratio*0.5));///TODO set using ij and ratio from buildingtoFit
//        			}
//        			else if (i == bi.getBuildingDraw()[i].length -2 && j == 1) {
//        				gc.strokeLine(i*ratio+(ratio*0.5), j*ratio, i*ratio, j*ratio);///TODO set using ij and ratio from buildingtoFit
//        			}
//        			else if (i == bi.getBuildingDraw()[i].length -2 && j == bi.getBuildingDraw()[i].length -2) {
//        				gc.strokeLine(i*ratio+(ratio*0.5), j*ratio, i*ratio, j*ratio);///TODO set using ij and ratio from buildingtoFit
//        			}
//        			else {
//        				gc.strokeLine(i*ratio-(ratio*0.5), j*ratio, i*ratio+(ratio*0.5), j*ratio);///TODO set using ij and ratio from buildingtoFit
//        			}
//        		}
//        	}
//        }
		//Above is old and uses Building Interface
		//Building outer wall
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.strokeLine(1*ratio, 1*ratio, (bi.getBuildingXY()[1]+1)*ratio, 1*ratio); //North wall
        gc.strokeLine(1*ratio, 1*ratio, 1*ratio, (bi.getBuildingXY()[0]+1)*ratio); //West wall
        gc.strokeLine((bi.getBuildingXY()[1]+1)*ratio, 1*ratio, (bi.getBuildingXY()[1]+1)*ratio, (bi.getBuildingXY()[0]+1)*ratio); //East wall
        gc.strokeLine(1*ratio, (bi.getBuildingXY()[0]+1)*ratio, (bi.getBuildingXY()[1]+1)*ratio, (bi.getBuildingXY()[0]+1)*ratio); //South Wall
        //Room walls
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        for (int i = 0; i < bi.getAllRooms().size() ; i++) {
        	gc.strokeLine((bi.getAllRooms().get(i).getDoorCoords()[1]+1)*ratio, (bi.getAllRooms().get(i).getDoorCoords()[0]+1)*ratio, (bi.getAllRooms().get(i).getDoorCoords()[1]+1)*ratio, (bi.getAllRooms().get(i).getDoorCoords()[2]+1)*ratio); //North wall
            gc.strokeLine((bi.getAllRooms().get(i).getDoorCoords()[1]+1)*ratio, (bi.getAllRooms().get(i).getDoorCoords()[0]+1)*ratio, (bi.getAllRooms().get(i).getDoorCoords()[3]+1)*ratio, (bi.getAllRooms().get(i).getDoorCoords()[0]+1)*ratio); //West wall
            gc.strokeLine((bi.getAllRooms().get(i).getDoorCoords()[1]+1)*ratio, (bi.getAllRooms().get(i).getDoorCoords()[2]+1)*ratio, (bi.getAllRooms().get(i).getDoorCoords()[3]+1)*ratio, (bi.getAllRooms().get(i).getDoorCoords()[2]+1)*ratio); //East wall
            gc.strokeLine((bi.getAllRooms().get(i).getDoorCoords()[3]+1)*ratio, (bi.getAllRooms().get(i).getDoorCoords()[0]+1)*ratio, (bi.getAllRooms().get(i).getDoorCoords()[3]+1)*ratio, (bi.getAllRooms().get(i).getDoorCoords()[2]+1)*ratio); //South Wall
        }
        //need to cut out doors
        for (int i = 0; i < bi.getAllRooms().size(); i++) {
        	//gc.fillRect(((bi.getAllRooms().get(i).getDoorCoords()[5])*ratio)+ratio-(0.5*ratio),  ((bi.getAllRooms().get(i).getDoorCoords()[4])*ratio)+ratio-(0.5*ratio),  ratio,  ratio);//TEST
        	gc.clearRect(((bi.getAllRooms().get(i).getDoorCoords()[5])*ratio)+ratio-(0.5*ratio),  ((bi.getAllRooms().get(i).getDoorCoords()[4])*ratio)+ratio-(0.5*ratio),  ratio,  ratio);
        }
	}
        private void drawWindow(GraphicsContext gc) {
//    		double ratio = BuildingtoFit();
//    		gc.setFill(Color.BLUE);
//            gc.setStroke(Color.BLACK);
//            gc.setLineWidth(5);
//            gc.fillRect(10, 30, 50, 50); //For Lights or Temps representaiton 
//            for (int i = 0; i < bi.getBuildingDraw().length; i++) {
//            	for (int j = 0; j < bi.getBuildingDraw()[i].length; j++) {
//            		if (bi.getBuildingDraw()[j][i] == '|') {
//            			gc.strokeLine(i*ratio, j*ratio-(ratio*0.5), i*ratio, j*ratio+(ratio*0.5));///TODO set using ij and ratio from buildingtoFit
//            		}
//            		else if (bi.getBuildingDraw()[j][i] == '-') {
//            			gc.strokeLine(i*ratio-(ratio*0.5), j*ratio, i*ratio+(ratio*0.5), j*ratio);///TODO set using ij and ratio from buildingtoFit
//            		}
//            	}
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
	
	public int getCanvasSize(){
		return canvasSize;
	}
	
	public double getRatio() {
		return BuildingtoFit();
	}
	
	public static void main(String[] args) {
		Application.launch(args);			// launch the GUI
	}

}
