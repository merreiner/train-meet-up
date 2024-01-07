// File name: GraphInterface.java
// Location: Support/Graphs
// Last Updated: May 9, 2022
// Description:
//      Interface for the graph object

package Support.Graphs;

public interface GraphInterface{
 
    // return graphsID
    String getID();

    // return number of vertices of the graph
    int getNumVertices();

    // return number of edges of the graph
    int getNumEdges();

    // returns index of vertex in adjacency matrix
    int getVertexIndex(Vertex vertex);

    // enlarges the adjacency matrix
    void enlarge();

    // check if graph has no vertices
    boolean isEmpty();

    // check if graph has the vertex of a given ID
    boolean hasVertex(String vertexID);

    // check if vertex of the graph has an edge
    boolean vertexHasEdgeID(String vertexID, String edgeID);

    // check if vertex of the graph has an edge
    boolean vertexHasEdge(String vertexID);

    // check if graph has edge of a given ID
    boolean hasEdge(String edgeID);

    // removes vertex from graph and reports
    // if done using a boolean
    boolean removeVertex(String vertexID);

    // returns vertex of the given ID
    Vertex getVertex(String vertexID);

    // adds vertex to graph and returns if done
    // using a boolean
    boolean addVertex(Vertex vertex);

    // removes edge knowning fromVertex ID and toVertex ID
    boolean removeEdge(String fromID, String toID, String edgeID);

    // removes edge knowning only the edgeID
    boolean removeEdge(String edgeID);

    // adds edge to the graph and reports back
    // using a boolean if edge was added
    boolean addEdge(Edge edge);

    // returns edge of the given ID from given fromVertex ID and toVertex ID
    Edge getEdge(String fromID, String toID, String edgeID);

    // returns edge knowning only the edgeID
    Edge getEdge(String edgeID);
}
