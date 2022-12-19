package com.mygdx.game;
import java.util.*;

public class Graph {
    private int adjMatrix[][] = {
            //0 1  2  3  4  5  6  7  8  9 10 11 -->node ke
            {0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0},
            {0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0},
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0},
    };
    private int size = 12;

    public void printgraph() {
        for (int[] i : adjMatrix) {
            for (int j : i) {
                System.out.print(j + " ");
            }
            System.out.println("");
        }
    }

    public ArrayList<Integer> shortestpath(int awal, int dest) {
        int pred[] = new int[this.size];
        if (BFS(awal, dest, pred) == false) {
            System.out.println("error");
            return null;
        }

        ArrayList<Integer> path = new ArrayList<Integer>();
        int crawl = dest;
//        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

        Collections.reverse(path);
        return path;

    }

    private boolean BFS(int awal, int dest, int pred[]) {
        ArrayList<Integer> queue = new ArrayList<Integer>();
        boolean visited[] = new boolean[this.size];

        for (int i = 0; i < this.size; i++) {
            visited[i] = false;
            pred[i] = -1;
        }

        visited[awal] = true;
        queue.add(awal);

        while (!queue.isEmpty()) {
            int u = queue.get(0);
            queue.remove(0);
            for (int i = 0; i < adjMatrix[u].length; i++) {
                if (adjMatrix[u][i] == 1 && visited[i] == false) {
                    visited[i] = true;
                    pred[i] = u;
                    queue.add(i);

                    if (adjMatrix[u][i] == 1 && i == dest) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void DFS(int awal, int dest){
        boolean visited[] = new boolean[this.size];

        ArrayList<Integer> shortest = this.DFS2(awal, dest, visited);

        if (shortest.size() !=0){
            for (int i=0; i<shortest.size()-1; i++){
                System.out.print(shortest.get(i)+", ");
            }
            System.out.println(shortest.get(-1));
        }else
            System.out.println("no other route found");
    }

    private ArrayList<Integer> DFS2(int awal, int dest, boolean[] visited) {
        ArrayList<Integer> temp = new ArrayList<Integer>();
        ArrayList<Integer> shortest = new ArrayList<Integer>();

        int count  = 0;
        count+=1;
        int min = 0;
        visited[awal] = true;
        temp.add(awal);

        if (awal != dest){
            for (int i=0; i<adjMatrix[awal].length; i++){
                if (adjMatrix[awal][i] == 1){
                    if (visited[i] != true){
                        this.DFS2(i, dest, visited);
                        visited[i] = false;
                        temp.remove(0);
                        count+=1;
                    }
                }
            }
        }else {
            if (min == 0){
                min = count;
                shortest = (ArrayList<Integer>)temp.clone();
            } else if (count<min) {
                min = count;
                shortest = (ArrayList<Integer>)temp.clone();
            }
        }
        return shortest;
    }
}