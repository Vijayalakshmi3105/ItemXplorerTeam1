package com.example.inventorymart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.EditText

import android.content.Context
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    private lateinit var email:EditText
    private lateinit var pass:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val kmsi = sharedPrefs.getBoolean("KMSI", false)

        if (kmsi) {
            val user = sharedPrefs.getString("user_email", null)
            if (user != null) {
                //Pehle aa chuka hai
                startActivity(Intent(this, AddItems::class.java))
                finish()
                return
            }
        }
    }
    fun getin(view : View){
        email=findViewById<EditText>(R.id.email)
        pass=findViewById<EditText>(R.id.pass)
        var e=email.text.toString()
        var p=pass.text.toString()
        e=e.replace('.','%')
        showToast(e)
        database=FirebaseDatabase.getInstance().getReference("users")
        // Reference to the specific user node

        database.child(e).get().addOnSuccessListener {
                if(it.exists()){
                    // The user node exists in the database
                    val storedPassword = it.child("pass").value.toString()
                    showToast(storedPassword)
                    if (p==storedPassword) {
                        getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit().putString("user_email", e).apply()
                        getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit().putBoolean("KMSI", true).apply()
                        getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit().putString("pass", p).apply()

                        showToast("Welcome")
                          // Password matches, allow the user to enter the AddItems activity
                        startActivity(Intent(this@MainActivity, AddItems::class.java))
                        finish()
                    } else {
                        // Password doesn't match, handle authentication failure
                        // You can show an error message to the user, for example.
                        showToast("This is a wrong pass")

                    }
                } else {
                    showToast("Node not found")

                    // The user node doesn't exist in the database
                    // Handle this scenario, e.g., show an error message for an unknown user
                }
            }.addOnCanceledListener {
            Toast.makeText(this,"Network Error",Toast.LENGTH_SHORT).show()
        }

    }
    fun showToast(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
    fun registerPage(view : View){
        startActivity(Intent(this,regPage::class.java))

    }
}