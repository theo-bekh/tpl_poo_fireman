package map;

/**
 * 
 * Défini la carte : taille des cases, dimensions, nature des terrains
 * Attention la carte ne contient pas les infos sur les robots et les incendies
 *
 */

public class Carte {
	
	private int tailleCases;
	private int nbLignes;
	private int nbColonnes;
	private Case[][] matrice;
	
	/**
	 * Constructeur
	 * @param NbLignes
	 * @param NbColonnes
	 * @param tailleCases
	 */
	public Carte(int NbLignes, int NbColonnes, int tailleCases) {
		matrice = new Case[NbLignes][NbColonnes];
		this.nbLignes = NbLignes;
		this.nbColonnes = NbColonnes;
		this.tailleCases = tailleCases;
	}
	
	
	public int getNbLignes() {
		return this.nbLignes;
	}
	
	public int getNbColonnes() {
		return this.nbColonnes;
	}
	
	public int getTailleCases() {
		return this.tailleCases;
	}
	
	public void setCase(Case laCase) {
		this.matrice[laCase.getLigne()][laCase.getColonne()] = laCase;
	}
	
	public Case getCase(int ligne, int colonne) {
		return matrice[ligne][colonne];
	}
	
	/**
	 * 
	 * @param src
	 * @param direction
	 * @return true si il y a une case voisine dans la direction, sinon renvoie false
	 */
	public boolean voisinExiste(Case src, Direction direction) {
		if (direction == Direction.SUD) {
			return src.getLigne() + 1 < this.nbLignes;
		} else if (direction == Direction.NORD) {
			return src.getLigne() - 1 >= 0;
		} else if (direction == Direction.EST) {
			return src.getColonne() + 1 < this.nbColonnes;
		} else if (direction == Direction.OUEST) {
			return src.getColonne() - 1 >= 0;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param src
	 * @param direction
	 * @return la case voisine dans la direction si elle existe, sinon renvoie null
	 */
	public Case getVoisin(Case src, Direction direction) {
		int ligne = src.getLigne();
		int colonne = src.getColonne();
		if (this.voisinExiste(src, direction)) {
			if (direction == Direction.SUD) {
				return this.matrice[ligne + 1][colonne];
			} else if (direction == Direction.NORD) {
				return this.matrice[ligne - 1][colonne];
			} else if (direction == Direction.EST) {
				return this.matrice[ligne][colonne + 1];
			} else {
				return this.matrice[ligne][colonne - 1];
		    }
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		String affichage = "";
		for (int i = 0; i < this.nbLignes; i++) {
			for (int j = 0; j < this.nbColonnes; j++) {
				affichage = affichage + this.matrice[i][j].toString() + "\n";
			}
		}
		return affichage;
	}
}





















