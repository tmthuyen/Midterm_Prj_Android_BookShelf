package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.myapplication.model.VolumeItem;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private SearchViewModel searchViewModel;
    private SearchAdapter adapter;
    private EditText searchEditText;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private LinearLayout emptyState;
    private ChipGroup chipGroup;
    private Chip chipAll;
    private Chip chipPreview;
    private Chip chipFree;
    private Chip chipEpub;
    private String currentQuery = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchEditText = view.findViewById(R.id.search_edit_text);
        recyclerView = view.findViewById(R.id.search_recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);
        emptyState = view.findViewById(R.id.empty_state);
        chipGroup   = view.findViewById(R.id.chipFilters);
        chipAll     = view.findViewById(R.id.chipAll);
        chipPreview = view.findViewById(R.id.chipPreview);
        chipFree    = view.findViewById(R.id.chipFree);
        chipEpub    = view.findViewById(R.id.chipEpub);
        adapter = new SearchAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this::openBookDetail);
        searchViewModel = new ViewModelProvider(requireActivity())
                .get(SearchViewModel.class);
        searchViewModel.getSearchResults().observe(
                getViewLifecycleOwner(),
                this::updateSearchResults
        );
        setupRealtimeSearch();
        setupChipFilters();
    }
    private void setupRealtimeSearch(){
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentQuery = s.toString().trim();
                if(currentQuery.isEmpty()){
                    adapter.setItems(new ArrayList<>());
                    recyclerView.setVisibility(View.GONE);
                    emptyState.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else {
                    showLoading();
                    searchViewModel.search(currentQuery);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void setupChipFilters(){
        if (chipAll != null) chipAll.setChecked(true);
        if (chipPreview != null) chipPreview.setChecked(false);
        if (chipFree != null) chipFree.setChecked(false);
        if (chipEpub != null) chipEpub.setChecked(false);

        if (chipAll != null)     updateChipColor(chipAll, chipAll.isChecked());
        if (chipPreview != null) updateChipColor(chipPreview, chipPreview.isChecked());
        if (chipFree != null)    updateChipColor(chipFree, chipFree.isChecked());
        if (chipEpub != null)    updateChipColor(chipEpub, chipEpub.isChecked());

        if (chipGroup != null) {
            chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
                if (chipAll != null)     updateChipColor(chipAll, chipAll.isChecked());
                if (chipPreview != null) updateChipColor(chipPreview, chipPreview.isChecked());
                if (chipFree != null)    updateChipColor(chipFree, chipFree.isChecked());
                if (chipEpub != null)    updateChipColor(chipEpub, chipEpub.isChecked());
                if (chipAll != null && chipAll.isChecked()) {
                    selectFilterChip(SearchFilter.ALL);
                } else if (chipPreview != null && chipPreview.isChecked()) {
                    selectFilterChip(SearchFilter.PREVIEW);
                } else if (chipFree != null && chipFree.isChecked()) {
                    selectFilterChip(SearchFilter.FREE);
                } else if (chipEpub != null && chipEpub.isChecked()) {
                    selectFilterChip(SearchFilter.EPUB);
                }
            });
        }
    }
    private void selectFilterChip(SearchFilter filter){
        searchViewModel.setFilter(filter, currentQuery);
        if (currentQuery != null && !currentQuery.isEmpty()) {
            showLoading();
            searchViewModel.search(currentQuery);
        }
    }
    private void updateSearchResults(List<VolumeItem> items){
        hideLoading();
        if(items == null || items.isEmpty()){
            adapter.setItems(new ArrayList<>());
            recyclerView.setVisibility(View.GONE);
            emptyState.setVisibility(View.VISIBLE);
        }else {
            adapter.setItems(items);
            recyclerView.setVisibility(View.VISIBLE);
            emptyState.setVisibility(View.GONE);
        }
    }
    private void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
        emptyState.setVisibility(View.GONE);
    }
    private void hideLoading(){
        progressBar.setVisibility(View.GONE);
    }
    private void openBookDetail(VolumeItem item){
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("volumeItem", item);
        fragment.setArguments(args);

        FragmentTransaction transaction =
                requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void updateChipColor(Chip chip, boolean isChecked) {
        if (chip == null) return;

        if (isChecked) {
            chip.setChipBackgroundColorResource(R.color.colorPrimary);
            chip.setTextColor(getResources().getColor(R.color.colorOnPrimary));
        } else {
            chip.setChipBackgroundColor(
                    ColorStateList.valueOf(Color.parseColor("#E2E8F0"))
            );
            chip.setTextColor(Color.parseColor("#0F172A"));
        }
    }
}