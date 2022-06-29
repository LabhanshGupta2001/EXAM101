package com.dollop.exam101.main.activity;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dollop.exam101.main.adapter.OrderHistoryAdapter;
import com.dollop.exam101.main.model.OrderHistoryModel;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity = OrderHistoryActivity.this;
    ActivityOrderHistoryBinding binding;
    ItemOrderHistoryBinding itemOrderHistoryBinding;
    ArrayList<OrderHistoryModel> orderHistoryModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        itemOrderHistoryBinding = ItemOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(itemOrderHistoryBinding.getRoot());
        setContentView(binding.getRoot());

       // itemOrderHistoryBinding.tvTotalRupees.setPaintFlags(itemOrderHistoryBinding.tvTotalRupees.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        initialise();

        OrderHistoryModel orderHistoryModel = new OrderHistoryModel();
        OrderHistoryModel orderHistoryModel1 = new OrderHistoryModel();
        OrderHistoryModel orderHistoryModel2 = new OrderHistoryModel();
        OrderHistoryModel orderHistoryModel3 = new OrderHistoryModel();
        OrderHistoryModel orderHistoryModel4 = new OrderHistoryModel();
        OrderHistoryModel orderHistoryModel5 = new OrderHistoryModel();

        orderHistoryModel.Data = "02 Jun, 2022";
        orderHistoryModel.TransactionId = "1234567475474155";
        orderHistoryModel.OrderId = "001";
        orderHistoryModel.Day = "20 DAYS";
        orderHistoryModel.TotalRupees = "4500";
        orderHistoryModel.Rupees = "3000";

        orderHistoryModel1.Data = "10 Jun, 2022";
        orderHistoryModel1.TransactionId = "1234567475474166";
        orderHistoryModel1.OrderId = "101";
        orderHistoryModel1.Day = "21 DAYS";
        orderHistoryModel1.TotalRupees = "4000";
        orderHistoryModel1.Rupees = "2700";

        orderHistoryModel2.Data = "14 Jun, 2022";
        orderHistoryModel2.TransactionId = "1234567475474177";
        orderHistoryModel2.OrderId = "202";
        orderHistoryModel2.Day = "30 DAYS";
        orderHistoryModel2.TotalRupees = "5000";
        orderHistoryModel2.Rupees = "3500";

        orderHistoryModel3.Data = "02 Jun, 2022";
        orderHistoryModel3.TransactionId = "1234567475474155";
        orderHistoryModel3.OrderId = "001";
        orderHistoryModel3.Day = "20 DAYS";
        orderHistoryModel3.TotalRupees = "4500";
        orderHistoryModel3.Rupees = "3000";

        orderHistoryModel4.Data = "02 Jun, 2022";
        orderHistoryModel4.TransactionId = "1234567475474155";
        orderHistoryModel4.OrderId = "001";
        orderHistoryModel4.Day = "20 DAYS";
        orderHistoryModel4.TotalRupees = "4500";
        orderHistoryModel4.Rupees = "3000";

        orderHistoryModel5.Data = "02 Jun, 2022";
        orderHistoryModel5.TransactionId = "1234567475474155";
        orderHistoryModel5.OrderId = "001";
        orderHistoryModel5.Day = "20 DAYS";
        orderHistoryModel5.TotalRupees = "4500";
        orderHistoryModel5.Rupees = "3000";


        orderHistoryModelArrayList.clear();
        orderHistoryModelArrayList.add(orderHistoryModel);
        orderHistoryModelArrayList.add(orderHistoryModel1);
        orderHistoryModelArrayList.add(orderHistoryModel2);
        orderHistoryModelArrayList.add(orderHistoryModel3);
        orderHistoryModelArrayList.add(orderHistoryModel4);
        orderHistoryModelArrayList.add(orderHistoryModel5);

        binding.rvOderHistory.setHasFixedSize(true);
        binding.rvOderHistory.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvOderHistory.setAdapter(new OrderHistoryAdapter(activity,orderHistoryModelArrayList));

    }

    private void initialise() {

        binding.ivBack.setOnClickListener(this);

        itemOrderHistoryBinding.tvTotalRupees.setPaintFlags(itemOrderHistoryBinding.tvTotalRupees.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    @Override
    public void onClick(View view) {

        if (view == binding.ivBack) {
            onBackPressed();
        }
    }
}