import Database.Manager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        var features = new Features();

        Manager.createDatabase();
        features.runMenu();
    }
}