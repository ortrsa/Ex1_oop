import java.io.*;
import java.util.*;

/**
 * this class contain algorithms and methods that used on Undirected (positive) Weighted Graph.
 * this class implements weighted_graph_algorithms interface and it's methods.
 */
public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph g0;
    private HashMap<node_info, Double> nodeDis;
    private HashMap<node_info, node_info> nodePar;
    private Set<node_info> used;
    private PriorityQueue<node_info> unused;
    private List<node_info> path;

    /**
     * default constructor.
     */
    public WGraph_Algo() {
        this.g0 = new WGraph_DS();
    }

    /**
     * Init the graph which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.g0 = g;
    }

    /**
     * return the graph which this set of algorithms operates on.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return g0;
    }

    /**
     * this method Compute a deep copy of this graph by making new WGraph_DS,
     * iterate throw all its nodes and the node neighbors,
     * use 'addNode' and 'connect' methods to create exactly same graph.
     * @return
     */

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

    /**
     * this method return true if the graph is connected.
     * if the graph is empty return true.
     * we assume that the graph is connected, reset all the nodes tag,
     * take the first node and change the tag of all the node that connected to it
     * to the distance from the first node.
     * after that iterate throw all the node, if there is node with tag -1 flag change to false.
     * return flag at end.
     * @return
     */
    @Override
    public boolean isConnected() {
        Iterator<node_info> it = g0.getV().iterator();
        if (!it.hasNext()) return true;
        boolean flag = true;

        reset_nodes();
        Queue<node_info> Q = new LinkedList<>();
        Iterator<node_info> it1 = g0.getV().iterator();
        Q.add(it1.next());
        Q.peek().setTag(Q.peek().getTag() + 1);

        while (!Q.isEmpty()) {
            node_info t3 = Q.poll();
            Iterator<node_info> it2 = g0.getV(t3.getKey()).iterator();
            while (it2.hasNext()) {
                node_info t1 = it2.next();
                if (t1.getTag() < 0) {
                    t1.setTag(t3.getTag() + 1);
                    Q.add(t1);
                }
            }
        }


        while (it.hasNext()) {
            node_info t8 = it.next();
            if (t8.getTag() == -1) {
                flag = false;
            }
        }

        return flag;
    }

    /**
     * return the shortest path distance by apply 'Dijkstra' with src,
     * the Dijkstra algorithm check the shortest weight from all node to src
     * and save it on the node tag
     * after end Dijkstra algorithm return dest tag.
     *
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */

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

    /**
     * return list that contains the shortest path (by weight) from src to dest.
     * reset all nodes tag,
     * apply 'Dijkstra' algorithm with 'src', if the 'dest' doesn't connect to 'src' return -1.
     * the 'Dijkstra' fill the nodePer HashMap with nodes and it's parents.
     * create pointer that point to the dest node and add it to the 'path' list,
     * while the pointer not equal to 'src' node, pointer = 'nodePar'.
     * reverse path list and return path.
     *
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (g0.getNode(src) == null || g0.getNode(dest) == null) return null;
        used = new HashSet<>();
        unused = new PriorityQueue<>();
        nodeDis = new HashMap<>();
        nodePar = new HashMap<>();
        path = new ArrayList<>();
        reset_nodes();
        Dijkstra(src);
        if (g0.getNode(dest).getTag() == -1) return path;
        node_info pointerNode = g0.getNode(dest);
        path.add(pointerNode);
        while (pointerNode != g0.getNode(src)) {
            pointerNode = nodePar.get(pointerNode);
            path.add(pointerNode);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     *  Saves this graph to the given file name,
     *  this method use the serializable interface to serialize the graph
     *  and save it to file.
     *  if the file save successfully return true.
     * @param file - the file name (may include a relative path).
     * @return
     */
    @Override
    public boolean save(String file) {
        System.out.println("serialize " + file);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(this.g0);

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }
        System.out.println(file + " was saved successfully");
        return true;
    }

    /**
     * Load this graph from the given file name,
     * this method deserialize the graph from the given file
     * and init the graph to this set of algorithm.
     * if the file Load successfully return true.
     * @param file - file name
     * @return
     */
    @Override
    public boolean load(String file) {
        System.out.println("Deserialize " + file);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);

            WGraph_DS loadedFile = (WGraph_DS) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
            this.g0 = loadedFile;
            System.out.println(file + " was loaded successfully");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }


        return false;
    }




    /**
     * Dijkstra algorithm is an algorithm to fined the shortest path between nodes(by weight).
     * this method find the shortest distance by weight from src node to all the node in the graph.
     * this method based on 4 data structure nodeDis(HashMap), unused(PriorityQueue), used(HashSet)
     * and nodePar(HashMap).
     * first add the src node to unused PriorityQueue and set is weight to 0,
     * continue while unused is not empty,
     * iterate throw all it's neighbors and every node that not in the nodeDis HaseMap add with -1 weight,
     * (nodeDis contains node(Key) and his distance from src(Value)),
     * if the node weight is bigger then his father weight + the edge weight or node weight = -1
     * replace the node weight with his father + the edge between the node and his father.
     * (skip this step for the father node).
     * add to unused PriorityQueue and if needed add the father to nodePar HaseMap , same for all neighbors.
     * after that pull the next node from unused, because of the PriorityQueue every next node on the list
     * will be with the lightest weight.
     * after iterate throw all the graph all the nodes will be in the nodeDis Hashmap and contain in the value
     * their min weight from src node.
     * also the nodePar will contain every node and his father when called from shortestPath method.
     *
     *
     * @param src
     */

    public void Dijkstra(int src) {
        g0.getNode(src).setTag(0.0);
        nodeDis.put(g0.getNode(src), 0.0);
        unused.add(g0.getNode(src));

        while (!unused.isEmpty()) {

            node_info t = unused.poll();
            used.add(t);
            Iterator<node_info> Nei = g0.getV(t.getKey()).iterator();
            while (Nei.hasNext()) {
                node_info tempNei = Nei.next();
                if (!nodeDis.containsKey(tempNei)) {
                    nodeDis.put(tempNei, -1.0);
                }
                double tempNeiDis = nodeDis.get(tempNei); // dis for the Nei we work on.
                double tDis = nodeDis.get(t); // parent dis.
                double EdgeDis = g0.getEdge(t.getKey(), tempNei.getKey()); // the edge.
                if ((tempNeiDis == -1.0 || tempNeiDis > (tDis + EdgeDis)) && !used.contains(tempNei)) {
                    nodeDis.put(tempNei, tDis + EdgeDis);
                    tempNei.setTag(tDis + EdgeDis);
                    unused.add(tempNei);
                    if (nodePar != null) {
                        nodePar.put(tempNei, t);

                    }

                }

            }


        }


    }

    /**
     * set all node tag to -1.
     */
    private void reset_nodes() {
        Iterator<node_info> it2 = g0.getV().iterator();
        while (it2.hasNext()) {
            node_info temp = it2.next();
            temp.setTag(-1);
        }
    }

}
