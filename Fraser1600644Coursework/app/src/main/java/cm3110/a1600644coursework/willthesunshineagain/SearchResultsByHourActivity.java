package cm3110.a1600644coursework.willthesunshineagain;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsByHourActivity extends AppCompatActivity {

    //-------------------- FIELDS --------------------

    //----- TAG AND OTHER STRINGS -----

    private static final String TAG = "Fraser1600644Coursework.SearchResultsByHourActivity";
    private static int hours;
    private static int searchTime;
    private static String location;

    //----- SHARED PREFERENCES -----

    private String sharedPrefFile = "cm3110.a1600644coursework.willthesunshineagain.demo";
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor mEditor;

    //----- DATABASE -----

    private ForecastDao forecastDAO;

    //----- COMPONENTS -----

    private TextView searchResults;

    //-------------------- METHODS --------------------

    //---------- APP LIFECYCLE ----------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_by_hour);
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
        fetchParams();
        //Setting up the shared preferences and the editor
        Log.d(TAG, "Initialised the shared preferences okay");

        //---------- COMPONENT INITIALISATIONS ----------

        searchResults = findViewById(R.id.searchResults);
        //Setting up the TextView
        searchForecastsDB();
        //Running the search
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "In the onStart event handler");
        //Calling super onStart method and logging message with the tag
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "In the onDestroy event handler");
        //Calling super onDestroy method and logging message with the tag
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "In the onStop event handler");
        //Calling super onStop method and logging message with the tag
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "In the onPause event handler");
        //Calling super onPause method and logging message with the tag
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "In the onResume event handler");
        //Calling super onResume method and logging message with the tag
    }

    //---------- ADDITIONAL / HELPER METHODS ----------

    public void fetchParams() {
        sharedPrefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //Setting up the shared preferences
        location = sharedPrefs.getString("CURRENT_LOCATION", null);
        hours = sharedPrefs.getInt("SEARCH_TIME", 3);
        Log.d(TAG, location);
        Log.d(TAG, String.valueOf(hours));
        //Storing the values from the shared preferences
    }

    private void searchForecastsDB() {
        SearchForecastsTask searchForecastsTask = new SearchForecastsTask();
        searchForecastsTask.execute();
    }

    //---------- NESTED DATABASE CLASSES ----------

    public class SearchForecastsTask extends AsyncTask<Void, Void, List<Forecast>> {

        @Override
        protected List<Forecast> doInBackground(Void... params) {
            Log.d(TAG, "In the search task");

            switch (hours) {
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
            return forecastDAO.getAllForecastsHourly(location, (hours / 3));
        }

        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            super.onPostExecute(forecasts);
            String output = "You searched for the temperatures in " + location + " going up to " + hours + " hours:";
            output += "\n\n";
            if (forecasts.size() != 0) {
                for (int i = 0; i < forecasts.size(); i++) {
                    output += "At ";
                    output += String.valueOf(forecasts.get(i).getTime()) + " it will be ";
                    output += String.valueOf(forecasts.get(i).getTemp()) + "°C" + "\n";
                }
            } else {
                output += "Unfortunately, there were no results for that. Try downloading a new forecast and trying again.";
            }
            searchResults.setText(output);
            //Showing the user the output
        }
    }

}
