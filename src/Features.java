import Database.Manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Features {
    private String masechet = "";
    private String dafAmud = "";
    Scanner scanner = new Scanner(System.in);

    public void receiveAndCovert() {
        System.out.println("enter מסכת");
        scanner.nextLine();
        String input = scanner.nextLine();
        masechet = "מסכת " + input;
        System.out.println("enter דף");
        String daf = scanner.next();
        System.out.println("enter עמוד");
        String amud = scanner.next();
        dafAmud = "דף " + daf + " - " + amud;
    }

    void convertForDatabase() {
        System.out.println("enter מסכת");
        scanner.nextLine();
        String input = scanner.nextLine();
        masechet = input;
        System.out.println("enter דף");
        String daf = scanner.next();
        System.out.println("enter עמוד");
        String amud = scanner.next();
        dafAmud = daf + " - " + amud;
    }

    public void runMenu() throws IOException {
        while (true) {
            Search search = new Search();
            System.out.println();
            System.out.println("enter choice:\n1.print Amud\n2.search for words\n3.create library of talmud on your computer\n4.print amud from library" +
                    "\n5.print dafim of perek\n6.print mishnayot of perek\n7.exit ");
            String choice = scanner.next();
            switch (choice) {
                case "1":
                    receiveAndCovert();

                    try {
                        search.printDafAmud(masechet, dafAmud);
                    } catch (FileNotFoundException e) {
                        System.out.println("file not found");
                    }
                    break;
                case "2":
                    scanner.nextLine();
                    System.out.println(" enter search");
                    String input = scanner.nextLine();
                    List<Location> list;
                    try {
                        list = search.findLocationsInFile(input);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    if (list.isEmpty()) {
                        System.out.println(" words not found in text");
                    }
                    for (Object o : list) {
                        System.out.println(o);
                    }
                    System.out.println("frequency:" + Search.LOCATION_COUNTER);
                    break;
                case "3":
                    File file = new File("./pages");
                    if (!file.exists()) {
                        FileHandling.createFilesForAmudim();
                    } else
                        System.out.println(" files already exist , to reinstall please delete folder  C:\\Users\\Owner\\IdeaProjects\\SearchTalmudBavli\\pages");
                    break;
                case "4":
                    convertForDatabase();
                    Manager.receiveInfoAndPrintAmud(masechet, dafAmud);
                    break;
                case "5":
                    Manager.receiveInfoFromUserAndPrintPerek();
                    break;
                case "6":
                    Manager.receiveInfoAndPrintMishnayotOfPerek();
                    break;
                case "7":
                    return;
                default:
                    System.out.println(" not valid entry");
                    runMenu();
                    return;
            }
        }
    }
}
