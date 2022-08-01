package com.atech.newapp.ui.fragment.home


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.atech.core.models.Article
import com.atech.newapp.R
import com.atech.newapp.databinding.RowNewsBinding
import com.atech.newapp.ui.utils.convertDateToTime
import com.atech.newapp.ui.utils.loadImage
import java.text.SimpleDateFormat

class HomeAdapter(
    private val listener: (Article) -> Unit,
    private val shareClick: (String, String) -> Unit
) : PagingDataAdapter<Article, HomeAdapter.HomeAdapterViewHolder>(DiffUtilArticle()) {


    inner class HomeAdapterViewHolder(
        private val binding: RowNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION)
                    getItem(absoluteAdapterPosition)?.let {
                        listener.invoke(it)
                    }
            }

            binding.imageButtonShare.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION)
                    getItem(absoluteAdapterPosition)?.let {
                        shareClick.invoke(it.title, it.url.toString())
                    }
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun bind(article: Article) = binding.apply {
            binding.root.transitionName = article.title
            textViewTextTitle.text = article.title
            textViewSource.text = article.source?.name
            article.urlToImage.loadImage(
                itemView,
                imageViewTitleImage,
                progressBarNews,
                10
            )

            try {
                val date =
                    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(article.publishedAt!!)
                        ?.convertDateToTime()

                textViewSource.text = binding.root.context.getString(
                    R.string.author_with_date, article.source?.name, date
                )
            } catch (e: Exception) {
                Toast.makeText(binding.root.context, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterViewHolder =
        HomeAdapterViewHolder(
            RowNewsBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HomeAdapterViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

class DiffUtilArticle() : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem == newItem

}