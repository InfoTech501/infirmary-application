package com.rocs.infirmary.application.service.dashboard;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import java.util.function.Function;

public class TableColumnHelper {
    public static <T> void setupNumberedColumn(TableColumn<T, String> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(getIndex() + 1));
            }
        });
        column.setStyle("-fx-alignment: CENTER;");
    }

    public static <T> void setupCenteredColumn(TableColumn<T, String> column,
                                                    Function<T, String> valueExtractor) {
            column.setCellValueFactory( cellData ->
                    new SimpleStringProperty(valueExtractor.apply(cellData.getValue())));
            column.setStyle("-fx-alihnment: CENTER;");
        }
    }
