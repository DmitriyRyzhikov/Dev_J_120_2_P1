package Dev_J_120;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        
        try {
            MyProperties myProperties = new MyProperties("prop.txt");
            myProperties.setPropertiesValue("p1", "true");
            myProperties.setPropertiesValue("p2", "2019");
            myProperties.setPropertiesValue("p3", "false");
            myProperties.setPropertiesValue("p4", "on");
            
            System.out.println("Properties содержит:");
            myProperties.getAllMap().forEach((key, value) -> System.out.println(key + "=" + value));
            
            //сохраняем Properties в файл, откуда он ранее был загружен, либо в который сохранялся. 
            myProperties.savePropertyFile();
        } 
        catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
        
    }

