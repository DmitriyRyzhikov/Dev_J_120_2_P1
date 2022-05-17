
package Dev_J_120;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyService {
    
    private HashMap<String, String> propSet;
    private Path pathToPropertiesFile;

 /*
   Конструктор по умолчанию, создающий пустой набор свойств.
 */   
    public PropertyService() {        
       propSet = new HashMap<>();               
    }
 /*   
   Конструктор, загружающий свойства из заданного файла; файл задан
   либо экземпляром класса java.io.File;     
 */
    public PropertyService(File file) throws IOException {
       this();
       file = Objects.requireNonNull(file, "The file can't be null.");
       Path path = file.toPath();
       FileService.readFileAndConvert(path);
       pathToPropertiesFile = path;
              
    }
 /*   
   Конструктор, загружающий свойства из заданного файла;  
   файл задан либо строкой с именем;     
 */
    public PropertyService(String fileName) throws IOException {
       this();
       fileName = fileName.trim();
       if(fileName == null || fileName.isEmpty())
          throw new IllegalArgumentException("The file name can't be null or empty.");
       Path path = Paths.get(fileName); 
       List<String> list = FileService.readFileAndConvert(path);
       pathToPropertiesFile = path;
       
       //list.forEach(System.out::println);           
    }   
    
 /* 
    Метод, возвращающий значение свойства с заданными именем; 
    если свойства с таким именем нет, то метод должен возвращать null;
 */ 
    public String getPropertyValue(String propertyName){
        return propSet.get(propertyName);
    } 
 /*
   Метод, удаляющий свойство с заданными именем; если свойства с 
   таким именем нет, то метод ничего не делает;  
  */    
    public void removeProperty(String propertyName){
       propSet.remove(propertyName); 
    } 
 /*
    Метод, проверяющий наличие свойства с заданными именем;
 */   
    public boolean hasProperty(String propertyName){
       return propSet.containsKey(propertyName);
    }
 /*
    Метод, устанавливающий значение свойства с заданными именем; если свойство 
   с таким именем есть, то метод задаёт свойству новое значение;  
 */
    public void setProperty(String key, String value){
       propSet.put(key, value);
    }
 /*
    Метод для печати Properties;  
 */   
    public void printProperties(){
        propSet.forEach((t, u) -> System.out.println(t + " = " + u));
    }
 /*
    Метод для печати pathToPropertiesFile;  
 */   
    public void printPropertiesPath(){
        System.out.println(pathToPropertiesFile.toString());
    }
}
