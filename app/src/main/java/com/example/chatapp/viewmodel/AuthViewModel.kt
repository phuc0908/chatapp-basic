package com.example.chatapp.viewmodel


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.chatapp.Destination
import com.example.chatapp.model.Account
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage

@SuppressLint("StaticFieldLeak")
class AuthViewModel(
    val context: Context
) : ViewModel(){
//    Sign In

    private val _userName = mutableStateOf("")
    val userName: String
        get() = _userName.value
    private val _password = mutableStateOf("")
    val password: String
        get() = _password.value

//    Sign Up
    private val _userNameRegister = mutableStateOf("")
    val userNameRegister: String
        get() = _userNameRegister.value

    private val _passwordRegister = mutableStateOf("")
    val passwordRegister: String
        get() = _passwordRegister.value

    private val _nickNameRegister = mutableStateOf("")
    val nickNameRegister: String
        get() = _nickNameRegister.value

    private val _cfpasswordRegister = mutableStateOf("")
    val cfpasswordRegister: String
        get() = _cfpasswordRegister.value
//    OTHER

    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference.child("avatars/newUser.png")

//    currentUser

    private val _currentUser = mutableStateOf<FirebaseUser?>(null)
    val currentUser: FirebaseUser?
        get() = _currentUser.value
    init {
        _currentUser.value = auth.currentUser
    }
    private fun updateCurrentUser(currentUser: FirebaseUser?) {
        _currentUser.value = currentUser
    }

//  SIGN IN
    fun updateUserName(newName: String) {
        _userName.value = newName
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }
//    SIGN UP
    fun updateUserNameRegister(newName: String) {
        _userNameRegister.value = newName
    }

    fun updatePasswordRegister(newPassword: String) {
        _passwordRegister.value = newPassword
    }
    fun updateNickName(newName: String) {
        _nickNameRegister.value = newName
    }
    fun updateCfPassword(newPassword: String) {
        _cfpasswordRegister.value = newPassword
    }


    fun createAccount(
        navController: NavController,
        accountViewModel: AccountViewModel
    ) {
        auth.createUserWithEmailAndPassword(userNameRegister, passwordRegister)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(
                        context,
                        "Register Successful.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val user = task.result?.user
                    updateCurrentUser(user)
//                    Push firebase
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        val account = user?.let {
                            Account(
                                uid = it.uid,
                                username = userNameRegister,
                                password = passwordRegister,
                                nickName = nickNameRegister,
                                imageUri = uri.toString()
                            )
                        }
                        if (account !== null) {
                            accountViewModel.updateAccount(account,user)
                            navController.navigate(Destination.Home.route)

                        }else{
                            Toast.makeText(
                                context,
                                "Register firebase realtime failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.w(TAG, "createUserWithEmailFirebaseRealtime:failure", task.exception)
                        }
                    }.addOnFailureListener { exception ->
                        exception.message?.let {
                            Log.d("Error Get Picture in Storage", it)
                        }
                    }

                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Register failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateCurrentUser(null)
                }
            }
    }
    fun signIn(navController: NavController) {

        if (!isEmailValid(userName)) {
            Toast.makeText(context, "Invalid email address.", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isPasswordValid(password)) {
            Toast.makeText(context, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(userName, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Authentication successful.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "signInWithEmail:success")
                    auth.currentUser?.let { it1 ->
                        Log.d("s", it1.uid)
                    }
                    val user = auth.currentUser
                    updateCurrentUser(user)

                    navController.navigate(Destination.Home.route){
                        popUpTo(Destination.SignIn.route) {
                            inclusive = true
                        }
                    }
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateCurrentUser(null)
                }
            }
    }

    fun signOut(navController: NavController){
        auth.signOut()
        updateCurrentUser(null)

        navController.navigate(Destination.SignIn.route) {
            launchSingleTop = true
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }


    companion object {
        private const val TAG = "EmailPassword"
    }
    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }
}
