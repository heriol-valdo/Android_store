package com.example.myapplication.PHP.Authentification

import android.content.Context
import com.example.myapplication.PHP.DataBase.MaBaseDeDonneesPHP
import com.example.myapplication.SQL_Lite.Model.User

import com.example.myapplication.PHP.Model.UserPHP


class AuthentificationControllerPHP(val context: Context) {

    fun authentifier(user: UserPHP) : UserPHP? {
        val maBaseDeDonneesSQLLite = MaBaseDeDonneesPHP(context)

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