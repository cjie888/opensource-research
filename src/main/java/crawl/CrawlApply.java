package crawl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @description 胡成杰(chengjiehu@creditease.cn)
 * 2016/8/17 11:37
 */
public class CrawlApply {

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        try {
//            JSESSIONID	UICHA2D4-T00DVFQ6TTS1B2HXV4XM3-5J73UWRI-Q8F	N/A	N/A	N/A	56
//            dms_login_id	xxxxx	N/A	N/A	N/A	25
//            dms_password	xxxxxxxx	N/A	N/A	N/A	28
//            loginToken	all1qov0q1zzwqg0	N/A	N/A	N/A	29
//            loginToken_user_id	xxxxxxxx	N/A	N/A	N/A	33
//            lzstat_uv	36806875761659789135|3371939@3605912	N/A	N/A	N/A	48
//            tmp0	eNpNjr9OwlAchVGjosjuaJih9t5bSsMkUgglIsFSRBe8vfdXuKR%2FzG0bSYyTi6Oji4ODuw%2FgK%2FgUJkTfwhoH3L6cnC%2FndPZYKiWEySSNQTbVQxYqnqQpDyCRYqEwCVwkQGNQeBArnPpK5M6BJYqT9c3%2B07rg%2BV81pAFs%2B9FUhBbPX9M4vokk3woiV%2FiwCQEV%2Fs40SJoSaAKFjHoRF54AfvVR3Pi6f1k%2BPC6f33bZDMLpXMAsVQ88l2lIwwwoxtTTmc5xBpggoAYFwy0gomGdkCqp1vZX4tG%2Fyyzs5nJrzuv7d%2F4PPv1Z0W7ZttU%2FnfT6ZutEvbgVvF5yrGangU2tMlRVc9Qe6MOhjY5xZzzSxj1SqXZrxDk%2FsyoDo10qs6SOtBoiKJvGuobKlK0Cw9BxOVjU1bvLHyDhcko%3D	N/A	N/A	N/A	455
            for (int i = 1; i < 612; i++) {
                CookieStore cookieStore = new BasicCookieStore();
                String JSESSIONID = "UICHA2D4-T00DVFQ6TTS1B2HXV4XM3-5J73UWRI-Q8F";
                System.out.println("JSESSIONID:" + JSESSIONID);
                // 新建一个Cookie
                BasicClientCookie cookie = new BasicClientCookie("JSESSIONID",
                        JSESSIONID);
                cookie.setDomain("creditease.corp");
                cookieStore.addCookie(cookie);
                BasicClientCookie cookie1 = new BasicClientCookie("dms_login_id",
                        "xxxxxxxxx");
                cookie1.setDomain("creditease.corp");
                cookieStore.addCookie(cookie1);
                BasicClientCookie cookie2 = new BasicClientCookie("dms_password",
                        "xxxxxxxxxxx");
                cookie2.setDomain("creditease.corp");
                cookieStore.addCookie(cookie2);
                BasicClientCookie cookie3 = new BasicClientCookie("loginToken",
                        "all1qov0q1zzwqg0");
                cookie3.setDomain("creditease.corp");
                cookieStore.addCookie(cookie3);
                BasicClientCookie cookie4 = new BasicClientCookie("loginToken_user_id",
                        "XXXXXXXXXXXXX");
                cookie4.setDomain("creditease.corp");
                cookieStore.addCookie(cookie4);
                BasicClientCookie cookie5 = new BasicClientCookie("lzstat_uv",
                        "36806875761659789135|3371939@3605912");
                cookie5.setDomain("creditease.corp");
                cookieStore.addCookie(cookie5);
                BasicClientCookie cookie6 = new BasicClientCookie("tmp0",
                        "eNpNjr9OwlAchVGjosjuaJih9t5bSsMkUgglIsFSRBe8vfdXuKR%2FzG0bSYyTi6Oji4ODuw%2FgK%2FgUJkTfwhoH3L6cnC%2FndPZYKiWEySSNQTbVQxYqnqQpDyCRYqEwCVwkQGNQeBArnPpK5M6BJYqT9c3%2B07rg%2BV81pAFs%2B9FUhBbPX9M4vokk3woiV%2FiwCQEV%2Fs40SJoSaAKFjHoRF54AfvVR3Pi6f1k%2BPC6f33bZDMLpXMAsVQ88l2lIwwwoxtTTmc5xBpggoAYFwy0gomGdkCqp1vZX4tG%2Fyyzs5nJrzuv7d%2F4PPv1Z0W7ZttU%2FnfT6ZutEvbgVvF5yrGangU2tMlRVc9Qe6MOhjY5xZzzSxj1SqXZrxDk%2FsyoDo10qs6SOtBoiKJvGuobKlK0Cw9BxOVjU1bvLHyDhcko%3D");
                cookie6.setDomain("creditease.corp");
                cookieStore.addCookie(cookie6);

                HttpContext httpContext = new BasicHttpContext();
                httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

                Header[] headers = {new BasicHeader("X_REQUESTED_WITH", "XMLHttpRequest")};
                String url = "http://yrdtongdun.creditease.corp/websql/queryResult.htm?t=" + System.currentTimeMillis();// 同步SMP地址
                Map<String, String> paraMap = new HashMap<>();

                paraMap.put("id", "631");
                paraMap.put("sql", "SELECT apply_id, plan_pay_principal - actual_pay_principal FROM repayment_statistics WHERE CURRENT_STATUS != 1 \tAND plan_pay_principal - actual_pay_principal > 0.01 ORDER BY apply_id DESC");
                paraMap.put("currPage", i + "");
                paraMap.put("pageSize", 500 + "");
                DefaultHttpClient client = new DefaultHttpClient();
                client.setCookieStore(cookieStore);
                HttpPost httpPost = new HttpPost(url);
                httpPost.setEntity(new UrlEncodedFormEntity(buildParameters(paraMap), "UTF-8"));

                // httpclient.getParams().setParameter("http.protocol.single-cookie-header", true);
                client.getParams().setParameter(
                        ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
                client.getParams().setParameter(HttpMethodParams.SINGLE_COOKIE_HEADER, true);

                httpPost.setHeaders(headers);
                HttpResponse httpResponse = client.execute(httpPost, httpContext);

                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream in = httpEntity.getContent();
                InputStreamReader reader = new InputStreamReader(in, "UTF-8");
                StringBuilder builder = new StringBuilder();
                char[] buffer = new char[512];
                int len = 0;
                while ((len = reader.read(buffer, 0, buffer.length)) != -1) {
                    builder.append(buffer, 0, len);
                }
                //System.out.println(builder.toString());


                Document doc = Jsoup.parse(builder.toString());

                Element table = doc.select("table").first();

                Elements trs = table.select("tr");
                for (Element tr : trs) {
                    Elements tds = tr.select("td");
                    boolean first = true;
                    for (Element td : tds) {
                        //System.out.print(td.attr("title"));
                        sb.append(td.attr("title"));
                        if (first) {
                            //System.out.print(",");
                            sb.append(",");
                            first = false;
                        }
                    }
                    if (tds != null && tds.size() > 1) {
                        sb.append("\r\n");
                    }
                    //System.out.println();
                }
                BufferedWriter bw = new BufferedWriter(new FileWriter("test.csv"));
                bw.write(sb.toString());
            }
            System.out.println(sb.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static List<NameValuePair> buildParameters(Map<String, String> parameters) {
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        Set<Map.Entry<String, String>> entry = parameters.entrySet();
        for (Map.Entry<String, String> e : entry) {
            NameValuePair p = new BasicNameValuePair(e.getKey(), e.getValue());
            paramList.add(p);
        }
        return paramList;
    }
}
