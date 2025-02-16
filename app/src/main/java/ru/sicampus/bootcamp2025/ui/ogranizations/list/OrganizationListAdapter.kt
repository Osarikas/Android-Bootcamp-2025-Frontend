package ru.sicampus.bootcamp2025.ui.ogranizations.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.sicampus.bootcamp2025.databinding.ItemOrganizationBinding
import ru.sicampus.bootcamp2025.domain.entities.OrganizationEntity

class OrganizationListAdapter(
    private val onItemClick: (OrganizationEntity) -> Unit
) : PagingDataAdapter<OrganizationEntity, OrganizationListAdapter.ViewHolder>(DiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemOrganizationBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }

    }
    inner class ViewHolder(
        private val binding: ItemOrganizationBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: OrganizationEntity) {
            println("item $item")
            binding.name.text = "${item.name}"
            binding.count.text = "Волонтеры: ${item.peopleCount}"
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }

    }
    object DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<OrganizationEntity>() {
        override fun areItemsTheSame(oldItem: OrganizationEntity, newItem: OrganizationEntity): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: OrganizationEntity, newItem: OrganizationEntity): Boolean {
            return oldItem == newItem
        }
    }


}