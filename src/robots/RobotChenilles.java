package robots;

import actions.OrdreDeverserEau;
import actions.OrdreRemplissage;
import map.*;
import simulation.*;

/**
 * 
 * Définie les caractéristiques et méthodes propre au robot de type RobotChenilles
 *
 */

public class RobotChenilles extends Robot{
	
	/**
	 * Constructeur
	 * Initialisation des attributs du robotChenilles
	 * @param caseRobot
	 */
	public RobotChenilles(Case caseRobot) {
		super(caseRobot);
		this.setTankCapacity(2000);
		this.setTankLevel(this.getTankCapacity());
		this.setTempsRemplissage(5*60*1000);
		this.setDebitIntervention((float) 750/(60*1000));
		this.setInitialSpeed(60/(3.6*1000));
	}
	
	public double getSpeed(NatureTerrain nature) {
		double vitesse = 0;
		if (nature == NatureTerrain.FORET) {
			vitesse = this.getInitialSpeed() / 2;
		} else {
			vitesse = this.getInitialSpeed();
		}
		return vitesse;
	}
	
	/**
	 * 
	 * @param laCase : destination
	 * @return true si laCase n'est pas de l'eau ni de la roche, sinon renvoie false
	 */
	public boolean isLegalDeplacement(Case laCase) {
		if (laCase.getNature().equals(NatureTerrain.EAU) || laCase.getNature().equals(NatureTerrain.ROCHE)) {
			return false;
		}
		return true;
	}
	
	public void verserEau(Simulateur simulation, Incendie incendie) {
		long dateCourrante = simulation.getDateCourante().getDate();
		simulation.ajouteEvenement(new OrdreDeverserEau(dateCourrante, simulation.getData().getCarte(), this, incendie, simulation));
	}
	
	public void remplirEau(Simulateur simulation) {
		long dateArrivee;
		Case destination = this.getClosestVoisinsEau(simulation, simulation.getData().getCarte());
		dateArrivee = (long) (simulation.getDateCourante().getDate() + this.shortestPathTime(simulation, destination));
		this.shortestPaths(simulation, destination, simulation.getDateCourante().getDate());
		simulation.ajouteEvenement(new OrdreRemplissage(dateArrivee, simulation.getData().getCarte(), this, simulation));
	}
	
	public void setInitialSpeed(double initialSpeed) {
		if (initialSpeed > 80/(3.6*1000)) {
			throw new IllegalArgumentException("La vitesse pour un Robot à Chenilles est de 80km/h max");
		} else {
			this.initialSpeed = initialSpeed;
		}
	}
}