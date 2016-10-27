package monty.oslapplication;

import java.util.List;

import monty.oslapplication.dummy.City;
import monty.oslapplication.dummy.City_detail;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by monty on 26/10/16.
 */
public interface Github {

    @GET("/mock/countries.json")
    Call<List<City>>  getdata();
}
