package com.example.todotask;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder>  implements Filterable{

    private Context mCtx;
    private List<Task> taskList;
    private  List<Task> filteredUserList;
    ArrayList<String> filterdNames;

    public TasksAdapter(Context mCtx, List<Task> taskList) {
        this.mCtx = mCtx;
        this.taskList = taskList;
        this.filteredUserList = taskList;
        notifyItemChanged(0, filteredUserList.size());
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_item, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Task t = taskList.get(position);
        holder.title_textView.setText(t.getTask());
        holder.description_textview.setText(t.getDesc());
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){

                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredUserList = taskList;
                } else {
                    List<Task> filteredList = new ArrayList<>();
                    for (Task row : taskList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTask().toLowerCase().contains(charString.toLowerCase()) || row.getDesc().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    filteredUserList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredUserList = (ArrayList<Task>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class TasksViewHolder extends RecyclerView.ViewHolder {

        TextView title_textView, description_textview;
        CheckBox checkbox;

        public TasksViewHolder(View itemView) {
            super(itemView);

            title_textView = itemView.findViewById(R.id.title_textView);
            description_textview = itemView.findViewById(R.id.description_textview);
            checkbox = itemView.findViewById(R.id.checkbox);

        }


    }

    /*private static class UserFilter extends Filter {

        private final TasksAdapter adapter;

        private final List<Task> originalList;

        private final List<Task> filteredList;

        private UserFilter(TasksAdapter adapter, List<Task> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (final Task user : originalList) {
                    if (user.getTask().contains(filterPattern)) {
                        filteredList.add(user);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredUserList.addAll((ArrayList<Task>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
*/

}
