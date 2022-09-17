package com.frist.turkey.ui.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frist.turkey.R
import com.frist.turkey.menu.DrawerAdapter
import com.frist.turkey.menu.DrawerItem
import com.frist.turkey.menu.SimpleItem
import com.frist.turkey.ui.home.fragment.ClientDetailFragment.ClientDetailFragment
import com.frist.turkey.ui.home.fragment.HomeFragment
import com.frist.turkey.ui.home.fragment.TyreDetail.TyreDetailFragment
import com.frist.turkey.ui.home.fragment.bilty.BiltyFragment

import com.frist.turkey.ui.home.fragment.driver.SearchDriverDetailFragment
import com.frist.turkey.ui.home.fragment.truckDetail.TruckDetailFragment
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*

class HomeActivity : AppCompatActivity(), DrawerAdapter.OnItemSelectedListener {
    private var screenTitles: Array<String>? = null
    private var screenIcons: Array<Drawable?>? = null
    private var slidingRootNav: SlidingRootNav? = null
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        ivMenu.setOnClickListener {
            slidingRootNav?.openMenu()
        }

        /*val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)*/
        slidingRootNav = SlidingRootNavBuilder(this)
          //  .withToolbarMenuToggle(toolbar)
            .withMenuOpened(false)
            .withContentClickableWhenMenuOpened(false)
            .withSavedState(savedInstanceState)
            .withMenuLayout(R.layout.menu_left_drawer)
            .inject()
        screenIcons = loadScreenIcons()
        screenTitles = loadScreenTitles()
        val v = Arrays.asList(
            createItemFor(POS_HOME).setChecked(true),
            createItemFor(POS_Create_Bilty),
            createItemFor(POSDriver_Details),
            createItemFor(POS_Truck_Details),
            createItemFor(POS_Tyre_Details),
            createItemFor(POS_Clients_Details),
            createItemFor(POS_Performance_Tracker),
            createItemFor(POS_Reports),
            createItemFor(POS_Growth_Chart),
            createItemFor(POS_Settings),
            createItemFor(POS_Contact_Us),
            createItemFor(POS_Logout)
        )
        val adapter = DrawerAdapter(v)
        adapter.setListener(this)
        val Menulist = findViewById<RecyclerView>(R.id.rvMenulist)
        Menulist.isNestedScrollingEnabled = false
        Menulist.layoutManager = LinearLayoutManager(this)
        Menulist.adapter = adapter
        adapter.setSelected(POS_HOME)
    }


    override fun onBackPressed() {

       // supportFragmentManager.findFragmentById(R.id.container)
        when(supportFragmentManager.findFragmentById(R.id.container)){
            is SearchDriverDetailFragment->{
                showFragment(HomeFragment.createFor(screenTitles?.get(0)))
                slidingRootNav?.closeMenu()
            }
            is BiltyFragment->{
                showFragment(HomeFragment.createFor(screenTitles?.get(0)))
                slidingRootNav?.closeMenu()
            }

            is TruckDetailFragment->{
                showFragment(HomeFragment.createFor(screenTitles?.get(0)))
                slidingRootNav?.closeMenu()
            }
            is TyreDetailFragment->{
                showFragment(HomeFragment.createFor(screenTitles?.get(0)))
                slidingRootNav?.closeMenu()
            }
            is ClientDetailFragment->{
                showFragment(HomeFragment.createFor(screenTitles?.get(0)))
                slidingRootNav?.closeMenu()
            }

            else->{
                super.onBackPressed()

            }
        }
    }
    override fun onItemSelected(position: Int) {
        when(position){
            POS_HOME->{
                showFragment(HomeFragment.createFor(screenTitles?.get(position)))
                slidingRootNav?.closeMenu()
            }
            POS_Create_Bilty->{
                showFragment(BiltyFragment.createFor(screenTitles?.get(position)))
                slidingRootNav?.closeMenu()
            }
            POSDriver_Details->{

                showFragment(SearchDriverDetailFragment.createFor(screenTitles?.get(position)))
                slidingRootNav?.closeMenu()
            }
            POS_Truck_Details->{
                showFragment(TruckDetailFragment.createFor(screenTitles?.get(position)))
                slidingRootNav?.closeMenu()
            }
            POS_Tyre_Details->{
                showFragment(TyreDetailFragment.createFor(screenTitles?.get(position)))
                slidingRootNav?.closeMenu()
            }
            POS_Clients_Details->{
                showFragment(ClientDetailFragment.createFor(screenTitles?.get(position)))
                slidingRootNav?.closeMenu()
            }
            POS_Performance_Tracker->{

            }
            POS_Reports->{

            }
            POS_Growth_Chart->{

            }
            POS_Settings->{

            }
            POS_Contact_Us->{

            }
            POS_Logout->{

            }

        }
        if (position == 11) {
            finish()
            slidingRootNav?.closeMenu()
        }



    }

    private  fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    private fun createItemFor(position: Int): DrawerItem<*> {
        return SimpleItem(screenIcons?.get(position)!!, screenTitles?.get(position) ?: "0")
            .withIconTint(color(R.color.white))
            .withTextTint(color(R.color.white))
            .withSelectedIconTint(color(R.color.white))
            .withSelectedTextTint(color(R.color.white))
    }

    private fun loadScreenTitles(): Array<String> {
        return resources.getStringArray(R.array.ld_activityScreenTitles)
    }

    private fun loadScreenIcons(): Array<Drawable?> {
        val ta = resources.obtainTypedArray(R.array.ld_activityScreenIcons)

        val icons = arrayOfNulls<Drawable>(ta.length())
        for (i in 0 until ta.length()) {
            val id = ta.getResourceId(i, 0)
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id)

            }
        }
        ta.recycle()
        return icons
    }

    @ColorInt
    private fun color(@ColorRes res: Int): Int {
        return ContextCompat.getColor(this, res)
    }

    companion object {
        private const val POS_HOME = 0
        private const val POS_Create_Bilty = 1
        private const val POSDriver_Details = 2
        private const val POS_Truck_Details = 3
        private const val POS_Tyre_Details = 4
        private const val POS_Clients_Details = 5
        private const val POS_Performance_Tracker = 6
        private const val POS_Reports = 7
        private const val POS_Growth_Chart = 8
        private const val POS_Settings = 9
        private const val POS_Contact_Us= 10
        private const val POS_Logout= 11

    }
}
