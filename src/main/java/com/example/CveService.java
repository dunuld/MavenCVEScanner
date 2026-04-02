package com.example;
import java.util.List;

public interface CveService {
    List<String> check(Dependency dep);
}