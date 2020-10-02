package cm3110.a1600644coursework.willthesunshineagain;

import android.arch.persistence.room.*;
import java.util.*;

/**
 * Class to represent the database access object for the Forecast Database
 * @author Hugh Fraser 16000644
 * @version 1.0
 */
@Dao
public interface ForecastDao {

    /**
     * Method to insert multiple forecasts into the database
     * @param forecasts An ArrayList of forecasts
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForecasts(Forecast[] forecasts);

    /**
     * Method to get all the forecasts from the database
     * @return An ArrayList containing all of the forecasts
     */
    @Query("SELECT * FROM forecast WHERE City LIKE :city")
    List<Forecast> getAllForecastsForLocation(String city);

    /**
     * Method to delete all the forecasts from the database
     */
    @Query("DELETE FROM forecast")
    void deleteAllForecasts();

    /**
     * Method to delete all the forecasts from the database belonging to a certain location
     */
    @Query("DELETE FROM forecast WHERE City LIKE :city")
    void deleteAllForecastsFor(String city);

    /**
     * Method to get all forecasts for a location within a certain time range
     * @return An ArrayList containing all of the forecasts
     */
    @Query("SELECT * FROM forecast WHERE City LIKE :city ORDER BY Date ASC, Time ASC LIMIT :amount")
    List<Forecast> getAllForecastsHourly(String city, int amount);

    /**
     * Method to get all forecasts for a location with a value for a field greater than/equal to specified value
     * @return An ArrayList containing all of the forecasts
     */
    @Query("SELECT * FROM forecast WHERE :term >= :value AND City LIKE :city")
    List<Forecast> getAllForecastsGreaterValue(String city, String term, double value);

    /**
     * Method to get all forecasts for a location with a value for a field less than/equal to specified value
     * @return An ArrayList containing all of the forecasts
     */
    @Query("SELECT * FROM forecast WHERE :term <= :value AND City LIKE :city")
    List<Forecast> getAllForecastsLessValue(String city, String term, double value);

}
