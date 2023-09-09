package com.dgstore.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dgstore.model.UserInfo;

import java.util.List;

public class ProfileViewModel extends ViewModel {
    private String  userInfo;
    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> mText1;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText1 = new MutableLiveData<>();

        List<String> userInfo = UserInfo.getInstance().getUserInfoList();

        mText.setValue(userInfo.get(0));
        mText1.setValue(userInfo.get(1));

    }

    public LiveData<String> getText() {

        return mText;
    }

    public LiveData<String> getText1() {

        return mText1;
    }
}
