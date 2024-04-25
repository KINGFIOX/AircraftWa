package edu.hitsz.aircraft.DataIO;
import java.util.List;


public interface IRankListDao {
    void load(String path);
    void store(String path);
    List<Entry> getAllEntry();
    void addEntry(Entry entry);
    void deleteEntry(int id);
    void sort();
}
