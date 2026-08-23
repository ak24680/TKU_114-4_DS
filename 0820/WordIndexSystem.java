import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WordIndexSystem {
    public static void main(String[] args) {
        String[] sentences = {
            "Java is a programming language.",
            "Java is object-oriented and powerful.",
            "Data structures in Java are very important, yes, very important."
        };

        Map<String, Integer> wordCounts = new HashMap<>();
        Set<String> uniqueWords = new HashSet<>();

        for (String sentence : sentences) {
            String clean = sentence.replaceAll("[,.]", "").toLowerCase();
            String[] words = clean.split("\\s+");

            for (String word : words) {
                if (word.isBlank()) continue;
                uniqueWords.add(word);
                wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
            }
        }

        System.out.println("所有不重複單字數量: " + uniqueWords.size());
        System.out.println("所有單字與出現次數: " + wordCounts);

        System.out.println("\n出現至少 2 次的單字：");
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            if (entry.getValue() >= 2) {
                System.out.println("  - " + entry.getKey() + ": " + entry.getValue() + " 次");
            }
        }
    }
}