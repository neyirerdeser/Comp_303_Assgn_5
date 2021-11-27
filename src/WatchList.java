
import java.util.*;

/**
 * Represents a sequence of watchables to watch in FIFO order.
 */
public class WatchList implements Bingeable<Watchable> {

	protected List<Watchable> aList = new LinkedList<>();
	protected String aName;
	protected int aNext;
	private Optional<Watchable> aLastWatched = Optional.empty();

	private int undoPointer = -1;
	private int redoPointer = -1;
	private Stack<Command> undoStack = new Stack<>();
	private Stack<Command> redoStack = new Stack<>();

	/**
	 * Creates a new empty watchlist.
	 * 
	 * @param pName
	 *            the name of the list
	 * @pre pName!=null;
	 */
	public WatchList(String pName) {
		assert pName != null;
		aName = pName;
		aNext = 0;
	}

	public WatchList(WatchList pWatchList) {
		aName = pWatchList.aName;
		aList = new LinkedList<>(pWatchList.aList);
		aNext = pWatchList.aNext;
		aLastWatched = pWatchList.aLastWatched;
	}

	/**
	 * updates the lastWatched attribute with the most recent version
	 * @param pLastWatched most recent last watched information
	 */
	public void update(Watchable pLastWatched) {
		aLastWatched = Optional.of(pLastWatched);
	}

	public Optional<Watchable> lastWatched() {
		return aLastWatched;
	}

	
	public String getName() {
		return aName;
	}
	
	/**
	 * Adds a watchable at the end of this watchlist.
	 * also adds the watchlist to the observers of the watchable
	 * 
	 * @param pWatchable
	 *            the watchable to add
	 * @pre pWatchable!=null;
	 */
	public void addWatchable(Watchable pWatchable) {
		assert pWatchable != null;

		deleteElementsAfterPointer(undoPointer);
		redoStack = new Stack<>();
		redoPointer = -1;

		Command command = new AddRemoveWatchable(this);
		command.execute();
		undoStack.push(command);
		undoPointer++;

		aList.add(pWatchable);
		pWatchable.addObserver(this);

	}
	
	/**
	 * Retrieves and removes the Watchable at the specified index.
	 * also removes the watchlist from the observers of the watchable
	 * 
	 * @param pIndex
	 *            the position of the Watchable to remove
	 * @pre pIndex < getTotalCount() && pIndex >= 0
	 */
	public Watchable removeWatchable(int pIndex) {
		assert pIndex < aList.size() && pIndex >= 0;

		deleteElementsAfterPointer(undoPointer);
		redoStack = new Stack<>();
		redoPointer = -1;

		Command command = new AddRemoveWatchable(this);
		command.execute();
		undoStack.push(command);
		undoPointer++;

		if (aNext > pIndex) {
			aNext--;
		}
		Watchable pWatchable =  aList.remove(pIndex);
		pWatchable.removeObserver(this);
		return pWatchable;
	}
	
	/**
	 * @return the total number of valid watchable elements
	 */
	public int getValidCount() {
		int count = 0;
		for (Watchable item : aList) {
			if (item.isValid()) {
				count++;
			}
		}
		return count;
	}
	
	@Override
	public int getTotalCount() {
		return aList.size();
	}
	
	@Override
	public int getRemainingCount() {
		return aList.size() - aNext;
	}
	
	@Override
	public Watchable next() {
		assert getRemainingCount() > 0;

		deleteElementsAfterPointer(undoPointer);
		redoStack = new Stack<>();
		redoPointer = -1;

		Command command = new NextResetCommand(this);
		command.execute();
		undoStack.push(command);
		undoPointer++;

		Watchable next = aList.get(aNext);
		aNext++;
		if (aNext >= aList.size()) {
			aNext = 0;
		}
		return next;
	}
	
	@Override
	public void reset() {
		deleteElementsAfterPointer(undoPointer);
		redoStack = new Stack<>();
		redoPointer = -1;

		Command command = new NextResetCommand(this);
		command.execute();
		undoStack.push(command);
		undoPointer++;

		aNext = 0;
	}

	/**
	 * Changes the name of this watchlist.
	 *
	 * @param pName
	 *            the new name
	 * @pre pName!=null;
	 */
	public void setName(String pName) {
		assert pName != null;
		deleteElementsAfterPointer(undoPointer);
		redoStack = new Stack<>();
		redoPointer = -1;

		Command command = new SetNameCommand(this);
		command.execute();
		undoStack.push(command);
		undoPointer++;
		aName = pName;
	}

	private void deleteElementsAfterPointer(int undoRedoPointer) {
		if(undoStack.size()<1)return;
		for(int i = undoStack.size()-1; i > undoRedoPointer; i--)
		{
			undoStack.remove(i);
		}
	}

	public void undo() {
		if(undoPointer < 0) return;
		Command command = undoStack.get(undoPointer);

		redoStack.push(command);
		redoPointer++;

		command.unExecute();
		undoPointer--;
	}

	public void redo() {

		if(redoPointer < 0) {
			if(undoPointer >=0) {
				Command command = undoStack.get(undoPointer);
				command.reExecute();
			}
			return;
		}
		Command command = redoStack.get(redoPointer);

		undoStack.push(command);
		undoPointer++;

		command.reExecute();
		redoPointer--;
	}
	
	@Override
	public Iterator<Watchable> iterator() {
		return Collections.unmodifiableList(aList).iterator();
	}
}
