package cm3110.a1600644coursework.willthesunshineagain.utils;

/**
 * Class to represent an Object of information about the weather in a particular location
 */
public class ForecastLocation {

    //---------- FIELDS ----------

    String name; //The location name
    String country; //The country the location is in
    String description; //Description of the current weather
    int weatherId; //ID code describing the weather
    String time; //Time of the forecast
    int temp; //The current temperature (in Kelvin)
    int lowTemp; //The lowest possible temperature that day
    int highTemp; //The highest possible temperature that day
    int humidity; //The humidity level
    double rainfall; //The amount of rainfall
    double windSpeed; //The wind speed
    double windDirection; //The direction of the wind

    //---------- CONSTRUCTORS ----------

    /**
     * Parametric constructor
     * @param name The location name
     * @param country The country the location is in
     * @param description Description of the current weather
     * @param time The time of the forecast
     * @param temp The current temperature (in Kelvin)
     * @param lowTemp The lowest possible temperature that day
     * @param highTemp The highest possible temperate that day
     * @param humidity The humidity level
     * @param rainfall The amount of rainfall
     * @param windSpeed The wind speed
     * @param windDirection The wind direction
     */
    public ForecastLocation(String name, String country, String description, int weatherId, String time, int temp, int lowTemp, int highTemp, int humidity, double rainfall, double windSpeed, double windDirection) {
        this.name = name;
        this.country = country;
        this.description = description;
        this.weatherId = weatherId;
        this.time = time;
        this.temp = temp;
        this.lowTemp = lowTemp;
        this.highTemp = highTemp;
        this.humidity = humidity;
        this.rainfall = rainfall;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }


    //---------- GETTERS ----------

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public int getTemp() {
        return temp;
    }

    public int getLowTemp() {
        return lowTemp;
    }

    public int getHighTemp() {
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

    public int getWeatherId() {
        return weatherId;
    }

    //---------- SETTERS ----------


    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public void setLowTemp(int lowTemp) {
        this.lowTemp = lowTemp;
    }

    public void setHighTemp(int highTemp) {
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

    public void setWeatherId(int weatherId) {
        this.weatherId = weatherId;
    }

}