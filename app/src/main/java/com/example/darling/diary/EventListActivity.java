package com.example.darling.diary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Darling on 2017/3/30.
 */

public class EventListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private ListView navList;
    private ArrayAdapter<String> adapter;
    private ActionBar actionBar;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        drawerLayout=(DrawerLayout)super.findViewById(R.id.drawer_layout);
        navList=(ListView)super.findViewById(R.id.left_drawer);
        navList.setOnItemClickListener(this);

        FragmentManager fm =getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
        initActionBar();
        initDrawerLayout();
        initListView();
    }

    protected Fragment createFragment(){
        return new EventListFragment();
    }

    private String[] tabs=new String[]{"登录","同步 ","回收站","我的设置 "};
    /**
     初始化drawer的item列表。这要根据你的app内容来处理，但是一个navigation drawer通常由一个ListView组成，所以列表应该通过一个Adapter填入。
     */
    private void initListView(){
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                tabs);
        navList.setAdapter(adapter);

        navList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?>parent,View view,int position,long id){
                Toast.makeText(EventListActivity.this,"ddd",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initActionBar(){
        actionBar=super.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
    private void initDrawerLayout(){
        toggle=new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_menu_add, R.string.drawer_open,
                R.string.drawer_close){
            /** 当drawer处于完全关闭的状态时调用 */
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            /** 当drawer处于完全打开的状态时调用 */
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };
        // 设置drawer触发器为DrawerListener
        drawerLayout.setDrawerListener(toggle);
    }
    private void toggleNav(){
        if(drawerLayout.isDrawerOpen(Gravity.START)){
            drawerLayout.closeDrawer(Gravity.START);
        }else{
            drawerLayout.openDrawer(Gravity.START);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                toggleNav();
                break;
            case R.id.menu_item_new_event:
                Event event = new Event();
                EventLab.get(this).addEvent(event);
                Intent intent = EventPagerActivity.newIntent(this,event.getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
