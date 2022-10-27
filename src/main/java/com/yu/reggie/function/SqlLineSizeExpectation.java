package com.yu.reggie.function;

import java.util.List;

public class SqlLineSizeExpectation extends Exception {
    public SqlLineSizeExpectation(List<String> line) {
        System.err.printf("size of (%s) is not accepted", line.size());
    }
}
