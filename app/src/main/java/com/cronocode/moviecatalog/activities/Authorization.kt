package com.cronocode.moviecatalog.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cronocode.moviecatalog.R
import com.cronocode.moviecatalog.databinding.ActivityAuthorizationBinding
import com.cronocode.moviecatalog.models.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase


class Authorization : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityAuthorizationBinding

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN: Int = 11
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        //delete previous auth
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        /**onClickListeners*/
        binding.googleBtn.setOnClickListener(View.OnClickListener {
            val intent = Intent(mGoogleSignInClient.signInIntent)
            startActivityForResult(intent, RC_SIGN_IN)
            //startActivity(Intent(this, MainActivity::class.java))
        })



        binding.signUpBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, registration::class.java))
        })
        binding.loginBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, login::class.java))
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != RESULT_CANCELED){
            if(requestCode==RC_SIGN_IN){
                val task : Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
                val account = task.result
                account.idToken?.let { authWithGoogle(it) }
            }
        }
    }

    private fun authWithGoogle(idToken: String) {
        val credentials : AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credentials)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) run {
                    val user: FirebaseUser = mAuth.currentUser!!
                    val firebaseUser: User =
                        User(user.uid, user.displayName.toString(), user.photoUrl.toString(), "Unknown")
                    database.reference
                        .child("profiles")
                        .child(user.uid)
                        .setValue(firebaseUser).addOnCompleteListener { task ->
                            if(task.isSuccessful) run{
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("profile", user.photoUrl)
                                startActivity(intent)
                                finishAffinity()
                            }
                            else{
                                Toast.makeText(this, task.exception!!.localizedMessage, Toast.LENGTH_SHORT).show()
                            }
                        }
                    //Log.e("profile", user.photoUrl.toString())
                }
                else run{
                    task.exception?.let { Log.e("err", it.localizedMessage) }
                }
            }
    }
}