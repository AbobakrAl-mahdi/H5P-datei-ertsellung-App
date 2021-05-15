package h5pErsteller;


import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class ZipDirectory extends Application {
	
	File srcFile, zipFile;
	

	@Override
	public void start(Stage st) throws Exception {
		GridPane root = new GridPane();
		Label l1 = new Label("choose a File to Zip");
		Button b1 = new Button("Auswählen");
		Label l2 = new Label("choose where to save the File");
		Button b2 = new Button("auswählen");
		Button b3 = new Button("Start!");
		root.add(l1, 1, 1);
		root.add(b1, 2, 1);
		root.add(l2, 1, 2);
		root.add(b2, 2, 2);
		root.add(b3, 2, 3);
		root.setAlignment(Pos.CENTER);
		root.setVgap(10);
		root.setHgap(10);
		
		Scene sc = new Scene(root,400,400);
		Stage st1 = new Stage();
		st1.setScene(sc);
		st1.show();
		
		b1.setOnAction(e->{
			DirectoryChooser chooser = new DirectoryChooser();
	        srcFile= chooser.showDialog(null);
		});
		
		b2.setOnAction(e->{
			DirectoryChooser chooser = new DirectoryChooser();
	        zipFile= chooser.showDialog(null);
		});
		
		b3.setOnAction(e->{
			zipping(srcFile);
		});
	}
	
    private void zipping(File sourceFile) {
    	try {
    		FileOutputStream fos = new FileOutputStream(zipFile.getPath()+"/"+srcFile.getName()+".h5p");
	        ZipOutputStream zipOut = new ZipOutputStream(fos);
	        File fileToZip = new File(sourceFile.getPath());
	        
	        File [] children1 = fileToZip.listFiles();
	        for (File childFile : children1) {
				zipFile(childFile, childFile.getName(), zipOut);	
	        }
	        zipOut.close();
			fos.close();
			JOptionPane.showMessageDialog(null, "H5P-Datei erfolgreich erstellt!", "Erfolgreich", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			System.out.println("didnt work");
		}
	}

	public static void main(String[] args) throws IOException {
    	launch(args);
    }
    
    

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
	            }
	            return;
	        }
	        FileInputStream fis = new FileInputStream(fileToZip);
	        ZipEntry zipEntry = new ZipEntry(fileName);
	        zipOut.putNextEntry(zipEntry);
	        byte[] bytes = new byte[1024];
	        int length;
	        while ((length = fis.read(bytes)) >= 0) {
	            zipOut.write(bytes, 0, length);
	        }
	        fis.close();
	    }



	}
