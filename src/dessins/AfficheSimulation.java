package dessins;

import java.util.ArrayList;

import map.Carte;
import map.Incendie;
import robots.Robot;

/**
 * 
 * Classe utilisée pour afficher la simulation
 *
 */
public class AfficheSimulation {
	/**
	 * Affiche la simulation
	 * @param gsim Le GuiSimulator sur lequel afficher
	 * @param carte La carte
	 * @param incendies La liste des incendies
	 * @param robots La liste des robots
	 * @param diffHeight Différence entre la taille de la fenêtre et la taille du canvas. Utilisé pour redimensionner la taille de la fenêtre.
	 */
	public static void paint(gui.GUISimulator gsim, Carte carte, ArrayList<Incendie> incendies,ArrayList<Robot> robots, int diffHeight) {
		int nbCol = carte.getNbColonnes();
		int nbLig = carte.getNbLignes();
		int width = gsim.getWidth();
		int height = gsim.getHeight()-diffHeight-4;
		int size = Math.min((int)(height/nbLig), (int)(width/nbCol));
		int offsetX = (width/2)-(nbCol*size/2);
		
		AfficheCarte.paint(gsim, carte, nbCol, nbLig, size, offsetX);
		AfficheFeux.paint(gsim,incendies, nbCol, nbLig, size, offsetX);	
		AfficheRobots.paint(gsim, robots, nbCol, nbLig, size, offsetX);

	}
	private AfficheSimulation() {
		super();
	}
}
