package simulator;


import util.InputGUI;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.LabelBuilder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
import actor.ActorFactory;
import army.Army;
/**
 * Creates a StackPane surface onto which the Army objects will be drawn.
 * @author Rex Woollard
 */
public class Simulator extends StackPane {
  /* <i>Simulator<i> instance variable, forcesOfLight, creates an <i>Army<i> Object that is null  */
  private Army forcesOfLight;
  /* <i>Simulator<i> instance variable, forcesOfDarkness, creates an <i>Army<i> Object that is null*/
  private Army forcesOfDarkness;
  /* <i>Simulator<i> instance variable, creates a stagelist window */
  private Stage stageListControllerWindow;
  /* <i>Simulator<i> instance variable, creates a stagetable window */
  private Stage stageTableControllerWindow;
  /* <i>Simulator<i> instance variable, creates a primarystage for the battlefield to display on */
  private Stage primaryStage;

  /* <i>Simulator<i> default constuctor, sets the primary stage and the default armies, forcesOfLight and forcesOfDarkness.
   * The default constuctor also sets the opposingArmies and builds a listViewWindow and tableViewWindow for organization and viewing
   */
  public Simulator(Stage primaryStage) {
  	this.primaryStage = primaryStage;
    forcesOfLight = new Army("Forces of Light",Color.RED);
    forcesOfDarkness = new Army("Forces of Darkness",Color.GREEN);
    forcesOfLight.setOpposingArmy(forcesOfDarkness);
    forcesOfDarkness.setOpposingArmy(forcesOfLight);
    buildListViewWindow();
    buildTableViewWindow();
    } // end constructor

  /*Method <i>populate<i> is used to populate the armies with various <i>Actor<i> subclass objects, this is done by selecing the
   * type of Actor, and the X, Y coordinates of population and spread variation
   */
  public void populate() {
	  Scene sceneOwner = getScene();
    forcesOfLight.populate(ActorFactory.Type.HOBBIT, 2, getChildren(), new Point2D (sceneOwner.getWidth() * 0.5, sceneOwner.getHeight() * 0.6),1.0);
    forcesOfLight.populate(ActorFactory.Type.ELF, 4, getChildren(), new Point2D (sceneOwner.getWidth() * 0.5, sceneOwner.getHeight() * 0.4),1.0);
    forcesOfLight.populate(ActorFactory.Type.WIZARD, 4, getChildren(), new Point2D (sceneOwner.getWidth() * 0.6, sceneOwner.getHeight() * 0.4),1.0);
    forcesOfDarkness.populate(ActorFactory.Type.ORC, 4, getChildren(), new Point2D (sceneOwner.getWidth() * 0.5, sceneOwner.getHeight() *0.3), 3.0);
 
    forcesOfDarkness.populate(ActorFactory.Type.TROLL, 5, getChildren(), new Point2D (sceneOwner.getWidth() * 0.35, sceneOwner.getHeight() * 0.5),2.0);
  } // end populate()
  
  	/** Method <i>displayLightArmyToConsole<i> used to display the army of Light to the console */
	public void displayLightArmyToConsole()	{ forcesOfLight.displayAll(); }
	
	/** Method <i>dispalyDarkArmyToConsole<i> used to display the army of darkness to the console */
	public void displayDarkArmyToConsole()	{ forcesOfDarkness.displayAll(); }		
	
  /** Method <i>editDarkArmy<i> used to edit the army of darkness with the index set */
  public void editDarkArmy()	{
  	if (forcesOfDarkness.size() == 0) {
  		System.out.println("No Actor objects in Army");
  		return;
  	}
  	forcesOfDarkness.edit(InputGUI.getInt("Index to Edit", 0, forcesOfDarkness.size()-1)); 
  } 
  
  /** Method <i>editLightArmy<i> used to edit the army of light with the index set */
  public void editLightArmy() {	
  	if (forcesOfLight.size() == 0) {
  		System.out.println("No Actor objects in Army");
  		return;
  	}
  	forcesOfLight.edit(InputGUI.getInt("Index to Edit", 0, forcesOfLight.size()-1)); 
  }
  
  /** Method <i>buildListViewWindow<i> used to create the listview in the battlefield simulator, this allows a
   * visual representation of the <i>toString<i> method for each subclass <i>Actor<i> in list format
   */
  public final void buildListViewWindow() { // final because of its use in the constructor
  	final double SCENE_WIDTH = 900.0;
  	final double SCENE_HEIGHT = 400.0;
    VBox vBoxLightArmy = VBoxBuilder.create().spacing(5.0).prefWidth(SCENE_WIDTH/2.0).children(LabelBuilder.create().text(forcesOfLight.getName())   .textAlignment(TextAlignment.CENTER).build(), forcesOfLight.getListViewOfActors())   .build();
    VBox vBoxDarkArmy  = VBoxBuilder.create().spacing(5.0).prefWidth(SCENE_WIDTH/2.0).children(LabelBuilder.create().text(forcesOfDarkness.getName()).textAlignment(TextAlignment.CENTER).build(), forcesOfDarkness.getListViewOfActors()).build();
    HBox hBoxSceneGraphRoot = HBoxBuilder.create().spacing(5.0).children(vBoxLightArmy, vBoxDarkArmy).build();
    stageListControllerWindow = StageBuilder.create().style(StageStyle.UTILITY).resizable(false).scene(new Scene(hBoxSceneGraphRoot, SCENE_WIDTH, SCENE_HEIGHT)).build();
    stageListControllerWindow.initOwner(primaryStage); // establishes control relationship between two separate windows
  } // end buildListViewWindow()
  
  /** Method <i>buildTableViewWindow<i> used to created the tableview for the battlefield simulator, this allows a
   * visual representation of the <i>toString<i> method for each subclass <i>Actor<i> in table format, this includes
   * X and Y coordinates and click sorting.
   */
  public final void buildTableViewWindow() { // final because of its use in the constructor
	    VBox vBoxLightArmy = new VBox(5.0);
	    vBoxLightArmy.getChildren().addAll(
	        LabelBuilder.create().text(forcesOfLight.getName()).textAlignment(TextAlignment.CENTER).build(),
	        forcesOfLight.getTableViewOfActors());
	    VBox vBoxDarkArmy = new VBox(5.0);
	    vBoxDarkArmy.getChildren().addAll(
	        LabelBuilder.create().text(forcesOfDarkness.getName()).textAlignment(TextAlignment.CENTER).build(),
	        forcesOfDarkness.getTableViewOfActors());
	    HBox hBoxSceneGraphRoot = new HBox(5.0);

	    hBoxSceneGraphRoot.getChildren().addAll(vBoxLightArmy, vBoxDarkArmy);

	    if (stageTableControllerWindow != null) {
	      stageTableControllerWindow.close();
	      stageTableControllerWindow.setScene(null);
	    }
	    stageTableControllerWindow = new Stage(StageStyle.UTILITY);
	    stageTableControllerWindow.initOwner(primaryStage);
	    stageTableControllerWindow.setScene(new Scene(hBoxSceneGraphRoot));
	  } // end buildTableViewWindow()

  /** Method <i>openListWindow<i> used to open the listWindow */
	public void openListWindow()  { 
		stageListControllerWindow.show();
		}
	/** Method <i>closeListWindow<i> used to clost the listWindow */
	public void closeListWindow() { stageListControllerWindow.hide();	}
	
	/** Method <i>run<i> used to start the moving of battlefieldAvatar's for both the forcesOfDarkness and forcesOfLight <i> */
	public void run() {
		forcesOfDarkness.startMotion();
		forcesOfLight.startMotion();
	}

	/** Method <i>suspend<i> used to suspend all transitions being done on the battlefieldAvatar's, when called all transitions
	 * in place are pauses 
	 */
	public void suspend() {
		forcesOfDarkness.suspend();
		forcesOfLight.suspend();
	}

	/** Method <i>closeTableWindow<i> used to close the table window  */
	public void closeTableWindow() {
		stageTableControllerWindow.hide();
	}

	/** Method <i>openTableWindow<i> used to open the table window */
	public void openTableWindow() {
		stageTableControllerWindow.show();
	}

} // end class Simulator