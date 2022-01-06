package System;

import java.io.FileWriter;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class MaintainMovie {
	public ArrayList<Movie> movies = new ArrayList<Movie>();
	public String path;
	
	public void load(String path) throws Exception{
		CsvReader reader = new CsvReader(path); 
		reader.readHeaders();
		
		while(reader.readRecord()){ 
			Movie mov = new Movie();
			//name,id,email,password
			mov.setTitle(reader.get("title")); 
			mov.setGenre(reader.get("genre"));
			mov.setDesc(reader.get("description"));
			mov.setActors(reader.get("actors"));
			mov.setDirector(reader.get("director"));
			mov.setPrice(Double.valueOf(reader.get("price")));
			movies.add(mov);
		}
	}
	
	public void update(String path) throws Exception{
		try {		
				CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
				//name,id,email,password
				csvOutput.write("title");
				csvOutput.write("genre");
		    	csvOutput.write("description");
				csvOutput.write("actors");
				csvOutput.write("director");
				csvOutput.write("price");
				csvOutput.endRecord();

				// else assume that the file already has the correct header line
				// write out a few records
				for(Movie m: movies){
					csvOutput.write(m.getTitle());
					csvOutput.write(m.getGenre());
					csvOutput.write(m.getDesc());
					csvOutput.write(m.getActors());
					csvOutput.write(m.getDirector());
					csvOutput.write(String.valueOf(m.getPrice()));
					csvOutput.endRecord();
				}
				csvOutput.close();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
}
