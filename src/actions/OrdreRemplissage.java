package actions;
import robots.*;
import simulation.Evenement;
import simulation.Simulateur;
import map.*;

/**
 * 
 * Ordre de début d'évenement de remplissage. Lorsqu'executé va créer un évenement RemplirDrone ou RemplirRobot
 *
 */
public class OrdreRemplissage extends Evenement {
	
	private Robot robot;
	private Simulateur simulation;
	
	/**
	 * Constructeur
	 * @param date
	 * @param carte
	 * @param robot
	 * @param simulation
	 */
	public OrdreRemplissage(long date, Carte carte, Robot robot, Simulateur simulation) {
		super(date, carte);
		this.robot = robot;
		this.simulation = simulation;
	}
	
	/**
	 * Execute l'action liée à l'évènement
	 */
	public String execute() {
		if (!robot.isOccupe()) {
			long dateRemplissage = this.calculDateRemplissage();
			if (robot instanceof Drone && this.isLegalEventDrone()) {
				simulation.ajouteEvenement(new RemplirDrone(dateRemplissage, robot, this.getCarte()));
			} else if (this.isLegalEventRobot()){
				simulation.ajouteEvenement(new RemplirRobot(dateRemplissage, robot, this.getCarte()));
			} else {
				return "Pas moyen de remplir le réservoir chef";
			}
			robot.setOccupe(true);
			System.out.println(this.toString());
			return this.toString();
		}
		return "Pas moyen de remplir le réservoir chef";
	}
	
	/**
	 * 
	 * @return vrai si l'action est légale dans le cas d'un drone, faux sinon
	 */
	public boolean isLegalEventDrone() {
		return robot.getPosition().getNature().equals(NatureTerrain.EAU);
	}
	
	/**
	 * 
	 * @return vrai si l'action est légale dans le cas d'un robot terrestre, faux sinon
	 */
	public boolean isLegalEventRobot() {
		if (this.getCarte().voisinExiste(robot.getPosition(), Direction.NORD) && this.getCarte().getVoisin(robot.getPosition(), Direction.NORD).getNature().equals(NatureTerrain.EAU)) {
			return true;
		} else if (this.getCarte().voisinExiste(robot.getPosition(), Direction.EST) && this.getCarte().getVoisin(robot.getPosition(), Direction.EST).getNature().equals(NatureTerrain.EAU)) {
			return true;
		} else if (this.getCarte().voisinExiste(robot.getPosition(), Direction.SUD) && this.getCarte().getVoisin(robot.getPosition(), Direction.SUD).getNature().equals(NatureTerrain.EAU)) {
			return true;
		} else if (this.getCarte().voisinExiste(robot.getPosition(), Direction.OUEST) && this.getCarte().getVoisin(robot.getPosition(), Direction.OUEST).getNature().equals(NatureTerrain.EAU)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Calcule la date de fin de remplissage 
	 * @return La date de fin
	 */
	public long calculDateRemplissage() {
		System.out.println(robot.getTempsRemplissage());
		System.out.println(this.getDate());
		return ((long) this.getDate() + robot.getTempsRemplissage() * (robot.getTankCapacity() - robot.getTankLevel())/robot.getTankCapacity());
	}
	
	public String toString() {
		return "Ordre Remplissage " + robot.getPosition().toString() + " " + (new Date(this.calculDateRemplissage()));
	}

	public Simulateur getSimulation() {
		return simulation;
	}
}