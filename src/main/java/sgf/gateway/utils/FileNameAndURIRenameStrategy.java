package sgf.gateway.utils;

public class FileNameAndURIRenameStrategy {

    public String rename(String original) {

        String string = original.replaceAll(" ", "_");

        while (string.startsWith(".")) {

            string = string.replaceFirst("\\.", "");
        }

        string = string.replaceAll("[^a-zA-Z0-9_.-]", "");

        return string;
    }

}
