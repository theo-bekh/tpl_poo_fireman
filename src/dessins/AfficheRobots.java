package dessins;

import java.util.ArrayList;

import robots.*;


public class AfficheRobots {
	/**
	 * Affiche sur le GUISimulator les robots
	 * @param gsim Le GUISimulator
	 * @param robots La liste des robots à afficher
	 * @param width
	 * @param height
	 * @param size
	 * @param offsetX Sert à centrer l'affichage sur la fenêtre. Décale de offsetX pixels vers la droite
	 */
	static void paint(gui.GUISimulator gsim,ArrayList<Robot> robots,int width, int height, int size, int offsetX) {
		
		for(Robot rob : robots) {
			int colonne = rob.getPosition().getColonne();
			int ligne = rob.getPosition().getLigne();
			int xRob = (int)((colonne) * size)+offsetX;
			int yRob = (int)((ligne) * size);
			if (rob instanceof Drone) {
				gsim.addGraphicalElement(new gui.ImageElement(xRob, yRob, "images/drone.png", size, size, gsim));
			}
			if (rob instanceof RobotRoues) {
				gsim.addGraphicalElement(new gui.ImageElement(xRob, yRob, "images/roues.png", size, size, gsim));
			}
			
			if (rob instanceof RobotChenilles) {
				gsim.addGraphicalElement(new gui.ImageElement(xRob, yRob, "images/chenilles.png", size, size, gsim));
			}
			if (rob instanceof RobotPattes) {
				gsim.addGraphicalElement(new gui.ImageElement(xRob, yRob, "images/pattes.png", size, size, gsim));
			}
		}
	}
	
	private AfficheRobots() {
		super();
	}
}
