package hw4;


/**
 * Edge is an immutable class and represents an edge connecting a starting node with an ending node. 
 * It is directed and has a label.
 * 
 * Examples of Edge include <"1","2"> representing a edge from node "1" to node "2". 
 * 
 * Abstraction Function: An Edge , e, represents a directed edge going from one node to another with a label.
 * 
 * Representation Invariant: No duplicate edges(same StartNode, EndNode, and Label)
 * 
 * StartNode: A "vertex" type variable representing the start node;(type "variable" should have "toString" method)
 * EndNode: A "vertex" type variable representing the end node;(type "variable" should have "toString" method)
 * Label: A "label" type variable representing the label of the edge.(type "label" should have "toString" method)
 */

public class Edge<vertex, label> implements Comparable<Edge<vertex, label>>{
	
	/** Holds the value of the start node */
	private vertex StartNode;
	/** Holds the value of the end node */
	private vertex EndNode;
	/** Holds the value of the labels */
	private label Label;
	
	
	/**
	 * @param start is the start node with a value of string type, end is the 
	 * 		  end node with a value of string type, label is the label with a value of string type
     * @effects Constructs a new Edge with start node of string value "start", 
     * 			end node of string value "end", and label of string value "label".
     */
	public Edge(vertex start, vertex end, label label)
	{
		this.StartNode = start;
		this.EndNode = end;
		this.Label = label;
	}
	
	/**
     *
     * @return the start node of this edge
     */
	public vertex getStartNode() 
	{
		return this.StartNode;
	}
	
	/**
    *
    * @return the end node of this edge
    */
	public vertex getEndNode()
	{
		return this.EndNode;
	}
	
	/**
    *
    * @return the label of this edge
    */
	public label getLabel()
	{
		return this.Label;
	}

	/**
	 * Method for the use of interface Comparable
    * @param Edge o used to be compared with this
    * @return return 1 if this>o, -1 if this<o, 0 if this==o
    */
	@Override
	public int compareTo(Edge<vertex,label> o) {
		String string1 = this.getStartNode().toString()+this.getEndNode().toString();
		String string2 = o.getStartNode().toString()+o.getEndNode().toString();
		if(string1.compareTo(string2)>0)
		{
			return 1;
		}
		else if(string1.compareTo(string2)<0)
		{
			return -1;
		}
		else
		{
			if(this.getLabel() instanceof Integer)
			{
				return Integer.parseInt( this.getLabel().toString()) - Integer.parseInt(o.getLabel().toString());
			}
			else if(this.getLabel() instanceof Double)
			{
				if(Double.parseDouble( this.getLabel().toString()) - Double.parseDouble(o.getLabel().toString())>0)
				{
					return 1;
				}
				else if(Double.parseDouble( this.getLabel().toString()) - Double.parseDouble(o.getLabel().toString())<0)
				{
					return -1;
				}
				else
				{
					return 0;
				}
			}
			else
			{
				return this.getLabel().toString().compareTo(o.getLabel().toString());
			}
		}
		
	}
	
	@Override
	public boolean equals(Object o)
	{
		Edge<?,?> e = (Edge<?,?>)o;
		return this.StartNode.equals(e.StartNode)&&this.EndNode.equals(e.EndNode)&&this.Label.equals(e.Label);
	}
	
	@Override
	public int hashCode()
	{
		return (this.StartNode.toString()+this.EndNode.toString()+this.Label.toString()).hashCode();
	}
}
