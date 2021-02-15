package simulation;
import actions.OrdreDeplacement;
import actions.OrdreDeverserEau;
import actions.OrdreDeverserSable;
import actions.OrdreRemplissage;
import map.*;

/**
 * 
 * Interface à implémenter pour chaque évènement
 *
 */
public abstract class Evenement implements Comparable<Evenement>{
	
	private Date date;
	private Carte carte;
	
	/**
	 * Constructeur
	 * @param date
	 * @param carte
	 */
	public Evenement(long date, Carte carte) {
		this.date = new Date(date);
		this.carte = carte;
	}
	
	public long getDate() {
		return this.date.getDate();
	}
	
	public void setDate(long date) {
		this.date.setDate(date);
	}
	
	
	/**
	 * On trie les évènements par date croissante, pour 2 dates égales, on passe un priorité les exécutions, puis les ordres
	 * @Override
	 */
    public int compareTo(Evenement e) {
        if(this.date.getDate() < e.date.getDate()) {
            return -1;
        } if(this.date.getDate() > e.date.getDate()) {
            return 1;
        } else if (this instanceof OrdreDeplacement || this instanceof OrdreDeverserSable || this instanceof OrdreDeverserEau || this instanceof OrdreRemplissage) {
        	return 1;
        } else {
        	return -1;
        }
    }
    
    @Override
    public boolean equals(Object other) {
        if(other instanceof Evenement) {
            Evenement e = (Evenement) other;
            return e.date.getDate() == this.date.getDate();
        }
        return false;
    }

    @Override
    public abstract String toString();
	
    /**
	 * Execute l'action liée à l'évènement
	 */
	public abstract String execute();

	public Carte getCarte() {
		return carte;
	}
}