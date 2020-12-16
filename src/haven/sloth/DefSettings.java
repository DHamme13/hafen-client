package haven.sloth;

import com.google.common.flogger.FluentLogger;

public class DefSettings {
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    public static final Settings global = new Settings("config.ini");

    public static void init() {

    }
}
