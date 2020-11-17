import java.util.*;

public class WGraph_DS implements weighted_graph , java.io.Serializable {


    private int MC;
    private HashMap<Integer, node_info> g;
    private HashMap<Integer, HashMap<Integer, Double>> edges;
    private int edge_size;


    public WGraph_DS() {
        g = new HashMap<Integer, node_info>();
        edges = new HashMap<Integer, HashMap<Integer, Double>>();
        MC++;
    }

    @Override
    public node_info getNode(int key) {
        return g.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        if(!edges.containsKey(node1) || !edges.containsKey(node2)) return false;
        return (edges.get(node1).containsKey(node2) &&
                edges.get(node2).containsKey(node1));

    }

    @Override
    public double getEdge(int node1, int node2) {
        if(!g.containsKey(node1)||!g.containsKey(node2)) return -1;
        if(node1 == node2) return 0;
        if (!this.hasEdge(node1, node2)) return -1;
        if (edges.get(node1).containsKey(node2)) return edges.get(node1).get(node2);
        else return edges.get(node2).get(node1);

    }

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

    @Override
    public void connect(int node1, int node2, double w) {
        if (g.containsKey(node1) && g.containsKey(node2)&&node1 != node2 ) {
               if(!hasEdge(node1, node2)) edge_size++;
                HashMap<Integer, Double> t1 = edges.get(node1);
                HashMap<Integer, Double> t2 = edges.get(node2);
                t1.put(node2, w);
                t2.put(node1, w);
                edges.put(node1, t1);
                edges.put(node2, t2);
                MC++;

        }
    }

    @Override
    public Collection<node_info> getV() {
        if(g == null)return null;
        return g.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        if(!g.containsKey(node_id)||g.get(node_id)==null) return null;
        Collection<node_info> c = new ArrayList<>();
        Iterator<Integer> it = edges.get(node_id).keySet().iterator();
        while (it.hasNext()) {
            c.add(g.get(it.next()));
        }
        return c;

    }

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

    @Override
    public void removeEdge(int node1, int node2) {
    if(hasEdge(node1, node2)){
        edges.get(node1).remove(node2);
        edges.get(node2).remove(node1);
        edge_size--;
        MC++;
    }
    }

    @Override
    public int nodeSize() {
        return g.size();
    }

    @Override
    public int edgeSize() {
        return edge_size;
    }

    @Override
    public int getMC() {
        return this.MC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        if (this.nodeSize()!= wGraph_ds.nodeSize())return false;
        if(this.edge_size != wGraph_ds.edge_size) return false;
        Iterator node1 = g.keySet().iterator();
        while (node1.hasNext()){
            int nodeKey = (int)node1.next();
            if(!wGraph_ds.g.containsKey(nodeKey)) return false;
            Iterator node1Nei = edges.get(nodeKey).keySet().iterator();
            while (node1Nei.hasNext()){
                int nodeNeiKey = (int)node1Nei.next();
                if(!wGraph_ds.edges.get(nodeKey).containsKey(nodeNeiKey))return false;
                //if((double)wGraph_ds.edges.get(nodeKey).get(nodeNeiKey) != (double)this.edges.get(nodeKey).get(nodeNeiKey) )return false;
            }
        }


        return true;

    }


    //***************************************************
    private class Nodeinfo implements node_info , Comparable<node_info> , java.io.Serializable {

        private int key;
        private String Info;
        private double Tag;

        public Nodeinfo(node_info a) {
            this.key = a.getKey();
            this.Info = a.getInfo();
            this.Tag = a.getTag();
        }


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

        @Override
        public int compareTo(node_info o) {
            if(this.Tag < o.getTag() ) return -1;
            if(this.Tag > o.getTag() ) return 1;
            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            node_info nodeinfo = (Nodeinfo) o;
            return key == nodeinfo.getKey();
        }

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