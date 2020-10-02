package cm3110.a1600644coursework.willthesunshineagain;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Class to represent the Forecast Database across all activities
 */
@Database(entities = {Forecast.class}, version = 1)
public abstract class ForecastDatabase extends RoomDatabase {

    public abstract ForecastDao forecastDao();
    //Providing the database access object for the database
    private static ForecastDatabase INSTANCE;
    //Creating a singleton instance

    public static ForecastDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
        //Checking if the database exists
            synchronized (ForecastDatabase.class) {
                if (INSTANCE == null) {
                //Checking if the database exists
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ForecastDatabase.class, "forecast_database")
                            //Specifying the Room database class and the database name
                            .fallbackToDestructiveMigration()
                            //Specifying the migration strategy callback
                            .build();
                            //Building the database
                }
            }
        }
        return INSTANCE;
    }

}
