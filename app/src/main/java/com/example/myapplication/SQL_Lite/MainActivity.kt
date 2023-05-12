package com.example.myapplication.SQL_Lite

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.SQL_Lite.DataBase.MaBaseDeDonneesSQL_Lite
import com.example.myapplication.SQL_Lite.ListUser_.ListUser
import com.example.myapplication.SQL_Lite.Model.User

class MainActivity : AppCompatActivity() {

    public lateinit var maBaseDeDonneesSQLLite: MaBaseDeDonneesSQL_Lite


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        maBaseDeDonneesSQLLite = MaBaseDeDonneesSQL_Lite(this)
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

        val utilisateur = User(0, nom, email)

        val id = maBaseDeDonneesSQLLite.ajouterUtilisateur(utilisateur)
        Toast.makeText(this, "Utilisateur enregistré avec l'ID $id", Toast.LENGTH_SHORT).show()

        nomEditText.setText("")
        emailEditText.setText("")
    }

    fun listUser(view: View){
        val intent = Intent(this, ListUser:: class.java)
        startActivity(intent)
       // overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

}

