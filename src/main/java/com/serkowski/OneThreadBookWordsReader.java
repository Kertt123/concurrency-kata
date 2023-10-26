package com.serkowski;


import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class OneThreadBookWordsReader {

    private static final String BOOKS_PATH = "src/main/resources/books";

    public static void main(String[] args) throws IOException {
        var readStart = System.currentTimeMillis();
        Set<String> lines = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(BOOKS_PATH))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    System.out.println("Read file: " + path);
                    lines.addAll(Files.readAllLines(path));
                }
            }
        }
        var words = splitIntoWords(lines);
        System.out.println("Read time: " + (System.currentTimeMillis() - readStart) + "ms" + " words: " + words.size());
        Map<String, Integer> countWordsMap = new HashMap<>();
        words.forEach(word ->
            countWordsMap.compute(word.toLowerCase(), (key, val) -> (val == null)
                    ? 1
                    : val + 1)
        );
        System.out.println("Whole time: " + (System.currentTimeMillis() - readStart) + "ms");
    }

    private static List<String> splitIntoWords(Set<String> lines) {
        return lines.stream()
                .map(o -> o.split("\\W+"))
                .flatMap(Arrays::stream)
                .toList();
    }
}