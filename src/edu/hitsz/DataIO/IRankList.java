package edu.hitsz.DataIO;

import java.io.IOException;
import java.util.List;


public interface IRankList {
    void load(String path) throws IOException;

    void store(String path) throws IOException;

    List<Entry> getAllEntries();

    void addEntry(Entry entry);

    void deleteEntry(int id);

    void printEntries();
}
