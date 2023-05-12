package com.example.myapplication.PHP.ListUser_

import android.content.Intent
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.example.myapplication.PHP.MainActivityPHP
import com.example.myapplication.SQL_Lite.DataBase.MaBaseDeDonneesSQL_Lite
import com.example.myapplication.SQL_Lite.MainActivity
import com.example.myapplication.SQL_Lite.Model.User
import com.example.myapplication.R
import com.example.myapplication.SQL_Lite.ListUser_.DetailsActivity
import com.example.myapplication.PHP.DataBase.MaBaseDeDonneesPHP
import com.example.myapplication.PHP.ListUser_.DetailsActivityPHP
import com.example.myapplication.PHP.Model.UserPHP


class  ListUserPHP : AppCompatActivity() {

    public lateinit var maBaseDeDonneesPHP: MaBaseDeDonneesPHP
    private lateinit var users: ArrayList<UserPHP>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user)

        maBaseDeDonneesPHP = MaBaseDeDonneesPHP(this)

        val listView = findViewById<ListView>(R.id.list_view)


        users = maBaseDeDonneesPHP.getUtilisateurs()

        val adapter = object : ArrayAdapter<UserPHP>(this, android.R.layout.simple_list_item_1, users) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
                val utilisateur = getItem(position)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.text = utilisateur?.nom ?: ""
                return view
            }
        }

        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val utilisateur = users[position]
            val intent = Intent(this, DetailsActivityPHP::class.java).apply {
                putExtra("utilisateur", utilisateur)
            }
            startActivity(intent)
        }



    }

    fun AddUser(view : View){
        val intent = Intent(this, MainActivityPHP:: class.java)
        startActivity(intent)
    }


}