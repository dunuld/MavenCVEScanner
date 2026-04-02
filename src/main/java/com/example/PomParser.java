package com.example;
import java.util.List;

public interface PomParser {
    List<Dependency> parse(String path);
}
