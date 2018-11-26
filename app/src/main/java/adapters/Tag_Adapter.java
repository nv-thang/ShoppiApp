package adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.os.shoppiapp.R;
import com.example.os.shoppiapp.TagActivity;

import java.util.ArrayList;

import models.Model_Tag;

public class Tag_Adapter extends RecyclerView.Adapter<Tag_Adapter.MyViewHolder> {

    private Context context;

    int myPos = 0;
    ArrayList<Model_Tag> todotodaytaskModelArrayList;

    public Tag_Adapter(Context context, ArrayList<Model_Tag> todotodaytaskModelArrayList) {
        this.context = context;
        this.todotodaytaskModelArrayList = todotodaytaskModelArrayList;
    }

    @NonNull
    @Override
    public Tag_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tag, parent, false);
        return new Tag_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tag_Adapter.MyViewHolder holder, int position) {
        Model_Tag modelfoodrecycler = todotodaytaskModelArrayList.get(position);

        holder.text.setText(modelfoodrecycler.getText());
        holder.ll1.setBackgroundResource(R.drawable.img_tag_whats_new);

        if (position == 0) {
            holder.ll1.setBackgroundResource(R.drawable.img_tag_whats_new);

            // holder.hoteltext1.setBackgroundResource(Color.parseColor("ffffff"));
        } else if (position == 1) {

            holder.ll1.setBackgroundResource(R.drawable.img_tag_topwear);
            // holder.hoteltext1.setBackgroundColor(Color.parseColor("#00000000"));
        } else if (position == 2) {

            holder.ll1.setBackgroundResource(R.drawable.img_tag_footwear);
        } else if (position == 3) {

            holder.ll1.setBackgroundResource(R.drawable.img_tag_active);

        } else if (position == 4) {

            holder.ll1.setBackgroundResource(R.drawable.img_tag_accessories);

        }
    }

    @Override
    public int getItemCount() {
        return todotodaytaskModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        FrameLayout ll1;

        public MyViewHolder(final View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text);
            ll1 = itemView.findViewById(R.id.ll1);
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getLayoutPosition();
                    Intent intent = new Intent(v.getContext(), TagActivity.class);
                    intent.putExtra("position", p);
                    v.getContext().startActivity(intent);
                                    }
            });

        }
    }
}
