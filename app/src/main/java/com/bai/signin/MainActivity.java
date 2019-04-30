package com.bai.signin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.bai.signin.adapter.DateAdapter;
import com.bai.signin.adapter.WeekAdapter;
import com.bai.signin.decoration.CommonItemDecoration;
import com.bai.signin.model.SignDateModel;
import com.bai.signin.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvCurrentDate; //今天的日期
    private TextView tvIntegral; //积分总数
    private TextView tvSignedDays; //累计签到次数
    private TextView tvSignBtn; //签到按钮

    private DateAdapter dateAdapter;

    private List<SignDateModel> models = new ArrayList<>(35);
    private int current;
    private boolean isRequest;
    private String ruleUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.layout_back).setOnClickListener(v -> finish());
        ((TextView) findViewById(R.id.toolbar_title)).setText(R.string.sign_in_title);
        ((TextView) findViewById(R.id.sign_ruler)).setText(R.string.sign_in_ruler);
        findViewById(R.id.sign_ruler).setOnClickListener(this);

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        tvCurrentDate = findViewById(R.id.tv_current_date);
        tvCurrentDate.setText(sf.format(new Date()));
        tvIntegral = findViewById(R.id.tv_integral);
        tvIntegral.setText("0");
        tvSignedDays = findViewById(R.id.tv_signed_days);
        tvSignedDays.setText(getString(R.string.sign_in_total_days, 0));
        tvSignBtn = findViewById(R.id.tv_sign_btn);
        tvSignBtn.setOnClickListener(this);

        //初始化日历
        RecyclerView rv_sign_week = findViewById(R.id.rv_sign_week);
        RecyclerView rv_sign_date = findViewById(R.id.rv_sign_date);

        GridLayoutManager weekLayoutManager = new GridLayoutManager(this, 7);
        WeekAdapter weekAdapter = new WeekAdapter(this);
        rv_sign_week.setLayoutManager(weekLayoutManager);
        rv_sign_week.setAdapter(weekAdapter);

        GridLayoutManager dataLayoutManager = new GridLayoutManager(this, 7);
        dateAdapter = new DateAdapter(this);
        rv_sign_date.setLayoutManager(dataLayoutManager);
        rv_sign_date.setAdapter(dateAdapter);
        rv_sign_date.addItemDecoration(new CommonItemDecoration(dip2px(14),
                dip2px(8), dip2px(7), dip2px(4), dip2px(7), dip2px(4)));

        initData(null);
    }

    private void initData(Date currentDate) {
        models.clear();
        current = 14 + DateUtil.getCurrentDayOfWeek(currentDate) - 1; //今天在日历中的位置
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        for (int i = 0; i < current + 1; i++) {
            SignDateModel model = new SignDateModel();
            //初始化前半部分日历数据
            Date lastDate = DateUtil.getDateForLastDay(currentDate, current - i);
            model.setContent(String.valueOf(lastDate.getDate()));
            model.setDate(sf.format(lastDate));
            if (lastDate.getDay() == 0 || lastDate.getDay() == 6) {
                model.setStatus(SignDateModel.STATUS_SAT_OR_SUN);
            }
            if (current - i == 0) {
                model.setStatus(SignDateModel.STATUS_CURRENT_DAY);
            }
            models.add(i, model);
        }
        int after = 34 - current; //今天之后需要补齐多少天
        for (int i = 1; i < after + 1; i++) {
            SignDateModel model = new SignDateModel();
            //初始化后半部分日历数据
            Date nextDate = DateUtil.getDateForNextDay(currentDate, i);
            model.setContent(String.valueOf(nextDate.getDate()));
            model.setDate(sf.format(nextDate));
            if (nextDate.getDay() == 0 || nextDate.getDay() == 6) {
                model.setStatus(SignDateModel.STATUS_SAT_OR_SUN);
            }
            models.add(current + i, model);
        }
        dateAdapter.setData(models);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_sign_btn) {
            if (isRequest) {
                return;
            }
            models.get(current).setStatus(SignDateModel.STATUS_HAVE_SIGN);
            tvIntegral.setText(String.valueOf("100"));
            tvSignedDays.setText(getString(R.string.sign_in_total_days, 100));
            dateAdapter.notifyDataSetChanged();
            tvSignBtn.setText(R.string.sign_in_goto_lottery);
        }
    }

    public float dip2pxFloat(float dp) {
        try {
            DisplayMetrics metrics = getApplicationContext().getResources().getDisplayMetrics();
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
        } catch (Throwable e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public int dip2px(float dp) {
        return (int) (dip2pxFloat(dp) + 0.5f);
    }
}
