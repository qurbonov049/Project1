package com.example.jobalifproject;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class ModelRepository {

    private ModelDao mModelDao;
    private LiveData<List<Model>> mAllModel;

    ModelRepository(Application application) {
        ModelRoomDatabase db = ModelRoomDatabase.getDatabase(application);
        mModelDao = db.modelDao();
        mAllModel = mModelDao.getAlphabetizedWords();
    }

    LiveData<List<Model>> getAllWords() {
        return mAllModel;
    }

    void insert(Model model) {
        ModelRoomDatabase.databaseWriteExecutor.execute(() -> {
            mModelDao.insert(model);
        });
    }
}
