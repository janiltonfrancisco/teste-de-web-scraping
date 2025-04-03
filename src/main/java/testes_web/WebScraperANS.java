/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package testes_web;

/**
 *
 * @author janil
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class WebScraperANS {

    private static final String URL_ANS = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
    private static final String DOWNLOAD_DIR = "downloads";
    private static final String ZIP_FILE = "anexos_ans.zip";

    public static void main(String[] args) {
        try {
            // 1.1. Acesso ao site
            Document doc = Jsoup.connect(URL_ANS).get();

            // 1.2. Download dos Anexos I e II em formato PDF
            List<String> pdfUrls = findPdfUrls(doc);
            if (!pdfUrls.isEmpty()) {
                createDownloadDirectory();
                downloadPdfs(pdfUrls);

                // 1.3. Compactação de todos os anexos em um único arquivo (formato ZIP)
                zipFiles();
                System.out.println("Processo concluído com sucesso!");
            } else {
                System.out.println("Não foram encontrados links para os Anexos I e II.");
            }

        } catch (IOException e) {
            System.err.println("Ocorreu um erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static List<String> findPdfUrls(Document doc) {
        List<String> pdfUrls = new ArrayList<>();
        Elements links = doc.select("a[href$='.pdf']"); // Seleciona links que terminam com .pdf

        // Procurar por links que contenham "Anexo I" e "Anexo II" no texto ou no atributo title
        for (Element link : links) {
            String href = link.absUrl("href");
            String text = link.text().toLowerCase();
            String title = link.attr("title").toLowerCase();

            if ((text.contains("anexo i") || title.contains("anexo i")) && pdfUrls.size() < 2) {
                pdfUrls.add(href);
            } else if ((text.contains("anexo ii") || title.contains("anexo ii")) && pdfUrls.size() < 2) {
                pdfUrls.add(href);
            }
        }
        return pdfUrls;
    }

    private static void createDownloadDirectory() throws IOException {
        Path downloadPath = Paths.get(DOWNLOAD_DIR);
        if (!Files.exists(downloadPath)) {
            Files.createDirectories(downloadPath);
        }
    }

    private static void downloadPdfs(List<String> pdfUrls) {
        int count = 1;
        for (String pdfUrl : pdfUrls) {
            try {
                URL url = new URL(pdfUrl);
                InputStream in = url.openStream();
                Path filePath = Paths.get(DOWNLOAD_DIR, "Anexo_" + count + ".pdf");
                Files.copy(in, filePath);
                System.out.println("Download do " + filePath.getFileName() + " concluído.");
                in.close();
                count++;
            } catch (IOException e) {
                System.err.println("Erro ao baixar o arquivo: " + pdfUrl + " - " + e.getMessage());
            }
        }
    }

    private static void zipFiles() throws IOException {
        Path zipFilePath = Paths.get(ZIP_FILE);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath.toFile()))) {
            Path sourceDirPath = Paths.get(DOWNLOAD_DIR);
            Files.walk(sourceDirPath)
                .filter(path -> !Files.isDirectory(path))
                .forEach(path -> {
                    ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
                    try {
                        zos.putNextEntry(zipEntry);
                        Files.copy(path, zos);
                        zos.closeEntry();
                    } catch (IOException e) {
                        System.err.println("Erro ao adicionar arquivo ao ZIP: " + path + " - " + e.getMessage());
                    }
                });
        }
        System.out.println("Arquivos compactados em: " + zipFilePath);
    }
}