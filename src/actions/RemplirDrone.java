package actions;
import map.*;
import robots.*;
import simulation.Evenement;

/**
 * 
 * Evenement remplir un drone sur une case d'eau
 *
 */
public class RemplirDrone extends Evenement{
	
	private Robot robot;
	
	/**
	 * Constructeur
	 * @param dateDebut La date à laquelle l'évènement doit commencer 
	 * @param robot
	 * @param carte
	 */
	public RemplirDrone(long dateDebut, Robot robot, Carte carte) {
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
	
	/**
	 * 
	 * @return vrai si l'action est légale, faux sinon
	 */
	public boolean isLegalEvent() {
		if (robot.getPosition().getNature().equals(NatureTerrain.EAU)) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return robot.getPosition().toString() + " Robot rempli !";
	}
}