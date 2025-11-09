package edu.pro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;;

public class Main {

    public static void main(String[] args) throws IOException {

        LocalDateTime start = LocalDateTime.now();

        String content = new String(Files.readAllBytes(Paths.get("src/edu/pro/txt/harry.txt")));

        content = content
            .replaceAll("[^A-Za-z ]", " ")
            .toLowerCase(Locale.ROOT);

        List<String> words = Arrays.asList(content.split(" +")).stream()
            .filter(word -> !word.isEmpty())
            .collect(Collectors.toList());

        Map<String, Long> wordCounts = words.stream()
            .collect(Collectors.groupingBy(
                word -> word,
                Collectors.counting()
            ));

        System.out.println("--- Top 30 Most Frequent Words ---");
        wordCounts.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(30)
            .forEach(entry ->
                System.out.printf("%-15s %d%n", entry.getKey(), entry.getValue())
            );

        LocalDateTime finish = LocalDateTime.now();

        System.out.println("------");
        System.out.println(ChronoUnit.MILLIS.between(start, finish));

    }
}
