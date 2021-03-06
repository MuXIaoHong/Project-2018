package com.example.belon.project_2018.request;


import com.example.belon.project_2018.func.ApiDataFunc;
import com.example.belon.project_2018.http.http.func.ApiRetryFunc;
import com.example.belon.project_2018.http.http.request.BaseHttpRequest;
import com.example.belon.project_2018.mode.ApiResult;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Description: 返回APIResult的基础请求类
 * @author: <a href="http://www.xiaoyaoyou1212.com">DAWI</a>
 * @date: 17/5/28 15:46.
 */
public abstract class ApiBaseRequest<R extends ApiBaseRequest> extends BaseHttpRequest<R> {
    public ApiBaseRequest(String suffixUrl) {
        super(suffixUrl);
    }

    protected <T> ObservableTransformer<ApiResult<T>, T> apiTransformer() {
        return new ObservableTransformer<ApiResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ApiResult<T>> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new ApiDataFunc<T>())
                        .retryWhen(new ApiRetryFunc(retryCount, retryDelayMillis));
            }
        };
    }
}
