package com.example.myapplication.SQL_Lite.Authentification

import android.content.Context
import com.example.myapplication.SQL_Lite.DataBase.MaBaseDeDonneesSQL_Lite
import com.example.myapplication.SQL_Lite.Model.User


class AuthentificationController(val context: Context) {

    fun authentifier(user: User) : User?{
        val maBaseDeDonneesSQLLite = MaBaseDeDonneesSQL_Lite(context)

        // Recherche de l'utilisateur par email
        val utilisateurs = maBaseDeDonneesSQLLite.getUtilisateurs()
        val utilisateur = utilisateurs.find { it.email == user.email }

        // Vérification du mot de passe
        if (utilisateur != null && utilisateur.nom == user.nom) {
            return utilisateur
        } else {
            return null
        }
    }
}