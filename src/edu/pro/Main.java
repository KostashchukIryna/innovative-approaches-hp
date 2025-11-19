package edu.pro;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final Charset ENCODING = StandardCharsets.ISO_8859_1;

    /**
     * optimized method to clean and tokenize a line.
     * This avoids slow Regular Expressions by processing characters directly,
     * which is the key time optimization.
     * * @param line The string line to process.
     * @return A List of cleaned, lowercase words found in the line.
     */
    private static List<String> tokenizeLine(String line) {
        List<String> words = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (Character.isLetter(c)) {
                currentWord.append(Character.toLowerCase(c));
            }
            else {
                if (currentWord.length() > 0) {
                    words.add(currentWord.toString());
                    currentWord.setLength(0);
                }
            }
        }

        // Add the last word if it exists after the loop finishes
        if (currentWord.length() > 0) {
            words.add(currentWord.toString());
        }

        return words;
    }

    public static void main(String[] args) throws IOException {

        LocalDateTime start = LocalDateTime.now();

        try (Stream<String> lines = Files.lines(Paths.get("src/edu/pro/txt/harry.txt"), ENCODING)) {

            Map<String, Long> wordCounts = lines
                .flatMap(line -> tokenizeLine(line).stream())
                .collect(Collectors.groupingBy(
                    word -> word,
                    Collectors.counting()
                ));

            System.out.println("--- Top 30 Most Frequent Words ---");
            wordCounts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(30)
                .forEach(entry ->
                    System.out.printf(Locale.ROOT, "%-15s %d%n", entry.getKey(), entry.getValue())
                );

        } catch (IOException e) {
            System.err.println("Error reading file. Ensure harry.txt exists and has correct encoding.");
            throw e;
        }

        LocalDateTime finish = LocalDateTime.now();

        System.out.println("------");
        System.out.println("Execution Time (milliseconds): " + ChronoUnit.MILLIS.between(start, finish));

    }
}