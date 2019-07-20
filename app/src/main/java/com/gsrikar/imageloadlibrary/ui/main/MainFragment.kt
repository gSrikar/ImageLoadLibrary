package com.gsrikar.imageloadlibrary.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gsrikar.imageloadlibrary.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val adapter = PhotoAdapter(
        arrayListOf(
            "https://cdn.pixabay.com/photo/2019/03/31/14/38/holland-4093234_960_720.jpg",
            "https://cdn.pixabay.com/photo/2016/09/02/08/32/cuba-1638594_960_720.jpg",
            "https://cdn.pixabay.com/photo/2019/06/22/08/10/state-of-the-union-4291098_960_720.jpg",
            "https://cdn.pixabay.com/photo/2018/05/03/09/56/india-3370930_960_720.jpg",
            "https://cdn.pixabay.com/photo/2019/07/15/15/03/sunflower-4339580_960_720.jpg",
            "https://cdn.pixabay.com/photo/2019/07/17/10/13/musk-beetle-4343564_960_720.jpg",
            "https://cdn.pixabay.com/photo/2019/06/12/21/09/ocean-4270249_960_720.jpg",
            "https://cdn.pixabay.com/photo/2017/06/22/23/40/picking-flowers-2432972_960_720.jpg",
            "https://cdn.pixabay.com/photo/2014/03/14/11/17/dancer-287078_960_720.jpg",
            "https://cdn.pixabay.com/photo/2019/07/16/16/41/gaztelugatze-4342242_960_720.jpg"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setAdapter()
    }

    private fun setAdapter() {
        imageRecyclerView.adapter = adapter
    }

}
