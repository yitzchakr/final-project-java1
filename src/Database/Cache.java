package Database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cache {
   static List<MasechetIndex> masechtas = new ArrayList<>();

   static boolean isInMemory (int index, String dafAmud) {


       for (int i = 0; i < masechtas.get(index).dapim.size(); i++) {
           if (masechtas.get(index).dapim.get(i).getNumber().equals(dafAmud)) {
               System.out.println(masechtas.get(index).dapim.get(i).getText());
               DafIndex daf;
               daf = masechtas.get(index).dapim.get(i);
               masechtas.get(index).dapim.remove(i);
               masechtas.get(index).dapim.addFirst(daf);

               for (int j = 0; j <masechtas.get(index).dapim.size() ; j++) {
                   System.out.print(" דף "+masechtas.get(index).dapim.get(j).getNumber()+" ");
               }
               System.out.println( " cache: ");

            return true;
           }
       }
       return false;
   }
   static void placeInMemory(DafIndex daf, int masIdx) throws IOException {
       String content = daf.getContent();
       DafIndex cacheDaf =  new DafIndex(daf.getNumber());
       cacheDaf.setText(content);
       masechtas.get(masIdx).dapim.addFirst(cacheDaf);
       if ( masechtas.get(masIdx).dapim.size()>5){
           masechtas.get(masIdx).dapim.removeLast();
       }
       System.out.println(content);
       System.out.println("\n"+ " cache: ");
       for (int i = 0; i <masechtas.get(masIdx).dapim.size() ; i++) {
           System.out.print("  דף "+masechtas.get(masIdx).dapim.get(i).getNumber()+" ");
       }
   }
}

