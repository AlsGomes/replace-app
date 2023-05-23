package br.com.agdev;

import br.com.agdev.model.Substitution;
import br.com.agdev.model.SubstitutionsList;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
    public static final String FILE_EXTENSIONS_TO_IGNORE = System.getProperty("user.dir") + File.separator + "extensionsToIgnore.txt";

    public static void replaceInFilesOfPath(Path source) {
        replaceInFilesOfPath(
                source,
                getFoldersToIgnore(),
                getExtensionsToIgnore(),
                getSubstitutions());
    }

    public static void replaceFile(Path source, Path destination) {
        try {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void replaceInFilesOfPath(
            Path source,
            List<String> foldersToIgnore,
            List<String> extensionsToIgnore,
            List<Substitution> substitutions) {

        try {
            List<Path> pathList = Files.list(source).toList();
            for (Path path : pathList) {
                if (Files.isDirectory(path)) {
                    boolean runThroughThisFolder = !foldersToIgnore.contains(path.getFileName().toString());
                    if (runThroughThisFolder) {
                        replaceInFilesOfPath(path, foldersToIgnore, extensionsToIgnore, substitutions);
                    } else {
                        System.out.println(String.format("Pasta %s sendo ignorada", path));
                    }
                    continue;
                }

                boolean ignoreFile = extensionsToIgnore.contains(getFileExtension(path.getFileName().toString()));
                if (ignoreFile) {
                    System.out.println(String.format("Arquivo %s sendo ignorado", path));
                    continue;
                }

                System.out.println(String.format("Arquivo %s sendo verificado", path));
                StringBuilder currentFile = new StringBuilder();
                Files.newBufferedReader(path)
                        .lines()
                        .forEach(line -> {
                            currentFile.append(line);
                            currentFile.append("\n");
                        });

                String replaced = currentFile.toString();
                for (Substitution substitution : substitutions) {
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

    private static List<Substitution> getSubstitutions() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            SubstitutionsList substitutionsList =
                    objectMapper.readValue(Files.newInputStream(Paths.get(FILE_SUBSTITUTIONS)), SubstitutionsList.class);

            return substitutionsList.getSubstitutions();
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

    private static List<String> getExtensionsToIgnore() {
        try {
            return Files.readAllLines(Paths.get(FILE_EXTENSIONS_TO_IGNORE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
