package hw4;

import java.util.Iterator;

/**
 * GraphWrapper is a mutable class and used by client to control the graph.
 * All edges and nodes are contained in Graph G.
 *
 *
 * Representation Field:
 * 	A private variable G of type Graph.(Graph is a mutable class)
 * 
 * Abstraction Function: 
 *  GraphWrapper represents a labeled multigraph containing directed edges and nodes. 
 *  
 * Representation Invariant:
 * 	No duplicate edges(no two edges have same start nodes, same end nodes, and same labels)
 *  No duplicate nodes(no two nodes have same string value)
 */
public class GraphWrapper<vertex, label>
{
	/** A Graph object created for the client */
	private Graph<vertex, label> G;

	/**
     * @effects Constructs a new Graph with no edges and nodes.
     */
	public GraphWrapper()
	{
		this.G= new Graph<vertex, label>();
	}
	
	/**
	@param: a node with string value
	@requires: the parameter should not be null
	@modifies: this.Nodes
	@effects: add a node a to the graph if no duplicates in the graph
	@returns: None
	*/
	public void addNode(vertex node)
	{
		G.addNode(node);
	}
	
	/**
	@param: start node, end node with string values, a label with string value
	@requires: the parameter should not be null
	@modifies: this.G
	@effects: add an edge and potentially two nodes a to the graph
	@returns: None
	*/
	public void addEdge(vertex start, vertex end, label label)
	{
		G.addEdge(start, end, label);
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
		return G.listNodes();
	}
	
	/**
	@param: Parent node with string value
	@requires: None
	@modifies: None
	@effects: None
	@returns: An iterator of all nodes starting from parent in the graph in sorted order if parent
			  is a start node in the graph, return an iterator of an empty hash set otherwise.
	*/ 
	public Iterator<String> listChildren(vertex parent)
	{
		return G.childNode(parent);
	}
} 
