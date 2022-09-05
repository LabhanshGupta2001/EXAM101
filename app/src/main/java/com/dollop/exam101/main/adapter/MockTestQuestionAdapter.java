package com.dollop.exam101.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.databinding.ItemMockTestQuestionBinding;
import com.dollop.exam101.main.activity.MockTestQuestionsActivity;
import com.dollop.exam101.main.model.Option;
import com.dollop.exam101.main.model.Question;

import java.util.List;
import java.util.Objects;

public class MockTestQuestionAdapter extends RecyclerView.Adapter<MockTestQuestionAdapter.MyHolder> {
    Context context;
    public static List<Question> questionList;

    @SuppressLint("NotifyDataSetChanged")
    public MockTestQuestionAdapter(List<Question> questionList, Context context) {
        this.questionList = questionList;
        this.context = context;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMockTestQuestionBinding binding = ItemMockTestQuestionBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyHolder(binding);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Question question = questionList.get(position);
        holder.binding.tvQuestion.setText(HtmlCompat.fromHtml(question.question, 0));
        if (!(question.options == null) && !question.options.isEmpty()) {
            holder.binding.rvRadioClickAns.setLayoutManager(new LinearLayoutManager(context));
            holder.binding.rvRadioClickAns.setAdapter(new TestAnsAdapter(context, question.options, this, position, holder.binding));
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public void SelectOption(int questionPosition, int position, ItemMockTestQuestionBinding binding) {
        Question question = questionList.get(questionPosition);
        int previousSelected = question.options.indexOf(new Option(true));
        if (previousSelected != -1) {
            Option option = question.options.get(previousSelected);
            option.Selected = false;
            question.options.set(previousSelected, option);
            Objects.requireNonNull(binding.rvRadioClickAns.getAdapter()).notifyItemChanged(previousSelected);
            MockTestQuestionsActivity.attemptedCount--; // minus attempted question count
        }
        Option option = question.options.get(position);
        option.Selected = true;
        question.SelectedAnswer = position;
        question.options.set(position, option);
        questionList.set(questionPosition, question);
        Objects.requireNonNull(binding.rvRadioClickAns.getAdapter()).notifyItemChanged(position);
        MockTestQuestionsActivity.attemptedCount++;//plus attempted question count
        // selectedOption.add(position+"");
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ItemMockTestQuestionBinding binding;

        public MyHolder(@NonNull ItemMockTestQuestionBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
