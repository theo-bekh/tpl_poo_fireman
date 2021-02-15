package shortestPaths;

import java.util.List;

import map.Carte;
import robots.Robot;
/**
 * 
 * Cette classe a pour but de manipuler des noeuds dans les calculs de chemins.
 * Elle a notament pour but de pouvoir considerer une case comme un noeud de graphe
 *
 */
public abstract class GraphNode {
	
	private double MinTime;
	private GraphNode previousNode;

	public GraphNode() {
		this.MinTime = Double.MAX_VALUE;
		this.previousNode = null;
	}	
	
/**
 * Permet de modifier le temps minimal pour aller dans ce noeud. Dépend du Dijkstra qu'on applique	
 * @param time
 */
	void setMinTime(double time){
		this.MinTime = time;
	}
/**
 * 	Permet d'obtenir le temps min pour aller sur ce Noeud.
 * @return Temps min pour aller sur le noeud
 */
	public double getMinTime() {
		return this.MinTime;
	}
/**
 * Permet d'obtenir le noeud précédent dans un calcul de Dijkstra.
 * @return Le noeud précedent.
 */
	public GraphNode getPreviousNode(){
		return this.previousNode;
	}
/**
 * Permet de modifier le noeud précédent dans un calcul de Dijkstra.
 */
	void setPreviousNode(GraphNode node){
		this.previousNode = node;
	}
/**
 * Donne le temps nécessaire pour aller d'un noeud à un autre noeud voisin. 
 * Dans notre cas il ne dépend pas de la case d'arrivée
 * @param carte
 * Carte détayant la géographie de la simulation
 * @param node
 * Noeud de départ pour un déplacement vers un Noeud voisin
 * @param robot
 * Robot effectuant le transport.
 * @return Le cout en double pour partir/arriver du Noeud
 */
	public abstract double getCost(Carte carte,GraphNode node, Robot robot);
/**
 * Permet d'obtenir les Noeuds adjacents aux noeuds. Il dépend principalement du robot car les déplacements
 * dépendent de la géographie de la carte.
 * @param carte
 * Carte dont dépend le noeud
 * @param robot
 * Robot qui va parcourir le graphe
 * @return La liste des noeuds adjacents de type List<GraphNode>
 */
	public abstract List<GraphNode> getAdjacentsNodes(Carte carte,Robot robot);
}
