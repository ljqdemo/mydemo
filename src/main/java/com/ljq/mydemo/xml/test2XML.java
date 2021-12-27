/*
package com.ljq.mydemo.xml;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.List;
import java.util.Objects;

*/
/**
 * @author gino
 * 2021-12-20
 *//*

public class test2XML {

    private static  final String  path="D:\\11\\weather_bj.xml";
    private static  final String  outPath="D:\\11\\1.xml";
    public static void main(String[] args) throws Exception {

       JSONArray array=JSON.parseArray("[{\"zindex\":1,\"avl\":3,\"w\":1080,\"h\":400,\"x\":\"0\",\"name\":\"台标区\",\"y\":\"0\",\"index\":\"0\",\"type\":\"Logo\",\"title\":\"Logo0\"},{\"zindex\":2,\"avl\":2,\"w\":1080,\"h\":390,\"x\":0,\"name\":\"告警区\",\"y\":411,\"index\":\"0\",\"type\":\"Warning\",\"title\":\"Warning0\"},{\"zindex\":3,\"w\":1080,\"h\":386,\"x\":0,\"name\":\"图文区\",\"y\":813,\"index\":\"1\",\"type\":\"ImageText\",\"title\":\"ImageText1\",\"media\":{\"id\":1234,\"name\":\"素材1234\",\"url\":\"素材地址或内容\",\"type\":2,\"checksum\":\"素材校验码\",\"extra\":{\"backgroundColor\":\"0x00ffffff\",\"color\":\"0xfff6f6f6\",\"scrollSpeed\":1,\"scrollDirection\":2,\"fontSize\":22}}},{\"zindex\":4,\"w\":1080,\"h\":300,\"x\":0,\"name\":\"天气区\",\"y\":1618,\"index\":\"0\",\"type\":\"Weather\",\"title\":\"Weather0\"},{\"zindex\":5,\"w\":1080,\"h\":397,\"x\":0,\"name\":\"时钟区\",\"y\":1214,\"index\":\"0\",\"type\":\"Clock\",\"title\":\"Clock0\"}]");

        //1.创建SAXBuilder对象
        SAXBuilder saxBuilder = new SAXBuilder();
        //2.创建输入流
        InputStream is = new FileInputStream(new File(path));
        //3.将输入流加载到build中
        Document document = saxBuilder.build(is);
        //4.获取根节点
        Element rootElement = document.getRootElement();

        //解析时间区以及台标
        List<Element>  children = rootElement.getChild("ImageList").getChild("ImageItem").getChild("DrawList").getChildren("DrawItem");
        for (Element child : children) {
            //解析DrawList 下所有的DrawItem
            Attribute attribute = child.getAttribute("model");
            String value = attribute.getValue();
            //如果是台标
            if (value.equals("tb")) {
                //解析台标的位置
                JSONObject tbObj = getObject("Logo", array);
                if (Objects.nonNull(tbObj)) {
                    String x = tbObj.getString("x");
                    String y = tbObj.getString("y");
                    String w = tbObj.getString("w");
                    String h = tbObj.getString("h");
                    Element element=child.getChild("Area");
                    //设置坐标
                    setLocation(element,x,y,w,h);
                    continue;
                }
            }
            //时间区解析
            if (value.equals("Date-h")) {
                //解析台标的位置
                JSONObject tbObj = getObject("Clock", array);
                if (Objects.nonNull(tbObj)) {
                    String x = tbObj.getString("x");
                    String y = tbObj.getString("y");
                    Long lx=Long.parseLong(x);
                    Long ly=Long.parseLong(y);
                    //小时区域相对边框是 x+9 y+2
                    lx+=9; ly+=2;
                    Element element=child.getChild("Area");
                    //设置坐标
                    setLocation(element,lx.toString(),ly.toString(),null,null);
                    continue;
                }
            }
            if (value.equals("Date-str1")) {
                //解析台标的位置
                JSONObject tbObj = getObject("Clock", array);
                if (Objects.nonNull(tbObj)) {
                    String x = tbObj.getString("x");
                    String y = tbObj.getString("y");
                    Long lx=Long.parseLong(x);
                    Long ly=Long.parseLong(y);
                    //小时区域相对边框是 x+59 y+2
                    lx+=59; ly+=2;
                    Element element=child.getChild("Area");
                    //设置坐标
                    setLocation(element,lx.toString(),ly.toString(),null,null);
                    continue;
                }
            }
            if (value.equals("Date-s")) {
                //解析台标的位置
                JSONObject tbObj = getObject("Clock", array);
                if (Objects.nonNull(tbObj)) {
                    String x = tbObj.getString("x");
                    String y = tbObj.getString("y");
                    Long lx=Long.parseLong(x);
                    Long ly=Long.parseLong(y);
                    //小时区域相对边框是577-503 x+74 y+2
                    lx+=74; ly+=2;
                    Element element=child.getChild("Area");
                    //设置坐标
                    setLocation(element,lx.toString(),ly.toString(),null,null);
                    continue;
                }
            }
            if (value.equals("Date-str2")) {
                //解析台标的位置
                JSONObject tbObj = getObject("Clock", array);
                if (Objects.nonNull(tbObj)) {
                    String x = tbObj.getString("x");
                    String y = tbObj.getString("y");
                    Long lx=Long.parseLong(x);
                    Long ly=Long.parseLong(y);
                    //小时区域相对边框是627-503 x+124 y+2
                    lx+=124; ly+=2;
                    Element element=child.getChild("Area");
                    //设置坐标
                    setLocation(element,lx.toString(),ly.toString(),null,null);
                    continue;
                }
            }

            if (value.equals("Date-m")) {
                //解析台标的位置
                JSONObject tbObj = getObject("Clock", array);
                if (Objects.nonNull(tbObj)) {
                    String x = tbObj.getString("x");
                    String y = tbObj.getString("y");
                    Long lx=Long.parseLong(x);
                    Long ly=Long.parseLong(y);
                    //小时区域相对边框是642-503 x+124 y+2
                    lx+=139; ly+=2;
                    Element element=child.getChild("Area");
                    //设置坐标
                    setLocation(element,lx.toString(),ly.toString(),null,null);
                    continue;
                }
            }

            if (value.equals("Date-d")) {
                //解析台标的位置
                JSONObject tbObj = getObject("Clock", array);
                if (Objects.nonNull(tbObj)) {
                    String x = tbObj.getString("x");
                    String y = tbObj.getString("y");
                    Long ly=Long.parseLong(y);
                    //小时区域相对边框是96-38     503-503    x+0 y+58
                     ly+=58;
                    Element element=child.getChild("Area");
                    //设置坐标
                    setLocation(element,x,ly.toString(),null,null);
                    continue;
                }
            }
            if (value.equals("Date-bg")) {
                //解析台标的位置
                JSONObject tbObj = getObject("Clock", array);
                if (Objects.nonNull(tbObj)) {
                    String x = tbObj.getString("x");
                    String y = tbObj.getString("y");
                    Element element=child.getChild("Area");
                    //设置坐标
                    setLocation(element,x,y,null,null);
                    continue;
                }
            }
            //字幕区设置


        }
        //解析天气区域
        List<Element>  weathers = rootElement.getChild("ImageList").getChild("ImageItem").getChild("AnimateRule").getChildren("AnimateSlot");
        for (Element weather : weathers) {
            List<Element> drawItem = weather.getChildren("DrawItem");
            AnalysisWeathers(drawItem,array);
        }
        //创建图文区


        //生成并输出xml
        CreateFile(document);
    }

    */
/**
     * 创建图文区的xml标签
     * @param array
     * @param rootElement
     *//*

    public static void creatImageAndText(JSONArray array, Element rootElement){
        Element child = rootElement.getChild("ImageList").getChild("AnimateRule");
        for (int i = 0; i <array.size() ; i++) {
            JSONObject object = array.getJSONObject(i);
            JSONObject media = object.getJSONObject("media");
            if (Objects.isNull(media)) {
                continue;
            }
            Element animateSlot = new Element("AnimateSlot");
            //TODO 设置切换时间
            //设置DrawItem
            Element DrawItem = new Element("DrawItem");
            animateSlot.addContent(DrawItem);
            DrawItem.setAttribute("type","image");
            DrawItem.setAttribute("model","imageAndText");
            //设置DrawItem Image
            Element Image = new Element("Image");
            DrawItem.addContent(Image);
            Image.setAttribute("file",media.getString("name"));
            //设置Area
            Element Area = new Element("Area");
            DrawItem.addContent(Area);
            Area.setAttribute("x",media.getLong("x").toString());
            Area.setAttribute("y",media.getLong("y").toString());
            Area.setAttribute("width",media.getLong("w").toString());
            Area.setAttribute("height",media.getLong("h").toString());
            child.setContent(animateSlot);
        }
    }


    */
/**
     * 查找出图文区的数组
     * 并将图片保存至本地
     * @param array
     * @return
     *//*

    public static JSONArray getImageAndTextArray(JSONArray array,String basePath){
        JSONArray resutl=new JSONArray();
        for (int i = 0; i <array.size() ; i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            String type = jsonObject.getString("type");
            if (!StringUtils.isEmpty(type) && type.equals("ImageText")) {
                JSONObject media = jsonObject.getJSONObject("media");
                if (null == media || media.isEmpty()) {
                    continue;
                }

            }

        }
     return  resutl;
    }




    */
/**
     * 解析天气区
     * @param weathers
     *//*

    public static void AnalysisWeathers(List<Element>  weathers,JSONArray array){
        for (Element child : weathers) {
            Attribute attribute = child.getAttribute("model");
            String value = attribute.getValue();
            setWeatherAndComputeLocation(value,child,array );
        }
    }

    */
/***
     * 设置天气以及计算并设置位置
     * @param value
     * @param child
     * @param array
     *//*

    public static void setWeatherAndComputeLocation(String value,Element child,JSONArray array){
        //天气背景框
        if (value.equals("weather-bg")) {
            //解析台标的位置
            JSONObject tbObj = getObject("Weather", array);
            if (Objects.nonNull(tbObj)) {
                String x = tbObj.getString("x");
                String y = tbObj.getString("y");
                Element element=child.getChild("Area");
                //设置坐标
                setLocation(element,x,y,null,null);
            }
        }
        //天气图标
        if (value.equals("weather-icon")) {
            //解析台标的位置
            JSONObject tbObj = getObject("Weather", array);
            if (Objects.nonNull(tbObj)) {
                String x = tbObj.getString("x");
                String y = tbObj.getString("y");
                Long ly=Long.parseLong(y);
                Long lx=Long.parseLong(x);
                lx+=10; ly+=12;
                Element element=child.getChild("Area");
                //设置坐标
                setLocation(element,lx.toString(),ly.toString(),null,null);
            }
        }

        //预警图标
        if (value.equals("warning-1")) {
            //解析台标的位置
            JSONObject tbObj = getObject("Warning", array);
            if (Objects.nonNull(tbObj)) {
                String x = tbObj.getString("x");
                String y = tbObj.getString("y");
                Element element=child.getChild("Area");
                //设置坐标
                setLocation(element,x,y,null,null);
            }
        }
        if (value.equals("warning-2")) {
            //解析台标的位置
            JSONObject tbObj = getObject("Warning", array);
            if (Objects.nonNull(tbObj)) {
                String x = tbObj.getString("x");
                String y = tbObj.getString("y");
                Long lx=Long.parseLong(x);
                lx+=112;
                Element element=child.getChild("Area");
                //设置坐标
                setLocation(element,lx.toString(),y,null,null);
            }
        }

        if (value.equals("warning-3")) {
            //解析台标的位置
            JSONObject tbObj = getObject("Warning", array);
            if (Objects.nonNull(tbObj)) {
                String x = tbObj.getString("x");
                String y = tbObj.getString("y");
                Long ly=Long.parseLong(y);
                Long lx=Long.parseLong(x);
                lx+=224;
                Element element=child.getChild("Area");
                //设置坐标
                setLocation(element,lx.toString(),y,null,null);
            }
        }
    }


    */
/**
     * 创建并生成新的xml文件
     * @param document
     * @throws IOException
     *//*

    public static void CreateFile(Document document) throws IOException {
        Format format = Format.getCompactFormat();
        // 设置换行Tab或空格
        format.setIndent("	");
        format.setEncoding("UTF-8");

        // 4、创建XMLOutputter的对象
        XMLOutputter outputer = new XMLOutputter(format);
        // 5、利用outputer将document转换成xml文档
        File file = new File(outPath);
        outputer.output(document, new FileOutputStream(file));
        System.out.println("生成xml成功！");
    }


    */
/**
     * 设置宽高以及x,y坐标
     * @param e
     * @param x
     * @param y
     * @param w
     * @param h
     * @return
     *//*

    public static Element setLocation(Element e,String x, String y,String w, String h ){
        e.setAttribute("x",x);
        e.setAttribute("y",y);
        if (!Objects.isNull(w)) {
            e.setAttribute("width",w);
        }
        if (!Objects.isNull(w)) {
            e.setAttribute("height",h);
        }

        return e;
    }

    */
/**
     * 从json数组中查询需要的对象
     * @param type
     * @param array
     * @return
     *//*

    public static JSONObject getObject(String type, JSONArray array) {
        JSONObject object = null;
        for (int i = 0; i < array.size(); i++) {
            JSONObject dest = array.getJSONObject(i);
            if (dest.get("type").equals(type)) {
                return dest;
            }
        }
        return object;
    }

}
*/
