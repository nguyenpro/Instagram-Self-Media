package com.npc.instafeed.base;

/**
 * Created by USER on 16/01/2017.
 */

public abstract class BasePresenter<T> {
    protected  T view;

    protected abstract void initView(T view);
}
