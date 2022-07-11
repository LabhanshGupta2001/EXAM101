package com.dollop.exam101.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ActivityMyCartBinding;
import com.dollop.exam101.databinding.BottomSheetBlogFilterBinding;
import com.dollop.exam101.databinding.BottomsheetApplycouponBinding;
import com.dollop.exam101.databinding.BottomsheetReferralcodeBinding;
import com.dollop.exam101.main.adapter.MyCartAdapter;
import com.dollop.exam101.main.model.MyCartModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class MyCartActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity;
    ActivityMyCartBinding binding;
    BottomSheetDialog bottomSheetApplyCoupon,bottomSheetDialogReferralCode;
    ArrayList<MyCartModel> myCartModelArrayList = new ArrayList<>();
    RecyclerView recyclerView;

    BottomsheetApplycouponBinding bottomsheetApplycouponBinding;
    BottomsheetReferralcodeBinding bottomsheetReferralcodeBinding;

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
        activity=MyCartActivity.this;

        binding.CardView.setOnClickListener(this);
        binding.CardViewOne.setOnClickListener(this);
        binding.tvButtonCheckoutId.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.CardView) {
            bottomSheetApplyCoupon = new BottomSheetDialog(activity);
            bottomsheetApplycouponBinding = BottomsheetApplycouponBinding.inflate(getLayoutInflater());
            bottomSheetApplyCoupon.setContentView(bottomsheetApplycouponBinding.getRoot());
            bottomSheetApplyCoupon.show();

            bottomsheetApplycouponBinding.tvApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetApplyCoupon.cancel();
                }
            });
        }else if(view==binding.CardViewOne){
            bottomSheetDialogReferralCode = new BottomSheetDialog(activity);
            bottomsheetReferralcodeBinding = BottomsheetReferralcodeBinding.inflate(getLayoutInflater());
            bottomSheetDialogReferralCode.setContentView(bottomsheetReferralcodeBinding.getRoot());
            bottomSheetDialogReferralCode.show();


            bottomsheetReferralcodeBinding.tvApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialogReferralCode.cancel();
                }
            });
        }else if (view==binding.tvButtonCheckoutId){
            Utils.I(activity,PaymentFailedActivity.class,null);
        }
        else if (view==binding.ivBack){
           onBackPressed();
        }
    }
}
