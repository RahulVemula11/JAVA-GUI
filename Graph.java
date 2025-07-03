import java.util.*;

class Graph {
    private final int vertices;
    private final List<List<Node>> adjList;

    static class Node implements Comparable<Node> {
        int vertex, weight;

        public Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        public int compareTo(Node other) {
            return Integer.compare(this.weight, other.weight);
        }
    }

    public Graph(int vertices) {
        this.vertices = vertices;
        adjList = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int src, int dest, int weight) {
        adjList.get(src).add(new Node(dest, weight));
        adjList.get(dest).add(new Node(src, weight)); // Undirected Graph
    }

    public void dijkstra(int start, int end) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] dist = new int[vertices];
        int[] prev = new int[vertices];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);

        pq.add(new Node(start, 0));
        dist[start] = 0;

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;

            if (u == end)
                break; // Stop early if we reached the destination

            for (Node neighbor : adjList.get(u)) {
                int v = neighbor.vertex;
                int weight = neighbor.weight;

                if (dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    prev[v] = u;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }
        printPath(prev, start, end, dist[end]);
    }

    private void printPath(int[] prev, int start, int end, int distance) {
        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = prev[at]) {
            path.add(at);
        }
        Collections.reverse(path);

        System.out.println("Shortest Path: " + path);
        System.out.println("Total Distance: " + distance);
    }

    public static void main(String[] args) {
        Graph graph = new Graph(6); // Example graph with 6 locations
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 1);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(2, 4, 10);
        graph.addEdge(3, 4, 2);
        graph.addEdge(3, 5, 6);
        graph.addEdge(4, 5, 3);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter start location (0-5): ");
        int start = scanner.nextInt();
        System.out.print("Enter destination (0-5): ");
        int end = scanner.nextInt();

        graph.dijkstra(start, end);
        scanner.close();
    }
}
