package com.example.demo;

import com.example.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @类名 : Controller
 * @说明 : TODO
 * @创建人 : gile
 * @创建时间 : 2019/6/11 16:54
 * @版本 1.0
 */
@RestController
public  class Controller {

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
        return "不好!" ;
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
        String url = "http://jie:jbkj@localhost:8080/oauth/token?redirect_uri=http://localhost:8090/&grant_type=authorization_code&code="+code;
        String url1 = "http://localhost:8080/control/authentication/login?username=admin&password=888888";
//        headers.add("client_secret", "jbkj");
//        headers.add("client_id", "jie");
//        String url = "http://localhost:520/hitokoto/test";
        //HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        HttpEntity<String> entity = new HttpEntity<String>("\"client_secret\":\"jbkj\",\"client_id\":\"jie\"",headers);
        ResponseEntity<String> strbody1=restTemplate.exchange(url1, HttpMethod.POST, entity,String.class);

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


}



