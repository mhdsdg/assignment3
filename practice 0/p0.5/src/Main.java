//403106176
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int logCount = Integer.parseInt(sc.nextLine());

        Log[] allLogs = new Log[logCount];
        //new lists of logs for each category
        ArrayList<Log> debug = new ArrayList<>();
        ArrayList<Log> error = new ArrayList<>();
        ArrayList<Log> warning = new ArrayList<>();
        ArrayList<Log> info = new ArrayList<>();
        //storing categories
        for (int i = 0; i < logCount; i++) {
            String line = sc.nextLine();
            int dateTimeEnd = line.indexOf("]");
            String dateTime = line.substring(1, dateTimeEnd);
            String[] dateAndTime = dateTime.split(" ");

            int levelStart = line.indexOf("[", dateTimeEnd + 1);
            int levelEnd = line.indexOf("]", levelStart);
            String level = line.substring(levelStart + 1, levelEnd);

            String message = line.substring(levelEnd + 2);

            allLogs[i] = new Log(dateAndTime[0], dateAndTime[1], level, message);
            switch (level) {
                case "DEBUG" -> debug.add(allLogs[i]);
                case "ERROR" -> error.add(allLogs[i]);
                case "WARNING" -> warning.add(allLogs[i]);
                case "INFO" -> info.add(allLogs[i]);
            }
        }

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] tokens = line.split(" ");
            switch (tokens[0]) {
                case "LEVEL" -> {
                    switch (tokens[1]) {
                        case "DEBUG" -> {
                            for (Log log : debug) {
                                System.out.println("[" + log.date + " " + log.time + "]" + " " +
                                        "[" + log.level + "] " + log.message);
                            }
                            System.out.println();
                        }
                        case "ERROR" -> {
                            for (Log log : error) {
                                System.out.println("[" + log.date + " " + log.time + "]" + " " +
                                        "[" + log.level + "] " + log.message);
                            }
                            System.out.println();
                        }
                        case "WARNING" -> {
                            for (Log log : warning) {
                                System.out.println("[" + log.date + " " + log.time + "]" + " " +
                                        "[" + log.level + "] " + log.message);
                            }
                            System.out.println();
                        }
                        case "INFO" -> {
                            for (Log log : info) {
                                System.out.println("[" + log.date + " " + log.time + "]" + " " +
                                        "[" + log.level + "] " + log.message);
                            }
                            System.out.println();
                        }
                    }
                }
                case "COUNT_LEVEL" -> {
                    switch (tokens[1]) {
                        case "DEBUG" -> System.out.println(debug.size());
                        case "ERROR" -> System.out.println(error.size());
                        case "WARNING" -> System.out.println(warning.size());
                        case "INFO" -> System.out.println(info.size());
                    }
                    System.out.println();
                }
                case "CONTAINS" -> {
                    String keyWord = tokens[1];
                    for (Log log : allLogs) {
                        if (log.message.contains(keyWord)) {
                            System.out.println("[" + log.date + " " + log.time + "]" + " " +
                                    "[" + log.level + "] " + log.message);
                        }
                    }
                    System.out.println();
                }
                case "ERROR_TIMESTAMPS" -> {
                    if (tokens.length > 1 && tokens[1].equals("--reverse")) {
                        for (int i = error.size() - 1; i >= 0; i--) {
                            System.out.println(error.get(i).date + " " + error.get(i).time);
                        }
                    } else {
                        for (Log log : error) {
                            System.out.println(log.date + " " + log.time);
                        }
                    }
                    System.out.println();
                }
                case "FREQUENCY_ANALYSIS" -> {
                    int topN = 5;
                    if (tokens.length > 1 && tokens[1].equals("--top")) topN = Integer.parseInt(tokens[2]);
                    Map<String, Integer> wordFrequency = new HashMap<>();
                    //counting the words
                    for (Log log : allLogs) {
                        String[] words = log.message.split(" ");
                        for (String word : words) {
                            wordFrequency.put(word.toLowerCase(), wordFrequency.getOrDefault(word.toLowerCase(), 0) + 1);
                        }
                    }
                    // sorting the words
                    List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordFrequency.entrySet());
                    sortedEntries.sort((e1, e2) -> {
                        int freqCompare = e2.getValue().compareTo(e1.getValue());
                        if (freqCompare != 0) {
                            return freqCompare;
                        } else {
                            return e1.getKey().compareTo(e2.getKey());
                        }
                    });

                    for (int i = 0; i < Math.min(topN, sortedEntries.size()); i++) {
                        Map.Entry<String, Integer> entry = sortedEntries.get(i);
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    System.out.println();
                }
                case "DATE_RANGE" -> {
                    LocalDate startDate = LocalDate.parse(tokens[1], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate endDate = LocalDate.parse(tokens[2], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    ArrayList<Log> logsInRange = new ArrayList<>();
                    for (Log log : allLogs) {
                        if (!log.getDate().isBefore(startDate) && !log.getDate().isAfter(endDate)) {
                            logsInRange.add(log);
                        }
                    }
                    for (Log log : logsInRange) {
                        System.out.println("[" + log.date + " " + log.time + "]" + " " +
                                "[" + log.level + "] " + log.message);
                    }
                    System.out.println();
                }
                case "TOP_K_LEVEL" -> {
                    int topN = Integer.parseInt(tokens[1]);
                    String logLevel = tokens[2];
                    ArrayList topK = new ArrayList();
                    topK = switch (logLevel) {
                        case "DEBUG" -> debug;
                        case "ERROR" -> error;
                        case "WARNING" -> warning;
                        case "INFO" -> info;
                        default -> topK;
                    };
                    if (tokens.length > 3 && tokens[3].equals("--reverse")) {
                        Collections.sort(topK, Comparator.comparing(Log::getDate).thenComparing(Log::getTime));
                    } else {
                        Collections.sort(topK, Comparator.comparing(Log::getDate).thenComparing(Log::getTime).reversed());
                    }
                    for (int i = 0; i < Math.min(topN , topK.size()); i++) {
                        Log log = (Log) topK.get(i);
                        System.out.println("[" + log.date + " " + log.time + "]" + " " +
                                "[" + log.level + "] " + log.message);
                    }
                    System.out.println();
                }
            }
        }
    }

    public static class Log {
        public String date;
        public String time;
        public String level;
        public String message;

        Log(String date, String time, String level, String message) {
            this.date = date;
            this.time = time;
            this.level = level;
            this.message = message;
        }

        public LocalDate getDate() {
            LocalDate dateLocal = LocalDate.parse(date , DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return dateLocal;
        }

        public LocalTime getTime() {
            LocalTime timeLocal = LocalTime.parse(time , DateTimeFormatter.ofPattern("HH:mm:ss"));
            return timeLocal;
        }
    }
}