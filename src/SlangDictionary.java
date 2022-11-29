import java.io.*;
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
    private int numOfWords;

    /**
     * Constructor of SlangDictionary
     */
    public SlangDictionary() {
        slangDictionary = new TreeMap<>();
        slangMeaningKeyword = new HashMap<>();
        numOfWords = 0;
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
                        numOfWords++;
                    }
                }
                else {
                    meaning.add(slangWordInfo[1]);
                    String[] keyWords = slangWordInfo[1].split(" ");
                    invertedIndex(slangWord, keyWords);
                    numOfWords++;
                }
                slangDictionary.put(slangWord.trim(), meaning);
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
    public String[][] searchBySlangWord(String slang) {
        if (slangDictionary.containsKey(slang)) {
            List<String> listOfMeaning = slangDictionary.get(slang);

            String[][] res = new String[listOfMeaning.size()][3];
            for (int i = 0; i < listOfMeaning.size(); i++) {
                res[i][0] = "" + (i + 1);
                res[i][1] = slang;
                res[i][2] = listOfMeaning.get(i);
            }

            return res;
        }
        else return null;
    }

    /**
     * Search by definition
     * @param def keyword in definition of slang words to look for
     * @return the linked list associated with each node's value is a pair of [slang, meaning]
     */
    public String[][] searchByDefinition(String def) {
        Set<String> slang = slangMeaningKeyword.get(def.toLowerCase());

        if (slang != null) {
            LinkedList<String[]> res = new LinkedList<>();
            for (String s: slang) {

                List<String> meaning = slangDictionary.get(s);
                for (String m: meaning) {
                    if (m.toLowerCase().contains(def.toLowerCase())) {

                        System.out.println(m);
                        res.add(new String[]{s, m});
                    }
                }
            }

            String[][] resData = new String[res.size()][3];
            for (int i = 0; i < res.size(); i++) {
                resData[i][0] = "" + (i + 1);
                resData[i][1] = res.get(i)[0];
                resData[i][2] = res.get(i)[1];
            }

            return resData;
        }
        return null;
    }

    public String[][] convertToTableData() {
        String[][] res = new String[numOfWords][3];

        int i = 0;
        for (Map.Entry<String, List<String>> m: slangDictionary.entrySet()) {
            List<String> meanings = m.getValue();
            for (String meaning: meanings) {
                res[i][0] = "" + (i + 1);
                res[i][1] = m.getKey();
                res[i][2] = meaning;
                i++;
            }
        }

        return res;
    }

    public boolean addSlangWord(String slang, String def) {
        if (!slangDictionary.containsKey(slang.toUpperCase())) {
            ArrayList<String> meaning = new ArrayList<>();
            meaning.add(def);
            System.out.println("Size = " + meaning.size() + ", def = " + def);
            slangDictionary.put(slang.toUpperCase(), meaning);
            invertedIndex(slang.toUpperCase(), def.split(" "));
            return true;
        }

        return false;
    }

//    private void uninvertedIndex(String[] def, String slang) {
//        if (slangDictionary.containsKey(slang.toUpperCase())) {
//            for (String s: def) {
//                if (slangMeaningKeyword.containsKey(s))
//            }
//        }
//    }

    public String[] randomSlangWord() {
        Random generator = new Random();

        Set<String> keys = slangDictionary.keySet();
        List<String> keyList = new ArrayList<>(keys);

        String randomKey = keyList.get(generator.nextInt(keyList.size()));

        List<String> value = slangDictionary.get(randomKey);

        return new String[]{randomKey, value.get(generator.nextInt(value.size()))};
    }

    public void deleteSlangWord(String slang, String def) {
        if (slangDictionary.containsKey(slang)) {
            List<String> meanings = slangDictionary.get(slang);

            if (meanings.size() == 1) {
                slangDictionary.remove(slang);
            }
            else {
                meanings.removeIf(meaning -> meaning.equals(def));
                slangDictionary.replace(slang, meanings);
                numOfWords--;
            }
        }
    }

    public void overwriteSlang(String slang, String oldDef, String newDef) {
        List<String> meanings = slangDictionary.get(slang);

        for (int i = 0; i < meanings.size(); i++) {
            if (meanings.get(i).equals(oldDef)) {
                meanings.set(i, newDef);

                String[] wordInMeanings = newDef.split(" ");
                invertedIndex(slang, wordInMeanings);
            }
        }

        slangDictionary.put(slang, meanings);
    }

    public void duplicateSlang(String slang, String def) {
        List<String> meanings = slangDictionary.get(slang.toUpperCase());
        meanings.add(def);

        invertedIndex(slang, def.split(" "));

        System.out.println(slang + " - " + meanings);
        slangDictionary.put(slang, meanings);
    }

    public void editSlang(String oldSlang, String newSlang, String definition) {
        oldSlang = oldSlang.toUpperCase();
        newSlang = newSlang.toUpperCase();

        if (slangDictionary.containsKey(newSlang)) {
            List<String> meanings = slangDictionary.get(newSlang);
            meanings.add(definition);

            List<String> oldMeanings = slangDictionary.get(oldSlang);
            oldMeanings.remove(definition);

            slangDictionary.replace(newSlang, meanings);
            slangDictionary.replace(oldSlang, oldMeanings);
        }

        else {
            List<String> newMeanings = new ArrayList<>();
            newMeanings.add(definition);

            List<String> oldMeanings = slangDictionary.get(oldSlang);
            oldMeanings.remove(definition);

            slangDictionary.put(newSlang, newMeanings);
            slangDictionary.replace(oldSlang, oldMeanings);
        }
    }

    public void writeDictionary(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            writer.write("Slag`Meaning\n");

            for (Map.Entry<String, List<String>> m: slangDictionary.entrySet()) {
                writer.write(m.getKey() + "`");

                List<String> meanings = m.getValue();

                for (int i = 0; i < meanings.size(); i++) {
                    if (i != 0) {
                        writer.write("| ");
                    }
                    writer.write(meanings.get(i));
                }
                writer.write("\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearDictionary() {
        for (Map.Entry<String, List<String>> map: slangDictionary.entrySet()) {
            map.getValue().clear();
        }
        slangDictionary.clear();

        for (Map.Entry<String, Set<String>> map: slangMeaningKeyword.entrySet()) {
            map.getValue().clear();
        }
        slangMeaningKeyword.clear();
        numOfWords = 0;
    }

    public TreeMap<String, List<String>> getSlangDictionary() {
        return slangDictionary;
    }
}
