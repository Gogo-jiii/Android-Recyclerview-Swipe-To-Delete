package com.example.recyclerviewswipetodelete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ModelClass> arrayList = new ArrayList<>();
    String[] data = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i"};
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        myAdapter = new MyAdapter(this, getData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(myAdapter);

        setSwipeToDelete();
    }

    private void setSwipeToDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                ModelClass modelClass = arrayList.get(viewHolder.getAdapterPosition());

                int position = viewHolder.getAdapterPosition();

                arrayList.remove(viewHolder.getAdapterPosition());

                myAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());

                Snackbar.make(recyclerView, modelClass.getName(), Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayList.add(position, modelClass);

                        myAdapter.notifyItemInserted(position);
                    }
                }).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private ArrayList<ModelClass> getData() {
        for (int i = 0; i < data.length; i++) {
            arrayList.add(new ModelClass(data[i]));
        }
        return arrayList;
    }
}