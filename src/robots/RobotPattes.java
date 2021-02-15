package robots;

import actions.OrdreDeverserSable;
import map.*;
import simulation.*;

/**
 * 
 * Définie les caractéristiques et méthodes propre au robot de type RobotPatte
 *
 */
public class RobotPattes extends Robot{
	
	/**
	 * Constructeur
	 * @param caseRobot
	 */
	public RobotPattes(Case caseRobot) {
		super(caseRobot);
		this.setTankCapacity(1);
		this.setTankLevel(this.getTankCapacity());
		this.setTempsRemplissage(0);
		this.setDebitIntervention((float) 600/(60*1000));
		this.setInitialSpeed(30/(3.6*1000));
	}
	
	public double getSpeed(NatureTerrain nature) {
		double vitesse = 0;
		if (nature == NatureTerrain.ROCHE) {
			vitesse = 10/(3.6*1000);
		} else {
			vitesse = this.getInitialSpeed();
		}
		return vitesse;
	}
	
	/**
	 * @param laCase : destination
	 * @return true si laCase n'est pas de l'eau, sinon renvoie false
	 */
	public boolean isLegalDeplacement(Case laCase) {
		if (laCase.getNature().equals(NatureTerrain.EAU)) {
			return false;
		}
		return true;
	}
	
	public void verserEau(Simulateur simulation, Incendie incendie) {
		long dateCourrante = simulation.getDateCourante().getDate();
		simulation.ajouteEvenement(new OrdreDeverserSable(dateCourrante, simulation.getData().getCarte(), this, incendie, simulation));
	}
	
	public void remplirEau(Simulateur simulation) {
		
	}
	
	public void setInitialSpeed(double initialSpeed) {
		this.initialSpeed = 30/(3.6*1000);
	}
}