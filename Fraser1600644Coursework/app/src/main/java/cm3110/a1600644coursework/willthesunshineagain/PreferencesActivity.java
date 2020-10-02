package cm3110.a1600644coursework.willthesunshineagain;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PreferencesActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //-------------------- FIELDS --------------------

    //----- TAG AND OTHER STRINGS -----

    private static final String TAG = "Fraser1600644Coursework.PreferencesActivity";
    private static String currentLocation = "";
    private static final String LOCATION_ADDDED = " was added to your location preferences.";
    private static final String LOCATION_ERROR = " could not be added to your list as you already have 10 locations.";
    private static final String LOCATION_DUPLICATE = " could not be added to your list as you have already chosen it.";
    private static final String LOCATION_LIMIT = "You must have at least 1 location to be able to download any forecasts.";

    //----- SHARED PREFERENCES -----

    private String sharedPrefFile = "cm3110.a1600644coursework.willthesunshineagain.demo";
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor mEditor;

    //----- LOCATIONS -----

    private ArrayList<String> locationsList = new ArrayList<>();
    private int numLocations = 0;
    private ArrayList<TextView> locationNames = new ArrayList<>();
    private ArrayList<Button> locationButtons = new ArrayList<>();

    //----- COMPONENTS -----

    private Button btnAdd;
    private Button btnRemove1;
    private Button btnRemove2;
    private Button btnRemove3;
    private Button btnRemove4;
    private Button btnRemove5;
    private Button btnRemove6;
    private Button btnRemove7;
    private Button btnRemove8;
    private Button btnRemove9;
    private Button btnRemove10;
    //Setting up the Buttons
    private TextView location1;
    private TextView location2;
    private TextView location3;
    private TextView location4;
    private TextView location5;
    private TextView location6;
    private TextView location7;
    private TextView location8;
    private TextView location9;
    private TextView location10;
    //Setting up the TextViews
    private Spinner spinLocations;
    //Setting up the Spinner

    //-------------------- METHODS --------------------

    //---------- APP LIFECYCLE ----------

    /**
     * Method that is run when the activity is created
     * This will run once, when the whole app loads
     * @param savedInstanceState The state of the app saved in a bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        //Default code
        Log.d(TAG, "In the onCreate event handler");
        //Logging message with the tag

        //---------- SHARED PREFERENCES INITIALISATION ----------

        sharedPrefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        mEditor = sharedPrefs.edit();
        //Setting up the shared preferences and the editor

        //---------- COMPONENT INITIALISATIONS ----------

        //----- SPINNER -----

        spinLocations = findViewById(R.id.spinnerLocations);
        //Setting up the Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.choiceOfLocations, android.R.layout.simple_spinner_item);
        //Creating the adapter for the Spinner
        spinLocations.setAdapter(adapter);
        //Applying the adapter
        spinLocations.setOnItemSelectedListener(this);
        //Adding the event listener to the spinner

        //----- TEXT VIEWS -----

        location1 = findViewById(R.id.location1);
        location2 = findViewById(R.id.location2);
        location3 = findViewById(R.id.location3);
        location4 = findViewById(R.id.location4);
        location5 = findViewById(R.id.location5);
        location6 = findViewById(R.id.location6);
        location7 = findViewById(R.id.location7);
        location8 = findViewById(R.id.location8);
        location9 = findViewById(R.id.location9);
        location10 = findViewById(R.id.location10);
        //Setting up the location TextViews

        //----- BUTTONS -----

        btnAdd = findViewById(R.id.buttonAdd);
        btnRemove1 = findViewById(R.id.buttonRemove1);
        btnRemove2 = findViewById(R.id.buttonRemove2);
        btnRemove3 = findViewById(R.id.buttonRemove3);
        btnRemove4 = findViewById(R.id.buttonRemove4);
        btnRemove5 = findViewById(R.id.buttonRemove5);
        btnRemove6 = findViewById(R.id.buttonRemove6);
        btnRemove7 = findViewById(R.id.buttonRemove7);
        btnRemove8 = findViewById(R.id.buttonRemove8);
        btnRemove9 = findViewById(R.id.buttonRemove9);
        btnRemove10 = findViewById(R.id.buttonRemove10);
        //Setting up the Buttons

        btnAdd.setOnClickListener(this);
        btnRemove1.setOnClickListener(this);
        btnRemove2.setOnClickListener(this);
        btnRemove3.setOnClickListener(this);
        btnRemove4.setOnClickListener(this);
        btnRemove5.setOnClickListener(this);
        btnRemove6.setOnClickListener(this);
        btnRemove7.setOnClickListener(this);
        btnRemove8.setOnClickListener(this);
        btnRemove9.setOnClickListener(this);
        btnRemove10.setOnClickListener(this);
        //Giving all the Buttons event listeners

        //----- SETTING UP THE EXISTING LOCATIONS -----

        prepTextViews();
        prepButtons();
        //Preparing the TextViews and Buttons for updating the interfaces
        fetchLocations();
        //Fetching the locations from the Shared Preferences
        locationsButtonsVisible();
        //Making any buttons and locations that need to be visible, visible
        locationsButtonsInvisible();
        //And making any buttons and locations that need to be invisible, invisible
    }

    /**
     * Method that is run when the activity is ready to be made visible to the user
     * and be interacted with
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "In the onStart event handler");
        //Calling super onStart method and logging message with the tag
    }

    /**
     * Method that is run when the activity is to be paused
     * This means that the activity is not in the foreground, but doesn't need to be destroyed
     */
    @Override
    protected void onPause() {
        saveLocations();
        //Updating the saved preferences
        super.onPause();
        Log.d(TAG, "In the onPause event handler");
        //Calling super onPause method and logging message with the tag
    }

    /**
     * Method that is run when another activity takes precedence over this one
     * Or when this activity has been terminated
     */
    @Override
    protected void onStop() {
        saveLocations();
        //Updating the saved preferences
        super.onStop();
        Log.d(TAG, "In the onStop event handler");
        //Calling super onStop method and logging message with the tag
    }

    /**
     * Method that is run when the user has finished completely with this activity
     * Lets the app know to destroy it
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "In the onDestroy event handler");
        //Calling super onDestroy method and logging message with the tag
    }

    /**
     * Method that is run when the user returns from another activity or from outside the app
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "In the onResume event handler");
        //Calling super onResume method and logging message with the tag
    }

    //---------- EVENT LISTENERS ----------

    /**
     * Method to deal with the event of a button being click on the Welcome interface
     * @param v The view (button) that has been clicked
     */
    @Override
    public void onClick(View v) {
        Toast t;
        String message;
        //Creating a Toast object and String for messages
        if (v.getId() == R.id.buttonAdd) {
            //If the user has chosen to add their currently selected location to their list
            if (numLocations < 10) {
                if (locationsList.contains(currentLocation)) {
                    message = LOCATION_DUPLICATE;
                    t = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
                    t.show();
                    //Telling the user they can't have duplicates
                } else {
                    int position = 0;
                    boolean found = false;
                    //Creating position marker and boolean flag variables
                    while ((position < locationsList.size()) && (found == false)) {
                        if (locationsList.get(position) == null) {
                            found = true;
                            //Setting the marker to true (the position marker will already be set)
                        } else {
                            position++;
                            //Incrementing the counter/position marker
                        }
                    }
                    //Finding the next null spot in the ArrayList
                    locationsList.remove(position);
                    locationsList.add(position, currentLocation);
                    //Adding the new location to the list
                    numLocations++;
                    //Incrementing the counter for the number of stored locations
                    locationsButtonsVisible();
                    locationsButtonsInvisible();
                    //Updating the user interface
                    message = currentLocation + LOCATION_ADDDED;
                    t = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
                    t.show();
                    //Telling the user that their location was added
                }
            } else {
                message = "Oops! " + currentLocation + LOCATION_ERROR;
                t = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
                t.show();
                //Telling the user they couldn't add any more locations
            }

        } else if (v.getId() == R.id.buttonRemove1) {
            //If the user has chosen to remove the first location
            if (numLocations == 1) {
                message = LOCATION_LIMIT;
                t = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
                t.show();
                //Telling the user they can't have less than 1 location
            } else {
                locationsList.remove(0);
                //Removing the location
                numLocations--;
                //Updating the counter
                locationsButtonsVisible();
                locationsButtonsInvisible();
                //Updating the interface
            }
        } else {
            if (v.getId() == R.id.buttonRemove2) {
                //If the user has chosen to remove the second location
                locationsList.remove(1);
                //Removing the location
            } else if (v.getId() == R.id.buttonRemove3) {
                //If the user has chosen to remove the third location
                locationsList.remove(2);
                //Removing the location
            } else if (v.getId() == R.id.buttonRemove4) {
                //If the user has chosen to remove the fourth location
                locationsList.remove(3);
                //Removing the location
            } else if (v.getId() == R.id.buttonRemove5) {
                //If the user has chosen to remove the fifth location
                locationsList.remove(4);
                //Removing the location
            } else if (v.getId() == R.id.buttonRemove6) {
                //If the user has chosen to remove the sixth location
                locationsList.remove(5);
                //Removing the location
            } else if (v.getId() == R.id.buttonRemove7) {
                //If the user has chosen to remove the seventh location
                locationsList.remove(6);
                //Removing the location
            } else if (v.getId() == R.id.buttonRemove8) {
                //If the user has chosen to remove the eighth location
                locationsList.remove(7);
                //Removing the location
            } else if (v.getId() == R.id.buttonRemove9) {
                //If the user has chosen to remove the ninth location
                locationsList.remove(8);
                //Removing the location
            } else if (v.getId() == R.id.buttonRemove10) {
                //If the user has chosen to remove the tenth location
                locationsList.remove(9);
                //Removing the location
            }
            numLocations--;
            //Updating the counter
            locationsButtonsVisible();
            locationsButtonsInvisible();
            //Updating the interface
        }
    }

    /**
     * Method to deal with the event of an item in a spinner being selected
     * @param parent The spinner's adapter
     * @param v The spinner
     * @param pos The position of the selected item in the spinner
     * @param id The ID of the selected item
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
        this.currentLocation = parent.getItemAtPosition(pos).toString();
        //Setting the current location that the user has picked from the spinner
    }

    /**
     * Method to deal with the event of no items being selected from a spinner
     * This method has no body as it is not necessary, but is required for the interface
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Do nothing
    }

    //---------- ADDITIONAL / HELPER METHODS ----------

    /**
     * Method to get the locations from the user's shared preferences
     */
    public void fetchLocations() {
        sharedPrefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //Setting up the shared preferences
        Log.d(TAG,"After setting up shared prefs");
        numLocations = 0;
        Log.d(TAG,"After initialising counter");
        //Initialising the counter
        for (int i = 1; i < 11; i++) {
            locationsList.add(sharedPrefs.getString("LOCATION_" + i, null));
            //Adding each location to the list
            Log.d(TAG, locationsList.toString());
        }
        for (int i = 0; i < locationsList.size(); i++) {
            if (locationsList.get(i) != null) {
                numLocations++;
                //If a value isn't null, the counter is updated
            }
        }
        //Updating the counter for how many locations
    }

    /**
     * Method to prep all of the TextView components for updating by adding them to an ArrayList
     */
    public void prepTextViews() {
        locationNames.add(location1);
        locationNames.add(location2);
        locationNames.add(location3);
        locationNames.add(location4);
        locationNames.add(location5);
        locationNames.add(location6);
        locationNames.add(location7);
        locationNames.add(location8);
        locationNames.add(location9);
        locationNames.add(location10);
        //Adding each TextView to the ArrayList
    }

    /**
     * Method to prep all of the Button components for updating by adding them to an ArrayList
     */
    public void prepButtons() {
        locationButtons.add(btnRemove1);
        locationButtons.add(btnRemove2);
        locationButtons.add(btnRemove3);
        locationButtons.add(btnRemove4);
        locationButtons.add(btnRemove5);
        locationButtons.add(btnRemove6);
        locationButtons.add(btnRemove7);
        locationButtons.add(btnRemove8);
        locationButtons.add(btnRemove9);
        locationButtons.add(btnRemove10);
        //Adding each Button to the ArrayList
    }

    /**
     * Method used to make any TextViews and Buttons needed visible
     */
    public void locationsButtonsVisible() {
        for (int i = 0; i < numLocations; i++) {
            locationNames.get(i).setVisibility(View.VISIBLE);
            locationButtons.get(i).setVisibility(View.VISIBLE);
            //Making the text and button visible
            locationNames.get(i).setText(locationsList.get(i));
            //Adding the name of the location to the TextView
        }
    }

    /**
     * Method used to get rid of any extra TextViews and Buttons that don't currently need to be displayed
     */
    public void locationsButtonsInvisible() {
        for (int i = 9; i > numLocations - 1; i--) {
            locationNames.get(i).setVisibility(View.INVISIBLE);
            locationButtons.get(i).setVisibility(View.INVISIBLE);
            //Making the text and button invisible
        }
    }

    /**
     * Method to save the chosen locations to the shared preferences once the user is done
     */
    public void saveLocations() {
        sharedPrefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        mEditor = sharedPrefs.edit();
        //Setting up the shared preferences and the editor
        mEditor.clear();
        //Clearing current saved locations
        for (int i = 0; i < numLocations; i++) {
            mEditor.putString("LOCATION_" + (i+1), locationsList.get(i));
            Log.d(TAG,locationsList.get(i));
        }
        //Adding current saved locations to the shared preferences
        mEditor.apply();
        //Applying the changes
    }

}
