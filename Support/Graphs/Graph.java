// File name: Graph.java
// Location: Support/Graphs
// Last Updated: May 9, 2022
// Description:
//      Graph object which implement GraphInterface

package Support.Graphs;

import java.util.Iterator;
import Support.Lists.LBList;

public class Graph implements GraphInterface{
    
    protected String ID;
    protected LBList[][] graphEdges;
    protected Vertex[] graphVertices;
    protected int numVertices;
    protected int numEdges;

    // primary constructor of the Graph. Will throw exception if any argument is null
    public Graph(Vertex[] vertices, Edge[] edges, String ID)throws IllegalArgumentException{

        if(vertices == null || edges == null || ID == null){
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        this.ID = ID;
        this.graphVertices = vertices;
        this.numVertices = vertices.length;

        for(int i = 0; i < vertices.length; i++){
            for(int j = 0; j < vertices.length; j++){
                if(vertices[i].equals(vertices[j]) && i != j){
                    throw new IllegalArgumentException("Graph has duplicate vertices.");
                }
            }
        }

        this.graphEdges = new LBList[vertices.length][vertices.length];
        this.numEdges = 0;
        int indexFrom = 0;
        int indexTo = 0;
        
        boolean setTo = false;
        boolean setFrom = false;
        for(int i = 0; i < edges.length; i++){
            for(int j = 0; j < vertices.length; j++){
                if(edges[i].getFromVertex().equals(vertices[j])){
                    indexFrom = j;
                    setFrom = true;
                }
                if(edges[i].getToVertex().equals(vertices[j])){
                    indexTo = j;
                    setTo = true;
                }
            }

            if(!(setFrom && setTo)){
                throw new IllegalArgumentException("Vertices for edge does not exist. Edge ID: " + edges[i].getID());
            }

            if(graphEdges[indexFrom][indexTo] == null){
                LBList<Edge> list = new LBList<>();
                list.add(edges[i]);
                graphEdges[indexFrom][indexTo] = list;
                ++numEdges;
            }
            else{
                if(graphEdges[indexFrom][indexTo].contains(edges[i])){
                    throw new IllegalArgumentException("Graph has two duplicate edges");
                }
                graphEdges[indexFrom][indexTo].add(edges[i]);
                ++numEdges;
            }
        }
        

    }

    // secondary constructor of the Graph which will have 0 vertices and 0 edges
    // will throw exceptoin if ID is null
    public Graph(String ID)throws IllegalArgumentException{
        if(ID == null){
            throw new IllegalArgumentException("ID cannot be null");
        }
        this.ID = ID;
        graphVertices = new Vertex[1];
        graphEdges = new LBList[1][1];
        this.numVertices = 0;
        this.numEdges = 0;
    } 

    // default constructor of Graph which will always throw an exception as
    // we do not all of variables in graph to be null
    public Graph()throws IllegalArgumentException{
        throw new IllegalArgumentException("Graph does not allow for a default constructor. Must at minimum, pass graph ID");
    }

    // return graphsID
    public String getID(){return ID;}

    // return graphEdges variable
    public LBList[][] getGraph(){return graphEdges;}

    // return number of vertices of the graph
    public int getNumVertices(){return numVertices;}

    // returns index of vertex in adjacency matrix
    public int getNumEdges(){return numEdges;}

    // returns index of a vertex, if vertex is not
    // part of the graph, the method will return -1
    public int getVertexIndex(Vertex vertex){
        for(int i = 0; i < getNumVertices(); i++){
            if(graphVertices[i].equals(vertex)){
                return i;
            }
        }
        return -1;
    }

    // enlarges the adjacency matrix
    public void enlarge(){
        Vertex[] nVa;
        LBList[][] nEa;
        if(getNumVertices() == 0){
            System.out.println("Here1");
            nVa = new Vertex[1];
            nEa = new LBList[1][1];
        }
        else{
            nVa = new Vertex[getNumVertices()*2];
            nEa = new LBList[getNumVertices()*2][getNumVertices()*2];
        }

        for(int i = 0; i < getNumVertices(); i++){
            nVa[i] = graphVertices[i];
        }
        for(int i = 0; i < getNumVertices(); i++){
            for(int j = 0; j < getNumVertices(); j++){
                nEa[i][j] = graphEdges[i][j];
            }
        }
        graphVertices = nVa;
        graphEdges = nEa;
    }

    // check if graph has no vertices
    public boolean isEmpty(){
        if(getNumVertices() == 0){return true;}
        return false;
    }

    // check if graph has the vertex of a given ID
    public boolean hasVertex(String vertexID){
        for(int i = 0; i < getNumVertices(); i++){
            if(graphVertices[i].getID().equals(vertexID)){return true;}
        }
        return false;
    }

    // check if vertex of the graph has an edge
    public boolean vertexHasEdge(String vertexID){
        Vertex vertex = getVertex(vertexID);
        if(!hasVertex(vertexID)){return false;}
        int indexVertex = getVertexIndex(vertex);
        for(int i = 0; i < getNumVertices(); i++){
            if(graphEdges[indexVertex][i] != null){
                if(graphEdges[indexVertex][i].size() != 0){
                    return true;
                }
            }
        }

        return false;   
    }

    // returns edge of the given ID
    public boolean hasEdge(String vertexID){
        Edge edge = null;
        for(int i = 0; i < getNumVertices(); i++){
            for(int j = 0; j < getNumVertices(); j++){
                if(graphEdges[i][j] != null){
                    Edge tempEdge = null;
                    Iterator<Edge> iter = graphEdges[i][j].iterator();
                    while(iter.hasNext()){
                        tempEdge = iter.next();
                        if(tempEdge.getID().equals(ID)){
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    // check if vertex of the graph has an edge
    public boolean vertexHasEdgeID(String vertexID, String edgeID){
        Vertex vertex = getVertex(vertexID);
        if(!hasVertex(vertexID)){return false;}

        int indexVertex = getVertexIndex(vertex);

        for(int i = 0; i < getNumVertices(); i++){
            if(graphEdges[indexVertex][i] != null){
                Iterator<Edge> iter = graphEdges[indexVertex][i].iterator();
                while(iter.hasNext()){
                    if(iter.next().getID().equals(ID)){return true;}
                }
            }
        }

        return false;
    }

    // removes vertex from graph and report boolean
    public boolean removeVertex(String vertexID){
        Vertex vertex = getVertex(ID);
        if(!hasVertex(ID)){return false;}
            
        int vertexIndex = getVertexIndex(vertex);

        graphVertices[vertexIndex] = null;
        
        for(int i = vertexIndex; i < getNumVertices()-1; i++){
            graphVertices[i] = graphVertices[i+1];
        }
        
        int edgesRemoved = 0;
        for(int i = 0; i < getNumVertices(); i++){
            if(graphEdges[i][vertexIndex] != null){
                edgesRemoved = edgesRemoved + graphEdges[i][vertexIndex].size();
            }
            if(graphEdges[i][vertexIndex] != null && i != vertexIndex){
                edgesRemoved = edgesRemoved + graphEdges[vertexIndex][i].size();
            }
        }
        numEdges = numEdges - edgesRemoved;

        for(int i = vertexIndex; i < getNumVertices()-1; i++){
            for(int j = vertexIndex; j < getNumVertices()-1; j++){
                if(i+1 < getNumVertices()){
                    graphEdges[i][j] = graphEdges[i+1][j];
                }
                if(j + 1 < getNumVertices()){
                    graphEdges[i][j] = graphEdges[i][j+1];
                } 
            }
        }

        numVertices = numVertices - 1;
        return true;
    }

    // returns vertex of the given string ID
    public Vertex getVertex(String vertexID){
        for(int i = 0; i < getNumVertices(); i++){
            if(graphVertices[i].getID().equals(vertexID)){
                return graphVertices[i];
            }
        }

        return null;
    }

    // add vertex to the graph and report boolean 
    public boolean addVertex(Vertex vertex){
        if(hasVertex(vertex.getID())){return false;}
        if(getNumVertices() + 1 > graphVertices.length){enlarge();}
        graphVertices[getNumVertices()] = vertex;
        ++numVertices; 
        return true;
    }

    // removes edge of the given string
    public boolean removeEdge(String fromID, String toID, String edgeID){
        Vertex fromVertex = getVertex(fromID);
        Vertex toVertex = getVertex(toID);
        if(!hasVertex(fromID) || !hasVertex(toID) ){return false;}
        
        int indexFromVertex = getVertexIndex(fromVertex);
        int indexToVertex = getVertexIndex(toVertex);
        if(graphEdges[indexFromVertex][indexToVertex] != null){
            Iterator<Edge> iter = graphEdges[indexFromVertex][indexToVertex].iterator();
            while(iter.hasNext()){
                Edge tempEdge = iter.next();
                if(tempEdge.getID().equals(ID)){
                    graphEdges[indexFromVertex][indexToVertex].remove(tempEdge);
                    numEdges = numEdges - 1;
                    return true;
                }
            }
        }
        return false;
    }

    // removes edge knowning only the edgeID
    public boolean removeEdge(String edgeID){
        Edge edge = null;
        for(int i = 0; i < getNumVertices(); i++){
            for(int j = 0; j < getNumVertices(); j++){
                if(graphEdges[i][j] != null){
                    Edge tempEdge = null;
                    Iterator<Edge> iter = graphEdges[i][j].iterator();
                    while(iter.hasNext()){
                        tempEdge = iter.next();
                        if(tempEdge.getID().equals(ID)){
                            graphEdges[i][j].remove(tempEdge);
                            --numEdges;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    // returns edge of the given string ID
    public Edge getEdge(String fromID, String toID, String edgeID){
        Vertex fromVertex = getVertex(fromID);
        Vertex toVertex = getVertex(toID);

        if(!hasVertex(toID) || !hasVertex(fromID) ){return null;}
        int indexFromVertex = getVertexIndex(fromVertex);
        int indexToVertex = getVertexIndex(toVertex);
        if(graphEdges[indexFromVertex][indexToVertex] != null){
            Iterator<Edge> iter = graphEdges[indexFromVertex][indexToVertex].iterator();
            while(iter.hasNext()){
                Edge tempEdge = iter.next();
                if(tempEdge.getID().equals(edgeID)){return tempEdge;}
            }
        }
        return null;
    }

    // returns edge of given string on ID
    public Edge getEdge(String edgeID){
        Edge edge = null;
        for(int i = 0; i < getNumVertices(); i++){
            for(int j = 0; j < getNumVertices(); j++){
                if(graphEdges[i][j] != null){
                    Edge tempEdge = null;
                    Iterator<Edge> iter = graphEdges[i][j].iterator();
                    while(iter.hasNext()){
                        tempEdge = iter.next();
                        if(tempEdge.getID().equals(edgeID)){
                            return tempEdge;
                        }
                    }
                }
            }
        }

        return edge;
    }
    
    // returns boolean if method is able to add edge to the graph
    public boolean addEdge(Edge edge){
        int toIndex = getVertexIndex(edge.getToVertex());
        int fromIndex = getVertexIndex(edge.getFromVertex());
        if(!vertexHasEdgeID(edge.getFromVertex().getID(), ID)){
            if(graphEdges[fromIndex][toIndex] == null){
                LBList<Edge> list = new LBList<>();
                list.add(edge);
                graphEdges[fromIndex][toIndex] = list;
                ++numEdges; 
                return true;
            }
            else{
                graphEdges[fromIndex][toIndex].add(edge);
                ++numEdges; 
                return true;
            }
        }

        return false;
    }

    // creates a CSV files of adjacency matrix
    public String toViz(String dilimiter){
        String s = "F\\T";
        for(int i = 0; i < getNumVertices(); i++){
            s = s + dilimiter + graphVertices[i].toString();   
        }
        s = s + "\n";
        for(int j = 0; j < getNumVertices(); j++){
            s = s + graphVertices[j].toString();
            for(int k = 0; k < getNumVertices(); k++){
                if (graphEdges[j][k] != null){
                    s = s + dilimiter+ "{" + graphEdges[j][k].toString() + "}";
                }
                else{
                    s = s + dilimiter;
                }
            }  
            s = s + "\n";
        }
        return s;
    }

    // saves vertex information to a CSV file
    public String toSaveVertex(String dilimiter){
        String s = "";
        for(int i = 0; i < getNumVertices(); i++){
            s = s + graphVertices[i].toSave(dilimiter) + "\n";
        }
        return s;
    }

    // saves edge information to a CSV file
    public String toSaveEdge(String dilimiter){
        String s = "";
        for(int i = 0; i < getNumVertices(); i++){
            for(int j = 0; j < getNumVertices(); j++){
                if(graphEdges[i][j] != null){
                    Iterator<Edge> iter = graphEdges[i][j].iterator();
                    while(iter.hasNext()){
                        s = s + iter.next().toSave(dilimiter) + "\n";
                    }
                }
            }
        }
        return s;
    }
    
    // print out vertex toString() and Edges
    @Override
    public String toString(){
        String s = "Vertices:\n";
        for(int i = 0; i < getNumVertices(); i++){
            if(i == 0){
                s = s+ graphVertices[i].toString();
            }
            else{
                s = s + ";" + graphVertices[i].toString();
            }      
        }
        s = s + "\n\nEdges:\n";
        for(int i = 0; i < getNumVertices(); i++){
            for(int j = 0; j < getNumVertices(); j++){
                String vertex1 = graphVertices[i].getID();
                String vertex2 = graphVertices[j].getID();
                String edges = "";
                if (graphEdges[i][j] != null){
                    edges = graphEdges[i][j].toString();
                    s = s + vertex1 + " " + vertex2 + " {" + edges + "}\n";
                }             
            }
        }
        return s;
    }

    // will determine if graphs are equal by
    // applying string equals method on IDs
    @Override
    public boolean equals(Object graph){
        Graph raph = (Graph) graph;
        return getID().equals(raph.getID());
    }



}
