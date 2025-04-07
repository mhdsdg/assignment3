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

        goThroughTable(table, queries , found);
    }

    static int goThroughTable(String[] table , String [] queries , boolean[] found) {
        boolean[][] visited = new boolean[table.length][table.length];
        for (int k = 0 ; k < table.length ; k++) {
            String row = table[k];
            for (int t = 0 ; t < row.length(); t++) {
                char ch = row.charAt(t);
                for(int d = 0 ; d < queries.length ; d++) {
                    String query = queries[d];
                    if (ch == query.charAt(0)) {
                        for (boolean[] array : visited) {
                            Arrays.fill(array, false);
                        }
                        if(recursive(table , query , k , t , visited) == 0){
                            found[d] = true;
                        }
                    }
                }
            }
        }
        for (boolean b : found) {
            if(b) System.out.println("FOUND");
            else System.out.println("NOT FOUND");
        }
        return 0;
    }

    static int recursive(String[] table , String query , int row , int col , boolean[][] visited) {
        visited[row][col] = true;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(i == 0 && j == 0) continue;
                if(row + j < table.length &&
                        col + i < table.length &&
                        row + j >= 0 &&
                        col + i >= 0 &&
                        !visited[row + j][col + i] &&
                        query.charAt(1) == table[row + j].charAt(col + i)){
                    if (query.length() == 2) return 0;
                    int result = recursive(table , query.substring(1) , row + j , col + i , visited);
                    if(result == 0){
                        return 0;
                    }
                }
            }
        }
        return -1;
    }
}