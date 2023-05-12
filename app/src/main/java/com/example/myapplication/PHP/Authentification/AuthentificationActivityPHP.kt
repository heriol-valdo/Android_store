package com.example.myapplication.PHP.Authentification

import com.example.myapplication.PHP.Authentification.AuthentificationControllerPHP



import android.content.Intent
import android.os.Bundle


import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.SQL_Lite.ListUser_.ListUser
import com.example.myapplication.SQL_Lite.Model.User
import com.example.myapplication.R
import com.example.myapplication.SQL_Lite.Authentification.AuthentificationController
import com.example.myapplication.PHP.ListUser_.ListUserPHP
import com.example.myapplication.PHP.Model.UserPHP

class AuthentificationActivityPHP : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    //fonction d'authentification sur une bases de donnees PHP
    fun  AuthUtilisateur( view: View){
        val nomEditText = findViewById<EditText>(R.id.nom)
        val emailEditText = findViewById<EditText>(R.id.email)

        val nom = nomEditText.text.toString()
        val email = emailEditText.text.toString()

        if (nom.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Adresse e-mail invalide", Toast.LENGTH_SHORT).show()
            return
        }

        val user = UserPHP(nom, email)
        val Auth = AuthentificationControllerPHP(this).authentifier(user)

        if(Auth == null ){Toast.makeText(this, "l'utilisateur est invalide", Toast.LENGTH_SHORT).show()}
        else{  val intent = Intent(this, ListUserPHP:: class.java)
            startActivity(intent)}


    }





}