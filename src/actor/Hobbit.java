package actor;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.CircleBuilder;
import army.Army;
import util.Input;
import util.InputGUI;
import util.SingletonRandom;
/** The <i>Hobbit<i> class is a subclass of the superclass <i>Actor<i>. <i>Hobbit<i> class uses the <i>Actor<i> super class
 * to create Hobbit actors, including their unique attribute stealth
 * @author Brodie Taillefer
 *
 */

public class Hobbit extends Actor {
	/** final static double used to set boundaries on the <i>hobbit<i> class variable stealth. The stealth value will adhere 
	 * to its boundaries */
	private final static double MIN_STEALTH = 10;
	/** final static double used to set Max value on <i>hobbit<i> class variable stealth. The stealth value will adhere
	 * to its set boundary */
	private final static double MAX_STEALTH = 100;
	/**  Static int used to keep track of hobbit actors created, organizational naming tool */
	private static int hobbitSerial = 1;
/** double stealth variable, hobbit unique attribute */
	private double stealth;
	/** <i>Hobbit<i> constructor used to randomly assign instance variables. The Constuctor calls the superclass constructor
	 * with parameters hobbitSerial to add numerical values to <i>Hobbit<i> actors incrementlly.
	 * Stealth variable is assigned using the <i>SingletonRandom<i> class
	 * @param armyAllegiance 
	 */
	
	public Hobbit(Army armyAllegiance) {
		super(hobbitSerial++,armyAllegiance);
		stealth = SingletonRandom.instance.getNormalDistribution(MIN_STEALTH, MAX_STEALTH, 2.0);
	}
	
	/** Method <i>getStealth<i>used to return stealth instance variable, used in later assignments for JavaFX Tables 
	 * 
	 * @return stealth
	 */
	public double getStealth() {
		return stealth;
	}
	
	/**Method <i>setStealth<i> is used set the speed variable according to boundaries set, snapping to either the Minimum or Maximum value if user inputed
	 * value does not match boundaries
	 */
	public void setStealth(double stealth) {
		if (stealth < MIN_STEALTH){
			stealth = MIN_STEALTH;
			 System.out.println("Stealth below boundary setting to MIN_SPEED");
		}
		else if (stealth > MAX_STEALTH){
			stealth = MAX_STEALTH;
			System.out.println("Stealth above boundary setting to MAX_SPEED");
		}
		this.stealth = stealth;
	}
	/** Method <i>inputAllFields<i> is used to manually set all <i>Hobbit<i>variables, as well as the super class <i>Actor<i> variables.The inputAllFields
	 * super method is called. Stealth  Input is done by the class <i>Input<i> , and then plugged into setStealth() method to ensure the boundaries work.
	 */
	@Override
	public void inputAllFields() {
		super.inputAllFields();
		double userStealth = Input.instance.getDouble("Enter stealth value: ");
		this.setStealth(userStealth);
	}
	
	/** Method <i>buildBattleFieldAvatar<i>, builds the avatar for the subclass Actor object with the specified properties */
	 @Override
	 protected Node buildBattleFieldAvatar() {
		 return  CircleBuilder.create().radius(5.0).strokeWidth(2.0).fill(Color.GREEN).build();
	 }
	 
	/** Method <i>toString<i> exists for every object,but is undefined. The superclass method toString is called to use utilise the <i>Actor<i> toString
	 * <i> toString <i> is now populated with information that can sucessfully output the <i>Hobbit<i> objects.
	 */
	@Override
	public String toString() {
		return String.format("%s Stealth: %f",super.toString(),stealth); 
	
	/** Method <i>findNewLocationToMoveTo<i> is used to give the subclass actor object the information on where to move
	 * <i>findNewLocationToMoveTo<i> uses the scene's width and height value to apply boundaries to the x and y coordinates
	 * An opposingArmy is created to track the enemy, <i>findNearestOpponent<i> is called to calculate which opposingActor is
	 * the closest in terms of the X and Y coordinates. If the X and Y coordinates are not within the boundary, the values are changed accordingly
	 * The X and Y are then returned as a Point2D object with a SingletonRandom applied to allow a spread of the battlefieldAvatar's.
	 * With Hobbit the X and Y coordinates are inverted to allow the Hobbit and enemies to never fully reach eachother
	 */
	}
	@Override
	public Point2D findNewLocationToMoveTo(){
		double sceneWidth = this.getBattleFieldAvatar().getScene().getWidth();
		double sceneHeight = this.getBattleFieldAvatar().getScene().getHeight();
		double range = 30.0;
		
		Army opposingArmy = armyAllegiance.getOpposingArmy();
		Node myAvatar = buildBattleFieldAvatar();
		Actor nearestOpponent = opposingArmy.findNearestOpponent(this);
		double targetX = nearestOpponent.getBattleFieldAvatar().getTranslateX();
		double targetY = nearestOpponent.getBattleFieldAvatar().getTranslateY();
		
		if (targetX < 0.0) {
			targetX = range + myAvatar.getTranslateX() ;
		}
		else if (targetX > sceneWidth) {
			targetX = sceneWidth - range;
		}
		if (targetY < 0.0) {
			targetY = range;
		}
		else if (targetY > sceneHeight) {
			targetY = sceneHeight - range;
		}
		
		if (isHealthyEnoughToMove())
		return  new Point2D(targetY * SingletonRandom.instance.getNormalDistribution(1.01, 1.04, 2.0) , targetX * SingletonRandom.instance.getNormalDistribution(1.01, 1.04, 2.0) );
		else
		return null;
    }
	
}
