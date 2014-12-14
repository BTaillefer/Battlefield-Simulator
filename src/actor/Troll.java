package actor;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.CircleBuilder;
import army.Army;
import util.Input;
import util.InputGUI;
import util.SingletonRandom;

/** The <i>Troll<i> class is a subclass of the superclass <i>Actor<i>. <i>Troll<i> class uses the <i>Actor<i> super class
 * to create Troll actors, including their unique attributes, boolean value hasWeapon, boolean value mountainTroll, and double height.
 * @author Brodie Taillefer
 *
 */
public class Troll extends Actor {
	/**Boolean unique instance variable for the <i> Troll<i> class.*/
	private boolean hasWeapon; 
	/**Boolean unique instance variable for the <i>Troll<i> class.*/
	private boolean mountainTroll;
	/**Double unique instance variable for the <i>Troll<i> class.*/
	private double height;
	/** static final double used to set Min value boundaries on the class instance variable height. The height value will adhere to its Min value. @value(MIN_HEIGHT) */
	private static final double MIN_HEIGHT = 5;
	/** static final double used to set Max value boundaries on the class instance variable height. The rage height will adhere to its Max value. @value(MAX_HEIGHT) */
	private static final double MAX_HEIGHT = 15;
	/**Static int used to keep track of <i>Troll<i> actors created, organizational naming tool */
	private static int trollSerialNumber = 1;
	
	/**<i>Troll<i> Constructor used to randomly assign and initialize instance variables.The Constructor calls the superclass constructor
	 * with parameters trollSerialNumber to add numerical values to <i>Troll<i> actors incrementally. Height variable is randomly assigned using the 
	 * SingletonRandom class using Gaussian distribution.hasWeapon instance variable is populated with Math.random with a 70% chance the troll has his weapon.
	 * mountainTroll instance variable is populated with Math.random with a 15% chance that the troll is a mountain troll.
	 * @param armyAllegiance
	 */
	public Troll(Army armyAllegiance) {
		super(trollSerialNumber++,armyAllegiance);
		hasWeapon = (Math.random() <0.7) ? true : false;
		mountainTroll = (Math.random()) <0.15 ? true : false;
		height = SingletonRandom.instance.getNormalDistribution(MIN_HEIGHT, MAX_HEIGHT, 3.0);
	}
	/**Method <i>getHeight<i> used to return double value height.
	 * @return height
	 */
	public double getHeight() {
		return height;
	}
	
	/**Method <i>setHeight<i> is used set the height variable according to boundaries set, snapping to either the Minimum or Maximum value if user inputed
	 * value does not match boundaries. Added print out's to tell user if the set method has to lower or raise the height value to either Max or Min Value.
	 * @param height
	 */
	public void setHeight(double height) {
		if (height < MIN_HEIGHT){
			height = MIN_HEIGHT;
			 System.out.println("Height below boundary setting to MIN_HEIGHT");
		}
		else if (height > MAX_HEIGHT){
			height = MAX_HEIGHT;
			System.out.println("Height above boundary setting to MAX_HEIGHT");
		}
		this.height = height;
	}
	
	/**Method <i>getHasWeapon<i> used to return boolean value getHasWeapon, if the Troll has his weapon or not
	 * @return hasWeapon
	 */
	public String getHasWeapon() {
		return hasWeapon ? "Has Weapon" : "Does not have Weapon";
	}
	
	/**Method <i>setHasWeapon<i> used to manually set the hasWeapon variable. User Input is done via <i>InputGUI<i>.
	 * 
	 */
	public void setHasWeapon() {
		hasWeapon = InputGUI.getBooleanGUI("Does the troll have a weapon?");
	}
	
	/**Method <i>getMountainTroll<i> used to return boolean value mountainTroll, if the troll is apart of the mountain species(inherently larger and stronger)
	 * @return mountainTroll
	 */
	public String getMountainTroll() {
		return mountainTroll ? "Is a mountain troll" : "Not a mountain troll";
	}
	
	/** Method <i>setMountainTroll<i> used to manually set the value of mountainTroll.User input is done via <i>InputGUI<i>.
	 */
	public void setMountainTroll() {
		mountainTroll = InputGUI.getBooleanGUI("Is this troll a mountain troll?");
	}
	/**Method <i>inputAllFields<i> is used to manually set all <i>Troll<i>variables, as well as the super class <i>Actor<i> variables.The inputAllFields
	 * super method is called. For height, user input is done via <i>Input<i> class and then plugged into setHeight() method to ensure the boundaries are working.
	 * Boolean instance variables hasWeapon and mountainTroll are set by using the <i>InputGUI<i> class.<i>inputAllFields<i> overrides the super inputAllfields().
	 */
	@Override
	public void inputAllFields() {
		super.inputAllFields();
		double userHeight = Input.instance.getDouble("How tall is the troll, between " + MIN_HEIGHT + "-" + MAX_HEIGHT);
		this.setHeight(userHeight);
		hasWeapon = InputGUI.getBooleanGUI("Does the troll have a weapon?");
		mountainTroll = InputGUI.getBooleanGUI("Is this troll a mountain troll?");
	}
	
	/** Method <i>buildBattleFieldAvatar<i>, builds the avatar for the subclass Actor object with the specified properties */
	 @Override
	 protected Node buildBattleFieldAvatar() {
		 return CircleBuilder.create().radius(5.0).strokeWidth(2.0).fill(Color.DARKSALMON).build();
	 }
	
	/**Method <i>toString<i> overrides the super method toString. The toString method returns a string value to used to successfully print <i>Troll<i> objects.
	 */
	@Override
	public String toString() {
		return String.format("%s Height:%f %4s %4s", super.toString(),height,getHasWeapon(),getMountainTroll());
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
	
	


