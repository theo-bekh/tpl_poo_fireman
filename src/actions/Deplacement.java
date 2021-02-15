package actions;
import robots.*;
import simulation.Evenement;
import map.*;

/**
 * 
 * Evenement déplacement d'un robot, déplace le robot dans la direction donnée
 *
 */
public class Deplacement extends Evenement {
	
	private Robot robot;
	private Direction direction;
	private boolean finVoyage;

	/**
	 * Constructeur
	 * @param date La date à laquelle l'évènement doit commencer 
	 * @param carte
	 * @param robot
	 * @param direction
	 * @param finVoyage Vaut vrai si il s'agit de l'arrivée de la suite de déplacement, faux sinon
	 */
	public Deplacement(long date, Carte carte, Robot robot, Direction direction, boolean finVoyage) {
		super(date, carte); //Necessaire pour initialiser avec une valeur avant de calculer la date d'execution
		this.robot = robot;
		this.direction = direction;
		this.finVoyage = finVoyage;
	}
	
	@Override
    public int compareTo(Evenement e) {
        return super.compareTo(e);
    }
	
	@Override
	public String toString() {
		return "Deplacement " + direction.toString() + " " + (new Date(this.getDate()));
	}
	
	/**
	 * Execute l'action liée à l'évènement
	 */
	public String execute() {
		if (robot.isLegalDeplacement(this.getNextCase())) {
			robot.setPosition(this.getNextCase());
			if (finVoyage) robot.setOccupe(false);
			return this.toString();
		} else {
			return "Deplacement illegal déso";
		}
	}
	
	/**
	 * 
	 * @return la case suivante selon le déplacement
	 * Attention, ne retourne pas la même valeur selon si appelée avant ou après execute()
	 */
	public Case getNextCase() {
		return this.getCarte().getVoisin(robot.getPosition(), direction);
	}
	
	/**
	 * 
	 * @return vrai si l'action est légale, faux sinon
	 */
	public boolean isLegalEvent() {
		return robot.isLegalDeplacement(this.getNextCase());
	}

	public Robot getRobot() {
		return robot;
	}

	public Direction getDirection() {
		return direction;
	}
}