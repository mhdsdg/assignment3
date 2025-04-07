//403106176
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        String table[] = new String[N];
        for (int i = 0; i < N; i++) {
            table[i] = sc.next();
        }

        int M = sc.nextInt();

        boolean[] found = new boolean[M];

        String[] queries = new String[M];
        for (int i = 0; i < M; i++) {
            queries[i] = sc.next();
        }

        goThroughTable(table, queries, found);
    }

    static void goThroughTable(String[] table, String[] queries, boolean[] found) {
        for (int k = 0; k < table.length; k++) {
            String row = table[k];
            for (int t = 0; t < row.length(); t++) {
                char ch = row.charAt(t);
                for (int d = 0; d < queries.length; d++) {
                    String query = queries[d];
                    if (ch == query.charAt(0)) {
                        boolean[][] visited = new boolean[table.length][table[0].length()];
                        if (recursive(table, query, k, t, visited)) {
                            found[d] = true;
                        }
                    }
                }
            }
        }
        for (boolean b : found) {
            if (b) System.out.println("FOUND");
            else System.out.println("NOT FOUND");
        }
    }

    static boolean recursive(String[] table, String query, int row, int col, boolean[][] visited) {
        if (query.length() == 1) {
            return true;
        }
        visited[row][col] = true;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < table.length &&
                        newCol >= 0 && newCol < table[0].length() &&
                        !visited[newRow][newCol] &&
                        query.charAt(1) == table[newRow].charAt(newCol)) {
                    if (recursive(table, query.substring(1), newRow, newCol, visited)) {
                        return true;
                    }
                }
            }
        }
        visited[row][col] = false; // Backtrack
        return false;
    }
}