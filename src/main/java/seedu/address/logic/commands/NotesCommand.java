package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_OPERATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES_PATH;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import seedu.address.model.Model;
import seedu.address.model.notes.Notes;


/**
 * NotesCommand is created when a notecommand is parsed, to return what operation to be done.
 * implementation is not yet completed
 */
public class NotesCommand extends Command {

    public static final String COMMAND_WORD = "notes";
    public static final String HOME_DIRECTORY = System.getProperty("user.home");

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allows for creating, deleting and opening of new file "
            + "Parameters: "
            + PREFIX_NOTES_OPERATION + "OPERATION "
            + PREFIX_NOTES_PATH + "PATH \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NOTES_OPERATION + "open "
            + PREFIX_NOTES_PATH + "test.docx ";

    public static final String MESSAGE_OPEN_SUCCESS = "Opened file ";
    public static final String MESSAGE_OPEN_FAIL = "Failed to open file ";
    public static final String MESSAGE_UNABLE_TO_FIND = "Unable to find file to open ";

    private static final String MESSAGE_CREATE_SUCCESS = "File is successfully created at ";
    private static final String MESSAGE_CREATE_FAIL = "File is unable to be created at ";
    private static final String MESSAGE_NOTHING_HAPPENED = "Nothing Happened ";
    private static final String MESSAGE_MAKEDIR_SUCCESSFUL = "Directory is successfully created ";
    private static final String MESSAGE_MAKEDIR_UNSUCCESSFUL = "Directory is unable to be created ";

    private static final String MESSAGE_LISTED_DIR = "Listed Directory at ";
    private static final String MESSAGE_LISTED_DIR_FAIL = "Unable to list directory at ";
    private static final String DUMMY_TEST = "Dummy test ";



    private String notesOperation;
    private String path;

    /**
     * Creates a new NotesCommand to Create/Open/Delete a new note.
     *
     * @param note the operation and location that will be done to the note.
     */
    public NotesCommand(Notes note) {
        this.notesOperation = note.getOperation();
        this.path = note.getPath(); // the path will be with respect to the Desktop.
    }

    /**
     * Opens a document at the specified path.
     *
     * @param path the given path that the document resides in.
     * @return a CommandResult based on whether the operation succeed or failed.
     */
    public CommandResult openDoc(String path) {
        String pathName = HOME_DIRECTORY + File.separatorChar + "Desktop" + File.separatorChar + path;
        File myFile = new File(pathName);
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(myFile);
            }
            return new CommandResult(MESSAGE_OPEN_SUCCESS);
        } catch (IOException ex) {
            return new CommandResult(MESSAGE_OPEN_FAIL);
        } catch (IllegalArgumentException ex) {
            return new CommandResult(MESSAGE_UNABLE_TO_FIND);
        }

    }

    /**
     * Creates a document at the specified path.
     *
     * @param path the given path that the created document will reside in.
     * @return a CommandResult based on whether the operation succeed or failed.
     */
    public CommandResult createDoc(String path) {
        String currentDirectory = path.split("/")[0];
        String pathName = HOME_DIRECTORY + File.separatorChar + "Desktop" + File.separatorChar + path;
        File myFile = new File(pathName);
        try {
            myFile.createNewFile();
            Notes.setList(listfilesArray(currentDirectory));
            return new CommandResult(MESSAGE_CREATE_SUCCESS + pathName);
        } catch (IOException ex) {
            return new CommandResult(MESSAGE_CREATE_FAIL + pathName);

        }

    }

    /**
     * Creates a folder at the specified path.
     * @param path the given path that the created document will reside in.
     * @return a CommandResult based on whether the operation succeed or failed.
     */
    public CommandResult createFolder(String path) {
        String pathName = HOME_DIRECTORY + File.separatorChar + "Desktop" + File.separatorChar + path;
        File myFile = new File(pathName);

        if (myFile.mkdir()) { // return true if directory is created
            Notes.setList(listfilesArray(path));
            return new CommandResult(MESSAGE_MAKEDIR_SUCCESSFUL + pathName);
        } else {
            return new CommandResult(MESSAGE_MAKEDIR_UNSUCCESSFUL + pathName);
        }

    }

    /**
     * Tentative, may remove this function.
     * @param path dummy
     * @return dummy
     */
    public CommandResult listFiles(String path) {
        String pathName = HOME_DIRECTORY + File.separatorChar + "Desktop" + File.separatorChar + path;
        File myFile = new File(pathName);
        ArrayList<Notes> filesArrayList = new ArrayList<>();

        File[] allFiles = myFile.listFiles();
        for (File f : allFiles) {
            String[] allFileName = f.toString().split(File.separator);
            String filename = allFileName[allFileName.length - 1];
            if (filename.charAt(0) == ('.')) {
                continue;
            }
            Notes note = new Notes(filename);
            filesArrayList.add(note);
            System.out.println(filename);
        }
        Notes.setList(filesArrayList);
        return new CommandResult(MESSAGE_LISTED_DIR + pathName);
    }

    /**
     * Tentative, to be updated.
     * @param path dummy
     * @return dummy
     */
    private ArrayList<Notes> listfilesArray(String path) {

        String pathName = HOME_DIRECTORY + File.separatorChar + "Desktop" + File.separatorChar + path;
        File myFile = new File(pathName);
        ArrayList<Notes> filesArrayList = new ArrayList<>();

        File[] allFiles = myFile.listFiles();
        for (File f : allFiles) {
            String[] allFileName = f.toString().split(File.separator);
            String filename = allFileName[allFileName.length - 1];
            if (filename.charAt(0) == ('.')) {
                continue;
            }
            Notes note = new Notes(filename);
            filesArrayList.add(note);
            System.out.println(filename);
        }
        return filesArrayList;

    }



    @Override
    public CommandResult execute(Model model) {

        model.updateNotesList(PREDICATE_SHOW_ALL_NOTES);

        if (this.notesOperation.equals("open")) {
            return openDoc(this.path);
        } else if (this.notesOperation.equals("create")) {
            return createDoc(this.path);
        } else if (this.notesOperation.equals("createfolder")) {
            return createFolder(this.path);
        } else if (this.notesOperation.equals("list")) {
            return listFiles(this.path);
        }

        return new CommandResult(MESSAGE_NOTHING_HAPPENED);
    }

    @Override // not yet finish implementing
    public boolean equals(Object other) {
        return other == this;
    }
}
