package actions;

import map.*;
import robots.*;
import simulation.Evenement;
import donnees.*;

/**
 * 
 * Evenement déverser l'eau, verse la quantité d'eau necessaire à l'extinction du feu dans la limite du réservoir
 *
 */
public class ViderReservoir extends Evenement {
	
	private Robot robot;
	private Incendie incendie;
	private DonneesSimulation data;
	
	/**
	 * Constructeur
	 * @param dateDebut La date à laquelle l'évènement doit commencer 
	 * @param carte
	 * @param robot
	 * @param incendie
	 * @param data
	 */
	public ViderReservoir(long dateDebut, Carte carte, Robot robot, Incendie incendie, DonneesSimulation data) {
		super(dateDebut, carte); //Necessaire pour initialiser avec une valeur avant de calculer la date d'execution	
		this.robot = robot;
		this.incendie = incendie;
		this.data = data;
	}
	
	/**
	 * Execute l'action liée à l'évènement
	 */
	public String execute() {
		int quantiteVersee = (int) robot.getTankLevel();
		robot.setTankLevel(0);
		incendie.setEauNecessaire(incendie.getEauNecessaire() - quantiteVersee);
		robot.setOccupe(false);
		return this.toString();
		}
		
	
	public Robot getRobot() {
		return robot;
	}
	
	@Override
    public int compareTo(Evenement e) {
        return super.compareTo(e);
    }
	
	@Override
	public String toString() {
		return "Deverser eau " + robot.toString() + " " + incendie.getEauNecessaire();
	}

	public Incendie getIncendie() {
		return incendie;
	}

	public DonneesSimulation getData() {
		return data;
	}
}
