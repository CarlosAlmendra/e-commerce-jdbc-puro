package org.unicesumar.util;

import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabaseInitializer {
    public static String sqlFilePath = "src/main/resources/init.sql";

    public static void initialize() throws Exception {
        String sql = new String(Files.readAllBytes(Paths.get(sqlFilePath)));
        DatabaseConnection.initDatabase(sql);
    }
}
