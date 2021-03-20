package com.example.firebasedemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Student")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAdd: Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener() {

            val stdID: String = findViewById<TextView>(R.id.txtID).text.toString()
            val stdName: String = findViewById<TextView>(R.id.txtName).text.toString()
            val stdProgramme: String = findViewById<TextView>(R.id.txtProgramme).text.toString()

            myRef.child(stdID).child("Name").setValue(stdName)
            myRef.child(stdID).child("Programme").setValue(stdProgramme)

        }
        val getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()

                for (student in snapshot.children) {
                    var name = student.child("Name").getValue()
                    sb.append("${name} \n")
                }

                val tvResult:TextView =findViewById(R.id.tvResult)
                tvResult.setText(sb)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }

        val btnGet: Button = findViewById(R.id.btnGet)
        btnGet.setOnClickListener() {

            //myRef.addValueEventListener(getData)
            //myRef.addListenerForSingleValueEvent(getData)

            val qry: Query = myRef.orderByChild("Programme").equalTo("RSD")
            qry.addValueEventListener(getData)
            qry.addListenerForSingleValueEvent(getData)
        }

    }
}
