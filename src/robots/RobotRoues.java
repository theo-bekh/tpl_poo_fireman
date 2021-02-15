package robots;

import actions.OrdreDeverserEau;
import actions.OrdreRemplissage;
import map.*;
import simulation.*;

/**
 * 
 * Définie les caractéristiques et méthodes propre au robot de type RobotRoues
 *
 */

public class RobotRoues extends Robot{
	
	/**
	 * Constructeur
	 * @param caseRobot
	 */
	public RobotRoues(Case caseRobot) {
		super(caseRobot);
		this.setTankCapacity(5000);
		this.setTankLevel(this.getTankCapacity());
		this.setTempsRemplissage(10*60*1000);
		this.setDebitIntervention((float) 1200/(60*1000));
		this.setInitialSpeed(80/(3.6*1000));
	}
	
	public double getSpeed(NatureTerrain nature) {
		return this.getInitialSpeed();
	}
	
	/**
	 * @param laCase : destination
	 * @return true si laCase est un TERRAIN_LIBRE ou un HABITAT, sinon renvoie false
	 */
	public boolean isLegalDeplacement(Case laCase) {
		if (laCase.getNature().equals(NatureTerrain.TERRAIN_LIBRE) || laCase.getNature().equals(NatureTerrain.HABITAT)) {
			return true;
		}
		return false;
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
		if (initialSpeed > 1000/(3.6*1000)) {
			throw new IllegalArgumentException("La vitesse pour un Robot à Roues est de 1000km/h max");
		} else {
			this.initialSpeed = initialSpeed;
		}
	}
}