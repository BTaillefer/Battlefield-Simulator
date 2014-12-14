package army;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.effect.*;
import javafx.scene.paint.*;
import actor.Actor;
import actor.ActorFactory;
/**<i>Army<i> Class is used to create two separate army's that can then be filled with references to subclass <i>Actor<i> objects.The <i>Army<i> class
 * includes String variable, armyName which name's the Army's created, and an Arraylist of Actor's which stores the Actor's. The <i>Army<i> Class works together
 * with the <i>ActoryFactory<i> to create object's.
 * @author Brodie Taillefer
 *
 */

public class Army  {
	/** Instance String variable armyName is used to name <i>Army<i> object's that are created */
	private String armyName;
	//private ObservableList <Actor> actors =  FXCollections.observableArrayList(); //ArrayList<Actor>(); 
	/** Creates an ObservableList of <i>Actor<i> object's for visual representation, example tableview, listview , etc */
	private ObservableList<Actor> actors =  FXCollections.observableArrayList(new ArrayList<Actor>()); //ArrayList<Actor>();
	/** Creates ListView of <i>Actor<i> objects */
	private ListView<Actor> listview;
	/** Creates a TableView of <i>Actor<i> objects */
	private TableView<Actor> tableView;
	/** Creates <i>Army<i> object, opposingArmy */
	private Army opposingArmy;
	
	/** Initializes <i>Color<i> object */
	private Color color = Color.RED;
	/** Intializes an <i>Effect<i> object, with a dropShadow */
	private Effect effect = DropShadowBuilder.create().color(color).build();
	/** Method <i>getDropShadow<i> used to return dropshadow */
	public Effect getDropShadow() { return effect; }
	
	/**<i>Army<i> Constructor used to initialize <i>Army<i> objects. The constructor takes parameter armyName to initialize the armyName of <i>Army<i> objects.
	 * For each <i>Army<i> object created an ArrayList of reference to <i>Actor<i> objects.
	 * With Assignment4 we are adding the Army constructor to apply a Color for each army created, aswell as a listview and tableview for each subclass <i>Actor<i> object
	 * @param armyName
	 * @param color 
	 */
	public Army(String armyName, Color color) {
		this.color = color;
		listview = new ListView<Actor>();
		this.armyName = armyName;
		listview.setItems(actors);
		tableView = Actor.createTable();
		tableView.setItems(actors);
		
	}
	/** Method <i>populate<i> is used to populate <i>Army<i> array with <i>Actor<i> objects.<i>populate<i> uses <i>ActoryFactory<i> to create objects with the parameters
	 * Type which is used to call what type of subclass <i>Actor<i> object you want created, and numToAdd for the amount created.
	 * With Assignment4 we are adding the populate function to use the ObservableList and to populate them at a certain X and Y coordinate with a differentiation spread
	 * @param type
	 * @param numToAdd
	 * @param listChildNodes 
	 */
	public void populate(ActorFactory.Type type, int numToAdd, ObservableList<Node> listChildNodes, Point2D ptCenter, double spread) { 
		 for (int i=0; i<numToAdd; ++i) { 
		 Actor actor = type.create(this);
		 actors.add(actor); 
		 actor.setRandomLocationAroundPoint(ptCenter,spread);
		 listChildNodes.add(actor.getBattleFieldAvatar());
		 }
	}
	
	/** Method <i>edit<i> used to manually edit any subclass<i>Actor<i> object in the arraylist. <i>edit<i> takes parameter indexOfActorToEdit for which index
	 * to edit. The Method call's the correct <i>Actor<i> <i>inputAllFields<i> to edit the object.
	 * @param indexOfActorToEdit
	 */
	public void edit(int indexOfActorToEdit) {
		actors.get(indexOfActorToEdit).inputAllFields();
	}
	
	/**Method <i>displayAll<i> is an advanced for loop used to display all <i>Actor<i> objects in the army arraylist
	 */
	public void displayAll() {
		for (Actor currentActor : actors) {
			System.out.println(currentActor);
	    }  
    }
	
	/** Method <i>display<i> is used to display a single <i>Actor<i> object, takes parameter index to allow user to edit any <i>Actor<i> object
	 * @param index
	 */
	public void display(int index) {
		System.out.println(actors.get(index));
	}
	
	/** Method <i>size<i> returns an int , the size of the ArrayList of each <i>Army<i> object.
	 * @return actors.size()
	 */
	public int size() {
		return actors.size();
	}
	
	/** Method <i>toString<i> returns a string to succesfully output <i>Army<i> object information.
	 */
	public String toString(){
		return String.format("%s, Size:%d",armyName, actors.size());
	}
	
	/** Method <i>getName<i> used to return armyName */
	public String getName() {
		return armyName;
	}
	
	/** Method <i>getListViewOfActors<i> used to return listview of <i>Actor<i> objects */
	public Node getListViewOfActors() {
		return listview;
	}
	
	/** Method <i>getTableViewOfActors<i> used to return tableview of <i>Actor<i> objects */
	public Node getTableViewOfActors() {
		return tableView;
	}
	
	/** Method <i>startMotion<i> used to implement the <i>move<i> method on all <i>Actor<i> subclass object's. Done by Advanced FOR Loop */
	public void startMotion() {
		for (Actor actor : actors)
			actor.move();
	}
	
	/** Method <i>suspend<i> used to implement the <i>suspend<i< method on all <i>Actor<i> subclass object's. Done by Advanced FOR Loop */
	public void suspend() {
		for (Actor actor : actors)
			actor.suspend();
	}
	
	/** Method <i>getOpposingArmy<i> used to return the opposingArmy */
	public Army getOpposingArmy() {
		return opposingArmy;
		
	}
	
	/** Method <i>setOpposingArmy<i> used to set the opposingArmy variable */
	public void setOpposingArmy(Army opposingArmy) {
		this.opposingArmy = opposingArmy;
		
	}
	
	/** Method <i>findNearestOpponent<i> used to find which opposingActor in the enemy army is the closest in terms of X and Y coordinates
	 * This is done by taking the actor that is moving X and Y coordinates and comparing them to each <i>Actor<i> object in the opposingArmy.
	 * When every enemy object has been checked it returns the closest one as nearestOpponent, if none are found null will be returned
	 * 
	 * @param actorToMove
	 * @return nearestOpponent
	 */
	public Actor findNearestOpponent(Actor actorToMove) {
		Point2D actorToMoveLocation = new Point2D(actorToMove.getBattleFieldAvatar().getTranslateX(),actorToMove.getBattleFieldAvatar().getTranslateY());
		Actor nearestOpponent = null;
		double closestDistance = Double.MAX_VALUE;
		for (Actor opposingActor : actors ) {
			double currentDistance = actorToMoveLocation.distance(opposingActor.getBattleFieldAvatar().getTranslateX(),opposingActor.getBattleFieldAvatar().getTranslateY());
			if (currentDistance < closestDistance ) {
				closestDistance = currentDistance;
				nearestOpponent = opposingActor;
			}
		}	
		return nearestOpponent;
	}

}



