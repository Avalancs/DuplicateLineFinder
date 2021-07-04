import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Program to print out duplicate lines from a file,
 * where the difference between the duplicates is
 * lower- and uppercase letters.
 *
 * This is useful if you have a list of
 * file names on linux that you want to
 * put in a zip file, but worry that
 * you will not be able to unzip on windows.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        String fileName;
        if(args.length == 1) {
            fileName = args[0];
        } else {
            fileName = "lines.txt";
        }


        getDuplicates(Paths.get(fileName));
    }

    public static void getDuplicates(Path file) throws IOException {
        Map<String, Integer> lowerCaseMap = new HashMap<>();

        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        lines.sort(String::compareToIgnoreCase);

        lines.forEach(l -> {
            String lowercase = l.toLowerCase();
            if(lowerCaseMap.containsKey(lowercase)) {
                lowerCaseMap.put(lowercase, lowerCaseMap.get(lowercase) + 1);
            } else {
                lowerCaseMap.put(lowercase, 1);
            }
        });

        List<String> duplicates = lines.stream().filter(l -> lowerCaseMap.get(l.toLowerCase())>1)
            .sorted(String::compareToIgnoreCase)
            .collect(Collectors.toList());
        duplicates.forEach(System.out::println);
    }
}
