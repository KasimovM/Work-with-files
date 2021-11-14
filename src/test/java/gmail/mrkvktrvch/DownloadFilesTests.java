package gmail.mrkvktrvch;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.selector.ByText;
import com.codeborne.xlstest.XLS;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DownloadFilesTests {

    @Test
    @DisplayName("Скачивание текстового документа и проверка его содержимого")
    public void downloadTextFileTest() throws IOException {

        open("https://github.com/sberbank-ai/ru-dalle/blob/master/README.md");
        File readme = $("#raw-url").download();
        String readmeContent = IOUtils.toString(new FileReader(readme));
        assertTrue(readmeContent.contains("from rudalle import get_rudalle_model, get_tokenizer, get_vae, " +
                "get_realesrgan, get_ruclip"));
    }

    @Test
    @DisplayName("Скачивание и парсинг PDF-файла")
    public void downloadPdfFileTest() throws IOException {

        open("https://git-scm.com/book/ru/v2/");
        File pdfFile = $(".ebooks").$("a").download();

        PDF parsedPdfFile = new PDF(pdfFile);
        Assertions.assertEquals("Scott Chacon, Ben Straub", parsedPdfFile.author);
    }

    @Test
    @DisplayName("Скачивание и парсинг XLS-файда")
    public void downloadXlsFileTest() throws FileNotFoundException {

        open("https://ckmt.ru/price-download.html");
        File xlsFile = $("#content").$("a").download();
        XLS parsedXls = new XLS(xlsFile);

        boolean checkPassed = parsedXls.excel.getSheetAt(0)
                .getRow(456)
                .getCell(2)
                .getStringCellValue()
                .contains("15шт");

        assertTrue(checkPassed);
    }
}
