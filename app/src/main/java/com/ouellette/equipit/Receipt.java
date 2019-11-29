package com.ouellette.equipit;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ouellette.equipit.data.DatabaseHandler;

import java.sql.Date;

public class Receipt extends Fragment {

    private TextView paydateTV;
    private TextView warrantydateTV;
    private TextView warrantylocaTV;

    public Receipt() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_receipt, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Create receipt instance
        com.ouellette.equipit.model.Receipt receiptobj = new com.ouellette.equipit.model.Receipt();

        //Find View
        paydateTV = view.findViewById(R.id.pay_date_titlef);
        warrantydateTV = view.findViewById(R.id.warranty_date_titlef);
        warrantylocaTV = view.findViewById(R.id.warranty_location_titlef);

        //Get Arguments
        if(getArguments() !=null) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            String receiptAsString = getArguments().getString("receiptobj");
            receiptobj = gson.fromJson(receiptAsString, com.ouellette.equipit.model.Receipt.class);

            //Format date
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            CharSequence paydate = df.format("yyyy-MM-dd", receiptobj.getR_pay_date());
            CharSequence warrantydate = df.format("yyyy-MM-dd", receiptobj.getR_warranty_date());

            //SetText
            paydateTV.setText(paydate);
            warrantydateTV.setText(warrantydate);
            warrantylocaTV.setText(receiptobj.getR_warranty_location());

        }

    }

}
