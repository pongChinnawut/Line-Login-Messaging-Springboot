package com.example.demo.model;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javassist.bytecode.ByteArray;
import javassist.bytecode.stackmap.TypeData;
import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;

import javax.imageio.ImageIO;

public class QRCodeGenerator {



    public static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

//        Path path = FileSystems.getDefault().getPath(filePath);
        Path path5 = Paths.get("D:\\picSpring\\QRcode_"+ text+".png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path5);

    }


    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path5 = Paths.get("D:\\picSpring\\pongQRcode_"+ text+".png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path5);
//ห้ามลบของเก่า ก่อนเอาภาพจากเครื่อง
//        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
//        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
//        byte[] pngData = pngOutputStream.toByteArray();
//        return pngData;

        BufferedImage image = ImageIO.read(new File("D:\\picSpring\\QRcode_"+text+".png"));
//
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] bytes = baos.toByteArray();
        return bytes;

        }

//    public ResponseEntity<Byte[]> getImagePok() {
//        try {
//            Path file = Paths.get("D:\\picSpring\\").resolve("QRcode_pokas_1998@hotmail.com.png");
//            Resource resource = new UrlResource(file.toUri());
//            if (resource.exists() || resource.isReadable()) {
//                Byte[] byteArray = StreamUtils.copyToByteArray(resource.getInputStream());
//
//                return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(byteArray);
//
//            } else {
//                throw new RuntimeException("error");
//
//            }
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    }
