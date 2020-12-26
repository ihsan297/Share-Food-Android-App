package com.example.foodsharing1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FoodPostAdapter extends RecyclerView.Adapter<FoodPostAdapter.RVViewHolder> {

    private List<FoodPost> mPosts;

    public FoodPostAdapter(List<FoodPost> mPosts) {
        this.mPosts = mPosts;
    }
    @NonNull
    @Override

    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.postrow, parent, false);

        return new RVViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, final int position) {

        holder.FoodLocation.setText(mPosts.get(position).foodloc);
        holder.foodDesc.setText(mPosts.get(position).fooddesc);
        String prefix=mPosts.get(position).foodname;
        holder.foodName.setText(mPosts.get(position).foodname);
        holder.userName.setText("John");
        StorageReference mImageRef =
                FirebaseStorage.getInstance().getReference("Posts/"+prefix+".jpg");
        final long ONE_MEGABYTE = 1024 * 1024;
        mImageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.foogImg.setImageBitmap(bm);

            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               holder.like.setColorFilter(22);
            }
        });



        //       holder.P_Img=ls.get(position).getP_Image();





    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }


    public class RVViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        ImageView foogImg,like;
        TextView FoodLocation,foodDesc,foodName;


        public RVViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName=itemView.findViewById(R.id.foodTitle);
            foodDesc=itemView.findViewById(R.id.postDetails);
            FoodLocation=itemView.findViewById(R.id.itemlocation);
            foogImg=itemView.findViewById(R.id.foodPic);
            userName=itemView.findViewById(R.id.userName);
            like=itemView.findViewById(R.id.like);

        }
    }

}
