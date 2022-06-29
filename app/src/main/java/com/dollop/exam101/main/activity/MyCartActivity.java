package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.main.fragment.adapter.MyCartAdapter;
import com.dollop.exam101.main.model.MyCartModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class MyCartActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity=MyCartActivity.this;
    ActivityMyCartBinding binding;
    BottomSheetDialog bottomSheetDialog;
    ArrayList<MyCartModel> myCartModelArrayList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

        MyCartModel myCartModel = new MyCartModel();
        MyCartModel myCartModel1 = new MyCartModel();
        MyCartModel myCartModel2 = new MyCartModel();


        myCartModel.Point = "Microsoft power Point";
        myCartModel.FundamentalsDesign = "Best way learn fundamentals of\ndesign thinking.";
        myCartModel.Rupees = "₹500";
        myCartModel.Wishlist = "Add to Wishlist";
        myCartModel.Remove = "Remove";


        myCartModel1.Point = "Microsoft power Point";
        myCartModel1.FundamentalsDesign = "Best way learn fundamentals of\ndesign thinking.";
        myCartModel1.Rupees = "₹500";
        myCartModel1.Wishlist = "Add to Wishlist";
        myCartModel1.Remove = "Remove";


        myCartModel2.Point = "Microsoft power Point";
        myCartModel2.FundamentalsDesign = "Best way learn fundamentals of\ndesign thinking.";
        myCartModel2.Rupees = "₹500";
        myCartModel2.Wishlist = "Add to Wishlist";
        myCartModel2.Remove = "Remove";

        myCartModelArrayList.clear();
        myCartModelArrayList.add(myCartModel);
        myCartModelArrayList.add(myCartModel1);
        myCartModelArrayList.add(myCartModel2);

        binding.rvPackageListId.setHasFixedSize(true);
        binding.rvPackageListId.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvPackageListId.setAdapter(new MyCartAdapter(activity, myCartModelArrayList));
    }


    private void init() {
        binding.CardView.setOnClickListener(this);
        binding.CardViewOne.setOnClickListener(this);
        binding.tvButtonCheckoutId.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.CardView) {
            bottomSheetDialog = new BottomSheetDialog(MyCartActivity.this);
            bottomSheetDialog.setContentView(R.layout.bottomsheet_applycoupon);
            bottomSheetDialog.show();
        }else if(view==binding.CardViewOne){
            bottomSheetDialog = new BottomSheetDialog(MyCartActivity.this);
            bottomSheetDialog.setContentView(R.layout.bottomsheet_referralcode);
            bottomSheetDialog.show();
        }else if (view==binding.tvButtonCheckoutId){
            Utils.I(activity,PaymentFailedActivity.class,null);
        }
    }
}
