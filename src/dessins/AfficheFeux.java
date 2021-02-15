package dessins;

import java.util.ArrayList;

import map.Incendie;

public class AfficheFeux {
	/**
	 * Affiche sur le GUISimulator les feux
	 * @param gsim Le GUISimulator
	 * @param incendies Liste des incendies a afficher
	 * @param width
	 * @param height
	 * @param size
	 * @param offsetX Sert à centrer l'affichage sur la fenêtre. Décale de offsetX pixels vers la droite
	 */
	static void paint(gui.GUISimulator gsim, ArrayList<Incendie> incendies, int width, int height, int size, int offsetX) {
		
		for (Incendie incendie : incendies) {
			if (incendie.getEauNecessaire() > 0) {
				int ligne = incendie.getPosition().getLigne();
				int colonne = incendie.getPosition().getColonne();
				int x = (int) ((colonne) * size)+offsetX;
				int yIncendie = (int) ((ligne) * size);
				gsim.addGraphicalElement(new gui.ImageElement(x, yIncendie, "images/flamme.png", size, size, gsim));
			}
		}
		
	}
	
	private AfficheFeux() {
		super();
	}
}
