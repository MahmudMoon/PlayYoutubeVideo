package com.texlabit.playyoutubevideo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView ;
    YouTubePlayer youTubePlayer_con;
    ArrayList<String> songs;
    public static String TAG = "MyTag";
    public int currentPlay = 0;
    YouTubePlayer.OnInitializedListener initializedListener;
    String[] songs_selected =
            {"UvAPcNPXVDQ"
            ,"rTuxUAuJRyY"
            ,"sddTKvsHVTo"
            ,"EpEraRui1pc"
            ,"jBQpGiubj0c"};

    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songs = new ArrayList<>();
        listView = (ListView)findViewById(R.id.playList);
        youTubePlayer_con = null;


        songs = loadArrayList(songs);
        Log.i(TAG, "onCreate: "+songs.size());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,songs);
        listView.setAdapter(arrayAdapter);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);

        initializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.i(TAG, "onInitializationSuccess: "+currentPlay + "is selected");

                youTubePlayer_con = youTubePlayer;
                if(youTubePlayer.isPlaying()){
                    youTubePlayer.release();
                }
                youTubePlayer.loadVideo(songs.get(currentPlay));
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(youTubePlayer_con!=null && youTubePlayer_con.isPlaying()){
                    youTubePlayer_con.release();
                    currentPlay = i;
                }
                youTubePlayerView.initialize(GetYoutubeConfig.youtube_api,initializedListener);
            }
        });




    }

    private ArrayList<String> loadArrayList(ArrayList<String> songs) {

        for(int i=0;i<5;i++){
            songs.add(songs_selected[i]);
        }
        return songs;
    }
}
