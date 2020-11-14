import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {

    private weighted_graph g0;
    private HashMap<node_info, Double> nodeDis;
    private HashMap<node_info, node_info> nodePar;
    private Set<node_info> used;
    private PriorityQueue<node_info> unused;
    private List<node_info> path;




    public WGraph_Algo() {
        this.g0 = new WGraph_DS();
    }

    @Override
    public void init(weighted_graph g) {
        this.g0 = g;

    }

    @Override
    public weighted_graph getGraph() {
        return g0;
    }

    @Override
    public weighted_graph copy() {
        weighted_graph a = new WGraph_DS();
        Iterator<node_info> it = g0.getV().iterator();
        while (it.hasNext()) {
            node_info temp = it.next();
            a.addNode(temp.getKey());
            Iterator<node_info> it1 = g0.getV(temp.getKey()).iterator();
            while (it1.hasNext()) {
                node_info temp1 = it1.next();
                a.addNode(temp1.getKey());
                a.connect(temp.getKey(), temp1.getKey(), g0.getEdge(temp.getKey(), temp1.getKey()));
            }

        }
        return a;
    }


    @Override
    public boolean isConnected() {
        Iterator<node_info> it = g0.getV().iterator();
        if (!it.hasNext()) return true;
        boolean flag = true;

        // reset_nodes();
        Queue<node_info> Q = new LinkedList<>();
        Iterator<node_info> it1 = g0.getV().iterator();
        Q.add(it1.next());
        Q.peek().setTag(Q.peek().getTag() + 1);

        while (!Q.isEmpty()) {
            node_info t3 = Q.poll();
            System.out.println(t3);
            Iterator<node_info> it2 = g0.getV(t3.getKey()).iterator();
            while (it2.hasNext()) {
                node_info t1 = it2.next();
                System.out.println(t1);
                if (t1.getTag() < 0) {
                    t1.setTag(t3.getTag() + 1);
                    Q.add(t1);
                }
            }
        }


        while (it.hasNext()) {
            node_info t8 = it.next();
            //System.out.println(t8);
            if (t8.getTag() == -1) {
                flag = false;
            }
            t8.setTag(-1);
        }

        return flag;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (g0.getNode(src) == null || g0.getNode(dest) == null) return -1;
        used = new HashSet<>();
        unused = new PriorityQueue<>();
        nodeDis = new HashMap<>();
        reset_nodes();
        Dijkstra(src);

        return g0.getNode(dest).getTag();
    }

    @Override
    public List<node_info> shortestPath(int dest, int src) {
        if (g0.getNode(src) == null || g0.getNode(dest) == null) return null;
        used = new HashSet<>();
        unused = new PriorityQueue<>();
        nodeDis = new HashMap<>();
        nodePar = new HashMap<>();
        path = new ArrayList<>();
        reset_nodes();
        Dijkstra(src);
       System.out.println(g0.getNode(dest).getTag());
        if(g0.getNode(dest).getTag()==-1) return path;
        node_info pointerNode = g0.getNode(dest);
        path.add(pointerNode);
        while (pointerNode!= g0.getNode(src)) {
           pointerNode = nodePar.get(pointerNode);
            path.add(pointerNode);
        }

        return path;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }


    public void Dijkstra(int src){


        g0.getNode(src).setTag(0.0);
        nodeDis.put(g0.getNode(src), 0.0);
        unused.add(g0.getNode(src));

        while (!unused.isEmpty()) {

            node_info t = unused.poll();
            //System.out.println("*********op node: " + t.getKey());
            used.add(t);
            Iterator<node_info> Nei = g0.getV(t.getKey()).iterator();
            while (Nei.hasNext()) {
                node_info tempNei = Nei.next();
               // System.out.println("Nei num:" + tempNei.getKey());
                if (!nodeDis.containsKey(tempNei)) {
                    nodeDis.put(tempNei, -1.0);
                }
                double tempNeiDis = nodeDis.get(tempNei); // dis for the Nei we work on.
                double tDis = nodeDis.get(t); // parent dis.
                double EdgeDis = g0.getEdge(t.getKey(), tempNei.getKey()); // the edge.
                if ((tempNeiDis == -1.0 || tempNeiDis > (tDis + EdgeDis)) && !used.contains(tempNei) ) {
                    nodeDis.put(tempNei, tDis + EdgeDis);
                    tempNei.setTag(tDis + EdgeDis);
                    unused.add(tempNei);
                   if (nodePar!=null) {
                       nodePar.put(tempNei, t);

                   }

                }

            }


        }


    }

    private void reset_nodes(){
        Iterator<node_info> it2 = g0.getV().iterator();
        while (it2.hasNext()) {
            node_info temp = it2.next();
            temp.setTag(-1);
        }
    }

}
