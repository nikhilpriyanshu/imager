package helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyHelper {
    private static Logger logger = LoggerFactory.getLogger(PropertyHelper.class);
    private static String QUOTE = "\"";
    private static String COMMA = ",";
    private static int SPACES_IN_TAB = 2;

    public static JSONArray readFileProperties(List<String> files) {
        List<String> lines = readFilePropertiesRaw(files);
        List<String> jsonFormattedLines = getFilePropertiesAsJson(lines);
        String jsonLine = getCompactJSONString(jsonFormattedLines);
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = (JSONArray) jsonParser.parse(jsonLine);
            return jsonArray;
        } catch (ParseException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private static String getCompactJSONString(List<String> jsonFormattedLines) {
        String jsonLine = "";
        for (String line : jsonFormattedLines) {
            jsonLine = jsonLine + line.strip();
        }
        return jsonLine;
    }

    private static List<String> getFilePropertiesAsJson(List<String> lines) {
        List<String> newLines = new ArrayList<String>();
        if (lines.isEmpty()) {
            return newLines; 
        }
        newLines.add("[");
        int lastLeadingSpaceCount = 0;
        String newLine = "";
        newLines.add("{");
        for (String line : lines) {
            int leadingSpaceCount = getLeadingSpaceCount(line);
            if (leadingSpaceCount < lastLeadingSpaceCount) {
                if (!newLine.isEmpty()) {
                    newLine = newLine.substring(0, newLine.lastIndexOf(','));
                    newLines.add(newLine);
                }
                if (leadingSpaceCount != 0) {
                    while (lastLeadingSpaceCount != leadingSpaceCount + SPACES_IN_TAB) {
                        String blockEndString = getSpaceString(lastLeadingSpaceCount - SPACES_IN_TAB) + "}";
                        newLines.add(blockEndString);
                        lastLeadingSpaceCount = lastLeadingSpaceCount - SPACES_IN_TAB;
                    }
                    String blockEndString = getSpaceString(lastLeadingSpaceCount - SPACES_IN_TAB) + "}" + COMMA;
                    newLines.add(blockEndString);
                } else {
                    String blockEndString = getSpaceString(leadingSpaceCount) + "}";
                    newLines.add(blockEndString + COMMA);
                    newLines.add("{");
                }
            } else {
                if (!newLine.isEmpty()) {
                    newLines.add(newLine);
                }
            }
            line = line.strip();
            String key = getKey(line);
            String value = getValue(line);
            newLine = getJsonCompitableString(key, value, leadingSpaceCount);
            lastLeadingSpaceCount = leadingSpaceCount;
        }
        if (!newLines.isEmpty()) {
            int lastLineIndex = newLines.size() - 1;
            String lastLine = newLines.get(lastLineIndex);
            newLines.remove(lastLineIndex);
            newLines.add(lastLine.substring(0, lastLine.lastIndexOf(',')));
            newLines.add("}");
        }
        newLines.add("]");
        return newLines;
    }

    private static String getJsonCompitableString(String key, String value, int leadingSpaceCount) {
        String line = "";
        line = line + getSpaceString(leadingSpaceCount);
        line = line + QUOTE;
        if (!key.isEmpty() && !value.isEmpty()) {
            line = line + key + QUOTE + ":" + QUOTE + value + QUOTE + COMMA;
        } else {
            line = line + key + QUOTE + ":{";
        }
        return line;
    }

    private static String getSpaceString(int leadingSpaceCount) {
        String line = "";
        for (int count = 0; count < leadingSpaceCount; count ++) {
            line = line + " ";
        }
        return line;
    }

    private static int getLeadingSpaceCount(String line) {
        int count = 0;
        for (count = 0; line.charAt(count) == ' '; count ++);
        return count;
    }

    private static String getKey(String line) {
        String key = "";
        if (line.contains(": ")) {
            line.strip();
            key = line.substring(0, line.indexOf(": "));
        } else {
            line.strip();
            key = line.substring(0, line.length());
        }
        return key;
    }

    private static String getValue(String line) {
        String value = "";
        if (line.contains(": ")) {
            line.strip();
            value = line.substring(line.indexOf(": ") + 2, line.length());
        }
        return value;
    }

    private static List<String> readFilePropertiesRaw(List<String> files) {
        List<String> lines = new ArrayList<String>();
        String[] command = getCommand(files);
        Process cmdProcess = null;
        try {
            cmdProcess = Runtime.getRuntime().exec(command);
            lines = readLines(cmdProcess.getInputStream());
            cmdProcess.waitFor();
            if (cmdProcess.exitValue() != 0) {
                lines = readLines(cmdProcess.getErrorStream());
                logger.error("Failed to read command output : {}", lines);
                lines.clear();
            }
        } catch (Exception e) {
            logger.error("Failed to read media properties : {}", e);
        } finally {
            if (cmdProcess != null) {
                cmdProcess.destroy();
            }
        }
        return lines;
    }

    private static String[] getCommand(List<String> files) {
        List<String> command = new ArrayList<String>();
        command.add("identify");
        command.add("-verbose");
        command.addAll(files);
        return command.toArray(String[]::new);
    }

    private static List<String> readLines(InputStream inputStream) {
        try (Stream<String> lines = new BufferedReader(new InputStreamReader(inputStream)).lines()) {
            return lines.collect(Collectors.toList());
        }
    }
}
