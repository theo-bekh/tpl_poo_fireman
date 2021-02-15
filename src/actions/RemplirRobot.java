package actions;
import map.*;
import robots.*;
import simulation.Evenement;

/**
 * 
 * Evènement qui remplit le robot à côté d'une case d'eau
 *
 */
public class RemplirRobot extends Evenement{
	
	private Robot robot;
	
	/**
	 * Constructeur
	 * @param dateDebut La date à laquelle l'évènement doit commencer 
	 * @param robot
	 * @param carte
	 */
	public RemplirRobot(long dateDebut, Robot robot, Carte carte) {
		super(dateDebut, carte);
		this.robot = robot;
	}
	
	/**
	 * Execute l'action liée à l'évènement
	 */
	public String execute() {
		robot.setTankLevel(robot.getTankCapacity());
		robot.setOccupe(false);
		return this.toString();
	}
	
	/**
	 * Retourne
	 * @param dateDebut
	 * @return la date d'execution de l'évènement != de la dadte de début
	 */
	public long calculDateExecution(long dateDebut) {
		long dateExecution;
		dateExecution = (long) (dateDebut + (robot.getTankCapacity() - robot.getTankLevel())/robot.getDebitIntervention());
		return dateExecution;
	}
	
	@Override
	public String toString() {
		return robot.getPosition().toString() + " Robot rempli !";
	}
}