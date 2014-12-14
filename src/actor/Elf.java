package actor;

import javafx.animation.RotateTransitionBuilder;
import javafx.animation.TranslateTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.RectangleBuilder;
import javafx.util.Duration;
import army.Army;
import util.Input;
import util.InputGUI;
import util.SingletonRandom;
/** The <i>Elf<i> class is a subclass of the superclass <i>Actor<i>. <i>ELf<i> class uses the <i>Actor<i> super class
 * to create Elf actors, including their unique attributes,archeryLevel and wisdom
 * @author Brodie
 *
 */

public class Elf extends Actor {
	/**double instance variable archeryLevel for the <i>Elf<i> Class. */
	private double archeryLevel;
	/** static final double used to set Min value boundaries on the <i>Elf<i>class instance variable archeryLevel. The archeryLevel value will adhere to its Min value. @value(MIN_ARCHERY_LEVEL) */
	private static double MIN_ARCHERY_LEVEL = 20;
	/** static final double used to set Max value boundaries on the <i>ELf<i> class instance variable archeryLevel. The archeryLevel value will adhere to its Max value. @value(MAX_ARCHERY_LEVEL) */
	private static double MAX_ARCHERY_LEVEL = 100;
	/** double instance variable wisdom for the <i>Elf<i> Class. */
	private double wisdom;
	/** static final double used to set Max value boundaries on the <i>Elf<i> class instance variable wisdom. The wisdom value will adhere to its Min value. @value(MIN_WISDOM) */
	private static double MIN_WISDOM = 20;
	/** static final double used to set Max value boundaries on the <i>Elf<i> class instance variable wisdom. The wisdom value will adhere to its Max value. @value(MAX_WISDOM) */
	private static double MAX_WISDOM = 100;
	/**Static int used to keep track of <i>Elf<i> actors created, organizational naming tool */
	private static int elfSerialNumber = 1;

	/**<i>Elf<i> Constructor used to randomly assign and initialize instance variables.The Constructor calls the superclass constructor
	 * with parameters elfSerialNumber to add numerical values to <i>Troll<i> actors incrementally, and another armyAllegiance arguement.archeryLevel variable and wisdom variable are randomly
	 * assigned via <i>SingletonRandom<i> and using Gaussian Distribution
	 * @param armyAllegiance
	 */
	public Elf(Army armyAllegiance) {
		super(elfSerialNumber++,armyAllegiance);
		archeryLevel = SingletonRandom.instance.getNormalDistribution(MIN_ARCHERY_LEVEL, MAX_ARCHERY_LEVEL, 3.0);
		wisdom = SingletonRandom.instance.getNormalDistribution(MIN_WISDOM, MAX_WISDOM, 3.0);	
	}
	
	/**Method <i>getArcheryLevel<i> used to return double value instance variable archeryLevel
	 * @return archeryLevel
	 */
	public double getArcheryLevel() {
		return archeryLevel;
	}
	
	/**Method <i>setArcheryLevel<i> is used set the archeryLevel variable according to boundaries set, snapping to either the Minimum or Maximum value if user inputed
	 * value does not match boundaries. Added print out's to tell user if the set method has to lower or raise the archeryLevel value to either Max or Min Value.
	 * @param archeryLevel
	 */
	public void setArcheryLevel(double archeryLevel) {
		if (archeryLevel < MIN_ARCHERY_LEVEL){
			archeryLevel = MIN_ARCHERY_LEVEL;
			System.out.println("Archery Level below boundary setting to MIN_ARCHERY_LEVEL");
		}
		else if (archeryLevel > MAX_ARCHERY_LEVEL){
			archeryLevel = MAX_ARCHERY_LEVEL;
			System.out.println("Archery Level above boundary setting to MAX_ARCHERY_LEVEL");
		}
		this.archeryLevel = archeryLevel;
	}
	
	/**Method <i>getWisdom<i> used to return double value instance variable wisdom
	 * @return wisdom
	 */
	public double getWisdom() {
		return wisdom;
	}
	
	/**Method <i>setWisdom<i> is used set the wisdom variable according to boundaries set, snapping to either the Minimum or Maximum value if user inputed
	 * value does not match boundaries. Added print out's to tell user if the set method has to lower or raise the wisdom value to either Max or Min Value.
	 * @param wisdom
	 */
	public void setWisdom(double wisdom) {
		if (wisdom < MIN_WISDOM){
			wisdom = MIN_WISDOM;
			System.out.println("Wisdom below boundary setting to MIN_WISDOM");
		}
		else if (wisdom > MAX_WISDOM){
			wisdom = MAX_WISDOM;
			System.out.println("Wisdom above boundary setting to MAX_WISDOM");
		}
		this.wisdom = wisdom;
	}
	/**Method <i>inputAllFields<i> is used to manually set all <i>Elf<i>variables, as well as the super class <i>Actor<i> variables.The inputAllFields
	 * super method is called. For archeryLevel, user input is done via <i>Input<i> class and then plugged into setArcheryLevel() method to ensure the boundaries are working.
	 * For wisdom, user input is done via <i>Input<i<> class and then plugged into setWisdom() method to ensure the boundaries are working.
	 */
	@Override
	public void inputAllFields() {
		super.inputAllFields();
		double userAcheryLevel = Input.instance.getDouble("Enter elf's archery level: ");
		this.setArcheryLevel(userAcheryLevel);
		double userWisdom = Input.instance.getDouble("Enter Elf's wisdom: ");
		this.setWisdom(userWisdom);
	}
	
	/** Method <i>buildBattleFieldAvatar<i>, builds the avatar for the subclass Actor object with the specified properties
	 */
	 @Override
	 protected Node buildBattleFieldAvatar() {
		 return RectangleBuilder.create().width(5.0).height(10.0).fill(Color.AZURE).build();
	 }
	
	/**Method <i>toString<i> overrides the super method toString. The toString method returns a string value to used to successfully print <i>Elf<i> objects.
	 */
	@Override
	public String toString() {
		return String.format("%s ArcheryLevel:%.4f Wisdom:%.4f", super.toString(),archeryLevel, wisdom);
	}
	
	/** Method <i>findNewLocationToMoveTo<i> is used to give the subclass actor object the information on where to move
	 * <i>findNewLocationToMoveTo<i> uses the scene's width and height value to apply boundaries to the x and y coordinates
	 * An opposingArmy is created to track the enemy, <i>findNearestOpponent<i> is called to calculate which opposingActor is
	 * the closest in terms of the X and Y coordinates. If the X and Y coordinates are not within the boundary, the values are changed accordingly
	 * The X and Y are then returned as a Point2D object with a SingletonRandom applied to allow a spread of the battlefieldAvatar's
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
