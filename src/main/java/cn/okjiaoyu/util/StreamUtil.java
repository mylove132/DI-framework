package cn.okjiaoyu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author: liuzhanhui
 * @Decription:常用的数据流
 * @Date: Created in 2018-07-24:17:45
 * Modify date: 2018-07-24:17:45
 */
public final class StreamUtil {

    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);

    public static String getString(InputStream is){
        StringBuffer sb = new StringBuffer();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while ((line = bufferedReader.readLine())!=null){
                sb.append(line);
            }
        } catch (IOException e) {
           logger.error("get String inpustream failure", e);
           throw new RuntimeException(e);
        }finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                logger.error("close bufferedReader failure", e);
                throw new RuntimeException(e);
            }
        }
        return sb.toString();
    }
}
