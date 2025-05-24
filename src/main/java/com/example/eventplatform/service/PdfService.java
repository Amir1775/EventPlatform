package com.example.eventplatform.service;


import com.example.eventplatform.repository.Event;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;

@Service
public class PdfService {

    public byte[] generateEventPdf(Event event) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Загрузка шрифта с поддержкой кириллицы
        PDType0Font font = PDType0Font.load(document, new File("C:/Windows/Fonts/arial.ttf"));
        contentStream.setFont(font, 12);

        contentStream.beginText();
        contentStream.newLineAtOffset(25, 700);

        // Добавление текста
        contentStream.showText("Приглашение на мероприятие");
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Название: " + (event.getTitle() != null ? event.getTitle() : "Не указано"));
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Описание: " + (event.getDescription() != null ? event.getDescription() : "Не указано"));
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Адрес: " + (event.getAddress() != null ? event.getAddress() : "Не указано"));
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Начало: " + (event.getStartTime() != null ? event.getStartTime() : "Не указано"));
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Конец: " + (event.getEndTime() != null ? event.getEndTime() : "Не указано"));
        contentStream.newLineAtOffset(0, -20);

        String organizerName = event.getOrganizer() != null && event.getOrganizer().getName() != null
                ? event.getOrganizer().getName() : "Не указано";
        contentStream.showText("Организатор: " + organizerName);
        contentStream.newLineAtOffset(0, -20);

        String categoryName = event.getCategory() != null && event.getCategory().getName() != null
                ? event.getCategory().getName() : "Не указано";
        contentStream.showText("Категория: " + categoryName);
        contentStream.newLineAtOffset(0, -20);

        String locationTypeName = event.getLocationType() != null && event.getLocationType().getName() != null
                ? event.getLocationType().getName() : "Не указано";
        contentStream.showText("Тип места: " + locationTypeName);

        contentStream.endText();

        // Добавление фото
        if (event.getPhotoPath() != null) {
            File file = new File(event.getPhotoPath());
            if (file.exists()) {
                PDImageXObject pdImage = PDImageXObject.createFromFile(file.getAbsolutePath(), document);
                contentStream.drawImage(pdImage, 25, 400, 200, 200);
            }
        }

        contentStream.close();
        document.save(baos);
        document.close();

        return baos.toByteArray();
    }
}
