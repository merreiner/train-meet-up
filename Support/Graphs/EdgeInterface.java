// File name: EdgeInterface.java
// Location: Support/Graphs
// Last Updated: May 9, 2022
// Description:
//      Interface for the Edge object

package Support.Graphs;

public interface EdgeInterface<T>{
    
    // return edge ID as a string
    String getID();

    // return edge type as a string
    String getType();

    // return fromVertex vertex of the edge
    Vertex getFromVertex();

    // return toVertex vertex of the edge
    Vertex getToVertex();

    // return data of edge
    T getData();

}
