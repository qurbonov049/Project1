package com.example.jobalifproject;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModelRoom extends AndroidViewModel {

    private ModelRepository mRepository;

    private final LiveData<List<Model>> mAllModels;

    public ViewModelRoom(Application application) {
        super(application);
        mRepository = new ModelRepository(application);
        mAllModels = mRepository.getAllWords();
    }

    LiveData<List<Model>> getAllModels(){
        return mAllModels;
    }

    void insert(Model model) {
        mRepository.insert(model);
    }
}
