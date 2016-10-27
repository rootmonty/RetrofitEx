package monty.oslapplication.dummy;

import com.google.gson.annotations.SerializedName;

/**
 * Created by monty on 26/10/16.
 */
public class City {
    @SerializedName("name")
    public String id="";

    @SerializedName("area")
    public String content="";

    public City(){}
    public void setId(String id){this.id = id;}
    public void setContent(String content){this.content = content;}

    public String getId(){
        return id;
    }
    public String getContent(){
        return content;
    }

}
