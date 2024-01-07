// File name: Train.java
// Location: Support/Graphs
// Last Updated: May 11, 2022
// Description:
//      Train object which implements TrainInterface

package Support.Graphs;

public class Train implements TrainInterface {
    
    protected String train_id;
    protected String actual_time;
    protected int actual_time_seconds;

    // main constructor of Vertex. Will throw exception if any
    // argument is null.
    public Train(String train_id, String actual_time, int actual_time_seconds)throws IllegalArgumentException{
        if(train_id == null || actual_time == null || actual_time_seconds == 0){
            throw new IllegalArgumentException("Arguements cannot be null.");
        }
        this.train_id = train_id;
        this.actual_time = actual_time;
        this.actual_time_seconds = actual_time_seconds;
    }

    // defualt constructor of Vertex which will always throw IllegalArguementException
    // as we do not allow for null variables
    public Train()throws IllegalArgumentException{
        throw new IllegalArgumentException("Arguements cannot be null.");
    }

    // return edge ID as a string
    public String getTrainID(){return train_id;}

    // return vertex type as a string
    public String getActualTime(){return actual_time;}

    // return vertex data as a T
    public int getActualTimeSeconds(){return actual_time_seconds;}
    
    // Will return the vertices ID
    @Override
    public String toString(){
        return getTrainID();
    }

}
