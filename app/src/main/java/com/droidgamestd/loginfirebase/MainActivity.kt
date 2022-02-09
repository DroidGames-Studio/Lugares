package com.droidgamestd.loginfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.Toast
import com.droidgamestd.loginfirebase.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        auth = Firebase.auth


        binding.btLogin.setOnClickListener{
                hacerLogin()

        }

        binding.btRegister.setOnClickListener{
            hacerRegister()

        }
    }




    private fun hacerRegister(){
        var email = binding.etUsername.text.toString()
        var pass = binding.etPass.text.toString()

        auth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener(this) {
                    task ->
                if(task.isSuccessful){
                    Log.d("UsuarioCreate","El usuario ha sido registrado correctamente")
                    Toast.makeText(this,"El usuario ha sido registrado correctamente", Toast.LENGTH_LONG).show()
                    var user = auth.currentUser
                    actualiza(user)
                }else{
                    Log.d("creando usuario","Ha ocurrido un error al registrar su usuario")
                    Toast.makeText(this,"Ha ocurrido un error al registrar su usuario", Toast.LENGTH_LONG).show()
                    actualiza(null)
                }

            }

    }

    private fun hacerLogin(){
        var email = binding.etUsername.text.toString()
        var pass = binding.etPass.text.toString()

        auth.signInWithEmailAndPassword(email,pass)
            .addOnCompleteListener(this){
                    task ->
                if(task.isSuccessful){
                    Log.d("Authentication","Logged")
                    val user = auth.currentUser
                    actualiza(user)
                }else{
                    Log.d("","")
                    Toast.makeText(this,"Ha ocurrido un error al iniciar sesion", Toast.LENGTH_LONG).show()
                    actualiza(null)
                }
            }

    }

    private fun actualiza(user: FirebaseUser?){
        if(user != null){
            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val usuario = auth.currentUser
        actualiza(usuario)
    }
}