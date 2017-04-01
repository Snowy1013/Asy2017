package com.snowy.sample;

import com.snowy.sample.designpattern.DesignPatternActivity;
import com.snowy.sample.opensource.okhttp.HttpMainActivity;
import com.snowy.sample.opensource.rxjava.RxJavaDemoActivity;
import com.snowy.sample.primary.camera.CameraDemoActivity;
import com.snowy.sample.primary.contacts.ContactsDemoActivity;
import com.snowy.sample.primary.imgandanim.ImageAndAnimationActivity;
import com.snowy.sample.primary.print.PrintDemoActivity;
import com.snowy.sample.primary.share.ShareDemoActivity;
import com.snowy.sample.primary.systembartint.SystemBarTintMainActivity;
import com.snowy.sample.uicode.ClockActivity;
import com.snowy.sample.uicode.DrawViewActivity;
import com.snowy.sample.uicode.ExpandableListViewActivity;
import com.snowy.sample.uicode.FrameLayoutActivity;
import com.snowy.sample.uicode.GridViewActivity;
import com.snowy.sample.uicode.GridViewMainActivity;
import com.snowy.sample.uicode.ImageViewActivity;
import com.snowy.sample.uicode.StreamLayoutActivity;
import com.snowy.sample.uicode.ViewPagerMainActivity;
import com.snowy.sample.uicode.fragment.FragmentDemoActivity;

import org.ayo.sample.menu.Leaf;
import org.ayo.sample.menu.MainPagerActivity;
import org.ayo.sample.menu.Menu;
import org.ayo.sample.menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MainPagerActivity {

    @Override
    public List<Menu> getMenus() {
        List<Menu> menus = new ArrayList<>();

        Menu menu1 = new Menu("Android", R.drawable.typical_ic_profile_normal, R.drawable.typical_ic_profile_pressed);
        menus.add(menu1);
        {
            MenuItem menuItem1 = new MenuItem("控件", R.drawable.typical_ic_weixin_normal, R.drawable.typical_ic_weixin_pressed);
            menu1.addMenuItem(menuItem1);
            {
                menuItem1.addLeaf(new Leaf("跟随手指的小球", "", DrawViewActivity.class));
                menuItem1.addLeaf(new Leaf("霓虹灯效果", "", FrameLayoutActivity.class));
                menuItem1.addLeaf(new Leaf("时钟", "", ClockActivity.class));
                menuItem1.addLeaf(new Leaf("图片浏览器", "", ImageViewActivity.class));
                menuItem1.addLeaf(new Leaf("瀑布流", "", StreamLayoutActivity.class));
                menuItem1.addLeaf(new Leaf("带预览的图片浏览器", "", GridViewActivity.class));
                menuItem1.addLeaf(new Leaf("可展开的列表组件", "", ExpandableListViewActivity.class));
                menuItem1.addLeaf(new Leaf("带分隔线的GridView", "", GridViewMainActivity.class));
                menuItem1.addLeaf(new Leaf("ViewPager", "", ViewPagerMainActivity.class));
                menuItem1.addLeaf(new Leaf("Fragment", "", FragmentDemoActivity.class));
            }

            MenuItem menuItem2 = new MenuItem("基础", R.drawable.typical_ic_weixin_normal, R.drawable.typical_ic_weixin_pressed);
            menu1.addMenuItem(menuItem2);
            {
                menuItem2.addLeaf(new Leaf("通讯录", "", ContactsDemoActivity.class));
                menuItem2.addLeaf(new Leaf("Android浸入式 ", "", SystemBarTintMainActivity.class));
                menuItem2.addLeaf(new Leaf("分享", "", ShareDemoActivity.class));
                menuItem2.addLeaf(new Leaf("相机", "", CameraDemoActivity.class));
                menuItem2.addLeaf(new Leaf("打印", "", PrintDemoActivity.class));
                menuItem2.addLeaf(new Leaf("图像与动画", "", ImageAndAnimationActivity.class));
            }

            MenuItem menuItem3 = new MenuItem("开源组件", R.drawable.typical_ic_weixin_normal, R.drawable.typical_ic_weixin_pressed);
            menu1.addMenuItem(menuItem3);
            {
                menuItem3.addLeaf(new Leaf("OkHttp", "", HttpMainActivity.class));
                menuItem3.addLeaf(new Leaf("RxJava", "", RxJavaDemoActivity.class));
            }
        }

        Menu menu2 = new Menu("Java", R.drawable.typical_ic_find_normal, R.drawable.typical_ic_find_pressed);
        menus.add(menu2);
        {
            MenuItem menuItem1 = new MenuItem("Master", R.drawable.typical_ic_find_normal, R.drawable.typical_ic_find_pressed);
            menu2.addMenuItem(menuItem1);
            {
                menuItem1.addLeaf(new Leaf("设计模式", "", DesignPatternActivity.class));
//                menuItem1.addLeaf(new Leaf("多线程和RxJava", "", AsyncDemoActivity.class));
//                menuItem1.addLeaf(new Leaf("数据库", "", XUtilsDBDemoActivity.class));
            }
        }

        return menus;
    }
}
