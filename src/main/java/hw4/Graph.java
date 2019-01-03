package hw4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Iterator;


/**
 * Graph is a generic mutable class and represents a directed labeled multigraph composed by nodes and edges.
 * The variable type of nodes and edge are all generic type "vertex", labels are generic type "label".(type "vertex" and type "label" must have 
 * "toString" method)
 *
 *
 * Representation Field:
 * 	adj <startnode1, edges>, <startnode2, edges>,... <startnodek, edges>
 * 	Nodes n1, n2, ..., nn
 *  
 *  adj is a hashmap representing an adjacency list set start nodes 
 *  as key and directed edges started from the start node as value.
 *  Nodes is a TreeSet containing all nodes in the graph
 *  
 * 
 * Abstraction Function: 
 *  Graph, g, represents a directed labeled multigraph containing the following terms:
 *  	adj: The key of adj is start node, and the value of adj is directed edges going from the start node
 *  	Nodes: all nodes in the graph
 *  
 * Representation Invariant:
 * 	No duplicate edges(no two edge have same start nodes, same end nodes, and same labels)
 *  No duplicate nodes(no two nodes have same values)
 *  adj[key].getStartNode==key for all key in adj
 *  All start nodes contained in adj are in Nodes
 */

public class Graph<vertex, label> {
	
	/** A hashmap holding all start nodes and edges starting from them */
	HashMap<vertex, HashSet<Edge<vertex, label>>> adj ;
	/** A TreeSet containing all nodes in the graph */
	HashSet<vertex> Nodes;
	
	
	
	private void checkRep() throws RuntimeException
	{
		Iterator<HashMap.Entry<vertex, HashSet<Edge<vertex, label>>>> iter = adj.entrySet().iterator();
		TreeSet<Edge<vertex, label>> allEdges = new TreeSet<Edge<vertex, label>>();
		//iterator over the hash map
	    while (iter.hasNext()) {
	    	HashMap.Entry<vertex, HashSet<Edge<vertex, label>>> pair = iter.next();
	    	//if the start node of the edge is in tree set Nodes
	    	if(!Nodes.contains(pair.getKey()))
	    	{
	    		throw new RuntimeException("start node not exists in Nodes");
	    	}
	    	Iterator<Edge<vertex, label>> iter1 = pair.getValue().iterator();
	        while(iter1.hasNext())
	        {
	        	Edge<vertex, label> edge = (Edge<vertex, label>) iter1.next();
	        	//if the start node of the edge is not the key of the hash map
	        	if(!edge.getStartNode().equals(pair.getKey()))
	        	{
	        		throw new RuntimeException("start node is not the key");
	        	}
	        	//if any two edges are duplicated
	        	if(allEdges.contains(edge))
	        	{
	        		throw new RuntimeException("duplicate edges");
	        	}
	        }
	    }
	}
	
	/**
     * @effects Constructs a new Graph with no edges and nodes.
     */
	public Graph()
	{
		this.adj = new HashMap<vertex, HashSet<Edge<vertex, label>>>();
		this.Nodes = new HashSet<vertex>();
		checkRep();
	}
	
	/**
	@param: a node with string value
	@requires: the parameter should not be null
	@modifies: this.Nodes
	@effects: add a node a to the graph if it is no duplicates in the graph
	@returns: None
	*/
	public void addNode(vertex node)
	{
		Nodes.add(node);
		checkRep();
	}
	
	/**
	@param: start node and end node with string value, and label with string value
	@requires: the parameter should not be null
	@modifies: this.Nodes && this.adj
	@effects: add an edge and potentially two nodes a to the graph
	@returns: None
	*/
	public void addEdge(vertex start, vertex end, label label)
	{
		//add the nodes into the graph if they are not existed
		if(!this.Nodes.contains(start))
		{
			this.Nodes.add(start);
		}
		
		if(!this.Nodes.contains(end))
		{
			this.Nodes.add(end);
		}
		//if the start node is in the adjacent list
		Edge<vertex, label> newEdge = new Edge<vertex, label>(start, end, label);
		if(!this.adj.containsKey(start))
		{
			HashSet<Edge<vertex, label>> Edges = new HashSet<Edge<vertex, label>>();
			Edges.add(newEdge);
			this.adj.put(start, Edges);
		}
		else
		{
			if(!this.adj.get(start).contains(newEdge))
			{
				this.adj.get(start).add(newEdge);
			}
		}
		//checkRep();
	}
	
	/**
	@param: None
	@requires: None
	@modifies: None
	@effects: None
	@returns: A hash set containing all nodes in the graph
	*/
	public TreeSet<vertex> getAllNodes()
	{
		return new TreeSet<vertex>(Nodes);
	}
	
	/**
	@param: A node with string value
	@requires: None
	@modifies: None
	@effects: None
	@returns: A hash set containing all edges starting from parameter node if node is the graph, return
			  an empty hash set if node is not in the graph or no edges start from the node
	*/ 
	public TreeSet<Edge<vertex, label>> getEdges(vertex node)
	{
		if(!this.adj.containsKey(node))
		{
			return new TreeSet<Edge<vertex, label>>();
		}
		else
		{
			TreeSet<Edge<vertex, label>> result = new TreeSet<Edge<vertex, label>>();
			result.addAll(this.adj.get(node));
			return result;
		}
	}
	
	/**
	@param: start node node1 with string value, end node node2 with string value
	@requires: None
	@modifies: None
	@effects: None
	@returns: A tree set containing all edges starting from node1 to node2
	*/ 
	public TreeSet<Edge<vertex, label>> getTwoNodeEdges(vertex node1, vertex node2)
	{
		TreeSet<Edge<vertex, label>> result = new TreeSet<Edge<vertex, label>>();
		if(this.adj.containsKey(node1))
		{
			HashSet<Edge<vertex, label>> edges = this.adj.get(node1);
			Iterator<Edge<vertex, label>> iter = edges.iterator();
			while(iter.hasNext())
			{
				Edge<vertex, label> e = iter.next();
				if(e.getEndNode().equals(node2))
				{
					result.add(e);
				}
			}
		}
		return result;
	}
	
	
	/**
	@param: A node with string value
	@requires: None
	@modifies: None
	@effects: None
	@returns: false if node is not in Nodes, true otherwise
	*/ 
	public boolean ContainNode(vertex node)
	{
		return this.Nodes.contains(node);
	}
	
	/**
	@param: None
	@requires: None
	@modifies: None
	@effects: None
	@returns: An iterator of all nodes in the graph in sorted order
	*/ 
	public Iterator<vertex> listNodes()
	{
		//tree set automatically sorts all the nodes
		Iterator<vertex> iter = this.Nodes.iterator();
		TreeSet<vertex> result = new TreeSet<vertex>();
		while(iter.hasNext())
		{
			result.add(iter.next());
		}
		return result.iterator();
	} 
	
	/**
	@param: Parent node with string value
	@requires: None
	@modifies: None
	@effects: None
	@returns: An iterator of all nodes starting from parent in the graph in sorted order if parent
			  is a start node in the graph, return an iterator of an empty hash set otherwise.
	*/ 
	public Iterator<String> childNode(vertex parent)
	{
		//return an iterator of an empty tree set if parent node is not existed
		if(!this.adj.containsKey(parent))
		{
			TreeSet<String> TreeSet = new TreeSet<String>();
			return TreeSet.iterator();
		}
		//modify the format of each child node 
		TreeSet<String> resultNodes = new TreeSet<String>();
		Iterator<Edge<vertex, label>> iter = this.adj.get(parent).iterator();
		while(iter.hasNext())
		{
			Edge<vertex, label> edge = iter.next();
			resultNodes.add(edge.getEndNode().toString()+"("+edge.getLabel().toString()+")");
		}
		return resultNodes.iterator();
	}
	
	/**
	@param: Parent node with string value
	@requires: None
	@modifies: None
	@effects: None
	@returns: An iterator iterating over all child nodes of the parent node in order
	*/ 
	public Iterator<vertex> getNeighbors(vertex parent)
	{
		if(!this.adj.containsKey(parent))
		{
			TreeSet<vertex> TreeSet = new TreeSet<vertex>();
			return TreeSet.iterator();
		}
		TreeSet<vertex> resultNodes = new TreeSet<vertex>();
		Iterator<Edge<vertex, label>> iter = this.adj.get(parent).iterator();
		while(iter.hasNext())
		{
			Edge<vertex, label> edge = iter.next();
			resultNodes.add(edge.getEndNode());
		}
		return resultNodes.iterator();
	}
}
