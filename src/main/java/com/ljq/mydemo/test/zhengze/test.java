package com.ljq.mydemo.test.zhengze;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author gino
 * 2021-12-14
 */
public class test {
    public static void main(String[] args) {
        String dest="[{\"ptext\":[{\"title\":\"ptext2\",\"align\":\"left\",\"draggable\":true,\"prop\":\"ptext\",\"id\":\"aqijgu840880000000\",\"fontWeight\":\"normal\",\"padding\":0,\"wordBreak\":true,\"amount\":6,\"h\":100,\"column\":1,\"letterSpacing\":0,\"marquee\":false,\"url\":\"/news/page?newsno=20211214N5F7PTIJVRY\",\"revision\":1,\"zindex\":2,\"borderRadius\":0,\"background\":\"rgba(255, 255, 255, 1)\",\"w\":200,\"name\":\"文本2\",\"x\":181,\"y\":166,\"fontSize\":12,\"lineHeight\":14,\"ptext\":\"测试新闻的另存为\",\"fontColor\":\"#333333\",\"font\":\"SimSun\"},{\"id\":\"1lgrf03mnr8g0000000\",\"name\":\"文本5\",\"title\":\"ptext5\",\"prop\":\"ptext\",\"zindex\":5,\"w\":264,\"h\":38,\"x\":642,\"y\":159,\"ptext\":\"121\",\"font\":\"SimSun\",\"align\":\"left\",\"fontSize\":12,\"fontColor\":\"#333333\",\"background\":\"rgba(255, 255, 255, 1)\",\"fontWeight\":\"normal\",\"borderRadius\":0,\"padding\":0,\"url\":\"/news/page?newsno=20211126NFDG7UNQCOH\",\"letterSpacing\":0,\"lineHeight\":14,\"amount\":6,\"column\":1,\"marquee\":false,\"draggable\":true,\"wordBreak\":true}],\"pbutton\":[{\"id\":\"2upapjwcx240000000\",\"name\":\"按钮3\",\"title\":\"pbutton3\",\"prop\":\"pbutton\",\"zindex\":3,\"areaData\":[],\"w\":100,\"h\":40,\"x\":460,\"y\":159,\"hidden\":false,\"label\":\"默认按钮\",\"draggable\":true,\"urlType\":2,\"subUnit\":\"\",\"url\":\"/news/page?newsno=20211126NE47I4HYM06\"}],\"bgimages\":[],\"pmedia\":[{\"id\":\"2s6r3hbcwhk00000000\",\"name\":\"多媒体4\",\"title\":\"pmedia4\",\"prop\":\"pmedia\",\"zindex\":4,\"w\":600,\"h\":300,\"x\":187,\"y\":297,\"mtype\":2,\"urls\":[\"http://192.168.1.201:8080/group1/M02/01/10/wKgByGGLl_eABe6nAAOh4bzYcOk753.gif?token=f2a2d03ecda9e68345e1b767fcbde7d4&ts=1639472995\"],\"lurls\":[\"/news/page?newsno=20211108NUHYY9VYSXA\"],\"titles\":[\"山西日报1\"],\"isMultiple\":false,\"imgModel\":\"cover\",\"walkingType\":\"none\",\"interval\":3,\"borderRadius\":0,\"controls\":false,\"autoplay\":true,\"loop\":true,\"draggable\":true,\"pageButton\":false,\"pageButtonText\":\"翻页\",\"pageVertical\":\"center\",\"pageHorizontal\":\"center\",\"pageButPadding\":0}],\"pweather\":[],\"pclock\":[],\"phtml\":[],\"pextend\":[]}]";


        List<String> a=new ArrayList<>();

a.add("a");
        a.add("a");

        List<String> collect = a.stream().distinct().collect(Collectors.toList());

        System.out.println(collect);
        System.out.println(a);

   /*     Pattern pattern = Pattern.compile("^newsno=*\"$");
        Matcher matcher = pattern.matcher(dest);
        if(matcher.find()){
            System.out.println(matcher.group(1));
        }*/


        // 查找的字符串
        String line = "（乙方）:xxx科技股份有限公司\"（乙方）:xxx有限公司（乙方）\":xxx技术股份有限公司\"";
        //正则表达式
        String pattern = "newsno=(.*?)\""; //Java正则表达式以括号分组，第一个括号表示以"（乙方）:"开头，第三个括号表示以" "(空格)结尾，中间括号为目标值，
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 创建 matcher 对象
        Matcher m = r.matcher(dest);
        System.out.println("-----");
        while (m.find()) {
            /*
             自动遍历打印所有结果   group方法打印捕获的组内容，以正则的括号角标从1开始计算，我们这里要第2个括号里的
             值， 所以取 m.group(2)， m.group(0)取整个表达式的值，如果越界取m.group(4),则抛出异常
           */
            System.out.println("Found value: " + m.group(1));
        }
    }
}
