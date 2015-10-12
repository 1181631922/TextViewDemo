package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adapter.MessageGroupFragmentAdapter;
import com.example.textviewdemo.R;

/**
 * 棣栭〉
 * @author Ansen
 * @create time 2015-09-08
 */
public class MainFragment extends Fragment {
	private ViewPager vPager;
	private List<Fragment> list = new ArrayList<Fragment>();
	private MessageGroupFragmentAdapter adapter;

	private ImageView ivShapeCircle;
	private TextView tvFollow,tvRecommend,tvLocation;
	
    private int offset=0;//鍋忕Щ閲�216  鎴戣繖杈瑰彧鏄妇渚嬭鏄�,涓嶅悓鎵嬫満鍊间笉涓�鏍�
    private int currentIndex=1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, null);
		

		/**
		 * 鍒濆鍖栦笁涓狥ragment  骞朵笖濉厖鍒癡iewPager
		 */
		vPager = (ViewPager) rootView.findViewById(R.id.viewpager_home);
		DynamicFragment dynamicFragment = new DynamicFragment();
		MessageFragment messageFragment = new MessageFragment();
		PersonFragment personFragment = new PersonFragment();
		list.add(dynamicFragment);
		list.add(messageFragment);
		list.add(personFragment);
//		adapter = new MessageGroupFragmentAdapter(getActivity().getSupportFragmentManager(), list);
		adapter = new MessageGroupFragmentAdapter(getChildFragmentManager(), list);
		vPager.setAdapter(adapter);
		vPager.setOffscreenPageLimit(2);
		vPager.setCurrentItem(1);
		vPager.setOnPageChangeListener(pageChangeListener);

		
		ivShapeCircle = (ImageView) rootView.findViewById(R.id.iv_shape_circle);
		
		tvFollow=(TextView) rootView.findViewById(R.id.tv_follow);
		tvRecommend=(TextView) rootView.findViewById(R.id.tv_recommend);
		tvRecommend.setSelected(true);//鎺ㄨ崘榛樿閫変腑
		tvLocation=(TextView) rootView.findViewById(R.id.tv_location);
		
		/**
		 * 鏍囬鏍忎笁涓寜閽缃偣鍑绘晥鏋�
		 */
		tvFollow.setOnClickListener(clickListener);
		tvRecommend.setOnClickListener(clickListener);
		tvLocation.setOnClickListener(clickListener);

		initCursorPosition();
		return rootView;
	}
	
	private OnClickListener clickListener=new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_follow:
				//褰撴垜浠缃畇etCurrentItem鐨勬椂鍊欏氨浼氳Е鍙憊iewpager鐨凮nPageChangeListener鍊熷彛,
				//鎵�浠ユ垜浠笉闇�瑕佸幓鏀瑰彉鏍囬鏍忓瓧浣撳暐鐨�
				vPager.setCurrentItem(0);
				break;
			case R.id.tv_recommend:
				vPager.setCurrentItem(1);
				break;
			case R.id.tv_location:
				vPager.setCurrentItem(2);
				break;
			}
		}
	};

	private void initCursorPosition() {
		DisplayMetrics metric = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
		int width = metric.widthPixels;
		Matrix matrix = new Matrix();
		
		//鏍囬鏍忔垜鐢╳eight璁剧疆鏉冮噸  鍒嗘垚5浠�
		//(width / 5) * 2  杩欓噷琛ㄧず鏍囬鏍忎袱涓帶浠剁殑瀹藉害
		//(width / 10)  鏍囬鏍忎竴涓帶浠剁殑2鍒嗕箣涓�
		//7  绾︾瓑浜庡師鐐瑰搴︾殑涓�鍗�
		matrix.postTranslate((width / 5) * 2 + (width / 10)-7,0);//鍥剧墖骞崇Щ
		ivShapeCircle.setImageMatrix(matrix);
		
		//涓�涓帶浠剁殑瀹藉害  鎴戠殑鎵嬫満瀹藉害鏄�1080/5=216 涓嶅悓鐨勬墜鏈哄搴︿細涓嶄竴鏍峰摝
		offset=(width / 5);
	}

	/**
	 * ViewPager婊戝姩鐩戝惉,鐢ㄤ綅绉诲姩鐢诲疄鐜版寚绀哄櫒鏁堟灉
	 * 
	 * TranslateAnimation 寮鸿皟涓�涓湴鏂�,鏃犺浣犵Щ鍔ㄤ簡澶氬皯娆�,鐜板湪鍋滅暀鍦ㄥ摢閲�,浣犵殑璧峰浣嶇疆浠庢湭鍙樺寲杩�.
	 * 渚嬪:鎴戣繖涓猟emo閲岄潰  鎺ㄨ崘绉诲姩鍒颁簡鍚屽煄,鎸囩ず鍣ㄤ篃鍋滅暀鍒颁簡鍚屽煄涓嬮潰,浣嗘槸鎸囩ず鍣ㄥ湪灞忓箷涓婄殑浣嶇疆杩樻槸鎺ㄨ崘涓嬮潰.
	 */
	private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int index) {
			changeTextColor(index);
			translateAnimation(index);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};
	
	/**
	 * 鏀瑰彉鏍囬鏍忓瓧浣撻鑹�
	 * @param index
	 */
	private void changeTextColor(int index){
		tvFollow.setSelected(false);
		tvRecommend.setSelected(false);
		tvLocation.setSelected(false);
		
		switch (index) {
		case 0:
			tvFollow.setSelected(true);
			break;
		case 1:
			tvRecommend.setSelected(true);
			break;
		case 2:
			tvLocation.setSelected(true);
			break;
		}
	}
	
	/**
	 * 绉诲姩鏍囬鏍忕偣鐐圭偣...
	 * @param index
	 */
	private void translateAnimation(int index){
		TranslateAnimation animation = null;
		switch(index){
		case 0:
			if(currentIndex==1){//浠庢帹鑽愮Щ鍔ㄥ埌鍏虫敞   X鍧愭爣鍚戝乏绉诲姩216
				animation=new TranslateAnimation(0,-offset,0,0);
			}else if (currentIndex == 2) {//浠庡悓鍩庣Щ鍔ㄥ埌鍏虫敞   X鍧愭爣鍚戝乏绉诲姩216*2  璁颁綇璧峰x鍧愭爣鏄悓鍩庨偅閲�
                animation = new TranslateAnimation(offset, -offset, 0, 0);  
            }
			break;
		case 1:
            if(currentIndex==0){//浠庡叧娉ㄧЩ鍔ㄥ埌鎺ㄨ崘   X鍧愭爣鍚戝彸绉诲姩216
            		animation=new TranslateAnimation(-offset,0,0,0);
			}else if(currentIndex==2){//浠庡悓鍩庣Щ鍔ㄥ埌鎺ㄨ崘   X鍧愭爣鍚戝乏绉诲姩216
				animation=new TranslateAnimation(offset, 0,0,0);
			}
			break;
		case 2:
			if (currentIndex == 0) {//浠庡叧娉ㄧЩ鍔ㄥ埌鍚屽煄   X鍧愭爣鍚戝彸绉诲姩216*2  璁颁綇璧峰x鍧愭爣鏄叧娉ㄩ偅閲�
                animation = new TranslateAnimation(-offset, offset, 0, 0);
            } else if(currentIndex==1){//浠庢帹鑽愮Щ鍔ㄥ埌鍚屽煄   X鍧愭爣鍚戝彸绉诲姩216
				animation=new TranslateAnimation(0,offset,0,0);
			}
			break;
		}
		animation.setFillAfter(true);
		animation.setDuration(300);
		ivShapeCircle.startAnimation(animation);
		
		currentIndex=index;
	}
}
