package shortestPaths;


import actions.OrdreDeplacement;
import map.*;
import robots.Robot;
import simulation.Evenement;
import simulation.Simulateur;
/**
 * 
 * Le but de cette classe est de pouvoir calculer à l'aide d'un résultat de Dijkstra le chemin pour aller d'un 
 * point à un autre. Cela revient à remonter les graphes du noeud d'arrivée au noeud de départ. La classe ajoute
 * automatiquement les évenements en questiion dans la simulation.
 *
 */
public class ShortestPaths {
	/**
	 * Cela ajoute les évenements grace au résulatat d'un Djikstra pour aller d'un poitn à un autre à 
	 * un moment donné.
	 *  L'utilisateur doit vérifié que la case d'arrivé est bien accessible pour le robot donné.
	 * @param simulation
	 * La simualation en question de type simulation
	 * @param shortestPathsResults
	 * Resulats du Djikstra pour la case d'arrivé et le robot de type DjikstraResults
	 * @param executionDate
	 * Debut de l'execution du chemin de type double.
	 * @param startingCase
	 * Case de départ de type Case
	 * @param targetedCase
	 * Case de départ de type targetedCase
	 * @param robot
	 * Le robot qui parcours l'ensemble du graph
	 */
	public ShortestPaths(Simulateur simulation, DijkstraResults shortestPathsResults, long executionDate, Case startingCase, Case targetedCase, Robot robot)  {
			Case currentCase = targetedCase; // On part de la case d'arrivée
			Case previousCase  = null;
			Direction direction = null;
			Evenement event;
			int indice = 0;
			boolean finVoyage;
			while (currentCase != startingCase) { //Tant qu'on est pas a la case de depart
				previousCase = (Case) shortestPathsResults.getSettledNodes().get(currentCase).getPreviousNode();
				if (currentCase.getLigne() - previousCase.getLigne() == 1) { // Detrermine la direction
					direction = Direction.SUD;
				}else if (currentCase.getLigne() - previousCase.getLigne() == -1){
					direction = Direction.NORD;
				}else if(currentCase.getColonne() - previousCase.getColonne() == 1) {
					direction = Direction.EST;
				}else {
					direction = Direction.OUEST;
				}
				if (indice == 0) finVoyage = true; // Assure que le parcours de chemin ne sera pas interompu sinon
				else finVoyage = false;
				event = new OrdreDeplacement((long) (executionDate // Ajout de l'évenement
						+ shortestPathsResults.getSettledNodes().get(previousCase).getMinTime())
						, simulation.getData().getCarte(),robot, direction, simulation, finVoyage);
				simulation.ajouteEvenement(event);
				currentCase = previousCase;
		}	
	}

}
	