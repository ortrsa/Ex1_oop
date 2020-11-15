import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.HashMap;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {

    private static Random rnd;
    private WGraph_DS G;
    private final int SEED = 2;
    @Test
    void hasEdge() {
        G = GraphGenerator(5, 10, SEED);
        assertAll("chacing group",
                () -> assertTrue(G.hasEdge(3, 2)),
                () -> assertTrue(G.hasEdge(4, 2)),
                () -> assertTrue(G.hasEdge(3, 4)),
                () -> assertTrue(G.hasEdge(4, 1)),
                () -> G.removeEdge(4, 1),
                () -> assertFalse(G.hasEdge(4, 1))
        );

    }

    @Test
    void getEdge() {
        G = GraphGenerator(5, 10, SEED);
        assertEquals(2, G.getEdge(2, 4));
        assertEquals(2, G.getEdge(4, 2));
        assertEquals(0, G.getEdge(2, 2));
        assertEquals(-1, G.getEdge(2, 22), "22 is not a node on this graph");
    }

    @Test
    void nodeSize() {
        WGraph_DS G1 = new WGraph_DS();
        assertEquals(0, G1.nodeSize());
        G = GraphGenerator(5, 10, SEED);
        assertEquals(5, G.nodeSize());
        G.removeNode(1);
        assertEquals(4, G.nodeSize());
        G.addNode(1);
        assertEquals(5, G.nodeSize());
    }

    @Test
    void edgeSize() {
        WGraph_DS G1 = new WGraph_DS();
        assertEquals(0, G1.edgeSize());
        G = GraphGenerator(5, 10, SEED);
        assertEquals(10, G.edgeSize());
        G.removeNode(1);

    }

    @Test
    void testEquals() {
        G = GraphGenerator(10, 20, SEED);
        WGraph_Algo ga = new WGraph_Algo();
        WGraph_Algo gc = new WGraph_Algo();
        ga.init(G);
        gc.init(ga.copy());
        assertEquals(ga,gc);
        ga.getGraph().removeNode(5);
        assertNotEquals(ga,gc);

    }



    @org.junit.Test(timeout = 10)

    @Test
    void time() {
        rnd = new Random(SEED);
        int i = 0;
        int tt =5;
        int jump = 1000;
        int no = 1000000, ed = 1000000;
        WGraph_DS G = GraphGenerator(no, ed, SEED);
        weighted_graph_algorithms ga = new WGraph_Algo();
        ga.init(G);


        assertEquals( 1., 1.,0.0001);
//        b = ga.isConnected();
//        //  System.out.println(g);
//        //System.out.println(""+i+") "+g.nodeSize()+"  con: "+b);
       // assertEquals( b, false,"G");
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
           // System.out.println(G.edgeSize() + " " + v + " " + w +" "+ (t) );

        }
        return G;
    }
}