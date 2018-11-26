package adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.os.shoppiapp.R;
import com.hsalf.smilerating.BaseRating;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import models.Order;
import models.Review;

public class Review_Adapter extends RecyclerView.Adapter<Review_Adapter.ViewHolder> {

    Context context;
    ArrayList<Review> review_models;

    public Review_Adapter(Context context, ArrayList<Review> review_models) {
        this.context = context;
        this.review_models = review_models;
    }

    @NonNull
    @Override
    public Review_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new Review_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Review_Adapter.ViewHolder holder, final int position) {
        holder.reviewer.setText(review_models.get(position).getNameReviewer());
        holder.status.setText(review_models.get(position).getStatus());
        Picasso.get().load(review_models.get(position).getImgReviewer()).error(R.drawable.img_loading).into(holder.imageView);

        if (review_models.get(position).getSmile() == 0) {
            holder.img.setImageResource(R.drawable.terrible);
        }
        if (review_models.get(position).getSmile() == 1) {
            holder.img.setImageResource(R.drawable.bad);
        }
        if (review_models.get(position).getSmile() == 2) {
            holder.img.setImageResource(R.drawable.okay);
        }
        if (review_models.get(position).getSmile() == 3) {
            holder.img.setImageResource(R.drawable.good);
        }
        if (review_models.get(position).getSmile() == 4) {
            holder.img.setImageResource(R.drawable.great);
        }

//        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onItemClickedListener != null) {
//                    onItemClickedListener.onItemClick(cart_models.get(position).getId());
//                    Toast.makeText(v.getContext(), (position)+" ", Toast.LENGTH_SHORT).show();
//                    cart_models.remove(position);
//                    cart_models.clear();
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return review_models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reviewer, status;
        ImageView img;
        CircleImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            reviewer = itemView.findViewById(R.id.tv_item_reviewer);
            status = itemView.findViewById(R.id.tv_item_status);
            img = itemView.findViewById(R.id.img_item_review);
            imageView = itemView.findViewById(R.id.img_item_reviewer);

        }
    }

    public interface OnItemClickedListener {
        void onItemClick(String id);
    }

    private Cart_Adapter.OnItemClickedListener onItemClickedListener;

    public void setOnItemClickedListener(Cart_Adapter.OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}