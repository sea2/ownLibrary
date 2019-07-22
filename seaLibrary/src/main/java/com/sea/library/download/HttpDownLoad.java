package com.sea.library.download;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.sea.library.util.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpDownLoad {
    public static final String TAG = "HttpDownLoad";
    private static final int kMaxRecvByte = 4096;

    public Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 根据URL下载文件，前提是这个文件当中的内容是文本，函数的返回值就是文件当中的内容
     * 1.创建一个URL对象
     * 2.通过URL对象，创建一个HttpURLConnection对象
     * 3.得到InputStram
     * 4.从InputStream当中读取数据
     *
     * @param urlStr
     * @return
     */
    public static String download(String urlStr, boolean hasLine) {
        Log.d(TAG, "start");
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader buffer = null;
        try {
            // 创建一个URL对象
            URL Fileurl = new URL(urlStr);
            Log.d(TAG, "new URL");
            // 创建一个Http连接
            HttpURLConnection urlConn = (HttpURLConnection) Fileurl.openConnection();
            // 使用IO流读取数据
            Log.d(TAG, "urlConn" + urlConn.getContentLength() + "code" + urlConn.getResponseCode());
            buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
                if (hasLine)
                    sb.append("\r\n");
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception");
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 该函数返回整形 -1：代表下载文件出错 0：代表下载文件成功 1：代表文件已经存在
     */
    public static int downFile(String url_, String filePath_, String fileName_) {
        if (FileUtils.fileIsExists(filePath_ + fileName_)) {
            return 1;
        }

        Log.d(TAG, "down to file, URL:" + url_ + ", filepath:" + filePath_ + "， fileName_：" + fileName_);
        OutputStream outStream = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(url_);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            inputStream = urlConn.getInputStream();

            Log.d(TAG, "url:" + url_);

            int totalLength = urlConn.getContentLength();
            Log.d(TAG, "down to file, totalLength:" + totalLength);
            FileUtils.creatSDDir(filePath_);
            File newFile = FileUtils.creatSDFile(filePath_ + fileName_);
            outStream = new FileOutputStream(newFile);

            int readByte = 0;
            int totalReadByte = 0;
            byte[] recvBuff = new byte[kMaxRecvByte];
            while ((readByte = inputStream.read(recvBuff)) != -1) {
                outStream.write(recvBuff, 0, readByte);
                totalReadByte += readByte;
            }
            outStream.flush();

            if (totalReadByte < totalLength) {
                return -1;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return -1;

        } finally {

            try {
                if (outStream != null) {
                    outStream.close();
                }

                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return 0;
    }

    /**
     * 根据URL得到输入流
     *
     * @param urlStr
     * @return
     * @throws MalformedURLException
     * @throws IOException
     */
    public static InputStream getInputStreamFromUrl(String urlStr)
            throws MalformedURLException, IOException {
        URL url = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        InputStream inputStream = urlConn.getInputStream();
        return inputStream;
    }
}
