package data;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * Created by syz on 2016/4/9.
 */
public class JuheDemo {
    public static final String DEF_CHATSET = "UTH-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    public static final String APPKEY = "5cd97982c4a9f8352b5497694f26be84";

    public static void getRequest1(){
        String result = null;
        String url = "http://op.juhe.cn/onebox/stock/query ";
        Map params = new HashMap();
        params.put("key",APPKEY);
        params.put("dtype","");
        params.put("stock","");

        try{
            result = net(url, params ,"GET");
            JSONObject object = JSONObject.fromObject(result);
            if(object.getInt("error_code")==0){
                System.out.println(object.get("result"));
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

    }

    public static String net(String strUrl,Map params,String method) throws Exception{
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try{
            StringBuffer sb = new StringBuffer();
            if(method==null||method.equals("GET")){
                strUrl = strUrl+"?"+urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection)url.openConnection();
            if(method==null||method.equals("GET")){
                conn.setRequestMethod("GET");
            }else{
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent",userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout((DEF_CONN_TIMEOUT));
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if(params!=null&&method.equals("POST")){
                try{
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeByte(urlencode(params));
                }catch (Exception e){

                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is,DEF_CHATSET));
            String strRead = null;
            while((strRead = reader.readLine())!=null){
                sb.append(strRead);
            }
            rs = sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                reader.close();
            }
            if(conn!=null){
                conn.disconnect();
            }
        }
        return rs;
    }

    public static String urlencode(Map<String,Object>data){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry i : data.entrySet()){
            try{
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
