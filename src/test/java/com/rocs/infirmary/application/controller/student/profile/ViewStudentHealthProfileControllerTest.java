package com.rocs.infirmary.application.controller.student.profile;

import com.rocs.infirmary.application.data.model.person.student.Student;
<<<<<<< HEAD
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
=======
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
>>>>>>> a36da81 (updated unit test for view student health profile)
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
<<<<<<< HEAD
=======
import org.mockito.MockitoAnnotations;
>>>>>>> a36da81 (updated unit test for view student health profile)
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
<<<<<<< HEAD
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
=======
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
>>>>>>> a36da81 (updated unit test for view student health profile)

@ExtendWith(ApplicationExtension.class)
class ViewStudentHealthProfileControllerTest {

<<<<<<< HEAD
    private TableView<Student> studentTableView;
    private TextField searchTextField;
    private ComboBox<String> sectionComboBox;
    private ComboBox<String> sexComboBox;
    private Button clearFilterBtn;


    private static final int MOCK_STUDENT_COUNT = 2;
=======
    private StudentHealthProfileController controller;

    private TableView<Student> studentTableView;
    private TableColumn<Student, String> lrnColumn;
    private TableColumn<Student, String> firstNameColumn;
    private TableColumn<Student, String> lastNameColumn;
    private TableColumn<Student, String> gradeColumn;
    private TableColumn<Student, String> sectionColumn;
    private TableColumn<Student, String> genderColumn;
    private TableColumn<Student, String> ageColumn;

    private TextField searchTextField;

    private ComboBox<String> sectionComboBox;
    private ComboBox<String> sexComboBox;

    private Button ageFilterBtn;
    private Button aToZFilterBtn;
    private Button zToAFilterBtn;
    private Button clearFilterBtn;

    private StackPane rootStackPane;

    private ComboBox<Integer> rowsPerPageComboBox;
    private Label paginationLabel;
    private Label rowsPageLabel;
    private ToggleButton togglePrevBtn;
    private ToggleButton toggleNextBtn;

    @BeforeEach
    void setupMocks() {
        MockitoAnnotations.openMocks(this);
    }
>>>>>>> a36da81 (updated unit test for view student health profile)

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/StudentHealthProfile.fxml"));
<<<<<<< HEAD
        BorderPane mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.show();

        stage.toFront();
    }

    @BeforeEach
    void setup(FxRobot robot) throws TimeoutException {
        studentTableView = robot.lookup("#studentTableView").queryAs(TableView.class);
        searchTextField = robot.lookup("#searchTextField").queryAs(TextField.class);
        sectionComboBox = robot.lookup("#sectionComboBox").queryAs(ComboBox.class);
        sexComboBox = robot.lookup("#sexComboBox").queryAs(ComboBox.class);
        clearFilterBtn = robot.lookup("#clearFilterBtn").queryAs(Button.class);

        assertNotNull(studentTableView, "studentTableView should not be null");
        assertNotNull(searchTextField, "searchTextField should not be null");
        assertNotNull(sectionComboBox, "sectionComboBox should not be null");
        assertNotNull(sexComboBox, "sexComboBox should not be null");
        assertNotNull(clearFilterBtn, "clearFilterBtn should not be null");


        robot.interact(() -> {
            searchTextField.clear();
            sectionComboBox.getSelectionModel().selectFirst();
            sexComboBox.getSelectionModel().selectFirst();
            studentTableView.getItems().clear();
        });
    }

   // @Disabled
    @Test
    void testProfilesDisplayed(FxRobot robot) {

        loadMockStudents(robot);

        robot.interact(() -> {
            assertEquals(MOCK_STUDENT_COUNT, studentTableView.getItems().size(), "Should display all mock students after loading.");
            assertFalse(studentTableView.getItems().isEmpty(), "Student TableView should not be empty.");
            assertFalse(studentTableView.getColumns().isEmpty(), "Student TableView should have columns.");
        });
    }

    private void loadMockStudents(FxRobot robot) {
        ObservableList<Student> mockStudents = FXCollections.observableArrayList();


        robot.interact(() -> {

            Student s1 = new Student();
            s1.setLrn("2024001");
            s1.setFirstName("Juan");
            s1.setLastName("Dela Cruz");
            s1.setGradeLevel("10");
            s1.setSection("A");
            s1.setGender("Male");
            s1.setAge(15);


            Student s2 = new Student();
            s2.setLrn("2024002");
            s2.setFirstName("Maria");
            s2.setLastName("Santos");
            s2.setGradeLevel("9");
            s2.setSection("B");
            s2.setGender("Female");
            s2.setAge(14);

            mockStudents.addAll(s1, s2);


            studentTableView.setItems(mockStudents);
        });
    }

    //@Disabled
    @Test
    void testSearchByName(FxRobot robot) {
        loadMockStudents(robot); // Load data first!

        final String searchName = "Juan";
        robot.clickOn(searchTextField);
        robot.write(searchName);

        robot.sleep(500);

        assertEquals(searchName, searchTextField.getText(), "Search field should contain the written name.");

        robot.interact(() -> {
            assertEquals(1, studentTableView.getItems().size(), "Only one student should match the name search.");
            assertEquals("Juan", studentTableView.getItems().get(0).getFirstName(), "The displayed student should be Juan.");
        });
    }

    //@Disabled
    @Test
    void testSearchByLRN(FxRobot robot) {
        loadMockStudents(robot);

        final String searchLRN = "2024002";
        robot.clickOn(searchTextField);
        robot.write(searchLRN);

        robot.sleep(500); // Give time for the filter listener to fire

        assertEquals(searchLRN, searchTextField.getText(), "Search field should contain the written LRN.");

        robot.interact(() -> {
            assertEquals(1, studentTableView.getItems().size(), "Only one student should match the LRN search.");
            assertEquals("Maria", studentTableView.getItems().get(0).getFirstName(), "The displayed student should be Maria.");
        });
    }

    //@Disabled
    @Test
    void testFilterBySection(FxRobot robot) {
        loadMockStudents(robot);

        robot.interact(() -> {
            sectionComboBox.setItems(FXCollections.observableArrayList("All Sections", "A", "B"));
            sectionComboBox.getSelectionModel().select("A");
        });

        final String selectedSection = "A";
        robot.sleep(500);


        assertEquals(selectedSection, sectionComboBox.getValue(), "Section ComboBox should be set to 'A'.");

        robot.interact(() -> {
            assertEquals(1, studentTableView.getItems().size(), "Only one student should match the section filter.");
            assertEquals("Juan", studentTableView.getItems().get(0).getFirstName(), "The displayed student should be Juan (Section A).");
        });
    }

    //@Disabled
    @Test
    void testFilterByGender(FxRobot robot) {
        loadMockStudents(robot);


        robot.interact(() -> {
            sexComboBox.setItems(FXCollections.observableArrayList("All Genders", "Male", "Female"));
            sexComboBox.getSelectionModel().select("Female");
        });

        final String selectedGender = "Female";

        robot.sleep(500);


        assertEquals(selectedGender, sexComboBox.getValue(), "Gender ComboBox should be set to 'Female'.");
        robot.interact(() -> {
            assertEquals(1, studentTableView.getItems().size(), "Only one student should match the gender filter.");
            assertEquals("Maria", studentTableView.getItems().get(0).getFirstName(), "The displayed student should be Maria (Female).");
        });
    }

    //@Disabled
    @Test
    void testClearFilter(FxRobot robot) {
        loadMockStudents(robot);

        robot.interact(() -> {
            searchTextField.setText("Juan");
            sectionComboBox.setItems(FXCollections.observableArrayList("All Sections", "A", "B"));
            sectionComboBox.getSelectionModel().select("A");
            sexComboBox.setItems(FXCollections.observableArrayList("All Genders", "Male", "Female"));
            sexComboBox.getSelectionModel().select("Male");
        });


        robot.sleep(500);


        robot.clickOn(clearFilterBtn);


        robot.sleep(500);


        assertEquals("", searchTextField.getText(), "Search field should be cleared.");
        assertEquals("All Sections", sectionComboBox.getValue(), "Section filter should be reset to 'All Sections'.");
        assertEquals("All Genders", sexComboBox.getValue(), "Gender filter should be reset to 'All Genders'.");

        robot.interact(() -> {
            assertEquals(MOCK_STUDENT_COUNT, studentTableView.getItems().size(), "All students should be displayed after clearing the filter.");
        });
=======
        loader.setControllerFactory(param -> {
            controller = new StudentHealthProfileController();
            return controller;
        });

        StackPane mainLayout = loader.load();
        Scene scene = new Scene(mainLayout);
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    void setup(FxRobot robot) throws Exception {
        studentTableView = robot.lookup("#studentTableView").queryAs(TableView.class);
        lrnColumn = robot.lookup("#lrnColumn").queryAs(TableColumn.class);
        firstNameColumn = robot.lookup("#firstNameColumn").queryAs(TableColumn.class);
        lastNameColumn = robot.lookup("#lastNameColumn").queryAs(TableColumn.class);
        gradeColumn = robot.lookup("#gradeColumn").queryAs(TableColumn.class);
        sectionColumn = robot.lookup("#sectionColumn").queryAs(TableColumn.class);
        genderColumn = robot.lookup("#genderColumn").queryAs(TableColumn.class);
        ageColumn = robot.lookup("#ageColumn").queryAs(TableColumn.class);

        searchTextField = robot.lookup("#searchTextField").queryAs(TextField.class);

        sectionComboBox = robot.lookup("#sectionComboBox").queryAs(ComboBox.class);
        sexComboBox = robot.lookup("#sexComboBox").queryAs(ComboBox.class);

        ageFilterBtn = robot.lookup("#ageFilterBtn").queryAs(Button.class);
        aToZFilterBtn = robot.lookup("#aToZFilterBtn").queryAs(Button.class);
        zToAFilterBtn = robot.lookup("#zToAFilterBtn").queryAs(Button.class);
        clearFilterBtn = robot.lookup("#clearFilterBtn").queryAs(Button.class);

        rootStackPane = robot.lookup("#rootStackPane").queryAs(StackPane.class);

        rowsPerPageComboBox = robot.lookup("#rowsPerPageComboBox").queryAs(ComboBox.class);
        paginationLabel = robot.lookup("#paginationLabel").queryAs(Label.class);
        rowsPageLabel = robot.lookup("#rowsPageLabel").queryAs(Label.class);
        togglePrevBtn = robot.lookup("#togglePrevBtn").queryAs(ToggleButton.class);
        toggleNextBtn = robot.lookup("#toggleNextBtn").queryAs(ToggleButton.class);

        assertNotNull(studentTableView);
        assertNotNull(lrnColumn);
        assertNotNull(firstNameColumn);
        assertNotNull(lastNameColumn);
        assertNotNull(gradeColumn);
        assertNotNull(sectionColumn);
        assertNotNull(genderColumn);
        assertNotNull(ageColumn);
        assertNotNull(searchTextField);
        assertNotNull(sectionComboBox);
        assertNotNull(sexComboBox);
        assertNotNull(ageFilterBtn);
        assertNotNull(aToZFilterBtn);
        assertNotNull(zToAFilterBtn);
        assertNotNull(clearFilterBtn);
        assertNotNull(rootStackPane);
        assertNotNull(rowsPerPageComboBox);
        assertNotNull(paginationLabel);
        assertNotNull(rowsPageLabel);
        assertNotNull(togglePrevBtn);
        assertNotNull(toggleNextBtn);

        robot.interact(() -> {
            try {
                setupTestData();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setupTestData() throws Exception {
        Student student1 = mock(Student.class);
        when(student1.getLrn()).thenReturn("12345678910");
        when(student1.getFirstName()).thenReturn("John");
        when(student1.getLastName()).thenReturn("Doe");
        when(student1.getGradeLevel()).thenReturn("10");
        when(student1.getSection()).thenReturn("Diamond");
        when(student1.getGender()).thenReturn("Male");
        when(student1.getAge()).thenReturn(Integer.valueOf("15"));

        Student student2 = mock(Student.class);
        when(student2.getLrn()).thenReturn("12345678911");
        when(student2.getFirstName()).thenReturn("Anna");
        when(student2.getLastName()).thenReturn("Smith");
        when(student2.getGradeLevel()).thenReturn("9");
        when(student2.getSection()).thenReturn("Ruby");
        when(student2.getGender()).thenReturn("Female");
        when(student2.getAge()).thenReturn(Integer.valueOf("14"));

        Student student3 = mock(Student.class);
        when(student3.getLrn()).thenReturn("12345678912");
        when(student3.getFirstName()).thenReturn("Mark");
        when(student3.getLastName()).thenReturn("Brown");
        when(student3.getGradeLevel()).thenReturn("8");
        when(student3.getSection()).thenReturn("Diamond");
        when(student3.getGender()).thenReturn("Male");
        when(student3.getAge()).thenReturn(Integer.valueOf("13"));

        Field masterListField = StudentHealthProfileController.class.getDeclaredField("masterStudentList");
        masterListField.setAccessible(true);
        javafx.collections.ObservableList<Student> masterStudentList =
                (javafx.collections.ObservableList<Student>) masterListField.get(controller);

        masterStudentList.clear();
        masterStudentList.addAll(List.of(student1, student2, student3));

        Method populateComboBoxesMethod = StudentHealthProfileController.class.getDeclaredMethod("populateComboBoxes", List.class);
        populateComboBoxesMethod.setAccessible(true);
        populateComboBoxesMethod.invoke(controller, List.of(student1, student2, student3));

        Method updatePageMethod = StudentHealthProfileController.class.getDeclaredMethod("updatePage");
        updatePageMethod.setAccessible(true);
        updatePageMethod.invoke(controller);
    }

    @Test
    void testControlsLoaded() {
        assertNotNull(studentTableView);
        assertNotNull(searchTextField);
        assertNotNull(sectionComboBox);
        assertNotNull(sexComboBox);
        assertNotNull(ageFilterBtn);
        assertNotNull(aToZFilterBtn);
        assertNotNull(zToAFilterBtn);
        assertNotNull(clearFilterBtn);
        assertNotNull(rowsPerPageComboBox);
        assertNotNull(paginationLabel);
        assertNotNull(rowsPageLabel);
        assertNotNull(togglePrevBtn);
        assertNotNull(toggleNextBtn);
    }

    @Test
    void testComboBoxesLoaded() {
        assertTrue(sectionComboBox.getItems().contains("All Sections"));
        assertTrue(sectionComboBox.getItems().contains("Diamond"));
        assertTrue(sectionComboBox.getItems().contains("Ruby"));

        assertTrue(sexComboBox.getItems().contains("All Genders"));
        assertTrue(sexComboBox.getItems().contains("Male"));
        assertTrue(sexComboBox.getItems().contains("Female"));
    }

    @Test
    void testInitialTableDataLoaded() {
        assertEquals(3, studentTableView.getItems().size());
        assertEquals("1 - 3 of 3", paginationLabel.getText());
        assertEquals("3", rowsPageLabel.getText());
    }

    @Test
    void testFirstLetterAutoCapitalization() {
        assertEquals("Male", controller.firstLetterAutoCapitalization("male"));
        assertEquals("Female", controller.firstLetterAutoCapitalization("FEMALE"));
        assertEquals("Diamond", controller.firstLetterAutoCapitalization("diamond"));
        assertEquals("", controller.firstLetterAutoCapitalization(""));
        assertNull(controller.firstLetterAutoCapitalization(null));
    }

    @Disabled
    @Test
    void testSearchFilter(FxRobot robot) {
        robot.clickOn(searchTextField);
        robot.write("Anna");

        assertEquals(1, studentTableView.getItems().size());
        assertEquals("Anna", studentTableView.getItems().get(0).getFirstName());
    }

    @Disabled
    @Test
    void testSearchByLrn(FxRobot robot) {
        robot.clickOn(searchTextField);
        robot.write("12345678910");

        assertEquals(1, studentTableView.getItems().size());
        assertEquals("12345678910", studentTableView.getItems().get(0).getLrn());
    }

    @Disabled
    @Test
    void testSectionFilter() {
        sectionComboBox.getSelectionModel().select("Diamond");

        assertEquals(2, studentTableView.getItems().size());
        assertTrue(studentTableView.getItems().stream()
                .allMatch(student -> student.getSection().equalsIgnoreCase("Diamond")));
    }

    @Disabled
    @Test
    void testGenderFilter() {
        sexComboBox.getSelectionModel().select("Male");

        assertEquals(2, studentTableView.getItems().size());
        assertTrue(studentTableView.getItems().stream()
                .allMatch(student -> student.getGender().equalsIgnoreCase("Male")));
    }

    @Disabled
    @Test
    void testCombinedFilters(FxRobot robot) {
        sectionComboBox.getSelectionModel().select("Diamond");
        sexComboBox.getSelectionModel().select("Male");

        robot.clickOn(searchTextField);
        robot.write("John");

        assertEquals(1, studentTableView.getItems().size());
        assertEquals("John", studentTableView.getItems().get(0).getFirstName());
    }

    @Disabled
    @Test
    void testClearFilter(FxRobot robot) {
        sectionComboBox.getSelectionModel().select("Diamond");
        sexComboBox.getSelectionModel().select("Male");
        robot.clickOn(searchTextField);
        robot.write("John");

        robot.clickOn(clearFilterBtn);

        assertEquals("All Sections", sectionComboBox.getValue());
        assertEquals("All Genders", sexComboBox.getValue());
        assertEquals("", searchTextField.getText());
    }

    @Disabled
    @Test
    void testSortAToZ(FxRobot robot) {
        robot.clickOn(aToZFilterBtn);

        assertFalse(studentTableView.getSortOrder().isEmpty());
        assertEquals(lastNameColumn, studentTableView.getSortOrder().get(0));
        assertEquals(TableColumn.SortType.ASCENDING, lastNameColumn.getSortType());
    }

    @Disabled
    @Test
    void testSortZToA(FxRobot robot) {
        robot.clickOn(zToAFilterBtn);

        assertFalse(studentTableView.getSortOrder().isEmpty());
        assertEquals(lastNameColumn, studentTableView.getSortOrder().get(0));
        assertEquals(TableColumn.SortType.DESCENDING, lastNameColumn.getSortType());
    }

    @Disabled
    @Test
    void testSortByAge(FxRobot robot) {
        robot.clickOn(ageFilterBtn);

        assertFalse(studentTableView.getSortOrder().isEmpty());
        assertEquals(ageColumn, studentTableView.getSortOrder().get(0));
        assertEquals(TableColumn.SortType.ASCENDING, ageColumn.getSortType());
    }

    @Disabled
    @Test
    void testRowsPerPageChange() {
        rowsPerPageComboBox.getSelectionModel().select(Integer.valueOf(5));

        assertEquals(5, rowsPerPageComboBox.getValue());
    }

    @Disabled
    @Test
    void testPaginationButtons() {
        assertTrue(togglePrevBtn.isVisible());
        assertTrue(toggleNextBtn.isVisible());
        assertFalse(togglePrevBtn.isDisable());
        assertFalse(toggleNextBtn.isDisable());
>>>>>>> a36da81 (updated unit test for view student health profile)
    }
}