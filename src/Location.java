public class Location {
    String masechet;
    String daf ;

    public Location(String masechet, String daf) {
        this.masechet = masechet;
        this.daf = daf;
    }
    public String toString(){
        return masechet + "   " + daf;
    }
}
