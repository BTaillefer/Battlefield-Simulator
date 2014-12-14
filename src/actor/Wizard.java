package actor;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.RectangleBuilder;
import army.Army;
import util.Input;
import util.InputGUI;
import util.SingletonRandom;

 /** The <i>Wizard<i> class is a subclass of the superclass <i>Actor<i>. The <i>Wizard<i> class uses the <i>Actor<i>
 * super class to create <i>Wizard<i> objects, including their unique attributes, hasStaff and hasHorse.
 * @author Brodie
 *
 */
public class Wizard extends Actor {
	/** Boolean value which is unique to the <i>Wizard<i> class, determines if the wizard has a staff */
	private boolean hasStaff;
	/** Boolean value which is unique to the <i>Wizard<i> class, determines if the wizard has a horse */
	private boolean hasHorse;
	/** Static int value used to keep track of created, <i>Wizard<i> objects */
	private static int wizardSerial = 1;
	
	/** <i>Wizard<i> constructor used to randomly assign the superclass <i>Actor<i> objects. The <i>Actor<i>
	 * super constructor is called with parameters wizardSerial to keep track of the Wizard objects created
	 * The Math.random class is used to assign the wizard's unique values of hasStaff and hasHorse.
	 * 90% chance the wizard has his staff, and 10% chance the wizard has his horse
	 * @param armyAllegiance 
	 */
	
	public Wizard(Army armyAllegiance) {
		super(wizardSerial++,armyAllegiance);
		hasStaff = (Math.random() <0.90) ? true : false;
		hasHorse = (Math.random() <0.10) ? true : false;	
	}
	/**String method to successfully output if the wizard has his staff or not
	 * @return hasStaff
	 */
	public String hasStaffString() {
		return hasStaff ? "Has Staff" : "Does not have Staff";
	}
	
	/** String method to successfully output if the wizard has his horse or not
	 * 
	 * @return hasHorse
	 */
	public String hasHorseString() {
		return hasHorse ? "Has Horse" : "Does not have Horse";
	}
	
	/**  Method used to manually set the wizard's hasHorse value. Input is done by the <i>InputGUI<i> class
	 * and returns either a true or false value.
	 */
	public void setHasHorse() {
		hasHorse = InputGUI.getBooleanGUI("Does the Wizard have his horse?");
	}
	
	/** Method used to manually set the wizard's hasStaff value. Input is done by the <i>InputGUI<i> class
	 * and returns either a true or false value.
	 */
	public void setHasStaff() {
		hasStaff = InputGUI.getBooleanGUI("Does the Wizard have his staff?");
	}
	
	/** String method <i>toString<i> populated with valuable information to successfully output
	 * <i>Wizard<i> objects. The <i>Actor<i> superclass toString method is called to output the
	 * default <i>Actor<i> instance variables
	 */
	@Override
	public String toString() {
		return String.format("%s %s %s",super.toString(),hasStaffString(), hasHorseString());
	}
	
	/** Method <i>buildBattleFieldAvatar<i>, builds the avatar for the subclass Actor object with the specified properties */
	 @Override
	 protected Node buildBattleFieldAvatar() {
		 return RectangleBuilder.create().width(10.0).height(20.0).fill(Color.GOLD).build();
	 }

	/** Method used to allow user to input all instance variables for <i>Wizard<i> objects. The super inputAllFields
	 * is called, to set all <i>Actor<i> instance variables, and <i>InputGUI<i> used to set hasStaff and hasHorse
	 */
	@Override
	public void inputAllFields(){
		super.inputAllFields();
		hasStaff = InputGUI.getBooleanGUI("Does the Wizard have his staff?");
		hasHorse = InputGUI.getBooleanGUI("Does the Wizard have his horse?");
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
