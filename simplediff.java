package com.sayonarakuso.simplediff.service;

import difflib.DiffRow;
import difflib.DiffRowGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiffService {

    public String getDiff(String text1, String text2) {
        // Split the input strings into lists of lines
        List<String> original = List.of(text1.split("\\r?\\n"));
        List<String> revised = List.of(text2.split("\\r?\\n"));

        // Create the diff generator
        DiffRowGenerator generator = DiffRowGenerator.create()
                .showInlineDiffs(true)
                .inlineDiffByWord(true)
                .oldTag(f -> "<span style='background-color: #ffaaaa;'>") // deleted text
                .newTag(f -> "<span style='background-color: #aaffaa;'>") // added text
                .build();

        // Generate the diff rows
        List<DiffRow> rows = generator.generateDiffRows(original, revised);

        // Build the result as an HTML string
        StringBuilder result = new StringBuilder();
        for (DiffRow row : rows) {
            result.append("<p>")
                  .append("Original: ").append(row.getOldLine()).append("<br>")
                  .append("Revised: ").append(row.getNewLine())
                  .append("</p>");
        }
        return result.toString();
    }
}
