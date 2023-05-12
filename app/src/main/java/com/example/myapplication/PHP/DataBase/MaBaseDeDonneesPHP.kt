package com.example.myapplication.PHP.DataBase

import android.annotation.SuppressLint

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.android.volley.Response
import com.android.volley.Request

import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.example.myapplication.PHP.Model.UserPHP
import org.json.JSONArray
import org.json.JSONException


class MaBaseDeDonneesPHP (val context: Context){


    // Méthode pour ajouter un utilisateur dans la table utilisateur
    fun insertDataToMySQL(user :UserPHP) {
        val requestQueue = Volley.newRequestQueue( context)
        //modifie ca pour mettre l'adresse du serveur distants
        val url = "http://localhost/Android/data_base_insert.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                // Traitement de la réponse si nécessaire
            },
            Response.ErrorListener { error ->
                // Gestion des erreurs si nécessaire
            }) {

            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["nom"] = user.nom
                params["email"] = user.email

                return params
            }
        }
        requestQueue.add(stringRequest)
    }

    // Méthode pour modifier un utilisateur dans la table utilisateur
    fun updateUserInMySQL(user: UserPHP) {
        val requestQueue = Volley.newRequestQueue(context)
        val url = "http://localhost/Android/data_base_update_user.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                // Traitement de la réponse si nécessaire
            },
            Response.ErrorListener { error ->
                // Gestion des erreurs si nécessaire
            }) {

            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] = user.id.toString()
                params["nom"] = user.nom
                params["email"] = user.email

                return params
            }
        }
        requestQueue.add(stringRequest)
    }

    // Méthode pour supprimer un utilisateur dans la table utilisateur
    fun deleteDataFromMySQL(id: Long): Int {
        val requestQueue = Volley.newRequestQueue(context)
        val url = "http://localhost/Android/data_base_delete_user.php"

        var nbLignesSupprimees = 0

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                // Traitement de la réponse si nécessaire
                nbLignesSupprimees = response.toInt()
            },
            Response.ErrorListener { error ->
                // Gestion des erreurs si nécessaire
            }) {

            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["id"] = id.toString()
                return params
            }
        }
        requestQueue.add(stringRequest)

        return nbLignesSupprimees
    }




    // Méthode pour récupérer tous les utilisateurs dans la table utilisateur
    fun getUtilisateurs(): ArrayList<UserPHP> {
        val users = ArrayList<UserPHP>()
        val requestQueue = Volley.newRequestQueue(context)
        val url = "http://localhost/Android/data_base_list_user.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val id = jsonObject.getLong("id")
                        val nom = jsonObject.getString("nom")
                        val email = jsonObject.getString("email")
                        users.add(UserPHP(id, nom, email))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                // Gestion des erreurs si nécessaire
            })

        requestQueue.add(stringRequest)

        return users
    }


    //fonction pour gerer l'authentification
    fun authenticate(user: UserPHP, callback: (Boolean) -> Unit) {
        val requestQueue = Volley.newRequestQueue(context)
        val url = "http://localhost/Android/data_base_auth_user.php"

        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                // Si la réponse du serveur est "1", l'authentification a réussi
                callback(response.trim() == "1")
            },
            Response.ErrorListener { error ->
                // Gestion des erreurs si nécessaire
            }) {

            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["nom"] = user.nom
                params["email"] = user.email
                return params
            }
        }
        requestQueue.add(stringRequest)
    }


}