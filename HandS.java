// File name: HandS.java
// Location: main directory
// Last Updated: May 11, 2022
// Description:
//      When Harry met Sally program

import java.util.Scanner;
import Support.Graphs.*;
import Support.Lists.LBList;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.Date;

public class HandS{
    
    static LBList<Graph> graphs = new LBList<>();
   
    public static void main(String[] args){

        /* Preamble print */
        System.out.printf("%n--------- WHEN DID HARRY MEET SALLY? ---------%n%n");
        System.out.println("Who is Harry?");
        System.out.println("      Harry is a guy who has lived in New Jersey his whole life and commutes exclusively using NJ Transit trains. Harry doesn’t have much to do, so he is willing to spend up to four hours commuting one way. Even though he spends a lot of time commuting, he still tries to minimize his time on trains, always picking one of the ten shortest routes.");
        System.out.println();
        System.out.println("Who is Sally?");
        System.out.println("      Sally is a gal who is new to New Jersey. Her hobbies include looking at maps and calculating carbon emissions. Sally spends a lot of time figuring out the least amount of trains needed to get from point A to point B. She always takes one of the five shortest routes. Sally has a day job, so she is only willing to spend 2 hours commuting per day.");
        System.out.println();
        System.out.println("This program answers the questions, 'Did Harry meeet Sally?' and 'How did it happen?'");
        System.out.println();
        System.out.println("All train data from March 2018 to April 2020 for NJ Tranist is available.");
        System.out.println();
        System.out.println("The following stations are available: Kingsland, Rutherford, Wood Ridge, Newark Airport, Watsessing Avenue, East Orange, Union, New York Penn Station, Newark Broad Street, Newark Penn Station, Hoboken, Secaucus");
        System.out.printf("%n----------------------------------------------%n%n");

        /* GETTING HARRY INFORMATION AND CREATING HARRY GRAPH */
        boolean escapeH = false;
        while(!escapeH){
            try{
                Scanner input = new Scanner(System.in);
                System.out.println("Epoch converter: https://www.unixtimestamp.com");
                System.out.println();
                System.out.printf("What month and year (format as 'July2019') >> ");
                String monthYear = input.nextLine();
                System.out.printf("What time did Harry leave? (in epoch) >> ");
                int hStartTime = input.nextInt();
                input.nextLine();
                // set Harry's time on trains
                int hHoursOnTrain = 4;

                importGraph(hStartTime, hHoursOnTrain, monthYear);

                escapeH = true;
            }
            catch(InputMismatchException e){
                System.out.println("\nData Not Entered Properly. Try again.");
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        /* GETTING SALLY INFORMATION AND CREATING HARRY GRAPH */
        boolean escapeS = false;
        while(!escapeS){
            try{
                Scanner input = new Scanner(System.in);
                System.out.printf("\nWhat month and year (format as 'July2019') >> ");
                String monthYear = input.nextLine();
                System.out.printf("What time did Sally leave? (in epoch) >> ");
                int sStartTime = input.nextInt();
                input.nextLine();
                // set Sally's time on trains
                int sHoursOnTrain = 2;

                importGraph(sStartTime, sHoursOnTrain, monthYear);

                escapeS = true;
            }
            catch(InputMismatchException e){
                System.out.println("\nData Not Entered Properly. Try again.");
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        /* TRAVERSING HARRY GRAPH */
        int numOfRoutesH = 10;
        LBList<String> hTravs = traverse(graphs.get(0), "Harry", numOfRoutesH);
        String[] tokenH;

        /* TRAVERSING SALLY GRAPH */
        int numOfRoutesS = 5;
        LBList<String> sTravs = traverse(graphs.get(1), "Sally", numOfRoutesS);
        String[] tokenS;
        
        /* COMPARING HARRY AND SALLY TRAVERSALS */
        boolean a;
        int counter = 0;
        int counter2 = 0;
        System.out.println();
        for(int i = 0; i < hTravs.size(); i++){
            for(int k = 0; k < sTravs.size(); k++){
                a = true;
                tokenH = hTravs.get(i).split("-");
                tokenS = sTravs.get(k).split("-");
                while(a){
                    if(a){
                        for(int j = 0; j < tokenH.length; j++){
                            if(a){
                                for(int l = 0; l < tokenS.length; l++){
                                    if(a){
                                        // if the have the same train in the given combination inside the i and k for loops, print out the informatio and add one to the counter
                                        // exit the while loop as to not double count if they took two trains together in the same combination
                                        if(tokenH[j].equals(tokenS[l])){
                                            ++counter;
                                            System.out.printf("If Harry takes train %sand Sally takes train %sthey would meet on %s.%n%n", toPrint(hTravs.get(i), graphs.get(0)), toPrint(sTravs.get(k), graphs.get(1)), graphs.get(0).getEdge(tokenH[j]).getData().toString());
                                            a = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    a = false;
                    ++counter2;
                }
            }
        }
        float percent = (float)counter / (float)counter2 * 100;
        System.out.printf("There is a %.0f percent chance that Harry met Sally.%n%n", percent);
        
    }
    
    // import graph from a given folder name
    // which contains both a vertex text file 
    // and an edge text file
    public static void importGraph(int StartTime, int HoursOnTrain, String monthYear) throws Exception{
        int EndTime = StartTime + (HoursOnTrain * 3600);
        String GraphName = "TrainData" + monthYear;
        if(Files.notExists(Paths.get("input/"+ GraphName))){
            throw new Exception("\nCancelling import. Graph " + GraphName + " does not exist.");
        }
        Graph graph = null;
        String dilimiter = ",";
        try{
            Vertex[] ver = importVertex(GraphName, dilimiter);
            graph = new Graph(ver, importEdge(GraphName, ver, dilimiter, StartTime, EndTime), GraphName);
        }
        catch(Exception e){
            throw e;
        }
        graphs.add(graph);
    }

    // Method will import vertex information from
    // the CSV file vertex.csv
    // Exception are caught and handled appriopately
    public static Vertex[] importVertex(String GraphName, String dilimiter)throws Exception{
        if(Files.notExists(Paths.get("input/"+ GraphName + "/vertex.csv"))){
            throw new Exception("Cancelling import. File vertex.csv for graph " + GraphName + " does not exist.");
        }
        Scanner vertexInput = new Scanner(Paths.get("input/"+ GraphName + "/vertex.csv"));
        LBList<Vertex> vertices = new LBList<>();
        while(vertexInput.hasNext()){
            String line = vertexInput.nextLine();
            String[] token = line.split(dilimiter);
            String ID = "";
            String type = "";
            String[] data = new String[token.length-2];
            for(int i = 0; i < 4; i++){
                if(i == 0){
                    ID = token[i];
                }
                else if(i == 1){
                    type = token[i];
                }
                else{
                    for(int j = 2; j < token.length; j++){
                        data[j-2] = token[j];
                    }
                }
            }
            Vertex<Object> v = new Vertex<>(ID, type, createData(type, data));
            vertices.add(v);
        }
        vertexInput.close();

        Vertex[] ver = new Vertex[vertices.size()];
        Iterator<Vertex> iterV = vertices.iterator();
        int i = 0;
        while(iterV.hasNext()){
            ver[i] = iterV.next();
            ++i;
        }
        return ver;
    }

    // Method will import edge information from
    // the CSV file edge.csv
    // Exception are caught and handled appriopately
    public static Edge[] importEdge(String GraphName, Vertex[] vertices, String dilimiter, int StartTime, int EndTime)throws Exception{
        if(Files.notExists(Paths.get("input/"+ GraphName + "/edge.csv"))){
            throw new Exception("Cancelling import. File edge.csv for graph " + GraphName + " does not exist.");
        }
        Scanner edgeInput = new Scanner(Paths.get("input/" + GraphName + "/edge.csv"));
        LBList<Edge> edges = new LBList<>();
        while(edgeInput.hasNext()){
            String line = edgeInput.nextLine();
            String[] token = line.split(dilimiter);
            String toID = "";
            String fromID = "";
            String ID = "";
            String type = "";
            String[] data = new String[token.length-4];
            if((Integer.valueOf(token[token.length-1]) >= StartTime) && (Integer.valueOf(token[token.length-1]) <= EndTime)){
                for(int i = 0; i < 5; i++){
                    if(i == 0){
                        toID = token[i];
                    }
                    else if(i == 1){
                        fromID = token[i];
                    }
                    else if(i == 2){
                        ID = token[i];
                    }
                    else if(i == 3){
                        type = token[i];
                    }
                    else{
                        for(int j = 4; j < token.length; j++){
                            data[j-4] = token[j];
                        }
                    }
                }
                
                boolean to = false;
                boolean from = false;
                Vertex toVertex = null;
                Vertex fromVertex = null;
                for(int i = 0; i < vertices.length; i++){
                    if (vertices[i].getID().equals(toID)) {
                        to = true;
                        toVertex = vertices[i];
                    }
                    if (vertices[i].getID().equals(fromID)) {
                        from = true;
                        fromVertex = vertices[i];
                    }
                    if (to && from) {break;}
                }
                if (!(to && from)){
                    throw new Exception("Cancelling import. Vertices for edge does not exist. Edge ID: " + ID);
                }
                
                Edge<Object> e = new Edge<>(ID, fromVertex, toVertex, type, createData(type, data));
                edges.add(e);
            }
        }
        
        Edge[] ed = new Edge[edges.size()];
        Iterator<Edge> iterE = edges.iterator();
        int j = 0;
        while(iterE.hasNext()){
            ed[j] = iterE.next();
            ++j;
        }
        return ed;
    }

    // USER MUST IMPLEMENT THIS METHOD BY INSERTING CONSTRUCTOR 
    // INFORMATION AS NOTED IN THE TRY BLOCK.
    // THIS METHOD IS USED WHEN IMPORTING VERTEX and EDGE INFORMATION
    // LENGTH OF DATA DEPENDS ON TYPE
    public static Object createData(String type, String[] data)throws Exception{
        while(true){
            try{
                // INSERT CONSTRUCTOR(S) HERE
                if(type.equals("Integer")){
                    Integer object = Integer.valueOf(data[0]);
                    return object;
                }
                if(type.equals("Train")){
                    Train object = new Train(data[0], data[1], Integer.valueOf(data[2]));
                    return object;
                }
                else{
                    throw new Exception("\nSpecified type has no built in constructor.\nCancelling data creation.");
                }
            }
            catch(Exception e){
                throw e;
            }
            
        }
    }

    public static LBList<String> traverse(Graph graph, String name, int atMost){
        Scanner input = new Scanner(System.in);
        while(true){
            try{
                System.out.printf("\nWhere is %s coming from?\n>> ", name);
                String fromID = input.nextLine();
                System.out.printf("Where is %s going to?\n>> ", name);
                String toID = input.nextLine();
                if(graph.hasVertex(fromID) && graph.hasVertex(toID)){
                    LBList<String> goodOnes = allTraversals(graph, fromID, toID, atMost);
                    if(goodOnes.isEmpty()){
                        System.out.println("That route was not possible with the given constraints.");
                    }
                    return goodOnes;
                }
                else{
                    System.out.println("\nOne or more vertices do not exist.");
                }
            }
            catch(InputMismatchException e){
                input.nextLine();
                System.out.println("\nInvalid input");
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    // Method will determine all possible paths from vertex with ID fromID to the
    // vertex with ID toID by walking through all possible combinations. Graph will only
    // traverse each edge once and traversal method works for multiple edges between vertices
    public static LBList<String> allTraversals(Graph graph, String fromID, String toID, int atMost){
        int fromIndex = graph.getVertexIndex(graph.getVertex(fromID));
        int toIndex = graph.getVertexIndex(graph.getVertex(toID));
        LBList[][] edges = graph.getGraph();
        LBList<String> allOnes = new LBList<>();
        LBList<String> goodOnes = new LBList<>();
        boolean flag2 = true;
        while(flag2){
            // intializing first list
            for(int j = 0; j < graph.getNumVertices(); j++){
                LBList<Edge> e = edges[fromIndex][j];
                if(e != null){
                    // create iterator
                    Iterator<Edge> iter = e.iterator();
                    // loop on all edges in LBList
                    while(iter.hasNext()){
                        Edge tempEdge = iter.next();
                        allOnes.add(tempEdge.getID());
                    }
                }
            }
            if(allOnes.isEmpty()){
                System.out.println("\nfromVertex is disconnected from the graph");
            }
            // iterating through list
            while(!allOnes.isEmpty()){
                // find intial number of items in list to make new combonations
                int intialSize = allOnes.size();
                // loops on inital combos in list
                for(int i = 0; i < intialSize; i++){
                    // tokenize each combo in list
                    String[] token = allOnes.get(0).split("-");
                    // break out if too many combinations of paths
                    if(goodOnes.size() >= atMost){
                        flag2 = false;
                    }
                    // vertex is the right ending
                    else if(graph.getVertexIndex(graph.getEdge(token[token.length-1]).getToVertex()) == toIndex){ 
                        goodOnes.add(allOnes.get(0));
                    }
                    // vertex has no more edges
                    else if(!graph.vertexHasEdge(graph.getEdge(token[token.length-1]).getToVertex().getID())){

                    }
                    else{
                        // loop on each possible toVertex
                        for(int j = 0; j < graph.getNumVertices(); j++){
                            // defines possible edges last token vertex to jth vertex
                            LBList<Edge> e = edges[graph.getVertexIndex(graph.getEdge(token[token.length-1]).getToVertex())][j];
                            // check if there exist edges
                            if(e != null){
                                // create iterator
                                Iterator<Edge> iter = e.iterator();
                                // loop on all edges in LBList
                                while(iter.hasNext()){
                                    Edge tempEdge = iter.next();
                                    boolean flag = false;
                                    // check if edge was already in tokenized string
                                    for (int k = 0; k < token.length; k++){
                                        // EDITED HERE to add traversal greater than method
                                        // becasue the data IDs are assigned in the time, compare the interger value of the IDs to guarentee you aren't getting on a train that already happened
                                        if(!(tempEdge.getID().equals(token[k])) && ((Integer.valueOf(graph.getEdge(token[token.length-1]).getID()) <= (Integer.valueOf(tempEdge.getID()))))){
                                            flag = true;
                                        }
                                    }
                                    // if it wasn't, add it to the end of the list
                                    if (flag){
                                        String s = allOnes.get(0) + "-" + tempEdge.getID();
                                        allOnes.add(s);
                                    }
                                }
                            }
                        }
                    }
                    // remove inital combination from list
                    allOnes.remove(allOnes.get(0));
                }
            }
            if(goodOnes.isEmpty()){
                System.out.println("\ntoVertex does not have a path to fromVertex");
                flag2 = false;
            }
        }
        return goodOnes;
    }

    public static String toPrint(String travs, Graph graph){
        String s = "";
        String[] token;
        String current = "";
        token = travs.split("-");
        for(int j = 0; j < (token.length); j++){
            current = token[j];
            String fromStation = graph.getEdge(current).getFromVertex().getID();
            String toStation = graph.getEdge(current).getToVertex().getID();
            String train = graph.getEdge(current).getData().toString();
            // String time = graph.getEdge(current).getData().getActualTime();
            if(Integer.valueOf(j) == (token.length-1)){
                s = s + train + " from " + fromStation + " to " + toStation + ", ";
            }
            else{
                s = s + train + " from " + fromStation + " to " + toStation + ", then train ";
            }
        }
        return s;
    }
}
