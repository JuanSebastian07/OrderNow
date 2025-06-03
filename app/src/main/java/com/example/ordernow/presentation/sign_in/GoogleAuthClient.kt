package com.example.ordernow.presentation.sign_in

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.example.ordernow.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class GoogleAuthClient (
    private val context : Context
) {
    private val tag = "GoogleAuthClient: "
    private val credentialManager = CredentialManager.create(context)
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun isSignedIn() : Boolean  {
        return firebaseAuth.currentUser != null
    }

    suspend fun signIn(): SignInResult {
        if(isSignedIn()){
            val user = firebaseAuth.currentUser
            return SignInResult(
                data = user?.run{
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        }

        try {
            val result = buildCredentialRequest()
            return handleSignInResult(result)
        } catch (e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            return SignInResult(
                data = null,
                errorMessage = e.message ?: "Error desconocido al iniciar sesión"
            )
        }
    }

    private suspend fun handleSignInResult(result: GetCredentialResponse): SignInResult {
        val credential = result.credential

        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                val googleCredentials = GoogleAuthProvider.getCredential(
                    tokenCredential.idToken,
                    null
                )

                val authResult = firebaseAuth.signInWithCredential(googleCredentials).await()
                val user = authResult.user

                return SignInResult(
                    data = user?.run{
                        UserData(
                            userId = uid,
                            username = displayName,
                            profilePictureUrl = photoUrl?.toString()
                        )
                    },
                    errorMessage = null
                )
            } catch (e: GoogleIdTokenParsingException) {
                e.printStackTrace()
                return SignInResult(
                    data = null,
                    errorMessage = e.message ?: "Error al procesar el token de Google"
                )
            } catch (e: Exception) {
                e.printStackTrace()
                return SignInResult(
                    data = null,
                    errorMessage = e.message ?: "Error inesperado durante la autenticación"
                )
            }
        } else {
            return SignInResult(
                data = null,
                errorMessage = "Credencial no válida"
            )
        }
    }

    fun getSignedInUser() : UserData? = firebaseAuth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }


    private suspend fun buildCredentialRequest(): GetCredentialResponse {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(
                        context.getString(R.string.web_client_id)
                    )
                    .setAutoSelectEnabled(false)
                    .build()
            )
            .build()

        return credentialManager.getCredential(
            request = request, context = context
        )
    }

    suspend fun signOut(){
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
        firebaseAuth.signOut()
    }
}