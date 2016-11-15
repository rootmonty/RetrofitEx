package monty.oslapplication;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import monty.oslapplication.database.db_handler;
import monty.oslapplication.dummy.City;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    db_handler dbHandler;

    List<City> items = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("OSLApplication");
        dbHandler = new db_handler(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.My_Container_1,new TimeFragment());
        fragmentTransaction.add(R.id.My_Container_2,new DataFragment());

        fragmentTransaction.commit();



        /**
         * Introducing the method to compare the content(area) of states in increasing order
         * using comparator to sort the list
         */


        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_refresh:
               // refresh();
                return true;
            case R.id.action_sort:
                sort();
                return true;
            default:
                super.onOptionsItemSelected(item);
                return true;
        }
    }



    public List<City> sort(){
        items = dbHandler.getAll();
        Collections.sort(items,new AreaComparator());
        //dbHandler.clearcache();
        return items;
    }

    public List<City> getItems(){
        List<City> getlist;
        getlist = dbHandler.getAll();
        return getlist;
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            setContentView(R.layout.main_horizontal);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.item_detail_container_1, new TimeFragment());
            fragmentTransaction.add(R.id.item_detail_container_2, new DataFragment());

            fragmentTransaction.commit();

        } else {
            setContentView(R.layout.main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitle("OSLApplication");
            dbHandler = new db_handler(this);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.add(R.id.My_Container_1, new TimeFragment());
            fragmentTransaction.add(R.id.My_Container_2, new DataFragment());

            fragmentTransaction.commit();

        }

    }
}
