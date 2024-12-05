package com.cct.skyapiinterfaceimpl;

import cn.hutool.core.io.resource.ClassPathResource;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * 完全基于文件查询
 */
public class SearcherTest {
    public static void main(String[] args) throws IOException {
        // 1、创建 searcher 对象
        String dbPath = "ipdata/ip2region.xdb";
        ClassPathResource resource = new ClassPathResource(dbPath);
        String absolutePath = resource.getAbsolutePath();
        System.out.println(absolutePath);
        Searcher searcher = null;
        try {
            searcher = Searcher.newWithFileOnly(absolutePath);
        } catch (IOException e) {
            System.out.printf("failed to create searcher with `%s`: %s\n", absolutePath, e);
            return;
        }

        // 2、查询
        String ip = "39.171.168.200";
        try {
            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
        } catch (Exception e) {
            System.out.printf("failed to search(%s): %s\n", ip, e);
            e.printStackTrace();
        }

        // 3、关闭资源
        searcher.close();

        // 备注：并发使用，每个线程需要创建一个独立的 searcher 对象单独使用。
    }
}