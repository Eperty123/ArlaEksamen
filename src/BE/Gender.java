package BE;

import java.util.HashMap;
import java.util.Map;

public enum Gender {
    Female(0),
    Male(1);

    private final int value;
    private static final Map map = new HashMap<>();

    Gender(int value) {
        this.value = value;
    }

    static {
        for (Gender gender : Gender.values()) {
            map.put(gender.value, gender);
        }
    }

    public static Gender valueOf(int gender) {
        return (Gender) map.get(gender);
    }

    public int getValue() {
        return this.value;
    }
}
