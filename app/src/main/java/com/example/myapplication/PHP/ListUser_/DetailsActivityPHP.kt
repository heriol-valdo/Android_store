package com.example.myapplication.PHP.ListUser_

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
import com.example.myapplication.PHP.ListUser_.ListUserPHP
import com.example.myapplication.PHP.DataBase.MaBaseDeDonneesPHP
import com.example.myapplication.PHP.Model.UserPHP

class DetailsActivityPHP : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val utilisateur = intent.getSerializableExtra("utilisateur") as UserPHP

        val nomTextView = findViewById<EditText>(R.id.nom_text_view)
        val emailTextView = findViewById<EditText>(R.id.email_text_view)
        nomTextView.setText(utilisateur.nom)
        emailTextView.setText(utilisateur.email)
    }


    fun DeleteUser(view: View){
        val user = intent.getSerializableExtra("utilisateur") as UserPHP
        val nbLignesSupprimees = MaBaseDeDonneesPHP(this).deleteDataFromMySQL(user.id)

        if (nbLignesSupprimees > 0) {
            Toast.makeText(this, "Utilisateur supprimé avec succès", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ListUserPHP:: class.java)
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



        val user = intent.getSerializableExtra("utilisateur") as UserPHP
        val userModier = UserPHP(user.id,nomTextView.text.toString(),emailTextView.text.toString())
        val nbLignesSupprimees = MaBaseDeDonneesPHP(this).updateUserInMySQL(userModier)
        Toast.makeText(this, "Utilisateur a ete modifier", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, ListUserPHP:: class.java)
        startActivity(intent)
    }
}

