package com.diff.difference;

import java.io.*;
import java.util.*;

import com.github.difflib.*;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.text.DiffRow;
//import com.github.difflib.text.DiffRowGenerator;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class DifferenceApplication {

    private static List<String> fileToLines(String filename) {
    List<String> lines = new LinkedList<String>();
    String line = "";
    try {
      BufferedReader in = new BufferedReader(new FileReader(filename));
      while ((line = in.readLine()) != null) {
        lines.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return lines;
  }
    
	public static void main(String[] args) throws DiffException {
		SpringApplication.run(DifferenceApplication.class, args);
	List<String> original =fileToLines("originalFile.txt");
    List<String> revised  = fileToLines("revisedFile.txt");

    StringBuilder sb = new StringBuilder();
    DiffRowGenerator.Builder builder = new DiffRowGenerator.Builder();
    DiffRowGenerator dfg = builder.build();
    List<DiffRow> rows = dfg.generateDiffRows(original, revised);
    for (final DiffRow diffRow : rows)
    {
      if (diffRow.getTag().equals(DiffRow.Tag.INSERT)) // or use switch* 
      {
        sb.append("<div class='insert'>\n" + diffRow.getNewLine() + "</div>");
      }
      else if (diffRow.getTag().equals(DiffRow.Tag.DELETE))
      {
        sb.append("<div class='del'>\n" + diffRow.getOldLine() + "</div>");
      }
      else if (diffRow.getTag().equals(DiffRow.Tag.CHANGE))
      {
        sb.append("<div class='mod'>\n");
        sb.append("\t<div class='mc'>" + diffRow.getOldLine() + "</div>\n");
        sb.append("\t<div class='mc'>" + diffRow.getNewLine() + "</div>\n");
        sb.append("</div>\n");
      }
    }
 
    DiffRowGenerator generator = DiffRowGenerator.create()
                .showInlineDiffs(true)
                .inlineDiffByWord(false)
                .oldTag(f -> "~")
                .newTag(f -> "**")
                .build();
List<DiffRow> rows1 = generator.generateDiffRows(original, revised
             /*  Arrays.asList("This is a test senctence.", "This is the second line.", "And here is the finish."),
                Arrays.asList("This is a test for diffutils.", "This is the second line.")*/);
        
System.out.println("|original|new|");
System.out.println("|--------|---|");
for (DiffRow row : rows1) {
    System.out.println("|" + row.getOldLine() + "|" + row.getNewLine() + "|");
}
    
    System.out.println(sb);

        }
        
        
}
