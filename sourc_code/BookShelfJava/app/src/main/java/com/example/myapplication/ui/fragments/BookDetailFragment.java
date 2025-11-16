package com.example.myapplication.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.Book;
import com.example.myapplication.ui.viewmodel.CurrentBookViewModel;
import com.example.myapplication.ui.viewmodel.LibraryViewModel;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetailFragment extends Fragment {

    //
//<!--       image_cover,  text_title, text_author, layout_metadata,  text_rating text_pages
//    text_year text_description button_library  button_preview  text_format text_language text_publisher recycler_similar-->
    private ImageView image_cover;
    private TextView text_title, text_author, text_rating, text_pages, text_year,
            text_format, text_language, text_publisher, text_description;
    private Button button_library, button_preview;
    private RecyclerView recycler_similar;
    private CurrentBookViewModel currentBookViewModel;
    private LibraryViewModel libraryViewModel;


    public BookDetailFragment() {
        // Required empty public constructor
    }


    public static BookDetailFragment newInstance(String param1, String param2) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        initAndObserveViewModel();

        handleClickView();
    }

    private void initAndObserveViewModel() {
        currentBookViewModel = new ViewModelProvider(requireActivity()).get(CurrentBookViewModel.class);
        // tra ve book va call bindView(book)
        currentBookViewModel.getCurrentBook().observe(getViewLifecycleOwner(), this::bindView);

        libraryViewModel = new ViewModelProvider(requireActivity()).get(LibraryViewModel.class);
    }

    private void initView(View view) {
        image_cover = view.findViewById(R.id.image_cover);
        text_title = view.findViewById(R.id.text_title);
        text_author = view.findViewById(R.id.text_author);
//        layout_metadata = view.findViewById(R.id.layout_metadata);
        text_rating = view.findViewById(R.id.text_rating);
        text_pages = view.findViewById(R.id.text_pages);
        text_year = view.findViewById(R.id.text_year);
        text_description = view.findViewById(R.id.text_description);
        button_library = view.findViewById(R.id.button_library);
        button_preview = view.findViewById(R.id.button_preview);
        text_format = view.findViewById(R.id.text_format);
        text_language = view.findViewById(R.id.text_language);
        text_publisher = view.findViewById(R.id.text_publisher);
        recycler_similar = view.findViewById(R.id.recycler_similar);
    }

    private void bindView(Book book) {
        if (book == null) return;
        Picasso.get()
                .load(book.getThumbnail())
                .placeholder(R.drawable.placeholder_cover)
                .into(image_cover);

        text_title.setText(book.getTitle());
        text_author.setText(book.getAuthors());
        text_rating.setText(book.getRatingsCount().toString());
        text_pages.setText(book.getPageCount().toString());
        text_year.setText(book.getPublishedDate());
        text_description.setText(book.getDescription());
        text_format.setText(book.getFormat());
        text_language.setText(book.getLanguage());
        text_publisher.setText(book.getPublisher());

    }

    private void handleClickView() {
        if (button_library == null) return;
        button_library.setOnClickListener(v -> {
            // call insert Room
            if (libraryViewModel == null) return;
            libraryViewModel.addBook(currentBookViewModel.getCurrentBook().getValue());
            Toast.makeText(getContext(), "Add to library successfully", Toast.LENGTH_SHORT).show();
        });

        if (button_preview == null) return;
        button_preview.setOnClickListener(v -> {

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        currentBookViewModel.getCurrentBook().removeObservers(getViewLifecycleOwner());
    }

}