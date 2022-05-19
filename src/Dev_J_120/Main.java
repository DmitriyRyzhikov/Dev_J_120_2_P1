package Dev_J_120;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        try {
            
            PropertyService ps1 = new PropertyService("D:\\prop.txt");
            ps1.setProperty("size array", "not full", "array is broken");
            ps1.printProperties();             
            FileService.writeFile(ps1, "D:\\nnn.txt");
            
            System.out.println("");
            
            PropertyService ps = new PropertyService();
            ps.setProperty("summ", "340");
            ps.setProperty("book", "A dog's heart", "Author Bulgakov");
            ps.printProperties();
            System.out.println("");
            FileService.writeFile(ps); 
            FileService.writeFile(ps, "D:\\Property.txt");

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
 

    }
        
    }

