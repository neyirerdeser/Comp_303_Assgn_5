import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWatchable implements Watchable {

    String aTitle;
    String aStudio;
    Language aLanguage;
    Map<String, String> aTags = new HashMap<>();
    private List<WatchList> aObservers = new ArrayList<>();

    public AbstractWatchable(String pTitle, String pStudio, Language pLanguage) {
        assert pTitle != null && pLanguage != null && pStudio != null;
        aTitle = pTitle;
        aStudio = pStudio;
        aLanguage = pLanguage;
    }

    @Override
    public void watch() {
        System.out.print("\nNow playing " + this.getTitle());
        notifyObservers();
    }

    @Override
    public final void notifyObservers() {
        for(WatchList observer : aObservers)
            observer.update(this);
    }

    @Override
    public final void addObserver(WatchList pWatchList) {
        aObservers.add(pWatchList);
    }

    @Override
    public final void removeObserver(WatchList pWatchList) {
        aObservers.remove(pWatchList);
    }

    @Override
    public abstract boolean isValid();

    @Override
    public final String getTitle() {
        return aTitle;
    }

    @Override
    public String getStudio() {
        return aStudio;
    }

    @Override
    public Language getLanguage() {
        return aLanguage;
    }

    @Override
    public final String setInfo(String key, String val) {
        assert key != null && !key.isBlank();
        if (val == null) return aTags.remove(key);
        else return aTags.put(key, val);
    }

    @Override
    public final String getInfo(String key) {
        assert hasInfo(key);
        return aTags.get(key);
    }

    @Override
    public final boolean hasInfo(String key) {
        assert key != null && !key.isBlank();
        return aTags.containsKey(key);
    }
}
