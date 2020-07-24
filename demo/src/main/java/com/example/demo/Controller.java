package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.pojo.User;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @类名 : Controller
 * @说明 : TODO
 * @创建人 : gile
 * @创建时间 : 2019/6/11 16:54
 * @版本 1.0
 */
@RestController
public class Controller {

    public static String code = "";

    @Autowired
    private OAuth2RestOperations oauthRestTemplate;

    @Autowired
    private RestTemplate restTemplate;


    @PreAuthorize("#oauth2.hasScope('app') and #oauth2.hasScope('test')")
    @RequestMapping("/gainCode")
    public String gainCode(String code) {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("code", code);
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);

//        ResponseEntity<String> stringResponseEntity = oauthRestTemplate.postForEntity("http://172.20.0.156:8080/test1", user, String.class);
        ResponseEntity<String> stringResponseEntity = oauthRestTemplate.exchange("http://172.20.0.156:8080/test1", HttpMethod.POST, requestEntity, String.class);
        return "你好!";
    }

    @RequestMapping("/gainToken")
    public String gainToken(String code) {

        Controller.code = code;

        OAuth2AccessToken accessToken = oauthRestTemplate.getAccessToken();
        return "不好!" + accessToken;
    }

    @RequestMapping("/getCode")
    public String getCode() {
        return "不好!";
    }

    @RequestMapping("/oauth/error")
    public String error(@RequestParam Map<String, String> parameters) {
        String uri = parameters.get("redirect_uri");
        //    log.info("重定向: {}", uri);

        return "redirect:" + uri + "?error=1";
    }

    @RequestMapping("/test1")
    public String test(String code) {
        User user = new User();
        user.setUsername("root");
        user.setPassword("root");
        OAuth2AccessToken accessToken = oauthRestTemplate.getAccessToken();
        ResponseEntity<String> stringResponseEntity = oauthRestTemplate.postForEntity("http://172.20.0.156:8080/user/test1", accessToken, String.class);
//        POST http://jie:jbkj@localhost:8080/oauth/token?redirect_uri=http://localhost:8090/&grant_type=authorization_code&code=tOz96C
        final String url = "http://localhost:8080/oauth/token";
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("redirect_uri", "http://localhost:8090/");
        //requestHeaders.add("intent", String.valueOf(intent));
        requestHeaders.add("grant_type", "authorization_code");
        requestHeaders.add("code", code);
        RestTemplate template = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);

        // ResponseEntity<String> response = oauthRestTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String sttr = stringResponseEntity.toString();
        System.out.println(sttr);
        return accessToken.getValue();
    }


    @RequestMapping("/yjq")
    public ResponseEntity getToken(@RequestParam String code) {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String url = "http://jie:jbkj@localhost:8080/oauth/token?redirect_uri=http://localhost:8090/&grant_type=authorization_code&code=" + code;
        String url1 = "http://localhost:8080/control/authentication/login?username=admin&password=888888";
//        headers.add("client_secret", "jbkj");
//        headers.add("client_id", "jie");
//        String url = "http://localhost:520/hitokoto/test";
        //HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        HttpEntity<String> entity = new HttpEntity<String>("\"client_secret\":\"jbkj\",\"client_id\":\"jie\"", headers);
        ResponseEntity<String> strbody1 = restTemplate.exchange(url1, HttpMethod.POST, entity, String.class);

        // String strbody=restTemplate.exchange(url, HttpMethod.POST, entity,String.class).getBody();
        //ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);


        //   HttpHeaders headers = new HttpHeaders();


//        WeatherResponse weatherResponse= JSONObject.parseObject(strbody,WeatherResponse.class);


        //System.err.println("::::::::::::::"+strbody);

        //   log.info("receive code {}",code);

//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      /*  MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
   //     params.add("grant_type", "authorization_code");
        params.add("code", code);
        params.add("client_id", "jie");
        params.add("client_secret", "jbkj");
        params.add("redirect_uri", "http://localhost:8090/");
       // HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
     //   ResponseEntity<String> response = oauthRestTemplate.postForEntity("http://172.20.0.156:520/hitokoto/test", requestEntity, String.class);
        String token = response.getBody();*/
        // log.info("token => {}",token);
        return strbody1;
    }


    @RequestMapping("/authorizedUrl")
    public String authorizedUrl() {

        return "redirect:" + "http://172.20.0.156:8080/oauth/authorize?client_id=jie&response_type=code&redirect_uri=http://172.20.0.156:8090/gdjb/";
    }

    // 微信授权回调地址
    @RequestMapping("/gdjb")
    public String callback(String code, HttpServletRequest request) {
        String accessTokenUrl = "http://jie:jbkj@localhost:8080/oauth/token?redirect_uri=http://172.20.0.156:8090/gdjb/&grant_type=authorization_code&code=" + code;
        HttpEntity<String> entity = new HttpEntity<String>(null, new HttpHeaders());
        ResponseEntity<String> strbody1 = restTemplate.postForEntity(accessTokenUrl, entity, String.class);
        String body = strbody1.getBody();
        System.out.println("body:" + body);

        JSONObject jsonObject = JSONObject.parseObject(body);

        String accessToken = jsonObject.getString("access_token");
        String refreshToken = jsonObject.getString("refresh_token");
        String expiresIn = jsonObject.getString("expires_in");
        String tokenType = jsonObject.getString("token_type");

        String testUrl = "http://jie:jbkj@172.20.0.156:8080/tset1";
        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("accessToken",accessToken);
//        httpHeaders.set("expiresIn",expiresIn);
//        httpHeaders.set("tokenType",tokenType);
        HttpEntity<String> testEntity = new HttpEntity<String>(body, httpHeaders);
        //  ResponseEntity<String> testStrbody = restTemplate.postForEntity(testUrl, testEntity, String.class);
        //   String test = testStrbody.getBody();

//        boolean containsKey = resultAccessToken.containsKey("errcode");
//        if (containsKey) {
//            request.setAttribute("errorMsg", "系统错误!");
//            return errorPage;
//        }
//        // 2.使用access_token获取用户信息
//        String accessToken = resultAccessToken.getString("access_token");
//        S
//        tring openid = resultAccessToken.getString("openid");
//        // 3.拉取用户信息(需scope为 snsapi_userinfo)
//        String userInfoUrl = weiXinUtils.getUserInfo(accessToken, openid);
//        JSONObject userInfoResult = HttpClientUtils.httpGet(userInfoUrl);
//        System.out.println("userInfoResult:" + userInfoResult);
//        request.setAttribute("nickname", userInfoResult.getString("nickname"));
//        request.setAttribute("city", userInfoResult.getString("city"));
//        request.setAttribute("headimgurl", userInfoResult.getString("headimgurl"));
        return body;
    }


    /**
     * post请求传输String参数 例如：name=Jack&sex=1&type=2
     * Content-type:application/x-www-form-urlencoded
     *
     * @param url
     * url地址
     * @param strParam
     * 参数
     * @return
     */

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class); // 日志记录

    private static RequestConfig requestConfig = null;

    public static JSONObject httpPost(String url, String strParam) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        JSONObject jsonResult = null;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        try {
            if (null != strParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            CloseableHttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    // 读取服务器返回过来的json字符串数据
                    str = EntityUtils.toString(result.getEntity(), "utf-8");
                    // 把json字符串转换成json对象
                    jsonResult = JSONObject.parseObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        } finally {
            httpPost.releaseConnection();
        }
        return jsonResult;
    }

}



