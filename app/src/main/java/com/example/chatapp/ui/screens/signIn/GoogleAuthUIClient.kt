package com.example.chatapp.ui.screens.signIn

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.chatapp_dacs3.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthUIClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender?{
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        }catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    suspend fun signInWithIntent(intent: Intent): SignInResult{
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val ggIdToken = credential.googleIdToken
        val ggCredentials = GoogleAuthProvider.getCredential(ggIdToken,null)

        return try {
            val user = auth.signInWithCredential(ggCredentials).await().user
            SignInResult(
                data = user?.run {
                     UserData(
                         userId = uid,
                         username = displayName,
                         profilePictureUrl = photoUrl?.toString()
                     )
                },
                errorMes = null
            )
        }catch (e : Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMes = e.message
            )
        }
    }

    suspend fun signOut(){
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        }catch (e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.run{
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(R.string.your_web_client_id))
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}