// File name: Vertex.java
// Location: Support/Graphs
// Last Updated: May 9, 2022
// Description:
//      Vertex object which implements VertexInterface

package Support.Graphs;

public class Vertex<T> implements VertexInterface<T> {
    
    protected String ID;
    protected String type;
    protected T data;

    // main constructor of Vertex. Will throw exception if any
    // argument is null.
    public Vertex(String ID, String type, T data)throws IllegalArgumentException{
        if(ID == null || type == null || data == null){
            throw new IllegalArgumentException("Arguements cannot be null.");
        }
        this.ID = ID;
        this.type = type;
        this.data = data;
    }

    // defualt constructor of Vertex which will always throw IllegalArguementException
    // as we do not allow for null variables
    public Vertex()throws IllegalArgumentException{
        throw new IllegalArgumentException("Arguements cannot be null.");
    }

    // return edge ID as a string
    public String getID(){return ID;}

    // return vertex type as a string
    public String getType(){return type;}

    // return vertex data as a T
    public T getData(){return data;}

    // return string that can be used to save vertice information
    public String toSave(String dilimiter){
        return (ID + dilimiter + type + dilimiter + data.toString());
    }

    // will check if vertices are equal by 
    // determining if objects have the same IDs
    @Override
    public boolean equals(Object vertex){
        Vertex vertextemp = (Vertex) vertex;
        return (vertextemp.getID() == getID());
    }

    // Will return the vertices ID
    @Override
    public String toString(){
        return getID();
    }

}
