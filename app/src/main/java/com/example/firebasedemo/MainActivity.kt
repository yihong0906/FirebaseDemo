package com.example.firebasedemo

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Student")
        val btnget:Button=findViewById(R.id.buttonget)

        val btnAdd:Button=findViewById(R.id.buttonadd)
        btnAdd.setOnClickListener(){


            val studID :String = findViewById<TextView>(R.id.txtID).text.toString()
            val studName :String = findViewById<TextView>(R.id.txtName).text.toString()
            val studProg :String = findViewById<TextView>(R.id.txtProgramme).text.toString()

            myRef.child(studID).child("Name").setValue(studName)
            myRef.child(studID).child("Programme").setValue(studProg)

        }

        val getData = object :ValueEventListener
        {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                var sb = StringBuilder()

                for(student in p0.children)
                {
                    var name = student.child("Name").getValue()
                    sb.append("${name} \n")

                }
                val txtView : TextView = findViewById(R.id.textView)
                txtView.setText(sb)
            }

        }
        btnget.setOnClickListener()
        {
            val qry :Query = myRef.orderByChild("Programme").equalTo("RSF")
            qry.addValueEventListener(getData)
            qry.addListenerForSingleValueEvent(getData)
            //myRef.addValueEventListener(getData)
            //myRef.addListenerForSingleValueEvent(getData)
        }
    }
}