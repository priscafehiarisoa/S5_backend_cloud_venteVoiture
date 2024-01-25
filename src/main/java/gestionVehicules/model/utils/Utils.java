package gestionVehicules.model.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    public static LocalDateTime convertToDateTime(String timeString) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime DL;
        try{
            DL= LocalDateTime.parse(timeString, formatter);
        }catch (Exception e){
            DL= LocalDateTime.parse(timeString+":00", formatter);
        }
        return DL;
    }
}
