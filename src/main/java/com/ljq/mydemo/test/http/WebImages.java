package com.ljq.mydemo.test.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author gino
 * 2022-01-11
 */
public class WebImages{


    public static void main(String[] args) throws AWTException, URISyntaxException, IOException {

        String urlString="http://192.168.1.70:21118/rest-admin/v1/ops/terminal/terminalInfo?corpCode=BJ";
        //String result = HttpUtil.get("http://192.168.1.70:21118/rest-admin/v1/ops/terminal/terminalInfo?corpCode=BJ");

        HttpResponse execute = HttpRequest.get(urlString).timeout(-1).execute();
        String result = execute.body();
        String contentEncoding = execute.header("Content-Encoding");

        if("gzip".equals(contentEncoding)){
            System.out.println("---------------------");
            System.out.println("----------"+contentEncoding+"----------");
        }
        System.out.println(result);
        JSONObject jsonObject = JSON.parseObject(result);

        System.out.println(jsonObject);
    }

    public void getImage() throws AWTException, URISyntaxException, IOException {
        //此方法仅用于JDK1.6及以上版本
        Desktop.getDesktop().browse(new URI("http://www.wanglaosan.xyz"));
        Robot robot = new Robot();
        robot.delay(1000);
        Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        int width = (int)d.getWidth();
        int height = (int)d.getHeight();
        //最大化浏览器
        robot.keyPress(KeyEvent.VK_F11);
        robot.delay(2000);
        Image image = robot.createScreenCapture(new Rectangle(0,0,width,height));
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics q = bi.createGraphics();
        q.drawImage(image,0,0,width,height,null);
        ImageIO.write(bi,"jpg",new java.io.File("C:\\Users\\Administrator\\Desktop\\1\\jietu.png"));
    }
}
