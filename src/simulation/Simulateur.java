package simulation;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.zip.DataFormatException;

import donnees.DonneesSimulation;
import dessins.*;
import map.*;
import strategies.*;
import donnees.LecteurDonnees;

/**
 * 
 * Le simulateur 
 *
 */
public class Simulateur implements gui.Simulable{
	
	private gui.GUISimulator gsim;
	private DonneesSimulation data;
	private PriorityQueue<Evenement> events;
	private Date dateCourante;
	private Pompier pompier;
	private int diffHeight;
	
	/**
	 * Constructeur
	 * @param path Chemin vers la map
	 * @throws FileNotFoundException
	 * @throws DataFormatException
	 * @throws CloneNotSupportedException
	 */
	public Simulateur(String path, Pompier strat) throws FileNotFoundException, DataFormatException, CloneNotSupportedException {
		super();	
			
		dateCourante = new Date(0);
						
		data = LecteurDonnees.lire(path); 
		
	
		events = new PriorityQueue<Evenement>();
		
		gsim = new gui.GUISimulator(500, 500, Color.white);
		gsim.setSimulable(this);
				
		this.pompier = strat;
		
		diffHeight = gsim.getHeight() - gsim.getPanelHeight();
		
		restart();
	}
	
	/**
	 * 
	 * @return une référence vers les DonneesSimulation 
	 */
	public DonneesSimulation getData(){
		return this.data;
	}
	
	/**
	 * Ajoute un évènement à la queue d'évènement, les évènements sont triés sur leur date
	 * @param e
	 */
	public void ajouteEvenement(Evenement e) {
		events.add(e);
	}
	
	/**
	 * Incrémente la date courante et traite les évènements
	 */
	private void incrementeDate(){
		
		if (simulationTerminee()) return;
		
		//récupère le pas et incrémente la date courante
		dateCourante.incremente(data.getPas());
		
		//traite les évènements
		Evenement e = events.peek();
		while (!events.isEmpty() && e.getDate() <= dateCourante.getDate()) {
			e = events.poll();
			e.execute();
			e = events.peek();
		}
	}
	
	/**
	 * 
	 * @return vrai si la simulation est terminée, faux sinon
	 */
	private boolean simulationTerminee() {
		return data.getIncendies().isEmpty() || events.isEmpty();
	}
	
	@Override
	/**
	 * Appelée par le GuiSimulator. Fait avancer la simulation à la prochaine itération
	 */
	public void next() {
		//le pompier traite les actions
		pompier.execute(this);
		
		//traite les évènements
		incrementeDate();
		
		//affichage
		gsim.reset();
		AfficheSimulation.paint(gsim, data.getCarte(), data.getIncendies(), data.getRobots(), diffHeight);
		System.out.println(dateCourante);
	}

	@Override
	/**
	 * Fonction appelée par le GuiSimulator. Remet à zéro la simulation
	 */
	public void restart() {	
		
		//reset
		try {
			dateCourante.setDate(0);
			events.clear();
			data.reset();	
			gsim.reset();
		}catch(Exception e)
		{
			System.out.println();
			System.exit(1);
		}
		
		for(Object e : events.toArray())
			System.out.println(((Evenement)e).getDate());
		
		//affichage
		AfficheSimulation.paint(gsim, data.getCarte(), data.getIncendies(), data.getRobots(), diffHeight);

	}
	
	/**
	 * 
	 * @return Date courante
	 */
	public Date getDateCourante() {
		return dateCourante;
	}
	
	/**
	 * Print syntax error and exit the program
	 */
	private static void syntaxErrExit() {
		System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier> [options]");
        System.out.println("Options : -i : pompier intelligent (par defaut)");
        System.out.println("          -g : pompier glouton");
        System.exit(1);
	}
	
	/**
	 * Le main prend en entrée sur la ligne de commande le chemin vers un fichier .map
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception{
		if (args.length < 1) {
            syntaxErrExit();
        }
		
		Pompier pompier = new PompierIntelligent();
		
		//pompier
		if (args.length == 2) {
			if (args[1].equals("-g")) pompier = new PompierGlouton();
			else if (args[1].equals("-i")) pompier = new PompierIntelligent();
			else syntaxErrExit();
		}
			
		
		try {
			new Simulateur(args[0], pompier);  
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
        
	}
}
