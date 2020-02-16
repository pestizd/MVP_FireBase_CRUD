package StipeBacic.myapplication.Core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import StipeBacic.myapplication.Model.Player;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;


public class MainActitvityInteractor implements MainActivityContract.Interactor {

    private MainActivityContract.onOperationListener mListener;
    private ArrayList<Player> players = new ArrayList<>();

    public MainActitvityInteractor(MainActivityContract.onOperationListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void performCreatePlayer(DatabaseReference reference, Player player) {

        mListener.onStart();
        reference.child(player.getKey()).setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    mListener.onSuccess();
                    mListener.onEnd();
                }
                else {

                    mListener.onFailure();
                    mListener.onEnd();
                }
            }
        });
    }

    @Override
    public void performReadPlayers(DatabaseReference reference) {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Player player = dataSnapshot.getValue(Player.class);
                players.add(player);
                mListener.onRead(players);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Player player = dataSnapshot.getValue(Player.class);
                mListener.onUpdate(player);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Player player = dataSnapshot.getValue(Player.class);
                mListener.onDelete(player);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void performUpdatePlayer(DatabaseReference reference, Player player) {
        mListener.onStart();
        reference.child(player.getKey()).setValue(player).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                    mListener.onEnd();

                }else {

                    mListener.onEnd();

                }

            }
        });
    }

    @Override
    public void performDeletePlayer(DatabaseReference reference, Player player) {
        mListener.onStart();
        reference.child(player.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                    mListener.onEnd();

                }else {

                    mListener.onEnd();

                }


            }
        });

    }
}
