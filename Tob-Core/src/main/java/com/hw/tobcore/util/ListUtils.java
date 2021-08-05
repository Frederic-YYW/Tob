package com.hw.tobcore.util;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @Description:
 * @Author:Frederic-YYW
 * @Email:yuanyangwen@iristar.com.cn
 * @Date 2020/6/10 10:38
 */
public class ListUtils {

    /**
     * 利用guava集合求交集
     */
    public static List<String> retainAllByGuava(List<String> orgList1, List<String> orgList2) {
        Set<String> set1 = new HashSet<>(orgList1);
        Set<String> set2 = new HashSet<>(orgList2);
        Sets.SetView<String> intersection = Sets.intersection(set1, set2);
        System.out.println("交集的个数为:" + intersection.size());
        return new ArrayList<>(intersection);
    }

    @Test
    public void test(){
        List<String> list1= Arrays.asList("fdasfsafdasdfasf","2gsdfgsdgdsgs","3gdsfgsgfsdfgsfg","gsdfgsdgfdsgs");
        List<String> list2= Arrays.asList("1","2","fdasfsafdasdfasf","6");
        List<String> strings = retainAllByGuava(list1, list2);
        System.out.println(strings);
    }
}
