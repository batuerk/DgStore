package com.dgstore.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dgstore.UserInfo;

import java.util.List;

public class ProfileViewModel extends ViewModel {
    private String  userInfo;
    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> mText1;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText1 = new MutableLiveData<>();

        List<String> userInfo = UserInfo.getInstance().getUserInfoList();

//        StringBuilder sb = new StringBuilder();
//        System.out.println("userInfo içeriği: " + userInfo.get(0));
//        for (String i : userInfo) {
//            sb.append(i).append("\n");
//            System.out.println("i: " + i);
//
//        }
        mText.setValue(userInfo.get(0));
        mText1.setValue(userInfo.get(1));

//        System.out.println("sb: " + sb);
//        System.out.println("sb1: " + sb1);

    }

//    public void init(Application application) {
//        MyDatabase myDatabase = MyDatabase.getMyDatabase(application);
//
//    }

//    public void setUserInfo(String userInfo) {
//        this.userInfo = userInfo;
//    }

    public LiveData<String> getText() {

        return mText;
    }

    public LiveData<String> getText1() {

        return mText1;
    }
}
