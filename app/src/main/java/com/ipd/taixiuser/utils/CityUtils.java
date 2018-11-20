package com.ipd.taixiuser.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.widget.wheelview.OnWheelChangedListener;
import com.ipd.jumpbox.jumpboxlibrary.widget.wheelview.WheelView;
import com.ipd.jumpbox.jumpboxlibrary.widget.wheelview.adapters.AbstractWheelTextAdapter;
import com.ipd.taixiuser.R;
import com.ipd.taixiuser.bean.CityBean;
import com.ipd.taixiuser.db.CityDao;
import com.ipd.taixiuser.platform.http.RxScheduler;

import java.util.List;

import rx.Observable;
import rx.Subscriber;


public class CityUtils implements OnWheelChangedListener {
    private View mRootView;

    public static CityUtils getInstance() {
        return new CityUtils();
    }

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewArea;
    private TextView mBtnConfirm;
    private TextView mBtnCancel;
    private Context context;
    private CityDao cityDao;
    private ProvinceWheelAdapter provinceAdapter;
    private CityWheelAdapter cityAdapter;
    private AreaWheelAdapter areaAdapter;
    private Dialog cityDialog;

    public void showSelectDialog(final Context context, final onSelectCityFinishListener listener) {
        cityDao = new CityDao();
        this.context = context;
        mRootView = View.inflate(context, R.layout.city_choose_dialog, null);
        mViewProvince = (WheelView) mRootView.findViewById(R.id.wv_country);
        mViewCity = (WheelView) mRootView.findViewById(R.id.wv_city);
        mViewArea = (WheelView) mRootView.findViewById(R.id.wv_area);
        mBtnConfirm = (TextView) mRootView.findViewById(R.id.tv_commit);
        mBtnConfirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                cityDialog.dismiss();
                CityBean province = provinceAdapter.getCity(mViewProvince.getCurrentItem());
                CityBean city = cityAdapter.getCity(mViewCity.getCurrentItem());
                CityBean area = areaAdapter.getCity(mViewArea.getCurrentItem());

                if (listener != null) {
                    listener.onFinish(province, city, area);
                }
            }
        });
        mBtnCancel = (TextView) mRootView.findViewById(R.id.tv_cancel);
        mBtnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                cityDialog.dismiss();
            }
        });

        setUpListener();
        setUpData();

    }

    private void setUpListener() {
        mViewProvince.addChangingListener(this);
        mViewCity.addChangingListener(this);
    }

    private void setUpData() {
        List<CityBean> province = cityDao.getCityByParentId("0");

        provinceAdapter = new ProvinceWheelAdapter(context, province);
        mViewProvince.setViewAdapter(provinceAdapter);
        mViewProvince.setCurrentItem(0);

        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewArea.setVisibleItems(7);
        updateCities();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreaes();
        }
    }


    private Subscriber mCitySubscriber, mAreaSubscriber;

    private void updateCities() {
        if (mCitySubscriber != null) {
            mCitySubscriber.unsubscribe();
        }

        Observable.create(new Observable.OnSubscribe<List<CityBean>>() {
            @Override
            public void call(Subscriber<? super List<CityBean>> subscriber) {
                List<CityBean> regionList = cityDao.getCityByParentId(provinceAdapter.getCity(mViewProvince.getCurrentItem()).getId() + "");
                subscriber.onNext(regionList);
            }
        })
                .compose(RxScheduler.Companion.<List<CityBean>>applyScheduler())
                .subscribe(mCitySubscriber = new Subscriber<List<CityBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<CityBean> cityBeans) {
                        cityAdapter = new CityWheelAdapter(context, cityBeans);
                        mViewCity.setViewAdapter(cityAdapter);
                        mViewCity.setCurrentItem(0);
                        updateAreaes();
                    }
                });
    }

    private void updateAreaes() {
        if (mAreaSubscriber != null) {
            mAreaSubscriber.unsubscribe();
        }

        Observable.create(new Observable.OnSubscribe<List<CityBean>>() {
            @Override
            public void call(Subscriber<? super List<CityBean>> subscriber) {
                List<CityBean> regionList = cityDao.getCityByParentId(cityAdapter.getCity(mViewCity.getCurrentItem()).getId() + "");
                subscriber.onNext(regionList);
            }
        })
                .compose(RxScheduler.Companion.<List<CityBean>>applyScheduler())
                .subscribe(mAreaSubscriber = new Subscriber<List<CityBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<CityBean> cityBeans) {
                        areaAdapter = new AreaWheelAdapter(context, cityBeans);
                        mViewArea.setViewAdapter(areaAdapter);
                        mViewArea.setCurrentItem(0);

                        showPopupIfNotExist();
                    }
                });
    }

    private void showPopupIfNotExist() {
        if (cityDialog == null) {
            cityDialog = new Dialog(context, R.style.dialog);
            cityDialog.setContentView(mRootView);
            cityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    cityDialog = null;
                    if (mCitySubscriber != null) {
                        mCitySubscriber.unsubscribe();
                        mCitySubscriber = null;
                    }
                    if (mAreaSubscriber != null) {
                        mAreaSubscriber.unsubscribe();
                        mAreaSubscriber = null;
                    }
                    cityDao = null;
                }
            });

            cityDialog.getWindow().setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams params = cityDialog.getWindow().getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            cityDialog.getWindow().setAttributes(params);

            cityDialog.show();
        }

    }


    class ProvinceWheelAdapter extends BaseCityAdapter {

        public ProvinceWheelAdapter(Context context, List<CityBean> list) {
            super(context, list);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index).getTitle();
        }

    }

    class CityWheelAdapter extends BaseCityAdapter {

        public CityWheelAdapter(Context context, List<CityBean> list) {
            super(context, list);
        }


        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index).getTitle();
        }

    }

    class AreaWheelAdapter extends BaseCityAdapter {

        public AreaWheelAdapter(Context context, List<CityBean> list) {
            super(context, list);
        }


        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index).getTitle();
        }

    }

    public interface onSelectCityFinishListener {
        void onFinish(CityBean province, CityBean city, CityBean area);
    }

    public abstract class BaseCityAdapter extends AbstractWheelTextAdapter {
        public List<CityBean> list;

        public BaseCityAdapter(Context context, List<CityBean> list) {
            super(context);
            this.list = list;
        }

        @Override
        public int getItemsCount() {
            return list == null ? 0 : list.size();
        }

        public CityBean getCity(int position) {
            return list.get(position);
        }


        @Override
        protected abstract CharSequence getItemText(int index);
    }

}
