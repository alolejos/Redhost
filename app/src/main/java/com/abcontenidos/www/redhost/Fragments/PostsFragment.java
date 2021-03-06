package com.abcontenidos.www.redhost.Fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.abcontenidos.www.redhost.Activities.CommerceActivity;
import com.abcontenidos.www.redhost.Activities.ForgotActivity;
import com.abcontenidos.www.redhost.Dbases.MyDbHelper;
import com.abcontenidos.www.redhost.Recyclers.ListAdapterInstagram;
import com.abcontenidos.www.redhost.Recyclers.MyRecyclerViewAdapter;
import com.abcontenidos.www.redhost.Objets.Post;
import com.abcontenidos.www.redhost.Dbases.PostDao;
import com.abcontenidos.www.redhost.Activities.PostInfo;
import com.abcontenidos.www.redhost.R;
import com.abcontenidos.www.redhost.Objets.User;
import com.abcontenidos.www.redhost.Dbases.UserDao;

import java.util.ArrayList;

public class PostsFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener, ListAdapterInstagram.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    ListAdapterInstagram adapter1;
    Intent i;
    public User user;
    UserDao userDao;
    PostDao postDao;
    ArrayList<Post> list;
    RecyclerView recyclerView;
    TextView texto;
    Boolean llave = true;
    SearchView busqueda;

    public PostsFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        // carga elementos visuales
        texto = view.findViewById(R.id.texto);

        // lectura del usuario de la base de datos
        MyDbHelper helper = new MyDbHelper(getActivity(), "user");
        SQLiteDatabase db = helper.getWritableDatabase();
        userDao = new UserDao(db);
        user = userDao.get();

        // lectura de los posts
        MyDbHelper helperPosts = new MyDbHelper(getActivity(), "posts");
        SQLiteDatabase db1 = helperPosts.getWritableDatabase();
        postDao= new PostDao(db1);
        list = postDao.getall();
        db.close();
        db1.close();

        if(list.size()!=0){
            texto.setVisibility(View.INVISIBLE);
        }

        // carga del Recyclerview

        recyclerView = view.findViewById(R.id.recycler_main);
        int numberColumnsGrid = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberColumnsGrid));
        adapter = new MyRecyclerViewAdapter(getActivity(), list);
        adapter1 = new ListAdapterInstagram(getActivity(), list);
        adapter.setClickListener(this);
        adapter1.setClickListener(this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onItemClick(View view, int position) {
        i = new Intent(getActivity(), PostInfo.class);
        i.putExtra("key", Integer.valueOf(list.get(position).getId()));
        Log.d("key", list.get(position).getId());
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_comercio:
                Intent intent = new Intent(getContext(), CommerceActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_grid:
                if(llave){
                    item.setIcon(R.mipmap.grid_icon);
                    llave = false;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(adapter1);
                }else{
                    item.setIcon(R.mipmap.item_icon);
                    llave = true;
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    recyclerView.setAdapter(adapter);
                }
                return true;
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
            if(user.getCommerce_name().equals("null")){
                MenuItem comercio = menu.findItem(R.id.action_comercio);
                comercio.setVisible(false);
            }



        //MenuItem item2 = menu.findItem(R.id.action_grid);
        //item2.setVisible(false);
    }

}



