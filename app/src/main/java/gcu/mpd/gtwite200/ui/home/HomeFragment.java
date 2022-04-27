package gcu.mpd.gtwite200.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.SupportMapFragment;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;
import java.util.Objects;

import gcu.mpd.gtwite200.AsyncTaskListener;
import gcu.mpd.gtwite200.Event;
import gcu.mpd.gtwite200.EventType;
import gcu.mpd.gtwite200.FetchRSSFeed;
import gcu.mpd.gtwite200.MapsActivity;
import gcu.mpd.gtwite200.R;
import gcu.mpd.gtwite200.TrafficURL;

/**
 * @author Gael Twite S1824428
 */
public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener, AsyncTaskListener {

    private HomeViewModel homeViewModel;
    private Spinner spinner;
    private MapsActivity mMap;
    private TextView tvEventTotal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        spinner = root.findViewById(R.id.spinnerEvent);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Objects.requireNonNull(getContext()), R.array.event_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        tvEventTotal = root.findViewById(R.id.totalEvents);
        JodaTimeAndroid.init(getContext());
        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        FetchRSSFeed fetchRSSFeed = null;

        if (text.equalsIgnoreCase("current incidents")) {
            fetchRSSFeed = new FetchRSSFeed(this, TrafficURL.currentIncidents, EventType.CURRENT_INCIDENT);
        }
        if (text.equalsIgnoreCase("ongoing roadworks")) {
            fetchRSSFeed = new FetchRSSFeed(this, TrafficURL.ongoingRoadworks, EventType.ONGOING_ROADWORK);
        }
        if (text.equalsIgnoreCase("planned roadworks")) {
            fetchRSSFeed = new FetchRSSFeed(this, TrafficURL.plannedRoadworks, EventType.PLANNED_ROADWORK);
        }
        fetchRSSFeed.execute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        System.out.println("Nothing");
    }

    @Override
    public void newEvents(ArrayList<Event> events) {
        tvEventTotal.setText("Number of events: " + events.size());
        mMap = new MapsActivity(events);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(mMap);
        }
    }
}