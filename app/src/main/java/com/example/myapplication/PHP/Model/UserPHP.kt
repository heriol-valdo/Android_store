package com.example.myapplication.PHP.Model

 data  class UserPHP(var id: Long, var nom: String, var email:String  ) :  java.io.Serializable {

  constructor( nom : String, email: String) : this(0,nom,email){

  }
 }