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


import javafx.event.ActionEvent;
import javafx.scene.chart.XYChart;

public class DemoController {

    public ToggleableSeriesChart<Number, Number> chart;

    public void initialize() {
        final XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("x²");
        final XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("sin(x)");
        final XYChart.Series<Number, Number> series3 = new XYChart.Series<>();
        series3.setName("cos(x)");

        for (double i = -1 * Math.PI; i <= Math.PI; i = i + 0.2d) {
            series1.getData().add(new XYChart.Data<>(i, (i * i) - 1));
            series2.getData().add(new XYChart.Data<>(i, Math.sin(i)));
            series3.getData().add(new XYChart.Data<>(i, Math.cos(i)));
        }

        chart.getData().addAll(series1, series2, series3);
    }

    public void clear(ActionEvent actionEvent) {
        chart.getData().clear();
    }
}
