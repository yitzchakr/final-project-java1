import Database.DafIndex;

import java.io.File;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        File file = new File("./pages");
        System.out.println(file.exists());

    }
}
