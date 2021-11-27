received a grade of 70%

## Problem Statement

Using the principles, mechanisms, and techniques seen until Chapters 8.4 of the textbook, design and write the code necessary to enhance the movie library design from the baseline code (adapted from solutions to Assignment 4) to meet the following requirements. *Your solution should continue to respect principles of good design seen in all prior chapters.*

1. Add a `lastWatched()` method to the WatchList class. This method returns a reference to the last Watchable object that has been watched from the watchlist (i.e., on which the method `watch()` from Watchable was last called). Note that you should not rely on the method `next()` from WatchList to know the last watched object (a client might call `watch()` on an arbitrary object since the last call to `next()`). Do not loop through every Watchable object in a WatchList every time the method `lastWatched()` is called. [5 points]
2. Use class inheritance to group common properties and behaviors of `Watchable` implementations to reduce code duplication. Make sure to follow good design principles for inheritance. [5 points]
3. Consider the state-modifying actions of WatchList: `setName`, `addWatchable`, `removeWatchable`, `next`, and `reset`. Add two additional methods, `undo()` and `redo()`. Like most modern software, the `undo()` method reverses the effect of the last state-modifying method call. Calling `undo()` multiple times in a row undoes the actions in the reverse order they were performed, until the first action. Calling `undo()` after all actions have been undone does nothing. Similarly, `redo()` performs again the last undone action, and calling it multiple times performs the undone actions in the reverse order they were undone, until there are no more actions to redo. After the last undone action is redone, calling `redo()` does nothing. However, calling `redo()` right after an action other than `undo()` causes the last action to be repeated. For example, calling `redo()` right after `next()` is equivalent to calling `next()` a second time. If some actions are performed after calling `undo()`, it is no longer possible to `redo()` the undone actions, as it may corrupt the state of the WatchList (and `redo()` instead repeats the last action). As an example of the expected behavior, consider the undo and redo commands in Microsoft Word. Try typing "ABC", then hit Ctrl+Z and Ctrl+Y (or Cmd+Z/Cmd+Y on Mac, not Cmd+Shift+Z) in different sequences. Finally, create a UML state diagram to demonstrate your design. [10 points: same scheme, scaled from 0-5 to 0-10]

