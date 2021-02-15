package map;

/**
 * 
 * Définie une date en ms et permet l'affichage lisible (classe utile seulement pour faire un affichage
 * de la forme XX minutes XX secondes) 
 *
 */
public class Date {
	
	long date;
	
	/**
	 * 
	 * @param date
	 */
	public Date(long date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		String secondes = Long.toString((long)((date % 60000)/1000));
		String minutes = Long.toString((long)((date / 60000) % 60));
		String heures = Long.toString((long)(date / 3600000));
		if (heures.length() == 1) heures = "0" + heures;
		if (minutes.length() == 1) minutes = "0" + minutes;
		if (secondes.length() == 1) secondes = "0" + secondes;
		return heures + ":" + minutes + ":" + secondes;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}
	
	public void incremente(long pas) {
		this.date += pas;
	}
}