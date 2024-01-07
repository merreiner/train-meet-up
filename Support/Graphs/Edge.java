// File name: Edge.java
// Location: Support/Graphs
// Last Updated: May 9, 2022
// Description:
//      Edge object which implements EdgeInterface

package Support.Graphs;

public class Edge<T> implements EdgeInterface<T>{

    protected String ID;
    protected String type;
    protected T data;
    protected Vertex fromVertex;
    protected Vertex toVertex;

    // main constuctor, will throw exception if any argument is null
    public Edge(String ID, Vertex fromVertex, Vertex toVertex, String type, T data)throws IllegalArgumentException{

        if(ID == null || fromVertex == null || toVertex == null || type == null || data == null){
            throw new IllegalArgumentException("Arguments cannot be null");
        }
        this.ID = ID;
        this.type = type;
        this.data = data;
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
    }

    // default constructor which will always throw IllegalArgumentException
    // as we do not allow for null arguments in the edge object.
    public Edge()throws IllegalArgumentException{
        throw new IllegalArgumentException("Arguments cannot be null");
    }

    // return edge ID as a string
    public String getID(){return ID;}

    // return vertex type as a string
    public String getType(){return type;}

    // return fromVertex vertex of the edge
    public Vertex getFromVertex(){return fromVertex;}

    // return toVertex vertex of the edge
    public Vertex getToVertex(){return toVertex;}   
    
    // return data of edge
    public T getData(){return data;}

    // equals mehtod which will call
    // ID.equals()
    @Override
    public boolean equals(Object edge){
        Edge<T> edgeTemp = (Edge<T>) edge;
        return edgeTemp.getID().equals(getID());
    }

    // Method will return string of edge information
    // so the data can be saved.
    public String toSave(String dilimiter){
        return (fromVertex.getID() + dilimiter + toVertex.getID() + dilimiter + ID + dilimiter + type + dilimiter + data.toString());
    }

    // toString() method which will return the ID
    @Override
    public String toString(){
        return getID();
    }
}
