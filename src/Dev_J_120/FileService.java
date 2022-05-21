
package Dev_J_120;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

    class FileService {
/*    Метод, читающий строки из файла и выполняющий анализ и первичное 
    преобразование строк;  */
    public static List<String> readFileAndConvert(Path path) throws IOException, NullPointerException, AccessDeniedException{
        path = Objects.requireNonNull(path, "The file can't be null.");
        List<String> list = Files.readAllLines(path);
        list = killerBOM(list);
        list = checkCharE(list);
        list.removeIf(x ->!(x.contains("=") || x.contains("#")));
        list.replaceAll(String::trim);
    return list;    
    }
/*    Метод умеет разбирать строки, полученные из файла, трансформирует нужную
    строку в тип Map<String, String[]>, который принимает класс, хранящий 
    эти Properties;  */
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
  //  Методы, записывающие Properties в файл; 
    public static void writeFile(PropertyService ps) throws IOException, NullPointerException, AccessDeniedException{
        ps = Objects.requireNonNull(ps, "The Properties can't be null.");
        Path path = ps.getPathToPropertiesFile();
        path = Objects.requireNonNull(path, "The path to destination file can't be null.");
        writeListtoFile(path, ps);
    }
/*   Перегруженный метод, записывающий Properties в новый файл; В качестве параметра - 
    объект типа File. */
    public static void writeFile(PropertyService ps, File file) throws IOException, NullPointerException, AccessDeniedException{
        file = Objects.requireNonNull(file, "The file can't be null.");
        ps = Objects.requireNonNull(ps, "The Properties can't be null.");
        Path path = file.toPath();
        writeListtoFile(path, ps);
    }
/*    Перегруженный метод, записывающий Properties в новый файл; В качестве параметра - 
    строка с путем к файлу и его именем. Здесь могут быть проблемы из-за некорректного
    указания fileName. Считаем, что fileName указанна корректно, если объект типа File 
    или Path создается без выброса NullPointerException. */
    public static void writeFile(PropertyService ps, String fileName) throws IOException, NullPointerException, AccessDeniedException{
        ps = Objects.requireNonNull(ps, "The Properties can't be null.");
        if(fileName.trim().isEmpty())
           throw new IllegalArgumentException();
        Path path = Paths.get(fileName);
        writeListtoFile(path, ps);       
    }
//Вспомогательные методы:    
    
    //Метод написан, чтобы избежать дублирования кода
    public static void writeListtoFile(Path path, PropertyService ps) throws IOException, NullPointerException{
        if(!path.isAbsolute())
            path = path.toAbsolutePath();
        Path dir = path.getParent();
        if(!Files.isDirectory(dir))
           Files.createDirectories(dir);
        if(!Files.exists(path))
            Files.createFile(path);
        List<String> list = new ArrayList<>();
        list.add(LocalDateTime.now().toString()); 
        ps.getPropSet().forEach((x, y) -> {
           if(y[0] != null)
              list.add(y[0] + "\n" + x + "=" + y[1]);
           else
              list.add(x + "=" + y[1]);
        });
        Files.write(path, list);
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
}
