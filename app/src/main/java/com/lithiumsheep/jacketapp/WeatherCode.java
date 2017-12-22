package com.lithiumsheep.jacketapp;

public class WeatherCode {

    // Based on Groups defined at https://openweathermap.org/weather-conditions
    enum Group {
        THUNDERSTORM,
        DRIZZLE,
        RAIN,
        SNOW,
        ATMOSPHERE,
        CLEAR,
        CLOUDS,
        EXTREME,
        ADDITIONAL
    }

    private int id;
    private String description;
    private Group weatherGroup;

    public WeatherCode(int id, String description, Group weatherGroup) {
        this.id = id;
        this.description = description;
        this.weatherGroup = weatherGroup;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Group getWeatherGroup() {
        return weatherGroup;
    }

}
