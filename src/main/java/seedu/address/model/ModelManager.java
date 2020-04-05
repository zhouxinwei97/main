package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.diary.DiaryBook;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.notes.Notes;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.Major;
import seedu.address.model.nusmodule.ModuleBook;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.ModuleTask;
import seedu.address.model.nusmodule.NusModule;
import seedu.address.model.person.Person;
import seedu.address.todolist.Task;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final ObservableList<DiaryEntry> diaryEntries;
    private DiaryBook diaryBook;
    private final FilteredList<Notes> filesInFolder;
    private ModuleBook moduleBook;
    private final FilteredList<Task> deadlineTaskList;
    private final FilteredList<NusModule> moduleListTaken;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        diaryEntries = this.addressBook.getDiaryList();
        diaryBook = new DiaryBook();
        filesInFolder = new FilteredList<>(Notes.getAllFilesInFolder());
        deadlineTaskList = new FilteredList<>(Task.getNewDeadlineTaskList());
        moduleBook = new ModuleBook();
        moduleListTaken = new FilteredList<>(moduleBook.getModulesTakenList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Diary Module ==================================================================================
    @Override
    public void addDiaryEntry(DiaryEntry diaryEntry) {
        diaryBook.addEntry(diaryEntry);
    }

    @Override
    public boolean isEmptyDiaryEntry(DiaryEntry diaryEntry) {
        // implement later
        return false;
    }

    @Override
    public String showDiaryLog() {
        return diaryBook.showLog();
    }

    @Override
    public Path getDiaryBookFilePath() {
        return userPrefs.getDiaryBookFilePath();
    }

    @Override
    public ObservableList<DiaryEntry> getDiaryList() {
        return diaryEntries;
    }


    //=========== Cap Module ==================================================================================

    /**
     * Dummy java docs
     * @param
     * @return
     */
    @Override
    public boolean hasModule(ModuleCode moduleCode) {
        return moduleBook.hasModule(moduleCode);
    }

    @Override
    public void addModule(NusModule module) {
        moduleBook.addModule(module);
    }

    @Override
    public void deleteModule(ModuleCode moduleCode) {
        moduleBook.deleteModule(moduleCode);
    }

    @Override
    public void gradeModule(ModuleCode moduleCode, Grade updatedGrade) {
        moduleBook.gradeModule(moduleCode, updatedGrade);
    }

    @Override
    public double getCap() {
        return moduleBook.getCap();
    }

    public ObservableList<NusModule> getModulesListTaken() {
        return moduleListTaken;
    }

    @Override
    public void updateModulesListTaken(Predicate<NusModule> predicate) {
        requireNonNull(predicate);
        moduleListTaken.setPredicate(predicate);
    }

    @Override
    public ModuleBook getModuleBook() {
        return this.moduleBook;
    }

    @Override
    public int getSizeOfModuleTaskList(ModuleCode moduleCode) {
        return moduleBook.getSizeOfModuleTaskList(moduleCode);
    }

    @Override
    public List<ModuleTask> getModuleTaskList(ModuleCode moduleCode) {
        return moduleBook.getModuleTaskList(moduleCode);
    }
    @Override
    public void deleteModuleTask(ModuleCode moduleCode, Index index) {
        moduleBook.deleteModuleTask(moduleCode, index);
    }

    @Override
    public void doneModuleTask(ModuleCode moduleCode, Index index) {
        moduleBook.doneModuleTask(moduleCode, index);
    }

    @Override
    public void addModuleTask(ModuleTask moduleTask) {
        moduleBook.addModuleTask(moduleTask);
    }

    @Override
    public void updateMajor(Major major) {
        moduleBook.setMajor(major);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Notes Module ==================================================================================
    /** Returns an list of String that contains what is currently in the folder */
    @Override
    public ObservableList<Notes> getFilesInFolderList() {
        return filesInFolder;
    }

    @Override
    public void updateNotesList(Predicate<Notes> predicate) {
        requireNonNull(predicate);
        filesInFolder.setPredicate(predicate);
    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

    //=========== Deadline Module ==================================================================================

    /**
     * Dummy java docs
     * @param deadline contains task
     */

    @Override
    public void addDeadline(Task deadline) {

    }

    @Override
    public boolean isEmptyDeadline(Task deadline) {
        return false;
    }

    /** Returns an list of String that contains what is currently in the folder */
    @Override
    public ObservableList<Task> getDeadlineTaskList() {
        return deadlineTaskList;
    }

    @Override
    public void updateDeadlineTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        deadlineTaskList.setPredicate(predicate);
    }

    @Override
    public List<Task> findTasksByDate(String date) {
        List<Task> targetTasks = new ArrayList<>();
        for(Task task: deadlineTaskList) {
            if(task.getDate().equals(date)) {
                targetTasks.add(task);
            }
        }
        return targetTasks;
    }

    @Override
    public List<Task> findTasksByCat(String cat) {
        List<Task> targetTasks = new ArrayList<>();
        for(Task task: deadlineTaskList) {
            if(task.getCategory().equals(cat)) {
                targetTasks.add(task);
            }
        }
        return targetTasks;
    }


    //=========== TD Module ==================================================================================


    @Override
    public void addToDo(Task todo) {

    }

    @Override
    public boolean isEmptyToDo(Task todo) {
        return false;
    }

}
