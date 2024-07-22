package Database;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DafIndex {
    private  String number;
    private String path= "./pages/";
    private boolean hasMishna;
    private String text = "";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isHasMishna() {
        return hasMishna;
    }

    public void setHasMishna(boolean hasMishna) {
        this.hasMishna = hasMishna;
    }

    public DafIndex(String number) {
        this.number = number;
    }
    public DafIndex(){}
    public String toString(){

      return path;
    }

    public  String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String text) {
        this.path = text;
    }
    public void addToPath(String text){
        this.path += text;
    }
   public String getContent() throws IOException {
       File file = new File(getPath());
       FileReader reader = new FileReader(file);
       BufferedReader read = new BufferedReader(reader);

      return read.lines().collect(Collectors.joining("\n"));


   }
}
