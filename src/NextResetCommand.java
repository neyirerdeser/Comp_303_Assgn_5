public class NextResetCommand extends AbstractCommand{

    public NextResetCommand(WatchList pWatchList) {
        super(pWatchList);
    }

    @Override
    public void unExecute() {
        super.unExecute();
        aWatchList.aNext = aPrevState.aNext;
    }

    @Override
    public void reExecute() {
        super.reExecute();
        if(aNextState == null) return;
        aWatchList.aNext = aNextState.aNext;
    }
}
