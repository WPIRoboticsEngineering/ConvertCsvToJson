package convert.Csv.To.Json;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {

	public static void main(String[] args) {
		String home = System.getProperty("user.home");
		final JFileChooser fc = new JFileChooser(home + "/Downloads/");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv", "CSV");
		fc.setAcceptAllFileFilterUsed(false);
		;

		fc.addChoosableFileFilter(filter);
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			run(file);
		}

	}

	public static void run(File returned) {
		new Thread(() -> {
			HashMap<String, ArrayList<String>> hashMap = new HashMap<>();
			URI uri = returned.toURI();
			List<String> lines;
			try {
				lines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());
				int lineCount =0;
				for (String line : lines) {
					if(lineCount==0)
						System.out.println(lineCount+" "+line);
					else {
						String[] fields = line.split(",");
						String name = fields[1];
						String team = fields[3];
						if(hashMap.get(team)==null) {
							hashMap.put(team, new ArrayList<String>());
						}
						hashMap.get(team).add(name);
					}
					
					lineCount++;
				}
				for(String key:hashMap.keySet()) {
					String start  ="\""+key+"\""+": [";
					for(String username:hashMap.get(key)) {
						start+="\""+username+"\" ,";
					}
					start=start.substring(0, start.length()-1);
					start+="],";
					System.out.println(start);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}).start();
	}

}
