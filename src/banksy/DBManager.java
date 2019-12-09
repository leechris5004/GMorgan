package banksy;

import java.io.IOException;

public interface DBManager
{
	public void createNewDatabase(String fileName) throws IOException;
	public void createNewTable(String fileName) throws IOException;
}