package br.com.agdev;

import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
//        String folder = "03.01-conhecendo-o-modelo-de-dominio-do-projeto-e-criando-as-entidades";
//        String path = "/home/als/Documents/" + folder;

        String path = "/home/als/Documents/repositorios/curso-especialista-jpa/17.02-configurando-um-projeto-web-com-spring-mvc";

        String source = "/home/als/Documents/17.01-reconhecendo-o-que-aprendemos-em-aplicacoes-web";
        String sourcePomXml = source + "/pom.xml";
        String sourcePersistenceXml = source + "/src/main/resources/META-INF/persistence.xml";
        String sourceGerenciamentoTransacoesTest = source + "/src/test/java/com/algaworks/ecommerce/conhecendoentitymanager/GerenciamentoTransacoesTest.java";
        String sourceFlushTest = source + "/src/test/java/com/algaworks/ecommerce/conhecendoentitymanager/FlushTest.java";
//        String sourceRelacionamentoOneToManyTest = source + "/src/test/java/com/algaworks/ecommerce/relacionamentos/RelacionamentoOneToManyTest.java";
        String sourceExecutarDDL = source + "/src/main/java/com/algaworks/ecommerce/util/ExecutarDDL.java";
        String sourceEcmMachineMultiTenantConnectionProvider = source + "/src/main/java/com/algaworks/ecommerce/hibernate/EcmMachineMultiTenantConnectionProvider.java";
        String sourceEcmSchemaMultiTenantConnectionProvider = source + "/src/main/java/com/algaworks/ecommerce/hibernate/EcmSchemaMultiTenantConnectionProvider.java";

        String destPomXml = path + "/pom.xml";
        String destPersistenceXml = path + "/src/main/resources/META-INF/persistence.xml";
        String destGerenciamentoTransacoesTest = path + "/src/test/java/com/algaworks/ecommerce/conhecendoentitymanager/GerenciamentoTransacoesTest.java";
        String destFlushTest = path + "/src/test/java/com/algaworks/ecommerce/conhecendoentitymanager/FlushTest.java";
        String destRelacionamentoOneToManyTest = path + "/src/test/java/com/algaworks/ecommerce/relacionamentos/RelacionamentoOneToManyTest.java";
        String destExecutarDDL = path + "/src/main/java/com/algaworks/ecommerce/util/ExecutarDDL.java";
        String destEcmMachineMultiTenantConnectionProvider = path + "/src/main/java/com/algaworks/ecommerce/hibernate/EcmMachineMultiTenantConnectionProvider.java";
        String destEcmSchemaMultiTenantConnectionProvider = path + "/src/main/java/com/algaworks/ecommerce/hibernate/EcmSchemaMultiTenantConnectionProvider.java";

        ReplaceService.replaceInFilesOfPath(Paths.get(path));
        ReplaceService.replaceFile(Paths.get(sourcePersistenceXml), Paths.get(destPersistenceXml));
        ReplaceService.replaceFile(Paths.get(sourcePomXml), Paths.get(destPomXml));
        ReplaceService.replaceFile(Paths.get(sourceGerenciamentoTransacoesTest), Paths.get(destGerenciamentoTransacoesTest));
        ReplaceService.replaceFile(Paths.get(sourceFlushTest), Paths.get(destFlushTest));
//        ReplaceService.replaceFile(Paths.get(sourceRelacionamentoOneToManyTest), Paths.get(destRelacionamentoOneToManyTest));
        ReplaceService.replaceFile(Paths.get(sourceExecutarDDL), Paths.get(destExecutarDDL));
//        ReplaceService.replaceFile(Paths.get(sourceEcmMachineMultiTenantConnectionProvider), Paths.get(destEcmMachineMultiTenantConnectionProvider));
//        ReplaceService.replaceFile(Paths.get(sourceEcmSchemaMultiTenantConnectionProvider), Paths.get(destEcmSchemaMultiTenantConnectionProvider));

        // /home/als/Documents/repositorios/curso-especialista-jpa/15.05-implementando-multitenancy-com-abordagem-por-maquina/src/main/java/com/algaworks/ecommerce/hibernate/EcmMachineMultiTenantConnectionProvider.java
    }
}
