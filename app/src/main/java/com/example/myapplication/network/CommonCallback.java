package com.example.myapplication.network;

public interface CommonCallback<T> {
    /**
     * 接口成功回调
     */
    void onSuccess(T data);

    /**
     * 接口失败回调
     */
    void onError(String error);
}
