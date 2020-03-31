package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.notes.Notes;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.NusModule;
import seedu.address.model.person.Person;
import seedu.address.todolist.Deadline;
import seedu.address.todolist.ToDo;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Notes> PREDICATE_SHOW_ALL_NOTES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getDiaryBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);
    //=========== Diary Module ==================================================================================
    boolean isEmptyDiaryEntry(DiaryEntry diaryEntry);

    void addDiaryEntry(DiaryEntry diaryEntry);

    String showDiaryLog();

    ObservableList<DiaryEntry> getDiaryList();

    //=========== Notes Module ==================================================================================
    /** Returns an list of String that contains what is currently in the folder */
    ObservableList<Notes> getFilesInFolderList();

    /**
     * Updates the notes list by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateNotesList(Predicate<Notes> predicate);

    //=========== CAP Module ==================================================================================
    /**
     * Returns true if a module with the same identity as {@code module} exists in the address book.
     */
    boolean hasModule(ModuleCode moduleCode);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the address book.
     */
    void addModule(NusModule module);

    void deleteModule(ModuleCode moduleCode);

    void gradeModule(ModuleCode moduleCode, Grade grade);

    double getCap();

    //=========== Deadline ==================================================================================

    /**
     * Adds deadline.
     */
    void addDeadline(Deadline deadline);

    /**
     * Checks if content of deadline is empty
     */
    boolean isEmptyDeadline(Deadline deadline);

    //=========== TD ==================================================================================

    /**
     * Adds todo.
     */
    void addToDo(ToDo todo);

    /**
     * Checks if content of todo is empty
     */
    boolean isEmptyToDo(ToDo todo);

}
