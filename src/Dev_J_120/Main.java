package Dev_J_120;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        try {
            
            PropertyService ps = new PropertyService();
            PropertyService ps1 = new PropertyService("D:\\prop.txt");
            ps1.printProperties();
            System.out.println();
            ps1.setProperty("size array", "not full", "array is broken");
            ps1.printProperties();

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
 

    }
        
    }

