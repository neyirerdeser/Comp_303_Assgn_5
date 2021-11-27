public class AddRemoveWatchable extends AbstractCommand {

    public AddRemoveWatchable(WatchList pWatchList) {
        super(pWatchList);
    }

    @Override
    public void unExecute() {
        super.unExecute();
        aWatchList.aList = aPrevState.aList;
    }

    @Override
    public void reExecute() {
        super.reExecute();
        if(aNextState == null) return;
        aWatchList.aList = aNextState.aList;
    }
}
