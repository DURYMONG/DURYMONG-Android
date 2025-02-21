package com.example.durymong.view.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.example.durymong.MainActivity
import com.example.durymong.databinding.ActivityCreateMongBinding
import com.example.durymong.util.SharedPreferencesHelper

class CreateMongActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateMongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateMongComplete.setOnClickListener {
            val mongName = binding.etCreateMongName.text.toString().trim()

            if (mongName.isEmpty()) {
                Toast.makeText(this, "몽의 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            SharedPreferencesHelper.setMongSelected(true)

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}