package br.com.agdev;

import br.com.agdev.model.Substitution;
import br.com.agdev.model.SubstitutionsList;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReplaceService {

    public static final String FILE_SUBSTITUTIONS = System.getProperty("user.dir") + File.separator + "substitutions.json";
    public static final String FILE_FOLDERS_TO_IGNORE = System.getProperty("user.dir") + File.separator + "foldersToIgnore.txt";

    public static void replaceInFilesOfPath(Path source) {
        replaceInFilesOfPath(source, getFoldersToIgnore());
    }

    public static void replaceFile(Path source, Path destination) {
        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void replaceInFilesOfPath(Path source, List<String> foldersToIgnore) {
        try {
            List<Path> pathList = Files.list(source).toList();
            for (Path path : pathList) {
                if (Files.isDirectory(path)) {
                    boolean runThroughThisFolder = !foldersToIgnore.contains(path.getFileName().toString());
                    if (runThroughThisFolder) {
                        replaceInFilesOfPath(path, foldersToIgnore);
                    } else {
                        System.out.println(String.format("Pasta %s sendo ignorada", path));
                    }
                    continue;
                }

                System.out.println(String.format("Arquivo %s sendo varificado", path));
                StringBuffer currentFile = new StringBuffer();
                Files.newBufferedReader(path)
                        .lines()
                        .forEach(line -> {
                            currentFile.append(line);
                            currentFile.append("\n");
                        });

                String replaced = currentFile.toString();
                for (Substitution substitution : getSubstitutionList().getSubstitutions()) {
                    Pattern pattern = Pattern.compile(substitution.getRegex(), Pattern.MULTILINE);
                    Matcher matcher = pattern.matcher(replaced);
                    while (matcher.find()) {
                        replaced = matcher.replaceAll(substitution.getNewValue());
                    }
                }

                boolean fileHasChanged = !replaced.contentEquals(currentFile);
                if (fileHasChanged) {
                    System.out.println(String.format("Arquivo %s alterado", path));
                    Files.writeString(path, replaced);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static SubstitutionsList getSubstitutionList() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(Files.newInputStream(Paths.get(FILE_SUBSTITUTIONS)), SubstitutionsList.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<String> getFoldersToIgnore() {
        try {
            return Files.readAllLines(Paths.get(FILE_FOLDERS_TO_IGNORE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
