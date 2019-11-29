package ee.ttu.algoritmid.trampoline;

import java.util.*;

public class HW02 implements TrampolineCenter {

    private static List<String> dijkstraAnswerBuilder(int size, int[][][] from) {
        // initialize answer array as linked list
        LinkedList<String> answer = new LinkedList<>();

        // make starting coords the end coordinates
        int[] coords = new int[]{size - 1, size - 1};

        while (true) {

            // get the "from" coordinates of current coords
            int[] fromcoords = from[coords[0]][coords[1]];

            // if there was no change in the vertical coordinates
            if (fromcoords[0] == coords[0]) {

                // push to the start of answer list a string of East and the change in horizontal coordinates
                answer.push("E" + (coords[1] - fromcoords[1]));
            }

            // if there was no change in horizontal coordinates
            else {

                // push to the start of answer list a string of South and the change in vertical coordinates
                answer.push("S" + (coords[0] - fromcoords[0]));
            }

            // make new current coordinates the "from" coordinates of this cycle
            coords = fromcoords;

            // if the current "from" coordinates are that of the starting coordinates, break out as answer has been built
            if (fromcoords[0] == 0 && fromcoords[1] == 0) {
                break;
            }
        }

        return answer;
    }

    private static List<String> dijkstraEqualV1(int[][] map) {
        // in case of map with size 0, return empty list
        if (map.length == 1) {
            return new ArrayList<>();
        }

        // edge length
        int size = map.length;

        // storing distance to corresponding coordinates
        int[][] dist = new int[size][size];

        // storing coordinate from which you can get to corresponding coordinate
        int[][][] from = new int[size][size][2];

        // storing next coordinate to check
        ArrayDeque<int[]> queue = new ArrayDeque<>();

        // add starting coordinate to queue
        queue.add(new int[]{0, 0});

        // while there are nodes to check
        while (!queue.isEmpty()) {

            // get first coord from queue
            int[] coords = queue.poll();

            // get jump value from said coordinate on map
            int jump = map[coords[0]][coords[1]];

            // calculate new vertical coordinate
            int newVertical = coords[0] + jump;

            // calculate new horizontal coordinate
            int newHorizontal = coords[1] + jump;

            // if new vertical coordinate doesn't jump over the edge and the target coordinates haven't been visited
            if (newVertical < size && dist[newVertical][coords[1]] == 0) {

                // save current coords to the "from" of new coords
                from[newVertical][coords[1]] = coords;

                // make the distance of target coords the distance of current coords plus one
                dist[newVertical][coords[1]] = dist[coords[0]][coords[1]] + 1;

                // add new coords to the end of the queue
                queue.add(new int[]{newVertical, coords[1]});

                // if the new coordinates are the end coordinates, break out
                if (newVertical == (size - 1) && coords[1] == (size - 1)) {
                    break;
                }
            }

            // if new horizontal coordinate doesn't jump over the edge and the target coordinates haven't been visited
            if (newHorizontal < size && dist[coords[0]][newHorizontal] == 0) {

                // save current coords to the "from" of new coords
                from[coords[0]][newHorizontal] = coords;

                // make the distance of target coords the distance of current coords plus one
                dist[coords[0]][newHorizontal] = dist[coords[0]][coords[1]] + 1;

                // add new coords to the end of the queue
                queue.add(new int[]{coords[0], newHorizontal});

                // if the new coordinates are the end coordinates, break out
                if (coords[0] == (size - 1) && newHorizontal == (size - 1)) {
                    break;
                }
            }
        }

        // solution was found
        if (!queue.isEmpty()) {
            return dijkstraAnswerBuilder(size, from);
        }

        // solution wasn't found
        else {
            return null;
        }
    }

    private static List<String> dijkstraEqualV2(int[][] map) {
        // in case of map with size 0, return empty list
        if (map.length == 1) {
            return new ArrayList<>();
        }

        // edge length
        int size = map.length;

        // storing coordinate from which you can get to corresponding coordinate
        int[][][] from = new int[size][size][];

        // storing next coordinate to check
        ArrayDeque<int[]> queue = new ArrayDeque<>();

        // add starting coordinate to queue
        queue.add(new int[]{0, 0});

        // while there are nodes to check
        while (!queue.isEmpty()) {

            // get first coord from queue
            int[] coords = queue.poll();

            // get jump value from said coordinate on map
            int jump = map[coords[0]][coords[1]];

            // calculate new vertical coordinate
            int newVertical = coords[0] + jump;

            // calculate new horizontal coordinate
            int newHorizontal = coords[1] + jump;

            // if new vertical coordinate doesn't jump over the edge and the target coordinates haven't been visited
            if (newVertical < size && from[newVertical][coords[1]] == null) {

                // save current coords to the "from" of new coords
                from[newVertical][coords[1]] = coords;

                // add new coords to the end of the queue
                queue.add(new int[]{newVertical, coords[1]});

                // if the new coordinates are the end coordinates, break out
                if (newVertical == (size - 1) && coords[1] == (size - 1)) {
                    break;
                }
            }

            // if new horizontal coordinate doesn't jump over the edge and the target coordinates haven't been visited
            if (newHorizontal < size && from[coords[0]][newHorizontal] == null) {

                // save current coords to the "from" of new coords
                from[coords[0]][newHorizontal] = coords;

                // add new coords to the end of the queue
                queue.add(new int[]{coords[0], newHorizontal});

                // if the new coordinates are the end coordinates, break out
                if (coords[0] == (size - 1) && newHorizontal == (size - 1)) {
                    break;
                }
            }
        }

        // solution was found
        if (!queue.isEmpty()) {
            return dijkstraAnswerBuilder(size, from);
        }

        // solution wasn't found
        else {
            return null;
        }
    }

    private static List<String> dijkstraApproxV1(int[][] map) {
        // in case of map with size 0, return empty list
        if (map.length == 1) {
            return new ArrayList<>();
        }

        // edge length
        int size = map.length;

        // storing coordinate from which you can get to corresponding coordinate
        int[][][] from = new int[size][size][];

        // storing next coordinate to check
        ArrayDeque<int[]> queue = new ArrayDeque<>();

        // add starting coordinate to queue
        queue.add(new int[]{0, 0});

        // while there are nodes to check
        while (!queue.isEmpty()) {

            // get first coord from queue
            int[] coords = queue.poll();

            // get jump value from said coordinate on map
            int jump = map[coords[0]][coords[1]];

            // calculate new vertical coordinate maximum (the +1 version)
            int newVertical = coords[0] + jump + 1;

            // calculate new horizontal coordinate maximum (the +1 version)
            int newHorizontal = coords[1] + jump + 1;

            // boolean for checking if to break out of the while loop early if answer has been reached
            boolean answerFound = false;

            // loop through all the three options, +1, 0 and -1
            for (int i = 0; i < 3; i++) {

                // if new vertical coordinate doesn't jump over the edge and the target coordinates haven't been visited
                if (newVertical < size && from[newVertical][coords[1]] == null) {

                    // save current coords to the "from" of new coords
                    from[newVertical][coords[1]] = coords;

                    // add new coords to the end of the queue
                    queue.add(new int[]{newVertical, coords[1]});

                    // if the new coordinates are the end coordinates, make the boolean true and break out
                    if (newVertical == (size - 1) && coords[1] == (size - 1)) {
                        answerFound = true;
                        break;
                    }
                }

                // if new horizontal coordinate doesn't jump over the edge and the target coordinates haven't been visited
                if (newHorizontal < size && from[coords[0]][newHorizontal] == null) {

                    // save current coords to the "from" of new coords
                    from[coords[0]][newHorizontal] = coords;

                    // add new coords to the end of the queue
                    queue.add(new int[]{coords[0], newHorizontal});

                    // if the new coordinates are the end coordinates, make the boolean true and break out
                    if (coords[0] == (size - 1) && newHorizontal == (size - 1)) {
                        answerFound = true;
                        break;
                    }
                }

                // subtract one from each of the new coordinates
                newVertical--;
                newHorizontal--;
            }

            // if answer was found in the loop, break out
            if (answerFound) {
                break;
            }
        }

        // solution was found
        if (!queue.isEmpty()) {
            return dijkstraAnswerBuilder(size, from);
        }

        // solution wasn't found
        else {
            return null;
        }
    }

    @Override
    public List<String> findMinJumps(int[][] map) {
        return aStar(map);
    }

    public static void main(String[] args) {
//        int[][] map = new int[][]{{0, 0, 1, 100, 1}, {100, 300, 100, 100, 100}, {100, 100, 100, 100, 1}, {100, 100, 100, 100, 100}, {100, 100, 100, 100, 100}};
        int[][] map = new int[][]{{0, 0, 3}, {0, 0, 0}, {3, 0, 0}};
        System.out.println(aStar(map));
    }

    private static List<String> aStar(int[][] map) {
        // in case of map with size 0, return empty list
        if (map.length == 1) {
            return new ArrayList<>();
        }

        aStar aStar = new aStar(map);
        return aStar.go();
    }

}

class aStar {
    private int[][] map;
    private int size;
    private HashMap<int[], String> path;
    private ArrayDeque<int[]> queue = new ArrayDeque<>();

    aStar(int[][] map) {
        this.map = map;
        this.size = map.length;
        this.path = new HashMap<>();
        queue.add(new int[]{0, 0});
        this.path.put(queue.peek(), "");
    }

    List<String> go() {

        while (!queue.isEmpty()) {
            int[] location = queue.poll();

            if (location[0] == location[1] && location[1] == size - 1) {
                List<String> answer = new ArrayList<>(Arrays.asList(path.get(location).split(",")));
                answer.remove(0);
                return answer;
            }

            int jump = map[location[0]][location[1]];
//            map[location[0]][location[1]] = -1;

            if (jump < 0) {
                continue;
            }

            for (int i = 1; i > -2; i--) {
                int modified = jump + i;

//                if (modified <= 0) {
//                    continue;
//                }

                int newY = modified + location[0];
                if (0 <= newY && newY < map.length && !path.containsKey(new int[]{newY, location[1]})) {
                    int[] cur = new int[]{newY, location[1]};
                    queue.add(cur);
//                    System.out.println(Arrays.toString(cur));
                    path.put(cur, path.get(location) + ",S" + modified);
                }

                int newX = modified + location[1];
                if (0 <= newX && newX < map.length && !path.containsKey(new int[]{location[0], newX})) {
                    int[] cur = new int[]{location[0], newX};
                    queue.add(cur);
//                    System.out.println(Arrays.toString(cur));

                    path.put(cur, path.get(location) + ",E" + modified);
                }

            }
        }
        return null;
    }

}