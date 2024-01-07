// File name: TrainInterface.java
// Location: Support/Graphs
// Last Updated: May 11, 2022
// Description:
//      interface for train

package Support.Graphs;

public interface TrainInterface<T>{

    // return train ID as a string
    public String getTrainID();

    // return vertex type as a string
    public String getActualTime();

    // return vertex data as a T
    public int getActualTimeSeconds();


}
