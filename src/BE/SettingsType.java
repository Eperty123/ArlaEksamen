package BE;

import java.util.HashMap;
import java.util.Map;

public enum SettingsType {
    MESSAGE_CHECK_FREQUENCY(0), // 0 - Message check update frequency (in seconds)
    CARD_OPEN_DURATION(1), // 1 - Employee card show duration (in seconds)
    TIME_FORMAT(2), // 2 - Time format for date/time
    WRONG_PASS_FREEZE_DURATION(3); // 3 - Wrong password freeze duration (in seconds)

    private int value;
    private static Map map = new HashMap<>();

    SettingsType(int value) {
        this.value = value;
    }

    static {
        for (SettingsType settingsType : SettingsType.values()) {
            map.put(settingsType.value, settingsType);
        }
    }

    public static SettingsType valueOf(int settingsType) {
        return (SettingsType) map.get(settingsType);
    }

    public int getValue() {
        return value;
    }
}
