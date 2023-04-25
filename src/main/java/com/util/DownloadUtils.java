package com.util;
 
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.mozilla.universalchardet.UniversalDetector;
 
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
public class DownloadUtils {
    private static final String ua = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36";
    private static int default_timeout = 500*1000;
    private static PoolingHttpClientConnectionManager cm = null;
    private static SSLConnectionSocketFactory sslsf1;
    private static Map<String, String> defaultHeader = new HashMap<>();
    static {
        sslsf1 = formSslsf1();
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf1)
                .register("http", new PlainConnectionSocketFactory())
                .build();
 
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(3000);
        cm.setDefaultMaxPerRoute(2000);
 
 
        //defaultHeader.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        //defaultHeader.put("Accept-Encoding", "gzip, deflate");
        //defaultHeader.put("accept-language", "zh-CN,zh;q=0.9,en;q=0.8");
        //defaultHeader.put("cache-control", "max-age=0");
        //defaultHeader.put("upgrade-insecure-requests", "1");
        defaultHeader.put("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
 
    }
 
    /**
     * //unable to find valid certification path to requested target 异常解决
     *
     * @return
     */
    private static SSLConnectionSocketFactory formSslsf1() {
        SSLConnectionSocketFactory sslConnectionSocketFactory = null;
        SSLContext sslContext = null;
        try {
            sslContext = SSLContexts.custom()
                    .loadTrustMaterial(null, new org.apache.http.ssl.TrustStrategy() {
                        @Override
                        public boolean isTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {
                            return true;
                        }
                    })
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                sslContext,
                NoopHostnameVerifier.INSTANCE);
        return sslConnectionSocketFactory;
 
    }
 
    /**
     * 构造一个单例的httpclient
     */
    static class Sington {
        private static CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setSocketTimeout(default_timeout)
                        .setConnectionRequestTimeout(default_timeout)
                        .setConnectTimeout(default_timeout).build()).build();
    }
 
 
    /**
     * get参数汇总
     *
     * @param url
     * @param charset
     * @param headers
     * @param ip
     * @param port
     * @param timeout
     * @return
     */
    public static String totalGet(String url, String charset, Map<String, String> headers, String ip, Integer port, Integer timeout) {
        String result = null;
        HttpGet httpGet = new HttpGet();
        CloseableHttpClient httpClient = Sington.httpClient;
        initconfig(httpGet, timeout, ip, port);
        initHeader(httpGet, headers);
        CloseableHttpResponse response = null;
        try {
//            response = httpClient.execute(httpGet);
            httpGet.setURI(new URI(url));
            HttpClientContext context = HttpClientContext.create();
            response = httpClient.execute(httpGet,context);
 
            //获取跳转url
            List<URI> redirectURIs = context.getRedirectLocations();
            String redirectUrl = null;
            if (redirectURIs != null && !redirectURIs.isEmpty()) {
                URI finalURI = redirectURIs.get(redirectURIs.size() - 1);
                redirectUrl = finalURI.toString();
               // System.out.println("finalURI=" + redirectUrl);
            }
 
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                result = getResponseContent(charset, response);
            }
        } catch (Exception e) {
          // e.printStackTrace();
        } finally {
            httpGet.releaseConnection();
            closeResponse(response);
        }
        return result;
    }
 
    /**
     * post参数汇总
     *
     * @param url
     * @param charset
     * @param headers
     * @param dataStr
     * @param ip
     * @param port
     * @param timeout
     * @return
     */
    public static String totalPost(String url, String charset, Map<String, String> headers, String dataStr, String ip, Integer port, Integer timeout) {
        String result = null;
        if (charset == null) {
            charset = "utf-8";
        }
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = Sington.httpClient;
        initconfig(httpPost, timeout, ip, port);
        initHeader(httpPost, headers);
        initPostEntity(dataStr, httpPost, charset);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("statusCode====="+statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), charset);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
            closeResponse(response);
        }
        return result;
    }
 
    /**
     * post参数汇总
     *
     * @param url
     * @param charset
     * @param headers
     * @param datas
     * @param ip
     * @param port
     * @param timeout
     * @return
     */
    public static String totalPost(String url, String charset, Map<String, String> headers, Map<String, Object> datas, String ip, Integer port, Integer timeout) {
        System.out.println("start post url====>>>"+url);
        long t1 = System.currentTimeMillis();
        String result = null;
        if (charset == null) {
            charset = "utf-8";
        }
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpClient httpClient = Sington.httpClient;
        initconfig(httpPost, timeout, ip, port);
        initHeader(httpPost, headers);
        initPostEntity(datas, httpPost,charset);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("statusCode====="+statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                result = EntityUtils.toString(response.getEntity(), charset);
            }
            long t2 = System.currentTimeMillis();
            System.out.println("request cost=====>>>>>"+(t2-t1)+" ms");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
            closeResponse(response);
        }
        return result;
    }
 
 
    public static boolean down2File(String url, String ip, Integer port, final String destPath, boolean ddxc) {
        // 创建默认的httpClient实例
        CloseableHttpClient httpClient = Sington.httpClient;
        CloseableHttpResponse httpResponse = null;
        RequestConfig requestConfig = getRequestConfig(1000 * 30, ip, port);
        RandomAccessFile raf = null;
        InputStream in = null;
        File tmpFile = null;
        File dFile = null;
 
        // 用get方法发送http请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        httpGet.addHeader("User-Agent", ua);
        dFile = new File(destPath);
        if (dFile.exists()) {
            System.out.println("file already exist skip ");
            return true;
        }
        tmpFile = new File(destPath + "_tmp");
        long downloadSize = 0L;
        long startIndex = 0L;
 
        if (ddxc) {
            if (tmpFile.exists() && tmpFile.isFile()) {
                downloadSize = tmpFile.length();
                startIndex = downloadSize;
            }
            httpGet.addHeader("Range", "bytes=" + startIndex + "-");//请求断点
        } else {
            if (tmpFile.exists() && tmpFile.isFile()) {
                boolean deleteFlag = tmpFile.delete();
                System.out.println("delete flag deleteFlag=="+ deleteFlag);
            }
        }
 
        try {
 
            // response实体
            httpResponse = httpClient.execute(httpGet);
            final HttpEntity entity = httpResponse.getEntity();
 
            if (null != entity) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                System.out.println("statusCode is "+statusCode);
                if (statusCode == 200 || statusCode == 206) {
                    raf = new RandomAccessFile(tmpFile, "rwd");
                    raf.seek(startIndex);
                    in = entity.getContent();
                    byte[] buffer = new byte[1024 * 10];
                    int size = 0;
                    System.out.println("start download ");
                    while ((size = in.read(buffer)) != -1) {
                        raf.write(buffer, 0, size);
 
                    }
                    System.out.println("download over!!!!");
                }
 
            }
        } catch (Exception e) {
            System.out.println("httpclient请求失败 url===="+url+":::::::"+ e.getMessage());
            return false;
        } finally {
            httpGet.releaseConnection();
            //规范流的关闭操作，否则如果报异常，流是没有正常关闭的
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
 
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
 
            closeResponse(httpResponse);
        }
        return tmpFile.renameTo(dFile);//重命名之前，该关闭的流都要关闭
    }
 
    public static byte[]  down2Byte(String url, String ip, Integer port) {
        // 创建默认的httpClient实例
        CloseableHttpClient httpClient =Sington.httpClient;
        CloseableHttpResponse httpResponse = null;
        RequestConfig requestConfig = getRequestConfig(1000 * 30, ip, port);
 
        // 用get方法发送http请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        httpGet.addHeader("User-Agent", ua);
        try {
            // response实体
            httpResponse = httpClient.execute(httpGet);
            final HttpEntity entity = httpResponse.getEntity();
 
            if (null != entity) {
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                System.out.println("statusCode is "+statusCode+" url=="+url);
                if (statusCode == 200 || statusCode == 206) {
                    byte[] contentBytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
                    return  contentBytes;
                }
 
            }
        } catch (Exception e) {
            System.out.println("httpclient请求失败 url===="+url+":::::::"+ e.getMessage());
            return null;
        } finally {
            //规范流的关闭操作，否则如果报异常，流是没有正常关闭的
            httpGet.releaseConnection();
            closeResponse(httpResponse);
        }
        return null;
    }
 
 
 
    /**
     * 初始化post值
     *
     * @param datas
     * @param httpPost
     */
    private static void initPostEntity(Map<String, Object> datas, HttpPost httpPost, String charset) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> entry : datas.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(key==null || value==null){
                continue;
            }
            params.add(new BasicNameValuePair(key, value.toString()));
        }
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, charset);
 
            httpPost.setEntity(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
 
    /**
     * 初始化post值
     *
     * @param dataStr
     * @param httpPost
     * @param charset
     */
    private static void initPostEntity(String dataStr, HttpPost httpPost, String charset) {
        StringEntity stringEntity = new StringEntity(dataStr, charset);
        httpPost.setEntity(stringEntity);
    }
 
    public static String getResponseContent(String charset, HttpResponse httpResponse) throws IOException {
        if (charset == null) {
            byte[] contentBytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
            String encoding = getEncoding(contentBytes);
          //  System.out.println("detect charset======"+encoding);
            return new String(contentBytes,encoding);
        } else {
            return IOUtils.toString(httpResponse.getEntity().getContent(), charset);
        }
    }
 
 
    /**
     * 关闭响应
     *
     * @param httpResponse
     */
    private static void closeResponse(CloseableHttpResponse httpResponse) {
        if (httpResponse != null) {
            try {
                EntityUtils.consume(httpResponse.getEntity());
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
 
 
    /**
     * 获取编码
     * @param bytes
     * @return
     */
    public static String getEncoding(byte[] bytes) {
        String defaultEncoding = "UTF-8";
        UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        detector.reset();
        //System.out.println("字符编码是："+ encoding);
        if (encoding == null) {
            encoding = defaultEncoding;
        }
        return encoding;
    }
 
 
    /**
     * 初始化config
     *
     * @param request
     * @param headers
     */
    private static void initHeader(HttpUriRequest request, Map<String, String> headers) {
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String name = entry.getKey();
                String value = entry.getValue();
                request.addHeader(name, value);
            }
        } else {
            for (Map.Entry<String, String> entry : defaultHeader.entrySet()) {
                String name = entry.getKey();
                String value = entry.getValue();
                request.addHeader(name, value);
            }
        }
    }
 
    /**
     * 初始化 config
     *
     * @param httpGet
     * @param timeout
     * @param ip
     * @param port
     */
    private static void initconfig(HttpGet httpGet, Integer timeout, String ip, Integer port) {
        RequestConfig requestConfig = getRequestConfig(timeout, ip, port);
        httpGet.setConfig(requestConfig);
    }
 
 
    /**
     * 初始化config
     *
     * @param httpPost
     * @param timeout
     * @param ip
     * @param port
     */
    private static void initconfig(HttpPost httpPost, Integer timeout, String ip, Integer port) {
        RequestConfig requestConfig = getRequestConfig(timeout, ip, port);
        httpPost.setConfig(requestConfig);
    }
 
    /**
     * 构造requestconfig
     *
     * @param timeout
     * @param ip
     * @param port
     * @return
     */
    private static RequestConfig getRequestConfig(Integer timeout, String ip, Integer port) {
        RequestConfig.Builder confBuilder = RequestConfig.custom();
        if (timeout == null) {
            timeout = default_timeout;
        }
        confBuilder.setConnectTimeout(timeout).setSocketTimeout(timeout).setConnectionRequestTimeout(timeout);
        if (ip != null && port != null) {
            confBuilder.setProxy(new HttpHost(ip, port));
        }
        confBuilder.setRedirectsEnabled(true);
        return confBuilder.build();
    }
 
}