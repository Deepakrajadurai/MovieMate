// File: app/src/main/java/com/example/moviemate/viewmodel/AuthViewModel.java
package com.example.moviemate.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;

public class AuthViewModel extends ViewModel {
    private final MutableLiveData<FirebaseUser> userLiveData;
    private final FirebaseAuth auth;

    public AuthViewModel() {
        userLiveData = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();
        userLiveData.postValue(auth.getCurrentUser());
    }

    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public void logout() {
        auth.signOut();
        userLiveData.postValue(null);
    }
}