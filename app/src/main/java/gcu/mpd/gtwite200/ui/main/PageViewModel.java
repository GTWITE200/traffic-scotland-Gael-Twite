package gcu.mpd.gtwite200.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import gcu.mpd.gtwite200.Event;
import gcu.mpd.gtwite200.Repository;

/**
 * @author Gael Twite S1824428
 */
public class PageViewModel extends AndroidViewModel {
    private Repository repo = Repository.getInstance();
    private LiveData<ArrayList<Event>> events;
    private LiveData<ArrayList<Event>> onGoingRoadworks;
    private LiveData<ArrayList<Event>> plannedRoadworks;
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();

    public PageViewModel(Application application) {
        super(application);
        events = repo.getCurrentEvents();
        onGoingRoadworks = repo.getOnGoingRoadworks();
        plannedRoadworks = repo.getPlannedRoadworks();
    }

    public int getIndex() { return mIndex.getValue(); }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<ArrayList<Event>> getCurrentEvents() {
        return events;
    }

    public LiveData<ArrayList<Event>> getOnGoingRoadworks() { return onGoingRoadworks; }

    public LiveData<ArrayList<Event>> getPlannedRoadworks() { return plannedRoadworks; }
}