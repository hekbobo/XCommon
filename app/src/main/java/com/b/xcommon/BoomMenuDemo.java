package com.b.xcommon;

import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;

import com.nightonke.boommenu.Animation.AnimationManager;
import com.nightonke.boommenu.Animation.BoomEnum;
import com.nightonke.boommenu.Animation.EaseEnum;
import com.nightonke.boommenu.Animation.OrderEnum;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceAlignmentEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.OnBoomListenerAdapter;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

import java.util.ArrayList;

public class BoomMenuDemo {
    View mExpandView;
    FrameLayout mExpandBtn;

    public void init(BoomMenuButton fab){

        BoomMenuButton bmb = fab;
        bmb.setBoomEnum(BoomEnum.RANDOM);
        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setShowMoveEaseEnum(EaseEnum.EaseInSine);
        bmb.setOrderEnum(OrderEnum.RANDOM);
        bmb.setShowDelay(0);
        bmb.setUse3DTransformAnimation(true);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_11_1);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_11_1);
        bmb.setCacheOptimization(false);
        bmb.setButtonPlaceAlignmentEnum(ButtonPlaceAlignmentEnum.Bottom);
        bmb.setButtonBottomMargin(Util.dp2px(100));

        bmb.setShowDuration(200);
        bmb.setHideDuration(100);


        initListener(bmb);

//        address();

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
                mExpandView = LayoutInflater.from(bmb.getContext()).inflate(R.layout.xexpand, bmb.getBackgroundView(), false);
                mExpandBtn = mExpandView.findViewById(R.id.expand_btn);
                mExpandBtn.setOnClickListener(new View.OnClickListener() {
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
                                new BounceInterpolator() , new ArrayList<View>(){{add(mExpandBtn);}});
                    }
                }, 100);
            }

            @Override
            public void light() {
                bmb.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AnimationManager.animate("rotation", 0, 300, new float[]{135, 0},
                                new BounceInterpolator() , new ArrayList<View>(){{add(mExpandBtn);}});
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
            R.drawable.input_zkp_new,
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
            R.string.input_item_temp_estimate,
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
            R.string.input_item_temp_estimate_description,
            R.string.input_item_temp_estimate_description,
    };
}
