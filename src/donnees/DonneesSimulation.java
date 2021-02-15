package donnees;
import map.*;
import robots.*;
import java.util.ArrayList;
import java.util.Iterator;
public class DonneesSimulation {
	
	private Carte carte;
	private ArrayList<Robot> listeRobots = new ArrayList<Robot>(); // liste des robots dans leur état courrant
	private ArrayList<Incendie> listeIncendies = new ArrayList<Incendie>(); // liste des incendies dans leur état courant
	private ArrayList<Robot> initialRobots = new ArrayList<Robot>(); // liste des robots dans leur état initial (nécessaire pour restart)
	private ArrayList<Incendie> initialIncendies = new ArrayList<Incendie>(); // liste des incendies dans leur état initial (nécessaire pour restart)
	private ArrayList<Case> listeEau = new ArrayList<Case>(); // liste des cases qui sont de l'eau
	private ArrayList<Case> listeVoisinsEau = new ArrayList<Case>();// liste des cases qui sont des berges
	private long pas = 1000; // pas en ms (1 seconde ici)
	
	/**
	 * Constructeur
	 */
	public DonneesSimulation() {
	}
	
	public long getPas() {
		return pas;
	}
	
	public Robot getRobot(int indice) {
		return this.listeRobots.get(indice);
	}
	
	public ArrayList<Robot> getRobots() {
		return this.listeRobots;
	}
	
	public ArrayList<Incendie> getIncendies() {
		return this.listeIncendies;
	}
	/**
	 * @param robot
	 */
	public void addRobot(Robot robot) {
		this.listeRobots.add(this.listeRobots.size(), robot);
	}
	
	public Incendie getIncendie(int indice) {
		return this.listeIncendies.get(indice);
	}
	
	public void removeIncendie(int indice) {
		this.listeIncendies.remove(indice);
	}
	
	public void addIncendie(Incendie incendie) {
		this.listeIncendies.add(this.listeIncendies.size(), incendie);
	}
	public void setInitialPositions(DonneesSimulation data){
		this.initialIncendies = data.listeIncendies;
		this.initialRobots = data.listeRobots;
	}
	public void initialiseCarte(int nbLignes, int nbColonnes, int tailleCases) {
		this.carte = new Carte(nbLignes, nbColonnes, tailleCases);
	}
	
	public Carte getCarte() {
		return this.carte;	
	}
	
	/**
	 * Supprime les feux éteints de la liste des incendies courrants
	 */
	public void checkFeuEteint() {
		Iterator<Incendie> incendies = listeIncendies.iterator();
		while (incendies.hasNext()) {
		   Incendie incendie = incendies.next();
		   if (incendie.getEauNecessaire() <= 0) {
			   incendies.remove();
		   }
		}
	}
	
	/**
	Renvoie un clone de l'objet tel que seulement les incendies et robots ont été clonés
	*/
	public DonneesSimulation clonePositions() throws CloneNotSupportedException {
		DonneesSimulation savedData = new DonneesSimulation();
		
		ArrayList<Robot> savedRobots = new ArrayList<Robot>();
		ArrayList<Incendie> savedIncendies = new ArrayList<Incendie>();
		
		for (Robot robot : listeRobots) {
			savedRobots.add((Robot) robot.clone());
		}
		for (Incendie incendie : listeIncendies) {
			savedIncendies.add((Incendie) incendie.clone());
		}
		
		savedData.carte = carte;
		savedData.listeIncendies = savedIncendies;
		savedData.listeRobots = savedRobots;
		
		return savedData;		
	}
	
	public void reset() throws CloneNotSupportedException {
		this.listeIncendies = initialIncendies;
		this.listeRobots = initialRobots;
		this.setInitialPositions(this.clonePositions());
	}

	public ArrayList<Case> getListeEau() {
		return listeEau;
	}

	public ArrayList<Case> getListeVoisinsEau() {
		return listeVoisinsEau;
	}
}