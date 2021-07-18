package com.hins.sp01hello.base64Image;

import com.alibaba.fastjson.JSONObject;
import com.hins.sp01hello.config.SSLSocketClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.*;

/**
 * @author qixuan.chen
 * @date 2020-08-15 16:29
 */
@Slf4j
@Component
public class HttpTool {

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 发送请求 (OKHttp) (cookie登录)
     * @param path
     * @return
     */
    public String sentOKHttpHtml(String path,String cookie){
        String html = null;
        int num = 1;
        while(html == null){
            try {
                log.info("第"+num+"次 请求 v2："+path);
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())//配置
                        .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置
                        .build();
                Request request = new Request.Builder()
                        .url(path)
                        .method("GET", null)
                        .addHeader("cookie", cookie)
                        .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
                        .build();

                Response response = client.newCall(request).execute();
                //System.out.println(response.body().string());
                html = response.body().string();
            } catch (Exception e) {
                html = null;
                System.gc(); // 回收资源
                log.info("第"+num+"次 http请求失败!");
                //e.printStackTrace();
            }
            num++;
        }
        return html;
    }

    /**
     * 发送请求
     * @param path
     * @return
     */
    public String sentHttpHtml(String path){
        String html = null;
        int num = 1;
        while(html == null){
            try {
                log.info("第"+num+"次 请求 v2："+path);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.COOKIE,"PHPSESSID=626ngia2k71nh38d89honle553; _ga=GA1.2.1431723662.1592312797; nav_switch=booklist; _gid=GA1.2.263080656.1592638029; _pk_ses.1.aca7=1; _gat_gtag_UA_164071320_1=1; _pk_id.1.aca7=6d81dde7a1fec2a9.1592312798.3.1592643451.1592641072.; PHPSESSID=mbskhq5tv846j475spjqecmjd5");
                headers.add("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");

                MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();      //map里面是请求体的内容
                HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);


                ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.GET, requestEntity, String.class);
                html = response.getBody();
            } catch (RestClientException e) {
                html = null;
                System.gc(); // 回收资源
                log.info("第"+num+"次 http请求失败!");
                //e.printStackTrace();
            }
            num++;
        }
        return html;
    }

    /**
     * 发送请求
     * @param path
     * @return
     */
    public JSONObject sentHttp(String path, String cookie){
        JSONObject json = null;
        int num = 1;
        while(json == null){
            try {
                log.info("第"+num+"次 请求 v2: "+path);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cookie",cookie);
                headers.add("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1");


                MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();      //map里面是请求体的内容
                HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(map, headers);

//                String result = restTemplate.getForEntity(path, String.class).getBody();
                ResponseEntity<String> response = restTemplate.exchange(path, HttpMethod.GET, requestEntity, String.class);
                String result = response.getBody();
                json = JSONObject.parseObject(result);


            } catch (RestClientException e) {
                json = null;
                log.info("第"+num+"次 http请求失败!");
                //e.printStackTrace();
            }
            num++;
        }
        return json;
    }

    /**
     * 发送请求
     * @param path
     * @return
     */
    public JSONObject sentOkHttp(String path, String cookie){
        JSONObject json = null;
        int num = 1;
        while(json == null){
            try {
                log.info("第"+num+"次 请求 v2："+path);
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())//配置
                        .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置
                        .build();
                Request request = new Request.Builder()
                        .url(path)
                        .method("GET", null)
                        .addHeader("cookie", cookie)
                        .addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
                        .build();
                Response response = client.newCall(request).execute();
                String result = response.body().string();
                json = JSONObject.parseObject(result);


            } catch (Exception e) {
                json = null;
                log.info("第"+num+"次 http请求失败!");
                //e.printStackTrace();
            }
            num++;
        }
        return json;
    }

    /**
     * 发送请求
     * @param path
     * @return
     */
    public JSONObject sentHttp(String path){
        JSONObject json = null;
        int num = 1;
        while(json == null){
            try {
                log.info("第"+num+"次 请求 v2: "+path);
//                RestTemplate restTemplate = new RestTemplate();
                String result = restTemplate.getForEntity(path, String.class).getBody();
                json = JSONObject.parseObject(result);
//                RestTemplate restTemplate = new RestTemplate();
//                HttpHeaders headers = new HttpHeaders();
//                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//                headers.setContentType(type);
//                headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//                HttpEntity<String> formEntity = new HttpEntity<String>(null, headers);
//                String result = restTemplate.postForObject(path, formEntity, String.class);
//                json = JSON.parseObject(result);

            } catch (RestClientException e) {
                json = null;
                log.info("第"+num+"次 http请求失败!");
                //e.printStackTrace();
            }
            num++;
        }
        return json;
    }


    /**
     * 链接url下载图片
     * @param url
     * @param filePath
     */
    public String downloadPicture(String url,String filePath) {
        String mark = "1111";
        int m = 1;
        while (!"success".equals(mark)){
            try {
                log.info("第"+m+"次 下载 v2");
                log.info(url);
                mark = downloadImage(url, filePath);
            } catch (Exception e) {
                mark = "1111";
                System.gc(); // 回收资源
                log.info("第"+m+"次 下载失败!");
            }
        }


        if("success".equals(mark)){
            log.info("下载成功: "+filePath);
            return "success";
        }else{
            log.info("下载失败: "+filePath);
            return "error";
        }

    }

    /**
     * 链接url下载图片
     * @param url
     * @param filePath
     */
    public String downloadPictureByDel(String url,String filePath) {
        String mark = "1111";
        for (int m=1; m < 8 ; m++){
            try {
                log.info("第"+m+"次 下载 v2");
                log.info(url);
                mark = downloadImage(url, filePath);
                if("success".equals(mark)){
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.gc(); // 回收资源
                log.info("第"+m+"次 下载失败!");
            }
        }


        if("success".equals(mark)){
            log.info("下载成功: "+filePath);
            return "success";
        }else{
            log.info("下载失败: "+filePath);
            return "error";
        }

    }

    private String downloadImage(String url, String filePath){
        InputStream inputStream = null;
        OutputStream outputStream = null;

        //RestTemplate restTemplate = new RestTemplate();
        try {
            //Object result = restTemplate.getForObject(url, Object.class);
            HttpHeaders headers = new HttpHeaders();
            headers.add("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_1 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/10.0 Mobile/14E304 Safari/602.1");
            ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);
            byte[] result = response.getBody();
            inputStream = new ByteArrayInputStream(result);

            outputStream = new FileOutputStream(new File(filePath));

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            String mark = "error";
            try {
                mark = downloadImage2(url,filePath);
            } catch (IOException e1) {
                try {
                    mark = downloadImage2(url,filePath);
                } catch (IOException e2) {
                    e2.printStackTrace();
                    return "error";
                }

            }
            return mark;
        } finally {
            try {
                if(inputStream != null) inputStream.close();
                if(outputStream != null) outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("数据流关闭异常！");
            }
        }

        return "success";
    }

    /**
     * 处理特殊路径问题
     * @param url
     * @param filePath
     * @return
     * @throws IOException
     */
    private String downloadImage2(String url, String filePath) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();

            byte[] result = response.body().bytes();
            inputStream = new ByteArrayInputStream(result);

            outputStream = new FileOutputStream(new File(filePath));

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }finally {
            try {
                if(inputStream != null) inputStream.close();
                if(outputStream != null) outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("数据流关闭异常！");
            }
        }

        return "success";
    }




    /**
     * (破解防盗链)
     * 链接url下载图片
     * @param url
     * @param filePath
     */
    public String downloadPictureByDomain(String url,String filePath,String domain) {
        String mark = "1111";
        int m = 1;
        while (!"success".equals(mark)){
            try {
                log.info("第"+m+"次 下载 v2");
                log.info(url);
                mark = downloadImageByDomain(url, filePath,domain);
            } catch (Exception e) {
                mark = "1111";
                System.gc(); // 回收资源
                log.info("第"+m+"次 下载失败!");
            }
        }

        if("success".equals(mark)){
            log.info("下载成功: "+filePath);
            return "success";
        }else{
            log.info("下载失败: "+filePath);
            return "error";
        }

    }

    /**
     * (破解防盗链)
     * @param url
     * @param filePath
     * @param domain
     * @return
     */
    private String downloadImageByDomain(String url, String filePath,String domain){
        InputStream inputStream = null;
        OutputStream outputStream = null;

        String refererDomain = domain + "/";
        //RestTemplate restTemplate = new RestTemplate();
        try {
            //Object result = restTemplate.getForObject(url, Object.class);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Referer",refererDomain);
            headers.add("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_1 like Mac OS X) AppleWebKit/603.1.30 (KHTML, like Gecko) Version/10.0 Mobile/14E304 Safari/602.1");
            ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);
            byte[] result = response.getBody();
            inputStream = new ByteArrayInputStream(result);

            outputStream = new FileOutputStream(new File(filePath));

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            String mark = "error";
            try {
                mark = downloadImage2ByDomain(url,filePath,domain);
            } catch (IOException e1) {
                try {
                    mark = downloadImage2ByDomain(url,filePath,domain);
                } catch (IOException e2) {
                    e2.printStackTrace();
                    return "error";
                }

            }
            return mark;
        } finally {
            try {
                if(inputStream != null) inputStream.close();
                if(outputStream != null) outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("数据流关闭异常！");
            }
        }

        return "success";
    }


    /**
     * 处理特殊路径问题 (破解防盗链)
     * @param url
     * @param filePath
     * @return
     * @throws IOException
     */
    private String downloadImage2ByDomain(String url, String filePath,String domain) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        String refererDomain = domain + "/";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Referer",refererDomain)
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();

            byte[] result = response.body().bytes();
            inputStream = new ByteArrayInputStream(result);

            outputStream = new FileOutputStream(new File(filePath));

            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }finally {
            try {
                if(inputStream != null) inputStream.close();
                if(outputStream != null) outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("数据流关闭异常！");
            }
        }

        return "success";
    }



    // 判断文件是否存在
    public void judeFileExists(File file) {

        if (file.exists()) {
            System.out.println("file exists");
        } else {
            System.out.println("file not exists, create it ...");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // 判断文件夹是否存在
    public void judeDirExists(File file) {

        if(!file.getParentFile().exists()){
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                System.out.println("创建成功!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
