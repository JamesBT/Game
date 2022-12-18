package com.mygdx.game;
import java.util.*;

public class Graph {
    private int adjMatrix[][];
    private int size;

    // Initialize the matrix
    public Graph(int size) {
        this.size = size;
        adjMatrix = new int[size][size];
    }

    // Add edges
    public void addEdge(int i, int j) {
        adjMatrix[i][j] = 1;
        adjMatrix[j][i] = 1;
    }

    public void printgraph() {
        for (int[] i : adjMatrix) {
            for (int j : i) {
                System.out.print(j + " ");
            }
            System.out.println("");
        }
    }

    public void shortestpath(int awal, int dest) {
        int pred[] = new int[this.size];
        if (BFS(awal, dest, pred) == false) {
            System.out.println("error");
            return;
        }

        ArrayList<Integer> path = new ArrayList<Integer>();
        int crawl = dest;
        path.add(crawl);
        while (pred[crawl] != -1) {
            path.add(pred[crawl]);
            crawl = pred[crawl];
        }

        System.out.print("Path: ");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print(path.get(i) + " ");
        }
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

    private void DFS(int awal){
        boolean visited[] = new boolean[this.size];
        for (int i = 0; i < this.size; i++) {
            visited[i] = false;
        }
        this.DFS2(awal, visited);
    }

    private boolean DFS2(int awal, boolean[] visited) {
        visited[awal] = true;

        for (int i = 0; i < adjMatrix[awal].length; i++) {
            if (adjMatrix[awal][i] == 1 && visited[i] == false) {
                visited[i] = true;
                this.DFS2(i, visited);
            }
        }
        return false;
    }
}