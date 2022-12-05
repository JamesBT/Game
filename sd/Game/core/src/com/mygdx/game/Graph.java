package com.mygdx.game;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Arrays.*;

public class Graph<T>{
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

    // Print the matrix
//    public String toString() {
//        StringBuilder s = new StringBuilder();
//        for (int i = 0; i < this.size; i++) {
//            s.append(i + ": ");
//            for (int j : adjMatrix[i]) {
//                s.append((j ? 1 : 0) + " ");
//            }
//            s.append("\n");
//        }
//        return s.toString();
//    }

    //bfs
//    public int[] bfs(int awal,int tujuan){
//        ArrayList<Integer> queue = new ArrayList<Integer>();
//
//    }
}
