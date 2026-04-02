package com.cvescanner.service;
import com.cvescanner.data.Dependency;

import java.util.List;

public interface ICveService {
    List<String> check(Dependency dep);
}