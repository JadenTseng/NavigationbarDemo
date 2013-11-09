package com.cheney.android.nativebar;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheney.android.widget.navigation.RollNavigationBar;
import com.cheney.android.widget.navigation.adapter.RollNavigationBarAdapter;

/**
 * 
 * @ClassName: NativebarDemoActivity 
 * @Description: ���Խ��� 
 * @author mr.cheney cheney.cn@live.cn
 * @date 2013��11��9�� ����5:55:41 
 *
 */
public class NativebarDemoActivity extends Activity {
    private static final String TAG = NativebarDemoActivity.class.getSimpleName();
    
    private String[] mTitle = { "��ע", "����", "����", "�", "����" };
    
    private int[] mPhoto = { R.drawable.nav_menu_home, R.drawable.nav_menu_hot,
            R.drawable.nav_menu_category, R.drawable.nav_menu_like,
            R.drawable.nav_menu_me };
    
    private int[] mPhotoSelected = { R.drawable.nav_menu_home_selected,
            R.drawable.nav_menu_hot_selected,
            R.drawable.nav_menu_category_active,
            R.drawable.nav_menu_like_active, R.drawable.nav_menu_me_selected };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigationbar);
        RollNavigationBar rnb = (RollNavigationBar) findViewById(R.id.rollNavigationBar);
        /* ���ƶ�̬���� */
        List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
        for (int i = 0; i < mTitle.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", mTitle[i]);
            map.put("photo", mPhoto[i]);
            map.put("photoSelected", mPhotoSelected[i]);
            list.add(map);
        }
        /* ���û������Ļ���ʱ�䣬ʱ�䷶Χ��0.1~1s�����ڷ�Χ��Ĭ��0.1s */
        rnb.setSelecterMoveContinueTime(0.1f);// ���Բ����ã�Ĭ��0.1s
        /* ���û�������ʽ��ͼƬ�� */
        rnb.setSelecterDrawableSource(R.drawable.nav_menu_bg);// ����
        /* ���õ������ı�ѡλ�� */
        rnb.setSelectedChildPosition(0);// ���Բ�����

        /* ��������չ */
        final MyNavigationBarAdapter adapter = new MyNavigationBarAdapter(this,
                list);
        rnb.setAdapter(adapter);// ����
        rnb.setNavigationBarListener(new RollNavigationBar.NavigationBarListener() {
            /**
             * position ��ѡλ�� view Ϊ������ event �ƶ��¼�
             */
            @Override
            public void onNavigationBarClick(int position, View view,
                    MotionEvent event) {
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:// ����ȥʱ
                    break;
                case MotionEvent.ACTION_MOVE:// �ƶ���
                    break;
                case MotionEvent.ACTION_UP:// ̧��ʱ
                    break;
                }

            }

        });
    }

    /**
     * ��������չ
     * 
     * @author w.song
     * @version 1.0.1
     * @date 2012-4-22
     */
    class MyNavigationBarAdapter extends RollNavigationBarAdapter {
        private List<Map<String, Object>> list;
        private LayoutInflater mInflater;

        public MyNavigationBarAdapter(Activity activity,
                List<Map<String, Object>> list) {
            mInflater = LayoutInflater.from(activity);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        /**
         * ��ȡÿ�����
         * 
         * @param position
         *            �����λ��
         * @param contextView
         *            ���
         * @param parent
         *            �ϲ����
         */
        @Override
        public View getView(int position, View contextView, ViewGroup parent) {
            mInflater.inflate(R.layout.item, (ViewGroup) contextView);
            RollNavigationBar rollNavigationBar = (RollNavigationBar) parent;
            /* ��ȡ��� */
            ImageView imageView = (ImageView) contextView
                    .findViewById(R.id.image_view);
            TextView titleView = (TextView) contextView
                    .findViewById(R.id.title_view);

            /* ��ȡ���� */
            String title = "" + list.get(position).get("title");
            int photo = (Integer) list.get(position).get("photo");
            int photoSelected = (Integer) list.get(position).get(
                    "photoSelected");

            /* ������ò��� */
            // ����ѡ���뱻ѡ��ͼƬ
            if (position == rollNavigationBar.getSelectedChildPosition()) {// ��ѡ��
                imageView.setBackgroundResource(photoSelected);
            } else {// û��ѡ��
                imageView.setBackgroundResource(photo);
            }
            titleView.setText(title);

            return contextView;
        }

    }
}
