package com.example.teacher.atndapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.teacher.atndapp.adapter.EventAdapter;
import com.example.teacher.atndapp.dto.EventDto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EventAdapter mEventAdapter;
    private String mCurrentSearchKeyword;

    @BindView(R.id.list_evet)
    ListView listEvent;

    @BindView(R.id.edit_search_keyword)
    EditText editSearchKeyword;

    @BindView(R.id.swipeList)
    SwipeRefreshLayout swipeRefreshLayout;

    @OnClick(R.id.btn_search)
    public void onBtnSearch(){
        String keyword = editSearchKeyword.getText().toString();
        if(TextUtils.isEmpty(keyword)){
           Toast.makeText(this,"validation error",Toast.LENGTH_LONG).show();
            return;
        }
        mCurrentSearchKeyword = keyword;
        search(mCurrentSearchKeyword);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                search(mCurrentSearchKeyword);

                // プログレスstop
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        });

        mEventAdapter = new EventAdapter(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_top) {
            // トップを押したときの処理

        } else if (id == R.id.nav_favorite) {
            // お気に入りを押したときの処理
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void search(final String keyword){
        String url = String.format(
                "http://api.atnd.org/events/?keyword_or=%s&format=json",keyword);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        final List<EventDto> lst = new ArrayList<>();

                        try {
                            JSONArray eventArray = response.getJSONArray("events");
                            for(int i = 0; i < eventArray.length(); i++){
                                JSONObject event = eventArray.getJSONObject(i).getJSONObject("event");
                                EventDto eventDto = new EventDto();
                                String id = event.getString("event_id");
                                eventDto.setEventID(id.isEmpty() ? -1 : Integer.parseInt(id));
                                eventDto.setTitle(event.getString("title"));
                                eventDto.setAddress(event.getString("address"));
                                eventDto.setStartAt(event.getString("started_at"));
                                eventDto.setDescription(event.getString("description"));
                                lst.add(eventDto);
                            }

//                            List<String> titleLst = new ArrayList<>();
//                            for(EventDto dto : lst){
//                                titleLst.add(dto.getTitle());
//                            }

//                            ArrayAdapter adapter =
//                                    new ArrayAdapter<String>(MainActivity.this,
//                                            android.R.layout.simple_list_item_1,
//                                            titleLst);
                            mEventAdapter.add(lst);
                            listEvent.setAdapter(mEventAdapter);

                            listEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    EventDto dto = lst.get(position);
                                    Intent intent = new Intent(
                                            MainActivity.this,
                                            EventDetailActivity.class
                                    );
                                    intent.putExtra(
                                            EventDetailActivity.EXTRA_WEB_VIEW_HTML_LOAD_DATA,
                                            dto.getDescription()
                                    );
                                    startActivity(intent);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"server error",Toast.LENGTH_LONG).show();
                    }
                });

        VolleyManager.getInstance(this).addToRequestQueue(jsObjRequest);
    }
}
