import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.*;

/**
 * PACKAGE_NAME
 * Created by PC
 * Date 11/20/2022 - 3:18 PM
 * Description: ...
 */
public class SlangDictionary {
    private TreeMap<String, List<String>> slangDictionary;

    public SlangDictionary() {
        slangDictionary = new TreeMap<>();
    }

    public void importDictionary(String fileName) {
        if (!slangDictionary.isEmpty()) {
            slangDictionary.clear();
        }
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            reader.readLine();
            String line = "";

            while ((line = reader.readLine()) != null) {
                if (!line.matches(".*.+\\`.+.*")) {
                    continue;
                }

                String[] slangWordInfo = line.split("`");

                String slangWord = slangWordInfo[0];
                List<String> meaning = new ArrayList<>();

                if (slangWordInfo[1].contains("|")) {
                    String[] listOfMeaning = slangWordInfo[1].split("\\|");
                    for (String s: listOfMeaning) {
                        meaning.add(s.trim());
                    }
                }
                else {
                    meaning.add(slangWordInfo[1]);
                }

                slangDictionary.put(slangWord, meaning);
            }

            reader.close();

            for(Map.Entry m: slangDictionary.entrySet()) {
                System.out.println(m.getKey() + ": " + m.getValue());
            }

        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SlangDictionary app = new SlangDictionary();
        app.importDictionary("slang.txt");
    }

}
