package gcu.mpd.gtwite200.ui.routes;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;

import gcu.mpd.gtwite200.Event;
import gcu.mpd.gtwite200.Repository;

/**
 * @author Gael Twite S1824428
 */
public class RoutesViewModel extends AndroidViewModel {
    private Repository repo = Repository.getInstance();
    private LiveData<ArrayList<Event>> events;

    public RoutesViewModel(Application application) {
        super(application);
        events = repo.getAllEvents();
    }

    public LiveData<ArrayList<Event>> getEvents() {
        return events;
    }
}