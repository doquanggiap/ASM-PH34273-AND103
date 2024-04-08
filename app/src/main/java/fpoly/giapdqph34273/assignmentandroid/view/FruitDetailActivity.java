package fpoly.giapdqph34273.assignmentandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import fpoly.giapdqph34273.assignmentandroid.adapter.ImageAdapter;
import fpoly.giapdqph34273.assignmentandroid.databinding.ActivityFruitDetailBinding;
import fpoly.giapdqph34273.assignmentandroid.model.Fruit;

public class FruitDetailActivity extends AppCompatActivity {
    ActivityFruitDetailBinding binding;
    Fruit fruit;
    private ImageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityFruitDetailBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);

        setContentView(binding.getRoot());

        showData();
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void showData() {
        //get data object intent
        Intent intent = getIntent();
        fruit = (Fruit) intent.getSerializableExtra("fruit");

        binding.tvName.setText("Name: " + fruit.getName());
        binding.tvPrice.setText("Price: " + fruit.getPrice());
        binding.tvDescription.setText("Description: " + fruit.getDescription());
        binding.tvQuantity.setText("Quantity: " + fruit.getQuantity());
        binding.tvStatus.setText("Status: "+fruit.getStatus());

//        String url = fruit.getImage().get(0);
//        String newUrl = url.replace("localhost", "10.0.2.2");
//
//        Glide.with(this)
//                .load(newUrl)
//                .thumbnail(Glide.with(this).load(R.drawable.baseline_broken_image_24))
//                .into(binding.img);

        adapter = new ImageAdapter(this, fruit.getImage());
        binding.rcvImg.setAdapter(adapter);



    }
}