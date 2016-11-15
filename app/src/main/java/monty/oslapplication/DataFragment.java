package monty.oslapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import monty.oslapplication.database.db_handler;
import monty.oslapplication.dummy.City;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by monty on 12/11/16.
 */
public class DataFragment extends Fragment {

    db_handler dbHandler;
    List<City> citylist;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View recycler = inflater.inflate(R.layout.item_list, container, false);
        View recyclerView = recycler.findViewById(R.id.item_list);

        dbHandler = new db_handler(recycler.getContext());
        assert recyclerView != null;
        sharedPreferences =getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);




     /*   if (findViewById(R.id.item_detail_container_2) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).a
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        */
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://staging.pstakecare.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Github service = retrofit.create(Github.class);

        Call<List<City>> source = service.getdata();

        source.enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String format = simpleDateFormat.format(new Date());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("time",format);
                editor.commit();
                List<City> temp = response.body();
                Log.i("the response:",response.body().toString());
                for(int i = 0;i< temp.size();i++){
                    City temobj = new City();
                   // dbHandler.clearcache();
                    temobj.setId(temp.get(i).getId());
                    Log.v("id:",temobj.getId());
                    temobj.setContent(temp.get(i).getContent());
                    Log.v("content",String.valueOf(temobj.getContent()));
                    dbHandler.addCity(temobj);
                    Log.v("dbhandler:",dbHandler.toString());
                    // items.add(i,temobj);

                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {

            }
        });

        citylist = dbHandler.getAll();
        Collections.sort(citylist,new AreaComparator());
        setupRecyclerView((RecyclerView) recyclerView);
        return recycler;
    }



    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(citylist));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private List<City> mValues;

        public SimpleItemRecyclerViewAdapter(List<City> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mContentView.setText(String.valueOf(mValues.get(position).content));

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public City mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
