import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
    private HashMap<String, Node> nodes;
    private List<Creature> creatureList;

    public Graph() {
        nodes = new HashMap<String, Node>();
        creatureList= new ArrayList<Creature>();
    }

    public void addNode(String n, String description) {
        Node node = new Node(n, description);
        nodes.put(n, node);
    }

    public void addDirectedEdge(String name1, String name2) {
        Node n1 = getNode(name1);
        Node n2 = getNode(name2);
        n1.addNeighbor(n2);
    }

    public void addUndirectedEdge(String name1, String name2) {
        addDirectedEdge(name1, name2);
        addDirectedEdge(name2, name1);
    }

    public void addCreature(Creature c) {
        creatureList.add(c);
    }

    public List<Creature> getCreatures(Node n) {
        List<Creature> output = new ArrayList<Creature>();

        for (Creature c:creatureList) {
            if(c.getLocation().equals(n.getName()))  output.add(c);
        }

        return output;

    }

    public Node getNode(String name) {
        return nodes.get(name);
    }

    public static void findPathToPlayer(ArrayList<Graph.Node> nodesToSearch, ArrayList<Graph.Node> path, Graph.Node startingNode) {
        for (Node node : nodesToSearch) {
            if (node == path.get(0)) {
                path.add(0, node);
                if (node != startingNode) {
                    nodesToSearch.clear();
                    nodesToSearch.add(startingNode);
                    findPathToPlayer(nodesToSearch, path, startingNode);
                } else return;
            }
        }
        for (Node node : nodesToSearch) {
            for (Node newNode: node.getNeighbors()) {
                nodesToSearch.add(newNode);
            }
            nodesToSearch.remove(node);
        }
        findPathToPlayer(nodesToSearch, path, startingNode);
    }

    public class Node {
        private String name;
        private String description;
        private ArrayList<Node> neighbors;
        private ArrayList<Item> items;

        private Node(String name, String description) {
            neighbors = new ArrayList<Node>();
            items = new ArrayList<Item>();
            this.name = name;
            this.description = description;
        }

        private void addNeighbor(Node n) {
            neighbors.add(n);
        }

        public String getNeighborNames() {
            String output = "";
            for (Node n : neighbors) {
                output += n.getName() + " ";
            }

            return output;
        }

        public ArrayList<Node> getNeighbors() {
            return neighbors;
        }

        public Node getNeighbor(String name) {
            for (Node n : neighbors) {
                if (n.getName().equalsIgnoreCase(name)) return n;
            }

            return null;
        }



        public String getName() {
            return name;
        }

        public Item getItem(String name) {
            for (Item i : items) {
                if (i.getName().equalsIgnoreCase(name)) return i;
            }

            return null;
        }

        public ArrayList<Item> getItems() {
            return items;
        }

        public void addItem(Item i) {
            items.add(i);
        }

        public Item removeItem(String name) {
            for (Item i : items) {
                if (i.getName().equalsIgnoreCase(name)) {
                    Item out = i;
                    items.remove(i);
                    return out;
                }
            }

            return null;
        }
    }
}
