package com.cvescanner.pomparser;
import com.cvescanner.data.Dependency;

import java.util.List;

public interface IPomParser {
    List<Dependency> parse(String path);
}
