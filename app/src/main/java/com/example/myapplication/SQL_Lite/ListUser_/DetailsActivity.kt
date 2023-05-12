package com.example.myapplication.SQL_Lite.ListUser_

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText

import android.widget.Toast
import com.example.myapplication.SQL_Lite.DataBase.MaBaseDeDonneesSQL_Lite
import com.example.myapplication.SQL_Lite.Model.User
import com.example.myapplication.R

class DetailsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val utilisateur = intent.getSerializableExtra("utilisateur") as User

        val nomTextView = findViewById<EditText>(R.id.nom_text_view)
        val emailTextView = findViewById<EditText>(R.id.email_text_view)
        nomTextView.setText(utilisateur.nom)
        emailTextView.setText(utilisateur.email)
    }


    fun DeleteUser(view: View){
        val user = intent.getSerializableExtra("utilisateur") as User
        val nbLignesSupprimees = MaBaseDeDonneesSQL_Lite(this).supprimerUtilisateur(user.int)

        if (nbLignesSupprimees > 0) {
            Toast.makeText(this, "Utilisateur supprimé avec succès", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ListUser:: class.java)
            startActivity(intent)
           // finish() // fermer l'activité de détail après la suppression
        } else {
            Toast.makeText(this, "Erreur lors de la suppression de l'utilisateur", Toast.LENGTH_SHORT).show()
        }
    }

    fun ModifierUser(view: View){
        val nomTextView = findViewById<EditText>(R.id.nom_text_view)
        val emailTextView = findViewById<EditText>(R.id.email_text_view)

        if (nomTextView.text.toString().isEmpty() || emailTextView.text.toString().isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailTextView.text.toString()).matches()) {
            Toast.makeText(this, "Adresse e-mail invalide", Toast.LENGTH_SHORT).show()
            return
        }



        val user = intent.getSerializableExtra("utilisateur") as User
        val userModier = User(user.int,nomTextView.text.toString(),emailTextView.text.toString())
        val nbLignesSupprimees = MaBaseDeDonneesSQL_Lite(this).modifierUtilisateur(userModier)
        Toast.makeText(this, "Utilisateur a ete modifier", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, ListUser:: class.java)
        startActivity(intent)
    }
}

