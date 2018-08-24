package com.example.dharmik.seproject;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MAinActivity";
    private static  final int RC_SIGN_IN = 8086;
    private FirebaseAuth mAuth;

    GoogleSignInClient mGoogleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        findViewById(R.id.sign_in_btn).setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mFireBaseUSer = mAuth.getCurrentUser();




    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String email = account.getEmail();
                if (email.endsWith("edu.in") || email.endsWith("ac.in")){
                firebaseAuthWithGoogle(account);
                }else{
                    Toast.makeText(getApplicationContext(),"Please login in college email!!",Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
                updateUI(null);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                                String email = user.getEmail();
                            //after successful sign in send user-details to nextActivity
                            if (email.endsWith("edu.in")) {
                                Intent i = new Intent(getApplicationContext(), StudentActivity.class);
                                Bundle parseData = new Bundle();
                                parseData.putString("UserName", user.getDisplayName());
                                parseData.putString("UserEmail", user.getEmail());
                                parseData.putString("UserPhoto", String.valueOf(user.getPhotoUrl()));
                                i.putExtras(parseData);
                                startActivity(i);
                            } else{
                                Intent i = new Intent(getApplicationContext(), FacultyActivity.class);
                                Bundle parseData = new Bundle();
                                parseData.putString("UserName", user.getDisplayName());
                                parseData.putString("UserEmail", user.getEmail());
                                parseData.putString("UserPhoto", String.valueOf(user.getPhotoUrl()));
                                i.putExtras(parseData);
                                startActivity(i);
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.signin_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    protected void signOut(){
        //firebase signOut

        FirebaseAuth.getInstance().signOut();
        //google signout
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"SuccessFully Log out!!",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private  void updateUI(FirebaseUser user){
        if (user!= null){
            if(user.getEmail().endsWith("edu.in") ){
                Intent i = new Intent(getApplicationContext(), StudentActivity.class);
                startActivity(i);

            }else if (user.getEmail().endsWith("ac.in")){
                Intent i = new Intent(getApplicationContext(), FacultyActivity.class);
                startActivity(i);
            }else {
                Toast.makeText(getApplicationContext(),"Log in with college id only!!",Toast.LENGTH_SHORT).show();
                return;
            }
        }else{
            Toast.makeText(getApplicationContext(),"Please Log in with google account!!",Toast.LENGTH_SHORT).show();
            return;
        }
    }
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.sign_in_btn) {
                signIn();
        }
    }
}
