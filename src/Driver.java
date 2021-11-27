import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Driver {
    public static void main(String[]args){
        TVShow t1 = new TVShow("Friends", Language.ENGLISH, "WarnerBrothers");                // should be included
        TVShow t2 = new TVShow("Le Mentaliste", Language.FRENCH, "WarnerBrothers");           // should be included
        TVShow t3 = new TVShow("La Teoría del Big Bang", Language.SPANISH, "WarnerBrothers"); // should not be included
        TVShow t4 = new TVShow("Daredevil", Language.ENGLISH, "MarvelStudio");                // should not be included

        t1.createAndAddEpisode(new File(""), "The One Where Monica Gets a Roommate");       // should be included
        t1.createAndAddEpisode(new File(""), "The One with the Sonogram at the End");
        t1.createAndAddEpisode(new File(""), "The One with the Thumb");

        t2.createAndAddEpisode(new File("/Users/neyirerdeser/Documents/pinkie.mov"), "Pilot");                                      // should be included
        t2.createAndAddEpisode(new File(""), "Red Hair and Silver Tape");
        t2.createAndAddEpisode(new File(""), "Red Tide");

        t3.createAndAddEpisode(new File(""), "Unaired Pilot");
        t3.createAndAddEpisode(new File(""), "Pilot");
        t3.createAndAddEpisode(new File(""), "The Big Bran Hypothesis");

        t4.createAndAddEpisode(new File(""), "Into the Ring");
        t4.createAndAddEpisode(new File(""), "Cut Man");
        t4.createAndAddEpisode(new File(""), "Rabbit in a Snowstorm");

        WatchList w1 = new WatchList("one");
        w1.addWatchable(t1);
        w1.addWatchable(t2);
        WatchList w2 = new WatchList("two");
        w2.addWatchable(t3);
        w2.addWatchable(t4);

        t2.watch();
        TVShow.Episode e1 = t2.getEpisode(1);
        w2.addWatchable(e1);
        e1.watch();
        // this effects w2 only, since e1 is not a direct element of w1
        // but it is a direct element of w2

        Optional<Watchable> l1 = w1.lastWatched();
        Optional<Watchable> l2 = w2.lastWatched();

        System.out.print("\n\nlast watched in w1: ");
        if(l1.isPresent()) System.out.println(l1.get().getTitle());
        else System.out.println("nothing!");
        System.out.print("last watched in w2: ");
        if(l2.isPresent()) System.out.println(l2.get().getTitle());
        else System.out.println("nothing!");



        System.out.println("=== ADD WATCHABLE TEST ===");
        System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext);            // one 2 0
        w1.undo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 1 0
        w1.undo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 0 0
        w1.redo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 1 0
        w1.redo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 2 0
        w1.undo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 1 0
        w1.redo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 2 0

        System.out.println("=== REMOVE WATCHABLE TEST ===");
        w1.removeWatchable(1);
        System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext);            // one 1 0
        w1.undo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 2 0
        w1.undo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 2 0
        w1.redo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 1 0

        System.out.println("=== SET NAME TEST ===");

        w1.setName("new"); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext);   // new 2 0
        w1.setName("abc"); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext);   // abc 2 0
        w1.undo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext);           // new 2 0
        w1.redo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext);           // abc 2 0
        w1.undo();
        w1.undo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext);           // one 2 0

        System.out.println("=== NEXT TEST ===");
        w1.addWatchable(t2);
        w1.addWatchable(t3);
        w1.addWatchable(t4);
        System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext);            // one 5 0
        w1.next(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 5 1
        w1.next(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 5 2
        w1.undo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 5 1
        w1.undo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 5 0
        w1.redo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 5 1
        w1.undo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 5 0
        w1.redo(); w1.redo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext); // one 5 2

        System.out.println("=== RESET TEST ===");
        w1.reset(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext);           // one 5 0
        w1.undo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext);            // one 5 2
        w1.redo(); System.out.println(w1.getName()+" "+w1.aList.size()+" "+w1.aNext);            // one 5 0












    }
}
