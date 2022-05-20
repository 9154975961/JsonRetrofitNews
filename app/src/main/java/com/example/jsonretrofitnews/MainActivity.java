package com.example.jsonretrofitnews;

import static com.example.jsonretrofitnews.R.drawable.start;
import static com.example.jsonretrofitnews.R.drawable.stop;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jsonretrofitnews.api.ApiClient;
import com.example.jsonretrofitnews.api.MessagesApi;
import com.example.jsonretrofitnews.models.Article;
import com.example.jsonretrofitnews.models.Message;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private TextView newTitle, author, newDescription;
    private Button nextBtn, backBtn;
    private ImageView newImage;
    List<Article> art = new ArrayList<>();

    int count = 0;
    boolean flag = false;

    private final String KEY = "956f2b42027e402a8e2b9ff7bafc8a01";
    private final String COUNTRY = "ru";

    Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newTitle = (TextView) findViewById(R.id.new_title);
        author = (TextView) findViewById(R.id.author);
        newDescription = (TextView) findViewById(R.id.new_Description);
        nextBtn = (Button) findViewById(R.id.next_btn);
        backBtn = (Button) findViewById(R.id.back_btn);

        newImage = (ImageView) findViewById(R.id.new_image);
        newImage.setImageResource(start);

        LoadJson();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int size = art.size();

                if (count != size-1) {
                    if (flag) {
                        count++;
                    } else {
                        if (!flag) {
                            flag = true;
                        }
                    }

                } else {
                    Toast.makeText(MainActivity.this, "Это последняя новость", Toast.LENGTH_LONG).show();
                }
                LoadView();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count!=0) {
                    count--;
                } else {
                    Toast.makeText(MainActivity.this, "Это первая новость", Toast.LENGTH_LONG).show();
                }
                LoadView();
            }
        });
    }

    public void LoadView(){

        Article str = art.get(count);

        newTitle.setText(str.getTitle());
        newDescription.setText(str.getDescription());
        author.setText(str.getSource().getName());

        if (str.getUrlToImage()!=null) {
            Picasso.get().load(str.getUrlToImage()).into(newImage);
        } else newImage.setImageResource(stop);

    }

    public void LoadJson(){

        MessagesApi messagesApi = ApiClient.getRetrofit().create(MessagesApi.class);

        Call<Message> call;
        call = messagesApi.getMessages(COUNTRY, KEY);

        call.enqueue(new Callback<Message>() {

            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {

                if (response.isSuccessful() && response.body() != null) {

                    message = response.body();
                    art = message.getArticles();

                } else {
                    Toast.makeText(MainActivity.this, "No Result", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}