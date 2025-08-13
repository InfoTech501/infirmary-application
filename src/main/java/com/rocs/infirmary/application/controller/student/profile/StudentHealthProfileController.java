package com.rocs.infirmary.application.controller.student.profile;

import com.rocs.infirmary.application.data.model.person.employee.Employee;
import com.rocs.infirmary.application.module.student.profile.StudentHealthProfileApplication;
import com.rocs.infirmary.application.data.model.person.student.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Controller for the Student Health Profile.
 * Displays common students data and allows selection of records.
 * It also allows the filtering of the students records.
 * Implements Initializable interface.
 */
public class StudentHealthProfileController implements Initializable {

    // table
    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student, String> lrnColumn;
    @FXML
    private TableColumn<Student, String> firstNameColumn;
    @FXML
    private TableColumn<Student, String> lastNameColumn;
    @FXML
    private TableColumn<Student, String> gradeColumn;
    @FXML
    private TableColumn<Student, String> sectionColumn;
    @FXML
    private TableColumn<Student, String> genderColumn;
    @FXML
    private TableColumn<Student, String> ageColumn;

    // search
    @FXML
    private TextField searchTextField;

    // control buttons
    @FXML
    private ComboBox<String> sectionComboBox, sexComboBox;
    @FXML
    private Button ageFilterBtn, aToZFilterBtn, zToAFilterBtn, clearFilterBtn;
    @FXML
    private StackPane rootStackPane;

    //pagination
    @FXML
    public ComboBox<Integer> rowsPerPageComboBox;
    @FXML
    public Label paginationLabel;
    @FXML
    public Label rowsPageLabel;
    @FXML
    public ToggleButton togglePrevBtn, toggleNextBtn;

    private int rowsPerPage = 10;
    private int currentPage = 1;

    private final ObservableList<Student> masterStudentList = FXCollections.observableArrayList();

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentHealthProfileController.class);
    private final StudentHealthProfileApplication studentHealthProfileApplication = new StudentHealthProfileApplication();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCollections();
        setupEventHandlers();
        setupFiltering();
        populateTableList();
        loadData();
    }

    private void initializeCollections() {
        FilteredList<Student> filteredList = new FilteredList<>(masterStudentList);
        SortedList<Student> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(studentTableView.comparatorProperty());
        studentTableView.setItems(sortedList);
    }

    private void setupEventHandlers() {
        aToZFilterBtn.setOnAction(event -> sortAToZ());
        zToAFilterBtn.setOnAction(event -> sortZToA());
        ageFilterBtn.setOnAction(event -> sortByAge());
        clearFilterBtn.setOnAction(event -> clearFilter());
        togglePrevBtn.setOnAction(event -> handlePrevPage());
        toggleNextBtn.setOnAction(event -> handleNextPage());

        studentTableView.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {
                    try
                    {
                        onClickShowMoreInformation(row.getItem());

                    } catch (IOException e) {
                        LOGGER.error("Row selection failure", e);
                    }
                }
            });
            return row;
        });

        setupRowsPerPageSelector();
    }

    private void setupRowsPerPageSelector() {
        rowsPerPageComboBox.setItems(FXCollections.observableArrayList(5,10,15,20));
        rowsPerPageComboBox.setValue(rowsPerPage);
        rowsPerPageComboBox.setOnAction(event -> {
            Integer selected = rowsPerPageComboBox.getValue();
            if (selected != null) {
                rowsPerPage = selected;
                currentPage = 1;
                updatePage();
            }
        });
    }

    /**
     * A public method which populates student tableview columns by mapping each table column to the corresponding Student object properties.
     */
    public void populateTableList() {
        studentTableView.setEditable(true);

        lrnColumn.setCellValueFactory(new PropertyValueFactory<>("lrn"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("gradeLevel"));
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("section"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        firstNameColumn.setCellFactory(createCapitalizingCellFactory());
        lastNameColumn.setCellFactory(createCapitalizingCellFactory());
        gradeColumn.setCellFactory(createCapitalizingCellFactory());
        sectionColumn.setCellFactory(createCapitalizingCellFactory());
        genderColumn.setCellFactory(createCapitalizingCellFactory());
        LOGGER.info("Populating student table");
    }

    /**
     * A method which loads the students' health records.
     */
    public void loadData() {
        try {
            List<Student> fullStudentList = studentHealthProfileApplication.getStudentHealthProfileFacade().getAllStudentHealthProfile();
            masterStudentList.setAll(fullStudentList);
            populateComboBoxes(fullStudentList);
            updatePage();
            LOGGER.info("Fetching records successful");
        } catch (NullPointerException e) {
            LOGGER.error("Null pointer exception{}", String.valueOf(e));
        }
    }

    private void updatePage() {
        List<Student> filtered = masterStudentList.stream().filter(this::applyAllFilters).toList();
        int total = filtered.size();
        int fromIndex = Math.max(0, (currentPage - 1) * rowsPerPage);
        int toIndex = Math.min(fromIndex + rowsPerPage, total);

        List<Student> pageData = filtered.subList(fromIndex, toIndex);
        ObservableList<Student> pageItems = FXCollections.observableArrayList(pageData);
        studentTableView.setItems(pageItems);

        paginationLabel.setText((fromIndex + 1) + " - " + toIndex + " of " + total);
        rowsPageLabel.setText(String.valueOf(pageItems.size()));
    }

    private void setupFiltering() {
        searchTextField.textProperty().addListener((obs, oldVal, newVal) -> {currentPage = 1; updatePage();});
        sectionComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {currentPage = 1; updatePage();});
        sexComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {currentPage = 1; updatePage();});
        LOGGER.info("Filtering system configured");
    }

    private boolean applyAllFilters(Student student) {
        String searchText = searchTextField.getText();
        if (searchText != null && !searchText.trim().isEmpty()) {
            String searchLower = searchText.toLowerCase().trim();
            boolean matchesSearch = student.getFirstName().toLowerCase().contains(searchLower) || student.getLastName().toLowerCase().contains(searchLower) || String.valueOf(student.getLrn()).contains(searchLower);
            if (!matchesSearch) return false;
        }

        String selectedSection = sectionComboBox.getValue();
        if (selectedSection != null && !selectedSection.equals("All Sections")) {
            String studentSectionCapitalized = firstLetterAutoCapitalization(student.getSection());
            if (!selectedSection.equals(studentSectionCapitalized)) {
                return false;
            }
        }

        String selectedGender = sexComboBox.getValue();
        if (selectedGender != null && !selectedGender.equals("All Genders"))
        {
            return selectedGender.equalsIgnoreCase(student.getGender());
        }
        return true;
    }

    private <T> Callback<TableColumn<T, String>, TableCell<T, String>> createCapitalizingCellFactory() {
        return column -> new TableCell<>() {
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null)
                {
                    setText(null);
                }
                else {
                    setText(firstLetterAutoCapitalization(item));
                }
            }
        };
    }

    /**
     * A utility that capitalizes first letter of any word.
     */
    public String firstLetterAutoCapitalization(String input) {
        if (input == null || input.isEmpty())
        {
            return input;
        }
        return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
    }

    private void onClickShowMoreInformation(Student selectedStudent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/StudentHealthProfileModal.fxml"));
            loader.setControllerFactory(param -> new StudentHealthProfileModalController(this));
            Parent root = loader.load();

            StudentHealthProfileModalController controller = loader.getController();
            controller.setSelectedStudent(selectedStudent);

            rootStackPane.getChildren().add(root);
            LOGGER.info("Showing more info modal");
        } catch (Exception e) {
            LOGGER.error("Showing more information exception{}", String.valueOf(e));
        }
    }

    private void handlePrevPage() {
        if (currentPage > 1) {
            currentPage--;
            updatePage();
        }
    }

    private void handleNextPage() {
        int total = (int) masterStudentList.stream().filter(this::applyAllFilters).count();
        int maxPage = (int) Math.ceil((double) total / rowsPerPage);
        if (currentPage < maxPage) {
            currentPage++;
            updatePage();
        }
    }

    private void populateComboBoxes(List<Student> students) {
        Set<String> sections = students.stream().map(Student::getSection).filter(section -> section != null && !section.trim().isEmpty()).map(this::firstLetterAutoCapitalization).collect(Collectors.toSet());
        ObservableList<String> sectionItems = FXCollections.observableArrayList();
        sectionItems.add("All Sections");
        sectionItems.addAll(sections.stream().sorted().toList());
        sectionComboBox.setItems(sectionItems);
        sectionComboBox.getSelectionModel().selectFirst();

        ObservableList<String> genderItems = FXCollections.observableArrayList("All Genders","Male","Female");
        sexComboBox.setItems(genderItems);
        sexComboBox.getSelectionModel().selectFirst();
    }

    private void sortAToZ() {
        studentTableView.getSortOrder().clear();
        lastNameColumn.setSortType(TableColumn.SortType.ASCENDING);
        studentTableView.getSortOrder().add(lastNameColumn);
        LOGGER.info("Table sorted to A to Z");
    }

    private void sortZToA() {
        studentTableView.getSortOrder().clear();
        lastNameColumn.setSortType(TableColumn.SortType.DESCENDING);
        studentTableView.getSortOrder().add(lastNameColumn);
        LOGGER.info("Table sorted to Z to A");
    }

    private void sortByAge() {
        studentTableView.getSortOrder().clear();
        ageColumn.setSortType(TableColumn.SortType.ASCENDING);
        studentTableView.getSortOrder().add(ageColumn);
        LOGGER.info("Table sorted by Age");
    }

    private void clearFilter() {
        sectionComboBox.getSelectionModel().selectFirst();
        sexComboBox.getSelectionModel().selectFirst();
        searchTextField.clear();
        studentTableView.getSortOrder().clear();
        currentPage = 1;
        updatePage();
        LOGGER.info("Sorting cleared");
    }
}
