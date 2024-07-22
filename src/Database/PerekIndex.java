package Database;

import java.util.ArrayList;
import java.util.List;

public class PerekIndex {

    List <DafIndex> dafIndices = new ArrayList<>();
    private String name = "";

 public PerekIndex(){}
    public PerekIndex(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
