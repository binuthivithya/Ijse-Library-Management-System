package edu.ijse.UIController;

import edu.ijse.dao.custom.impl.BookDaoImpl;
import edu.ijse.dto.BorrowingDto;
import edu.ijse.dto.MemberDto;
import edu.ijse.entity.BookEntity;
import edu.ijse.service.custom.impl.BookServiceImpl;
import edu.ijse.service.custom.impl.BorrowingServiceImpl;
import edu.ijse.service.custom.impl.MemberServiceImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class BorrowingFormController {
    public AnchorPane context;
    public TextField txtBorrowID;
    public Button btnSaveBorrowing;
    public ComboBox<String> cmbMember;
    public ComboBox<String> cmbBook;
    public TableView<BorrowingDto> tblBorrow;
    public TableColumn<BorrowingDto, String> colBorrowID;
    public TableColumn<BorrowingDto, String> colMember;
    public TableColumn<BorrowingDto, String> colBook;
    public TableColumn<BorrowingDto, Double> colPrice;
    public TableColumn<BorrowingDto, Date> colBorrowDate;
    public TableColumn<BorrowingDto, Date> colDueDate;

    private final BorrowingServiceImpl borrowingService = new BorrowingServiceImpl();
    private final ObservableList<BorrowingDto> borrowingList = FXCollections.observableArrayList();
    private final MemberServiceImpl memberService = new MemberServiceImpl();
    private final BookDaoImpl bookDao = new BookDaoImpl();
    private final BookServiceImpl bookService = new BookServiceImpl();

    public void initialize() throws Exception {
        colBorrowID.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        colMember.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getMemberId() + " | " + data.getValue().getMemberName()));

        colBook.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getBookId() + " | " + data.getValue().getBookName()));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        generateBorrowingID();
        loadBorrowing();

        setMembers();
        setBooks();
    }

    public void newBorrowingOnAction(ActionEvent actionEvent) {
        clear();
        generateBorrowingID();
    }

    public void backToDashOnAction(ActionEvent actionEvent) throws IOException {
        setUI("Dashboard");
    }


    public void saveBorrowingOnAction(ActionEvent actionEvent) throws Exception {

        if (cmbMember.getValue() ==  null) {
            new Alert(Alert.AlertType.ERROR, "Please Select a Member...!").show();
            return;
        }

        if (cmbBook.getValue() ==  null) {
            new Alert(Alert.AlertType.ERROR, "Please Select a Book...!").show();
            return;
        }

        Date borrowingDate = new Date();
        Date returnDate = Date.from(LocalDate.now().plusWeeks(2).atStartOfDay(ZoneId.systemDefault()).toInstant());

        BorrowingDto borrowing = new BorrowingDto(
                txtBorrowID.getText(),
                borrowingDate,
                returnDate,
                cmbMember.getValue().split("\\|")[0],
                cmbMember.getValue().split("\\|")[1],
                cmbBook.getValue().split("\\|")[0],
                cmbBook.getValue().split("\\|")[1],
                bookService.getBook(cmbBook.getValue().split("\\|")[0]).getUnitPrice(),
                false
        );
        try {
            String result = borrowingService.issueBook(borrowing);
            if ("Success".equals(result)) {
                borrowingList.add(borrowing);
                tblBorrow.refresh();
                clear();
                loadBorrowing();
                new Alert(Alert.AlertType.INFORMATION, "Borrowing Saved Successfully...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, result).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            generateBorrowingID();
        }
    }

    private void loadBorrowing() {
        try {
            ArrayList<BorrowingDto> borrowings = borrowingService.getAllNotReturned();
            borrowingList.clear();
            if (borrowings != null) {
                borrowingList.setAll(borrowings);
                tblBorrow.setItems(borrowingList);
                generateBorrowingID();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateBorrowingID() {
        try {
            String borrowingID = borrowingService.getLastID();

            if (borrowingID != null) {
                String[] split = borrowingID.split("-");
                String lastIDAsString = split[1];
                int lastIDAsInteger = Integer.parseInt(lastIDAsString);
                lastIDAsInteger++;
                String newID = String.format("BR-%03d", lastIDAsInteger);
                txtBorrowID.setText(newID);
            } else {
                txtBorrowID.setText("BR-001");
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
        txtBorrowID.clear();
        cmbMember.getSelectionModel().clearSelection();
        cmbBook.getSelectionModel().clearSelection();
    }

    private void setMembers() throws Exception {
        ArrayList<MemberDto> memberList = memberService.getAll();
        for (MemberDto member : memberList) {
            cmbMember.getItems().add(member.getMemberID()+" |  "+member.getName());
        }
    }

    private void setBooks() throws Exception {
        ArrayList<BookEntity> bookList = bookDao.getAll();
        for (BookEntity book : bookList) {
            cmbBook.getItems().add(book.getBookID()+" |  "+book.getTitle());
        }
    }
}
