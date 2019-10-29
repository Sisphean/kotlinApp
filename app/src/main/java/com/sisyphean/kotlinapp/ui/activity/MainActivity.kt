package com.sisyphean.kotlinapp.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sisyphean.kotlinapp.R
import com.sisyphean.kotlinapp.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val mFragments: ArrayList<Fragment> = arrayListOf()

    private val mFragmentManager : FragmentManager by lazy {
        supportFragmentManager
    }

    private var mLastFragmentId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

    }

    private fun initView() {
        navigation_btn.setOnNavigationItemSelectedListener(this)
        initFragments()
        switchFragment(0)
    }

    private fun initFragments() {
        val homeFragment = HomeFragment.getInstance()
        val tickerFragment = HomeFragment.getInstance()
        val tradeFragment = HomeFragment.getInstance()
        val assetFragment = HomeFragment.getInstance()

        mFragments.add(homeFragment)
        mFragments.add(tickerFragment)
        mFragments.add(tradeFragment)
        mFragments.add(assetFragment)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.tab_home -> switchFragment(0)
            R.id.tab_ticker -> switchFragment(1)
            R.id.tab_trade -> switchFragment(2)
            R.id.tab_asset -> switchFragment(3)
        }
        return true
    }

    private fun switchFragment(position: Int) {
        val curFragment = mFragments[position]
        val lastFragment = mFragments[mLastFragmentId]
        mLastFragmentId = position
        val transaction = mFragmentManager.beginTransaction()
        transaction.hide(lastFragment)
        if (!curFragment.isAdded) {
            transaction.add(R.id.container, curFragment)
        }
        transaction.show(curFragment)
        transaction.commit()
    }
}
