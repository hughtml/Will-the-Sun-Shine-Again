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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cm3110.a1600644coursework.willthesunshineagain.utils.Converter;
import cm3110.a1600644coursework.willthesunshineagain.utils.ForecastLocation;
import cm3110.a1600644coursework.willthesunshineagain.utils.HttpDownloader;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    //-------------------- FIELDS --------------------

    //----- TAG AND OTHER STRINGS -----

    private static final String TAG = "Fraser1600644Coursework.WelcomeActivity";
    private static final String JSON_WEB_ADR_PART1 = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private static final String JSON_WEB_ADR_PART2 = ",uk&units=metric&APPID=b3dddf9efa1e2a08b8be9074c87df59e";
    private String currentLocation;
    private ArrayList<String> chosenLocations;

    //----- SHARED PREFERENCES -----

    private String sharedPrefFile = "cm3110.a1600644coursework.willthesunshineagain.demo";
    private SharedPreferences sharedPrefs;
    private SharedPreferences.Editor mEditor;

    //----- DATABASE -----

    private ForecastDao forecastDAO;

    //----- COMPONENTS -----

    private ImageButton btnPreferences;
    private ImageButton btnDownload;
    private ImageButton btnSearch;
    //Setting up ImageButtons
    private Spinner spinLocations;
    private ArrayAdapter<CharSequence> adapter;
    //Setting up the Spinner
    private ImageView imgMain;
    private TextView descWeather;
    private TextView currentTemp;
    private TextView highLowTemp;
    private TextView dayAndDate;
    private TextView currentTime;
    private TextView humidity;
    private TextView rainfall;
    private TextView windDirection;
    private TextView windSpeed;
    //Setting up the current weather summary
    private TextView forecastTime1;
    private TextView forecastTime2;
    private TextView forecastTime3;
    private TextView forecastTime4;
    private TextView forecastTime5;
    private TextView forecastTime6;
    private ImageView imgHourly1;
    private ImageView imgHourly2;
    private ImageView imgHourly3;
    private ImageView imgHourly4;
    private ImageView imgHourly5;
    private ImageView imgHourly6;
    private TextView forecastTemp1;
    private TextView forecastTemp2;
    private TextView forecastTemp3;
    private TextView forecastTemp4;
    private TextView forecastTemp5;
    private TextView forecastTemp6;
    //Setting up the hourly forecasts
    private TextView day1;
    private TextView day2;
    private TextView day3;
    private TextView day4;
    private TextView HighLow1;
    private TextView HighLow2;
    private TextView HighLow3;
    private TextView HighLow4;
    //Setting up the daily forecasts

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
        setContentView(R.layout.activity_welcome);
        //Default code
        Log.d("onCreate", "In the onCreate event handler");
        //Logging message with the tag

        //---------- DATABASE INITIALISATION ----------

        ForecastDatabase forecastDB = ForecastDatabase.getDatabase(getApplicationContext());
        //Getting the database instance
        this.forecastDAO = forecastDB.forecastDao();
        //Getting the DAO from the database
        Log.d("onCreate", "Initialised database okay");

        //---------- SHARED PREFERENCES INITIALISATION ----------

        sharedPrefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        mEditor = sharedPrefs.edit();
        updateLocations();
        //Setting up the shared preferences and the editor
        Log.d("onCreate", "Initialised the shared preferences okay");

        //---------- COMPONENT INITIALISATIONS ----------

        //----- IMAGE BUTTONS -----

        btnPreferences = findViewById(R.id.buttonPreferences);
        btnDownload = findViewById(R.id.buttonDownload);
        btnSearch = findViewById(R.id.buttonSearch);
        //Setting up the ImageButtons
        btnPreferences.setOnClickListener(this);
        btnDownload.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        //Giving all the buttons event listeners
        Log.d("onCreate", "Initialised the buttons and their click listeners okay");

        //----- SPINNER -----

        spinLocations = findViewById(R.id.spinnerLocations);
        //Setting up the spinner
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, chosenLocations);
        //Creating the adapter for the Spinner
        spinLocations.setAdapter(adapter);
        //Applying the adapter
        spinLocations.setOnItemSelectedListener(this);
        //Adding the event listener to the spinner
        Log.d("onCreate", "Set up spinners alright");

    }

    /**
     * Method that is run when the activity is ready to be made visible to the user
     * and be interacted with
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("onStart", "In the onStart event handler");
        //Calling super onStart method and logging message with the tag
    }

    /**
     * Method that is run when the activity is to be paused
     * This means that the activity is not in the foreground, but doesn't need to be destroyed
     */
    @Override
    protected void onPause() {
        saveLocations();
        super.onPause();
        Log.d("onPause", "In the onPause event handler");
        //Calling super onPause method and logging message with the tag
    }

    /**
     * Method that is run when another activity takes precedence over this one
     * Or when this activity has been terminated
     */
    @Override
    protected void onStop() {
        saveLocations();
        super.onStop();
        Log.d("onStop", "In the onStop event handler");
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
        updateLocations();
        super.onResume();
        Log.d("onResume", "In the onResume event handler");
        //Calling super onResume method and logging message with the tag
    }

    //---------- EVENT LISTENERS ----------

    /**
     * Method to deal with the event of a button being click on the Welcome interface
     * @param v The view (button) that has been clicked
     */
    @Override
    public void onClick(View v) {
        Context context = WelcomeActivity.this;
        //Creating context to be used for intents
        Log.d("onClick", "In the onClick method");
        if (v.getId() == R.id.buttonPreferences) {
        //If the user has opted to go to their preferences screen
            Class destinationActivity = PreferencesActivity.class;
            //Defining a class object and setting it to the destination activity class
            Intent intent = new Intent(context, destinationActivity);
            //Creating an Intent to start this activity
            startActivity(intent);
            //Starting the activity
        } else if (v.getId() == R.id.buttonSearch) {
        //If the user has opted to go to the search screen
            Class destinationActivity = SearchActivity.class;
            //Defining a class object and setting it to the destination activity class
            Intent intent = new Intent(context, destinationActivity);
            //Creating an Intent to start this activity
            startActivity(intent);
            //Starting the activity
        } else {
        //If the user has opted to go to download fresh forecast data
            deleteForecastsDB();
            Log.d("onClick","Old forecasts deleted");
            //Deleting the current forecasts in the database
            DownloaderTask task = new DownloaderTask();
            String formedURL;
            //Creating a new DownloaderTask object and a String to hold the URL later on
            formedURL = JSON_WEB_ADR_PART1 + currentLocation.replace(' ', '+') + JSON_WEB_ADR_PART2;
            //Forming the URL with each location the user has chosen
            //Any spaces in the location name are changed to +s for the API URL
            Log.d("Formed URL is ", formedURL);
            //Checking that the URL has been formed correctly
            try {
                URL downloadURL = new URL(formedURL);
                Log.d(TAG, "After url");
                task.execute(downloadURL);
                //Trying to launch the downloader task

            } catch (MalformedURLException e) {
                Log.d(TAG,"Malformed URL");
                e.printStackTrace();
                //Printing any errors/exceptions
            }
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
        currentLocation = parent.getItemAtPosition(pos).toString();
        //Storing the current location
        getForecastsForCurrentLocation();
        //Calling a helper to update the interface for the current location
        Toast t = Toast.makeText(getBaseContext(), currentLocation, Toast.LENGTH_SHORT);
        t.show();
        //Checking that selecting an item is registered
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

    //---------- ADDITIONAL / HELPER CLASSES ----------

    /**
     * Method to get the locations from the user's shared preferences
     */
    public void updateLocations() {
        sharedPrefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //Setting up the shared preferences
        Log.d("Updating locations" ,"updatelocs");
        //Initialising the counter
        chosenLocations = new ArrayList<>();
        //Reinitialising the ArrayList
        int i = 1;
        //Setting up a counter
        while (sharedPrefs.getString("LOCATION_" + i, null) != null) {
            Log.d(TAG,sharedPrefs.getString("LOCATION_" + i, null));
            //Testing the result given
            chosenLocations.add(sharedPrefs.getString("LOCATION_" + i, null));
            //Adding the location to the
            i++;
            //Adding each location to the list
        }
        Log.d("Updating locations ", String.valueOf(chosenLocations.size()));
        //Testing the size of the ArrayList
        spinLocations = findViewById(R.id.spinnerLocations);
        //Setting up the spinner
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, chosenLocations);
        //Creating the adapter for the Spinner
        spinLocations.setAdapter(adapter);
        //Applying the adapter
    }

    /**
     * Method to save the chosen locations to the shared preferences once the user is done
     */
    public void saveLocations() {
        sharedPrefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        mEditor = sharedPrefs.edit();
        //Setting up the shared preferences and the editor
        for (int i = 0; i < chosenLocations.size(); i++) {
            mEditor.putString("LOCATION_" + i+1, chosenLocations.get(i));
        }
        //Adding the locations to the shared preferences
        mEditor.apply();
        //Applying the changes
    }

    /**
     * Method to update the main summary components to show the details of the current forecast
     * @param forecast The Forecast object to get the data from
     */
    public void updateMainSummary(Forecast forecast) {

        //----- SETTING UP COMPONENTS -----

        descWeather = findViewById(R.id.weatherDescription);
        currentTemp = findViewById(R.id.locationTemp);
        highLowTemp = findViewById(R.id.highTempLowTemp);
        dayAndDate = findViewById(R.id.dayAndDate);
        currentTime = findViewById(R.id.currentTime);
        humidity = findViewById(R.id.humidityLevel);
        rainfall = findViewById(R.id.rainFall);
        windDirection = findViewById(R.id.windDirection);
        windSpeed = findViewById(R.id.windDescription);
        imgMain = findViewById(R.id.imageMain);
        //Finding the view components

        //----- SETTING VALUES/UPDATING THE GUI -----

        descWeather.setText(Converter.weatherDesc(String.valueOf(forecast.getDescription())));
        currentTemp.setText(String.valueOf(forecast.getTemp()) + "°C");
        highLowTemp.setText(String.valueOf(forecast.getHighTemp()) + "°C | " + String.valueOf(forecast.getLowTemp()) + "°C");
        dayAndDate.setText(Converter.date(String.valueOf(forecast.getDate())));
        currentTime.setText("At " + String.valueOf(forecast.getTime()));
        humidity.setText(String.valueOf(forecast.getHumidity()) + "%");
        rainfall.setText(String.valueOf(Math.round(forecast.getRainfall() * 100.0)/100.0) + " mm");
        windDirection.setText(String.valueOf(Converter.windDirection(forecast.getWindDirection())));
        windSpeed.setText(String.valueOf(Converter.windSpeed(forecast.getWindSpeed())));
        //Setting all of the text values
        setWeatherIcon(forecast.getWeatherId(), imgMain);
        //Caller the helper method for the weather icon

    }

    /**
     * Method to update the hourly forecast components
     * @param forecasts An ArrayList of Forecast objects to get the data from
     */
    public void updateHourlyForecasts(List<Forecast> forecasts) {

        //----- SETTING UP FORECASTS -----

        Forecast forecast1 = forecasts.get(1);
        Forecast forecast2 = forecasts.get(2);
        Forecast forecast3 = forecasts.get(3);
        Forecast forecast4 = forecasts.get(4);
        Forecast forecast5 = forecasts.get(5);
        Forecast forecast6 = forecasts.get(6);
        //Storing each of the incremented forecasts

        //----- SETTING UP COMPONENTS -----

        forecastTime1 = findViewById(R.id.forecastTime1);
        forecastTime2 = findViewById(R.id.forecastTime2);
        forecastTime3 = findViewById(R.id.forecastTime3);
        forecastTime4 = findViewById(R.id.forecastTime4);
        forecastTime5 = findViewById(R.id.forecastTime5);
        forecastTime6 = findViewById(R.id.forecastTime6);
        //Hourly time stamps
        imgHourly1 = findViewById(R.id.imageHourly1);
        imgHourly2 = findViewById(R.id.imageHourly2);
        imgHourly3 = findViewById(R.id.imageHourly3);
        imgHourly4 = findViewById(R.id.imageHourly4);
        imgHourly5 = findViewById(R.id.imageHourly5);
        imgHourly6 = findViewById(R.id.imageHourly6);
        //Weather icons
        forecastTemp1 = findViewById(R.id.forecastTemp1);
        forecastTemp2 = findViewById(R.id.forecastTemp2);
        forecastTemp3 = findViewById(R.id.forecastTemp3);
        forecastTemp4 = findViewById(R.id.forecastTemp4);
        forecastTemp5 = findViewById(R.id.forecastTemp5);
        forecastTemp6 = findViewById(R.id.forecastTemp6);
        //Temperatures

        //----- SETTING VALUES/UPDATING THE GUI -----

        forecastTime1.setText(forecast1.getTime());
        forecastTime2.setText(forecast2.getTime());
        forecastTime3.setText(forecast3.getTime());
        forecastTime4.setText(forecast4.getTime());
        forecastTime5.setText(forecast5.getTime());
        forecastTime6.setText(forecast6.getTime());
        //Setting the text of the time stamps (current, + 3 hours, + 6 hours... up to + 12 hours)
        setWeatherIcon(forecast1.getWeatherId(), imgHourly1);
        setWeatherIcon(forecast2.getWeatherId(), imgHourly2);
        setWeatherIcon(forecast3.getWeatherId(), imgHourly3);
        setWeatherIcon(forecast4.getWeatherId(), imgHourly4);
        setWeatherIcon(forecast5.getWeatherId(), imgHourly5);
        setWeatherIcon(forecast6.getWeatherId(), imgHourly6);
        //Setting the weather icons for each time stamp
        forecastTemp1.setText(String.valueOf(forecast1.getTemp()) + "°C");
        forecastTemp2.setText(String.valueOf(forecast2.getTemp()) + "°C");
        forecastTemp3.setText(String.valueOf(forecast3.getTemp()) + "°C");
        forecastTemp4.setText(String.valueOf(forecast4.getTemp()) + "°C");
        forecastTemp5.setText(String.valueOf(forecast5.getTemp()) + "°C");
        forecastTemp6.setText(String.valueOf(forecast6.getTemp()) + "°C");
        //Setting the text of the temperatures for each time stamp

    }

    /**
     * Method to update the hourly forecast components
     * @param forecasts An ArrayList of Forecast objects to get the data from
     */
    public void updateDailyForecasts(List<Forecast> forecasts) {

        //----- SETTING UP FORECASTS -----

        Forecast forecast1 = forecasts.get(8);
        Forecast forecast2 = forecasts.get(16);
        Forecast forecast3 = forecasts.get(24);
        Forecast forecast4 = forecasts.get(32);
        //It's known that 24 hours spans 8 forecasts

        //----- SETTING UP COMPONENTS -----

        day1 = findViewById(R.id.day1);
        day2 = findViewById(R.id.day2);
        day3 = findViewById(R.id.day3);
        day4 = findViewById(R.id.day4);
        //Setting up the text fields to store the days
        HighLow1 = findViewById(R.id.day1HighLow);
        HighLow2 = findViewById(R.id.day2HighLow);
        HighLow3 = findViewById(R.id.day3HighLow);
        HighLow4 = findViewById(R.id.day4HighLow);
        //Setting up the text fields for the highest and lowest temperatures for each of the days

        //----- SETTING VALUES/UPDATING THE GUI -----

        day1.setText(Converter.findDayOfWeek(String.valueOf(forecast1.getDate())));
        day2.setText(Converter.findDayOfWeek(String.valueOf(forecast2.getDate())));
        day3.setText(Converter.findDayOfWeek(String.valueOf(forecast3.getDate())));
        day4.setText(Converter.findDayOfWeek(String.valueOf(forecast4.getDate())));
        //Setting the values for the days of the week
        HighLow1.setText(String.valueOf(forecast1.getHighTemp()) + "°C | " + String.valueOf(forecast1.getLowTemp()) + "°C");
        HighLow2.setText(String.valueOf(forecast2.getHighTemp()) + "°C | " + String.valueOf(forecast2.getLowTemp()) + "°C");
        HighLow3.setText(String.valueOf(forecast3.getHighTemp()) + "°C | " + String.valueOf(forecast3.getLowTemp()) + "°C");
        HighLow4.setText(String.valueOf(forecast4.getHighTemp()) + "°C | " + String.valueOf(forecast4.getLowTemp()) + "°C");
        //Setting the values for the highest and lowest temperatures for each of the days

    }

    /**
     * Method to set an image/icon to display a certain type of weather condition depending on the id
     * @param weatherId The id for the weather condition
     * @param imageToSet The ImageView component to set the image of
     */
    public void setWeatherIcon(int weatherId, ImageView imageToSet) {
        int convertedId = weatherId / 100;
        if (weatherId == 800) {
            imageToSet.setImageResource(R.drawable.sunny_clear);
        } else {
            switch (convertedId) {
                case 2:
                    //storm
                    imageToSet.setImageResource(R.drawable.cloudy_stormy);
                    break;
                case 3:
                    //drizzle
                    imageToSet.setImageResource(R.drawable.cloudy_sunny_rain);
                    break;
                case 5:
                    //rain
                    imageToSet.setImageResource(R.drawable.cloudy_rain);
                    break;
                case 6:
                    //snow
                    imageToSet.setImageResource(R.drawable.cloudy_snow);
                    break;
                case 7:
                    imageToSet.setImageResource(R.drawable.fog);
                    break;
                case 8:
                    imageToSet.setImageResource(R.drawable.cloudy);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Method to call the relevant AsyncTask to insert newly downloaded Forecasts to the database
     * @param forecastsToAdd An ArrayList of Forecast objects to add to the database
     */
    private void insertForecastsDB(ArrayList<Forecast> forecastsToAdd) {
        InsertForecastsTask insertForecastsTask = new InsertForecastsTask();
        //Creating a new InsertForecastsTask to use for added to the DB
        Forecast[] forecastsToAddArray = new Forecast[forecastsToAdd.size()];
        forecastsToAddArray = forecastsToAdd.toArray(forecastsToAddArray);
        //Converting the ArrayList to an array for use with the DAO
        insertForecastsTask.execute(forecastsToAddArray);
        //Sending the array through to the task to be added
    }

    /**
     * Method to call the AsyncTask to delete all the forecasts currently in the database
     */
    private void deleteForecastsDB() {
        DeleteForecastsTask deleteForecastsTask = new DeleteForecastsTask();
        //Creating a new DeleteForecastsTask object
        deleteForecastsTask.execute();
        //Telling the object to execute
    }

    /**
     * Method to call the AsyncTask to get all the forecasts currently in the database
     */
    private void getForecastsForCurrentLocation() {
        UpdateGUIForLocationTask updateGUIForLocationTask = new UpdateGUIForLocationTask();
        updateGUIForLocationTask.execute();
    }

    //---------- NESTED DATABASE CLASSES ----------

    /**
     * Class to get the DAO to interact with the database on a seperate thread
     * This is to insert forecasts to the database
     */
    public class InsertForecastsTask extends AsyncTask<Forecast, Void, Void> {

        @Override
        protected Void doInBackground(Forecast... forecasts) {
            Log.d(TAG, "In the insert task");
            forecastDAO.insertForecasts(forecasts);
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }

    }

    /**
     * Class to get the DAO to interact with the database on a seperate thread
     * This is to delete all forecasts for a specific location from the database
     */
    public class DeleteForecastsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, "In the delete task");
            forecastDAO.deleteAllForecastsFor(currentLocation);
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }

    }

    /**
     * Class to get the DAO to interact with the database on a seperate thread
     * This is to get all forecasts for a specific location from the database
     */
    public class UpdateGUIForLocationTask extends AsyncTask<Void, Void, List<Forecast>> {

        @Override
        protected List<Forecast> doInBackground(Void... params) {
            Log.d(TAG, "In the update task");
            return forecastDAO.getAllForecastsForLocation(currentLocation);
        }

        @Override
        protected void onPostExecute(List<Forecast> forecasts) {
            super.onPostExecute(forecasts);
            if (forecasts.size() != 0) {
                updateMainSummary(forecasts.get(0));
                updateHourlyForecasts(forecasts);
                updateDailyForecasts(forecasts);
                //Calling the update helper methods
            } else {
                String message = "Oops! You haven't downloaded any forecasts for this location yet.";
                Toast t = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
                t.show();
                //Telling the user they haven't got any stored forecasts for this location
            }
        }
    }

    //---------- OTHER NESTED CLASSES ----------

    /**
     * Class used to download the JSON from the API, parse it and format it
     */
    public class DownloaderTask extends AsyncTask<URL, Void, ArrayList<ForecastLocation>> {

        private final static String JSONTAG = "In the DownloaderTask method";

        @Override
        protected void onPostExecute(ArrayList<ForecastLocation> forecastLocations) {
            super.onPostExecute(forecastLocations);
            Log.d("Returned list size", String.valueOf(forecastLocations.size()));
            //Notifying log of position

            //----- ADDING TO DATABASE -----

            ArrayList<Forecast> forecastsToAdd = new ArrayList<>();
            //Creating an empty ArrayList to store all the Forecast objects that are going to be added to the database
            for (int i = 0; i < forecastLocations.size(); i++) {
                Forecast forecastToAdd = new Forecast();
                Log.d(TAG, "In forecast for loop");
                //Creating a new Forecast object to be added to the ArrayList
                forecastToAdd.setCity(String.valueOf(forecastLocations.get(i).getName()));
                forecastToAdd.setCountry(String.valueOf(forecastLocations.get(i).getCountry()));
                forecastToAdd.setDescription(String.valueOf(forecastLocations.get(i).getDescription()));
                forecastToAdd.setWeatherId(forecastLocations.get(i).getWeatherId());
                forecastToAdd.setTime(Converter.time(forecastLocations.get(i).getTime()));
                forecastToAdd.setDate(Converter.date(Converter.dateParts(forecastLocations.get(i).getTime())));
                forecastToAdd.setTemp(forecastLocations.get(i).getTemp());
                forecastToAdd.setLowTemp(forecastLocations.get(i).getLowTemp());
                forecastToAdd.setHighTemp(forecastLocations.get(i).getHighTemp());
                forecastToAdd.setHumidity(forecastLocations.get(i).getHumidity());
                forecastToAdd.setRainfall(forecastLocations.get(i).getRainfall());
                forecastToAdd.setWindSpeed(forecastLocations.get(i).getWindSpeed());
                forecastToAdd.setWindDirection(forecastLocations.get(i).getWindDirection());
                //Using the setters to add to the object from the JSON
                /* Log.d(TAG, forecastToAdd.getCity());
                Log.d(TAG, forecastToAdd.getCountry());
                Log.d(TAG, forecastToAdd.getDescription());
                Log.d(TAG, String.valueOf(forecastToAdd.getWeatherId()));
                Log.d(TAG, forecastToAdd.getTime());
                Log.d(TAG, forecastToAdd.getDate());
                Log.d(TAG, String.valueOf(forecastToAdd.getTemp()));
                Log.d(TAG, String.valueOf(forecastToAdd.getLowTemp()));
                Log.d(TAG, String.valueOf(forecastToAdd.getHighTemp()));
                Log.d(TAG, String.valueOf(forecastToAdd.getHumidity()));
                Log.d(TAG, String.valueOf(forecastToAdd.getRainfall()));
                Log.d(TAG, String.valueOf(forecastToAdd.getWindSpeed()));
                Log.d(TAG, String.valueOf(forecastToAdd.getWindDirection())); */
                Log.d(TAG, forecastToAdd.toString());
                //Testing
                forecastsToAdd.add(forecastToAdd);
                //Adding the new Forecast object to the ArrayList
            }
            Log.d(TAG, String.valueOf(forecastsToAdd.size()));
            //Testing
            insertForecastsDB(forecastsToAdd);
            //Adding the Forecasts to the database
            getForecastsForCurrentLocation();
            //Updating the GUI
        }

        @Override
        protected ArrayList<ForecastLocation> doInBackground(URL... urls) {
            Log.d(TAG, "doInBackground");

            URL downloadURL = urls[0];
            //Assuming there is only 1 URL in the parameters
            String downloaded = null;
            //Creating an empty String to store the downloaded data
            ArrayList<ForecastLocation> forecastLocations = new ArrayList<>();
            //Creating an empty ArrayLust of ForecastLocation objects
            try {
                downloaded = HttpDownloader.getResponseFromHttpUrl(downloadURL);
                //Using the HttpDownloader class to get the String of downloaded data
            } catch (IOException ioe) {
                ioe.printStackTrace();
                //Handling the exception
            }
            //Attempting to download the JSON data
            if (downloaded != null) {
                forecastLocations = parseJSON(downloaded);
                //If JSON was successfully downloaded, it is parsed
            }
            return forecastLocations;
            //The ArrayList is returned, even if it is still empty
        }

        private ArrayList<ForecastLocation> parseJSON(String downloaded) {
            Log.d(TAG, "In the parse");
            Log.d(TAG, downloaded);

            ArrayList<ForecastLocation> forecastLocations = new ArrayList<>();
            //Creating a new empty ArrayList to store the JSON once it's parsed
            try {

                //---------- WHOLE JSON DATA ----------

                JSONObject data = new JSONObject(downloaded);
                //Storing the downloaded data in a JSON object

                //---------- JSON OBJECTS/ARRAYS WHICH HAVE DESIRED VALUES ----------

                JSONObject city = data.getJSONObject("city");
                //Getting the JSONObject called city from the data
                JSONArray weatherList = data.getJSONArray("list");
                //Getting the JSONArray called list from the data
                ArrayList<JSONObject> weathers = new ArrayList<>();
                //Creating an ArrayList to store every weather JSONObject
                for (int i = 0; i < weatherList.length()-1; i++) {
                    Log.d(TAG,weatherList.getJSONObject(i).toString());
                    weathers.add(weatherList.getJSONObject(i));
                    //Adding all JSON weather objects to the ArrayList
                }

                //---------- TAKING VALUES FROM JSON OBJECTS/ARRAYS ----------

                String name = city.getString("name");
                String country = city.getString("country");
                //Getting values about the location itself

                for (int i = 0; i < weathers.size(); i++) {
                    Log.d(TAG,"In the second for loop");
                    String description = weathers.get(i).getJSONArray("weather").getJSONObject(0).getString("description"); //Description of the current weather
                    int weatherId = weathers.get(i).getJSONArray("weather").getJSONObject(0).getInt("id");
                    String time = weathers.get(i).getString("dt_txt");
                    Log.d(TAG,"After dt");
                    int temp = (int)weathers.get(i).getJSONObject("main").getDouble("temp");
                    Log.d(TAG,"After temp");
                    int lowTemp = (int)weathers.get(i).getJSONObject("main").getDouble("temp_min");
                    Log.d(TAG,"After temp_min");
                    int highTemp = (int)weathers.get(i).getJSONObject("main").getDouble("temp_max");
                    Log.d(TAG,"After temp_max");
                    int humidity = weathers.get(i).getJSONObject("main").getInt("humidity");
                    Log.d(TAG,"After humidity");
                    double rainfall;
                    if (weathers.get(i).getJSONObject("rain").length() != 0) {
                        rainfall = weathers.get(i).getJSONObject("rain").getDouble("3h");
                    } else {
                        rainfall = 0.0;
                    }
                    Log.d(TAG,"After rainfall");
                    //double snowfall = weathers.get(i).getJSONObject("snow").getDouble("3h");
                    Log.d(TAG,"After snowfall");
                    double windSpeed = weathers.get(i).getJSONObject("wind").getDouble("speed");
                    Log.d(TAG,"After wind speed");
                    double windDirection = weathers.get(i).getJSONObject("wind").getDouble("deg");
                    Log.d(TAG,"After wind direction");
                    //Getting the values about each forecast
                    ForecastLocation forecastLocation = new ForecastLocation(name, country, description, weatherId, time, temp, lowTemp, highTemp, humidity, rainfall, windSpeed, windDirection);
                    //Creating the new object with the stored data
                    forecastLocations.add(forecastLocation);
                    //Adding the new object to the ArrayList
                    Log.d(TAG, forecastLocations.toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
                //Handling the exception
            }
            //Attempting to parse the JSON data
            Log.d(TAG,forecastLocations.toString());
            return forecastLocations;
            //Returning the ArrayList, even if it is empty
        }

    }

}
