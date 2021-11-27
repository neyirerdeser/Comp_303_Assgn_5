public class SetNameCommand extends AbstractCommand {

    public SetNameCommand(WatchList pWatchList) {
        super(pWatchList);
    }

    @Override
    public void unExecute() {
        super.unExecute();
        aWatchList.aName = aPrevState.aName;
    }

    @Override
    public void reExecute() {
        super.reExecute();
        if(aNextState == null) return;
        aWatchList.aName = aNextState.aName;
    }
}
