package com.cct.skyapiinterfaceimpl;

import cn.hutool.core.io.resource.ClassPathResource;
import org.lionsoul.ip2region.xdb.Searcher;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class SearcherTest1 {
    public static void main(String[] args) throws IOException {
        // 1、创建 searcher 对象
        String dbPath = "ipdata/ip2region.xdb";
        ClassPathResource resource = new ClassPathResource(dbPath);
        String absolutePath = resource.getAbsolutePath();
        System.out.println(absolutePath);
        Searcher searcher = null;

        // 1、从 dbPath 中预先加载 VectorIndex 缓存，并且把这个得到的数据作为全局变量，后续反复使用。
        byte[] vIndex;
        try {
            vIndex = Searcher.loadVectorIndexFromFile(absolutePath);
        } catch (Exception e) {
            System.out.printf("failed to load vector index from `%s`: %s\n", absolutePath, e);
            return;
        }

        // 2、使用全局的 vIndex 创建带 VectorIndex 缓存的查询对象。
        try {
            searcher = Searcher.newWithVectorIndex(absolutePath, vIndex);
        } catch (Exception e) {
            System.out.printf("failed to create vectorIndex cached searcher with `%s`: %s\n", absolutePath, e);
            return;
        }

        // 3、查询
        String ip = "39.171.168.200";
        try {

            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
        } catch (Exception e) {
            System.out.printf("failed to search(%s): %s\n", ip, e);
        }

        // 4、关闭资源
        searcher.close();

        // 备注：每个线程需要单独创建一个独立的 Searcher 对象，但是都共享全局的制度 vIndex 缓存。
    }
}