import java.util.*;

public class WGraph_DS implements weighted_graph , java.io.Serializable {


    private int MC;
    private HashMap<Integer, node_info> g;
    private HashMap<Integer, HashMap<Integer, Edge>> edges;
    private int edge_size;


    public WGraph_DS() {
        g = new HashMap<Integer, node_info>();
        edges = new HashMap<Integer, HashMap<Integer, Edge>>();
        MC++;
    }

    @Override
    public node_info getNode(int key) {
        return g.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        if(!edges.containsKey(node1) || !edges.containsKey(node2)) return false;
        return (edges.get(node1).containsKey(node2) ||
                edges.get(node2).containsKey(node1));

    }

    @Override
    public double getEdge(int node1, int node2) {
        if(node1 == node2) return 0;
        if (!this.hasEdge(node1, node2)) return -1;
        if (edges.get(node1).containsKey(node2)) return edges.get(node1).get(node2).getWeight();
        else return edges.get(node2).get(node1).getWeight();

    }

    @Override
    public void addNode(int key) {
        if (!g.containsKey(key)) {
            node_info temp = new Nodeinfo(key);
            HashMap<Integer, Edge> t1 = new HashMap<Integer, Edge>();
            edges.put(key, t1);
            g.put(key, temp);
            MC++;
        }

    }

    @Override
    public void connect(int node1, int node2, double w) {
        if (g.get(node1) != null && g.get(node2) != null ) {
            if (!hasEdge(node1, node2) && (node1 != node2)) {
                Edge temp1 = new Edge(g.get(node1), g.get(node2), w);
                Edge temp2 = new Edge(g.get(node2), g.get(node1), w);
                HashMap<Integer, Edge> t1 = edges.get(node1);
                HashMap<Integer, Edge> t2 = edges.get(node2);
                
                t1.put(node2, temp1);
                t2.put(node1, temp2);
                edges.put(node1, t1);
                edges.put(node2, t2);
                MC++;
                edge_size++;
            }
        }
    }

    @Override
    public Collection<node_info> getV() {
        return g.values();
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        if(!g.containsKey(node_id)) return null;
        Collection<node_info> c = new ArrayList<>();
        Iterator<Integer> it = edges.get(node_id).keySet().iterator();
        while (it.hasNext()) {
            c.add(edges.get(node_id).get(it.next()).getDest());
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


    //***************************************************
    private class Nodeinfo implements node_info , Comparable<node_info> {

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

    private class Edge implements Comparable<Edge> {
        private int key;
        private node_info src;
        private node_info dest;
        private double weight;

        public Edge(node_info src, node_info dest, double weight) {
            this.key = src.getKey();
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        public node_info getSrc() {
            return src;
        }

        public void setSrc(node_info src) {
            this.src = src;
        }

        public node_info getDest() {
            return dest;
        }

        public void setDest(node_info dest) {
            this.dest = dest;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public int getkey() {
            return this.key;
        }



        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge edge = (Edge) o;
            return Double.compare(edge.weight, weight) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(weight);
        }

        @Override
        public String toString() {
            return " {" + key +"="+
                    "{" + src.getKey() +
                    "," + dest.getKey() +
                    "}" +", weight=" + weight +
                    '}';
        }

        @Override
        public int compareTo(Edge o) {
            if( weight< o.weight) return -1;
            if(weight> o.weight) return 1;
            return 0;
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