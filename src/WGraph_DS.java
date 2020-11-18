import java.util.*;

/**
 * this class represent undirectional weighted graph and implement weighted_graph interface.
 *
 */


public class WGraph_DS implements weighted_graph, java.io.Serializable {

    private int MC;
    private HashMap<Integer, node_info> g;
    private HashMap<Integer, HashMap<Integer, Double>> edges;
    private int edge_size;

    /**
     * default constructor.
     * crate new HaseMap-g that represent the graph himself,
     * new HaseMap-edges that contains Integer and another HaseMap.
     * "edges" works as follow: the key is the node himself and the value is HseMap that contain his
     * neighbor as key and the weight as value.
     */
    public WGraph_DS() {
        g = new HashMap<Integer, node_info>();
        edges = new HashMap<Integer, HashMap<Integer, Double>>();
        MC=0;
    }

    /**
     * return the node_data by the node_id,
     *
     * @param key - the node_id
     * @return
     */
    @Override
    public node_info getNode(int key) {
        return g.get(key);
    }

    /**
     * return true if and only if node2 appears in node1 edges hashmap and vice versa.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (!edges.containsKey(node1) || !edges.containsKey(node2)) return false;
        return (edges.get(node1).containsKey(node2) &&
                edges.get(node2).containsKey(node1));

    }

    /**
     * return the weight between node1 and node2,
     * if they doesn't exist return -1.
     * if node1=node2 return 0.
     * else return the weight by getting node1 edges hashmap and return the value of node 2.
     *
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (!g.containsKey(node1) || !g.containsKey(node2)) return -1;
        if (node1 == node2) return 0;
        if (!this.hasEdge(node1, node2)) return -1;
        else return edges.get(node1).get(node2);

    }

    /**
     * add new node to the graph by create new node with the given key,
     * if there is already a node with such a key no action be performed.
     * every node created with new edges HashMap that represent his neighbors and the wights.
     *
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (!g.containsKey(key)) {
            node_info temp = new Nodeinfo(key);
            HashMap<Integer, Double> t1 = new HashMap<Integer, Double>();
            edges.put(key, t1);
            g.put(key, temp);
            MC++;
        }

    }

    /**
     * this method connect node1 and node2 with weight 'w',
     * first checking if w>=0 else throw exception.
     * then if node1 and node2 is already connected make sure that edge_size doesn't increase.
     * than take node1 neighbors Hashmap and put inside
     * node2(as Key) with the weight(as Value) and vice versa.
     *
     * @param node1
     * @param node2
     * @param w
     */

    @Override
    public void connect(int node1, int node2, double w) {
        if (w < 0) throw new IllegalArgumentException("weight need to be bigger then 0");
        if (g.containsKey(node1) && g.containsKey(node2) && node1 != node2) {
            if (!hasEdge(node1, node2)) {
                edge_size++;
            }
            if(hasEdge(node1, node2) && getEdge(node1, node2)==w)MC--;
            HashMap<Integer, Double> t1 = edges.get(node1);
            HashMap<Integer, Double> t2 = edges.get(node2);
            t1.put(node2, w);
            t2.put(node1, w);
            edges.put(node1, t1);
            edges.put(node2, t2);
            MC++;

        }
    }

    /**
     * return a pointer to the collection that represent all the node in the graph.
     * simply use values method.
     *
     * @return
     */

    @Override
    public Collection<node_info> getV() {
        if (g == null) return null;
        return g.values();
    }

    /**
     * return the collection that represent all node_id neighbors by iterate throw all of them
     * and add them to new HaseSet.
     *
     * @param node_id
     * @return
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if (!g.containsKey(node_id) || g.get(node_id) == null) return null;
        Collection<node_info> c = new HashSet<>();
        Iterator<Integer> it = edges.get(node_id).keySet().iterator();
        while (it.hasNext()) {
            c.add(g.get(it.next()));
        }
        return c;

    }

    /**
     * remove node and all it's edges by iterate trow all it's edge hashmap and remove every
     * neighbors.
     * for every remove decrease edge size by 1.
     * at the end remove the node from the graph hashmap and return the deleted node.
     *
     * @param key
     * @return
     */
    @Override
    public node_info removeNode(int key) {
        node_info b = g.get(key);
        if (g.get(key) == null) return b;
        Iterator<Integer> it = edges.get(key).keySet().iterator();
        while (it.hasNext()) {
            edges.get(it.next()).remove(key);
            edge_size--;
        }
        edges.get(key).clear();
        g.remove(key);
        MC++;
        return b;
    }

    /**
     * remove edge between node1 and node2 by remove node2 from node1 edges hashmap and vice versa.
     * decrease edge size by 1.
     * @param node1
     * @param node2
     */

    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            edges.get(node1).remove(node2);
            edges.get(node2).remove(node1);
            edge_size--;
            MC++;
        }
    }

    /**
     * return the graph node size.
     * @return
     */
    @Override
    public int nodeSize() {
        return g.size();
    }

    /**
     * return the graph edge size.
     * @return
     */
    @Override
    public int edgeSize() {
        return edge_size;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * @return
     */
    @Override
    public int getMC() {
        return this.MC;
    }

    /**
     * checking if 2 graphs are equals by iterate throw all the node in the graph and for each node
     * iterate throw its neighbors if the node sizes in the graphs are the same and this graph contains
     * all the other nodes the nodes are equals and it's enough for directional inclusion,
     * same for edges.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        if (this.nodeSize() != wGraph_ds.nodeSize()) return false;
        if (this.edge_size != wGraph_ds.edge_size) return false;
        Iterator node1 = g.keySet().iterator();
        while (node1.hasNext()) {
            int nodeKey = (int) node1.next();
            if (!wGraph_ds.g.containsKey(nodeKey)) return false;
            Iterator node1Nei = edges.get(nodeKey).keySet().iterator();
            while (node1Nei.hasNext()) {
                int nodeNeiKey = (int) node1Nei.next();
                if (!wGraph_ds.edges.get(nodeKey).containsKey(nodeNeiKey)) return false;
            }
        }


        return true;

    }


    //***************************************************

    /**
     * this inner class represent simple node.
     * every node contain unique key, tag and a string that contains the node info.
     * implement node_info interface.
     */
    private class Nodeinfo implements node_info, Comparable<node_info>, java.io.Serializable {

        private int key;
        private String Info;
        private double Tag;

        /**
         * simple constructor.
         * construct node by key.
         * @param key
         */
        public Nodeinfo(int key) {
            this.key = key;
            this.Tag = -1;

        }


        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.Info;
        }

        @Override
        public void setInfo(String s) {
            this.Info = s;
        }

        @Override
        public double getTag() {
            return this.Tag;
        }

        @Override
        public void setTag(double t) {
            this.Tag = t;
        }

        @Override
        public String toString() {
            return "{" + " key=" + key +
                    " Tag=" + Tag +
                    ", Info='" + Info +
                    " }";
        }

        /**
         * compare nodes by their tag.
         * @param o
         * @return
         */
        @Override
        public int compareTo(node_info o) {
            if (this.Tag < o.getTag()) return -1;
            if (this.Tag > o.getTag()) return 1;
            return 0;
        }

        /**
         * checking if nodes are equals by this key,
         * (the graph cannot contain 2 nodes with the same key)
         *
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            node_info nodeinfo = (Nodeinfo) o;
            return key == nodeinfo.getKey();
        }

        /**
         * return hashCode for node by key.
         * @return
         */
        @Override
        public int hashCode() {
            return Objects.hash(key);
        }
    }


    @Override
    public String toString() {
        return
                "MC=" + MC +
                        ", g=" + g +
                        "\n" +
                        '}';
    }

}