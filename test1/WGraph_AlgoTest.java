import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    private static Random rnd;
    private WGraph_DS G;
    private final int SEED = 2;


    @Test
    void copy() {
        G = GraphGenerator(30, 20, SEED);
        WGraph_Algo ga = new WGraph_Algo();
        WGraph_Algo gb = new WGraph_Algo();
        ga.init(G);
        gb.init(ga.copy());
        assertAll(
                () -> assertEquals(ga.getGraph().edgeSize(), gb.getGraph().edgeSize(), "the graphs should be the same edge"),
                () -> assertEquals(ga.getGraph().nodeSize(), gb.getGraph().nodeSize(), "the graphs should be the same nodes"),
                () -> assertEquals(ga.getGraph().hasEdge(1, 2), gb.getGraph().hasEdge(1, 2), "the graphs should be the same edge"),
                () -> G.removeNode(5),
                () -> assertNotEquals(ga.getGraph().nodeSize(), gb.getGraph().nodeSize(), "the copy shouldn't change if the source graph changed")

        );
    }

    @Test
    void isConnected() {
        G = GraphGenerator(5, 7, SEED);
        WGraph_Algo ga = new WGraph_Algo();
        ga.init(G);
        assertTrue(ga.isConnected(), "connected graph");
        G.removeNode(1);
        assertTrue(ga.isConnected(), "removing this node should make the graph stay connected");
        G.removeNode(3);
        assertFalse(ga.isConnected(), "removing this node should make the graph disconnect");
        G.connect(4, 0, 5);
        assertTrue(ga.isConnected(), "adding this node connect the graph");
        WGraph_DS G0 = new WGraph_DS();
        ga.init(G0);
        assertTrue(ga.isConnected(), "empty graph is connected");
    }

    @Test
    void shortestPathDist() {
        G = GraphGenerator(6, 7, SEED);
        WGraph_Algo ga = new WGraph_Algo();
        G.connect(2, 0, 10);
        G.connect(5, 3, 10);
        ga.init(G);
        double dis = ga.shortestPathDist(4, 5);
        assertEquals(7.0, dis,"shortest path from 4 to 5");
        G.removeNode(2);
        dis = ga.shortestPathDist(4, 5);
        assertEquals(13.0, dis,"shortest path from 4 to 5 after removing node 2");

    }

    @Test
    void shortestPath() {
    }

    @Test
    void File() {

    }


    @Test
    void time(){
        graph_creator(10000000, 500000, SEED);
    }


    public static WGraph_DS GraphGenerator(int V, int E, int seed) {
        if (E > (long) V * (V - 1) / 2) throw new IllegalArgumentException("Too many edges");
        if (E < 0) throw new IllegalArgumentException("Too few edges");
        rnd = new Random(seed);
        WGraph_DS G = new WGraph_DS();
        for (int i = 0; i < V; i++) {
            G.addNode(i);
        }

        while (G.edgeSize() < E) {

            int v = rnd.nextInt(V);
            int w = rnd.nextInt(V);
            int t = 1 + rnd.nextInt(2);

            G.connect(v, w, t);
           // System.out.println(G.edgeSize() + " " + v + " " + w + " " + (G.getEdge(w, v) == t));

       }
        return G;
    }

    public static weighted_graph graph_creator(int v_size, int e_size, int seed) {
        weighted_graph g = new WGraph_DS();
        rnd = new Random(seed);
        for (int i = 0; i < v_size; i++) {

            g.addNode(i);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
       // int[] nodes = nodes(g);
        while (g.edgeSize() < e_size) {
            int a = nextRnd(0, v_size);
            int b = nextRnd(0, v_size);
          //  int i = nodes[a];
           // int j = nodes[b];
            g.connect(a, b,5);
        }
        return g;
    }
    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = rnd.nextDouble();
        double dx = max - min;
        double ans = d * dx + min;
        return ans;
    }
    }