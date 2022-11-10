package logger;

import java.util.logging.*;

public class LoggerTest {
    public static void main(String[] args) {
        // Logger.getGlobal().setLevel(Level.OFF); // 取消所有日志
        Logger.getGlobal().info("File->Open menu item selected");
    }
}
