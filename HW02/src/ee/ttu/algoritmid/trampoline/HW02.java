package ee.ttu.algoritmid.trampoline;

import java.util.*;

public class HW02 implements TrampolineCenter {

	private static List<String> dijkstraEqualV1(int[][] map) {
		if (map.length == 1) {
			return new ArrayList<>();
		}
		int size = map.length;  // edge length of the map
		int[][] dist = new int[size][size];  // array for storing distances for corresponding map squares
		int[][][] from = new int[size][size][2];  // array for storing the coordinates from which this coordinate was reached from
		ArrayDeque<int[]> queue = new ArrayDeque<>();  // queue for storing next node
		queue.add(new int[]{0, 0});  // throw starting point to queue
		while (!queue.isEmpty()) {
			int[] coords = queue.poll();
			int jump = map[coords[0]][coords[1]];  // jump value of the jumpy jump
			int newFirst = coords[0] + jump;  // new first coordinate
			int newSecond = coords[1] + jump;  // new second coordinate
			if (newFirst < size && dist[newFirst][coords[1]] == 0) {  // if not out of bounds and node not visited
				from[newFirst][coords[1]] = coords;  // save the current coords to the from of new coords
				dist[newFirst][coords[1]] = dist[coords[0]][coords[1]] + 1;
				queue.add(new int[]{newFirst, coords[1]});  // add this new node to the queue
				if (newFirst == (size - 1) && coords[1] == (size - 1)) {  // if has reached the end, break out
					break;
				}
			}
			if (newSecond < size && dist[coords[0]][newSecond] == 0) {  // if not out of bounds and node not visited
				from[coords[0]][newSecond] = coords;  // save the current coords to the from of new coords
				dist[coords[0]][newSecond] = dist[coords[0]][coords[1]] + 1;
				queue.add(new int[]{coords[0], newSecond});  // add this new node to the queue
				if (coords[0] == (size - 1) && newSecond == (size - 1)) {  // if has reached the end, break out
					break;
				}
			}
		}
		if (!queue.isEmpty()) {  // solution was found
			LinkedList<String> answer = new LinkedList<>();
			int[] coords = new int[]{size - 1, size - 1};
			while (true) {
				int[] fromcoords = from[coords[0]][coords[1]];
				if (fromcoords[0] == coords[0]) {
					answer.push("E" + (coords[1] - fromcoords[1]));
				}
				else {
					answer.push("S" + (coords[0] - fromcoords[0]));
				}
				coords = fromcoords;
				if (fromcoords[0] == 0 && fromcoords[1] == 0) {
					break;
				}
			}
			return answer;
		}
		else {  // solution was not found, null I guess
			return null;
		}
	}

	private static List<String> dijkstraEqualV2(int[][] map) {
		if (map.length == 1) {
			return new ArrayList<>();
		}
		int size = map.length;  // edge length of the map
		int[][][] from = new int[size][size][];  // array for storing the coordinates from which this coordinate was reached from
		ArrayDeque<int[]> queue = new ArrayDeque<>();  // queue for storing next node
		queue.add(new int[]{0, 0});  // throw starting point to queue
		while (!queue.isEmpty()) {
			int[] coords = queue.poll();
			int jump = map[coords[0]][coords[1]];  // jump value of the jumpy jump
			int newFirst = coords[0] + jump;  // new first coordinate
			int newSecond = coords[1] + jump;  // new second coordinate
			if (newFirst < size && from[newFirst][coords[1]] == null) {  // if not out of bounds and node not visited
				from[newFirst][coords[1]] = coords;  // save the current coords to the from of new coords
				queue.add(new int[]{newFirst, coords[1]});  // add this new node to the queue
				if (newFirst == (size - 1) && coords[1] == (size - 1)) {  // if has reached the end, break out
					break;
				}
			}
			if (newSecond < size && from[coords[0]][newSecond] == null) {  // if not out of bounds and node not visited
				from[coords[0]][newSecond] = coords;  // save the current coords to the from of new coords
				queue.add(new int[]{coords[0], newSecond});  // add this new node to the queue
				if (coords[0] == (size - 1) && newSecond == (size - 1)) {  // if has reached the end, break out
					break;
				}
			}
		}
		if (!queue.isEmpty()) {  // solution was found
			LinkedList<String> answer = new LinkedList<>();
			int[] coords = new int[]{size - 1, size - 1};
			while (true) {
				int[] fromcoords = from[coords[0]][coords[1]];
				if (fromcoords[0] == coords[0]) {
					answer.push("E" + (coords[1] - fromcoords[1]));
				}
				else {
					answer.push("S" + (coords[0] - fromcoords[0]));
				}
				coords = fromcoords;
				if (fromcoords[0] == 0 && fromcoords[1] == 0) {
					break;
				}
			}
			return answer;
		}
		else {  // solution was not found, null I guess
			return null;
		}
	}

	private static List<String> dijkstraApproxV1(int[][] map) {
		if (map.length == 1) {
			return new ArrayList<>();
		}
		int size = map.length;  // edge length of the map
		int[][][] from = new int[size][size][];  // array for storing the coordinates from which this coordinate was reached from
		ArrayDeque<int[]> queue = new ArrayDeque<>();  // queue for storing next node
		queue.add(new int[]{0, 0});  // throw starting point to queue
		while (!queue.isEmpty()) {
			int[] coords = queue.poll();
			int jump = map[coords[0]][coords[1]];  // jump value of the jumpy jump
			int newFirst = coords[0] + jump + 1;  // new first coordinate
			int newSecond = coords[1] + jump;  // new second coordinate
			boolean answerFound = false;
			for (int i = 0; i < 3; i++) {
				if (newFirst < size && from[newFirst][coords[1]] == null) {  // if not out of bounds and node not visited
					from[newFirst][coords[1]] = coords;  // save the current coords to the from of new coords
					queue.add(new int[]{newFirst, coords[1]});  // add this new node to the queue
					if (newFirst == (size - 1) && coords[1] == (size - 1)) {  // if has reached the end, break out
						answerFound = true;
						break;
					}
				}
				if (newSecond < size && from[coords[0]][newSecond] == null) {  // if not out of bounds and node not visited
					from[coords[0]][newSecond] = coords;  // save the current coords to the from of new coords
					queue.add(new int[]{coords[0], newSecond});  // add this new node to the queue
					if (coords[0] == (size - 1) && newSecond == (size - 1)) {  // if has reached the end, break out
						answerFound = true;
						break;
					}
				}
				newFirst--;
				newSecond--;
			}
			if (answerFound) {
				break;
			}
		}
		if (!queue.isEmpty()) {  // solution was found
			LinkedList<String> answer = new LinkedList<>();
			int[] coords = new int[]{size - 1, size - 1};
			while (true) {
				int[] fromcoords = from[coords[0]][coords[1]];
				if (fromcoords[0] == coords[0]) {
					answer.push("E" + (coords[1] - fromcoords[1]));
				}
				else {
					answer.push("S" + (coords[0] - fromcoords[0]));
				}
				coords = fromcoords;
				if (fromcoords[0] == 0 && fromcoords[1] == 0) {
					break;
				}
			}
			return answer;
		}
		else {  // solution was not found, null I guess
			System.out.println("no solution?");
			return null;
		}
	}

	@Override
	public List<String> findMinJumps(int[][] map) {
		return dijkstraApproxV1(map);
	}

	public static void main(String[] args) {
		int[][] map = new int[][]{{1, 2, 4}, {3, 10, 1}, {3, 2, 0}};
		System.out.println(dijkstraApproxV1(map));
	}
}