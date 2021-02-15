package strategies;

import map.Incendie;
import robots.*;
import simulation.*;

/**
 * 
 * Pompier glouton
 *
 */
public class PompierGlouton implements Pompier {
	
	/**
	 * @param Simulateur simulation
	 * @Override
	 */
	public void execute(Simulateur simu) {
		// On supprime de la liste les incendies éteints
		simu.getData().checkFeuEteint();
		// Si il n'y a plus d'incendies alors on return, fin de la simulation
		if (simu.getData().getIncendies().isEmpty()) {
			return;
		}
		/*
		 * Pour chaque robot libre, on choisit en fonction de son niveau d'eau et sa position l'action à réaliser.
		 */
		for (Robot robot : simu.getData().getRobots()) {
			if (!robot.isOccupe()) {
				// Si le réservoir est vide on va le remplir
				if (robot.getTankLevel() <= 0) {
					robot.remplirEau(simu);
				} else {
					robot.sortIncendies(simu);		//On trie la liste des incendies par ordre croissant de distance par rapport au robot
					/*
					 * On vérifie si le robot est déjà sur un incendie
					 */
					boolean estSurUnIncendie = false;
					Incendie leIncendie = null;
					for (Incendie incendie : simu.getData().getIncendies()) {
						if (robot.getPosition().equals(incendie.getPosition())) {
							estSurUnIncendie = true;
							leIncendie = incendie;
							break;
						}
					}
					//Si il est sur un incendie alors on verse de l'eau
					if (estSurUnIncendie) {
						robot.verserEau(simu, leIncendie);
					//Sinon on le déplace vers l'incendie le plus proche qui est accessible
					} else {
						int indice = 0;
						while (true) {
							try {
								Incendie target = simu.getData().getIncendie(indice);
								robot.shortestPaths(simu, target.getPosition(), simu.getDateCourante().getDate());
								break;
							} catch (UnsupportedOperationException e) {
								indice += 1;
								if (indice >= simu.getData().getIncendies().size()) {
									robot.setOccupe(true);
									break;
								}
							}
						}
					}
				}
			}
		}
	}
}
