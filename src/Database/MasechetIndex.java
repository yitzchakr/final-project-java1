package Database;

import java.util.ArrayList;
import java.util.List;

public class MasechetIndex {
   private String name;
    List<DafIndex> dapim =new ArrayList<>();
    List<PerekIndex>perakim= new ArrayList<>();

    public MasechetIndex(String name) {
        this.name = name;
    }
    public MasechetIndex(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getDapim() {
        return dapim;
    }

    public void setDapim(List dapim) {
        this.dapim = dapim;
    }
}
