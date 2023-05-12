package com.example.myapplication.SQL_Lite.DataBase

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.SQL_Lite.Model.User

class MaBaseDeDonneesSQL_Lite(context: Context) : SQLiteOpenHelper(context, NOM_BASE_DE_DONNEES, null, VERSION_BASE_DE_DONNEES) {
    override fun onCreate(db: SQLiteDatabase?) {
        // Créez la table utilisateur
        db?.execSQL("CREATE TABLE utilisateur (id INTEGER PRIMARY KEY AUTOINCREMENT, nom TEXT, email TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Mettez à jour la structure de la base de données si nécessaire
    }

    companion object {
        // Définissez le nom et la version de la base de données
        private const val NOM_BASE_DE_DONNEES = "ma_base_de_donnees"
        private const val VERSION_BASE_DE_DONNEES = 1

        // Définissez le nom de la table et des colonnes
        private const val NOM_TABLE_UTILISATEUR = "utilisateur"
        private const val COLONNE_ID = "id"
        private const val COLONNE_NOM = "nom"
        private const val COLONNE_EMAIL = "email"
    }

    // Méthode pour ajouter un utilisateur dans la table utilisateur
    fun ajouterUtilisateur(user: User): Long {
        // Créez une instance de ContentValues pour stocker les valeurs à insérer
        val valeurs = ContentValues()
        valeurs.put(COLONNE_NOM, user.nom)
        valeurs.put(COLONNE_EMAIL, user.email)

        // Insérez les valeurs dans la table utilisateur
        return writableDatabase.insert(NOM_TABLE_UTILISATEUR, null, valeurs)
    }

    // Méthode pour modifier un utilisateur dans la table utilisateur
    fun modifierUtilisateur(user: User): Int {
        // Créez une instance de ContentValues pour stocker les valeurs à mettre à jour
        val valeurs = ContentValues()
        valeurs.put(COLONNE_NOM, user.nom)
        valeurs.put(COLONNE_EMAIL, user.email)

        // Mettez à jour les valeurs dans la table utilisateur correspondant à l'ID spécifié
        return writableDatabase.update(NOM_TABLE_UTILISATEUR, valeurs, "$COLONNE_ID = ?", arrayOf(user.int.toString()))
    }


    // Méthode pour supprimer un utilisateur dans la table utilisateur
    fun supprimerUtilisateur(id: Long): Int {
        // Supprimez l'utilisateur correspondant à l'ID spécifié
        val nbLignesSupprimees = writableDatabase.delete(NOM_TABLE_UTILISATEUR, "$COLONNE_ID = ?", arrayOf(id.toString()))

        // Retournez le nombre de lignes supprimées (1 si l'utilisateur a été supprimé, 0 sinon)
        return nbLignesSupprimees
    }

    // Méthode pour récupérer tous les utilisateurs dans la table utilisateur
    @SuppressLint("Range")
    fun getUtilisateurs(): ArrayList<User> {
        val utilisateurs = ArrayList<User>()

        // Effectuez une requête pour récupérer tous les utilisateurs
        val requete = "SELECT * FROM $NOM_TABLE_UTILISATEUR"
        val curseur = readableDatabase.rawQuery(requete, null)

        // Parcourez le curseur et créez des objets Utilisateur pour chaque entrée
        while (curseur.moveToNext()) {
            val id = curseur.getLong(curseur.getColumnIndex(COLONNE_ID))
            val nom = curseur.getString(curseur.getColumnIndex(COLONNE_NOM))
            val email = curseur.getString(curseur.getColumnIndex(COLONNE_EMAIL))
            val utilisateur = User(id, nom, email)
            utilisateurs.add(utilisateur)
        }

        // Fermez le curseur et retournez la liste d'utilisateurs
        curseur.close()
        return utilisateurs
    }

    //fonction pour gerer l'authentification d'un utilisateur
    @SuppressLint("Range")
    fun authentifierUtilisateur(nomUtilisateur: String, email: String): User? {
        val requete = "SELECT * FROM $NOM_TABLE_UTILISATEUR WHERE $COLONNE_NOM = ? AND $COLONNE_EMAIL = ?"
        val curseur = readableDatabase.rawQuery(requete, arrayOf(nomUtilisateur, email))

          // Si le curseur ne contient pas d'entrée, cela signifie que l'utilisateur n'existe pas dans la base de données
        if (curseur.count == 0) {
            return null
        }

         // Si le curseur contient une entrée, créez un objet Utilisateur correspondant et retournez-le
        curseur.moveToFirst()
        val id = curseur.getLong(curseur.getColumnIndex(COLONNE_ID))
        val nom = curseur.getString(curseur.getColumnIndex(COLONNE_NOM))
        val email = curseur.getString(curseur.getColumnIndex(COLONNE_EMAIL))
        val utilisateur = User(id, nom, email)
        curseur.close()
        return utilisateur

    }
}