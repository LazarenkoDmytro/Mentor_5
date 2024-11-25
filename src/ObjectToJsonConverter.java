import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ObjectToJsonConverter {
    public static String toJson(Object obj) throws IllegalAccessException {
        if (isSerializable(obj)) {
            return getJsonString(obj);
        }

        throw new RuntimeException(obj.getClass().getName() + " is not serializable");
    }

    private static boolean isSerializable(Object obj) {
        return !Objects.isNull(obj) && obj.getClass().isAnnotationPresent(JsonSerializable.class);
    }

    private static String getJsonString(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        Map<String, String> fieldMap = new HashMap<>();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(obj);
                fieldMap.put(field.getName(), value == null ? "null" : String.valueOf(value));
            }
            clazz = clazz.getSuperclass();
        }

        return "{" + fieldMap.entrySet()
                .stream()
                .map(entry -> "\"" + entry.getKey() + "\":\""
                        + entry.getValue() + "\"")
                .collect(Collectors.joining(",")) + "}";
    }

    private ObjectToJsonConverter() {
    }
}
