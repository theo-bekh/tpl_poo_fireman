package map;

import java.util.*;

import robots.Robot;
import shortestPaths.*;

/**
 * 
 * Défini une case par sa position (ligne, colonne) et la nature de son terrain
 *
 */
public class Case extends GraphNode implements Cloneable{
	
	private int ligne;
	private int colonne;
	private NatureTerrain nature;
	
	@Override
	public int hashCode() {
		return ligne * 10000 + colonne;
	}	
	
	@Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if( ! (obj instanceof Case) ) return false;   

        Case other = (Case) obj;

        return this.ligne == other.ligne && this.colonne == other.colonne && this.nature == other.nature;
    }

	/**
	 * 
	 * @param ligne
	 * @param colonne
	 * @param nature
	 */
	public Case(int ligne, int colonne, NatureTerrain nature) {
		super();
		this.ligne = ligne;
		this.colonne = colonne;
		this.nature = nature;
	}
	
	public Case() {
		
	}

	public int getLigne() {
		return this.ligne;
	}

	public int getColonne() {
		return this.colonne;
	}
	
	public NatureTerrain getNature() {
		return this.nature;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		Case clone = (Case) super.clone();
		return clone;
	}
	
	@Override
	public String toString() {
		String affichage = "(" + Integer.toString(this.ligne) + ", " + Integer.toString(this.colonne) + ") " + this.nature.toString();
		return affichage;
	}

	/**
	 * @param carte
	 * @param targetNode une case voisine de la position du robot
	 * @param robot
	 * @return le temps nécessaire pour changer de case en prenant en compte la nature des terrains
	 */
	@Override
	public double getCost(Carte carte,GraphNode targetNode, Robot robot) {
		return ((carte.getTailleCases()/robot.getSpeed(this.nature) + carte.getTailleCases()/robot.getSpeed(((Case) targetNode).nature))/2);
	}

	/**
	 * @param carte
	 * @param robot
	 * @return une liste des noeuds voisins à la position du robot
	 */
	@Override
	public List<GraphNode> getAdjacentsNodes(Carte carte, Robot robot) {
		List<GraphNode> adjacentsNodes = new ArrayList<GraphNode>();
		for (Direction direction : Direction.values()) {
			Case adjacentCase = carte.getVoisin(this, direction);
			if (adjacentCase != null && robot.isLegalDeplacement(adjacentCase)) {
				adjacentsNodes.add(adjacentCase);
			}
		}
		return adjacentsNodes;
	}
}
