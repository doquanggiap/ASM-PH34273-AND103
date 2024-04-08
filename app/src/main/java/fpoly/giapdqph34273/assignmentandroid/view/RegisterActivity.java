package fpoly.giapdqph34273.assignmentandroid.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import fpoly.giapdqph34273.assignmentandroid.databinding.ActivityRegisterBinding;
import fpoly.giapdqph34273.assignmentandroid.model.Response;
import fpoly.giapdqph34273.assignmentandroid.model.User;
import fpoly.giapdqph34273.assignmentandroid.services.HttpRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private HttpRequest httpRequest;
    private File file;

    private File createFileFormUri (Uri path, String name) {
        File _file = new File(RegisterActivity.this.getCacheDir(), name + ".png");
        try {
            InputStream in = RegisterActivity.this.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) >0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            Log.d("123123", "createFileFormUri: " +_file);
            return _file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        httpRequest = new HttpRequest();


        userListener();

    }


    private void userListener() {
        binding.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                Log.d("123123", "onClick: " +123123);
            }
        });

        binding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody _username = RequestBody.create(MediaType.parse("multipart/form-data"),binding.edUsername.getText().toString().trim());
                RequestBody _password = RequestBody.create(MediaType.parse("multipart/form-data"),binding.edPassword.getText().toString().trim());
                RequestBody _email = RequestBody.create(MediaType.parse("multipart/form-data"),binding.edEmail.getText().toString().trim());
                RequestBody _name = RequestBody.create(MediaType.parse("multipart/form-data"),binding.edName.getText().toString().trim());
                MultipartBody.Part multipartBody;
                if (file !=null) {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"),file);
                    multipartBody = MultipartBody.Part.createFormData("avartar",file.getName(),requestFile);
                }else {
                    multipartBody = null;
                }
                Log.d("zzzzzz", "onClick: " + _username.toString());
                httpRequest.callAPI().register(_username,_password,_email,_name,multipartBody).enqueue(responseUser);


                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });
    }
    Callback<Response<User>> responseUser = new Callback<Response<User>>() {
        @Override
        public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
            if (response.isSuccessful()) {
                Log.d("123123", "onResponse: " + response.body().getStatus());
                if (response.body().getStatus() ==200) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại lỗi" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<User>> call, Throwable t) {
            t.getMessage();
        }
    };

    private void chooseImage() {
//        if (ContextCompat.checkSelfPermission(RegisterActivity.this,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("123123", "chooseAvatar: " +123123);
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            getImage.launch(intent);
//        }else {
//            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//
//        }
    }
    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == Activity.RESULT_OK) {
                        Intent data = o.getData();
                        Uri imageUri = data.getData();

                        Log.d("RegisterActivity", imageUri.toString());

                        Log.d("123123", "onActivityResult: "+data);

                        file = createFileFormUri(imageUri, "avatar");

                        //binding.avatar.setImageURI(imageUri);

                        Glide.with(binding.avatar)
                                .load(imageUri)
                                .centerCrop()
                                .circleCrop()
                                .into(binding.avatar);

//                        Glide.with(RegisterActivity.this)
//                                .load(file)
//                                .thumbnail(Glide.with(RegisterActivity.this).load(R.drawable.baseline_broken_image_24))
//                                .centerCrop()
//                                .circleCrop()
////                                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                .skipMemoryCache(true)
//                                .into(binding.avatar);
                    }
                }
            });





}