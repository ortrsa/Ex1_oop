import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {

    private static Random rnd;
    private WGraph_DS G;
    private final int SEED = 2;

    @Test
    void hasEdge() {
        G = GraphGenerator(5, 10, SEED);
        WGraph_DS G0 = GraphGenerator(0,0,SEED);
        assertAll("checking group",
                () -> assertFalse(G0.hasEdge(3, 2),"empty graph"),
                () -> assertTrue(G.hasEdge(3, 2),"should be edge"),
                () -> assertTrue(G.hasEdge(4, 2),"should be edge"),
                () -> assertTrue(G.hasEdge(3, 4),"should be edge"),
                () -> assertTrue(G.hasEdge(4, 1),"should be edge"),
                () -> G.removeEdge(4, 1),
                () -> assertFalse(G.hasEdge(4, 1),"shouldn't be edge"),
                () -> assertFalse(G.hasEdge(10, 1),"the graph doesn't have edge with key 10 ")
        );

    }

    @Test
    void getEdge() {
        G = GraphGenerator(5, 10, SEED);
        assertEquals(2, G.getEdge(2, 4),"wight of th edge");
        assertEquals(2, G.getEdge(4, 2),"should be the same as the opposite ");
        assertEquals(0, G.getEdge(2, 2),"the edge between node to himself should be 0");
        assertEquals(-1, G.getEdge(2, 22), "22 is not a node on this graph");
    }

    @Test
    void getV() {
        G = GraphGenerator(0, 0, SEED);
        assertTrue(G.getV().isEmpty());
        for (int i = 0; i < 10; i++) {
            G.addNode(i);
            G.connect(i,i+1,5);
        }
        for (node_info n: G.getV()) {

        }

    }
    @Test
    void getV_node_id(){
        G = GraphGenerator(5, 10, SEED);
        for (int i = 0; i < 4; i++) {
            assertTrue(G.getV(4).contains(G.getNode(i)),"all nodes are connected to node 4");
        }
        assertEquals(G.getV(4).size(),4,"node 4 have 4 Nei");
        G.removeNode(4);
        assertNull(G.getV(4),"checking neighbors for deleted node");
        G.addNode(5);
        assertNotNull(G.getV(5),"node with no neighbors return empty list but not null");

    }

    @Test
    void nodeSize() {
        WGraph_DS G1 = new WGraph_DS();
        assertEquals(0, G1.nodeSize(),"empty graph");
        G = GraphGenerator(5, 10, SEED);
        assertEquals(5, G.nodeSize(), "return node size");
        G.removeNode(1);
        assertEquals(4, G.nodeSize(), "return node size");
        G.addNode(1);
        assertEquals(5, G.nodeSize(), "return node size");
    }

    @Test
    void edgeSize() {
        WGraph_DS G1 = new WGraph_DS();
        assertEquals(0, G1.edgeSize(),"no edges");
        G = GraphGenerator(5, 10, SEED);
        assertEquals(10, G.edgeSize());


    }
    @Test
    void removeFromGraph(){
        G = GraphGenerator(10, 20, SEED);
        assertTrue(G.hasEdge(6,7));
        G.removeNode(6);
        assertFalse(G.hasEdge(6,7),"after remove 6 shouldn't be any edge to 6 ");
        assertNull(G.removeNode(6),"node 6 already removed, should return null");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                G.removeEdge(i,j);
            }
        }
        assertEquals(0,G.edgeSize(),"after remove all edges souled be 0");


    }

    @Test
    void testEquals() {
        G = GraphGenerator(10, 20, SEED);
        WGraph_Algo ga = new WGraph_Algo();
        WGraph_Algo gc = new WGraph_Algo();
        ga.init(G);
        gc.init(ga.copy());
        assertEquals(ga.getGraph(),gc.getGraph(),"the graph and his copy should be equals");
        ga.getGraph().removeNode(5);
        assertNotEquals(ga.getGraph(),gc.getGraph(),"after removing node");

    }
    @Test
    void WGraphTimeTest() {
        long start = new Date().getTime();
        G = GraphGenerator(500000, 2012745, SEED);
        assertFalse(G.hasEdge(1,2));
        for (int i = 0; i <10000 ; i++) {
            G.connect(i,i+1,6);
        }
        assertTrue(G.hasEdge(1,2));
        for (int i = 0; i <10000 ; i++) {
            G.removeNode(i);
        }
        assertFalse(G.hasEdge(63455,22234));
        long end = new Date().getTime();
        double time = (end-start)/1000.0;
        if (time>10){
                    fail("running time for timeTest is: " + time);
        }
    }




    ///////////////////////

    public static WGraph_DS GraphGenerator ( int V, int E, int seed){
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
            int t = rnd.nextInt(20);
            G.connect(v, w, t);
        }
        return G;
    }
}