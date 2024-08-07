package webcom;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@WebServlet("/saveSignature")
public class SignatureServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String signatureImage = request.getParameter("signatureImage");
        String textSignature = request.getParameter("textSignature");

        try {
            createPdf(signatureImage, textSignature);
            response.setContentType("text/plain");
            response.getWriter().println("Signature saved successfully!");
        } catch (DocumentException e) {
            throw new ServletException("PDF generation error", e);
        }
    }

    private void createPdf(String signatureImage, String textSignature) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("signature.pdf"));
        document.open();

        if (textSignature != null && !textSignature.isEmpty()) {
            document.add(new Paragraph("Typed Signature: " + textSignature));
        }

        if (signatureImage != null && !signatureImage.isEmpty()) {
            byte[] imageBytes = Base64.getDecoder().decode(signatureImage.split(",")[1]);
            Image image = Image.getInstance(new ByteArrayInputStream(imageBytes).toString());
            document.add(image);
        }

        document.close();
    }
}
