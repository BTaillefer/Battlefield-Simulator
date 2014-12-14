package actor;
import javafx.animation.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.converter.DoubleStringConverter;
import army.Army;
import util.Input;
import util.InputGUI;
import util.SingletonRandom;
/**
 * The <i>Actor<i> is the superclass for all actor objects,It provides the default constructor to create subclass Actors. Actor is an abstract class meaning actor objects can not be created directly.
 *
 * @author Brodie Taillefer
 *
 */


public abstract class Actor {
	/**Static final double for max_speed, speed cannot exceed this value           */
	// Create constants that are static throughout the entire the entire code, so the attributes of actors cannot  exceed
	// the minimum or maximum

	public final static double MAX_SPEED = 100; 
	
	/** Static final double for min_speed, speed cannot be placed lower than this value */
	public final static double MIN_SPEED = 20; 
	
	/**Static final double for max_health, health cannot exceed this value */
	public final static double MAX_HEALTH = 100;
	
	/** Static final double for min_health, health cannot be placed lower than this value */
	public final static double MIN_HEALTH = 10;
	
	/** Static final double for max_strength, strength cannot exceed this value */
	public final static double MAX_STRENGTH = 100;
	
	/** Static final double for min_strength, strength cannot be placed lower than his value */
	public final static double MIN_STRENGTH = 20;
	
	/** Static int used to assign numbers to each actor created for organization purposes */
	//static actorSerialNumber used to incrementally add a number to actor's name  
	private static int actorSerialNumber = 0;
	
	/** String <i>Actor<i> instance variable used to name <i>Actor<i> objects */
	private SimpleStringProperty name = new SimpleStringProperty();
	/** Double <i>Actor<i>instance variable */
	private SimpleDoubleProperty health = new SimpleDoubleProperty();
	/** Double <i>Actor<i> instance variable */
	private SimpleDoubleProperty speed = new SimpleDoubleProperty();
	/** Double <i>Actor<i> instance variable */
	private SimpleDoubleProperty strength = new SimpleDoubleProperty();
	/** Creating <i>Army<i> object armyAllegiance */
	protected Army armyAllegiance;
	/** Node battlefieldAvatar instance variable */
	private Node battlefieldAvatar;
	/** Transition object instance instance variable */
	private Transition transition;
	
	/** Used to return the battlefieldAvatar Node */
	public Node getBattleFieldAvatar() {
		return battlefieldAvatar; }
	
	/** Abstract method buildBattleFildAvatar, used to create battleFieldAvatar at the subclass level */
	protected abstract Node buildBattleFieldAvatar();
	
	/**  Tooltip object, used to display actor object information, instance variable */
	private Tooltip tooltip = new Tooltip();
	
	/** Static final double <i>Actor<i> Actor instance variable */
	private static final double THRESHOLD_OF_ADEQUATE_HEALTH = 0.3;
	

	
	//Constructor used to get individual values for each variable of actor object. Strength, health, and speed
	//are randomly assigned via SingletonRandom class within the parameters set.
	//actorSerialNumber is incremented by 1 for each time the constructor is run, adding the actorSerialNumber to
	//the end of actor name
	/**
	 * Default constructor for <i>Actor<i> class, randomly attributes values for Actor instance variables
	 * Strength, speed, health are all randomly calculated within boundaries by the SingletonRandom class
	 * actorSerialNumber and classSerial are used to incrementally name and organize actor objects
	 * In assignment4 the ability to allow a visual representation in JavaFX added many more variables.
	 * <i>Actor<i> default constructor now intializes a battlefildAvatar, armyAllegiance, and dropShadow
	 * 
	 * @param classSerial
	 * @param armyAllegiance
	 */
	public Actor(int classSerial, Army armyAllegiance) {
		battlefieldAvatar = buildBattleFieldAvatar();
		Tooltip.install(battlefieldAvatar, tooltip);
		this.armyAllegiance = armyAllegiance;
		setStrength(SingletonRandom.instance.getNormalDistribution(MIN_STRENGTH, MAX_STRENGTH, 3.0));
		setHealth(SingletonRandom.instance.getNormalDistribution(MIN_HEALTH,MAX_HEALTH, 3.0));
		setSpeed(SingletonRandom.instance.getNormalDistribution(MIN_SPEED, MAX_SPEED, 3.0));
		actorSerialNumber++;
		setName(actorSerialNumber + "." + getClass().getCanonicalName() + "." + classSerial);
		armyAllegiance.getDropShadow();
	}

	//Method inputAllFields allows the user to use InputGUI to manually update the Actors variables, within the boundaries set
	//if variables are not manually set, prompt will declare that actor has not been manually updated
	/** Method <i>inputAllFields<i> is used to manually set all <i>Actor<i> instance variables. Input is done by the class <i>inputGUI<i> and the static variables MAX and MIN are set 
	 * so that user input cannot exceed the value set
	 */
	public void inputAllFields(){
		InputGUI.showMessageGUI("Manually Editing " + getName());
		System.out.println("Updated Info on " + getName() + ":");
		double userSpeed = Input.instance.getDouble("Enter Speed Value: ");
		this.setSpeed(userSpeed);
		double userHealth= Input.instance.getDouble("Enter Health Value: ");
		this.setHealth(userHealth);
		double userStrength = Input.instance.getDouble("Enter Strength Value: ");
		this.setStrength(userStrength);
		String userName = Input.instance.getString("Enter Name: ");
		this.setName(userName);
	}
	
	//Method set is used to manually set certain actor variables within the boundaries set
	//Method get allows the actor variables to be accessed outside its class
	/** Method <i>setHealth<i> is used set the health variable according to boundaries set, snapping to either the Minimum or Maximum value if user inputed value does not match boundaries
	 * 
	 * @param health
	 */
	public void setHealth(double health) {
		if (health < MIN_HEALTH){
			health = MIN_HEALTH;
			 System.out.println("Health below boundary setting to MIN_HEALTH");
		}
		else if (health > MAX_HEALTH){
			health = MAX_HEALTH;
			 System.out.println("Health above boundary setting to MAX_HEALTH");
		}
		this.health.set(health);;
	}
	/** Method <i>setStrength<i> used to manually set the strength value according to boundaries set, snapping to either the Minimum or Maximum value if user inputed 
	 * value does not match boundaries
	 * 
	 * @param strength
	 */
	public void setStrength(double strength) {
		if (strength < MIN_STRENGTH){
			strength = MIN_STRENGTH;
			System.out.println("Strength below boundary setting to MIN_STRENGTH");
		}
		else if (strength > MAX_STRENGTH){
			strength = MAX_STRENGTH;
			System.out.println("Strength above boundary setting to MAX_STRENGTH");
		}
		this.strength.set(strength);
	}
	/**Method <i>setSpeed<i> is used set the speed variable according to boundaries set, snapping to either the Minimum or Maximum value if user inputed 
	 * value does not match boundaries
	 * 
	 * @param speed
	 */
	public void setSpeed(double speed) {
		if (speed < MIN_SPEED){
			speed = MIN_SPEED;
		    System.out.println("Speed below boundary setting to MIN_SPEED");
		}
		else if (speed > MAX_SPEED) {
			speed = MAX_SPEED;
		    System.out.println("Speed over boundary, setting to MAX_SPEED");
		}
		this.speed.set(speed);
	}
	/** Method <i>setName<i> is used to manually set the variable name of the actor object
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name.set(name);;
	}
	/** Method <i>getSpeed<i>used to return speed instance variable, used in later assignments for JavaFX Tables
	 * 	
	 * @return speed
	 */
	public  double getSpeed() {
		return speed.get();
	}
	/**Method <i>getHealth<i> used to return health instance variable, used in later assignments for JavaFX Tables
	 * 
	 * @return health
	 */
	public double getHealth() {
		return health.get();
	}
	/** Method <i>getStrength<i> used to return strength instance variable, used in later assignments for JavaFX Tables
	 * 
	 * @return strength
	 */
	public double getStrength() {
		return strength.get();
	}
	/** Method <i> getName<i> used to return String name instance variable, used in later assignments for JavaFX Tables
	 * 
	 * @return name
	 */
	public String getName(){
		return name.get();
	}
	
	/** Method <i>setRandomLocationAroundPoint<i> is used to randomly allocate battlefildAvatar's X and Y values
	 * @param ptCenterOfDistribution 
	 * @param spread
	 */
	public void setRandomLocationAroundPoint(Point2D ptCenterOfDistribution, double spread) {
		final double range = 50/2.0;
		battlefieldAvatar.setTranslateX(SingletonRandom.instance.getNormalDistribution(ptCenterOfDistribution.getX()-range, ptCenterOfDistribution.getX()+190, spread));
		battlefieldAvatar.setTranslateY(SingletonRandom.instance.getNormalDistribution(ptCenterOfDistribution.getY()-range, ptCenterOfDistribution.getY()+range, spread));
		 }

	
	//toString method is part of object class, but is not set to properly display the information you desire
	//by making toString and placing a return that can properly display the object.
	/**
	 * Method <i>toString<i> exists for every object,but is undefined. <i>
	 * toString <i> is now populated with information that will successfully
	 * output <i>Actor<i> subclass objects
	 * 
	 */
	public String toString() {
		return String.format("Name:%-20s Health:%.4f Strength:%.4f Speed:%.4f", getName(), getHealth(), getStrength(), getSpeed());
	}
	
	/** Method <i>createTable<i> is used to create a TableView in the battlefield simulator, this can be used to show army tables in a 
	 * grid like table for easy viewing and sorting.
	 * 
	 * @return table
	 */
	public static TableView<Actor> createTable() {
	        TableView<Actor> table = new TableView<Actor>();
	    final double PREF_WIDTH_DOUBLE = 50.0;
	    table.setPrefWidth(PREF_WIDTH_DOUBLE*8.0);
	        table.setEditable(true);
	        TableColumn<Actor, String> nameCol            = new TableColumn<>("Name");            nameCol.setCellValueFactory     (new PropertyValueFactory<Actor, String>("name"));        nameCol.setPrefWidth(PREF_WIDTH_DOUBLE*2.0);
	        TableColumn<Actor, Double> strengthCol    = new TableColumn<>("Strength");    strengthCol.setCellValueFactory (new PropertyValueFactory<Actor, Double>("strength"));  strengthCol.setPrefWidth(PREF_WIDTH_DOUBLE);
	        TableColumn<Actor, Double> speedCol            = new TableColumn<>("Speed");       speedCol.setCellValueFactory    (new PropertyValueFactory<Actor, Double>("speed"));     speedCol.setPrefWidth(PREF_WIDTH_DOUBLE);
	        TableColumn<Actor, Double> healthCol        = new TableColumn<>("Health");      healthCol.setCellValueFactory   (new PropertyValueFactory<Actor, Double>("health"));    healthCol.setPrefWidth(PREF_WIDTH_DOUBLE);
	        // START CODE TEST: Following code DOES WORK, but it is NOT generic.
	    TableColumn locationXCol = new TableColumn("X");  locationXCol.setPrefWidth(PREF_WIDTH_DOUBLE);
	    locationXCol.setCellValueFactory(new Callback<CellDataFeatures<Actor, Double>, ObservableDoubleValue>() {
	        public ObservableDoubleValue call(CellDataFeatures<Actor, Double> actor) { return actor.getValue().battlefieldAvatar.translateXProperty(); }});
	    TableColumn locationYCol = new TableColumn("Y");  locationYCol.setPrefWidth(PREF_WIDTH_DOUBLE);
	    locationYCol.setCellValueFactory(new Callback<CellDataFeatures<Actor, Double>, ObservableDoubleValue>() {
	        public ObservableDoubleValue call(CellDataFeatures<Actor, Double> actor) { return actor.getValue().battlefieldAvatar.translateYProperty(); }});
	        // END CODE TEST: Following code DOES WORK, but it is NOT generic.
	 
	    table.getColumns().addAll(nameCol, strengthCol, speedCol, healthCol, locationXCol, locationYCol);
	    nameCol.setCellFactory(TextFieldTableCell.<Actor>forTableColumn());
	    nameCol.setOnEditCommit(new EventHandler<CellEditEvent<Actor, String>>()      { @Override public void handle(CellEditEvent<Actor, String> t) { Actor a = (t.getTableView().getItems().get(t.getTablePosition().getRow())); a.setName(t.getNewValue()); a.adjustAvatarBasedOnActorAttributes(); }}); // end setOnEditCommit()

	    strengthCol.setCellFactory(TextFieldTableCell.<Actor,Double>forTableColumn(new DoubleStringConverter()));
	    strengthCol.setOnEditCommit(new EventHandler<CellEditEvent<Actor, Double>>()  {
	        @Override public void handle(CellEditEvent<Actor, Double> t) {
	            Actor a = (t.getTableView().getItems().get(t.getTablePosition().getRow())); 
	            try {
	                a.setStrength(t.getNewValue()); 
	                a.adjustAvatarBasedOnActorAttributes();
	       
	            } catch (IllegalArgumentException iae) {
	                // change to property was rejected, so old value remains unchanged, but the TableView says otherwise
	                Double d = t.getOldValue(); // No change to view, but it does retrieve the previous value.
	                System.out.println(d);
	            }
	            }
	        }); // end setOnEditCommit()

	    speedCol.setCellFactory(TextFieldTableCell.<Actor,Double>forTableColumn(new DoubleStringConverter()));
	    speedCol.setOnEditCommit(new EventHandler<CellEditEvent<Actor, Double>>()     { @Override public void handle(CellEditEvent<Actor, Double> t) { Actor a = (t.getTableView().getItems().get(t.getTablePosition().getRow())); a.setSpeed(t.getNewValue()); a.adjustAvatarBasedOnActorAttributes(); }}); // end setOnEditCommit()

	    healthCol.setCellFactory(TextFieldTableCell.<Actor,Double>forTableColumn(new DoubleStringConverter()));
	    healthCol.setOnEditCommit(new EventHandler<CellEditEvent<Actor, Double>>()    { @Override public void handle(CellEditEvent<Actor, Double> t) { Actor a = (t.getTableView().getItems().get(t.getTablePosition().getRow())); a.setHealth(t.getNewValue()); a.adjustAvatarBasedOnActorAttributes(); }}); // end setOnEditCommit()
	        return table;
	    } // end createTable()
	  //end of createTable()
	
	/** Method <i>adjustAvatarBasedOnActarAttributes<i> adjusts the battlefieldAvatar size based on the actor's strength and health values
	 * It also allows the battlefildAvatar's to acquire a mouseover tooltip for their toString implementation
	 */
	private void adjustAvatarBasedOnActorAttributes() { 
		  battlefieldAvatar.setScaleX((getStrength()+getHealth())/100.0); battlefieldAvatar.setScaleY((getStrength()+getHealth())/100.0);
		  tooltip.setText(toString());
		  }
		
	/** Abstract Method <i>findNewLocationToMoveTo<i>, declared in subclass level */
	public abstract Point2D findNewLocationToMoveTo();
	
	/** Method <i>isHealthyEnoughToMove<i> returns boolean value if the the actor's health
	 * is below the 30% threshold.
	 * @return true : false
	 */
	public boolean isHealthyEnoughToMove() {
		 return (getHealth() > MAX_HEALTH * THRESHOLD_OF_ADEQUATE_HEALTH) ? true : false; 
		 }



	/** Method <i>move<i> is a key feature in the movement in battlefieldAvatar's, the avatar can move either move or rotate
	 * if the ptNewLocation return's null, than the avatar will be put under a indefinite transition that will rotate 360 degrees.
	 * If ptNewLocation is not null, the avatar will be set with a transition with a 2 second delay, 100 second duration and the
	 * X and Y value that has been set
	 */
	public void move() {
		   /** Call once to begin motion; each call to move() invokes one short-term animation; an "onFinished" CALLBACK is set to call move() again; looks recursive BUT IT IS NOT (I call it chained). */
		    adjustAvatarBasedOnActorAttributes();
		 Point2D ptNewLocation = findNewLocationToMoveTo(); // COULD RETURN NULL if not moving
		 if (ptNewLocation == null) { // don't move . . . should be in a rotate mode
		 transition = RotateTransitionBuilder.create() // look around then invoke move() when finished looking
		 .node(battlefieldAvatar)
		       .fromAngle(0.0)
		 .toAngle(360.0)
		       .cycleCount(2) // if the Actor isn't yet healthy enough to move, then this version of move() (a RotateTransition) will be reasserted again and again.
		       .autoReverse(true)
		 .duration(Duration.seconds(300.0/getSpeed()))
		 .onFinished(new EventHandler<ActionEvent>() { @Override public void handle(ActionEvent arg0) { move(); }}) // Looks recursive, but not really . . . it is chained, where the termination of a transition calls move() to spawn another.
		 .build();
		 transition.play();
		 } else { // found a new location . . . move towards
		 transition = TranslateTransitionBuilder.create()
		 .node(battlefieldAvatar)
		 .delay(Duration.seconds(0))
		 .duration(Duration.seconds(40))
		 .toX(ptNewLocation.getX())
		 .toY(ptNewLocation.getY())
		 .onFinished(new EventHandler<ActionEvent>() { 
			 @Override public void handle(ActionEvent arg0) { 
				 move();
				 }
			 })  // Looks recursive, but not really . . . it is chained, where the termination of a transition calls move() to spawn another.
		 .build();
		 transition.play();
		 } 
		 }

	/** Method <i>suspend<i> pauses all current transitions that are taking place
	 */
	 public void suspend() {
		 if (transition != null) {
		 transition.stop();
		 transition = null;
		 }
		 }
	}


	
	




