package test.crawler.any;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

public class AnalyzerCsvTest {

	@Test
	public void testAnalyzer() throws Exception {
		String filePath = "./file/csv/test.csv";
		CSVReader reader = new CSVReader(new FileReader(filePath), ',');

		List<String[]> csvEntries = reader.readAll();

		Map<String, String> paramePairMap = new HashMap<String, String>();

		for (String[] paramePairStr : csvEntries) {
			System.out.println(paramePairStr[0] + ":" + paramePairStr[1]);

			// 将csv键值对装入map映射集合
			paramePairMap.put(paramePairStr[0], paramePairStr[1]);
		}

		// 遍历map映射集合
		Iterator<String> iterator = paramePairMap.keySet().iterator();
		while (iterator.hasNext()) {
			System.out.println(paramePairMap.get(iterator.next()));
		}
	}
}
