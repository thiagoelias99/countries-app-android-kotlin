package com.puc.telias.countriesapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.puc.telias.countriesapp.R
import com.puc.telias.countriesapp.database.AppDatabase
import com.puc.telias.countriesapp.databinding.ActivityLoginBinding
import com.puc.telias.countriesapp.databinding.ActivityRegisterBinding
import com.puc.telias.countriesapp.models.User
import com.puc.telias.countriesapp.repository.CountriesRepository
import com.puc.telias.countriesapp.repository.UsersRepository
import com.puc.telias.countriesapp.webclient.clients.CountryClient
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private val TAG = "RegisterActivity"
    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val repository by lazy {
        UsersRepository(
            AppDatabase.getConnection(this).usersDao(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            if (
                binding.name.text.isNullOrBlank() ||
                binding.userName.text.isNullOrBlank() ||
                binding.password1.text.isNullOrBlank()
            ) {
                Toast.makeText(
                    this,
                    "Todas as Informações devem ser preenchidas",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (
                !binding.password1.text.toString().equals(binding.password2.text.toString())
            ) {
                Toast.makeText(this, "As senhas nã o são iguais", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    repository.save(
                        User(
                            name = binding.name.text.toString(),
                            userName = binding.userName.text.toString(),
                            password = binding.password1.text.toString()
                        )
                    )
                    finish()
                }
            }

        }
    }
}