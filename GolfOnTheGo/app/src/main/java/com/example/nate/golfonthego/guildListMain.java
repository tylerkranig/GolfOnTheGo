package com.example.nate.golfonthego;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nate.golfonthego.guildBehind.*;

public class guildListMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guild_list_main);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Guild[] guild = {new Guild("Golfer4Life", 1), new Guild("HackingGolf", 2), new Guild("TigerBools", 3)};
        ListAdapter guildAdapter = new guildAdapter(this, guild);
        ListView guildListView = (ListView) findViewById(R.id.listGuilds);
        guildListView.setAdapter(guildAdapter);


    }

    AdapterView.OnItemClickListener guildClick(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Guild guild = (Guild) adapterView.getItemAtPosition(position);
                Toast.makeText(guildListMain.this, guild.get_name(), Toast.LENGTH_LONG).show();
            }
        };
    }
}
