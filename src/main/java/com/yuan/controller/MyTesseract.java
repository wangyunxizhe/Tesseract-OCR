package com.yuan.controller;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;
import java.net.URL;

/**
 * Created by wangy on 2018/11/7.
 */
public class MyTesseract {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int f = 1; f <= 14; f++) {
            File imageFile = new File("E:\\工作记录\\素材\\XXX.jpg");
            ITesseract instance = new Tesseract();
            URL url = ClassLoader.getSystemResource("tessdata");
            String path = url.getPath().substring(1);
            instance.setDatapath(path);
            // 默认是英文（识别字母和数字），如果要识别中文(数字 + 中文），需要制定语言包
            instance.setLanguage("jyqrs_word");
            try {
                String result = instance.doOCR(imageFile);
                System.out.println("识别结果：" + result + "  结果长度：" + result.length());
                System.out.println("字符分别是：");
                for (int i = 0; i < result.length(); i++) {
                    System.out.print(result.charAt(i) + "~~~~");
                }
                System.out.println();
            } catch (TesseractException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("耗时：" + (System.currentTimeMillis() - start) / 1000f + "秒");
        }
    }

}
