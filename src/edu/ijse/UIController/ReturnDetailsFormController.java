package edu.ijse.UIController;

import edu.ijse.dto.BorrowingDto;
import edu.ijse.dto.ReturnDto;
import edu.ijse.service.custom.impl.ReturnServiceImpl;
import edu.ijse.view.onReturnComplete;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class ReturnDetailsFormController {
    public AnchorPane context;
    public Button btnReturn;
    public TextField txtReturnID;
    public TextField txtBorrowID;
    public TextField txtMemberID;
    public TextField txtMemberName;
    public TextField txtBookID;
    public TextField txtBookTitle;
    public TextField txtBorrowDate;
    public TextField txtDueDate;
    public TextField txtReturnDate;
    public TextField txtFine;
    public CheckBox txtFinePaid;

    private final ReturnServiceImpl returnService = new ReturnServiceImpl();

    private onReturnComplete listener;

    public void initialize(){
        generateReturnID();
    }

    public void setOnReturnCompletedListener(onReturnComplete listener) {
        this.listener = listener;
    }

    public void returnOnAction(ActionEvent actionEvent) throws Exception {
        boolean isPaid = txtFinePaid.isSelected();
        ReturnDto returnDto = new ReturnDto(
                txtReturnID.getText(),
                java.sql.Date.valueOf(LocalDate.parse(txtReturnDate.getText())),
                Double.parseDouble(txtFine.getText()),
                txtBorrowID.getText(),
                txtMemberID.getText(),
                txtMemberName.getText(),
                txtBookID.getText(),
                txtBookTitle.getText(),
                java.sql.Date.valueOf(LocalDate.parse(txtDueDate.getText())),
                isPaid
        );

        returnService.returnBook(returnDto);

        if (listener != null) {
            listener.onReturnCompleted();
        }

        Stage stage = (Stage) context.getScene().getWindow();
        stage.close();
    }

    public void initData(BorrowingDto borrowing) {
        txtBorrowID.setText(borrowing.getBorrowId());
        txtMemberID.setText(borrowing.getMemberId());
        txtMemberName.setText(borrowing.getMemberName());
        txtBookID.setText(borrowing.getBookId());
        txtBookTitle.setText(borrowing.getBookName());

        // Convert java.sql.Date to java.util.Date, then to LocalDate
        java.util.Date utilDate = new java.util.Date(borrowing.getBorrowDate().getTime());
        LocalDate borrowDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        txtBorrowDate.setText(borrowDate.toString());

        // Assuming due date is 14 days after borrow date (adjust as needed)
        LocalDate dueDate = borrowDate.plusDays(14);
        txtDueDate.setText(dueDate.toString());

        // Set return date as current date
        LocalDate returnDate = LocalDate.now();
        txtReturnDate.setText(returnDate.toString());

        // Calculate fine
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        // Assuming Rs. 50 per day late as the fine
        double fine = daysLate > 0 ? daysLate * 50.0 : 0;
        txtFine.setText(String.format("%.2f", fine));

        // Initially set fine as unpaid
        txtFinePaid.setSelected(false);
    }

    private void generateReturnID() {
        try {
            String bookID = returnService.getLastId();
            if (bookID != null) {
                String[] split = bookID.split("-");
                String lastIDAsString = split[1];
                int lastIDAsInteger = Integer.parseInt(lastIDAsString);
                lastIDAsInteger++;
                String newID = String.format("R-%03d", lastIDAsInteger);
                txtReturnID.setText(newID);
            } else {
                txtReturnID.setText("R-001");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
