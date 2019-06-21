package com.autolink.sayaradz.ui.adapter.brand

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.autolink.sayaradz.ui.adapter.BaseAdapter
import com.autolink.sayaradz.vo.Brand
import com.bumptech.glide.RequestManager
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView
import java.lang.IllegalArgumentException


class BrandsAdapter(private val glide: RequestManager,
                    private val listener:OnBrandsClickListener,
                    private val displayType:String = DEFAULT_LIST_KEY): BaseAdapter<Brand>(glide,BRAND_COMPARATOR), FastScrollRecyclerView.SectionedAdapter{

    companion object {
        private const val TAG  = "BrandsFragment"
        const val CARD_LIST_KEY = "CARD_LIST_KEY"
        const val DEFAULT_LIST_KEY = "DEFAULT_LIST_KEY"

        val BRAND_COMPARATOR  = object : DiffUtil.ItemCallback<Brand>(){
            override fun areItemsTheSame(oldItem: Brand, newItem: Brand): Boolean {
                return  oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Brand, newItem: Brand): Boolean {
                return  oldItem.name == newItem.name
            }
        }
    }

    interface OnBrandsClickListener{
        fun onBrandClick(brand:Brand)
    }


    override fun getSectionName(position: Int): String  = getItem(position)?.name?.substring(0,1).toString()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when(displayType){
        CARD_LIST_KEY -> BrandCardViewHolder.create(parent,glide,listener)
        else -> BrandViewHolder.create(parent,glide,listener)
    }






}