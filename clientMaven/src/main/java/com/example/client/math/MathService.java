package com.example.client.math;

import com.example.client.model.Prognos;
import javafx.collections.ObservableList;

public interface MathService {
    ObservableList<Prognos> doSglaj(ObservableList<Prognos> list, int stepCounter);

    ObservableList<Prognos> doLessSquareMethod(ObservableList<Prognos> list, Integer stepCounter);

    ObservableList<Prognos> doOtclonPlus(ObservableList<Prognos> list1,ObservableList<Prognos> list2, int k);

    ObservableList<Prognos> doOtclonMinus(ObservableList<Prognos> list1,ObservableList<Prognos> list2, int k);


    ObservableList<Prognos> findMediana(ObservableList<Prognos> list) throws CloneNotSupportedException;

}
