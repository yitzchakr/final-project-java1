import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Search {
    static int LOCATION_COUNTER = 0;
     private boolean isValidText(String line) {
        if (line.isEmpty())return false;
        if (line.startsWith("מסכת ")) return false;
        if ((line.startsWith("פרק ") && line.length() < 40)) return false;
        if (line.startsWith("דף ") && line.length() <15) return false;
        return true;
    }
    private boolean containsWord (String line,String text){
       if (line.contains(" " +text+" ")||line.startsWith(text+" ")||line.endsWith(" "+ text)) return true;
       return false;
    }


    public void printDafAmud(String masechet, String dafAmud) throws FileNotFoundException {
        boolean foundMasechet = false;
        boolean foundDafAmud = false;
        Scanner reader = new Scanner(new File("./src/talmud.txt"));
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (!foundDafAmud) {
                if (!foundMasechet && line.startsWith(masechet)) {
                    foundMasechet = true;
                } else if (foundMasechet) {
                    if (line.startsWith("מסכת ") && !line.startsWith(masechet)) {
                        break;
                    } else if (line.equals(dafAmud)) {
                        foundDafAmud = true;
                    }
                }

            } else if (line.startsWith("דף") && line.length() < 15 && !line.equals(dafAmud)) {
                break;
            } else System.out.println(line);
        }
        reader.close();
        if (!foundMasechet) {
            System.out.println(" Masechet not found");
        } else if (!foundDafAmud) {
            System.out.println(" Daf Not found");
        }

    }

    public List<Location> findLocationsInFile(String text) throws FileNotFoundException {
        List<Location> locations = new ArrayList<>();
        String currentDaf = "";
        String currentMasechet = "";
        Scanner reader = new Scanner(new File("./src/talmud.txt"));
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line.startsWith("מסכת ") && line.length() < 30) {
                currentMasechet = line;
            }
            if (line.startsWith("דף") && line.length() < 15) {
                currentDaf = line;
            }
            if (isValidText(line) && containsWord(line,text)) {
                LOCATION_COUNTER++;
                Location loc = new Location(currentMasechet, currentDaf);
                locations.add(loc);
            }

        }
        reader.close();
        return locations;
    }

      public static void main(String[] args) throws IOException {
        Search test = new Search();
//        Scanner scanner= new Scanner(System.in);
//        String input = scanner.nextLine();
        List<Location> list=test.findLocationsInFile("גמ'");

        if (list.isEmpty()){
            System.out.println(" words not found in text");
        }
        for (Object o : list) {
            System.out.println(o);
        }
//        System.out.println(LOCATION_COUNTER);


//       test.printDafAmud("מסכת עירובין","דף ב - א");

    }

}
