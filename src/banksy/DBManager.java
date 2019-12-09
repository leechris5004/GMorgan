package banksy;

import java.io.IOException;

public interface DBManager
{
	public void createNewDatabase(String fileName) throws IOException; 
}