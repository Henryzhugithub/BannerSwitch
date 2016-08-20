package com.example.zhl.bannerswitch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import java.util.List;

/**
 * Created by zhl on 2016/4/27.
 */
public class AdViewFlipper extends ViewFlipper implements View.OnTouchListener{

    private float startX;
    private float endX;

    private List<String> imglist;
    private LinearLayout adPointLayout;
    private ImageView[] pointIvs;
    private int adIndex = 0;
    private Context context;

    public AdViewFlipper(Context context){
        this(context,null);
    }

    public AdViewFlipper(Context context, AttributeSet attrs){
        super(context,attrs);
        setOnTouchListener(this);
        this.context = context;
    }

    public void setList(List<String> imglist, LinearLayout adPointLayout){
        this.imglist = imglist;
        this.adPointLayout = adPointLayout;
    }

    public void setBanner(boolean isLocalImage){
        if (imglist==null||imglist.size()==0){
            return;
        }
        removeAllViews();
        pointIvs = new ImageView[imglist.size()];
        adPointLayout.removeAllViews();
        setAdPoints(imglist.size());
        for (int i=0;i<imglist.size();i++){
            if (isLocalImage){
                addView(getLocalImageView(imglist.get(i)));
            }else {
                //addView(getImageView(imglist.get(i)));
            }
        }
        left2right();
    }

    private void setAdPoints(int size){
    	if(pointIvs==null){
    		return ;
    	}
        for (int i=0;i<size;i++){
            pointIvs[i] = new ImageView(context);
            if (i==0){
                pointIvs[i].setImageResource(R.mipmap.ad_point_pressed);
            }else {
                pointIvs[i].setImageResource(R.mipmap.ad_point_normal);
            }
            LinearLayout.LayoutParams pointIvParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            pointIvParams.setMargins(10, 0, 0, 0);
            pointIvs[i].setLayoutParams(pointIvParams);
            adPointLayout.addView(pointIvs[i]);
        }

    }

    private ImageView getLocalImageView(String sysImgs){
        if (sysImgs==null){
            return null;
        }
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        //BMImageUtils.loadLocalImage(imageView,sysImgs,null);
        imageView.setImageResource(R.mipmap.icon);
        return imageView;
    }

    /*
    private ImageView getLocalImageView(String sysImgs){
        if (sysImgs==null){
            return null;
        }
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        //BMImageUtils.loadLocalImage(imageView,sysImgs,null);
        return imageView;
    }
    */

    /*
    private ImageView getImageView(SysImgs sysImgs){
        if (sysImgs==null){
            return null;
        }
        ImageView imageView = new ImageView(AppUtils.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        BMImageUtils.loadImage(imageView,sysImgs.getPictureURL());
        return imageView;
    }
    */

    @Override
    public void showPrevious() {
        super.showPrevious();
        changePre();
    }

    @Override
    public void showNext() {
        super.showNext();
        changeNext();
    }

    public void changePre() {
    	if(pointIvs==null){
    		return ;
    	}
        if (adIndex>0){
            adIndex--;
        }else {
            adIndex = pointIvs.length-1;
        }
        for (int i = 0; i < pointIvs.length; i++) {
            if (adIndex == i) {
                pointIvs[i].setImageResource(R.mipmap.ad_point_pressed);
            } else {
                pointIvs[i].setImageResource(R.mipmap.ad_point_normal);
            }

        }
    }

    public void changeNext() {
    	if(pointIvs==null){
    		return ;
    	}
        if (adIndex < pointIvs.length - 1) {
            adIndex++;
        } else {
            adIndex = 0;
        }
        for (int i = 0; i < pointIvs.length; i++) {
            if (adIndex == i) {
                pointIvs[i].setImageResource(R.mipmap.ad_point_pressed);
            } else {
                pointIvs[i].setImageResource(R.mipmap.ad_point_normal);
            }

        }
    }

    
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = motionEvent.getX();
                getParent().requestDisallowInterceptTouchEvent(true);
    			break;
            case MotionEvent.ACTION_UP:
                endX = motionEvent.getX();
                getParent().requestDisallowInterceptTouchEvent(false);
                if (endX-startX>100){
                    right2left();
                    showPrevious();
                    return true;
                }else if (endX-startX<-100){
                    left2right();
                    showNext();
                    return true;
                }
                break;
        }
        return false;
    }

    private void left2right(){
        setInAnimation(AnimationUtils.loadAnimation(context,
                R.anim.push_left_in));
        setOutAnimation(AnimationUtils.loadAnimation(context,
                R.anim.push_left_out));
    }

    private void right2left(){
        setInAnimation(AnimationUtils.loadAnimation(context,
                R.anim.push_right_in));
        setOutAnimation(AnimationUtils.loadAnimation(context,
                R.anim.push_right_out));
    }
    
}
