package com.hbm.handler;

import java.io.*;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import com.hbm.interfaces.Untested;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

public class HTTPHandler {

	public static String capsule = "ERROR         ";
	public static boolean newVersion = false;
	public static boolean optifine = false;
	public static String versionNumber = "";
	public static String changes = "";

	public static void loadStats() {

		try {
			loadVersion();
			loadOF();
			loadSoyuz();

		} catch (IOException e) {
			MainRegistry.logger.warn("Version checker failed!");
		}
	}
	
	@Untested
	private static void loadOF(){
		//Drillgon200: Yeah this is retarded but apparently optifine isn't added to the list of forge mods
		File mods = new File(MainRegistry.proxy.getDataDir().getPath() + "/mods/");
		DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
	        @Override
	        public boolean accept(Path file) throws IOException {
	            return (Files.isDirectory(file));
	        }
	    };
	    Path dir = mods.toPath();
	    try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, filter)) {
	        for (Path path : stream) {
	            if(path.getFileName().toString().toLowerCase().contains("optifine")){
	            	optifine = true;
	            	return;
	            }
	        }
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	    if(optifine){
	    	MainRegistry.logger.warn("Optifine present. This may cause compatibility with NTM's shaders!\n If experiencing issues, turn the enableShaders2 option in the config to false in order to disable them.");
	    }
	}

	public static BufferedReader getURLBufferReader(String url) {
		try {
			URL githubUrl = new URL(url);
			URLConnection con = githubUrl.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000); // 5 seconds timeout
			con.connect();
			InputStream input = con.getInputStream();
			return new BufferedReader(new InputStreamReader(input));
		} catch (SocketTimeoutException e) {
			MainRegistry.logger.warn("Search ended with timeout");
			return null;
		} catch (IOException e) {
            MainRegistry.logger.warn("Search ended with I/O error: {}", e.getMessage());
			return null;
		}
	}

	private static void loadVersion() throws IOException {
		MainRegistry.logger.info("Searching for new versions...");

		BufferedReader in = getURLBufferReader("https://raw.githubusercontent.com/Alcatergit/Hbm-s-Nuclear-Tech-GIT/Custom-1.12.2/src/main/java/com/hbm/lib/RefStrings.java");
		if(in == null) return;
        String line;

        while ((line = in.readLine()) != null) {

            if(line.contains("String VERSION")) {

            	int begin = line.indexOf('"');
            	int end = line.lastIndexOf('"');

            	String sub = line.substring(begin + 1, end);

            	newVersion = !RefStrings.VERSION.equals(sub);
            	versionNumber = sub;
    	        MainRegistry.logger.info("Found version " + sub);
            }
            if(line.contains("String CHANGELOG")) {

            	int begin = line.indexOf('"');
            	int end = line.lastIndexOf('"');

            	String sub = line.substring(begin + 1, end);

            	changes = sub.replaceAll("Â", "");
    	        MainRegistry.logger.info("Found changelog " + sub);
    	        break;
            }
        }

        MainRegistry.logger.info("Version checker ended.");
        in.close();
	}

	private static void loadSoyuz() throws IOException {

        BufferedReader in = getURLBufferReader("https://gist.githubusercontent.com/HbmMods/a1cad71d00b6915945a43961d0037a43/raw/soyuz_holo");
		if(in == null) return;
        String line = in.readLine();

        if(line != null)
        	capsule = line;

        in.close();
	}
}
