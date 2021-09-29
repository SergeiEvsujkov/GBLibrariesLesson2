package com.example.gb_libs_lesson1.view.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gb_libs_lesson1.databinding.ItemUserBinding
import com.example.gb_libs_lesson1.presentation.IUserListPresenter
import com.example.gb_libs_lesson1.view.UserItemView

class UsersRVAdapter(private val presenter: IUserListPresenter) : RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }
    }

    override fun getItemCount(): Int = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        presenter.bindView(holder.apply { pos = position })
    }

    class ViewHolder(private val vb: ItemUserBinding) : RecyclerView.ViewHolder(vb.root), UserItemView {

        override var pos: Int = -1

        override fun showLogin(login: String) {
            vb.tvLogin.text = login
        }
    }
}