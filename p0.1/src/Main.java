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
        Map<String, Integer> prefixCount = new HashMap<>();

        // Precompute all prefixes
        for (String word : words) {
            StringBuilder prefix = new StringBuilder();
            for (char ch : word.toCharArray()) {
                prefix.append(ch);
                prefixCount.put(prefix.toString(), prefixCount.getOrDefault(prefix.toString(), 0) + 1);
            }
        }

        // Find shortest unique prefix
        for (String word : words) {
            StringBuilder prefix = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                prefix.append(word.charAt(i));
                if (prefixCount.get(prefix.toString()) == 1) {
                    System.out.print((i + 1) + " ");
                    break;
                }
            }
        }
        System.out.println();
    }

    static void lettersFromEnd(String[] words) {
        Map<String, Integer> suffixCount = new HashMap<>();

        // Precompute all suffixes
        for (String word : words) {
            StringBuilder suffix = new StringBuilder();
            for (int i = word.length() - 1; i >= 0; i--) {
                suffix.insert(0, word.charAt(i));
                suffixCount.put(suffix.toString(), suffixCount.getOrDefault(suffix.toString(), 0) + 1);
            }
        }

        // Find shortest unique suffix
        for (String word : words) {
            StringBuilder suffix = new StringBuilder();
            for (int i = word.length() - 1; i >= 0; i--) {
                suffix.insert(0, word.charAt(i));
                if (suffixCount.get(suffix.toString()) == 1) {
                    System.out.print((word.length() - i) + " ");
                    break;
                }
            }
        }
        System.out.println();
    }
}
