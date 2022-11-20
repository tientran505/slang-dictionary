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

    /**
     * Search by definition
     * @param def keyword in definition of slang words to look for
     * @return the linked list associated with each node's value is a pair of [slang, meaning]
     */
    public LinkedList<String[]> searchByDefinition(String def) {
        LinkedList<String[]> slang = new LinkedList<>();

        for (Map.Entry<String, List<String>> entries: slangDictionary.entrySet()) {
            List<String> meanings = entries.getValue();
            for (String meaning: meanings) {
                if (meaning.toLowerCase().contains(def.toLowerCase())) {
                    slang.add(new String[]{entries.getKey(), meaning});
                }
            }
        }

        return slang;
    }

    public void addSlangWord(String slang, String def) {
        if (!slangDictionary.containsKey(slang)) {
            ArrayList<String> meaning = new ArrayList<>();
            meaning.add(def);
            slangDictionary.put(slang, meaning);
        }
    }

    public TreeMap<String, List<String>> getSlangDictionary() {
        return slangDictionary;
    }

    public static void main(String[] args) {
        SlangDictionary app = new SlangDictionary();
        app.importDictionary("slang.txt");

        LinkedList<String[]> slangs = app.searchByDefinition("heAR");

        System.out.println(slangs.size());

        for (String[] slang: slangs) {
            System.out.println(slang[0] + ": " + slang[1]);
        }
    }

}
