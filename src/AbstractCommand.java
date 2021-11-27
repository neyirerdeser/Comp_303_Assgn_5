public class AbstractCommand implements Command {

    WatchList aWatchList;
    WatchList aPrevState;
    WatchList aNextState;

    public AbstractCommand(WatchList pWatchList) {
        aWatchList = pWatchList;
    }

    @Override
    public void execute() {
        aPrevState = new WatchList(aWatchList);
    }

    @Override
    public void unExecute() {
        aNextState = new WatchList(aWatchList);
        aWatchList.aList = aPrevState.aList;
    }

    @Override
    public void reExecute() {
        aPrevState = new WatchList(aWatchList);
    }

}
