package com.frist.turkey.ui.home.fragment.truckDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frist.turkey.R
import com.frist.turkey.ui.home.fragment.HomeFragment

class TruckDetailFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_truck_detail, container, false)
    }

    companion object {
        private const val EXTRA_TEXT = "text"
        fun createFor(text: String?): TruckDetailFragment {
            val fragment = TruckDetailFragment()
            val args = Bundle()
            args.putString(EXTRA_TEXT, text)
            fragment.setArguments(args)
            return fragment
        }
    }
}