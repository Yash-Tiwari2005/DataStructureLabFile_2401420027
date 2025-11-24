/*
ENCS205 - Data Structures
Assignment 03: Implementation of Trees and Graph Algorithms for Campus Navigation and Data Management
Language: Java (single-file implementation)

Deliverable: ENCS205 Java implementation covering:
- Building ADT
- Binary Search Tree (BST) with insert/search/inorder/preorder/postorder
- AVL Tree (with LL, RR, LR, RL rotations) and height comparison
- Graph representation: adjacency list and adjacency matrix
- BFS and DFS traversals
- Dijkstra's shortest path
- Kruskal's minimum spanning tree (using Union-Find)
- Expression tree evaluation (simple arithmetic)

Compile: javac ENCS205_Assignment03_Campus_Navigation_Planner.java
Run: java ENCS205_Assignment03_Campus_Navigation_Planner

Note: Adapt and modularize into multiple files if required by your submission format.
*/

import java.util.*;

public class Assignment3 {

    // -------------------- Building ADT --------------------
    static class Building {
        int id;
        String name;
        String details;
        public Building(int id, String name, String details) {
            this.id = id;
            this.name = name;
            this.details = details;
        }
        public String toString() {
            return "(ID:" + id + ", " + name + ")";
        }
    }

    // -------------------- BST --------------------
    static class BSTNode {
        Building b;
        BSTNode left, right;
        BSTNode(Building b) { this.b = b; }
    }
    static class BinarySearchTree {
        BSTNode root;
        public void insert(Building b) { root = insertRec(root, b); }
        private BSTNode insertRec(BSTNode node, Building b) {
            if (node == null) return new BSTNode(b);
            if (b.id < node.b.id) node.left = insertRec(node.left, b);
            else if (b.id > node.b.id) node.right = insertRec(node.right, b);
            else node.b = b; // update
            return node;
        }
        public Building search(int id) {
            BSTNode cur = root;
            while (cur != null) {
                if (id == cur.b.id) return cur.b;
                cur = id < cur.b.id ? cur.left : cur.right;
            }
            return null;
        }
        public List<Building> inorder() { List<Building> res = new ArrayList<>(); inorderRec(root, res); return res; }
        private void inorderRec(BSTNode n, List<Building> r) {
            if (n == null) return; inorderRec(n.left, r); r.add(n.b); inorderRec(n.right, r);
        }
        public List<Building> preorder() { List<Building> res = new ArrayList<>(); preorderRec(root, res); return res; }
        private void preorderRec(BSTNode n, List<Building> r) {
            if (n == null) return; r.add(n.b); preorderRec(n.left, r); preorderRec(n.right, r);
        }
        public List<Building> postorder() { List<Building> res = new ArrayList<>(); postorderRec(root, res); return res; }
        private void postorderRec(BSTNode n, List<Building> r) {
            if (n == null) return; postorderRec(n.left, r); postorderRec(n.right, r); r.add(n.b);
        }
        public int height() { return heightRec(root); }
        private int heightRec(BSTNode n) { if (n == null) return 0; return 1 + Math.max(heightRec(n.left), heightRec(n.right)); }
    }

    // -------------------- AVL Tree --------------------
    static class AVLNode {
        Building b; AVLNode left, right; int height;
        AVLNode(Building b) { this.b = b; height = 1; }
    }
    static class AVLTree {
        AVLNode root;
        public void insert(Building b) { root = insertRec(root, b); }
        private AVLNode insertRec(AVLNode node, Building b) {
            if (node == null) return new AVLNode(b);
            if (b.id < node.b.id) node.left = insertRec(node.left, b);
            else if (b.id > node.b.id) node.right = insertRec(node.right, b);
            else { node.b = b; return node; }
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
            int balance = getBalance(node);
            // LL
            if (balance > 1 && b.id < node.left.b.id) return rightRotate(node);
            // RR
            if (balance < -1 && b.id > node.right.b.id) return leftRotate(node);
            // LR
            if (balance > 1 && b.id > node.left.b.id) { node.left = leftRotate(node.left); return rightRotate(node); }
            // RL
            if (balance < -1 && b.id < node.right.b.id) { node.right = rightRotate(node.right); return leftRotate(node); }
            return node;
        }
        private int getHeight(AVLNode n) { return n==null?0:n.height; }
        private int getBalance(AVLNode n) { return n==null?0:getHeight(n.left)-getHeight(n.right); }
        private AVLNode rightRotate(AVLNode y) {
            AVLNode x = y.left; AVLNode T2 = x.right; x.right = y; y.left = T2; y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right)); x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right)); return x;
        }
        private AVLNode leftRotate(AVLNode x) {
            AVLNode y = x.right; AVLNode T2 = y.left; y.left = x; x.right = T2; x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right)); y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right)); return y;
        }
        public List<Building> inorder() { List<Building> res = new ArrayList<>(); inorderRec(root, res); return res; }
        private void inorderRec(AVLNode n, List<Building> r) { if (n==null) return; inorderRec(n.left,r); r.add(n.b); inorderRec(n.right,r); }
        public int height() { return getHeight(root); }
    }

    // -------------------- Graph --------------------
    static class Edge {
        int u, v; double w;
        Edge(int u, int v, double w) { this.u = u; this.v = v; this.w = w; }
        public String toString() { return String.format("(%d-%d: %.2f)", u, v, w); }
    }
    static class CampusGraph {
        Map<Integer, Building> nodes = new HashMap<>();
        Map<Integer, List<Edge>> adj = new HashMap<>();
        boolean directed;
        CampusGraph(boolean directed) { this.directed = directed; }
        void addBuilding(Building b) { nodes.put(b.id, b); adj.putIfAbsent(b.id, new ArrayList<>()); }
        void addEdge(int u, int v, double w) {
            adj.putIfAbsent(u, new ArrayList<>());
            adj.putIfAbsent(v, new ArrayList<>());
            adj.get(u).add(new Edge(u,v,w));
            if (!directed) adj.get(v).add(new Edge(v,u,w));
        }
        // adjacency matrix
        double[][] adjacencyMatrix() {
            List<Integer> ids = new ArrayList<>(nodes.keySet()); Collections.sort(ids);
            int n = ids.size(); double INF = Double.POSITIVE_INFINITY;
            double[][] mat = new double[n][n]; for (int i=0;i<n;i++) for (int j=0;j<n;j++) mat[i][j] = (i==j?0:INF);
            Map<Integer,Integer> idx = new HashMap<>(); for (int i=0;i<n;i++) idx.put(ids.get(i), i);
            for (int u: adj.keySet()) for (Edge e: adj.get(u)) { mat[idx.get(u)][idx.get(e.v)] = e.w; }
            return mat;
        }
        // BFS
        List<Integer> bfs(int start) {
            List<Integer> order = new ArrayList<>(); Set<Integer> vis = new HashSet<>(); Queue<Integer> q = new LinkedList<>();
            vis.add(start); q.add(start);
            while (!q.isEmpty()) {
                int u = q.poll(); order.add(u);
                for (Edge e: adj.getOrDefault(u, Collections.emptyList())) if (!vis.contains(e.v)) { vis.add(e.v); q.add(e.v); }
            }
            return order;
        }
        // DFS
        List<Integer> dfs(int start) {
            List<Integer> order = new ArrayList<>(); Set<Integer> vis = new HashSet<>(); dfsRec(start, vis, order); return order;
        }
        void dfsRec(int u, Set<Integer> vis, List<Integer> order) {
            vis.add(u); order.add(u);
            for (Edge e: adj.getOrDefault(u, Collections.emptyList())) if (!vis.contains(e.v)) dfsRec(e.v, vis, order);
        }
        // Dijkstra
        Map<Integer, Double> dijkstra(int src) {
            Map<Integer, Double> dist = new HashMap<>(); for (int id: nodes.keySet()) dist.put(id, Double.POSITIVE_INFINITY);
            dist.put(src, 0.0);
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));
            pq.add(new int[]{src,0}); // store node, dummy
            while (!pq.isEmpty()) {
                int[] top = pq.poll(); int u = top[0]; double d = dist.get(u);
                for (Edge e: adj.getOrDefault(u, Collections.emptyList())) {
                    if (dist.get(e.v) > d + e.w) {
                        dist.put(e.v, d + e.w);
                        pq.add(new int[]{e.v,0});
                    }
                }
            }
            return dist;
        }
        // Kruskal's MST
        List<Edge> kruskalMST() {
            List<Edge> all = new ArrayList<>(); for (List<Edge> lst: adj.values()) all.addAll(lst);
            // for undirected, remove duplicates (u-v and v-u)
            if (!directed) {
                Set<String> seen = new HashSet<>(); List<Edge> filtered = new ArrayList<>();
                for (Edge e: all) {
                    String key = Math.min(e.u,e.v) + "-" + Math.max(e.u,e.v);
                    if (!seen.contains(key)) { seen.add(key); filtered.add(e); }
                }
                all = filtered;
            }
            all.sort(Comparator.comparingDouble(a -> a.w));
            UnionFind uf = new UnionFind(); for (int id: nodes.keySet()) uf.makeSet(id);
            List<Edge> mst = new ArrayList<>();
            for (Edge e: all) {
                if (uf.find(e.u) != uf.find(e.v)) { uf.union(e.u, e.v); mst.add(e); }
            }
            return mst;
        }
    }

    // -------------------- Union-Find --------------------
    static class UnionFind {
        Map<Integer,Integer> parent = new HashMap<>(); Map<Integer,Integer> rank = new HashMap<>();
        void makeSet(int x) { parent.put(x,x); rank.put(x,0); }
        int find(int x) { if (parent.get(x)==x) return x; int p = find(parent.get(x)); parent.put(x,p); return p; }
        void union(int x, int y) {
            int rx = find(x), ry = find(y); if (rx==ry) return;
            if (rank.get(rx) < rank.get(ry)) parent.put(rx, ry);
            else if (rank.get(rx) > rank.get(ry)) parent.put(ry, rx);
            else { parent.put(ry, rx); rank.put(rx, rank.get(rx)+1); }
        }
    }

    // -------------------- Expression Tree (evaluate postfix) --------------------
    static class ExprNode { String val; ExprNode left, right; ExprNode(String v){val=v;} }
    static class ExpressionTree {
        // build tree from postfix tokens
        ExprNode buildFromPostfix(String[] tokens) {
            Stack<ExprNode> st = new Stack<>();
            for (String t : tokens) {
                if (isOperator(t)) {
                    ExprNode r = st.pop(); ExprNode l = st.pop(); ExprNode node = new ExprNode(t); node.left = l; node.right = r; st.push(node);
                } else st.push(new ExprNode(t));
            }
            return st.isEmpty()?null:st.peek();
        }
        boolean isOperator(String s) { return s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/"); }
        double evaluate(ExprNode node) {
            if (node == null) return 0;
            if (!isOperator(node.val)) return Double.parseDouble(node.val);
            double l = evaluate(node.left); double r = evaluate(node.right);
            switch (node.val) {
                case "+": return l + r;
                case "-": return l - r;
                case "*": return l * r;
                case "/": return l / r;
            }
            return 0;
        }
    }

    // -------------------- Demonstration / main --------------------
    public static void main(String[] args) {
        // Sample buildings
        Building b1 = new Building(10, "CS Block", "Computer Science Dept");
        Building b2 = new Building(5, "Library", "Central Library");
        Building b3 = new Building(15, "Admin", "Administration");
        Building b4 = new Building(2, "Cafeteria", "Food Court");
        Building b5 = new Building(7, "Hostel A", "Boys Hostel");

        // BST
        BinarySearchTree bst = new BinarySearchTree();
        for (Building b: Arrays.asList(b1,b2,b3,b4,b5)) bst.insert(b);
        System.out.println("BST Inorder: " + bst.inorder());
        System.out.println("BST Preorder: " + bst.preorder());
        System.out.println("BST Postorder: " + bst.postorder());
        System.out.println("BST Height: " + bst.height());

        // AVL
        AVLTree avl = new AVLTree();
        for (Building b: Arrays.asList(b1,b2,b3,b4,b5)) avl.insert(b);
        System.out.println("AVL Inorder: " + avl.inorder());
        System.out.println("AVL Height: " + avl.height());

        // Graph
        CampusGraph g = new CampusGraph(false);
        for (Building b: Arrays.asList(b1,b2,b3,b4,b5)) g.addBuilding(b);
        g.addEdge(10,5,4.5); g.addEdge(10,15,6.0); g.addEdge(5,7,3.0); g.addEdge(2,5,2.0); g.addEdge(7,15,5.5);

        System.out.println("Adjacency List:");
        for (int id: g.adj.keySet()) System.out.println(id + " -> " + g.adj.get(id));

        System.out.println("BFS from 10: " + g.bfs(10));
        System.out.println("DFS from 10: " + g.dfs(10));

        // Adjacency Matrix (printed compactly)
        double[][] mat = g.adjacencyMatrix(); System.out.println("Adjacency Matrix:");
        for (double[] row: mat) System.out.println(Arrays.toString(row));

        // Dijkstra from 10
        Map<Integer, Double> dist = g.dijkstra(10);
        System.out.println("Dijkstra distances from 10:");
        for (int id: dist.keySet()) System.out.println(id + ": " + dist.get(id));

        // Kruskal MST
        List<Edge> mst = g.kruskalMST();
        System.out.println("Kruskal MST edges: " + mst);

        // Expression Tree: example energy bill expression: (units * rate) + fixed
        // postfix for (units rate * fixed +) -> units rate * fixed +
        String[] postfix = {"100","5","*","50","+"}; // 100 units * 5 rate + 50 fixed = 550
        ExpressionTree et = new ExpressionTree(); ExprNode root = et.buildFromPostfix(postfix); double val = et.evaluate(root);
        System.out.println("Expression tree evaluation (energy bill): " + val);

        System.out.println("\nDONE. Use this file as the base for your GitHub submission.\nMake sure to include a report and screenshots as required.");
    }
}
