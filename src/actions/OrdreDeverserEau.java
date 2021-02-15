package actions;

import robots.*;
import simulation.Evenement;
import simulation.Simulateur;
import map.*;

/**
 * 
 * Evènement de début de déversement d'eau. Lorsqu'executé, va générer un évènement DeverserEau
 *
 */
public class OrdreDeverserEau extends Evenement {
	
	private Robot robot;
	private Incendie incendie;
	private Simulateur simulation;
	
	/**
	 * Constructeur
	 * @param date La date à laquelle l'évènement doit commencer 
	 * @param carte
	 * @param robot
	 * @param incendie
	 * @param simulation
	 */
	public OrdreDeverserEau(long date, Carte carte, Robot robot, Incendie incendie, Simulateur simulation) {
		super(date, carte);
		this.robot = robot;
		this.incendie = incendie;
		this.simulation = simulation;
	}
	
	/**
	 * Execute l'action liée à l'évènement
	 */
	public String execute() {
		if (!robot.isOccupe() && this.isLegalEvent() && robot.getTankLevel() > 0) {
			long dateDeversement = this.calculDateDeversement();
			simulation.ajouteEvenement(new DeverserEau(dateDeversement, this.getCarte(), robot, incendie, simulation.getData()));
			robot.setOccupe(true);
			return this.toString();
		} else {
			return "Soit ton robot est occupé soit tu verses à côté soit t'es à sec chef";
		}
	}
	
	/**
	 * 
	 * @return vrai si l'action est légale, faux sinon
	 */
	public boolean isLegalEvent() {
		/*System.out.println(incendie.getPosition() + " " + robot.getPosition());
		System.out.println(incendie.getPosition().equals(robot.getPosition()));
		return incendie.getPosition().equals(robot.getPosition());*/
		boolean ligne = incendie.getPosition().getLigne() == robot.getPosition().getLigne();
		boolean colonne = incendie.getPosition().getColonne() == robot.getPosition().getColonne();
		boolean terrain = incendie.getPosition().getNature() == robot.getPosition().getNature();
		return ligne && colonne && terrain;
	}
	
	/**
	 * Calcule la date de fin de deversement
	 * @return
	 */
	public long calculDateDeversement() {
		long dateExecution = (long)(this.getDate() + simulation.getData().getPas());
		return dateExecution;
	}

	public Robot getRobot() {
		return robot;
	}

	public Incendie getIncendie() {
		return incendie;
	}

	public Simulateur getSimulation() {
		return simulation;
	}
	
	public String toString() {
		return "Ordre Deverser Eau " + incendie.getEauNecessaire() + " " + (new Date(this.calculDateDeversement()));
	}
}