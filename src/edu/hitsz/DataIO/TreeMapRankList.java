package edu.hitsz.aircraft.DataIO;

import java.io.*;
import java.util.*;

public class TreeMapRankList implements IRankList {
    private TreeMap<Integer, PriorityQueue<Entry>> map;

    public TreeMapRankList() {
        // 初始化TreeMap，分数高的排在前面，如果分数相同，则按时间升序排列
        this.map = new TreeMap<>(Collections.reverseOrder());
    }

    @Override
    public void load(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            return; // 如果文件不存在，直接返回
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int score = Integer.parseInt(parts[0]);
                long time = Long.parseLong(parts[1]);
                addEntry(new Entry(score, time));
            }
        }
    }

    @Override
    public void store(String path) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (PriorityQueue<Entry> queue : map.values()) {
                for (Entry entry : queue) {
                    writer.write(entry.score + "," + entry.time);
                    writer.newLine();
                }
            }
        }
    }

    @Override
    public List<Entry> getAllEntries() {
        List<Entry> entries = new ArrayList<>();
        for (PriorityQueue<Entry> queue : map.values()) {
            entries.addAll(queue);
        }
        return entries;
    }

    @Override
    public void addEntry(Entry entry) {
        map.putIfAbsent(entry.score, new PriorityQueue<>(Comparator.comparingLong(e -> e.time)));
        map.get(entry.score).add(entry);
    }

    @Override
    public void deleteEntry(int score) {
        // 从排行榜中删除分数为score的所有条目（可根据需要修改以删除特定条目）
        map.remove(score);
    }


    // 打印所有条目，用于测试
    @Override
    public void printEntries() {
        int i = 0;
        for (Map.Entry<Integer, PriorityQueue<Entry>> entry : map.entrySet()) {
            i++;
            System.out.println("No." + i + " Score: " + entry.getKey());
            for (Entry e : entry.getValue()) {
                System.out.println("  " + e);
            }
        }
    }
}

