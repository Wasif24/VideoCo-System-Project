package System;

import java.io.FileWriter;
import java.util.ArrayList;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class MaintainEmployee {
	public ArrayList<SysAdmin> employees = new ArrayList<SysAdmin>();
	public String path;
	
	public void load(String path) throws Exception{
		CsvReader reader = new CsvReader(path); 
		reader.readHeaders();
		
		while(reader.readRecord()){ 
			SysAdmin a = new SysAdmin();
			a.setName(reader.get("Name"));
			a.setEmail(reader.get("Email"));
			a.setPassword(reader.get("Password"));
			employees.add(a);
		}
	}
	
	public void update(String path) throws Exception{
		try {		
				CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
				//name,id,email,password
				csvOutput.write("Name");
				csvOutput.write("Email");
				csvOutput.write("Password");
				csvOutput.endRecord();

				// else assume that the file already has the correct header line
				// write out a few records
				for(SysAdmin s: employees){
					csvOutput.write(s.getName());
					csvOutput.write(s.getEmail());
					csvOutput.write(s.getPassword());
					csvOutput.endRecord();
				}
				csvOutput.close();
			
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
}
