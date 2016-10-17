package com.example.svenscan.svenscan.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.svenscan.svenscan.R;
import com.example.svenscan.svenscan.activities.GameActivity;

public class GameFragment extends Fragment{

    public static final String TAG = GameFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        TextView scoreView = (TextView)view.findViewById(R.id.score_view);
        GameActivity activity = (GameActivity) getActivity();
        int score = activity.getScore();
        int nbrOfQuestions = activity.getNBR_OF_QUESTIONS();

        scoreView.setText("\n" + score + "/" + nbrOfQuestions + "\n");

        return view;
    }

    public static Fragment newInstance() {
        return new GameFragment();
    }
}
