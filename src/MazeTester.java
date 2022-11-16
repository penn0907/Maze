import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import org.junit.jupiter.api.*;
import java.util.*;


class MazeTester {
	
	private String[] mazes;
	
	@BeforeEach
	void setUp() {
		System.out.println("Starting Set Up...");
		try {
			int[] sizes = {4, 6, 8, 10, 20};
			mazes = new String[sizes.length];
			for(int i = 0; i < sizes.length; i ++) {
				String maze = "";
				File file = new File("maze" + sizes[i] +".txt");
				FileReader fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String line = br.readLine();
				line = br.readLine();
				while(line != null) {
					maze += line + "\n";
					line = br.readLine();
				}
				mazes[i] = maze;
				br.close();
				fr.close();
			}
		}
		catch(Exception err) {
			System.out.println(err.getMessage());
		}
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		System.out.println("First Test...");
		System.out.println(Arrays.toString(mazes));
		System.out.println(mazes[0]);
	}

}
