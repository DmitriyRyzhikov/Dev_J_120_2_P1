
package Dev_J_120;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    Метод, записывающий Properties в файл; Properties пишутся либо в тот же
    файл, откуда загружались, либо - вновь созданные Properties пишутся по
    умолчанию в "user.home". Если "user.home" не подходит, следует выбрать
    перегруженную версию этого метода с возможностью указать место назначения
    для фала Properties. Первой строкой файла будет дата и время создания. 
    Строка не содержит символов "=" и "#" и при очередной загрузке Properties 
    из файла будет проигнорирована.  
    */ 
    public static void writeFile(PropertyService ps) throws IOException{
        ps = Objects.requireNonNull(ps, "The Properties can't be null.");
        Path path = ps.getPathToPropertiesFile();
        path = Objects.requireNonNull(path, "The path to destination file can't be null.");
        writeListtoFile(path.toFile(), ps);
    }
    /*
    Перегруженный метод, записывающий Properties в файл; В качестве параметра - 
    объект типа File.
    */ 
    public static void writeFile(PropertyService ps, File file) throws IOException{
        ps = Objects.requireNonNull(ps, "The Properties can't be null.");
        file = Objects.requireNonNull(file, "The file can't be null.");
        writeListtoFile(file, ps);
    }
    /*
    Перегруженный метод, записывающий Properties в файл; В качестве параметра - 
    строка с путем к файлу и его именем.
    */ 
    public static void writeFile(PropertyService ps, String fileName) throws IOException{
        ps = Objects.requireNonNull(ps, "The Properties can't be null.");
        if(fileName == null || fileName.isEmpty())
          throw new IllegalArgumentException("The file name can't be null or empty.");
        File file = new File(fileName);
        writeListtoFile(file, ps);
    }
    
    //Метод написан, чтобы избежать дублирования кода
    public static void writeListtoFile(File file, PropertyService ps) throws IOException{
        List<String> list = new ArrayList<>();
        list.add(LocalDateTime.now().toString() + "\n"); 
        ps.getPropSet().forEach((x, y) -> {
           if(y[0] != null)
              list.add(y[0] + "\n" + x + "=" + y[1] + "\n");
           else
              list.add(x + "=" + y[1] + "\n");
        });       
        if(!file.exists())
  /* в 148 строке проблема, которую я не смог понять и победить. Когда для записи файла мы задаем путь к нему
     и имя, происходит следующее. Если новый файл будет лежать в существующей директории, то все будет ок. 
     Файл будет создан и запись в него произойдет. Но, если помимо файла, придется создавать еще новую директорию, 
     то выбросится исключение FileNotFoundException: D:\123\nnn.txt (Отказано в доступе). При этом новая директория
     и новый файл физически создаются, но с атрибутом "Только для чтения." В связи с этим запись в файл произойти 
     не может. Пробовал делать тоже самое через класс Files - код короче, результат тот же.  */                     
           file.mkdirs();
          file.createNewFile();
        if(!file.canWrite())
            throw new FileNotFoundException("Указанный файл либо не существует, либо запись в него запрещена.");
        try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(file))) {
             list.forEach(x -> {
                 try {
                     bw1.write(x);
                 } catch (IOException ex) {
                     Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }); 
        }
        catch (IOException ex) {
           Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
