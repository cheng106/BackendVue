package com.mark.cheng.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author cheng
 * @since 2022/5/30 20:35
 **/
public class ResourceUtil {


    public static String getResource(String resource) {
        StringBuilder json = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(ResourceUtil.class.getClassLoader()
                        .getResourceAsStream(resource)), StandardCharsets.UTF_8))) {

            String str;
            while ((str = in.readLine()) != null) {
                json.append(str);
            }
        } catch (IOException e) {
            throw new RuntimeException("Caught exception reading resource " + resource, e);
        }
        return json.toString();
    }
}
