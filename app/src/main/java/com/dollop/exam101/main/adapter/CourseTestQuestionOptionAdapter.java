package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.R;
import com.dollop.exam101.databinding.ItemCourseTestQuestionOptionListBinding;
import com.dollop.exam101.main.activity.CourseTestActivity;
import com.dollop.exam101.main.model.OptionModel;
import com.dollop.exam101.main.model.QuestionModel;

import java.util.List;

public class CourseTestQuestionOptionAdapter extends RecyclerView.Adapter<CourseTestQuestionOptionAdapter.MyViewHolder> {
    Context context;
    List<OptionModel> optionModelArrayList;
    int row_index = -1;
    QuestionModel questionListModel;

    public CourseTestQuestionOptionAdapter(Context context, List<OptionModel> list, QuestionModel questionListModel) {
        this.context = context;
        this.optionModelArrayList = list;
        this.questionListModel = questionListModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseTestQuestionOptionListBinding binding = ItemCourseTestQuestionOptionListBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @SuppressLint({"ResourceAsColor", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Utils.E("optionModelArrayListOPPPPPPP:::++" + optionModelArrayList);
        OptionModel optionModel = optionModelArrayList.get(position);
        holder.binding.tvOption.setText((HtmlCompat.fromHtml(optionModel.options, HtmlCompat.FROM_HTML_MODE_LEGACY)));
        holder.binding.tvOption.setOnClickListener(view ->
        {
            questionListModel.isClicked = true;
            questionListModel.answer = position;
            row_index = position;
            notifyDataSetChanged();
        });
        if (row_index == position) {

            selectItem(holder);

        } else {
            unselectItem(holder);
        }
    }

    private void unselectItem(MyViewHolder holder) {
        holder.binding.cardOption.setStrokeColor(ContextCompat.getColor(context, R.color.color_gray3));
        holder.binding.cardOption.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
           /* Log.d("Position On Click", " " + position);
            Toast.makeText(context, "Postion On Click " + position, Toast.LENGTH_SHORT).show();*/
    }

    private void selectItem(MyViewHolder holder) {
        holder.binding.cardOption.setStrokeColor(ContextCompat.getColor(context, R.color.green));
        holder.binding.cardOption.setBackgroundColor(ContextCompat.getColor(context, R.color.status_bar));

          /*  holder.binding.tvOption.setDrawingCacheBackgroundColor(R.drawable.select_option_background);
            Log.d("Position Not Click"," "+position);
            Toast.makeText(context, "Postion Not Click "+position, Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public int getItemCount() {
        return optionModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemCourseTestQuestionOptionListBinding binding;

        public MyViewHolder(@NonNull ItemCourseTestQuestionOptionListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}