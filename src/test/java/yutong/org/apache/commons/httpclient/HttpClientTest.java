package yutong.org.apache.commons.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class HttpClientTest {
    public static String url = "http://192.168.1.204:8080/RuleEngineV4-Web/";


    public static void main(String[] args) {
        String elogin = url + "elogin.do";
        String evaluate = url + "evaluate.do";
        String judge = url + "judge.do";

        HttpClient httpClient = new HttpClient();
        // 设置代理服务器地址和端口
        // httpClient.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
        httpClient.getParams().setContentCharset("UTF-8");

        /* 设置 请求超时时间 */
        httpClient.getHttpConnectionManager().getParams()
                .setConnectionTimeout(10 * 1000); // 10s

        /* 设置 响应超时时间 */
        httpClient.getHttpConnectionManager().getParams()
                .setSoTimeout(20 * 1000); // 20s

        System.out.println("1.登录接口=====================");
        /* 1.登录接口 */
        invokeElogin(httpClient, elogin);

        System.out.println("2.获取规则列表接口=====================");
        /* 2.获取规则结构接口 */
        invokeEvaluate(httpClient, evaluate);

        System.out.println("3.调用规则执行接口=====================");
        /* 3.调用规则执行接口 */
        invokeJudge(httpClient, judge);
    }


    /**
     * 登录接口
     * 
     * @param httpClient
     * @param invokeUrl
     * 
     */
    public static void invokeElogin(HttpClient httpClient, String invokeUrl) {
        PostMethod postMethod = new PostMethod(invokeUrl);
        /* 设置请求头信息 */
        postMethod.setRequestHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=utf-8");

        postMethod.addParameter("property(userId)", "123");
        postMethod.addParameter("property(password)", "123");

        try {
            /* 执行调用并取得响应状态码 */
            int status = httpClient.executeMethod(postMethod);

            if (status == HttpStatus.SC_OK) {
                InputStream inputStream = postMethod.getResponseBodyAsStream();
                if (inputStream != null) {
                    String responseBody =
                            IOUtils.toString(inputStream, "UTF-8");
                    System.out.println(responseBody);
                }
            }
            else {
                System.out.println("失败状态码:" + status);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            postMethod.releaseConnection();
        }
    }


    /**
     * 获取规则结构接口
     * 
     * @param httpClient
     * @param invokeUrl
     * 
     */
    public static void invokeEvaluate(HttpClient httpClient, String invokeUrl) {
        PostMethod postMethod = new PostMethod(invokeUrl);
        /* 设置请求头信息 */
        postMethod.setRequestHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=utf-8");

        postMethod.addParameter("v", "B0046;C0055;C0058");

        try {
            /* 执行调用并取得响应状态码 */
            int status = httpClient.executeMethod(postMethod);

            if (status == HttpStatus.SC_OK) {
                InputStream inputStream = postMethod.getResponseBodyAsStream();
                if (inputStream != null) {
                    String responseBody =
                            IOUtils.toString(inputStream, "UTF-8");
                    System.out.println(responseBody);
                }
            }
            else {
                System.out.println("失败状态码:" + status);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            postMethod.releaseConnection();
        }
    }


    /**
     * 调用规则执行接口
     * 
     * @param httpClient
     * @param invokeUrl
     * 
     */
    public static void invokeJudge(HttpClient httpClient, String invokeUrl) {
        PostMethod postMethod = new PostMethod(invokeUrl);
        /* 设置请求头信息 */
        postMethod.setRequestHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=utf-8");

        /* 设置请求参数信息 */
        postMethod.addParameter("r", patientJson_1050);

        try {
            /* 执行调用并取得响应状态码 */
            int status = httpClient.executeMethod(postMethod);

            if (status == HttpStatus.SC_OK) {
                String responseCharSet = postMethod.getResponseCharSet();
                System.out.println(responseCharSet);
                InputStream inputStream = postMethod.getResponseBodyAsStream();
                if (inputStream != null) {
                    String responseBody = "";
                    boolean flag = true;
                    ByteArrayOutputStream byteArrayOutputStream =
                            new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, len);

                        /* 为防止返回内容过长 (限制<3MB) */
                        if (byteArrayOutputStream.size() > (3 * 1024 * 1024)) {
                            flag = false;
                            break;
                        }
                    }
                    byteArrayOutputStream.close();
                    inputStream.close();
                    if (flag) {
                        responseBody = byteArrayOutputStream.toString("UTF-8");
                        System.out.println(responseBody);
                        System.out.println(byteArrayOutputStream.size());
                        System.out.println(responseBody.length());

                        JSONObject jsonObject =
                                JSONObject.fromObject(responseBody);
                        String j_status = jsonObject.getString("status");
                        if (StringUtils.equals("OK", j_status)) {
                            String j_page = jsonObject.getString("page");
                            JSONArray dataArray =
                                    jsonObject.getJSONArray("data");
                            System.out.println("页数:"
                                    + j_page + ", 违规总数:" + dataArray.size());
                        }
                        else {
                            String j_errorcd = jsonObject.getString("errorcd");
                            if (StringUtils.equals("E1035", j_errorcd)) {
                                // TODO:E1035：用户未登录 --> 需要重新登录获取cookie
                                System.out.println(
                                        "E1035：用户未登录 --> 需要重新登录获取cookie");
                            }
                            else {
                                System.out.println(
                                        jsonObject.getString("message"));
                            }
                        }
                    }
                    else {
                        System.out.println("返回内容超过5M,不予处理.");
                    }
                }
            }
            else {
                System.out.println("失败状态码:" + status);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            postMethod.releaseConnection();
        }
    }

    public static String patientJson_1050 =
            "{\"oem\":\"e3569z001334522z\",\"ip\":\"192.168.1.172\",\"client-date\":\"20160623\",\"client-user\":\"user01\",\"client-pass\":\"user01\",\"package-type\":\"P\",\"executor\":\"\",\"package\":[{\"reid\":\"P001050\",\"retype\":\"01\",\"reversion\":\"V4\",\"data\":[{\"patientId\":\"P001\",\"encounters\":[{\"encounterId\":\"E001\",\"hospitalCode\":\"JGDM001\",\"encounterType\":\"100\",\"admissionDeptId\":\"入院科室标识\",\"dischargeDeptId\":\"出院科室标识\",\"encounterDoctorId\":\"医生标识\",\"orders\":[{\"orderId\":\"E001_O001\",\"orderType\":\"0\",\"transationItemCode\":\"DR1202\",\"hospitalItemName\":\"医院项目名称\",\"medicareItemName\":\"YBXMMC001\",\"startDate\":\"2016-09-12\",\"issueOrderDeptId\":\"下达医嘱的科室标识\",\"orderDoctorId\":\"开医嘱医生标识\"},{\"orderId\":\"E001_O001xxxx\",\"orderType\":\"0\",\"transationItemCode\":\"abcDR1202\",\"hospitalItemName\":\"医院项目名称\",\"medicareItemName\":\"YBXMMC001\",\"startDate\":\"2016-09-12\",\"issueOrderDeptId\":\"下达医嘱的科室标识\",\"orderDoctorId\":\"开医嘱医生标识\"},{\"orderId\":\"E001_O002\",\"orderType\":\"0\",\"transationItemCode\":\"DL7046\",\"hospitalItemName\":\"医院项目名称\",\"medicareItemName\":\"YBXMMC002\",\"startDate\":\"2016-09-12\",\"issueOrderDeptId\":\"下达医嘱的科室标识\",\"orderDoctorId\":\"开医嘱医生标识\"}]},{\"encounterId\":\"E002\",\"hospitalCode\":\"JGDM001\",\"encounterType\":\"100\",\"admissionDeptId\":\"入院科室标识\",\"dischargeDeptId\":\"出院科室标识\",\"encounterDoctorId\":\"医生标识\",\"orders\":[{\"orderId\":\"E002_O001\",\"orderType\":\"0\",\"hospitalItemName\":\"医院项目名称\",\"medicareItemName\":\"YBXMMC002\",\"transationItemCode\":\"DL7046\",\"startDate\":\"2016-09-12\",\"issueOrderDeptId\":\"下达医嘱的科室标识\",\"orderDoctorId\":\"开医嘱医生标识\"}]}]}]}]}";

    public static String patientJson_0230 =
            "{\"oem\":\"e3569z001334522z\",\"ip\":\"192.168.1.172\",\"client-date\":\"20160623\",\"client-user\":\"user01\",\"client-pass\":\"user01\",\"package-type\":\"P\",\"executor\":\"\",\"package\":[{\"reid\":\"P000230\",\"retype\":\"01\",\"reversion\":\"V4\",\"data\":[{\"patientId\":\"bxf\",\"encounters\":[{\"encounterId\":\"E0001\",\"admissionDate\":\"2016-03-08\",\"encounterType\":\"400\",\"adminssionDepartmentName\":\"aaaa\",\"dischargeDepartmentName\":\"bbb\",\"diagnoses\":[{\"diagnoseId\":\"111\",\"diagnoseName\":\"ccc\",\"diagnoseCode\":\"ccc\",\"diagnoseType\":\"10\",\"diagnoseCategory\":\"0\"}],\"orders\":[{\"orderId\":\"R0001\",\"orderType\":\"1\",\"detailType\":\"0\",\"medicareItemName\":\"aaaaa\",\"hospitalItemName\":\"输aa啊\",\"startDate\":\"2016-03-15\",\"issueOrderDeptName\":\"AA\"}]}]}]}]}";
}
