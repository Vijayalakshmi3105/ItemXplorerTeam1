package com.example.inventorymart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.values


class regPage : AppCompatActivity() {
    private lateinit var email:EditText
    private lateinit var pass:EditText
    private lateinit var cpass:EditText
    private lateinit var dept:EditText


    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg_page)
    }
    fun getin(view : View){
        email=findViewById<EditText>(R.id.email)
        pass=findViewById<EditText>(R.id.pass)
        cpass=findViewById<EditText>(R.id.Cpass)
        dept=findViewById<EditText>(R.id.dept)

        var e=email.text.toString()
        var p=pass.text.toString()
        var cp=cpass.text.toString()
        var d=dept.text.toString()

        e=e.replace('.','%')
        if(p==cp){
            val databaseReference = FirebaseDatabase.getInstance().reference
            val userReference = databaseReference.child("users").child(e)
            userReference.child("pass").setValue(p)
            Toast.makeText(this, "Registered"+e.replace('%','@'), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }else{
            Toast.makeText(this,"Passwords dont match",Toast.LENGTH_SHORT).show()
            pass.text.clear()
            cpass.text.clear()
        }
    }
}