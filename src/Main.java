import Database.Manager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        var features = new Features();

        Manager.createDatabase();
        features.runMenu();
    }
}