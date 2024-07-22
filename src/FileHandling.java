import java.io.*;
import java.util.Scanner;

public class FileHandling {


    static private boolean isValidText(String line) {
        if (line.isEmpty())return false;
        if (line.startsWith("מסכת ")) return false;
        if ((line.startsWith("פרק ") && line.length() < 40)) return false;
        return true;
    }

    static public void createFilesForAmudim() throws IOException {
        String currentDaf = "";
        String currentMasechet = "";
        String currentPerek = "";
        boolean newMasechet = false;
        boolean newPerek = false;


        String basePath = "./pages";
        File baseDir = new File(basePath);
        Scanner reader = new Scanner(new File("./src/talmud.txt"));
        BufferedWriter writer = null;

        while (reader.hasNextLine()) {
            String line = reader.nextLine();

            if (line.startsWith("מסכת ") && line.length() < 30) {
                currentMasechet = line.substring(5);
                File masechetDir = new File(baseDir, currentMasechet);
                masechetDir.mkdirs();
                newMasechet = true;
            }
            if (line.startsWith("פרק ") && line.length() < 40){
                currentPerek=line;
                newPerek =true;
            }
            if (line.startsWith("דף") && line.length() < 15) {
                currentDaf = line.substring(3);
                File dafFile = new File(baseDir + "/" + currentMasechet, currentDaf + ".txt");
                dafFile.createNewFile();
                if (writer != null) {
                    writer.close();
                }
                writer = new BufferedWriter(new FileWriter(dafFile));
            }
            if (writer != null && isValidText(line)) {
                if (newMasechet) {
                    writer.write("מסכת " + currentMasechet);
                    writer.newLine();
                    newMasechet = false;
                }
                if (newPerek){
                    writer.write(currentPerek);
                    writer.newLine();
                    newPerek= false;
                }
                writer.write(line);
                writer.newLine();
            }
        }
        if (writer != null) {
            writer.close();
        }
        reader.close();
    }

    static public void printAmud(String masechet, String amud) throws FileNotFoundException {
        File basePath = new File("./pages");
        File masechetDir = new File(basePath, masechet);
        if (!masechetDir.exists()) {
            System.out.println("masechet not found");
            return;
        }
        File file = new File("./pages/" + masechet + "/" + amud + ".txt");
        if (!file.exists()) {
            System.out.println("amud not found");
            return;
        }
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            System.out.println(line);
        }


    }

    public static void main(String[] args) throws IOException {
        //  printAmud("שבת", "קא - א");
           createFilesForAmudim();
    }
}
