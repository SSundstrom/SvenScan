package com.example.svenscan.svenscan.interfaces;

import java.util.Observer;

public interface IObservable {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
}
