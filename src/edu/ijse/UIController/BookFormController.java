package edu.ijse.UIController;

import edu.ijse.dao.custom.impl.CategoryDaoImpl;
import edu.ijse.dto.BookDto;
import edu.ijse.entity.CategoryEntity;
import edu.ijse.service.custom.impl.BookServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class BookFormController {
    public AnchorPane context;
    public TextField txtBookID;
    public TextField txtTitle;
    public TextField txtAuthor;
    public TextField txtUnitPrice;
    public TextField txtQoh;
    public TextField txtISBN;
    public Button btnSaveBook;
    public ComboBox<String> cmbBookCategory;
    public TableView<BookDto> tblBook;
    public TableColumn<BookDto, String> colBookID;
    public TableColumn<BookDto, String> colTitle;
    public TableColumn<BookDto, String> colAuthor;
    public TableColumn<BookDto, String> colISBN;
    public TableColumn<BookDto, Integer> colQoh;
    public TableColumn<BookDto, Double> colUnitPrice;
    public TableColumn<BookDto, String> colCategory;
    public TableColumn<BookDto, Button> colOption;

    private final BookServiceImpl bookService = new BookServiceImpl();
    private final ObservableList<BookDto> bookList = FXCollections.observableArrayList();
    private final CategoryDaoImpl categoryDao = new CategoryDaoImpl();

    public void initialize() throws Exception {

        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colQoh.setCellValueFactory(new PropertyValueFactory<>("qoh"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("categoryID"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));

        setCategory();
        generateBookID();
        loadBook();
        tblBook.setOnMouseClicked(this::selectValue);
    }

    public void saveBookOnAction(ActionEvent actionEvent) {

        //validate the QOH, Unit Price and ISBN
        if (!txtQoh.getText().matches("^\\d+$") || !txtUnitPrice.getText().matches("^\\d+(\\.\\d+)?$") || !txtISBN.getText().matches("^\\d{3}-\\d{2}-\\d{6}-\\d-\\d$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Input...!").show();
            return;
        }
        //validate the book category
        if (cmbBookCategory.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Please select a Book Category...!").show();
            return;
        }

        if (txtTitle.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Title cannot be empty...!").show();
            return;
        }
        

        Button button = new Button("Remove");
        if(btnSaveBook.getText().equalsIgnoreCase("Save Book")) {
            BookDto book = new BookDto(
                    txtBookID.getText(),
                    txtTitle.getText(),
                    txtAuthor.getText(),
                    txtISBN.getText(),
                    Integer.parseInt(txtQoh.getText()),
                    Double.parseDouble(txtUnitPrice.getText()),
                    cmbBookCategory.getValue().split(". ")[0],
                    button
            );
            try {
                String result = bookService.save(book);
                if ("Success".equals(result)) {
                    bookList.add(book);
                    tblBook.refresh();
                    clear();
                    loadBook();
                    new Alert(Alert.AlertType.CONFIRMATION, "Book Saved Successfully...!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Book Save Failure...!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            BookDto book = new BookDto(
                    txtBookID.getText(),
                    txtTitle.getText(),
                    txtAuthor.getText(),
                    txtISBN.getText(),
                    Integer.parseInt(txtQoh.getText()),
                    Double.parseDouble(txtUnitPrice.getText()),
                    cmbBookCategory.getValue().split(". ")[0],
                    button
            );
            try {
                String result = bookService.update(book);
                if ("Success".equals(result)) {
                    loadBook();
                    clear();
                    btnSaveBook.setText("Save Book");
                    new Alert(Alert.AlertType.CONFIRMATION, "Book Updated Successfully...!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Book Update Failed...!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                generateBookID();
            }
        }
    }

    public void backToDashOnAction(ActionEvent actionEvent) throws IOException {
        setUI("Dashboard");
    }

    public void newBookOnAction(ActionEvent actionEvent) {
        clear();
        generateBookID();
        btnSaveBook.setText("Save Book");
        cmbBookCategory.getSelectionModel().clearSelection();
    }

    private void loadBook(){
        try {
            ArrayList<BookDto> books = bookService.getAll();
            bookList.clear();
            if (books != null && !books.isEmpty()) {
                for (BookDto book : books) {
                    book.getButton().cursorProperty().set(Cursor.HAND);
                    book.getButton().setOnAction(event -> { // setting up the remove button to remove the records.
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure...?",
                                ButtonType.YES, ButtonType.NO);
                        Optional<ButtonType> buttonType = alert.showAndWait();
                        if (buttonType.get().equals(ButtonType.YES)) {
                            try {
                                String result = bookService.delete(book.getBookID());
                                if ("Success".equals(result)) {
                                    new Alert(Alert.AlertType.INFORMATION, "Book Deleted Successfully...").show();
                                    loadBook();
                                } else {
                                    new Alert(Alert.AlertType.ERROR, "Book Delete Failure...!").show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    bookList.add(book);
                }
            }
            generateBookID();
            tblBook.setItems(bookList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectValue(MouseEvent mouseEvent){
        BookDto selectedBook = tblBook.getSelectionModel().getSelectedItem();
        if(selectedBook != null){
            txtBookID.setText(selectedBook.getBookID());
            txtTitle.setText(selectedBook.getTitle());
            txtAuthor.setText(selectedBook.getAuthor());
            txtISBN.setText(selectedBook.getIsbn());
            txtQoh.setText(String.valueOf(selectedBook.getQoh()));
            txtUnitPrice.setText(String.valueOf(selectedBook.getUnitPrice()));
            cmbBookCategory.setValue(selectedBook.getCategoryID());
            btnSaveBook.setText("Update Book");
        }
    }

    private void setCategory() throws Exception {
        ArrayList<CategoryEntity> categoryList = categoryDao.getAll();
        for (CategoryEntity category : categoryList) {
            cmbBookCategory.getItems().add(category.getCatId()+". "+category.getCatName());
        }
    }

    private void generateBookID() {
        try {
            String bookID = bookService.getLastID();
            if (bookID != null) {
                String[] split = bookID.split("-");
                String lastIDAsString = split[1];
                int lastIDAsInteger = Integer.parseInt(lastIDAsString);
                lastIDAsInteger++;
                String newID = String.format("B-%03d", lastIDAsInteger);
                txtBookID.setText(newID);
            } else {
                txtBookID.setText("B-001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) context.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.show();
        stage.centerOnScreen();
    }

    private void clear(){
        txtBookID.clear();
        txtTitle.clear();
        txtAuthor.clear();
        txtISBN.clear();
        txtQoh.clear();
        txtUnitPrice.clear();
        cmbBookCategory.getSelectionModel().clearSelection();
    }

}
