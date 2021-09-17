package com.example.android.politicalpreparedness.election.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.network.models.Election

    @BindingAdapter("electionsListData")
    fun electionsRecyclerView(recyclerView: RecyclerView, data: List<Election>?) {
        val adapter = recyclerView.adapter as ElectionListAdapter
        adapter.submitList(data)
    }
