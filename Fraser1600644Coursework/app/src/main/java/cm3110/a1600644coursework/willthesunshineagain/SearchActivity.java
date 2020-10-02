package cm3110.a1600644coursework.willthesunshineagain;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //-------------------- FIELDS --------------------

    //----- TAG AND OTHER STRINGS -----

    private static final String TAG = "Fraser1600644Coursework.SearchActivity";
    private static String currentLocation1 = "";
    private static String currentLocation2 = "";
    private static String searchTerm = "";
    private static int searchTime;
    private static String greaterLessThan = "";
    private static double searchValue;
    private ArrayList<String> chosenLocations = new ArrayList<>();

    //----- SHARED PREFERENCES -----

    private String sharedPrefFile = "cm3110.a1600644coursework.willthesunshineagain.demo";
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor mEditor;

    //----- DATABASE -----

    private ForecastDao forecastDAO;

    //----- COMPONENTS -----

    private Spinner spinSearchLoc1;
    private Spinner spinSearchLoc2;
    private Spinner spinSearchTime;
    private Spinner spinSearchTerm;
    private Spinner spinGreaterLess;
    //Setting up the Spinners
    private Button btnSearch1;
    private Button btnSearch2;
    private Button btnDelete;
    //Setting up the Buttons
    private EditText valueField;
    //Setting up the EditText

    //-------------------- METHODS --------------------

    //---------- APP LIFECYCLE ----------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //Default code
        Log.d(TAG, "In the onCreate event handler");
        //Logging message with the tag

        //---------- DATABASE INITIALISATION ----------

        ForecastDatabase forecastDB = ForecastDatabase.getDatabase(getApplicationContext());
        //Getting the database instance
        this.forecastDAO = forecastDB.forecastDao();
        //Getting the DAO from the database
        Log.d(TAG, "Initialised database okay");

        //---------- SHARED PREFERENCES INITIALISATION ----------

        sharedPrefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        mEditor = sharedPrefs.edit();
        fetchLocations();
        //Setting up the shared preferences and the editor
        Log.d(TAG, "Initialised the shared preferences okay");

        //---------- COMPONENT INITIALISATIONS ----------

        //----- SPINNERS -----

        spinSearchLoc1 = findViewById(R.id.spinnerSearchLocation1);
        spinSearchLoc2 = findViewById(R.id.spinnerSearchLocation2);
        spinSearchTime = findViewById(R.id.spinnerSearchTime);
        spinSearchTerm = findViewById(R.id.spinnerSearchTerm);
        spinGreaterLess = findViewById(R.id.spinnerGreaterLessThan);
        //Setting up the Spinners
        ArrayAdapter<CharSequence> adapterLocations1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, chosenLocations);
        ArrayAdapter<CharSequence> adapterLocations2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, chosenLocations);
        ArrayAdapter<CharSequence> adapterSearchTimes = ArrayAdapter.createFromResource(this, R.array.searchTimes, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterSearchTerms = ArrayAdapter.createFromResource(this, R.array.searchTerms, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapterGreaterLessThan = ArrayAdapter.createFromResource(this, R.array.greaterLessThan, android.R.layout.simple_spinner_dropdown_item);
        //Creating the adapters
        spinSearchLoc1.setAdapter(adapterLocations1);
        spinSearchLoc2.setAdapter(adapterLocations2);
        spinSearchTime.setAdapter(adapterSearchTimes);
        spinSearchTerm.setAdapter(adapterSearchTerms);
        spinGreaterLess.setAdapter(adapterGreaterLessThan);
        //Applying the adapters
        spinSearchLoc1.setOnItemSelectedListener(this);
        spinSearchLoc2.setOnItemSelectedListener(this);
        spinSearchTime.setOnItemSelectedListener(this);
        spinSearchTerm.setOnItemSelectedListener(this);
        spinGreaterLess.setOnItemSelectedListener(this);
        //Adding event listeners

        //----- BUTTONS -----

        btnSearch1 = findViewById(R.id.buttonSearch1);
        btnSearch2 = findViewById(R.id.buttonSearch2);
        btnDelete = findViewById(R.id.buttonDeleteAll);
        //Setting up the Buttons
        btnSearch1.setOnClickListener(this);
        btnSearch2.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        //Giving all the buttons event listeners
        Log.d(TAG, "Initialised the buttons and their click listeners okay");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "In the onStart event handler");
        //Calling super onStart method and logging message with the tag
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "In the onPause event handler");
        //Calling super onPause method and logging message with the tag
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "In the onStop event handler");
        //Calling super onStop method and logging message with the tag
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "In the onDestroy event handler");
        //Calling super onDestroy method and logging message with the tag
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "In the onResume event handler");
        //Calling super onResume method and logging message with the tag
    }

    //---------- EVENT LISTENERS ----------

    @Override
    public void onClick(View v) {
        Context context = SearchActivity.this;
        //Creating context to be use for intents
        if (v.getId() == R.id.buttonSearch1) {
            //If the user has opted to search based on location and times
            saveSearchItems(1);
            //Saving the required items for the search query through a helper method
            Class destinationActivity = SearchResultsByHourActivity.class;
            //Defining class object and set it to the destination activity class
            Intent intent = new Intent(context, destinationActivity);
            //Creating an Intent to start this activity
            startActivity(intent);
            //Starting the activity
        } else if (v.getId() == R.id.buttonSearch2) {
            //If the user has opted to search based on location and a different factor
            saveSearchItems(2);
            //Saving the required items for the search query through a helper method
            Class destinationActivity = SearchResultsValueActivity.class;
            //Defining class object and set it to the destination activity class
            Intent intent = new Intent(context, destinationActivity);
            //Creating an Intent to start this activity
            startActivity(intent);
            //Starting the activity
        } else if (v.getId() == R.id.buttonDeleteAll) {
            deleteForecastsDB();
            //Deleting all of the forecasts from the database
            String message = "Successfully deleted all forecasts, ready for fresh ones!";
            Toast t = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
            t.show();
            //Telling the user they haven't got any stored forecasts for this location
        } else {
            //Do nothing
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
        if (parent.getId() == R.id.spinnerSearchLocation1) {
            currentLocation1 = parent.getItemAtPosition(pos).toString();
            //Log.d(TAG, currentLocation1);
            //Checking that selecting an item is registered
        } else if (parent.getId() == R.id.spinnerSearchLocation2) {
            currentLocation2 = parent.getItemAtPosition(pos).toString();
            //Checking that selecting an item is registered
        } else if (parent.getId() == R.id.spinnerSearchTime) {
            Log.d(TAG, String.valueOf(pos));
            Log.d(TAG, String.valueOf(searchTime));
            switch (pos) {
                case 0: searchTime = 3;
                        break;
                case 1: searchTime = 6;
                        break;
                case 2: searchTime = 9;
                        break;
                case 3: searchTime = 12;
                        break;
                case 4: searchTime = 15;
                        break;
                case 5: searchTime = 18;
                        break;
                case 6: searchTime = 21;
                        break;
                case 7: searchTime = 24;
                        break;
                case 8: searchTime = 27;
                        break;
                case 9: searchTime = 30;
                        break;
                case 10: searchTime = 33;
                        break;
                case 11: searchTime = 36;
                        break;
                case 12: searchTime = 39;
                        break;
                case 13: searchTime = 42;
                        break;
                case 14: searchTime = 45;
                        break;
                case 15: searchTime = 48;
                        break;
                case 16: searchTime = 51;
                        break;
                case 17: searchTime = 54;
                        break;
                case 18: searchTime = 57;
                        break;
                case 19: searchTime = 60;
                        break;
                case 20: searchTime = 63;
                        break;
                case 21: searchTime = 66;
                        break;
                case 22: searchTime = 69;
                        break;
                case 23: searchTime = 72;
                        break;
                case 24: searchTime = 75;
                        break;
                case 25: searchTime = 78;
                        break;
                case 26: searchTime = 81;
                        break;
                case 27: searchTime = 84;
                        break;
                case 28: searchTime = 87;
                        break;
                case 29: searchTime = 90;
                        break;
                case 30: searchTime = 93;
                        break;
                case 31: searchTime = 96;
                        break;
                case 32: searchTime = 99;
                        break;
                case 33: searchTime = 102;
                        break;
                case 34: searchTime = 105;
                        break;
                case 35: searchTime = 108;
                        break;
                case 36: searchTime = 111;
                        break;
                case 37: searchTime = 114;
                        break;
                case 38: searchTime = 117;
                        break;
                case 39: searchTime = 120;
                        break;
                default: break;
            }
            Log.d(TAG, String.valueOf(searchTime));
            //Checking that selecting an item is registered
        } else if (parent.getId() == R.id.spinnerSearchTerm) {
            searchTerm = parent.getItemAtPosition(pos).toString();
            //Checking that selecting an item is registered
        } else if (parent.getId() == R.id.spinnerGreaterLessThan) {
            if (parent.getItemAtPosition(pos).toString().equals("Greater Than or Equal To")) {
                greaterLessThan = "G";
            } else {
                greaterLessThan = "L";
            }
            //Checking that selecting an item is registered
        } else {
            //Do nothing
        }

    }

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
        //Initialising the counter
        int i = 1;
        //Setting up a counter
        while (sharedPrefs.getString("LOCATION_" + i, null) != null) {
            Log.d(TAG,sharedPrefs.getString("LOCATION_" + i, null));
            chosenLocations.add(sharedPrefs.getString("LOCATION_" + i, null));
            Log.d(TAG, chosenLocations.toString());
            i++;
            //Adding each location to the list
        }
    }

    /**
     * Method to save the search items needed for the query on the next activity
     * @param buttonId The id of the button that was pressed
     */
    public void saveSearchItems(int buttonId) {
        valueField = findViewById(R.id.valueField);
        sharedPrefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        mEditor = sharedPrefs.edit();
        //Setting up the shared preferences and the editor
        if (buttonId == 1) {
            //If the user has opted to search on an hourly basis
            mEditor.putString("CURRENT_LOCATION", currentLocation1);
            Log.d(TAG, currentLocation1);
            mEditor.putInt("SEARCH_TIME", searchTime);
            Log.d(TAG, String.valueOf(searchTime));
            //Adding the required values to the shared preferences
            mEditor.apply();
            //Applying the changes
        } else {
            //If the user has opted to search by a value
            mEditor.putString("CURRENT_LOCATION", currentLocation2);
            mEditor.putString("SEARCH_TERM", searchTerm);
            mEditor.putString("GREATER_LESS_THAN", greaterLessThan);
            mEditor.putString("SEARCH_VALUE", valueField.getText().toString());
            //Adding the required values to the shared preferences
            mEditor.apply();
            //Applying the changes
        }
    }

    private void deleteForecastsDB() {
        DeleteForecastsTask deleteForecastsTask = new DeleteForecastsTask();
        deleteForecastsTask.execute();
    }

    //---------- NESTED DATABASE CLASSES ----------

    public class DeleteForecastsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, "In the delete task");
            forecastDAO.deleteAllForecasts();
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }
    }


}
