package shortestPaths;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import robots.Robot;
import map.*;
/**
 * 
 * Cette classe a pour but d'appliquer l'algorithme de Dijkstra au graphe composée de l'ensemble des cases.
 * Le but est d'avoir une classe ayant comme attribut les résultats d'une application de l'algorithme. 
 * En effet, le résulat est réutilisable pour tout chemin partant d'une même Case. Les graphes sont composées
 * de Noeud de type GraphNode dont hérite la classe Case.
 *
 */
public class DijkstraResults {
	/**
	 * settledNodes est une Map contenant en clé l'ensmenble des des noeuds qui sont parcouru au cours de l'algo.
	 * La valeur de la clé permet de stocker le temps minimal pour aller aux noeud et de stocker le noeud qu'il
	 * faut parcourir juste avant la clé pour arriver avec un temps minimal au Noeud clé. 
	 * A la fin de l'algo les Nodes accessibles sont tous présent en tant que clé de la Map.
	 * 
	 *  unsettledNodes stocke seulement les GraphNodes qui ne sont pas encore parcourut lors de l'algorithme.
	 *  A la fin de l'algo, unsettledNodes ne devrait plus contenir de noeud.
	 */
	private Map<GraphNode,GraphNode> settledNodes;
	private Set<GraphNode> unsettledNodes;

/**
 * L'utilisateur appelle le constructeur de la classe dès qu'il veut calculer un plus court chemin (ou avoir une 
 * distance minimiale) qu'il va pouvoir stocker à son bon vouloir.
 * @param carte
 * La carte contenant notemment la géographie de la simulation de type Carte
 * @param source
 * Le noeud de départ pour le calcul du Dijkstra de type GraphNode. La source doit appartenir au Case de la Carte
 * pour que l'algorithme fonctionne.
 * @param robot
 * Le robot qui va effectuer les potentiels chemins
 */
	public DijkstraResults(Carte carte,GraphNode source, Robot robot) {
	    source.setMinTime(0);  //Le temps min pour arriver à la source est nul
	 
	    this.settledNodes = new HashMap<GraphNode,GraphNode>();
	    this.unsettledNodes = new HashSet<GraphNode>();
	 
	    unsettledNodes.add(source); // On ajoute la source dans les noeud pas encore visité
	 
	    while (unsettledNodes.size() != 0) { // Tant qu'on peut visité des noeud on continue
	        GraphNode currentNode = this.getLowestTimeNode(); // On choisit le Noeud le plus rapide à atteindre qui n'est pas visité
	        unsettledNodes.remove(currentNode); //Il n'est plus non visité
	        for (GraphNode adjacentNode : currentNode.getAdjacentsNodes(carte,robot)) { //parcours des voisins accessibles
        		double potentialNewTime = currentNode.getCost(carte,adjacentNode, robot) + currentNode.getMinTime(); //On calcul le potentiel temps min pour l'atteindre à partir du sous-graphes des visités
	            if (!settledNodes.containsKey(adjacentNode)) {
	            	if (potentialNewTime < adjacentNode.getMinTime() || !unsettledNodes.contains(adjacentNode)) {
	            		adjacentNode.setMinTime(potentialNewTime);
	            		adjacentNode.setPreviousNode(currentNode);
	            	}
	                unsettledNodes.add(adjacentNode);
	            }
	        }
	        settledNodes.put(currentNode,new Case()); // On ajoute le noeud traité dans les visités
	    }
	    
	    for(GraphNode node : settledNodes.keySet()) { //On copie les résultats par noeuds dans les valeur selon la clé de la map. (Sinon ils auraient put être écrasé par un autre appel au Dijkstra)
	    	settledNodes.get(node).setMinTime(node.getMinTime());
	    	settledNodes.get(node).setPreviousNode(node.getPreviousNode());
	    }
	}
	
/**
 * Permet d'acceder aux Noeuds qui sont accesibles, le temps min pour y aller et le Noeud précédent
 * @return La Map des résulats du Dikkstra
 */
	public Map<GraphNode,GraphNode> getSettledNodes(){
		return this.settledNodes;
	}
	
	
/**
 * Fonction annexe du Dijkstra. Permet de trouver le Noeud le plus rapidement accessible en partant du 
 * sous graphe des accessibles.
 * @return Le noeud qui a le temps d'accés le plus cours de type GraphNode
 */
	
	private GraphNode getLowestTimeNode() {
	    GraphNode lowestTimeNode = null;
	    double lowestTime = Double.POSITIVE_INFINITY;
	    for (GraphNode node: this.unsettledNodes) {
	        double nodeTime = node.getMinTime();
	        if (nodeTime < lowestTime) {
	            lowestTime = nodeTime;
	            lowestTimeNode = node;
	        }
	    }
	    return lowestTimeNode;
	}
	
}
