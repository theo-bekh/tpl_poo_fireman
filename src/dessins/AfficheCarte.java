package dessins;

import map.Carte;
import map.NatureTerrain;

public class AfficheCarte{
	/**
	 * Affiche sur le GUISimulator la carte
	 * @param gsim Le GUISimulator
	 * @param carte
	 * @param width
	 * @param height
	 * @param size
	 * @param offsetX Sert à centrer l'affichage sur la fenêtre. Décale de offsetX pixels vers la droite
	 */
	static void paint(gui.GUISimulator gsim, Carte carte, int width, int height, int size, int offsetX) {
		
		for(int i = 0 ; i < width ; i++) {
			for(int j = 0 ; j < height ; j++) {
				String nomImage;
				NatureTerrain nature = carte.getCase(j, i).getNature();
				if (nature == NatureTerrain.EAU) nomImage = "images/eau.png";
				else if (nature == NatureTerrain.FORET) nomImage = "images/foret.png";
				else if (nature == NatureTerrain.HABITAT) nomImage = "images/habitation.png";
				else if (nature == NatureTerrain.ROCHE) nomImage = "images/roche.png";
				else nomImage = "images/terrain_libre.png";
				gui.ImageElement image = new gui.ImageElement((int)(i*size) + offsetX, (int)(j*size), nomImage, size, size, gsim);
					
				gsim.addGraphicalElement(image);
				}
			}
		}
	private AfficheCarte() {
		super();
	}
}
