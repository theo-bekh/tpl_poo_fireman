package strategies;

import simulation.Simulateur;

/**
 * 
 * Interface designant un pompier. Chaque pompier a sa propre stratégie d'extinction des incendies.
 *
 */
public interface Pompier {
	/**
	 * Execute sa stratégie d'exctinction d'incendie pour une iteration de la simulation
	 * @param sim
	 */
	public void execute(Simulateur sim);
}
