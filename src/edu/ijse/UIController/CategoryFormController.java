package edu.ijse.UIController;

import edu.ijse.dto.CategoryDto;
import edu.ijse.service.custom.impl.CategoryServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CategoryFormController {
    public AnchorPane context;
    public TextField txtCatID;
    public TextField txtCatName;
    public TextField txtCatDesc;
    public TableView<CategoryDto> tblCategories;
    public TableColumn<CategoryDto,String> colCatID;
    public TableColumn<CategoryDto,String> colCatName;
    public TableColumn<CategoryDto,String> colCatDesc;
    public Button btnSaveCat;

    private final CategoryServiceImpl categoryService = new CategoryServiceImpl();
    private final ObservableList<CategoryDto> categoryList = FXCollections.observableArrayList();

    public void initialize() {

        colCatID.setCellValueFactory(new PropertyValueFactory<>("catId"));
        colCatName.setCellValueFactory(new PropertyValueFactory<>("catName"));
        colCatDesc.setCellValueFactory(new PropertyValueFactory<>("catDesc"));

        generateCategoryID();
        loadCategory();

        tblCategories.setOnMouseClicked(this::selectValue);
    }

    public void saveCatOnAction(ActionEvent actionEvent) {

        if (txtCatName.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name cannot be empty...!").show();
            return;
        }


        if (btnSaveCat.getText().equalsIgnoreCase("Save Category")) {
            CategoryDto category = new CategoryDto(
                    txtCatID.getText(),
                    txtCatName.getText(),
                    txtCatDesc.getText()

            );
            try {
                String result = categoryService.save(category);
                if ("Success".equals(result)) {
                    categoryList.add(category);
                    clear();
                    loadCategory();
                    new Alert(Alert.AlertType.INFORMATION,"Category has been Saved...!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR,"Customer Save Failure...").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                generateCategoryID();
            }
        } else {

            CategoryDto category = new CategoryDto(
                    txtCatID.getText(),
                    txtCatName.getText(),
                    txtCatDesc.getText()
            );

            try {
                String result = categoryService.update(category);
                if("Success".equals(result)){
                    loadCategory();
                    clear();
                    btnSaveCat.setText("Save Category");
                    new Alert(Alert.AlertType.INFORMATION,"Category has been Updated...!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR,"Category Update Failure...").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                generateCategoryID();
            }
        }
    }

    public void newCatOnAction(ActionEvent actionEvent) {
        clear();
        generateCategoryID();
        btnSaveCat.setText("Save Category");
    }

    public void backToDashOnAction(ActionEvent actionEvent) throws IOException {
        setUI("Dashboard");
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.show();
        stage.centerOnScreen();
    }

    private void clear() {
        txtCatID.clear();
        txtCatName.clear();
        txtCatDesc.clear();
    }

    private void loadCategory() {
        try {
            ArrayList<CategoryDto> categories = categoryService.getAll();
            categoryList.clear();

            categoryList.setAll(categories);
            generateCategoryID();
            tblCategories.setItems(categoryList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectValue(MouseEvent mouseEvent) {
        CategoryDto selectedCategory = tblCategories.getSelectionModel().getSelectedItem();
        if(selectedCategory != null) {
            txtCatID.setText(selectedCategory.getCatId());
            txtCatName.setText(selectedCategory.getCatName());
            txtCatDesc.setText(selectedCategory.getCatDesc());
            btnSaveCat.setText("Update Student");
        }

    }

    private void generateCategoryID() {
        try{
            String stringID = categoryService.getLastID();
            if (stringID != null) {
                String[] split = stringID.split("-");
                String lastIDAsString = split[1];
                int lastIDAsInteger = Integer.parseInt(lastIDAsString);
                lastIDAsInteger++;
                String newID = String.format("C-%03d", lastIDAsInteger);
                txtCatID.setText(newID);
            } else {
                txtCatID.setText("C-001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
