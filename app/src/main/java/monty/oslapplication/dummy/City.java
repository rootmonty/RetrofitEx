package monty.oslapplication.dummy;

import com.google.gson.annotations.SerializedName;

/**
 * Created by monty on 26/10/16.
 */
public class City {
    @SerializedName("name")
    public String id="";

    @SerializedName("area")
    public int content= 0;

    public City(){}
    public void setId(String id){this.id = id;}
    public void setContent(int content){this.content = content;}

    public String getId(){
        return id;
    }
    public int getContent(){
        return content;
    }

}
