package com.frist.turkey.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.frist.turkey.R
import com.frist.turkey.ui.home.HomeActivity
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.home_fragment.*
import org.jetbrains.annotations.Nullable

class HomeFragment : Fragment(), View.OnClickListener {

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

            initViews()
        initControll()

        val args: Bundle? = getArguments()
        val text = if (args != null) args.getString(EXTRA_TEXT) else ""
       // tvtext.text = text
    /*
        tvtext.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                Toast.makeText(v.getContext(), text, Toast.LENGTH_SHORT).show()
            }
        })
*/


    }

    private fun initControll() {
        cardOpertion.setOnClickListener(this)
        cardPayment.setOnClickListener(this)
        cardDocument.setOnClickListener(this)
        cardMaintenance.setOnClickListener(this)
        cardDesial.setOnClickListener(this)
        cardLocation.setOnClickListener(this)
    }

    private fun initViews() {


      /*  var operationList=ArrayList<homeModel>()
        operationList.add(homeModel("Operations",R.drawable.spalsh))
        operationList.add(homeModel("Payments",R.drawable.spalsh))
        operationList.add(homeModel("Documents",R.drawable.spalsh))
        operationList.add(homeModel("Maintenance",R.drawable.spalsh))
        operationList.add(homeModel("Diesel",R.drawable.spalsh))
        operationList.add(homeModel("Location",R.drawable.spalsh))
        rvHomeFragment.adapter= HomeAdapter(requireContext(),operationList)*/
    }

    companion object {
        private const val EXTRA_TEXT = "text"
        fun createFor(text: String?): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(EXTRA_TEXT, text)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.cardOpertion->{
                Toast.makeText(requireContext(), "card Opertion", Toast.LENGTH_SHORT).show()
            }
            R.id.cardPayment->{
                Toast.makeText(requireContext(), "card Payment", Toast.LENGTH_SHORT).show()
            }
            R.id.cardDocument->{
                Toast.makeText(requireContext(), "card Document", Toast.LENGTH_SHORT).show()
            }
            R.id.cardMaintenance->{
                Toast.makeText(requireContext(), "card Maintenance", Toast.LENGTH_SHORT).show()
            }
            R.id.cardDesial->{
                Toast.makeText(requireContext(), "card Desial", Toast.LENGTH_SHORT).show()
            }
            R.id.cardLocation->{
                Toast.makeText(requireContext(), "card Location", Toast.LENGTH_SHORT).show()
            }
        }
    }
}