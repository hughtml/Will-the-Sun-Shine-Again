package cm3110.a1600644coursework.willthesunshineagain.utils;

import java.util.ArrayList;

/**
 * Class to represent a number of converters for data before it is displayed on the GUI
 */
public class Converter {

    /**
     * Method to capitalise all first letters within a weather description
     * @param origDesc The original description
     * @return The formatted weather description
     */
    public static String weatherDesc(String origDesc) {
        String desc = "";
        //Declaring a String to hold the converted description
        ArrayList<String> fullDesc = new ArrayList<>();
        //Creating an ArrayList to store each character in the String
        for (int i = 0; i < origDesc.length(); i++) {
            fullDesc.add(String.valueOf(origDesc.charAt(i)));
            //Adding every character to the ArrayList
        }
        fullDesc.add(0, fullDesc.get(0).toUpperCase());
        //Converting the first character to uppercase
        fullDesc.remove(1);
        //Removing the lowercase equivalent
        for (int i = 1; i < fullDesc.size() - 1; i++) {
            if (fullDesc.get(i - 1).equals(" ")) {
                fullDesc.add(i, fullDesc.get(i).toUpperCase());
                fullDesc.remove(i + 1);
            }
        }
        //Converting the rest of the characters after a space to uppercase
        //Also removing the lowercase equivalent
        for (int i = 0; i < fullDesc.size(); i++) {
            desc += fullDesc.get(i);
        }
        //Adding the formatted String back to the variable
        return desc;
        //Returning the converted description
    }

    /**
     * Method to convert a wind direction from degrees to North/East etc
     * @param dirDegrees The direction in degrees
     * @return The converted direction
     */
    public static String windDirection(double dirDegrees) {
        String direction = null;
        //Declaring a String to hold the converted direction
        if ((dirDegrees >= 0.0) && (dirDegrees <= 11.24) || (dirDegrees >= 348.75) && (dirDegrees <= 360.0)) {
            direction = "North";
        } else if ((dirDegrees >= 11.25) && (dirDegrees <= 33.74)) {
            direction = "North North East";
        } else if ((dirDegrees >= 33.75) && (dirDegrees <= 56.24)) {
            direction = "North East";
        } else if ((dirDegrees >= 56.25) && (dirDegrees <= 78.74)) {
            direction = "East North East";
        } else if ((dirDegrees >= 78.75) && (dirDegrees <= 101.24)) {
            direction = "East";
        } else if ((dirDegrees >= 101.25) && (dirDegrees <= 123.74)) {
            direction = "East South East";
        } else if ((dirDegrees >= 123.75) && (dirDegrees <= 146.24)) {
            direction = "South East";
        } else if ((dirDegrees >= 146.25) && (dirDegrees <= 168.74)) {
            direction = "South South East";
        } else if ((dirDegrees >= 168.75) && (dirDegrees <= 191.24)) {
            direction = "South";
        } else if ((dirDegrees >= 191.25) && (dirDegrees <= 213.74)) {
            direction = "South South West";
        } else if ((dirDegrees >= 213.75) && (dirDegrees <= 236.24)) {
            direction = "South West";
        } else if ((dirDegrees >= 236.25) && (dirDegrees <= 258.74)) {
            direction = "West South West";
        } else if ((dirDegrees >= 258.75) && (dirDegrees <= 281.24)) {
            direction = "West";
        } else if ((dirDegrees >= 281.25) && (dirDegrees <= 303.74)) {
            direction = "West North West";
        } else if ((dirDegrees >= 303.75) && (dirDegrees <= 326.24)) {
            direction = "North East";
        } else if ((dirDegrees >= 326.25) && (dirDegrees <= 348.74)) {
            direction = "North North West";
        } else {
            //Do nothing
        }
        return direction;
        //Returning the converted direction
    }

    /**
     * Variable to convert the numeric speed of the wind to a String based on the Beaufort scale
     * @param origSpeed The original speed in m/s
     * @return The String describing the speed of the weather
     */
    public static String windSpeed(double origSpeed) {
        String speed = null;
        //Declaring a String to store the speed once converted
        if (origSpeed < 0.5) {
            speed = "Calm";
        } else if ((origSpeed >= 0.5) && (origSpeed <= 1.5)) {
            speed = "Light Air";
        } else if ((origSpeed >= 1.6) && (origSpeed <= 3.3)) {
            speed = "Light Breeze";
        } else if ((origSpeed >= 3.4) && (origSpeed <= 5.5)) {
            speed = "Gentle Breeze";
        } else if ((origSpeed >= 5.5) && (origSpeed <= 7.9)) {
            speed = "Moderate Breeze";
        } else if ((origSpeed >= 8.0) && (origSpeed <= 10.7)) {
            speed = "Fresh Breeze";
        } else if ((origSpeed >= 10.8) && (origSpeed <= 13.8)) {
            speed = "Strong Breeze";
        } else if ((origSpeed >= 13.9) && (origSpeed <= 17.1)) {
            speed = "High Wind/Moderate Gale";
        } else if ((origSpeed >= 17.2) && (origSpeed <= 20.7)) {
            speed = "Gale";
        } else if ((origSpeed >= 20.8) && (origSpeed <= 24.4)) {
            speed = "Strong/Severe Gale";
        } else if ((origSpeed >= 24.5) && (origSpeed <= 28.4)) {
            speed = "Stormy";
        } else if ((origSpeed >= 28.5) && (origSpeed <= 32.6)) {
            speed = "Violent Storm";
        } else {
            speed = "Hurricane Force";
        }
        return speed;
        //Returning the converted speed
    }

    /**
     * Method to extract the time from a date and time stamp
     * @param origTime The whole date and time stamp
     * @return The extracted time in the format HH:MM
     */
    public static String time(String origTime) {
        String time = null;
        //Declaring a String to hold the converted time
        time = origTime.substring(11,16);
        //Converting the time to just be in the HH:MM format/removing :SS
        return time;
        //Returning the converted String
    }

    /**
     * Method to format the date
     * @param origDate The whole date in the form DDMMYYYY
     * @return The extracted date in the form e.g. Monday 1st October
     */
    public static String date(String origDate) {
        String date = null;
        //Declaring a String to hold the converted date
        String day = origDate.substring(0, 2);
        String month = origDate.substring(2, 4);
        String year = origDate.substring(4);
        //Declaring Strings to hold sections of the date
        String dayOfWeek = findDayOfWeek(day, month, year);
        //Finding the day of the week the current date is occurring on
        date = dayOfWeek + " " + Integer.parseInt(day);
        //Beginning concatenation of String
        switch (Integer.parseInt(day)) {
            case 1:
            case 21:
            case 31:
                date += "st ";
                break;
            case 2:
            case 22:
                date += "nd ";
                break;
            case 3:
            case 23:
                date += "rd ";
                break;
            default:
                date += "th ";
                break;
        }
        switch (Integer.parseInt(month)) {
            case 1:
                date += "January";
                break;
            case 2:
                date += "February";
                break;
            case 3:
                date += "March";
                break;
            case 4:
                date += "April";
                break;
            case 5:
                date += "May";
                break;
            case 6:
                date += "June";
                break;
            case 7:
                date += "July";
                break;
            case 8:
                date += "August";
                break;
            case 9:
                date += "September";
                break;
            case 10:
                date += "October";
                break;
            case 11:
                date += "November";
                break;
            case 12:
                date += "December";
                break;
            default:
                break;
        }
        return date;
        //Returning the converted date
    }

    /**
     * Method to format a date
     * @param dateParts The whole date separated into parts
     * @return The date in the form DDMMYYYY
     */
    public static String date(ArrayList<String> dateParts) {
        String date = "";
        //Declaring a String to hold the converted date
        for (int i = 0; i < dateParts.size(); i++) {
            date += dateParts.get(i);
        }
        //Formatting the String
        return date;
        //Returning the converted date
    }

    /**
     * Method to find the parts of a date from the format YYYY-MM-DD
     * @param origDate The original date
     * @return An ArrayList with all the parts of the date
     */
    public static ArrayList<String> dateParts (String origDate) {
        ArrayList<String> dateParts = new ArrayList<>();
        //Declaring a String ArrayList to hold the converted date parts
        String day = origDate.substring(8, 10);
        String month = origDate.substring(5, 7);
        String year = origDate.substring(0, 4);
        //Declaring Strings to hold sections of the date
        dateParts.add(day);
        dateParts.add(month);
        dateParts.add(year);
        //Returning the converted date
        return dateParts;
    }

    /**
     * Method to find the day of the week a particular date occurs on
     * @param day The day of the month
     * @param month The month of the year
     * @param year The year
     * @return
     */
    public static String findDayOfWeek(String day, String month, String year) {
        String dayOfWeek = null;
        //Declaring a String to hold the day of the week
        int yearLastDigs = Integer.parseInt(year.substring(2));
        int keyValueMethod = yearLastDigs / 4;
        keyValueMethod = keyValueMethod + Integer.parseInt(day);
        int monthKeyVal = 0;
        //Declaring a variable to store the month's key value
        switch (Integer.parseInt(month)) {
            case 1:
            case 10:
                monthKeyVal = 1;
                break;
            case 2:
            case 3:
            case 11:
                monthKeyVal = 4;
                break;
            case 4:
            case 7:
                monthKeyVal = 0;
                break;
            case 5:
                monthKeyVal = 2;
                break;
            case 6:
                monthKeyVal = 5;
                break;
            case 8:
                monthKeyVal = 3;
                break;
            case 9:
            case 12:
                monthKeyVal = 6;
                break;
            default:
                break;
        }
        //Finding out the month's key value
        keyValueMethod = keyValueMethod + monthKeyVal;
        if (((Integer.parseInt(year) % 4 == 0) && (Integer.parseInt(year) % 100 != 0)) || (Integer.parseInt(year) % 400 == 0)) {
            //If the year is a leap year
            if (Integer.parseInt(month) < 3) {
                //And if the month is January or February
                keyValueMethod = keyValueMethod--;
                //1 is subtracted from the value just calculated
            }
        }
        keyValueMethod = keyValueMethod + 6;
        //The century will always be in the 2000s, so the code of 6 is used
        keyValueMethod = keyValueMethod + yearLastDigs;
        keyValueMethod = keyValueMethod % 7;
        //Finding the remainder when the value is divided by 7
        switch (keyValueMethod) {
            case 0:
                dayOfWeek = "Saturday";
                break;
            case 1:
                dayOfWeek = "Sunday";
                break;
            case 2:
                dayOfWeek = "Monday";
                break;
            case 3:
                dayOfWeek = "Tuesday";
                break;
            case 4:
                dayOfWeek = "Wednesday";
                break;
            case 5:
                dayOfWeek = "Thursday";
                break;
            case 6:
                dayOfWeek = "Friday";
                break;
            default:
                break;
        }
        return dayOfWeek;
        //Returning the day of the week when it's found
    }

    /**
     * Method to find the day of the week a particular date occurs on
     * @param date The whole date in the format DDMMYYYY
     * @return
     */
    public static String findDayOfWeek(String date) {
        String dayOfWeek = null;
        //Declaring a String to hold the day of the week
        String day = date.substring(0,2);
        String month = date.substring(2,4);
        String year = date.substring(4);
        int yearLastDigs = Integer.parseInt(year.substring(2));
        int keyValueMethod = yearLastDigs / 4;
        keyValueMethod = keyValueMethod + Integer.parseInt(day);
        int monthKeyVal = 0;
        //Declaring a variable to store the month's key value
        switch (Integer.parseInt(month)) {
            case 1:
            case 10:
                monthKeyVal = 1;
                break;
            case 2:
            case 3:
            case 11:
                monthKeyVal = 4;
                break;
            case 4:
            case 7:
                monthKeyVal = 0;
                break;
            case 5:
                monthKeyVal = 2;
                break;
            case 6:
                monthKeyVal = 5;
                break;
            case 8:
                monthKeyVal = 3;
                break;
            case 9:
            case 12:
                monthKeyVal = 6;
                break;
            default:
                break;
        }
        //Finding out the month's key value
        keyValueMethod = keyValueMethod + monthKeyVal;
        if (((Integer.parseInt(year) % 4 == 0) && (Integer.parseInt(year) % 100 != 0)) || (Integer.parseInt(year) % 400 == 0)) {
            //If the year is a leap year
            if (Integer.parseInt(month) < 3) {
                //And if the month is January or February
                keyValueMethod--;
                //1 is subtracted from the value just calculated
            }
        }
        keyValueMethod = keyValueMethod + 6;
        //The century will always be in the 2000s, so the code of 6 is used
        keyValueMethod = keyValueMethod + yearLastDigs;
        keyValueMethod = keyValueMethod % 7;
        //Finding the remainder when the value is divided by 7
        switch (keyValueMethod) {
            case 0:
                dayOfWeek = "Saturday";
                break;
            case 1:
                dayOfWeek = "Sunday";
                break;
            case 2:
                dayOfWeek = "Monday";
                break;
            case 3:
                dayOfWeek = "Tuesday";
                break;
            case 4:
                dayOfWeek = "Wednesday";
                break;
            case 5:
                dayOfWeek = "Thursday";
                break;
            case 6:
                dayOfWeek = "Friday";
                break;
            default:
                break;
        }
        return dayOfWeek;
        //Returning the day of the week when it's found
    }

}
