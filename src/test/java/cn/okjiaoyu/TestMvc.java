package cn.okjiaoyu;

import cn.okjiaoyu.annotation.Inject;
import cn.okjiaoyu.helper.HelperLoader;
import cn.okjiaoyu.service.CustomerService;
import org.junit.Test;

/**
 * @Author: liuzhanhui
 * @Decription:
 * @Date: Created in 2018-07-24:20:08
 * Modify date: 2018-07-24:20:08
 */
public class TestMvc {

    @Inject
    private CustomerService customerService;

    @Test
    public void Test(){
        HelperLoader.init();
    }
}
