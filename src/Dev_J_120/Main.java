package Dev_J_120;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

    public static void main(String[] args) {
        try {
            /*
            PropertyService ps = new PropertyService();
            ps.setProperty("попа0", "жопа");
            ps.setProperty("попа1", "большая жопа");
            ps.setProperty("попа2", "очень большая жопа");
            ps.setProperty("попа3", "жопная жопа");
            ps.printProperties();
            System.out.println();
            */
            PropertyService ps1 = new PropertyService("D:\\prop.txt");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
 

    }
        
    }

