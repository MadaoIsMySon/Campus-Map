package hw7;

/**
 * Building is an immutable class and represents a building or an intersection on campus
 * The name of the building is represented by string and each building or intersection has a unique string numeric id. 
 * The location of the building or intersection is represented by integer x and integer y.
 *
 *
 * Representation Field:
 *  
 *  name is a string represent the name of the building, each intersection has name ""
 *  id is a string represent a unique id of the building or intersection
 *  x,y are integers represent the x-coordinate and y-coordinate of the building or intersection.
 *  
 * Abstraction Function: 
 *   Building b, with <name, id, x, y>, is a building or intersection named "name" with a unique id "id" locating in (x,y) 
 *  
 * Representation Invariant:
 * 	name, id, x, y are not null
 * 	
 */

public class Building {
	//name of the building, "" if intersection
	String name;
	String id;
	int x;
	int y;
	
	/**
     * @effects Constructs a new Building with name, id, coordinates.
     */
	public Building(String name, String id, int x, int y)
	{
		this.name = name;
		this.id = id;
		this.x = x;
		this.y = y;
	}

	/**
     * @returns name of the building
     */
	public String getName() {
		return name;
	}
	/**
     * @returns id of the building
     */
	public String getId() {
		return id;
	}
	/**
     * @returns x coordinate of the building
     */
	public int getX() {
		return x;
	}
	/**
     * @returns y coordinate of the building
     */
	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Building other = (Building) obj;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		if (name == null) {
//			if (other.name != null)
//				return false;
//		} else if (!name.equals(other.name))
//			return false;
//		if (x != other.x)
//			return false;
//		if (y != other.y)
//			return false;
//		return true;
//	}
//
//	@Override
//	public String toString() {
//		return "Building [name=" + name + ", id=" + id + ", x=" + x + ", y=" + y + "]";
//	}
	
}
