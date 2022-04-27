package gcu.mpd.gtwite200;

import java.util.ArrayList;

public interface AsyncTaskListener {
    void newEvents(ArrayList<Event> events);
}
