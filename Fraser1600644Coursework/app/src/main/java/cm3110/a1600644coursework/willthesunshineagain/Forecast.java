package cm3110.a1600644coursework.willthesunshineagain;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;
import java.util.*;

/**
 * Class to represent an individual forecast
 * @author Hugh Fraser 1600644
 * @version 1.0
 */
@Entity(tableName = "forecast")
public class Forecast {

    //----------FIELDS----------

    @NonNull
    @PrimaryKey(autoGenerate = true)
    int forecastid;
    @ColumnInfo(name="City")
    private String city;
    @ColumnInfo(name="Country")
    private String country;
    @ColumnInfo(name="Weather Description")
    private String description;
    @ColumnInfo(name="Weather ID")
    private int weatherId;
    @ColumnInfo(name="Time")
    private String time;
    @ColumnInfo(name="Date")
    private String date;
    @ColumnInfo(name="Temp")
    private double temp;
    @ColumnInfo(name="LowTemp")
    private double lowTemp;
    @ColumnInfo(name="HighTemp")
    private double highTemp;
    @ColumnInfo(name="Humidity")
    private int humidity;
    @ColumnInfo(name="Rainfall")
    private double rainfall;
    @ColumnInfo(name="WindSpeed")
    private double windSpeed;
    @ColumnInfo(name="WindDirection")
    private double windDirection;

    //----------GETTERS----------

    @NonNull
    public int getForecastid() {
        return forecastid;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public int getWeatherId() {
        return weatherId;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public double getTemp() {
        return temp;
    }

    public double getLowTemp() {
        return lowTemp;
    }

    public double getHighTemp() {
        return highTemp;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getRainfall() {
        return rainfall;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }


    //----------SETTERS----------


    public void setForecastid(@NonNull int forecastid) {
        this.forecastid = forecastid;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setLowTemp(double lowTemp) {
        this.lowTemp = lowTemp;
    }

    public void setHighTemp(double highTemp) {
        this.highTemp = highTemp;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setRainfall(double rainfall) {
        this.rainfall = rainfall;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }
}
