import org.junit.jupiter.api.*;

import java.util.*;

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
                () -> assertEquals(ga.getGraph().edgeSize(), gb.getGraph().edgeSize(), "the graphs should have the same edge"),
                () -> assertEquals(ga.getGraph().nodeSize(), gb.getGraph().nodeSize(), "the graphs should have the same nodes"),
                () -> assertEquals(ga.getGraph().hasEdge(1, 2), gb.getGraph().hasEdge(1, 2), "the graphs should have the same edge"),
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
        G0 = GraphGenerator(5, 0, SEED);
        ga.init(G0);
        assertFalse(ga.isConnected(), "graph with no edges");
    }

    @Test
    void shortestPathDist() {
        G = GraphGenerator(500000, 700000, SEED);
        WGraph_Algo ga = new WGraph_Algo();
        G.connect(2, 0, 10);
        G.connect(5, 3, 10);
        ga.init(G);
        double dis = ga.shortestPathDist(78, 9);
        assertEquals(15.0, dis, "shortest path from 4 to 5");
        for (int i = 80; i <10080 ; i++) {
            G.removeNode(i);
        }

        dis = ga.shortestPathDist(4, 5);
        assertEquals(18.0, dis, "shortest path from 4 to 5 after removing node 10000 nodes");
        WGraph_DS G0 = new WGraph_DS();
        ga.init(G0);
        assertEquals(ga.shortestPathDist(1, 2), -1, "shortest path to non exist nodes is -1");
    }

    @Test
    void shortestPath() {
        G = GraphGenerator(6, 7, SEED);
        G.connect(0, 2, 10);
        G.connect(5, 3, 10);
        WGraph_Algo ga = new WGraph_Algo();
        ga.init(G);
        Iterator it = ga.shortestPath(4, 5).iterator();
        assertAll("4->0->3->1->2->5",
                () -> assertEquals(it.next(), G.getNode(4)),
                () -> assertEquals(it.next(), G.getNode(0)),
                () -> assertEquals(it.next(), G.getNode(3)),
                () -> assertEquals(it.next(), G.getNode(1)),
                () -> assertEquals(it.next(), G.getNode(2)),
                () -> assertEquals(it.next(), G.getNode(5))
        );

    }

    @Test
    void File() {
        G = GraphGenerator(6, 7, SEED);
        WGraph_Algo ga = new WGraph_Algo();
        WGraph_Algo q = new WGraph_Algo();
        ga.init(G);
        ga.save("file_test.obj");
        q.load("file_test.obj");
        assertEquals(G,q.getGraph(),"the graph copy should be equals to the graph source");
        q.getGraph().removeEdge(0,3);
        assertNotEquals(G,q.getGraph(),"change on the graph copy shouldn't effect on the graph source ");
        assertFalse(q.load("nonExistFile.obj"));
    }



/////////////////////////////
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

        }
        return G;
    }


}