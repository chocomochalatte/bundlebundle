package com.example.bundlebundle.product.slider

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bundlebundle.R
import com.example.bundlebundle.databinding.FragmentViewPagerBinding
import com.example.bundlebundle.databinding.ItemPagerBinding

class MyPagerViewHolder(val binding: ItemPagerBinding) :
    RecyclerView.ViewHolder(binding.root)

class MyPagerAdapter(private val myData: MutableList<Int>) :
    RecyclerView.Adapter<MyPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPagerViewHolder {
        return MyPagerViewHolder(
            ItemPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return myData.size
    }

    override fun onBindViewHolder(holder: MyPagerViewHolder, position: Int) {
        val binding = holder.binding
        binding.itemPageImageView.setImageResource(myData[position])
    }
}

class ViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myData = mutableListOf<Int>()
        myData.add(R.drawable.cherry)
        myData.add(R.drawable.cherry)
        myData.add(R.drawable.cherry)

        binding.viewpager.adapter = MyPagerAdapter(myData)
    }
}