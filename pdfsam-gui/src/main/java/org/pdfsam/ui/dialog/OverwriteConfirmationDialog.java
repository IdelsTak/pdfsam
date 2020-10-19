/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 09/ott/2014
 * Copyright 2017 by Sober Lemur S.a.s. di Vacondio Andrea (info@pdfsam.org).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pdfsam.ui.dialog;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

import java.util.Optional;

import javax.inject.Inject;

import org.pdfsam.configuration.StylesConfig;
import org.pdfsam.ui.support.Style;
import org.sejda.model.output.ExistingOutputPolicy;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * Dialog asking the user to confirm for the output file overwrite
 * 
 * @author Andrea Vacondio
 *
 */
public class OverwriteConfirmationDialog extends Stage {

    private ConfirmationDialogContent dialogContent;
    private Optional<ExistingOutputPolicy> response = empty();
    private HBox buttons = new HBox();

    @Inject
    public OverwriteConfirmationDialog(StylesConfig styles) {
        initModality(Modality.WINDOW_MODAL);
        initStyle(StageStyle.UTILITY);
        setResizable(false);
        this.dialogContent = new ConfirmationDialogContent(DialogStyle.WARNING.icon);
        VBox containerPane = new VBox();
        containerPane.getStyleClass().addAll(Style.CONTAINER.css());
        containerPane.getStyleClass().addAll("-pdfsam-dialog", DialogStyle.WARNING.style);
        buttons.getStyleClass().add("-pdfsam-dialog-buttons");
        containerPane.getChildren().addAll(dialogContent, buttons);
        Scene scene = new Scene(containerPane);
        scene.getStylesheets().addAll(styles.styles());
        setScene(scene);
    }

    public void setOwner(Window owner) {
        initOwner(owner);
    }

    OverwriteConfirmationDialog title(String title) {
        setTitle(title);
        return this;
    }

    OverwriteConfirmationDialog messageTitle(String title) {
        dialogContent.messageTitle(title);
        return this;
    }

    OverwriteConfirmationDialog messageContent(String title) {
        dialogContent.messageContent(title);
        return this;
    }

    OverwriteConfirmationDialog buttons(Button... buttons) {
        this.buttons.getChildren().setAll(buttons);
        return this;
    }

    public Optional<ExistingOutputPolicy> response() {
        showAndWait();
        return response;
    }

    public Button button(String text, ExistingOutputPolicy response) {
        Button button = new Button(text);
        button.getStyleClass().addAll(Style.BUTTON.css());
        button.setOnAction(e -> {
            this.response = ofNullable(response);
            hide();
        });
        return button;
    }

    public Button defaultButton(String text, ExistingOutputPolicy response) {
        Button button = button(text, response);
        button.setDefaultButton(true);
        return button;
    }

    public Button cancelButton(String text) {
        Button button = button(text, null);
        button.setCancelButton(true);
        return button;
    }

}
