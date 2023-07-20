package com.example.mrfmovie.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mrfmovie.databinding.ActivityMainBinding

import android.content.Intent
import com.example.mrfmovie.R
import com.example.mrfmovie.adapter.ViewPagerAdapter
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser!=null){
            setupTab()
        } else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun setupTab() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "Home")
        adapter.addFragment(ProfileFragment(), "User")

        binding.viewPager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.viewPager)

        binding.tabs.getTabAt(0)!!.setIcon(R.drawable.ic_baseline_home_24)
        binding.tabs.getTabAt(1)!!.setIcon(R.drawable.ic_baseline_account_circle_24)
    }
}