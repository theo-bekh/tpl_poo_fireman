package map;

/**
 * 
 * Définie un incendie
 *
 */

public class Incendie implements Cloneable, Comparable<Incendie>{
	
	Case position;
	int eauNecessaire;
	boolean prisEnCharge = false; //true si un robot est missionné sur cet incendie, sinon false
	double distanceAuRobot;

	/**
	 * Constructeur
	 * @param position de l'incendie
	 * @param eauNecessaire : nécessaire pour éteindre le feu
	 */
	public Incendie(Case position, int eauNecessaire) {
		this.position = position;
		this.eauNecessaire = eauNecessaire;
		this.distanceAuRobot = Double.MAX_VALUE;
		
	}
	
	public Incendie() {
		
	}
	
	public boolean isPrisEnCharge() {
		return prisEnCharge;
	}

	public void setPrisEnCharge(boolean prisEnCharge) {
		this.prisEnCharge = prisEnCharge;
	}
	
	@Override
	public String toString() {
		return position.toString() + " " + Integer.toString(this.eauNecessaire) + "L";
	}

	public Case getPosition() {
		return position;
	}

	public void setPosition(Case position) {
		this.position = position;
	}

	public int getEauNecessaire() {
		return eauNecessaire;
	}

	public void setEauNecessaire(int eauNecessaire) {
		this.eauNecessaire = eauNecessaire;
	}
	
	public Object clone() throws CloneNotSupportedException {
		Incendie clone = (Incendie) super.clone();
		return clone;
		}
	
	@Override
    public int compareTo(Incendie incendie) {
        if(this.distanceAuRobot < incendie.distanceAuRobot) {
            return -1;
        } else {
        	return 1;
        }
    }

	public double getDistanceAuRobot() {
		return distanceAuRobot;
	}

	public void setDistanceAuRobot(double distanceAuRobot) {
		this.distanceAuRobot = distanceAuRobot;
	}
}