import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by Thien Tien
 * Date 11/20/2022 - 3:18 PM
 * Description: Implement features of Slang Dictionary Application
 */
public class SlangDictionary {
    /**
     * Attribute of SlangDictionary: TreeMap with key is String and value is the list of String because
     * a slang word can have many meanings
     */
    private TreeMap<String, List<String>> slangDictionary;

    /**
     * Constructor of SlangDictionary
     */
    public SlangDictionary() {
        slangDictionary = new TreeMap<>();
    }

    /**
     * Import a list of slang words and meanings to memory
     * @param fileName name of file that stores slang word and its meaning(s)
     */
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
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    /**
     * Search by Slang Word
     * @param slang slang word to look for
     * @return list of meanings of slang word or null if the slang is not found
     */
    public List<String> searchBySlangWord(String slang) {
        if (slangDictionary.containsKey(slang)) {
            return slangDictionary.get(slang);
        }
        return null;
    }



    public static void main(String[] args) {
        SlangDictionary app = new SlangDictionary();
        app.importDictionary("slang.txt");

        List<String> def = app.searchBySlangWord(">:O");
        if (def != null) {
            for (String str: def) {
                System.out.println(str);
            }
        } else {
            System.out.println("Inexisted slang word");
        }
    }

}
