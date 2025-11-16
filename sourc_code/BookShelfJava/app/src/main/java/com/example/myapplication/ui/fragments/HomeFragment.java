package com.example.myapplication.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.data.remote.ApiClient;
import com.example.myapplication.data.remote.ApiServer;
import com.example.myapplication.R;
import com.example.myapplication.model.VolumeItem;
import com.example.myapplication.model.VolumeResponse;
import com.example.myapplication.ui.adapters.SearchAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private RecyclerView trendingRecycler;
    private RecyclerView newRecycler;
    private RecyclerView freeRecycler;

    private SearchAdapter trendingAdapter;
    private SearchAdapter newAdapter;
    private SearchAdapter freeAdapter;

    private ApiServer apiServer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        apiServer = ApiClient.getApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trendingRecycler = view.findViewById(R.id.trending_recycler_view);
        newRecycler = view.findViewById(R.id.new_recycler_view);
        freeRecycler = view.findViewById(R.id.free_recycler_view);
        trendingAdapter = new SearchAdapter();
        newAdapter = new SearchAdapter();
        freeAdapter = new SearchAdapter();
        setupRecycler(trendingRecycler, trendingAdapter);
        setupRecycler(newRecycler, newAdapter);
        setupRecycler(freeRecycler, freeAdapter);
        loadTrendingBooks();
        loadNewBooks();
        loadFreeBooks();
    }
    private void setupRecycler(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        );
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
    private void loadTrendingBooks(){
        Call<VolumeResponse> call = apiServer.searchBooks("subject:fiction", null, null, 20);
        call.enqueue(new Callback<VolumeResponse>() {
            @Override
            public void onResponse(Call<VolumeResponse> call, Response<VolumeResponse> response) {
                if (!isAdded()) return;
                if (response.isSuccessful() && response.body() != null) {
                    List<VolumeItem> items = response.body().getItems();
                    trendingAdapter.setItems(items);
                } else {
                    showToast("Không tải được sách xu hướng");
                }
            }

            @Override
            public void onFailure(Call<VolumeResponse> call, Throwable t) {
                if (!isAdded()) return;
                showToast("Lỗi API xu hướng: " + t.getMessage());
            }
        });
    }
    private void loadNewBooks(){
        Call<VolumeResponse> call = apiServer.searchBooks("subject:new", null, null, 20);
        call.enqueue(new Callback<VolumeResponse>() {
            @Override
            public void onResponse(Call<VolumeResponse> call, Response<VolumeResponse> response) {
                if (!isAdded()) return;
                if (response.isSuccessful() && response.body() != null) {
                    List<VolumeItem> items = response.body().getItems();
                    newAdapter.setItems(items);
                } else {
                    showToast("Không tải được sách mới");
                }
            }

            @Override
            public void onFailure(Call<VolumeResponse> call, Throwable t) {
                if (!isAdded()) return;
                showToast("Lỗi API sách mới: " + t.getMessage());
            }
        });
    }
    private void loadFreeBooks(){
        Call<VolumeResponse> call = apiServer.searchBooks("free", "free-ebooks", null, 20);
        call.enqueue(new Callback<VolumeResponse>() {
            @Override
            public void onResponse(Call<VolumeResponse> call, Response<VolumeResponse> response) {
                if (!isAdded()) return;
                if (response.isSuccessful() && response.body() != null) {
                    List<VolumeItem> items = response.body().getItems();
                    freeAdapter.setItems(items);
                } else {
                    showToast("Không tải được sách miễn phí");
                }
            }

            @Override
            public void onFailure(Call<VolumeResponse> call, Throwable t) {
                if (!isAdded()) return;
                showToast("Lỗi API sách miễn phí: " + t.getMessage());
            }
        });
    }
    private void showToast(String message) {
        if (isAdded()) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}