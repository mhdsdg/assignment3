//403106176
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m, n;
        m = sc.nextInt();
        n = sc.nextInt();

        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            graph.add(new ArrayList<>());
        }
        int[] group = new int[m]; // 0 for unassigned, 1 for group 1, 2 for group 2

        for (int i = 0; i < n; i++) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();
            graph.get(v1).add(v2);
            graph.get(v2).add(v1);
        }

        if (isBipartite(group, graph, m) == -1) System.out.println("No");
        else System.out.println("Yes");
    }

    static int isBipartite(int[] group, List<List<Integer>> graph, int m) {
        for (int i = 0; i < m; i++) {
            if (group[i] == 0) {
                if (iterate(group, graph, i) == -1) return -1;
            }
        }
        return 0;
    }

    static int iterate(int[] group, List<List<Integer>> graph, int n) {
        Stack<Integer> stack = new Stack<>();
        stack.push(n);
        group[n] = 1;

        while (!stack.isEmpty()) {
            int current = stack.pop();
            int neighbourColor = group[current] == 1 ? 2 : 1;

            for (int neighbor : graph.get(current)) {
                if (group[neighbor] == 0) {
                    group[neighbor] = neighbourColor;
                    stack.push(neighbor);
                } else if (group[neighbor] != neighbourColor) {
                    return -1; // Conflict detected
                }
            }
        }
        return 0;
    }
}