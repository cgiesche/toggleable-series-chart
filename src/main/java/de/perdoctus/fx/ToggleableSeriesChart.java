package de.perdoctus.fx;

/*
 * #%L
 * Toggleable Series Chart
 * %%
 * Copyright (C) 2016 Christoph Giesche
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */


import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ToggleableSeriesChart<X, Y> extends HBox {

    @FXML
    private VBox checkboxContainer;

    private ObjectProperty<XYChart<X, Y>> chart = new SimpleObjectProperty<>();

    private final SimpleListProperty<XYChart.Series<X, Y>> data = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final Map<XYChart.Series, CheckBox> seriesCheckBoxMap = new HashMap<>();

    public ToggleableSeriesChart() {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ToggleableSeriesChart.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        data.addListener((ListChangeListener<XYChart.Series<X, Y>>) c -> {
            while (c.next()) {
                c.getAddedSubList().forEach(this::createCheckboxForSeries);
                c.getRemoved().forEach(r -> checkboxContainer.getChildren().remove(seriesCheckBoxMap.get(r)));
            }
        });
        data.addListener((InvalidationListener) observable -> {
            chart.getValue().getData().clear();
            checkboxContainer.getChildren().clear();
        });
    }

    private void createCheckboxForSeries(final XYChart.Series<X, Y> series) {
        final CheckBox checkBox = new CheckBox(series.getName());
        checkBox.selectedProperty().addListener(observable -> {
            if (checkBox.selectedProperty().get()) {
                chart.getValue().getData().add(series);
            } else {
                chart.getValue().getData().remove(series);
            }
        });

        checkboxContainer.getChildren().add(checkBox);
        seriesCheckBoxMap.put(series, checkBox);
    }

    public ObservableList<XYChart.Series<X, Y>> getData() {
        return data.get();
    }

    public SimpleListProperty<XYChart.Series<X, Y>> dataProperty() {
        return data;
    }

    public void setData(ObservableList<XYChart.Series<X, Y>> data) {
        this.data.set(data);
    }

    public VBox getCheckboxContainer() {
        return checkboxContainer;
    }

    public void setCheckboxContainer(VBox checkboxContainer) {
        this.checkboxContainer = checkboxContainer;
    }

    public XYChart<X, Y> getChart() {
        return chart.get();
    }

    public void setChart(final XYChart<X, Y> chart) {
        getChildren().removeIf(node -> node instanceof XYChart);
        getChildren().add(chart);
        HBox.setHgrow(chart, Priority.ALWAYS);
        this.chart.set(chart);
    }

    public Map<XYChart.Series, CheckBox> getSeriesCheckBoxMap() {
        return seriesCheckBoxMap;
    }
}
