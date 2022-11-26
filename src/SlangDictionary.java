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
    private HashMap<String, Set<String>> slangMeaningKeyword;

    /**
     * Constructor of SlangDictionary
     */
    public SlangDictionary() {
        slangDictionary = new TreeMap<>();
        slangMeaningKeyword = new HashMap<>();
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
                        s = s.trim();
                        meaning.add(s);
                        String[] keyWords = s.split(" ");
                        invertedIndex(slangWord, keyWords);
                    }
                }
                else {
                    meaning.add(slangWordInfo[1]);
                    String[] keyWords = slangWordInfo[1].split(" ");
                    invertedIndex(slangWord, keyWords);
                }
                slangDictionary.put(slangWord, meaning);
            }

            reader.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    /**
     * Inverted index for <a href="https://viblo.asia/p/fulltext-search-don-gian-ma-huu-ich-DXOGRjbPGdZ">Full
     * Text Search algorithm</a>
     * @param slangWord
     * @param keyWords - meaning of slang word
     */
    private void invertedIndex(String slangWord, String[] keyWords) {
        for (String k: keyWords) {
            k = k.toLowerCase();
            Set<String> slang = slangMeaningKeyword.get(k);
            if (slang == null) {
                slang = new HashSet<>();
            }
            slang.add(slangWord);

            slangMeaningKeyword.put(k, slang);
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
        LinkedList<String[]> res = new LinkedList<>();
        Set<String> slang = slangMeaningKeyword.get(def.toLowerCase());

        if (slang != null) {
            for (String s: slang) {

                List<String> meaning = slangDictionary.get(s);
                for (String m: meaning) {
                    if (m.toLowerCase().contains(def.toLowerCase())) {

                        System.out.println(m);
                        res.add(new String[]{s, m});
                    }
                }
            }

            return res;
        }
        return null;
    }

    public void addSlangWord(String slang, String def) {
        if (!slangDictionary.containsKey(slang)) {
            ArrayList<String> meaning = new ArrayList<>();
            meaning.add(def);
            slangDictionary.put(slang, meaning);
        }
    }

    public void printKeyword() {
        for (Map.Entry m: slangMeaningKeyword.entrySet()) {
            System.out.println(m.getKey() + ": " + m.getValue());
        }
    }

    public TreeMap<String, List<String>> getSlangDictionary() {
        return slangDictionary;
    }

    public static void main(String[] args) throws InterruptedException {
        SlangDictionary app = new SlangDictionary();
        app.importDictionary("slang.txt");

        long startTime = System.currentTimeMillis();
        LinkedList<String[]> slangs = app.searchByDefinition("friend");
        long stopTime = System.currentTimeMillis();
        long elapseTime = stopTime - startTime;

        if (slangs != null) {
            for (String[] slang: slangs) {
                System.out.println(slang[0] + ": " + slang[1]);
            }
            System.out.println("\n\nTime to execute: " + elapseTime);
        }
    }
}
