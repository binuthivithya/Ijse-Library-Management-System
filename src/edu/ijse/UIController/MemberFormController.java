package edu.ijse.UIController;

import edu.ijse.dto.MemberDto;
import edu.ijse.service.custom.impl.MemberServiceImpl;
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
import java.util.Date;
import java.util.Optional;

public class MemberFormController {
    public AnchorPane context;
    public TextField txtMemberID;
    public TextField txtName;
    public TextField txtContact;
    public TextField txtEmail;
    public TextField txtAddress;
    public TableView<MemberDto> tblMember;
    public TableColumn<MemberDto, String> colMemberID;
    public TableColumn<MemberDto, String>  colName;
    public TableColumn<MemberDto, String>  colAddress;
    public TableColumn<MemberDto, String>  colContact;
    public TableColumn<MemberDto, String>  colEmail;
    public TableColumn<MemberDto, Date>  colDate;
    public TableColumn<MemberDto, Button>  colOption;
    public Button btnSaveMember;

    private final MemberServiceImpl memberService = new MemberServiceImpl();
    private final ObservableList<MemberDto> memberList = FXCollections.observableArrayList();;

    public void initialize() {
        colMemberID.setCellValueFactory(new PropertyValueFactory<>("memberID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("membershipDate"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));

        generateBookID();
        loadMember();

        tblMember.setOnMouseClicked(this::selectValue);
    }

    public void saveMemberOnAction(ActionEvent actionEvent) {
        //validate the email address
        if (!txtEmail.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Email Address...!").show();
            return;
        }

        if (txtName.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Name cannot be empty...!").show();
            return;
        }

        Button button = new Button("Remove");
        if (btnSaveMember.getText().equalsIgnoreCase("Save Member")) {
            MemberDto member = new MemberDto(
                    txtMemberID.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtContact.getText(),
                    txtEmail.getText(),
                    new Date(),
                    button
            );
            try {
                String result = memberService.save(member);
                if ("Success".equals(result)) {
                    memberList.add(member);
                    clear();
                    loadMember();
                    new Alert(Alert.AlertType.INFORMATION, "Member Saved Successfully...!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Member Save Failure..!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MemberDto member = new MemberDto(
                    txtMemberID.getText(),
                    txtName.getText(),
                    txtAddress.getText(),
                    txtContact.getText(),
                    txtEmail.getText(),
                    new Date(),
                    button
            );
            try {
                String result = memberService.update(member);
                if ("Success".equals(result)) {
                    loadMember();
                    clear();
                    btnSaveMember.setText("Save Member");
                    new Alert(Alert.AlertType.INFORMATION, "Member Updated Successfully...!").show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Member Update Failure..!").show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        generateBookID();
    }

    public void backToDashOnAction(ActionEvent actionEvent) throws IOException {
        setUI("Dashboard");
    }

    public void newBookOnAction(ActionEvent actionEvent) {
        clear();
        generateBookID();
        btnSaveMember.setText("Save Member");
    }

    private void loadMember() {
        try {
            ArrayList<MemberDto> members = memberService.getAll();
            memberList.clear();
            if(members != null && !members.isEmpty()) {
                for (MemberDto member : members) {
                    member.getButton().cursorProperty().set(javafx.scene.Cursor.HAND);
                    member.getButton().setOnAction(event -> { // setting up the remove button to remove the records.
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                "Are you sure...?", ButtonType.YES, ButtonType.NO);
                        Optional<ButtonType> buttonType = alert.showAndWait();
                        if (buttonType.get().equals(ButtonType.YES)) {
                            try {
                                String result = memberService.delete(member.getMemberID());
                                if ("Success".equals(result)) {
                                    new Alert(Alert.AlertType.INFORMATION, "Member Deleted Successfully...").show();
                                    loadMember();
                                } else {
                                    new Alert(Alert.AlertType.ERROR, "Member Delete Failure...!").show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    memberList.add(member);
                }
            }
            generateBookID();
            tblMember.setItems(memberList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectValue(MouseEvent mouseEvent) {
        MemberDto selectedMember = tblMember.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            txtMemberID.setText(selectedMember.getMemberID());
            txtName.setText(selectedMember.getName());
            txtAddress.setText(selectedMember.getAddress());
            txtContact.setText(selectedMember.getContact());
            txtEmail.setText(selectedMember.getEmail());
            btnSaveMember.setText("Update Member");

        }
    }

    private void generateBookID() {
        try {
            String bookID = memberService.getLastID();
            if (bookID != null) {
                String[] split = bookID.split("-");
                String lastIDAsString = split[1];
                int lastIDAsInteger = Integer.parseInt(lastIDAsString);
                lastIDAsInteger++;
                String newID = String.format("M-%03d", lastIDAsInteger);
                txtMemberID.setText(newID);
            } else {
                txtMemberID.setText("M-001");
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
        txtMemberID.clear();
        txtName.clear();
        txtContact.clear();
        txtEmail.clear();
        txtAddress.clear();
    }
}
