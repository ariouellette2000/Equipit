package com.ouellette.equipit.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ouellette.equipit.Details;
import com.ouellette.equipit.R;
import com.ouellette.equipit.model.Product;
import com.ouellette.equipit.model.Receipt;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterTwo extends RecyclerView.Adapter<RecyclerViewAdapterTwo.MyViewHolder> {
    private Context context;
    private List<Product> productList;
    private RecyclerViewAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public RecyclerViewAdapterTwo(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public RecyclerViewAdapterTwo(List<Product> productList, RecyclerViewAdapter.OnItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);

        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterTwo.MyViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.p_name_row.setText(product.getP_name());
        holder.p_company_row.setText(product.getP_company());

        holder.bind(productList.get(position), listener);
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView p_name_row;
        private TextView p_company_row;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            p_name_row = itemView.findViewById(R.id.p_name_row);
            p_company_row = itemView.findViewById(R.id.p_company_row);

        }

        public void bind(final Product product, final RecyclerViewAdapter.OnItemClickListener listener) {
            p_name_row.setText(product.getP_name());
            p_company_row.setText(product.getP_company());

            //Parcelable object 1 next lines
            final int position = this.getAdapterPosition();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(product);
                    Context context = view.getContext();
                    //Parcelable object 7 next lines
                    Product productobj = productList.get(position);
                    Gson gson = new Gson();
                    String productAsString = gson.toJson(productobj);

                    Intent intent = new Intent(context, Details.class);
                    intent.putExtra("productAsString", productAsString);
                    context.startActivity(intent);
                }
            });
        }
    }
}
