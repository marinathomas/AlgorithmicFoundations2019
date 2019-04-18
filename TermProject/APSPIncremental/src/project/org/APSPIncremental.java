package project.org;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class APSPIncremental {

	String inputFile = "C:\\Masters\\AlgorithmicFoundations2019\\TermProject\\APSPIncremental\\src\\project\\org\\textbook.gr.txt";
	int[][] distance;
	String[][] parents;
	int noOfVertices;
	int noOfArcs;

	static class Edge {
		int weight;
		int edgeTo;
	}

	public static void main(String args[]) {
		APSPIncremental apspIncremental = new APSPIncremental();
		apspIncremental.populateGarph();
		apspIncremental.printGraph();
		apspIncremental.floydWarshall();
		System.out.println("=====================");
		apspIncremental.printGraph();

	}

	private void floydWarshall() {
		for (int k = 0; k < noOfVertices; k++) {
			for (int i = 0; i < noOfVertices; i++) {
				for (int j = 0; j < noOfVertices; j++) {
					if (distance[i][k] != Integer.MAX_VALUE && distance[k][j] != Integer.MAX_VALUE && i!= j && i != k && j !=k) {
						if (distance[i][j] > distance[i][k] + distance[k][j]) {
							distance[i][j] = distance[i][k] + distance[k][j];
							if(parents[i][j] == null) {
								parents[i][j] = String.valueOf(k);
							}else {
								parents[i][j] = parents[k][j] ;
							}
						}
					}
				}
			}
		}

	}

	private void printGraph() {
		for (int i = 0; i < noOfVertices; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < noOfVertices; j++) {
				if(distance[i][j] == Integer.MAX_VALUE) {
					sb.append(" *");
				} else {
					sb.append(" " + distance[i][j]);
				}				
			}
			System.out.println(sb.toString());
		}
		System.out.println("=====================");
		for (int i = 0; i < noOfVertices; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < noOfVertices; j++) {
				sb.append(" " + parents[i][j]);
			}
			System.out.println(sb.toString());
		}
	}

	private void populateGarph() {
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(inputFile);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				String[] temp = sCurrentLine.split(" ");
				if ("p".equals(temp[0])) {
					noOfVertices = Integer.parseInt(temp[2]);
					noOfArcs = Integer.parseInt(temp[3]);
					distance = new int[noOfVertices][noOfVertices];
					parents = new String[noOfVertices][noOfVertices];
					initializePaths();
				}
				if ("a".equals(temp[0])) {
					int x = Integer.parseInt(temp[1]);
					int y = Integer.parseInt(temp[2]);
					int weight = Integer.parseInt(temp[3]);
					distance[x - 1][y - 1] = weight;
					parents[x - 1][y - 1] = String.valueOf(x-1);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void initializePaths() {
		for (int i = 0; i < noOfVertices; i++) {
			for (int j = 0; j < noOfVertices; j++) {
				if (i == j) {
					distance[i][j] = 0;
				} else {
					distance[i][j] = Integer.MAX_VALUE;
				}
				parents[i][j] = null;
			}
		}

	}

}
