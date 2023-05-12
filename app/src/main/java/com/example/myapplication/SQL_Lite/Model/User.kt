package com.example.myapplication.SQL_Lite.Model

 data  class User(var int: Long, var nom: String, var email:String  ) :  java.io.Serializable {

  constructor( nom : String, email: String) : this(0,nom,email){

  }
 }