package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.*;

public class TimeUtility {

    private static ObservableList<LocalTime> startEndTime = FXCollections.observableArrayList();

    public static ObservableList<LocalTime> getStartEndTime() {
        if(startEndTime.size() == 0){
            ZonedDateTime estStart = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));
            LocalDateTime localStart = estStart.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime localEnd = localStart.plusHours(14).plusMinutes(30);//localStart.plusHours(15);


            while(localStart.isBefore(localEnd)){
                startEndTime.add(localStart.toLocalTime());
                localStart = localStart.plusMinutes(30);

            }
        }

        return startEndTime;

    }
}
