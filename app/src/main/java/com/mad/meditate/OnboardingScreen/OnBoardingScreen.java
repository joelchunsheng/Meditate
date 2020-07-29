package com.android.meditate.OnboardingScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.meditate.Login.LoginActivity;
import com.android.meditate.MainActivity;
import com.android.meditate.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class OnBoardingScreen extends AppCompatActivity {

    // variables
    private ViewPager mSlideviewPager;
    private LinearLayout mDotLayout;

    // the dots to represent the sliding pages
    private TextView[] mDots;

    private SliderAdapter sliderAdapter;

    private Button mNextButton;
    private Button mBackButton;
    private Button mFinishButton;

    private int mCurrentPage = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // to set the onboarding to fullscreen and hide the action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboarding);

        // to check if the user has opened it before or not
        if (restorePreFData()) {

            Intent main = new Intent(this, LoginActivity.class);
            startActivity(main);
            finish();
        }

        mSlideviewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        // adding the next, back and finish button
        mNextButton = (Button) findViewById(R.id.nextButton);
        mBackButton = (Button) findViewById(R.id.backButton);
        mFinishButton = (Button) findViewById(R.id.finishButton);

        // call the adapter
        sliderAdapter = new SliderAdapter(this);
        mSlideviewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideviewPager.addOnPageChangeListener(viewListener);

        // next button click listener
        mNextButton.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view)
            {
                mCurrentPage = mSlideviewPager.getCurrentItem();
                mSlideviewPager.setCurrentItem(mCurrentPage + 1);

            }
        });

        // back button click listener
        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                mSlideviewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

        // finish button click listener
        mFinishButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {

                // whether the user has launch the app before or not
                savePrefsData();

                // when reach the last screen
                loadLastScreen();
            }
        });
}
    // show the finish button and hide the indicator and the next button
    public void loadLastScreen(){

        // Launch the main Activity, called MainActivity
        Intent main = new Intent(this, LoginActivity.class);
        startActivity(main);

        // Close the OnboardingActivity
        finish();
    }

    // to check if the user has already past the onboarding screen
    private void savePrefsData(){

        SharedPreferences userPref = this.getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPref.edit();
        editor.putBoolean("isOnBoardingScreenOpened", true);
        editor.commit();
    }

    // if the user has it open before
    private boolean restorePreFData(){

        SharedPreferences userPref = this.getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);
        Boolean isOnBoardingScreenOpenedBefore = userPref.getBoolean("isOnBoardingScreenOpened", false);
        return isOnBoardingScreenOpenedBefore;
    }

    public void addDotsIndicator(int position){

        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorGreyTransparent));

            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length > 0) {

            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

            addDotsIndicator(i);
            mCurrentPage = i;

            if(i == 0){

                mNextButton.setEnabled(true);
                mBackButton.setEnabled(false);
                mFinishButton.setEnabled(false);
                mBackButton.setVisibility(View.INVISIBLE);
                mFinishButton.setVisibility(View.INVISIBLE);

                mNextButton.setText("Next");
                mBackButton.setText("");
            } else if(i == mDots.length -1){

                mNextButton.setEnabled(false);
                mBackButton.setEnabled(true);
                mFinishButton.setEnabled(true);
                mBackButton.setVisibility(View.VISIBLE);
                mFinishButton.setVisibility(View.VISIBLE);

                mNextButton.setText("");
                mBackButton.setText("Back");
                mFinishButton.setText("Finish");
            } else {

                mNextButton.setEnabled(true);
                mBackButton.setEnabled(true);
                mFinishButton.setEnabled(false);
                mBackButton.setVisibility(View.VISIBLE);
                mFinishButton.setVisibility(View.INVISIBLE);

                mNextButton.setText("Next");
                mBackButton.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}
