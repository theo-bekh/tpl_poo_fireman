package robots;

import map.*;
import java.util.*;
import shortestPaths.*;
import simulation.*;

/**
 * 
 * Définie les caractéristiques et méthodes communes à tous les robots
 *
 */

public abstract class Robot implements Cloneable {
	
	private int tankCapacity;
	protected int tankLevel;
	private long tempsRemplissage; /* en ms */
	private float debitIntervention; /* en Litre/ms*/
	protected double initialSpeed;
	protected Case position;
	protected Case shortestPathsStartingCase;
	protected DijkstraResults shortestPathsResults;	
	protected boolean occupe;

	/**
	 * Constructeur
	 * @param caseRobot
	 */
	public Robot(Case caseRobot) {
		this.position = caseRobot;
	}
	
	/**
	 * 
	 * @param nature
	 * @return la vitesse du robot en fonction de la nature du terrain
	 */
	public abstract double getSpeed(NatureTerrain nature);
	
	/**
	 * 
	 * @param laCase
	 * @return true si le déplacement vers laCase est possible par le robot, sinon renvoie false
	 */
	public abstract boolean isLegalDeplacement(Case laCase);
	
	/**
	 * Envoie un robot vers le point d'eau le plus proche et rempli son réservoir au maximum
	 * @param simulation
	 */
	public abstract void remplirEau(Simulateur simulation);
	
	/**
	 * Lance l'ordre de verser de l'eau sur l'incendie passé en paramètre
	 * @param simulation
	 * @param incendie
	 */
	public abstract void verserEau(Simulateur simulation, Incendie incendie);
	
	/**
	 * Trie la liste courrante des incendies dans l'ordre croissant des distances au robot
	 * @param simulation
	 */
	public void sortIncendies(Simulateur simulation) {
		for (Incendie incendie : simulation.getData().getIncendies()) {
			try {
				incendie.setDistanceAuRobot(this.shortestPathTime(simulation, incendie.getPosition()));
			} catch(UnsupportedOperationException e) {
				incendie.setDistanceAuRobot(Double.MAX_VALUE);
			}
			
		}
		Collections.sort(simulation.getData().getIncendies());
	}
	
	/**
	 * 
	 * @param simulation
	 * @param carte
	 * @return la case d'eau la plus proche au sens du plus court chemin (dijkstra)
	 */
	public Case getClosestEau(Simulateur simulation, Carte carte) {
		Case destination = new Case();
		double minTime = Double.MAX_VALUE;
		for (Case eau : simulation.getData().getListeEau()) {
			double time = this.shortestPathTime(simulation, eau);
			if (time < minTime){
				minTime = time;
				destination = eau;
			}
		}
		return destination;
	}
	
	/**
	 * 
	 * @param simulation
	 * @param carte
	 * @return la berge la plus proche au sens du plus court chemin (dijkstra)
	 */
	public Case getClosestVoisinsEau(Simulateur simulation, Carte carte) {
		Case destination = new Case();
		double minTime = Double.MAX_VALUE;
		for (Case voisinEau : simulation.getData().getListeVoisinsEau()) {
			double time = this.shortestPathTime(simulation, voisinEau);
			if (time < minTime){
				minTime = time;
				destination = voisinEau;
			}
		}
		return destination;
	}
	/**
	 * 
	 * @return la Case de la postion d'un robot
	 */
	public Case getPosition() {
		return this.position;
	}
	/**
	 * C'est à l'utilisateur de s'assurer que le changement de position est valide
	 * @param Modifie la postion d'un robot
	 */
	public void setPosition(Case nouvellePosition) {
		this.position = nouvellePosition;
	}
	
	/**
	 * @return capacite d'eau que peut stocker un robot en L
	 */
	public int getTankCapacity() {
		return tankCapacity;
	}
	/**
	 * Modifie la capacité de stockage d'eau d'un robot en L
	 * @param tankCapacity
	 */
	public void setTankCapacity(int tankCapacity) {
		this.tankCapacity = tankCapacity;
	}
	/**
	 * @return La quantité d'eau que transporte le robot en L
	 */
	public int getTankLevel() {
		return tankLevel;
	}
	/**
	 * Permet d'obtenir la quantité d'eau d'un robot en L
	 * @param tankLevel
	 */
	public void setTankLevel(int tankLevel) {
		this.tankLevel = tankLevel;
	}
	/**
	 * 
	 * @return Le temps de remplissage d'un robot en ms
	 */
	public long getTempsRemplissage() {
		return tempsRemplissage;
	}
	/**
	 * Permet de modifier le temps de remplissage d'un robot en ms
	 * @param tempsRemplissage
	 */
	public void setTempsRemplissage(long tempsRemplissage) {
		this.tempsRemplissage = tempsRemplissage;
	}
	/** 
	 * 
	 * @return debit de largage d'eau en L/ms
	 */
	public float getDebitIntervention() {
		return debitIntervention;
	}
	/**
	 * Permet de modifier le debit de largage d'eau en L/ms
	 * @param debitIntervention
	 */
	public void setDebitIntervention(float debitIntervention) {
		this.debitIntervention = debitIntervention;
	}
	/**
	 * @return obtenir la vitesse d'un robot sur un terrain libre
	 */
	public double getInitialSpeed() {
		return this.initialSpeed;
	}

/**
 * Robot.shortestPathTime renvoie le temps minimal que le robot met pour aller de la case startingCase à 
 * la case targetedCase dans un simulation donnée. Si la startingCase n'est pas spécifiée, elle est mise
 * par défaut à la position du robot. 
 *  
 * L'algo vérifie d'abord que le calcul des plus court chemin n'ont pas encore été fait pour la case
 * de départ en question. (Dijkstra admet la particularité de pouvoir avoir tous les résultats pour 
 * tous les points d'arrivée pour un poin de départ. Sinon il récupère les données sauvegardé dans
 * le robot: Robot.shortestPathsResults accompagné de sa case de départ shortestPathsStartingCase.
 * Avec ceci on peut facilement récupérer la temps min de parcours. Voir DijkstraResults.
 * 
 * @param simulation
 * La simulation en question
 * @param targetedCase
 * La case d'arrivée pour le plus court chemin
 * @return Le temps le plus court pour ce trajet en Double.
 * @throws UnsupportedOperationException
 * Dans le cas où la targetedCase n'est pas accéssible (ex: ilots, mauvais environement pour un robot...)
 * Cette exception devra être gérée par l'utilisateur de la fonction.
 * @see Robot#shortestPathTime(Simulation, Case)
 */

	public double shortestPathTime(Simulateur simulation, Case targetedCase) throws UnsupportedOperationException{
		return shortestPathTime(simulation, targetedCase, this.position); // StartingCase par défaut
	}
/**
 * Robot.shortestPathTime renvoie le temps minimal que le robot met pour aller de la case startingCase à 
 * la case targetedCase dans un simulation donnée. Si la startingCase n'est pas spécifiée, elle est mise
 * par défaut à la position du robot.
 * 
 * L'algo vérifie d'abord que le calcul des plus court chemin n'ont pas encore été fait pour la case
 * de départ en question. (Dijkstra admet la particularité de pouvoir avoir tous les résultats pour 
 * tous les points d'arrivée pour un poin de départ. Sinon il récupère les données sauvegardé dans
 * le robot: Robot.shortestPathsResults accompagné de sa case de départ Robot.shortestPathsStartingCase.
 * Avec ceci on peut facilement récupérer la temps min de parcours. Voir DijkstraResults.
 * 
 * @param simulation
 * La simulation en question de type Simulation
 * @param targetedCase
 * La case d'arrivée pour le plus court chemin de type Case.
 * @param startingCase 
 * La case de départ du trajet de type Case.
 * @return Le temps le plus court pour le trajet donné en Double.
 * @throws UnsupportedOperationException
 * Dans le cas où la targetedCase n'est pas accéssible (ex: ilots, mauvais environement pour un robot...)
 * Cette exception devra être gérée par l'utilisateur de la fonction.
 * @see shortestPaths.DijkstraResults
 * @see Robot#shortestPathsResults
 * @see Robot#shortestPathsStartingCase
 */

	public double shortestPathTime(Simulateur simulation, Case targetedCase, Case startingCase ) throws UnsupportedOperationException{
		if (this.position != this.shortestPathsStartingCase) { // Si le calcul stocké n'est pas réutilisable
			this.shortestPathsStartingCase = this.position;
			this.shortestPathsResults = new DijkstraResults(simulation.getData().getCarte(), startingCase, this); // On calcul un Dijkstra
		}
		if (! this.shortestPathsResults.getSettledNodes().containsKey(targetedCase)) { //Renvoie une erreur si la case d'arrivée est innaccessible
			throw new UnsupportedOperationException("Case d'arrivée inaccessible");
		}
		return this.shortestPathsResults.getSettledNodes().get(targetedCase).getMinTime(); // Récupère le temps min en question
	}
/**
 * Robot.shortestPaths ajoute les évenements (ShortestPaths) à la simulation pour aller de la case 
 * startingCase à la case targetedCase dans un simulation donnée. Si la startingCase n'est pas spécifiée, 
 * elle est mise par défaut à la position du robot.
 * 
 * L'algo vérifie d'abord que le calcul des plus court chemin n'ont pas encore été fait pour la case
 * de départ en question. (Dijkstra admet la particularité de pouvoir avoir tous les résultats pour 
 * tous les points d'arrivée pour un poin de départ. Sinon il récupère les données sauvegardé dans
 * le robot: Robot.shortestPathsResults accompagné de sa case de départ Robot.shortestPathsStartingCase.
 * Avec les DijkstraResults on peut facilement retrouver le chemin en remontant les noeuds à l'envers
 * (attribut précedent des DijkstraResults). 
 * @param simulation
 * La simulation de type Simulation en question.
 * @param targetedCase
 * La case d'arrivée de type Case.
 * @param executionDate
 * La date de début d'éxecution pour ajouter les évenements élémentaires de type long.
 * @throws UnsupportedOperationException
 * Dans le cas où la targetedCase n'est pas accéssible (ex: ilots, mauvais environement pour un robot...)
 * Cette exception devra être gérée par l'utilisateur de la fonction.
 * @see shortestPaths.DijkstraResults
 * @see Robot#shortestPathsResults
 * @see Robot#shortestPathsStartingCase
 */

	public void shortestPaths(Simulateur simulation, Case targetedCase, long executionDate)throws UnsupportedOperationException{
		this.shortestPaths(simulation, targetedCase, this.position, executionDate); // StartingCase par défaut
	}
/**
 * Robot.shortestPaths ajoute les évenements (ShortestPaths) à la simulation pour aller de la case 
 * startingCase à la case targetedCase dans un simulation donnée.
 * 
 * L'algo vérifie d'abord que le calcul des plus court chemin n'ont pas encore été fait pour la case
 * de départ en question. (Dijkstra admet la particularité de pouvoir avoir tous les résultats pour 
 * tous les points d'arrivée pour un poin de départ. Sinon il récupère les données sauvegardé dans
 * le robot: Robot.shortestPathsResults accompagné de sa case de départ Robot.shortestPathsStartingCase.
 * Avec les DijkstraResults on peut facilement retrouver le chemin en remontant les noeuds à l'envers
 * (attribut précedent des DijkstraResults). 
 * @param simulation
 * La simulation de type Simulation en question.
 * @param targetedCase
 * La case d'arrivée de type Case.
 * @param startingCase
 * Case de départ de type Case.
 * @param executionDate
 * La date de début d'éxecution pour ajouter les évenements élémentaires de type long.
 * @throws UnsupportedOperationException
 * Dans le cas où la targetedCase n'est pas accéssible (ex: ilots, mauvais environement pour un robot...)
 * Cette exception devra être gérée par l'utilisateur de la fonction.
 * @see shortestPaths.DijkstraResults
 * @see Robot#shortestPathsResults
 * @see Robot#shortestPathsStartingCase
 */

	public void shortestPaths(Simulateur simulation, Case targetedCase, Case startingCase, long executionDate)throws UnsupportedOperationException{
		
		if (this.position != this.shortestPathsStartingCase) {// Nouoveau calcul si pas effectué 
			this.shortestPathsStartingCase = this.position;
			this.shortestPathsResults = new DijkstraResults(simulation.getData().getCarte(), startingCase, this);
		}
		if (! this.shortestPathsResults.getSettledNodes().containsKey(targetedCase)) {
			throw new UnsupportedOperationException("Case d'arrivée inaccessible");
		}
		new ShortestPaths(simulation,shortestPathsResults, executionDate, startingCase, targetedCase, this); // Ajout dans la liste d'évenement les évenements elem du plus court chemin
	}
	
	/**
	 * 		
	 * @return Vrai si le robot est occupé
	 */
	public boolean isOccupe() {
		return occupe;
	}
	
	/**
	 * Permet de rendre occupé un robot ou de le libérer
	 * @param occupe
	 */
	public void setOccupe(boolean occupe) {
		this.occupe = occupe;
	}
	
	/**
	 * Permet de cloner un robot superficiellement
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		Robot clone = (Robot) super.clone();
		return clone;
		}
	
	@Override
	public String toString() {
		return this.getClass().getName(); 
	}

	public abstract void setInitialSpeed(double d);
}