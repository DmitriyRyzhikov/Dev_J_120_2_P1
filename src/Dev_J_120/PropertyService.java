
package Dev_J_120;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
//класс для хранения Properties и комментариев к ним, если таковые есть.
public class PropertyService {
/*
    Класс имеет два поля: 
    - Map<String, String[]> propSet для хранения Properties. Первый String - 
    имя Property(key в MAP), String[] - массив, имеющий всегда длину 2. 
    В String[0] хранятся комментарии к этому свойству. Если их нет, то в 
    String[0] - null. В String[1] хранится значение свойства(value в MAP);
    - Path pathToPropertiesFile - путь  к файлу, из которого Properties были
    загружены. Если Properties не грузились из файла, а для их создания 
    использовался конструктор по умолчанию, в поле Path pathToPropertiesFile
    заносится "user.home". При сохранении в файл, имеется выбор:
    либо создать новый файл в "user.home" или любом другом месте, либо 
    перезаписать исходный файл. Такая структура полей однозначно связывает
    конкретное Property и комментарий к нему(если он есть), позволяет хранить 
    и редактировать не только, собственно сами Properties, но и комментарии к
    ним и места расположения файлов. 
    */    
    private Map<String, String[]> propSet;
    private Path pathToPropertiesFile;

 /*
   Конструктор по умолчанию, создающий пустой набор свойств.
 */   
    public PropertyService() {        
       propSet = new HashMap<>();
       pathToPropertiesFile = Paths.get(System.getProperty("user.home")+ "\\newProperties.txt");
    }
 /*   
   Конструктор, загружающий свойства из заданного файла; файл задан
   либо экземпляром класса java.io.File;     
 */
    public PropertyService(File file) throws IOException {
       file = Objects.requireNonNull(file, "The file can't be null.");
       Path path = file.toPath();
       FileService.readFileAndConvert(path);
       List<String> list = FileService.readFileAndConvert(path);
       Map<String, String[]> map = FileService.converterToMap(list); 
       pathToPropertiesFile = path;
       propSet = map;               
    }
 /*   
   Конструктор, загружающий свойства из заданного файла;  
   файл задан либо строкой с именем;     
 */
    public PropertyService(String fileName) throws IOException {
       if(fileName == null || fileName.isEmpty())
          throw new IllegalArgumentException("The file name can't be null or empty.");
       Path path = Paths.get(fileName); 
       List<String> list = FileService.readFileAndConvert(path);
       Map<String, String[]> map = FileService.converterToMap(list); 
       pathToPropertiesFile = path;
       propSet = map;           
    }
    //геттер для Path getPathToPropertiesFile().
    public Path getPathToPropertiesFile() {
        return pathToPropertiesFile;
    }  
 /* 
    Метод, возвращающий значение свойства с заданными именем; 
    если свойства с таким именем нет, то метод должен возвращать null;
 */ 
    public String getPropertyValue(String propertyName){
        String res;
       try { 
            String[] temp = propSet.get(propertyName);
            res = temp[1]; }
       catch (NullPointerException nue) {
            res = null; }
       return res;
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
        if(hasProperty(key)){
            String[] temp = propSet.get(key);
            temp[1] = value;
            propSet.put(key, temp); }
        else {
            String[] values = new String[2];
            values[1] = value;
            propSet.put(key, values);
        }
    }
    /*
    Перегруженный setProperty(String key, String value), добавилась 
    возможность редактировать комментарий
    */
    public void setProperty(String key, String value, String comment){
        if(hasProperty(key)) {
            String[] temp = propSet.get(key);
            temp[0] = "#" + comment;
            temp[1] = value;
        if(!(temp[0].length() > 1))
            temp[0] = null;
           propSet.put(key, temp); }
        else {   
            String[] values = new String[2];
            values[0] = "#" + comment;
            values[1] = value;
        if(!(values[0].length() > 1))
            values[0] = null;    
           propSet.put(key, values); }
       }
  //  Метод для печати Properties;  
    
    public void printProperties(){
        propSet.forEach((t, u) -> {
            if(u[0] == null)
               System.out.println(t + "=" + u[1]);
            else {
            System.out.println(u[0]);
            System.out.println(t + "=" + u[1]); }
        });
    }
 /*
    Метод для печати pathToPropertiesFile;  
 */   
    public void printPropertiesPath(){
        System.out.println(pathToPropertiesFile.toString());
    }
}