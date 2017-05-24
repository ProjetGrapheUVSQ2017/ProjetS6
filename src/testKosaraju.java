
import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Date 10/01/2014
 * @author Tushar Roy
 *
 * Given a directed graph, find all strongly connected components in this graph.
 * We are going to use Kosaraju's algorithm to find strongly connected component.
 *
 * Algorithm
 * Create a order of vertices by finish time in decreasing order.
 * Reverse the graph
 * Do a DFS on reverse graph by finish time of vertex and created strongly connected
 * components.
 *
 * Runtime complexity - O(V + E)
 * Space complexity - O(V)
 *
 * References
 * https://en.wikipedia.org/wiki/Strongly_connected_component
 * http://www.geeksforgeeks.org/strongly-connected-components/
 */
public class testKosaraju {

    public List<Set<Sommet>> scc(Graphe graph) {

    	 for (int i=0; i < graph.getNbSommets();i++){
         	System.out.println(graph.getSommet(i).getId());
         }
        //it holds vertices by finish time in reverse order.
        Deque<Sommet> stack = new ArrayDeque<>();
        //holds visited vertices for DFS.
        Set<Sommet> visited = new HashSet<>();

        //populate stack with vertices with vertex finishing last at the top.
        for (Sommet vertex : graph.get_liste_de_sommet()) {
        	//System.out.println("On regarde le sommet :"+vertex.getId());
            if (visited.contains(vertex)) {
                continue;
            }
            DFSUtil(vertex, visited, stack, graph);
        }


        //reverse the graph.
        Graphe reverseGraph = reverseGraph(graph);
       
        //Do a DFS based off vertex finish time in decreasing order on reverse graph..
        visited.clear();
        List<Set<Sommet>> result = new ArrayList<>();
        
        while (!stack.isEmpty()) {
        	Sommet vertex = reverseGraph.getSommet(stack.poll().getId());
        	System.out.println("dernier elem de la pile :"+vertex.getId());
            if(visited.contains(vertex)){
                continue;
            }
            Set<Sommet> set = new HashSet<>();
            DFSUtilForReverseGraph(vertex, visited, set, reverseGraph);
            	System.out.println("Taille de la liste :"+set.size());
            
            result.add(set);
        }
        
        return result;
    }

    private Graphe reverseGraph(Graphe graph) {
        Graphe reverseGraph = new GrapheListe();
        for (Sommet s : graph.get_liste_de_sommet()){
        	reverseGraph.addSommet(s);
        }
        for (Arc edge : graph.get_liste_arc()) {
            reverseGraph.addArc(edge.getSommetArrivee(), edge.getSommetDepart());
        }
        return reverseGraph;
    }

    private void DFSUtil(Sommet vertex,Set<Sommet> visited, Deque<Sommet> stack, Graphe graph) {
        visited.add(vertex);
        for (Sommet v : liste_voisins_fils(vertex, graph)) {
        	//System.out.println("Le sommet" + vertex.getId() + "a comme fils" + v.getId() );
            if (visited.contains(v)) {
                continue;
            }
            DFSUtil(v, visited, stack, graph);
        }
        stack.offerFirst(vertex);
        //System.out.println("On a stocké dans la pile :"+vertex.getId());
    }

    private void DFSUtilForReverseGraph(Sommet vertex, Set<Sommet> visited, Set<Sommet> set, Graphe graph) {
        visited.add(vertex);
        set.add(vertex);
        for (Sommet v : liste_voisins_fils(vertex, graph)) {
        	System.out.println("Le sommet" + vertex.getId() + "a comme fils" + v.getId() );
            if (visited.contains(v)) {
                continue;
            }
            DFSUtilForReverseGraph(v, visited, set, graph);
        }
    }

    public static void main(String args[]){
        Graphe graphe = new GrapheListe();
        graphe.addSommet(new Point(50,50));
        graphe.addSommet(new Point(250,50));
        graphe.addSommet(new Point(50,350));
        graphe.addSommet(new Point(250,350));
        graphe.addSommet(new Point(300,400));
        graphe.addSommet(new Point(200,400));
        
        graphe.addArc(graphe.getSommet(0),graphe.getSommet(1));
        graphe.addArc(graphe.getSommet(1),graphe.getSommet(2));
        graphe.addArc(graphe.getSommet(2),graphe.getSommet(0));
        graphe.addArc(graphe.getSommet(2),graphe.getSommet(3));
        graphe.addArc(graphe.getSommet(3),graphe.getSommet(4));
        graphe.addArc(graphe.getSommet(4),graphe.getSommet(5));
        graphe.addArc(graphe.getSommet(5),graphe.getSommet(3));
  
        testKosaraju scc = new testKosaraju();
        List<Set<Sommet>> result = scc.scc(graphe);

        System.out.println("prout");
        //print the result
        result.forEach(set -> {
            set.forEach(v -> System.out.print(v.getId() + " "));
            System.out.println();
        });
    }
    
	public ArrayList<Sommet> liste_voisins_fils(Sommet s, Graphe graph) {
		ArrayList<Sommet> res = new ArrayList<Sommet>();
		for(Arc act : graph.get_liste_arc()){
		 if(act.getSommetDepart().equals(s)){
				if(!res.contains(act.getSommetArrivee())){
					res.add(act.getSommetArrivee());
				}
			}
		}
		return res;
	}
}
