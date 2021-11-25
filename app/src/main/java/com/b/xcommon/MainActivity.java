package com.b.xcommon;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.nightonke.boommenu.Animation.AnimationManager;
import com.nightonke.boommenu.Animation.BoomEnum;
import com.nightonke.boommenu.Animation.EaseEnum;
import com.nightonke.boommenu.Animation.OrderEnum;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListener;
import com.nightonke.boommenu.OnBoomListenerAdapter;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String MAIN_ACTIVITY = "MainActivity";
    View mExpandView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BoomMenuButton bmb = findViewById(R.id.fab);
        bmb.setBoomEnum(BoomEnum.RANDOM);
        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setShowMoveEaseEnum(EaseEnum.EaseInSine);
        bmb.setOrderEnum(OrderEnum.RANDOM);
        bmb.setShowDelay(0);
        bmb.setUse3DTransformAnimation(true);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_9_1);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_9_1);
        bmb.setShowDuration(400);
        bmb.setCacheOptimization(false);

        initListener(bmb);

        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++)
            bmb.addBuilder(getTextOutsideCircleButtonBuilder(i));
    }

    private void initListener(final BoomMenuButton bmb) {
        bmb.setOnBoomListener(new OnBoomListenerAdapter() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                bmb.reboomImmediately();
            }
            @Override
            public void dim() {
                mExpandView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.xexpand, bmb.getBackgroundView(), false);
                mExpandView.findViewById(R.id.expand_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bmb.reboom();
                    }
                });
                bmb.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bmb.addExpandView(mExpandView);
                        AnimationManager.animate("rotation", 0, 300, new float[]{0, 135},
                                new BounceInterpolator() , new ArrayList<View>(){{add(mExpandView);}});
                    }
                }, 100);
            }

            @Override
            public void light() {
                bmb.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AnimationManager.animate("rotation", 0, 300, new float[]{135, 0},
                                new BounceInterpolator() , new ArrayList<View>(){{add(mExpandView);}});
                    }
                }, 100);
            }
        });
    }

    static TextOutsideCircleButton.Builder getTextOutsideCircleButtonBuilder(int i) {
        return new TextOutsideCircleButton.Builder()
                .normalImageRes(imageResources[i])
                .normalTextRes(item_text[i])
                .imageRect(new Rect(Util.dp2px(0), Util.dp2px(0),Util.dp2px(50), Util.dp2px(50)))
                .textTopMargin(Util.dp2px(-14))
                .background(false)
                .subTextGravity(Gravity.CENTER)
                .shadowEffect(false)
                .subNormalTextRes(item_description[i])
                .buttonRadius(Util.dp2px(45))
                .subnormalTextColorRes(R.color.sub_text_color);
    }

    private static final int[] imageResources = new int[]{
            R.drawable.in_bank_bill_logo,
            R.drawable.in_invoice_logo,
            R.drawable.input_bank_bill,
            R.drawable.input_invoice_paper,
            R.drawable.input_invoice_sms_web,
            R.drawable.input_invoice_temp_estimates,
            R.drawable.input_invoice_u_key,
            R.drawable.input_invoice_wx,
            R.drawable.input_pc,
            R.drawable.input_phone,
            R.drawable.input_zero_report,
            R.drawable.input_zero_report,
            R.drawable.input_zero_report,
            R.drawable.input_zero_report,
            R.drawable.input_zero_report,
            R.drawable.input_zero_report
    };

    private static final int[] item_text = new int[]{
            R.string.input_paper_invoice,
            R.string.input_electronic_invoice,
            R.string.input_electronic_invoice,
            R.string.input_electronic_invoice,
            R.string.input_from_ofd_input,
            R.string.input_from_ofd_scan,
            R.string.input_from_import,
            R.string.input_item_zero_report,
            R.string.input_item_temp_estimate,
    };

    private static final int[] item_description = new int[]{
            R.string.input_qr_code_sub_text,
            R.string.input_take_picture_sub_text,
            R.string.input_item_invoice_phone_sub,
            R.string.input_from_from_wx_sub_text,
            R.string.input_from_ofd_input_sub_text,
            R.string.input_take_picture_sub_text,
            R.string.input_from_import_sub_text,
            R.string.input_item_zero_report_description,
            R.string.input_item_temp_estimate_description,
    };

    void testxxx(){
        //        KLogWrap.init(KLockerLogHelper.getInstance(this));
//        KLogWrap.error("Tag", "XXXX");

//        String rest =  XBase64Util.decodeToString("eyJ0eXBlIjo2LCJleHRyYSI6IiIsImRsZyI6eyJ0aXRsZSI6IuaPkOekuiIsIm1lc3NhZ2UiOiLlvZPliY3mmK/mvJTnpLrotKblj7fvvIzor7flhYjms6jlhowiLCJsZWZ0QnRuIjoi55m75b2VIiwicmlnaHRCdG4iOiLnq4vljbPms6jlhowiLCJhY3Rpb24iOjF9fQ==");
//        Log.e(MAIN_ACTIVITY, rest);
//
//        Log.e(MAIN_ACTIVITY,Environment.getExternalStorageDirectory().getAbsolutePath());
//        Log.e(MAIN_ACTIVITY,Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
//
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
//        XFileUtils.getAllFiles(path+ "/android/data/com.tencent.mm/", "pdf");
//        XFileUtils.getAllFiles(path+ "/android/data/com.tencent.mm/MicroMsg/Download/", "pdf");
    }
}
