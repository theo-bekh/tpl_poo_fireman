package strategies;

import robots.*;
import simulation.Simulateur;
import map.*;

/**
 * 
 * Pompier intelligent
 *
 */
public class PompierIntelligent implements Pompier {

	@Override
	public void execute(Simulateur simu) { 
		//Enlève les feux qui ont été éteint de la liste
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
					robot.sortIncendies(simu);	//On trie la liste des incendies par ordre croissant de distance par rapport au robot
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
					//Si on est sur un incendie alors on verse de l'eau
					if (estSurUnIncendie) {
						robot.verserEau(simu, leIncendie);
					} else {
						/**
						 * Si le robot n'est pas déjà sur un incendie, alors on le dirige vers un incendie
						 * dont aucun robot ne s'occupe déjà, si tous les incendies sont déjà pris en charge par un robot,
						 * alors on dirige le robot vers l'incendie le plus proche de lui qui est accessible
						 */
						int indice = 0;
						Incendie target = simu.getData().getIncendie(indice);
						boolean isAccessible;
						Incendie sortieSecours = null; //L'incendie accessible le plus proche dans le cas ou tous les incendies sont pris en charge
						try {
							robot.shortestPathTime(simu, target.getPosition());
							isAccessible = true;
						} catch (UnsupportedOperationException e) {
							isAccessible = false;
						}
						/**
						 * On cherche un incendie non pris en charge et accessible parmis tous les incendies, si on en trouve un,
						 * on dirige le robot dessus, sinon, on dirige le robot vers l'incendie de secours (l'incendie accessible le plus proche
						 * qui est donc déjà pris en charge)
						 * Si aucun incendie n'est accessible, alors on met le robot en mode occupé et il ne fera plus rien de
						 * la simulation.
						 */
						while ((target.isPrisEnCharge() || !isAccessible) && indice < simu.getData().getIncendies().size()) {
							target = simu.getData().getIncendie(indice);
							try {
								robot.shortestPathTime(simu, target.getPosition());
								isAccessible = true;
							} catch (UnsupportedOperationException e) {
								isAccessible = false;
							}
							if (isAccessible) {
								if (!target.isPrisEnCharge()) {
									sortieSecours = target;
									break;
								} else if (sortieSecours == null) {
									sortieSecours = target;
								}
							}
							indice += 1;
						}
						if (indice >= simu.getData().getIncendies().size()) {
							if (sortieSecours != null) {
								robot.shortestPaths(simu, sortieSecours.getPosition(), simu.getDateCourante().getDate());
							} else {
								robot.setOccupe(true);
							}
						} else {
							robot.shortestPaths(simu, target.getPosition(), simu.getDateCourante().getDate());
						}
						target.setPrisEnCharge(true);
					}
				}
			}
		}
	}
}