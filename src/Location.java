public class Location {
    private String masechet;
    private String daf;

    public Location(String masechet, String daf) {
        this.masechet = masechet;
        this.daf = daf;
    }

    public String getMasechet() {
        return masechet;
    }

    public void setMasechet(String masechet) {
        this.masechet = masechet;
    }

    public String getDaf() {
        return daf;
    }

    public void setDaf(String daf) {
        this.daf = daf;
    }

    public String toString() {
        return masechet + "   " + daf;
    }
}
