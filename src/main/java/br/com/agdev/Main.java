package br.com.agdev;

import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
//        String folder = "03.01-conhecendo-o-modelo-de-dominio-do-projeto-e-criando-as-entidades";
//        String path = "/home/als/Documents/" + folder;

        String path = "/home/als/Documents/repositorios/curso-especialista-jpa/05.01-estados-e-ciclo-de-vida-dos-objetos";
        String sourcePomXml = "/home/als/Documents/02.17-conhecendo-e-usando-lombok/pom.xml";
        String sourcePersistenceXml = "/home/als/Documents/02.17-conhecendo-e-usando-lombok/src/main/resources/META-INF/persistence.xml";

        String destPomXml = path + "/pom.xml";
        String destPersistenceXml = path + "/src/main/resources/META-INF/persistence.xml";

        ReplaceService.replaceInFilesOfPath(Paths.get(path));
        ReplaceService.replaceFile(Paths.get(sourcePersistenceXml), Paths.get(destPersistenceXml));
        ReplaceService.replaceFile(Paths.get(sourcePomXml), Paths.get(destPomXml));
    }
}
