package com.example.client.math.impl;

import com.example.client.math.MathService;
import com.example.client.model.Prognos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.Initializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MathServiceImpl implements MathService {

    @Override
    public ObservableList<Prognos> doSglaj(ObservableList<Prognos> list, int stepCounter) {
        ObservableList<Prognos> newList = FXCollections.observableArrayList(list);

        for (int i = 1; i < list.size() - 1; i++) {
            double midl = (list.get(i - 1).getValue() + list.get(i).getValue() + list.get(i + 1).getValue()) / 3;
            Prognos prognos = new Prognos(list.get(i).getDate(), midl);
            newList.set(i, prognos);
        }

        return newList;
    }

    @Override
    public ObservableList<Prognos> doLessSquareMethod(ObservableList<Prognos> list, Integer stepCounter) {
        ObservableList<Prognos> newList = FXCollections.observableArrayList(list);


        double midlX = 0;
        double midlY = 0;
        double midlXY = 0;
        double midlXX = 0;
        for (Prognos prognos : newList) {
            midlX += prognos.getDate();
            midlY += prognos.getValue();
            midlXY += prognos.getDate() * prognos.getValue();
            midlXX += prognos.getDate() * prognos.getDate();
        }

        int size = newList.size();

        midlX = midlX / size;
        midlY = midlY / size;
        midlXY = midlXY / size;
        midlXX = midlXX / size;

        double a = (midlXY - (midlY * midlX)) / (midlXX - (midlX * midlX));
        double b = midlY - (a * midlX);

        for (Integer i = 0; i < stepCounter; i++) {
            int lastNum = newList.get(newList.size() - 1).getDate() + 1;
            newList.add(new Prognos(lastNum, 0));

        }


        for (int i = 0; i < newList.size(); i++) {
            Prognos prognos = new Prognos(newList.get(i).getDate(), a * newList.get(i).getDate() + b);
            newList.set(i, prognos);
        }


        return newList;
    }

    @Override
    public ObservableList<Prognos> doOtclonPlus(ObservableList<Prognos> list1, ObservableList<Prognos> list2, int k) {
        ObservableList<Prognos> newList = FXCollections.observableArrayList();
        double sum = 0;
        double definitionInSquare = 0;
        for (int i = 0; i < list1.size(); i++) {
            definitionInSquare = (list1.get(i).getValue() - list2.get(i).getValue()) * (list1.get(i).getValue() - list2.get(i).getValue());
            sum += definitionInSquare;

        }
        double midl = sum / list1.size();
        double sqrD = Math.sqrt(midl);

        for (Prognos prognos : list2) {
            Prognos prognos1 = new Prognos();
            prognos1.setDate(prognos.getDate());
            prognos1.setValue(prognos.getValue() + sqrD);
            newList.add(prognos1);
        }
        return newList;
    }

    @Override
    public ObservableList<Prognos> doOtclonMinus(ObservableList<Prognos> list1, ObservableList<Prognos> list2, int k) {
        ObservableList<Prognos> newList = FXCollections.observableArrayList();
        double sum = 0;
        double definitionInSquare = 0;
        for (int i = 0; i < list1.size(); i++) {
            definitionInSquare = (list1.get(i).getValue() - list2.get(i).getValue()) * (list1.get(i).getValue() - list2.get(i).getValue());
            sum += definitionInSquare;

        }
        double midl = sum / list1.size();
        double sqrD = Math.sqrt(midl);

        for (Prognos prognos : list2) {
            Prognos prognos1 = new Prognos();
            prognos1.setDate(prognos.getDate());
            prognos1.setValue(prognos.getValue() - sqrD);
            newList.add(prognos1);
        }
        return newList;
    }

    @Override
    public ObservableList<Prognos> findMediana(ObservableList<Prognos> list) throws CloneNotSupportedException {
        List<Prognos> list1 = new ArrayList<>();
        Prognos prognos1 = new Prognos();

        for (Prognos prognos : list) {
            list1.add(prognos.clone());
        }
        Collections.sort(list1, new Comparator<Prognos>() {
            @Override
            public int compare(Prognos o1, Prognos o2) {
                return (int) (o1.getValue() - o2.getValue());
            }
        });


        int mediana = (list1.get(((list1.size() - 1) / 2) + 1).getDate());
        int midlValue = (int) (list1.get(((list1.size() - 1) / 2) + 1).getValue());
        list1.clear();
        Prognos prognos = new Prognos();
        prognos.setDate(mediana);
        prognos.setValue(0);
        list1.add(prognos);
        Prognos newPrognos = new Prognos();
        newPrognos.setDate(mediana);
        newPrognos.setValue(midlValue);

        list1.add(newPrognos);
        return FXCollections.observableArrayList(list1);
    }
}
