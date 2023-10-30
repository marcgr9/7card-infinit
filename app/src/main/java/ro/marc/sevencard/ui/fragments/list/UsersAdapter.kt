package ro.marc.sevencard.ui.fragments.list

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.inSpans
import androidx.recyclerview.widget.RecyclerView
import ro.marc.sevencard.R
import ro.marc.sevencard.data.User
import ro.marc.sevencard.databinding.CompUserBinding

class UsersAdapter(
    private val onClick: (User) -> Unit,
    private val onLongClick: (User) -> Unit,
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val users = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(CompUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(users[position])
        holder.itemView.setOnClickListener {
            onClick(users[position])
        }
        holder.itemView.setOnLongClickListener {
            onLongClick(users[position])
            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(newUsers: List<User>) {
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: CompUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.id.text = user.id.toString()
            if (user.alias == null) {
                binding.alias.text = buildSpannedString {
                    font(ResourcesCompat.getFont(binding.root.context, R.font.italic)) {
                        append(binding.root.context.getString(R.string.list_no_alias))
                    }
                }
            } else {
                binding.alias.text = user.alias
            }
        }
    }

    inline fun SpannableStringBuilder.font(typeface: Typeface? = null, builderAction: SpannableStringBuilder.() -> Unit) =
        inSpans(StyleSpan(typeface?.style ?: Typeface.DEFAULT.style), builderAction = builderAction)

}