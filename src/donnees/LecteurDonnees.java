package donnees;
import map.*;
import robots.*;
import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;

/**
 * Lecteur de cartes au format spectifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées.
 * A noter: pas de vérification sémantique sur les valeurs numériques lues.
 *
 * IMPORTANT:
 *
 * Cette classe ne fait que LIRE les infos et les afficher.
 * A vous de modifier ou d'ajouter des méthodes, inspirées de celles présentes
 * (ou non), qui CREENT les objets au moment adéquat pour construire une
 * instance de la classe DonneesSimulation à partir d'un fichier.
 *
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues:
 *    public static DonneesSimulation creeDonnees(String fichierDonnees);
 * Et faire des méthode creeCase(), creeRobot(), ... qui lisent les données,
 * créent les objets adéquats et les ajoutent ds l'instance de
 * DonneesSimulation.
 */
public class LecteurDonnees {


    /**
     * Lit et affiche le contenu d'un fichier de donnees (cases,
     * robots et incendies).
     * Ceci est méthode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees)
     * @param fichierDonnees nom du fichier à lire
     * @throws CloneNotSupportedException 
     */
    public static DonneesSimulation lire(String fichierDonnees)
        throws FileNotFoundException, DataFormatException, CloneNotSupportedException {
    	DonneesSimulation simulation = new DonneesSimulation(); // Créer la simulation qui stockera les données nécessaires
        System.out.println("\n == Lecture du fichier" + fichierDonnees);
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        // Les données sont ajoutées lors de la lecture des éléments du fichier .map
        lecteur.lireCarte(simulation);
        lecteur.lireIncendies(simulation);
        lecteur.lireRobots(simulation);
        scanner.close();
        System.out.println("\n == Lecture terminee");
        simulation.setInitialPositions(simulation.clonePositions());
        return simulation;
    }




    // Tout le reste de la classe est prive!

    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit et affiche les donnees de la carte.
     * @throws ExceptionFormatDonnees
     */
    private void lireCarte(DonneesSimulation simulation) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();	// en m
            simulation.initialiseCarte(nbLignes, nbColonnes, tailleCases); // Initialise la carte
            System.out.println("Carte " + nbLignes + "x" + nbColonnes
                    + "; taille des cases = " + tailleCases);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    lireCase(lig, col, simulation.getCarte(), simulation); // On ajoute chaque case une à une dans la carte
                }
            }
            /**
             * On parcours la carte afin de trouver les voisins des cases qui sont des berges
             * dont on a besoin pour la suite de la simulation
             */
            Carte laCarte = simulation.getCarte();
            for (Case laCase : simulation.getListeEau()) {
            	if (laCase.getNature().equals(NatureTerrain.EAU)) {
                    if (laCarte.voisinExiste(laCase, Direction.NORD) && !laCarte.getVoisin(laCase, Direction.NORD).getNature().equals(NatureTerrain.EAU)) {
                    	simulation.getListeVoisinsEau().add(laCarte.getVoisin(laCase, Direction.NORD));
                    }
                    if (laCarte.voisinExiste(laCase, Direction.EST) && !laCarte.getVoisin(laCase, Direction.EST).getNature().equals(NatureTerrain.EAU)) {
                    	simulation.getListeVoisinsEau().add(laCarte.getVoisin(laCase, Direction.EST));
                    }
                    if (laCarte.voisinExiste(laCase, Direction.SUD) && !laCarte.getVoisin(laCase, Direction.SUD).getNature().equals(NatureTerrain.EAU)) {
                    	simulation.getListeVoisinsEau().add(laCarte.getVoisin(laCase, Direction.SUD));
                    }
                    if (laCarte.voisinExiste(laCase, Direction.OUEST) && !laCarte.getVoisin(laCase, Direction.OUEST).getNature().equals(NatureTerrain.EAU)) {
                    	simulation.getListeVoisinsEau().add(laCarte.getVoisin(laCase, Direction.OUEST));
                    }
                }
            }
            //System.out.println(simulation.getListeVoisinsEau());
        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
    }




    /**
     * Lit et affiche les donnees d'une case.
     */
    private void lireCase(int lig, int col, Carte carte, DonneesSimulation simulation) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Case (" + lig + "," + col + "): ");
        String chaineNature = new String();
        //		NatureTerrain nature;
        try {
            chaineNature = scanner.next();
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            //			NatureTerrain nature = NatureTerrain.valueOf(chaineNature);

            verifieLigneTerminee();

            System.out.print("nature = " + chaineNature);
            NatureTerrain nature = NatureTerrain.valueOf(chaineNature);
            Case newCase = new Case(lig, col, nature); // Initialisation de la case
            carte.setCase(newCase); // Ajout de la case à la carte
            if (nature.equals(NatureTerrain.EAU)) {
            	simulation.getListeEau().add(newCase);
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }

        System.out.println();
    }


    /**
     * Lit et affiche les donnees des incendies.
     */
    private void lireIncendies(DonneesSimulation simulation) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            System.out.println("Nb d'incendies = " + nbIncendies);
            for (int i = 0; i < nbIncendies; i++) {
                lireIncendie(i, simulation); // Ajout de chaque incendie un à un
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }


    /**
     * Lit et affiche les donnees du i-eme incendie.
     * @param i
     */
    private void lireIncendie(int i, DonneesSimulation simulation) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Incendie " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            Incendie newIncendie = new Incendie(simulation.getCarte().getCase(lig, col), intensite); // Initialise l'incendie
            simulation.addIncendie(newIncendie); // Ajout de l'incendie à la liste
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            System.out.println("position = (" + lig + "," + col
                    + ");\t intensite = " + intensite);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et affiche les donnees des robots.
     */
    private void lireRobots(DonneesSimulation simulation) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            System.out.println("Nb de robots = " + nbRobots);
            for (int i = 0; i < nbRobots; i++) {
                lireRobot(i, simulation); // Ajout des robots un à un
            }

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }


    /**
     * Lit et affiche les donnees du i-eme robot.
     * @param i
     */
    private void lireRobot(int i, DonneesSimulation simulation) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Robot " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            System.out.print("position = (" + lig + "," + col + ");");
            String type = scanner.next();
            System.out.print("\t type = " + type);
            /**
             * On ajoute les robots à la liste des robots avec leur caractéristiques
             */
            if (type.equals("DRONE")) {
            	Drone newRobot = new Drone(simulation.getCarte().getCase(lig, col));
            	simulation.addRobot(newRobot);
            } else if (type.equals("ROUES")) {
            	RobotRoues newRobot = new RobotRoues(simulation.getCarte().getCase(lig, col));
            	simulation.addRobot(newRobot);
            } else if (type.equals("CHENILLES")) {
            	RobotChenilles newRobot = new RobotChenilles(simulation.getCarte().getCase(lig, col));
            	simulation.addRobot(newRobot);
            } else {
                RobotPattes newRobot = new RobotPattes(simulation.getCarte().getCase(lig, col));
                simulation.addRobot(newRobot);
            }
            
            // lecture eventuelle d'une vitesse du robot (entier)
            System.out.print("; \t vitesse = ");
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            // pour lire un flottant:    ("(\\d+(\\.\\d+)?)");

            if (s == null) {
                System.out.print("valeur par defaut");
            } else {
            	// Si une vitesse est spécifier alors on la place dans le robot
                int vitesse = Integer.parseInt(s);
                int indiceDernierRobot = simulation.getRobots().size() - 1;
                simulation.getRobot(indiceDernierRobot).setInitialSpeed((double) vitesse/(3.6*1000));
                System.out.print(vitesse);
                
            }
            verifieLigneTerminee();

            System.out.println();
            
            

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }




    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}
