package com.travelocity.utilities;

import com.travelocity.utilities.dto.FlightDuration;

import java.time.Duration;

public class TimeHelper {

    /**
     *
     * @param flightDuration hours and minutes
     * @param flightDurationCompare hours and minutes
     * @return 0 if equal, >0 if flightDuration  > flightDurationCompare, <0 if flightDuration < flightDurationCompare
     */
    public static int compareFlightsDurations(FlightDuration flightDuration, FlightDuration flightDurationCompare) {
        Duration durationOne = Duration.ofMillis(Duration.ofHours(flightDuration.getHours()).toMillis())
                .plus(Duration.ofMillis(Duration.ofMinutes(flightDuration.getMinutes()).toMillis()));
        Duration durationTwo = Duration.ofMillis(Duration.ofHours(flightDurationCompare.getHours()).toMillis())
                .plus(Duration.ofMillis(Duration.ofMinutes(flightDurationCompare.getMinutes()).toMillis()));

        return durationOne.compareTo(durationTwo);
    }

}
