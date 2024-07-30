package edu.ijse.UIController;

import edu.ijse.dto.BorrowingDto;
import edu.ijse.dto.ReturnDto;
import edu.ijse.service.custom.impl.BorrowingServiceImpl;
import edu.ijse.service.custom.impl.ReturnServiceImpl;
import edu.ijse.view.onReturnComplete;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ReturnFormController implements onReturnComplete {
    public AnchorPane context;
    public TableView<ReturnDto> tblReturn;
    public TableColumn<ReturnDto, String> colReturnID;
    public TableColumn<ReturnDto, String> colBorrowID;
    public TableColumn<ReturnDto, String> colMemberID;
    public TableColumn<ReturnDto, String> colMemberName;
    public TableColumn<ReturnDto, String> colBookID;
    public TableColumn<ReturnDto, String> colBookTitle;
    public TableColumn<ReturnDto, Date> colDueDate;
    public TableColumn<ReturnDto, Double> colFine;
    public TableColumn<ReturnDto, Boolean> colIsFinePaid;
    public TextField txtSearch;

    public TableView<BorrowingDto> tblBorrowDetails;
    public TableColumn<BorrowingDto, String> colBorrowDetailsID;
    public TableColumn<BorrowingDto, String> colBorrowMemberID;
    public TableColumn<BorrowingDto, String> colBorrowMemberName;
    public TableColumn<BorrowingDto, String> colBorrowBookID;
    public TableColumn<BorrowingDto, String> colBorrowBookName;
    public TableColumn<BorrowingDto, Date> colBurrowedDate;

    private final ReturnServiceImpl returnService = new ReturnServiceImpl();
    private final ObservableList<ReturnDto> returnList = FXCollections.observableArrayList();
    private final BorrowingServiceImpl borrowingService = new BorrowingServiceImpl();
    private final ObservableList<BorrowingDto> borrowingList = FXCollections.observableArrayList();

    public void initialize(){

        colReturnID.setCellValueFactory(new PropertyValueFactory<>("returnId"));
        colBorrowID.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        colMemberID.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colMemberName.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colFine.setCellValueFactory(new PropertyValueFactory<>("fine"));

        colIsFinePaid.setCellValueFactory(new PropertyValueFactory<>("isPaid"));
        colIsFinePaid.setCellFactory(tc -> new TableCell<ReturnDto, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Paid" : "Not Paid");
                }
            }
        });

        colBorrowDetailsID.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        colBorrowMemberID.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        colBorrowMemberName.setCellValueFactory(new PropertyValueFactory<>("memberName"));
        colBorrowBookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colBorrowBookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colBurrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));

        loadReturn();
        loadBorrowing();

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                loadBorrowing();
                return;
            }
            searchBorrowings(newValue);
        });

        tblBorrowDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                openReturnDetailsForm(newValue);
            }
        });
    }

    public void newBookReturnOnAction(ActionEvent actionEvent) {
        clear();
    }

    public void backToDashOnAction(ActionEvent actionEvent) throws IOException {
        setUI("Dashboard");
    }

    private void searchBorrowings(String searchText) {
        searchText = "%" + searchText + "%";
        try {
            ArrayList<BorrowingDto> borrowingDtos = borrowingService.search(searchText);
            if (borrowingDtos != null && !borrowingDtos.isEmpty()) {
                borrowingList.setAll(borrowingDtos);
                tblBorrowDetails.setItems(borrowingList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadBorrowing() {
        try{
            ArrayList<BorrowingDto> borrowingDtos = borrowingService.getAllNotReturned();
            if (borrowingDtos != null && !borrowingDtos.isEmpty()) {
                borrowingList.setAll(borrowingDtos);
                tblBorrowDetails.setItems(borrowingList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadReturn() {
        try {
            ArrayList<ReturnDto> returns = returnService.getAll();
            returnList.clear();
            if (returns != null && !returns.isEmpty()) {
                returnList.setAll(returns);
                tblReturn.setItems(returnList);
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
        txtSearch.clear();
    }

    private void openReturnDetailsForm(BorrowingDto selectedBorrowing) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ReturnDetailsForm.fxml"));
            Parent root = loader.load();

            ReturnDetailsFormController controller = loader.getController();
            controller.initData(selectedBorrowing);
            controller.setOnReturnCompletedListener(this);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onReturnCompleted() {
        borrowingList.clear();
        loadBorrowing();
        loadReturn();
    }
}
