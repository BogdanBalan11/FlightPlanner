package com.example.flightplanner.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flightplanner.Activities.Domain.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _registrationSuccess = MutableLiveData<Boolean?>()
    val registrationSuccess: LiveData<Boolean?> get() = _registrationSuccess

    private val _loginSuccess = MutableLiveData<Boolean?>()
    val loginSuccess: LiveData<Boolean?> get() = _loginSuccess

    fun registerUser(email: String, password: String, username: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = firebaseAuth.currentUser?.uid
                    val user = UserModel(userId, email, username)

                    if (userId != null) {
                        firebaseDatabase.getReference("Users")
                            .child(userId)
                            .setValue(user)
                            .addOnSuccessListener {
                                _registrationSuccess.value = true
                            }
                            .addOnFailureListener {
                                _registrationSuccess.value = false
                            }
                    } else {
                        _registrationSuccess.value = false
                    }
                } else {
                    _registrationSuccess.value = false
                }
            }
    }

    fun loginUser(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = firebaseAuth.currentUser?.uid
                    if (userId != null) {
                        firebaseDatabase.getReference("Users")
                            .child(userId)
                            .get()
                            .addOnSuccessListener { dataSnapshot ->
                                val user = dataSnapshot.getValue(UserModel::class.java)
                                // Optionally save user in-memory, in a Singleton, or ViewModel
                                _loginSuccess.value = true
                            }
                            .addOnFailureListener {
                                _loginSuccess.value = false
                            }
                    } else {
                        _loginSuccess.value = false
                    }
                } else {
                    _loginSuccess.value = false
                }
            }
    }

    fun logoutUser() {
        firebaseAuth.signOut()
    }

    fun resetRegistrationSuccess() {
        _registrationSuccess.postValue(null)
    }

    fun resetLoginSuccess() {
        _loginSuccess.postValue(null)
    }
}
