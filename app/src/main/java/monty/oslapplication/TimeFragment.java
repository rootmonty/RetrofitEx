package monty.oslapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by monty on 12/11/16.
 */
public class TimeFragment extends Fragment {

    Button refresh;
    TextView timestamp;
    SharedPreferences sharedPreferences;
    boolean check;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.timestamp,container,false);
        refresh = (Button) rootview.findViewById(R.id.brefresh);
        sharedPreferences =getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        timestamp = (TextView) rootview.findViewById(R.id.timestamp) ;
        check = isNetworkAvailable();
        timestamp.setText("TIMESTAMP:"+sharedPreferences.getString("time",""));
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                timestamp.setText("TIMESTAMP:"+sharedPreferences.getString("time",""));
            }
        });
        return rootview;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

}
