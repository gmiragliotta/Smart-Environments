package com.unime.demoapp;

import com.unime.demoapp.Classifier.Recognition;

import java.util.List;

public interface ResultsView {
    void setResults(final List<Recognition> results);
}
