// File name: VertexInterface.java
// Location: Support/Graphs
// Last Updated: May 9, 2022
// Description:
//      interface for vertex

package Support.Graphs;

public interface VertexInterface<T>{

    // return edge ID as a string
    String getID();

    // return vertex type as a string
    String getType();

    // return vertex data
    T getData();

}
