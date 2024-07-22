package Database;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Manager {
    static List<MasechetIndex> masechtot = new ArrayList<>();
    static int MISHNA_COUNTER = 0;
    static private boolean containsMishna( String line){
        if (line.contains("מתני' ")  )return true;
        if (line.contains(" גמ'") ) return true;
        return false;
    }

    static private boolean isValidText(String line) {
        if (line.isEmpty())return false;
        if (line.startsWith("מסכת ")) return false;
        if ((line.startsWith("פרק ") && line.length() < 40)) return false;
        if (line.startsWith("דף ") && line.length() <15) return false;
        return true;
    }
    static  int searchForMasechet (String name){
        int s=0;
        int e = masechtot.size()-1;
        while (s<=e){
            int middle = (e+s)/2;
            if (masechtot.get(middle).getName().equals(name)){
                return middle;
            }
            if (masechtot.get(middle).getName().compareTo(name)<0){
                s= middle+1;
            }else e= middle-1;
        }
        return -1;
    }
    static DafIndex searchForDaf (int index, String number){
        if (index<0){
            System.out.println("masechet not found");
            return null;
        }
        int s = 0;
        int e = masechtot.get(index).dapim.size()-1;
        while (s<=e){
            int middle= (s+e)/2;
            if (masechtot.get(index).dapim.get(middle).getNumber().equals(number)){
                return masechtot.get(index).dapim.get(middle);
            }
            if (masechtot.get(index).dapim.get(middle).getNumber().compareTo(number)<0){
                s = middle +1;
            }else e = middle -1;

        }
        return null;
    }
    static void printMishnayotOfPerek (int masechetIdx, int perekIdx) throws FileNotFoundException {
        boolean startPerek = false;

        for (int i = 0; i < masechtot.get(masechetIdx).perakim.get(perekIdx).dafIndices.size(); i++){
            if (masechtot.get(masechetIdx).perakim.get(perekIdx).dafIndices.get(i).isHasMishna() == false){
                continue;
            }
            String path = masechtot.get(masechetIdx).perakim.get(perekIdx).dafIndices.get(i).getPath();
            File file = new File(path);
           Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                String line = reader.nextLine();
                if (line.startsWith("פרק")&&line.length()<45){
                    startPerek = !startPerek;
                }
                if (startPerek) {
                    printMishnayot(line);
                }
            }reader.close();
        }

    }

    static  public void  receiveInfoAndPrintAmud(String masechet, String dafAmud ) throws IOException {
        int index = searchForMasechet(masechet);
        DafIndex daf = searchForDaf(index,dafAmud);
        if (index==-1) return;
        if (!Cache.isInMemory(index,dafAmud)) {
            if (daf==null){
                System.out.println("daf not found");
            }else {
                Cache.placeInMemory(daf, index);
            }
        }
    }
    static void printPerek (int masechetIdx , int perekIdx) throws IOException {
        StringBuilder text = new StringBuilder();
        String currentPerek = "";
        String nextPerek = "*&^%";
        String perek;
        for (int i = 0; i < masechtot.get(masechetIdx).perakim.get(perekIdx).dafIndices.size(); i++) {
            DafIndex daf = masechtot.get(masechetIdx).perakim.get(perekIdx).dafIndices.get(i);
            text.append(daf.getContent()).append("\n");

        }
        currentPerek = masechtot.get(masechetIdx).perakim.get(perekIdx).getName();
        if (perekIdx < masechtot.get(masechetIdx).perakim.size()-1){
            nextPerek =masechtot.get(masechetIdx).perakim.get(perekIdx+1).getName();
        }
        int start = text.indexOf(currentPerek);
        int end = text.indexOf(nextPerek);
        if (end!= -1){
          perek = text.substring(start,end);
        }else  perek = text.substring(start);
        System.out.println(perek);
    }
    static public void receiveInfoFromUserAndPrintPerek() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter masechet");
        String masechet = scanner.nextLine();
        int index  = searchForMasechet(masechet);
        if (index < 0) {
            System.out.println("masechet not found");
            return;
        }
        System.out.println(masechtot.get(index).getName());
        for (int i = 0; i < masechtot.get(index).perakim.size(); i++) {
            System.out.println(masechtot.get(index).perakim.get(i).getName()+" . "+(i+1));
        }
        System.out.println("enter number of perek that you want to print");
        int perekIdx = scanner.nextInt();
        if (perekIdx<1 || perekIdx > masechtot.get(index).perakim.size()){
            System.out.println("perek not found");
            return;
        }
        printPerek(index,perekIdx-1);

    }
    static void printMishnayot(String text){
        String mishna = "מתני' ";
        String gemara = " גמ'" ;
        String part;
        while (text.contains(mishna) ||text.contains(gemara) ){
            int stMishna = text.indexOf(mishna);
            int stGmra = text.indexOf(gemara);
            if (stMishna<stGmra &&text.contains(mishna)){
               part = text.substring(stMishna,stGmra) ;
                System.out.println("\n");
                System.out.println( "משנה" + " "+ (char)( 'א' + MISHNA_COUNTER++));
                System.out.println(part);
               text = text.substring(stGmra + gemara.length());
            }else if (!text.contains(gemara)){
                part = text.substring(stMishna);
                System.out.println();
                System.out.println( "משנה" + " "+ (char)( 'א' + MISHNA_COUNTER++));
                System.out.println(part );
                break;
            }else  {
                part = text.substring(0,stGmra);
                System.out.print(part);
                text = text.substring(stGmra + gemara.length());

            }
        }
    }
    static public void receiveInfoAndPrintMishnayotOfPerek() throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter masechet");
        String masechet = scanner.nextLine();
        int index  = searchForMasechet(masechet);
        if (index < 0) {
            System.out.println("masechet not found");
            return;
        }
        System.out.println(masechtot.get(index).getName());
        for (int i = 0; i < masechtot.get(index).perakim.size(); i++) {
            System.out.println(masechtot.get(index).perakim.get(i).getName()+" . "+(i+1));
        }
        System.out.println("enter number of perek that you want to print");
        int perekIdx = scanner.nextInt();
        if (perekIdx<1 || perekIdx > masechtot.get(index).perakim.size()){
            System.out.println("perek not found");
            return;
        }
        printMishnayotOfPerek(index,perekIdx-1);
    }

    static public void createDatabase() throws FileNotFoundException {

        Scanner reader = new Scanner(new File("./src/talmud.txt"));
        PerekIndex perek = null;
        DafIndex daf = null;
        String currentDaf="";
        String currentMasechet="";
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            if (line != null) {
                if (line.startsWith("מסכת ") ) {
                    currentMasechet = line.substring(5);
                    MasechetIndex mas = new MasechetIndex(currentMasechet);
                    masechtot.add(mas);
                }
                if (line.startsWith("פרק ") &&line.length()<40){
                    perek = new PerekIndex(line);
                    masechtot.getLast().perakim.add(perek);

                }
                if (line.startsWith("דף") && line.length() < 15) {
                    currentDaf = line.substring(3);
                    daf = new DafIndex(currentDaf);
                    daf.addToPath(currentMasechet +"/"+currentDaf+".txt");
                    masechtot.getLast().dapim.add(daf);
                    perek.dafIndices.add(daf);
                }

                if (isValidText(line)&&perek.dafIndices.isEmpty()){
                    perek.dafIndices.add(daf);
                }
                if (containsMishna(line)){
                    masechtot.getLast().dapim.getLast().setHasMishna(true);
                }

            }
        }reader.close();
        masechtot.sort(Comparator.comparing(MasechetIndex ::getName));
        for (MasechetIndex masechetIndex : masechtot) {
            masechetIndex.dapim.sort(Comparator.comparing(DafIndex::getNumber));
        }
        for (MasechetIndex masechetIndex : masechtot) {
            MasechetIndex mas = new MasechetIndex(masechetIndex.getName());
            Cache.masechtas.add(mas);

        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        createDatabase();
        printMishnayotOfPerek(2,3);
 //    printPerek(0,1);
//        printMishnayot("דלית בה שיעורא כדתנן חרס כדי ליתן בין פצים לחבירו כלי חרס חזי ליה דמיטנף חזי לאומנא דמנקב ספר תורה חזי למקרא בבלוי והא בעי גניזה שם תהא גניזתה (ואמר) רב בכל עושין מחיצה חוץ ממלח ורבב ושמואל אמר אפילו מלח אמר רב פפא ולא פליגי הא במלח סדומית הא במלח איסתרוקנית והשתא דאמר רבה עושה אדם שני צבורי מלח ומניח עליהם קורה שהמלח מעמדת את הקורה והקורה מעמדת את המלח אפי' מלח איסתרוקנית ולא פליגי הא דאיכא קורה הא דליכא קורה: מרחיקין את הריחים ג' מן השכב שהן ארבעה מן הרכב וכו': מאי טעמא משום טיריא והא תניא ושל חמור שלשה מן האיסטרוביל שהן ארבעה מן הקלת התם מאי טיריא איכא אלא משום קלא: ואת התנור שלשה מן הכליא שהן ארבעה מן השפה: אמר אביי שמע מינה כליא דתנור טפח נפקא מינה למקח וממכר: מתני' לא יעמיד אדם תנור בתוך הבית אלא אם כן יש על גביו גובה ארבע אמות היה מעמידו בעלייה צריך שיהא תחתיו מעזיבה שלשה טפחים ובכירה טפח ואם הזיק משלם מה שהזיק ר' שמעון אומר לא אמרו כל השיעורין האלו אלא שאם הזיק פטור מלשלם לא יפתח אדם חנות של נחתומין ושל צבעין תחת אוצרו של חבירו ולא רפת בקר באמת ביין התירו אבל לא רפת בקר: גמ' והתניא בתנור ארבעה ובכירה שלשה אמר אביי כי תניא ההיא בדנחתומין דתנור דידן כי כירה דנחתומין: לא יפתח חנות וכו': תנא אם היתה רפת קודמת לאוצר מותר בעי אביי כיבד וריבץ לאוצר מהו ריבה בחלונות מהו אכסדרה תחת האוצר מהו בנה עלייה על גבי ביתו מהו תיקו בעי רב הונא בריה דרב יהושע תמרי ורמוני מאי תיקו: באמת ביין התירו וכו'. תנא . ביין התירו מפני שמשביחו ולא רפת בקר מפני שמסריחו אמר רב יוסף האי דידן אפילו קוטרא דשרגא נמי קשיא ליה א''ר ששת ואספסתא כרפת בקר דמיא: מתני' חנות שבחצר יכול למחות בידו ולומר לו איני יכול לישן מקול הנכנסין ומקול היוצאין אבל עושה כלים יוצא ומוכר בתוך השוק ואינו יכול למחות בידו ולומר לו איני יכול לישן לא מקול הפטיש ולא מקול הריחים ולא מקול התינוקות: גמ' מ''ש רישא ומ''ש סיפא אמר אביי סיפא אתאן לחצר אחרת א''ל רבא אי הכי ליתני חצר אחרת מותר אלא אמר רבא\n");
//        System.out.println((masechtot.get(0).getName()));
//        for (int i = 0; i < masechtot.get(0).dapim.size(); i++) {
//           System.out.println(masechtot.get(0).dapim.get(i).getNumber()+ " : " +masechtot.get(0).dapim.get(i).isHasMishna());
//
//        }
//        System.out.println(searchForMasechet("בבא קמא"));

      //  String daf =(searchForDaf(3,"יח - א"));
//        searchAndPrint("בבא בתרא","יח - ב");
//        System.out.println(masechtot.get(4).getName());
//        for (int i = 0; i < masechtot.get(4).perakim.size() ; i++) {
//            System.out.println(masechtot.get(4).perakim.get(i).dafIndices.getLast().getNumber());
//        }
       // printPerek(1,1);
    //    receiveInfoFromUserAndPrintPerek();
    }

}
