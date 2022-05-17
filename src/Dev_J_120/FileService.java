
package Dev_J_120;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

    class FileService {

    /*
    Метод, читающий строки из файла и разбирающий его на properties и comments;  
    */   
    public static List<String> readFileAndConvert(Path path) throws IOException{
        path = Objects.requireNonNull(path, "The file can't be null.");
        if(!Files.isReadable(path))
            throw new IOException("The file does not exist or reading from the file is prohibited");
        List<String> list = Files.readAllLines(path);  
        list.removeIf(x ->!(x.contains("=") || x.contains("#")));
        list.replaceAll(String::trim); 
        return list;
    }
    /*
    Метод, записывающий строки в файл;  
    */ 
    public static void writeFile(Path path, PropertyService ps){
        
    }
    public static Map<String, String[]> converter(List<String> list){
        Map<String, String[]> map = new HashMap<>();
        String[] listArray = (String[]) list.toArray();
        for(int i = 0; i < listArray.length; i++) {
            if(!(listArray[i].charAt(0) == '#')) {
               String[] temp = listArray[i].split("=");
               String key = temp[0];
               temp[0] = null;
               map.put(key, temp);
            }
            else {
               String comment = listArray[i];
               String[] temp = listArray[i + 1].split("=");
               String key = temp[0];
               temp[0] = comment;
               map.put(key, temp);
               i++;
            }    
        }
     return map;
    }
 
}
