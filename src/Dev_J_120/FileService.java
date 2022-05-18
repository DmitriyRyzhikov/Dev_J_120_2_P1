
package Dev_J_120;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

    class FileService {

    /*
    Метод, читающий строки из файла и выполняющий анализ и первичное 
    преобразование строк;  
    */   
    public static List<String> readFileAndConvert(Path path) throws IOException{
        path = Objects.requireNonNull(path, "The file can't be null.");
        if(!Files.isReadable(path))
            throw new IOException("The file does not exist or reading from the file is prohibited");
        List<String> list = Files.readAllLines(path);
        list = killerBOM(list);
        list = checkCharE(list);
        list.removeIf(x ->!(x.contains("=") || x.contains("#")));
        list.replaceAll(String::trim);
    return list;    
    }
    /*
    Метод продолжает преобразование строк, полученных из файла, трансформирует
    исходную строку в тип Map<String, String[]>, который принимает класс, хранящий 
    эти Properties;  
    */
    public static Map<String, String[]> converterToMap(List<String> list){
        Map<String, String[]> map = new HashMap<>();
        Object[] listArray = list.toArray();
        for(int i = 0; i < listArray.length; i++) {
            if(!(((String)listArray[i]).charAt(0) == '#')) {
               String[] temp = ((String)listArray[i]).split("=");
               String key = temp[0].trim();
               temp[0] = null;
               map.put(key, temp);
            }
            else {
               String comment = (String)listArray[i];
               String[] temp = ((String)listArray[i + 1]).split("=");
               String key = temp[0].trim();
               temp[0] = comment;
               map.put(key, temp);
               i++;
            }    
        }
     return map;
    }
/*
    Метод, удаляющий метку порядка байтов, если она есть в начале txt файла
    и сохранилась после его чтения в первую строку List.
*/  
    public static List<String> killerBOM(List<String> list){
        StringBuilder sb = new StringBuilder();
        for (char character : list.get(0).toCharArray()) { 
            if (character != '\uFEFF') 
                sb.append(character); 
        }  
        list.remove(0);
        list.add(0, sb.toString());
    return list;
    }
/*
    Метод, проверяющий и удаляющий строки, в которых количество 
    символов "=" больше 1, если они не комментарии. Из-за неопределенности,
    такие строки при загрузке в Properties игнорируются.
*/    
    public static List<String> checkCharE(List<String> list){
        for(int i = 0; i<list.size(); i++) {
            int count = 0;
            for(char character : list.get(i).toCharArray()) {
                if(list.get(i).toCharArray()[0] == '#')
                    break;
                if(character == '=')
                    count++;  }
        if(count > 1)
           list.set(i, "");
        }
    return list;
    }
    /*
    Метод, записывающий строки в файл;  
    */ 
    public static void writeFile(Path path, PropertyService ps){
        
    }
}
