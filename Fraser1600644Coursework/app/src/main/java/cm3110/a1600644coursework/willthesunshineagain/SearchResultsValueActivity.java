package cm3110.a1600644coursework.willthesunshineagain;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import cm3110.a1600644coursework.willthesunshineagain.utils.Converter;

public class SearchResultsValueActivity extends AppCompatActivity {

    //-------------------- FIELDS --------------------

    //----- TAG AND OTHER STRINGS -----

    private static final String TAG = "Fraser1600644Coursework.SearchResultsValueActivity";
    private static double value;
    private static String location;
    private static String searchTerm;
    private static String greaterLess;

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
        setContentView(R.layout.activity_search_results_value);
        //Default code
        Log.d(TAG, "In the onCreate event handler");
        //Logging message with the ta

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
        searchTerm = sharedPrefs.getString("SEARCH_TERM", null);
        greaterLess = sharedPrefs.getString("GREATER_LESS_THAN", null);
        value = Double.parseDouble(sharedPrefs.getString("SEARCH_VALUE", "0.0"));
        Log.d(TAG, "Current location:" + location);
        Log.d(TAG, "They are searching for " + searchTerm);
        Log.d(TAG, "They want " + greaterLess);
        Log.d(TAG, "The value is " + String.valueOf(value));
        //Storing the values from the shared preferences
    }

    private void searchForecastsDB() {
        if (searchTerm.equals("Temperature (Celsius)")) {
            if (greaterLess.equals("G")) {
                SearchGreaterTemperatureForecastsTask searchForecastsTask = new SearchGreaterTemperatureForecastsTask();
                searchForecastsTask.execute();
            } else {
                SearchLessTemperatureForecastsTask searchForecastsTask = new SearchLessTemperatureForecastsTask();
                searchForecastsTask.execute();
            }

        } else if (searchTerm.equals("Humidity (Percentage)")) {
            if (greaterLess.equals("G")) {
                SearchGreaterHumidityForecastsTask searchForecastsTask = new SearchGreaterHumidityForecastsTask();
                searchForecastsTask.execute();
            } else {
                SearchLessHumidityForecastsTask searchForecastsTask = new SearchLessHumidityForecastsTask();
                searchForecastsTask.execute();
            }
        } else {
            if (greaterLess.equals("G")) {
                SearchGreaterWindSpeedForecastsTask searchForecastsTask = new SearchGreaterWindSpeedForecastsTask();
                searchForecastsTask.execute();
            } else {
                SearchLessWindSpeedForecastsTask searchForecastsTask = new SearchLessWindSpeedForecastsTask();
                searchForecastsTask.execute();
            }
        }

    }

    //---------- NESTED DATABASE CLASSES ----------

    public class SearchGreaterTemperatureForecastsTask extends AsyncTask<Void, Void, List<Forecast>> {

        @Override
        protected List<Forecast> doInBackground(Void... params) {
            Log.d(TAG, "In the search task");
            return forecastDAO.getAllForecastsGreaterValue(location, "Temp", value);
        }

        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            super.onPostExecute(forecasts);
            String output = "You searched for the temperatures in " + location + " which are greater than or equal to " + value + "°C";
            output += "\n\n";
            if (forecasts.size() != 0) {
                for (int i = 0; i < forecasts.size(); i++) {
                    output += "On ";
                    output += String.valueOf(Converter.findDayOfWeek(forecasts.get(i).getDate()));
                    output += " at ";
                    output += String.valueOf(forecasts.get(i).getTime()) + " the temperature will be ";
                    output += String.valueOf(forecasts.get(i).getTemp()) + "°C" + "\n";
                }
            } else {
                output += "Unfortunately, there were no results for that. Try downloading a new forecast and trying again.";
            }
            searchResults.setText(output);
            //Showing the user the output
        }
    }

    public class SearchLessTemperatureForecastsTask extends AsyncTask<Void, Void, List<Forecast>> {

        @Override
        protected List<Forecast> doInBackground(Void... params) {
            Log.d(TAG, "In the search task");
            return forecastDAO.getAllForecastsLessValue(location, "Temp", value);
        }

        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            super.onPostExecute(forecasts);
            String output = "You searched for the temperatures in " + location + " which are less than or equal to " + value + "°C";
            output += "\n\n";
            if (forecasts.size() != 0) {
                for (int i = 0; i < forecasts.size(); i++) {
                    output += "On ";
                    output += String.valueOf(Converter.findDayOfWeek(forecasts.get(i).getDate()));
                    output += " at ";
                    output += String.valueOf(forecasts.get(i).getTime()) + " the temperature will be ";
                    output += String.valueOf(forecasts.get(i).getTemp()) + "°C" + "\n";
                }
            } else {
                output += "Unfortunately, there were no results for that. Try downloading a new forecast and trying again.";
            }
            searchResults.setText(output);
            //Showing the user the output
        }
    }

    public class SearchGreaterHumidityForecastsTask extends AsyncTask<Void, Void, List<Forecast>> {

        @Override
        protected List<Forecast> doInBackground(Void... params) {
            Log.d(TAG, "In the search task");
            return forecastDAO.getAllForecastsGreaterValue(location, "Humidity", value);
        }

        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            super.onPostExecute(forecasts);
            String output = "You searched for the humidity levels in " + location + " which are greater than or equal to " + value + "%";
            output += "\n\n";
            if (forecasts.size() != 0) {
                for (int i = 0; i < forecasts.size(); i++) {
                    output += "On ";
                    output += String.valueOf(Converter.findDayOfWeek(forecasts.get(i).getDate())) + " it will be ";
                    output += " at ";
                    output += String.valueOf(forecasts.get(i).getTime()) + " the humidity will be ";
                    output += String.valueOf(forecasts.get(i).getHumidity()) + "%" + "\n";
                }
            } else {
                output += "Unfortunately, there were no results for that. Try downloading a new forecast and trying again.";
            }
            searchResults.setText(output);
            //Showing the user the output
        }
    }

    public class SearchLessHumidityForecastsTask extends AsyncTask<Void, Void, List<Forecast>> {

        @Override
        protected List<Forecast> doInBackground(Void... params) {
            Log.d(TAG, "In the search task");
            return forecastDAO.getAllForecastsLessValue(location, "Humidity", value);
        }

        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            super.onPostExecute(forecasts);
            String output = "You searched for the humidity levels in " + location + " which are greater than or equal to " + value + "%";
            output += "\n\n";
            if (forecasts.size() != 0) {
                for (int i = 0; i < forecasts.size(); i++) {
                    output += "On ";
                    output += String.valueOf(Converter.findDayOfWeek(forecasts.get(i).getDate())) + " it will be ";
                    output += " at ";
                    output += String.valueOf(forecasts.get(i).getTime()) + " the humidity will be ";
                    output += String.valueOf(forecasts.get(i).getHumidity()) + "%" + "\n";
                }
            } else {
                output += "Unfortunately, there were no results for that. Try downloading a new forecast and trying again.";
            }
            searchResults.setText(output);
            //Showing the user the output
        }
    }

    public class SearchGreaterWindSpeedForecastsTask extends AsyncTask<Void, Void, List<Forecast>> {

        @Override
        protected List<Forecast> doInBackground(Void... params) {
            Log.d(TAG, "In the search task");
            return forecastDAO.getAllForecastsGreaterValue(location, "WindSpeed", value);
        }

        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            super.onPostExecute(forecasts);
            String output = "You searched for the wind speeds in " + location + " which are greater than or equal to " + value + "metres per second.";
            output += "\n\n";
            if (forecasts.size() != 0) {
                for (int i = 0; i < forecasts.size(); i++) {
                    output += "On ";
                    output += String.valueOf(Converter.findDayOfWeek(forecasts.get(i).getDate()));
                    output += " at ";
                    output += String.valueOf(forecasts.get(i).getTime()) + " the wind speed will be ";
                    output += String.valueOf(forecasts.get(i).getWindSpeed()) + " m/s" + "\n";
                }
            } else {
                output += "Unfortunately, there were no results for that. Try downloading a new forecast and trying again.";
            }
            searchResults.setText(output);
            //Showing the user the output
        }
    }

    public class SearchLessWindSpeedForecastsTask extends AsyncTask<Void, Void, List<Forecast>> {

        @Override
        protected List<Forecast> doInBackground(Void... params) {
            Log.d(TAG, "In the search task");
            return forecastDAO.getAllForecastsLessValue(location, "WindSpeed", value);
        }

        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            super.onPostExecute(forecasts);
            String output = "You searched for the wind speeds in " + location + " which are less than or equal to " + value + "metres per second.";
            output += "\n\n";
            if (forecasts.size() != 0) {
                for (int i = 0; i < forecasts.size(); i++) {
                    output += "On ";
                    output += String.valueOf(Converter.findDayOfWeek(forecasts.get(i).getDate()));
                    output += " at ";
                    output += String.valueOf(forecasts.get(i).getTime()) + " the wind speed will be ";
                    output += String.valueOf(forecasts.get(i).getWindSpeed()) + " m/s" + "\n";
                }
            } else {
                output += "Unfortunately, there were no results for that. Try downloading a new forecast and trying again.";
            }
            searchResults.setText(output);
            //Showing the user the output
        }
    }

}
