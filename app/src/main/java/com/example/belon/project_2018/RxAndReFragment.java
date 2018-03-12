package com.example.belon.project_2018;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.belon.project_2018.api.AuthorService;
import com.example.belon.project_2018.base.BaseFragment;
import com.example.belon.project_2018.http.common.GsonUtil;
import com.example.belon.project_2018.http.http.ViseHttp;
import com.example.belon.project_2018.http.http.callback.ACallback;
import com.example.belon.project_2018.http.http.core.ApiTransformer;
import com.example.belon.project_2018.http.http.mode.CacheMode;
import com.example.belon.project_2018.http.http.mode.CacheResult;
import com.example.belon.project_2018.http.http.subscriber.ApiCallbackSubscriber;
import com.example.belon.project_2018.mode.AuthorModel;
import com.example.belon.project_2018.request.ApiGetRequest;
import com.example.belon.project_2018.request.ApiPostRequest;
import com.vise.log.ViseLog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhouyanan on 2018/2/9.
 * RxJava和Retrofit网络请求示例
 */

public class RxAndReFragment extends BaseFragment {
    @BindView(R.id.clear_cache)
    Button clearCache;
    @BindView(R.id.request_get_1)
    Button requestGet1;
    @BindView(R.id.request_get_2)
    Button requestGet2;
    @BindView(R.id.request_get_3)
    Button requestGet3;
    @BindView(R.id.request_get_4)
    Button requestGet4;
    @BindView(R.id.request_get_5)
    Button requestGet5;
    @BindView(R.id.request_get_6)
    Button requestGet6;
    @BindView(R.id.request_get_7)
    Button requestGet7;
    @BindView(R.id.request_get_8)
    Button requestGet8;
    @BindView(R.id.request_get_9)
    Button requestGet9;
    @BindView(R.id.request_get_10)
    Button requestGet10;
    @BindView(R.id.request_get_11)
    Button requestGet11;
    @BindView(R.id.request_post_1)
    Button requestPost1;
    @BindView(R.id.request_post_2)
    Button requestPost2;
    @BindView(R.id.request_post_3)
    Button requestPost3;
    @BindView(R.id.request_post_4)
    Button requestPost4;
    @BindView(R.id.request_retrofit_1)
    Button requestRetrofit1;
    @BindView(R.id.request_retrofit_2)
    Button requestRetrofit2;
    @BindView(R.id.show_response_data)
    TextView showResponseData;
    Unbinder unbinder;
    private Context mContext;

    public static RxAndReFragment newInstance() {
        return new RxAndReFragment();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mContext = getContext();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_net_test;
    }

    private void clearCache() {
        ViseHttp.clearCache();
    }

    @OnClick(R.id.request_get_1)
    public void request_get_1() {
        showResponseData.setText("");
        ViseHttp.GET("b6fe647e368b")
                .tag("request_get_1")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String authorModel) {
                        ViseLog.i("request onSuccess!");
                        if (authorModel == null) {
                            return;
                        }
                        showResponseData.setText(authorModel.toString());
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                });
    }

    private void request_get_2() {
        showResponseData.setText("");
        ViseHttp.GET("getAuthor")
                .tag("request_get_2")
                .setLocalCache(true)
                .cacheMode(CacheMode.FIRST_CACHE)
                .request(new ACallback<CacheResult<AuthorModel>>() {
                    @Override
                    public void onSuccess(CacheResult<AuthorModel> cacheResult) {
                        ViseLog.i("request onSuccess!");
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.isCache()) {
                            showResponseData.setText("From Cache:\n" + cacheResult.getCacheData().toString());
                        } else {
                            showResponseData.setText("From Remote:\n" + cacheResult.getCacheData().toString());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                });
    }

    private void request_get_3() {
        showResponseData.setText("");
        ViseHttp.GET("getAuthor")
                .tag("request_get_3")
                .setLocalCache(true)
                .cacheMode(CacheMode.FIRST_REMOTE)
                .request(new ACallback<CacheResult<AuthorModel>>() {
                    @Override
                    public void onSuccess(CacheResult<AuthorModel> cacheResult) {
                        ViseLog.i("request onSuccess!");
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.isCache()) {
                            showResponseData.setText("From Cache:\n" + cacheResult.getCacheData().toString());
                        } else {
                            showResponseData.setText("From Remote:\n" + cacheResult.getCacheData().toString());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                });
    }

    private void request_get_4() {
        showResponseData.setText("");
        ViseHttp.GET("getAuthor")
                .tag("request_get_4")
                .setLocalCache(true)
                .cacheMode(CacheMode.ONLY_CACHE)
                .request(new ACallback<CacheResult<AuthorModel>>() {
                    @Override
                    public void onSuccess(CacheResult<AuthorModel> cacheResult) {
                        ViseLog.i("request onSuccess!");
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.isCache()) {
                            showResponseData.setText("From Cache:\n" + cacheResult.getCacheData().toString());
                        } else {
                            showResponseData.setText("From Remote:\n" + cacheResult.getCacheData().toString());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                });
    }

    private void request_get_5() {
        showResponseData.setText("");
        ViseHttp.GET("getAuthor")
                .tag("request_get_5")
                .setLocalCache(true)
                .cacheMode(CacheMode.ONLY_REMOTE)
                .request(new ACallback<CacheResult<AuthorModel>>() {
                    @Override
                    public void onSuccess(CacheResult<AuthorModel> cacheResult) {
                        ViseLog.i("request onSuccess!");
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.isCache()) {
                            showResponseData.setText("From Cache:\n" + cacheResult.getCacheData().toString());
                        } else {
                            showResponseData.setText("From Remote:\n" + cacheResult.getCacheData().toString());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                });
    }

    private void request_get_6() {
        showResponseData.setText("");
        ViseHttp.GET("getAuthor")
                .tag("request_get_6")
                .setLocalCache(true)
                .cacheMode(CacheMode.CACHE_AND_REMOTE)
                .request(new ACallback<CacheResult<AuthorModel>>() {
                    @Override
                    public void onSuccess(CacheResult<AuthorModel> cacheResult) {
                        ViseLog.i("request onSuccess!");
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.isCache()) {
                            showResponseData.setText("From Cache:\n" + cacheResult.getCacheData().toString());
                        } else {
                            showResponseData.setText("From Remote:\n" + cacheResult.getCacheData().toString());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                });
    }

    private void request_get_7() {
        showResponseData.setText("");
        ViseHttp.GET("getString")
                .tag("request_get_7")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ViseLog.i("request onSuccess!");
                        if (data == null) {
                            return;
                        }
                        showResponseData.setText(data);
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                });
    }

    private void request_get_8() {
        showResponseData.setText("");
        ViseHttp.GET("getListAuthor")
                .tag("request_get_8")
                .request(new ACallback<List<AuthorModel>>() {
                    @Override
                    public void onSuccess(List<AuthorModel> authorModel) {
                        ViseLog.i("request onSuccess!");
                        if (authorModel == null) {
                            return;
                        }
                        showResponseData.setText(authorModel.toString());
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                });
    }

    private void request_get_9() {
        showResponseData.setText("");
        ViseHttp.BASE(new ApiGetRequest("getApiResultAuthor").tag("request_get_9")).request(new ACallback<AuthorModel>() {
            @Override
            public void onSuccess(AuthorModel authorModel) {
                ViseLog.i("request onSuccess!");
                if (authorModel == null) {
                    return;
                }
                showResponseData.setText(authorModel.toString());
            }

            @Override
            public void onFail(int errCode, String errMsg) {
                ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
            }
        });
    }

    private void request_get_10() {
        showResponseData.setText("");
        ViseHttp.BASE(new ApiGetRequest("getApiResultString").tag("request_get_10")).request(new ACallback<String>() {
            @Override
            public void onSuccess(String data) {
                ViseLog.i("request onSuccess!");
                if (data == null) {
                    return;
                }
                showResponseData.setText(data);
            }

            @Override
            public void onFail(int errCode, String errMsg) {
                ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
            }
        });
    }

    private void request_get_11() {
        showResponseData.setText("");
        ViseHttp.BASE(new ApiGetRequest("getApiResultListAuthor").tag("request_get_11")).request(new ACallback<List<AuthorModel>>() {
            @Override
            public void onSuccess(List<AuthorModel> authorModel) {
                ViseLog.i("request onSuccess!");
                if (authorModel == null) {
                    return;
                }
                showResponseData.setText(authorModel.toString());
            }

            @Override
            public void onFail(int errCode, String errMsg) {
                ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
            }
        });
    }

    private void request_post_1() {
        showResponseData.setText("");
        ViseHttp.BASE(new ApiPostRequest("postAuthor").tag("request_post_1")).request(new ACallback<String>() {
            @Override
            public void onSuccess(String data) {
                ViseLog.i("request onSuccess!");
                if (data == null) {
                    return;
                }
                showResponseData.setText(data);
            }

            @Override
            public void onFail(int errCode, String errMsg) {
                ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
            }
        });
    }

    private void request_post_2() {
        showResponseData.setText("");
        ViseHttp.BASE(new ApiPostRequest("postFormAuthor")
                .tag("request_post_2")
                .addForm("author_name", getString(R.string.author_name))
                .addForm("author_nickname", getString(R.string.author_nickname))
                .addForm("author_account", "xiaoyaoyou1212")
                .addForm("author_github", "https://github.com/xiaoyaoyou1212")
                .addForm("author_csdn", "http://blog.csdn.net/xiaoyaoyou1212")
                .addForm("author_websit", "http://www.huwei.tech/")
                .addForm("author_introduction", getString(R.string.author_introduction)))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ViseLog.i("request onSuccess!");
                        if (data == null) {
                            return;
                        }
                        showResponseData.setText(data);
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                });
    }

    private void request_post_3() {
        showResponseData.setText("");
        AuthorModel mAuthorModel = new AuthorModel();
        mAuthorModel.setAuthor_id(1008);
        mAuthorModel.setAuthor_name(getString(R.string.author_name));
        mAuthorModel.setAuthor_nickname(getString(R.string.author_nickname));
        mAuthorModel.setAuthor_account("xiaoyaoyou1212");
        mAuthorModel.setAuthor_github("https://github.com/xiaoyaoyou1212");
        mAuthorModel.setAuthor_csdn("http://blog.csdn.net/xiaoyaoyou1212");
        mAuthorModel.setAuthor_websit("http://www.huwei.tech/");
        mAuthorModel.setAuthor_introduction(getString(R.string.author_introduction));
        ViseHttp.BASE(new ApiPostRequest("postJsonAuthor")
                .tag("request_post_3")
                .setJson(GsonUtil.gson().toJson(mAuthorModel)))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ViseLog.i("request onSuccess!");
                        if (data == null) {
                            return;
                        }
                        showResponseData.setText(data);
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                });
    }

    private void request_post_4() {
        showResponseData.setText("");
        AuthorModel mAuthorModel = new AuthorModel();
        mAuthorModel.setAuthor_id(1009);
        mAuthorModel.setAuthor_name(getString(R.string.author_name));
        mAuthorModel.setAuthor_nickname(getString(R.string.author_nickname));
        mAuthorModel.setAuthor_account("xiaoyaoyou1212");
        mAuthorModel.setAuthor_github("https://github.com/xiaoyaoyou1212");
        mAuthorModel.setAuthor_csdn("http://blog.csdn.net/xiaoyaoyou1212");
        mAuthorModel.setAuthor_websit("http://www.huwei.tech/");
        mAuthorModel.setAuthor_introduction(getString(R.string.author_introduction));
        ViseHttp.BASE(new ApiPostRequest("postUrlAuthor")
                .tag("request_post_4")
                .addUrlParam("appId", "10001")
                .addUrlParam("appType", "Android")
                .setJson(GsonUtil.gson().toJson(mAuthorModel)))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        ViseLog.i("request onSuccess!");
                        if (data == null) {
                            return;
                        }
                        showResponseData.setText(data);
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                });
    }

    private void request_retrofit_1() {
        ViseHttp.RETROFIT()
                .create(AuthorService.class)
                .getAuthor()
                .compose(ApiTransformer.<AuthorModel>norTransformer())
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<AuthorModel>() {
                    @Override
                    public void onSuccess(AuthorModel authorModel) {
                        ViseLog.i("request onSuccess!");
                        if (authorModel == null) {
                            return;
                        }
                        showResponseData.setText(authorModel.toString());
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                }));
    }

    private void request_retrofit_2() {
        ViseHttp.RETROFIT()
                .create(AuthorService.class)
                .getAuthor()
                .compose(ApiTransformer.<AuthorModel>norTransformer())
                .compose(ViseHttp.getApiCache().<AuthorModel>transformer(CacheMode.CACHE_AND_REMOTE, AuthorModel.class))
                .subscribe(new ApiCallbackSubscriber<>(new ACallback<CacheResult<AuthorModel>>() {
                    @Override
                    public void onSuccess(CacheResult<AuthorModel> cacheResult) {
                        ViseLog.i("request onSuccess!");
                        if (cacheResult == null || cacheResult.getCacheData() == null) {
                            return;
                        }
                        if (cacheResult.isCache()) {
                            showResponseData.setText("From Cache:\n" + cacheResult.getCacheData().toString());
                        } else {
                            showResponseData.setText("From Remote:\n" + cacheResult.getCacheData().toString());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        ViseLog.e("request errorCode:" + errCode + ",errorMsg:" + errMsg);
                    }
                }));
    }

    @Override
    public void onDestroy() {
        ViseHttp.cancelAll();
        super.onDestroy();
    }
    }

