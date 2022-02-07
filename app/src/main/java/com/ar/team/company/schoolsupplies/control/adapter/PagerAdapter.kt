package com.ar.team.company.schoolsupplies.control.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.ui.fragments.tools.general.GeneralToolsFragment
import com.ar.team.company.schoolsupplies.ui.fragments.tools.special.SpecialToolsFragment
import java.util.ArrayList

class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    // Fields:
    private val fragments: MutableList<Fragment> = ArrayList()
    private val headers: MutableList<String> = ArrayList()

    // Method(InitData):
    fun initData(context: Context) {
        // Adding:
        addData(GeneralToolsFragment(), context.getString(R.string.general_tools_tab_name))
        addData(SpecialToolsFragment(), context.getString(R.string.special_tools_tab_name))
    }

    // Method(AddData):
    private fun addData(fragment: Fragment, header: String) {
        // Adding:
        fragments.add(fragment)
        headers.add(header)
    }

    // Method(Create):
    override fun createFragment(position: Int): Fragment = fragments[position]

    // Method(GetItemCount):
    override fun getItemCount(): Int = fragments.size

    // Method(GetHeaders):
    fun getHeader(position: Int): String = headers[position]
}