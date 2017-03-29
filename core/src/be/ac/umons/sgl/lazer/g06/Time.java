package be.ac.umons.sgl.lazer.g06;
/**
 * Some static time functions
 */
public class Time {
	/**
	 * Return a pretty representation of the time. E.g. 2jours 3heures 2minutes 35secondes.
	 * @param seconds Time to represent in seconds.
	 * @param displayall If false, units that equals 0 won't be represented e.g. 0jours.
	 * @param longunits If true, long units will be used e.g. Xjours. If false, short units will be used e.g. Xj.
	 * @return String representation of time.
	 */
	public static String prettyTime(int seconds, boolean displayall, boolean longunits) {
		String pretty = "";
		if(seconds < 0) {
			seconds = -seconds;
			pretty = "- ";
		}
		
		int days = seconds/(24*60*60);
		seconds = seconds%(24*60*60);
		int hours = seconds/(60*60);
		seconds = seconds%(60*60);
		int min = seconds/60;
		seconds = seconds%60;
		
		if(displayall || days != 0) {
			pretty += Integer.toString(days)+(longunits ? "jours " : "j ");
		}
		if(displayall || hours != 0) {
			pretty += Integer.toString(hours)+(longunits ? "heures " : "h ");
		}
		if(displayall || min != 0) {
			pretty += Integer.toString(min)+(longunits ? "minutes " : "m ");
		}
		// also to display 0
		if(displayall || seconds != 0 || pretty.length() < 1) {
			pretty += Integer.toString(seconds)+(longunits ? "seconds" : "s ");
		}
		
		return pretty.trim();
	}
}
