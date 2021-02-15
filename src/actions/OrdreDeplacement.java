package actions;
import robots.*;
import simulation.Evenement;
import simulation.Simulateur;
import map.*;

/**
 * 
 * Evènement de début d'un déplacement. Lorsqu'executé va générer un évenement Déplacement
 *
 */
public class OrdreDeplacement extends Evenement {
	
	private Robot robot;
	private Simulateur simulation;
	private Direction direction;
	private boolean finVoyage;
	
	/**
	 * Constructeur
	 * @param date La date à laquelle l'évènement doit commencer 
	 * @param carte
	 * @param robot
	 * @param direction
	 * @param simulation
	 * @param finVoyage Vaut vrai si il s'agit de l'arrivée, faux sinon
	 */
	public OrdreDeplacement(long date, Carte carte, Robot robot, Direction direction, Simulateur simulation, boolean finVoyage) {
		super(date, carte);
		this.robot = robot;
		this.direction = direction;
		this.simulation = simulation;
		this.finVoyage = finVoyage;
	}
	
	/**
	 * Execute l'action liée à l'évènement
	 */
	public String execute() {
		if (!robot.isOccupe()) {
			long dateDeplacement = this.calculDateDeplacement();
			simulation.ajouteEvenement(new Deplacement(dateDeplacement, this.getCarte(), robot, direction, finVoyage));
			robot.setOccupe(true);
			return this.toString();
		} else {
			return "Deplacement annulé car robot occupé sorryyyyyyyy";
		}
	}
	
	/**
	 * Calcule la date effective de fin du déplacement 
	 * @return La date de fin du déplacement
	 */
	public long calculDateDeplacement() {
		long dateDeplacement;
		try {
			dateDeplacement = (long) (this.getDate() + (this.getCarte().getTailleCases()/2)*(1/robot.getSpeed(robot.getPosition().getNature()) + 1/robot.getSpeed(this.getNextCase().getNature())));
		}
		catch (NullPointerException e){
			dateDeplacement = 0;
		}
		return dateDeplacement;
	}

	public Robot getRobot() {
		return robot;
	}
	
	/**
	 * 
	 * @return la case suivante selon le déplacement
	 * Attention, ne retourne pas la même valeur selon si appelée avant ou après execute()
	 */
	public Case getNextCase() {
		return this.getCarte().getVoisin(robot.getPosition(), direction);
	}
	
	public String toString() {
		return "Ordre Deplacement " + (new Date(this.getDate())) + " " + (new Date(this.calculDateDeplacement())).toString();
	}

	public Simulateur getSimulation() {
		return simulation;
	}
	
}