package edu.hitsz.aircraft.DataIO;

public class Entry {
    public int score;  // 分数，作为排名依据
    public long time;  // 时间戳

    public Entry(int score, long time) {
        this.score = score;
        this.time = time;
    }

    // 提供一个打印方法方便输出
    @Override
    public String toString() {
        return "Score: " + score + ", Time: " + time;
    }
}