package com.adya.guardiannewsapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        requireActivity().onSearchRequested();

        Button button = view.findViewById(R.id.search_button);
        button.setOnClickListener(view2 -> getActivity().onSearchRequested());

        Button sharedPrefButton = view.findViewById(R.id.sharedPrefButton);
        SharedPreferences sh = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        boolean checked = sh.getBoolean("checked", false);
        if (checked) {
            sharedPrefButton.setText(R.string.checked_text);
        } else {
            sharedPrefButton.setText(R.string.unchecked_text);
        }
        sharedPrefButton.setOnClickListener(v -> {
            // Storing data into SharedPreferences
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
            boolean isChecked = sh.getBoolean("checked", false);
            if (!isChecked) {
                sharedPrefButton.setText(R.string.checked_text);
            } else {
                sharedPrefButton.setText(R.string.unchecked_text);
            }

            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putBoolean("checked", !isChecked);
            myEdit.apply();
        });

        return view;
    }
}