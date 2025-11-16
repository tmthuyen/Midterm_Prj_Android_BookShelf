package com.example.myapplication.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.myapplication.data.remote.ApiClient;
import com.example.myapplication.data.remote.ApiServer;
import com.example.myapplication.R;
import com.example.myapplication.data.repository.BookRepository;
import com.example.myapplication.model.AccessInfo;
import com.example.myapplication.model.Book;
import com.example.myapplication.model.Epub;
import com.example.myapplication.model.ImageLinks;
import com.example.myapplication.model.SaleInfo;
import com.example.myapplication.model.VolumeInfo;
import com.example.myapplication.model.VolumeItem;
import com.example.myapplication.model.VolumeResponse;
import com.example.myapplication.model.mappers.BookMapper;
import com.example.myapplication.ui.adapters.SearchAdapter;
import com.example.myapplication.ui.viewmodel.SearchViewModel;

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
    private static final int STATUS_READING = 1;
    private static final int STATUS_DOWNLOADED = 2;
    private static final int STATUS_COMPLETED = 3;
    private RecyclerView trendingRecycler;
    private RecyclerView newRecycler;
    private RecyclerView freeRecycler;

    private SearchAdapter trendingAdapter;
    private SearchAdapter newAdapter;
    private SearchAdapter freeAdapter;
    private SearchViewModel searchViewModel;
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
        searchViewModel = new ViewModelProvider(requireActivity())
                .get(SearchViewModel.class);
        trendingAdapter.setOnItemClickListener(this::openBookDetail);

        trendingAdapter.setOnMoreClickListener((anchor, volumeItem) -> {
            showMoreMenu(anchor, volumeItem);
        });

        newAdapter.setOnMoreClickListener((anchor, volumeItem) -> {
            showMoreMenu(anchor, volumeItem);
        });
        freeAdapter.setOnMoreClickListener((anchor, volumeItem) -> {
            showMoreMenu(anchor, volumeItem);
        });
        newAdapter.setOnItemClickListener(this::openBookDetail);
        freeAdapter.setOnItemClickListener(this::openBookDetail);
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
    private void showMoreMenu(View anchor, VolumeItem volumeItem){
        PopupMenu popup = new PopupMenu(requireContext(), anchor);
        popup.getMenuInflater().inflate(R.menu.menu_book_more, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.action_view_detail) {
                openBookDetail(volumeItem);
                return true;
            }
            Book book = BookMapper.fromVolumeItem(volumeItem);
            if (id == R.id.action_add_to_library) {
                addToLibrary(book);
                return true;
            } else if (id == R.id.action_mark_reading) {
                markAsStatus(book, STATUS_READING);
                return true;
            } else if (id == R.id.action_open_preview) {
                openPreview(volumeItem);
                return true;
            } else if (id == R.id.action_download_epub) {
                downloadEpub(book);
                return true;
            } else if (id == R.id.action_delete_from_library) {
                deleteFromLibrary(book);
                return true;
            }
            return false;
        });
        popup.show();
    }
    private void addToLibrary(Book book){
        if(book == null || TextUtils.isEmpty(book.getId())){
            Toast.makeText(getContext(), "Không có Id để lưu", Toast.LENGTH_SHORT).show();
            return;
        }
        searchViewModel.saveTolibrary(book);
        Toast.makeText(getContext(), "Đã thêm vào thư viện", Toast.LENGTH_SHORT).show();
    }
    private void deleteFromLibrary(Book book){
        searchViewModel.deleteFromLibrary(book);
        Toast.makeText(getContext(), "Đã xoá khỏi thư viện", Toast.LENGTH_SHORT).show();
    }
    private void markAsStatus(Book book, int status){
        book.setReadingStatus(status);
        searchViewModel.updateReadingStatus(book.getId(), status);
        Toast.makeText(getContext(), "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
    }
    private void openPreview(VolumeItem item){
        VolumeInfo info = item.getVolumeInfo();
        String previewLink = info != null ? info.getPreviewLink() : null;
        if (previewLink == null || previewLink.isEmpty()) {
            Toast.makeText(getContext(), "Sách này không có preview", Toast.LENGTH_SHORT).show();
            return;
        }

        //chưa code xong mốt code tiếp
    }
    private void downloadEpub(Book book) {
        // chưa code xong mốt code tiếp
    }
}