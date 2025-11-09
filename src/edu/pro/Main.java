package edu.pro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) throws IOException {

        LocalDateTime start = LocalDateTime.now();

        Map<String, Long> wordCounts;

        try (Stream<String> lines = Files.lines(Paths.get("src/edu/pro/txt/harry.txt"), StandardCharsets.ISO_8859_1)) {

            wordCounts = lines
                .flatMap(line -> {
                    String normalizedLine = line
                        .replaceAll("[^A-Za-z ]", " ")
                        .toLowerCase(Locale.ROOT);

                    return Arrays.stream(normalizedLine.split(" +"));
                })
                .filter(word -> !word.isEmpty())
                .collect(Collectors.groupingBy(
                    word -> word,
                    Collectors.counting()
                ));
        }

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