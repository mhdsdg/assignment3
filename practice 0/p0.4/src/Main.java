//403106176
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int y = sc.nextInt();
        int x = sc.nextInt();
        Cell[][] field = new Cell[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                int height = sc.nextInt();
                field[i][j] = new Cell(i , j , height);
            }
        }
        System.out.println(trappedRainWater(field , x , y));
    }

    public static int trappedRainWater(Cell[][] field , int x , int y) {
        boolean[][] visited = new boolean[y][x];
        int totalRainWater = 0;
        int[][] neighbours = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        PriorityQueue<Cell> heap = new PriorityQueue<>((a, b) -> a.height - b.height);
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (i == 0 || i == y - 1 ||
                        j == 0 || j == x - 1) {
                    visited[i][j] = true;
                    heap.offer(field[i][j]);
                }
            }
        }
        while (!heap.isEmpty()) {
            Cell current = heap.poll();
            for (int i = 0; i < neighbours.length; i++) {
                int nj = neighbours[i][0] + current.j;
                int nx = neighbours[i][1] + current.i;
                if(nj >= 0 && nj < y && nx >= 0 && nx < x && visited[nj][nx] == false) {
                    if (current.height > field[nj][nx].height) {
                        totalRainWater += -field[nj][nx].height + current.height;
                        field[nj][nx].height = current.height;
                    }
                    visited[nj][nx] = true;
                    heap.offer(field[nj][nx]);
                }
            }
        }
        return totalRainWater;
    }

    public static class Cell{
        int j , i , height ;
        Cell(int j , int i , int height){
            this.j = j;
            this.i = i;
            this.height = height;
        }
    }
}