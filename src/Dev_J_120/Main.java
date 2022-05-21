/*  
          При создании нового объекта PropertyService, следует учитывать следующее:
        Конструктор по умолчанию используется в том случае, если создается абсолютно новый
        набор свойств. Заполняется он посредством метода ps.setProperty. При желании, в этом
        методе сразу можно задавать комментарии к свойствам. Сохранить можно куда угодно. По
        умолчанию сохраняется в "user.home" с именем файла newProperties.txt. 
          При создании объекта PropertyService, использующего существующий файл свойств, 
        путь к нему следует указать в параметре конструктора. Если путь указывается в виде
        относительного пути, файл должен находиться в соответствующей папке проекта. При
        последующем сохранении, свойства по умолчанию сохраняется в файл, из которого были
        загружены. Можно указать новое место назначения и новое имя файла. Если место назначения 
        доступно, новый файл будет создан и в него сохранены свойства. 
         Первой строкой файла, в качестве справочной информации, будет дата и время создания файла. 
        Строка не содержит символов "=" и "#" и при очередной загрузке Properties из файла 
        будет проигнорирована.  
      */  
package Dev_J_120;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;

public class Main {

    public static void main(String[] args) {              
        try {           
            PropertyService ps = new PropertyService("prop.txt");
            ps.printProperties(); 
            
//при записи можно указывать путь к новому файлу как в абсолютном так и в относительном виде.
            ps.writeToFile("prop1.txt");
            
                                  
 // здесь попытался поймать все, что выбрасывалось при тестировании.
        } catch (IOException | NullPointerException | IllegalArgumentException ex) {
            System.out.println("\n--------The application is stopped-------\n");
            if(ex instanceof IllegalArgumentException) {
               System.out.println("Syntax error in file name, folder name, or volume label.");
               System.out.println(ex.getMessage()); }
            else if(ex instanceof NullPointerException)  {             
               System.out.println("It is not possible to create a new file at the specified path."
                   + "File path or file name is incorrect.");
                System.out.println(ex.getMessage()); }
            else if(ex instanceof FileNotFoundException) {
                System.out.println("Syntax error in file name, folder name, or volume label.");
                System.out.println(ex.getMessage()); }
            else if(ex instanceof AccessDeniedException) {
                System.out.println("Writing or rading to/from the file is prohibited.");
                System.out.println(ex.getMessage()); }
            else if(ex instanceof NoSuchFileException) {
                System.out.println("An attempt to access a non-existent file, "
                        + "or the specified path does not exist or/and its creation is impossible.");
                System.out.println(ex.getMessage()); }
            else if(ex instanceof InvalidPathException) {
                System.out.println("The file name can't be null or empty. Syntax error in file name, folder name, or volume label.");
                System.out.println(ex.getMessage()); }
            else {
                System.out.println("An error has occurred. See the information below: " + ex.getClass());
                System.out.println(ex.getMessage());}                       
            }            
        }
    }


