package actor;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.CircleBuilder;
import army.Army;
import util.Input;
import util.InputGUI;
import util.SingletonRandom;
/** The <i>Orc<i> class is a subclass of the superclass <i>Actor<i>. <i>Orc<i> class uses the <i>Actor<i> super class
 * to create Orc actors, including their unique attributes, rage and hasArmour.
 * @author Brodie Taillefer
 *
 */
public class Orc extends Actor {
	/** double rage value, Orc unique instance variable */
	private double rage;
	/** static final double used to set Min value boundaries on the class instance variable rage. The rage value will adhere to its Min value. */
	private static final double MIN_RAGE = 20 ;
	/** static final double used to set max value boundaries on the class instance variable rage. The rage value will adhere to its Max value. */
	private static final double MAX_RAGE = 100;
	/**boolean hasArmour value,Orc unique instance variable. Return's true or false if the Orc has armour. */
	private boolean hasArmour;
	/** static int used to track the amount of Orc objects created, organizational naming tool */
	private static int orcSerialNumber = 1;
	
	/** <i>Orc<i> Constructor used to randomly assign and initialize instance variables.The Constructor calls the superclass constructor
	 * with parameters orcSerialNumber to add numerical values to <i>Orc<i> actors incrementally. Rage variable is randomly assigned using the 
	 * SingletonRandom class using Gaussian distribution. hasArmour is randomly assigned using Math.random, with a 60% chance of the Orc having his
	 * Armour
	 * @param armyAllegiance
	 */
	public Orc(Army armyAllegiance) {
		super(orcSerialNumber++,armyAllegiance);
		rage = SingletonRandom.instance.getNormalDistribution(MIN_RAGE, MAX_RAGE, 3.0);
		hasArmour = (Math.random() > 0.40) ? true : false ;
	}
	
	/**String return method <i>getHasArmour<i> used to output if the Orc has Armour or not.
	 * 
	 * @return hasArmour
	 */
	public String getHasArmour() {
		return hasArmour ? "Has Armour" : "Does not have Armour";
	}
	/** Method <i>setHasArmour<i> used to manually set the Orc's hasArmour value. Input is done by the <i>InputGUI<i> class
	 * and returns either a true or false value.
	 */
	public void setHasArmour() {
		hasArmour = InputGUI.getBooleanGUI("Does the Orc have armour?");
	}
	/** Method used to return Orc rage instance variable
	 * 
	 * @return rage
	 */
	public double getRage() {
		return rage;
	}
	/**Method <i>setRage<i> is used set the rage variable according to boundaries set, snapping to either the Minimum or Maximum value if user inputed
	 * value does not match boundaries. Added print out's to tell user if the set method has to lower or raise the rage value to either Max or Min Value.
	 * 
	 * @param rage
	 */
	public void setRage(double rage) {
		if (rage < MIN_RAGE){
			rage = MIN_RAGE;
			 System.out.println("Rage below boundary setting to MIN_RAGE");
		}
		else if (rage > MAX_RAGE){
			rage = MAX_RAGE;
			 System.out.println("Rage above boundary setting to MAX_RAGE");
		}
		this.rage = rage;
	}
	/**Method <i>inputAllFields<i> is used to manually set all <i>Orc<i>variables, as well as the super class <i>Actor<i> variables.The inputAllFields
	 * super method is called. Rage Input is done by the class <i>Input<i> , and then plugged into setRage() method to ensure the boundaries work.
	 * <i>inputAllFields<i> overrides the super inputAllfields().
	 * 
	 */
	@Override
	public void inputAllFields() {
		super.inputAllFields();
		double userRage = Input.instance.getDouble("Enter orcs current rage: ");
		this.setRage(userRage);
		hasArmour = InputGUI.getBooleanGUI("Does the orc have armour?");
	}
	
	/** Method <i>buildBattleFieldAvatar<i>, builds the avatar for the subclass Actor object with the specified properties
	 */
	 @Override
	 protected Node buildBattleFieldAvatar() {
		 return CircleBuilder.create().radius(5.0).strokeWidth(2.0).fill(Color.BLACK).build();
	 }
	/** Method <i>toString<i> exists for every object,but is undefined. The superclass method toString is called to use utilize the <i>Actor<i> toString
	 * <i> toString <i> is now populated with information that can successfully output <i>Orc<i> objects.
	 */
	@Override
	public String toString() {
		return String.format("%s Rage: %4f %s",super.toString(),rage,getHasArmour());
	}
	
	/** Method <i>findNewLocationToMoveTo<i> is used to give the subclass actor object the information on where to move
	 * <i>findNewLocationToMoveTo<i> uses the scene's width and height value to apply boundaries to the x and y coordinates
	 * An opposingArmy is created to track the enemy, <i>findNearestOpponent<i> is called to calculate which opposingActor is
	 * the closest in terms of the X and Y coordinates. If the X and Y coordinates are not within the boundary, the values are changed accordingly
	 * The X and Y are then returned as a Point2D object with a SingletonRandom applied to allow a spread of the battlefieldAvatar's.
	 */
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
		double myX = this.getBattleFieldAvatar().getTranslateX();
		double myY = this.getBattleFieldAvatar().getTranslateY();
		
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
		
		if (isHealthyEnoughToMove()){
			if (myX - targetX > 40 && myY - targetY > 40){
				if(targetX - myX < 40 && targetY - myY < 40){
					
				return new Point2D(myX,myY);
				}
			}
		}
		if (isHealthyEnoughToMove())
		return  new Point2D(targetX *  SingletonRandom.instance.getNormalDistribution(0.98, 1.02, 2.0) ,targetY *  SingletonRandom.instance.getNormalDistribution(0.98, 1.02, 2.0));
		else
		return null;
    }
    

}
