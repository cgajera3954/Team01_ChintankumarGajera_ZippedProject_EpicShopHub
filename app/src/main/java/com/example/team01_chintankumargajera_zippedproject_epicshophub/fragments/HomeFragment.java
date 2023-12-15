package com.example.team01_chintankumargajera_zippedproject_epicshophub.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.R;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.adapters.CategoryAdapter;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.adapters.NewProductsAdapter;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.adapters.PopularProductsAdapter;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.models.CategoryModel;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.models.NewProductsModel;
import com.example.team01_chintankumargajera_zippedproject_epicshophub.models.PopularProductsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    // Category RecyclerView
    RecyclerView catRecyclerview, newProductRecyclerview, popularRecyclerview;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    // New Products RecyclerView
    NewProductsAdapter newProductsAdapter;
    List<NewProductsModel> newProductsModelList;

    // Popular Products RecyclerView
    PopularProductsAdapter popularProductsAdapter;
    List<PopularProductsModel> popularProductsModelList;

    // Firestore
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        catRecyclerview = root.findViewById(R.id.rec_category);
        newProductRecyclerview = root.findViewById(R.id.new_product_rec);
        popularRecyclerview = root.findViewById(R.id.popular_rec);

        db = FirebaseFirestore.getInstance();

        // Image slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1, "Discount On Shoes Items", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2, "Discount On Perfume", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3, "70% OFF", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);

        // Category
        setupCategoryRecyclerView();

        // New Products
        setupNewProductsRecyclerView();

        // Popular Products
        setupPopularProductsRecyclerView();

        return root;
    }

    private void setupCategoryRecyclerView() {
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(), categoryModelList);
        catRecyclerview.setAdapter(categoryAdapter);

        fetchCategories();
    }

    private void setupNewProductsRecyclerView() {
        newProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        newProductsAdapter = new NewProductsAdapter(getContext(), newProductsModelList);
        newProductRecyclerview.setAdapter(newProductsAdapter);

        fetchNewProducts();
    }

    private void setupPopularProductsRecyclerView() {
        popularRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularProductsModelList = new ArrayList<>();
        popularProductsAdapter = new PopularProductsAdapter(getContext(), popularProductsModelList);
        popularRecyclerview.setAdapter(popularProductsAdapter);

        fetchPopularProducts();
    }

    private void fetchCategories() {
        db.collection("Category").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        CategoryModel categoryModel = document.toObject(CategoryModel.class);
                        categoryModelList.add(categoryModel);
                        categoryAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchNewProducts() {
        db.collection("NewProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                        newProductsModelList.add(newProductsModel);
                        newProductsAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchPopularProducts() {
        db.collection("AllProducts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
                        popularProductsModelList.add(popularProductsModel);
                        popularProductsAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
