import java.util.ArrayList;
import java.util.List;

/**
 * Represents a video object that can be watched
 */
interface Watchable {

	/**
	 * Plays the video to the user
	 * @pre isValid()
	 */
	public void watch();

	/**
	 * updates each observer (watchlist) related to the watchable,
	 * in the case it is watched
	 */
	public void notifyObservers();

	/**
	 * adds the Watchlist to the list of oberservers,
	 * meaning the watchable is included in the watchlist
	 * @param pWatchList observer to be added
	 */
	public void addObserver(WatchList pWatchList);

	/**
	 * removes the watchlist from the list of observers to be notified
	 * @param pWatchList
	 */
	public void removeObserver(WatchList pWatchList);

	/**
	 * Indicates whether the Watchable element is ready to be played.
	 * 
	 * @return true if the file path points to an existing location that can be read and is not a directory
	 */
	public boolean isValid();
	public String getTitle();
	public String getStudio();
	public Language getLanguage();
	public String setInfo(String key, String val);
	public String getInfo(String key);
	public boolean hasInfo(String key);
}