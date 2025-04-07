//403106176
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim();
        String[] words = input.split("\\s*-\\s*|\\s+");

        words = removeEmptyStrings(words);
        lettersFromStart(words);
        lettersFromEnd(words);
    }

    static String[] removeEmptyStrings(String[] array) {
        List<String> list = new ArrayList<>();
        for (String s : array) {
            if (!s.isEmpty()) list.add(s);
        }
        return list.toArray(new String[0]);
    }

    static void lettersFromStart(String[] words) {
        Map<String, Set<String>> prefixMap = new HashMap<>();

        for (String word : words) {
            StringBuilder prefix = new StringBuilder();
            for (char ch : word.toCharArray()) {
                prefix.append(ch);
                prefixMap.computeIfAbsent(prefix.toString(), k -> new HashSet<>()).add(word);
            }
        }

        for (String word : words) {
            StringBuilder prefix = new StringBuilder();
            boolean uniqueFound = false;

            for (int i = 0; i < word.length(); i++) {
                prefix.append(word.charAt(i));
                if (prefixMap.get(prefix.toString()).size() == 1) {
                    System.out.print((i + 1) + " ");
                    uniqueFound = true;
                    break;
                }
            }

            if (!uniqueFound) {
                System.out.print(word.length() + " ");
            }
        }
        System.out.println();
    }

    static void lettersFromEnd(String[] words) {
        Map<String, Set<String>> suffixMap = new HashMap<>();

        for (String word : words) {
            StringBuilder suffix = new StringBuilder();
            char[] chars = word.toCharArray();
            for (int i = chars.length - 1; i >= 0; i--) {
                suffix.insert(0, chars[i]);
                suffixMap.computeIfAbsent(suffix.toString(), k -> new HashSet<>()).add(word);
            }
        }

        for (String word : words) {
            StringBuilder suffix = new StringBuilder();
            boolean uniqueFound = false;
            char[] chars = word.toCharArray();

            for (int i = chars.length - 1; i >= 0; i--) {
                suffix.insert(0, chars[i]);
                if (suffixMap.get(suffix.toString()).size() == 1) {
                    System.out.print((chars.length - i) + " ");
                    uniqueFound = true;
                    break;
                }
            }

            if (!uniqueFound) {
                System.out.print(chars.length + " ");
            }
        }
        System.out.println();
    }
}