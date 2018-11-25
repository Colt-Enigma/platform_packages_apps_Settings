package com.android.settings;

 import android.widget.ImageView;
 import com.bumptech.glide.Glide;
 import android.content.Context;
 import android.util.AttributeSet;

 public class GlideImageView extends ImageView
 {

     /**
      * @param context
      */
     public GlideImageView(Context context)
     {
         super(context);
     }

     /**
      * @param context
      * @param attrs
      */
     public GlideImageView(Context context, AttributeSet attrs)
     {
         super(context, attrs);
     }

     /**
      * @param context
      * @param attrs
      * @param defStyle
      */
     public GlideImageView(Context context, AttributeSet attrs, int defStyle)
     {
         super(context, attrs, defStyle);
     }

     @Override
     protected void onAttachedToWindow() {
         super.onAttachedToWindow();
 	Glide.with(this.getContext()).load(R.raw.colt_logo).asGif().crossFade().into(this);
     }
 }
