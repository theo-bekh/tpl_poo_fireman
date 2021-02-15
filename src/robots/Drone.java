package robots;
import actions.OrdreViderReservoir;
import actions.OrdreRemplissage;
import map.*;
import simulation.*;
/**
 * 
 * Définie les caractéristiques et méthodes propre au robot de type Drone
 *
 */
public class Drone extends Robot{
	
	/**
	 * Constructeur
	 * Initialisation des attributs du drone
	 * @param caseRobot
	 */
	public Drone(Case caseRobot) {
		super(caseRobot);
		this.setTankCapacity(10000);
		this.setTankLevel(this.getTankCapacity());
		this.setTempsRemplissage(30*60*1000);
		this.setDebitIntervention((float) 20000/(60*1000));
		this.setInitialSpeed(100/(3.6*1000));
	}
	
	public double getSpeed(NatureTerrain nature) {
		return this.getInitialSpeed();
	}
	
	/**
	 *
	 * @param laCase : destination
	 * @return true si laCase est dans la carte, sinon renvoie false
	 */
	public boolean isLegalDeplacement(Case laCase) {
		if (laCase == null) return false;
		return true;
	}

	public void verserEau(Simulateur simulation, Incendie incendie) {
		long dateCourrante = simulation.getDateCourante().getDate();
		simulation.ajouteEvenement(new OrdreViderReservoir(dateCourrante, simulation.getData().getCarte(), this, incendie, simulation));
	}
	
	public void remplirEau(Simulateur simulation) {
		long dateArrivee;
		Case destination = this.getClosestEau(simulation, simulation.getData().getCarte());
		dateArrivee = (long) (simulation.getDateCourante().getDate() + this.shortestPathTime(simulation, destination));
		this.shortestPaths(simulation, destination, simulation.getDateCourante().getDate());
		simulation.ajouteEvenement(new OrdreRemplissage(dateArrivee, simulation.getData().getCarte(), this, simulation));
	}
	
	public void setInitialSpeed(double initialSpeed) {
		if (initialSpeed > 150/(3.6*1000)) {
			throw new IllegalArgumentException("La vitesse pour un Drone est de 150km/h max");
		} else {
			this.initialSpeed = initialSpeed;
		}
	}
	
	
	
	
	
	
	
	
	
}