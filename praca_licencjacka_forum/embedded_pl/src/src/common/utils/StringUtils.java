/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common.utils;

/**
 *
 * @author Aleksander Wojcik
 */
public final class StringUtils {

    public static boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }
}
