package com.example.myapplication.PHP

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.PHP.DataBase.MaBaseDeDonneesPHP
import com.example.myapplication.PHP.ListUser_.ListUserPHP
import com.example.myapplication.PHP.Model.UserPHP


class MainActivityPHP : AppCompatActivity() {

    public lateinit var maBaseDeDonneesPHP: MaBaseDeDonneesPHP


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        maBaseDeDonneesPHP = MaBaseDeDonneesPHP(this)
    }


    fun enregistrerUtilisateur(view: View) {
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

        val utilisateur = UserPHP(0, nom, email)

        val id = maBaseDeDonneesPHP.insertDataToMySQL(utilisateur)
        Toast.makeText(this, "Utilisateur enregistré avec l'ID $id", Toast.LENGTH_SHORT).show()

        nomEditText.setText("")
        emailEditText.setText("")
    }

    fun listUser(view: View){
        val intent = Intent(this, ListUserPHP:: class.java)
        startActivity(intent)
       // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}

