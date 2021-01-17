package io.project.app.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * The JSON Conversion utility
 *
 * @author armen
 */
public class FrontendGsonConverter {

    /**
     * Gets the date format
     *
     * @return Returns date format
     */
    private static String getDateFormat() {
        return "yyyy-MM-dd'T'HH:mm:ss.SSS";
    }

    /**
     * Convert the object to JSON string
     *
     * @param <T> The generic parameter
     * @param object The object to serialize
     * @return Returns serialized string
     */
    public static <T> String toJson(T object) {
        // create json string
        return new GsonBuilder().setPrettyPrinting().setDateFormat(getDateFormat()).registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).enableComplexMapKeySerialization().serializeNulls().create().toJson(object);
    }

    /**
     * Import JSON file in to the object
     *
     * @param <T> The T parameter for input
     * @param json
     * @param classType The class type
     * @return The object
     */
    public static <T> T fromJson(String json, Class<T> classType) {

        return new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json1, type, jsonDeserializationContext) -> LocalDate.parse(json1.getAsJsonPrimitive().getAsString())).registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json12, type, jsonDeserializationContext) -> LocalTime.parse(json12.getAsJsonPrimitive().getAsString())).registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json13, type, jsonDeserializationContext) -> LocalDateTime.parse(json13.getAsJsonPrimitive().getAsString()))               
                
                .enableComplexMapKeySerialization().serializeNulls().create().fromJson(json, classType);

//    }
    }

    /**
     * A fast adapter method to change types with exact same schema
     *
     * @param <From> The source type
     * @param <To> The target type
     * @param object The object
     * @param targetType The target type
     * @return
     */
    public static <From, To> To adapt(From object, Class<To> targetType) {
        return fromJson(toJson(object), targetType);
    }

    public static Gson fromWithDateTime() {
        return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
            Instant instant = Instant.parse(json.getAsString());
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }).create();
    }

//    public static Gson fromWithZoneDateTime() {
//        Gson gson = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, (JsonDeserializer<ZonedDateTime>) (json, type, jsonDeserializationContext) -> ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString())).create();
//
//        return gson;
//    }

}
