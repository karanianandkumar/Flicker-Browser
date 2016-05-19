package com.anandkumar.flickerbrowser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private final String LOG_TAG="MainActivity";
    private List<Photo> mPhotoList=new ArrayList<Photo>();
    private RecyclerView mRecyclerView;
    private FlickerRecyclerViewAdapter flickerRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activateToolbar();

        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        flickerRecyclerViewAdapter=new FlickerRecyclerViewAdapter(MainActivity.this,
                new ArrayList<Photo>());
        mRecyclerView.setAdapter(flickerRecyclerViewAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(this, mRecyclerView, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this,"Single Tap",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this,"Long Press",Toast.LENGTH_SHORT).show();
            }
        }));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id== R.id.menu_search){
            Intent intent=new Intent(this,SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

            String query=getSavedPrefData(FLICKER_QUERY);
        if(query.length()>0) {
            ProcessPhotos processPhotos = new ProcessPhotos(query, true);
            processPhotos.execute();
        }

    }

    private String getSavedPrefData(String flickerQuery) {
        SharedPreferences sharedPref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPref.getString(flickerQuery,"");
    }

    public class ProcessPhotos extends GetFlickerJsonData{

        public ProcessPhotos(String searchCriteria,boolean matchAll){
            super(searchCriteria,matchAll);
        }
        public void execute(){
            super.execute();
            ProcessData processData=new ProcessData();
            processData.execute();
        }

        public class ProcessData extends DownloadJsonData{

            protected void onPostExecute(String webData){
                super.onPostExecute(webData);
                flickerRecyclerViewAdapter.loadNewData(getmPhotos());
            }
        }
    }
}
