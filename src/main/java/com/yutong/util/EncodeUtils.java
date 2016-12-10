package com.yutong.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringEscapeUtils;


/**
 * 编码解码工具类
 * 
 * @author yuxiangtong
 *
 */
public class EncodeUtils {

    private static final String DEFAULT_ENCODING = "UTF-8";


    /**
     * Hex编码.
     */
    public static String hexEncode(byte[] input) {
        return Hex.encodeHexString(input);
    }


    /**
     * Hex解码.
     */
    public static byte[] hexDecode(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        }
        catch (DecoderException e) {
            throw new IllegalStateException("Hex Decoder exception", e);
        }
    }


    /**
     * Base64编码.
     */
    public static String base64Encode(byte[] input) {
        return Base64.encodeBase64String(input);
    }


    /**
     * Base64编码, URL安全(对URL不支持的字符如+,/,=转为其他字符, 见RFC3548).
     */
    public static String base64UrlEncode(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }


    /**
     * Base64解码.
     */
    public static byte[] base64Decode(String input) {
        return Base64.decodeBase64(input);
    }


    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String urlEncode(String input) {
        return urlEncode(input, DEFAULT_ENCODING);
    }


    /**
     * URL 编码.
     */
    public static String urlEncode(String input, String encoding) {
        try {
            return URLEncoder.encode(input, encoding);
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception",
                    e);
        }
    }


    /**
     * URL 解码, Encode默认为UTF-8.
     */
    public static String urlDecode(String input) {
        return urlDecode(input, DEFAULT_ENCODING);
    }


    /**
     * URL 解码.
     */
    public static String urlDecode(String input, String encoding) {
        try {
            return URLDecoder.decode(input, encoding);
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception",
                    e);
        }
    }


    /**
     * Html转码.
     */
    public static String htmlEscape(String html) {
        return StringEscapeUtils.escapeHtml(html);
    }


    /**
     * Html 反转码.
     */
    public static String htmlUnescape(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml(htmlEscaped);
    }


    /**
     * Xml转码.
     */
    public static String xmlEscape(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }


    /**
     * Xml 反转码.
     */
    public static String xtmlUnescape(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

}
